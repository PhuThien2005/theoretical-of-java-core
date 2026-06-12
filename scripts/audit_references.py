#!/usr/bin/env python3
"""Audit reference links used by Java theory notes and Anki TSV cards."""

from __future__ import annotations

import argparse
import csv
import re
import socket
import ssl
import sys
import urllib.error
import urllib.parse
import urllib.request
from dataclasses import dataclass
from pathlib import Path


URL_RE = re.compile(r"https?://[^\s<>)\]}\"']+")
TRUSTED_DOMAINS = {
    "docs.oracle.com": "official Java/Oracle documentation",
    "dev.java": "official Java learning site",
    "openjdk.org": "official OpenJDK project site",
    "docs.gradle.org": "official Gradle documentation",
    "gradle.org": "official Gradle site",
    "maven.apache.org": "official Maven documentation",
    "junit.org": "official JUnit site",
    "docs.spring.io": "official Spring documentation",
    "spring.io": "official Spring site",
    "github.com": "source repository or project documentation",
    "refactoring.guru": "reputable design-pattern reference, not a Java language authority",
}


@dataclass(frozen=True)
class UrlHit:
    url: str
    source: Path
    line: int


def iter_files(root: Path, topic: str | None) -> list[Path]:
    base = root / topic if topic else root
    patterns = ["*/README.md", "*/theory/*.md", "*/terms/*.md", "*/anki/*.tsv"]
    files: list[Path] = []
    if topic:
        patterns = ["README.md", "theory/*.md", "terms/*.md", "anki/*.tsv"]
    for pattern in patterns:
        files.extend(sorted(base.glob(pattern)))
    return [path for path in files if path.is_file()]


def extract_urls(root: Path, topic: str | None) -> list[UrlHit]:
    hits: list[UrlHit] = []
    for path in iter_files(root, topic):
        for line_no, line in enumerate(path.read_text(encoding="utf-8").splitlines(), start=1):
            for match in URL_RE.finditer(line):
                url = match.group(0).rstrip(".,;:")
                hits.append(UrlHit(url=url, source=path, line=line_no))
    return hits


def trust_label(url: str) -> str:
    host = urllib.parse.urlparse(url).netloc.lower()
    host = host.split("@")[-1].split(":")[0]
    for domain, label in TRUSTED_DOMAINS.items():
        if host == domain or host.endswith("." + domain):
            return label
    return "needs manual trust review"


def check_url(url: str, timeout: float) -> tuple[str, str]:
    headers = {"User-Agent": "learning-java-reference-audit/1.0"}
    for method in ("HEAD", "GET"):
        request = urllib.request.Request(url, method=method, headers=headers)
        try:
            with urllib.request.urlopen(request, timeout=timeout, context=ssl.create_default_context()) as response:
                status = getattr(response, "status", 200)
                return "ok" if status < 400 else "bad", str(status)
        except urllib.error.HTTPError as exc:
            if method == "HEAD" and exc.code in {403, 405, 429}:
                continue
            return "bad", f"HTTP {exc.code}"
        except (urllib.error.URLError, socket.timeout, TimeoutError) as exc:
            if method == "HEAD":
                continue
            return "bad", exc.__class__.__name__
    return "bad", "unknown"


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--topic", help="Limit audit to one topic folder, e.g. 09-oop")
    parser.add_argument("--timeout", type=float, default=8.0)
    parser.add_argument("--no-network", action="store_true", help="Only report extracted links and trust labels")
    parser.add_argument("--output", default="reports/reference-audit.tsv")
    args = parser.parse_args()

    root = Path.cwd()
    hits = extract_urls(root, args.topic)
    unique_urls = sorted({hit.url for hit in hits})
    status_cache: dict[str, tuple[str, str]] = {}
    if not args.no_network:
        for url in unique_urls:
            status_cache[url] = check_url(url, args.timeout)

    output = root / args.output
    output.parent.mkdir(parents=True, exist_ok=True)
    with output.open("w", encoding="utf-8", newline="") as file:
        writer = csv.writer(file, delimiter="\t")
        writer.writerow(["status", "detail", "trust", "url", "source", "line"])
        for hit in hits:
            status, detail = status_cache.get(hit.url, ("not_checked", "network disabled"))
            writer.writerow([
                status,
                detail,
                trust_label(hit.url),
                hit.url,
                hit.source.as_posix(),
                hit.line,
            ])

    bad = [url for url, (status, _) in status_cache.items() if status != "ok"]
    manual = [url for url in unique_urls if trust_label(url) == "needs manual trust review"]
    print(f"Links found: {len(unique_urls)}")
    print(f"Report: {output.as_posix()}")
    if manual:
        print(f"Manual trust review: {len(manual)}")
    if bad:
        print("Unreachable links:")
        for url in bad:
            print(f"- {url} ({status_cache[url][1]})")
        return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
