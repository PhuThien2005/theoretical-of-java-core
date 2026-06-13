#!/usr/bin/env python3
"""Find Anki cards that are too generic, under-explained, or poorly sourced."""

from __future__ import annotations

import argparse
import csv
import re
import sys
from dataclasses import dataclass
from pathlib import Path


GENERIC_PHRASES = [
    "appears in real java code and interview questions",
    "knowing the name but not knowing the practical rule",
    "control, create, compare, or restrict",
    "type safety, runtime behavior, memory, ordering, error handling, visibility, api design, or testability",
    "category of ideas",
    "specific part of",
    "is a specific concept in",
    "learn its java rule, valid use cases, and failure mode rather than only its name",
    "use it to predict the exact java rule",
    "review it with a tiny example instead of memorizing only the label",
    "a common mistake is knowing the name but not knowing",
]

# Patterns where an answer states a count/number but doesn't enumerate the items.
# e.g. "Java has 8 primitive types." with no listing is a hollow answer.
import re as _re
HOLLOW_COUNT_PATTERN = _re.compile(
    r'\bjava has \d+ [\w ]+(\.|$)',
    _re.IGNORECASE,
)
# Short answers (< 8 words) on question cards that start with "how many" or "what are"
HOLLOW_SHORT_THRESHOLD = 8
HOLLOW_QUESTION_PREFIXES = ("how many", "what are", "list ", "name ", "which ")


EXPECTED_COLUMNS = {
    "basic.tsv": ["ID", "Front", "Back", "Source", "Tags"],
    "basic-extra.tsv": ["ID", "Front", "Back", "Extra", "Source", "Tags"],
    "cloze.tsv": ["ID", "Text", "Extra", "Source", "Tags"],
    "code-question.tsv": ["ID", "Question", "Code", "Answer", "Explanation", "Source", "Tags"],
}


@dataclass
class Finding:
    severity: str
    path: Path
    line: int
    note_id: str
    reason: str


def row_text(row: dict[str, str]) -> str:
    return " ".join((value or "") for value in row.values()).lower()


def has_url(value: str) -> bool:
    return "http://" in value or "https://" in value


def is_local_source_valid(root: Path, source: str) -> bool:
    local = source.split("|", 1)[0].strip()
    if not local:
        return False
    return (root / local).exists()


def audit_file(root: Path, path: Path) -> list[Finding]:
    findings: list[Finding] = []
    expected = EXPECTED_COLUMNS.get(path.name)
    if not expected:
        return findings
    with path.open("r", encoding="utf-8", newline="") as file:
        reader = csv.DictReader(file, delimiter="\t")
        headers = reader.fieldnames or []
        if headers != expected:
            findings.append(Finding("error", path, 1, "", f"header should be {expected}, got {headers}"))
            return findings
        seen_ids: set[str] = set()
        for line_no, row in enumerate(reader, start=2):
            note_id = (row.get("ID") or "").strip()
            text = row_text(row)
            if note_id in seen_ids:
                findings.append(Finding("error", path, line_no, note_id, "duplicate ID inside file"))
            seen_ids.add(note_id)
            for phrase in GENERIC_PHRASES:
                if phrase in text:
                    findings.append(Finding("warn", path, line_no, note_id, f"generic/template wording: {phrase}"))
                    break
            # Detect hollow answers: answer states a count but doesn't list items
            back = row.get("Back") or row.get("Text") or ""
            front = (row.get("Front") or row.get("Question") or "").lower().strip()
            if HOLLOW_COUNT_PATTERN.search(back) and len(back.split()) < 12:
                findings.append(Finding("warn", path, line_no, note_id,
                    "hollow count answer: states a number but does not list the items (e.g. 'Java has 8 primitive types' — list them)"))
            elif any(front.startswith(p) for p in HOLLOW_QUESTION_PREFIXES):
                if len(back.split()) < HOLLOW_SHORT_THRESHOLD:
                    findings.append(Finding("warn", path, line_no, note_id,
                        f"shallow answer for listing/count question: '{back[:80]}' — expand to include all items or a meaningful grouping"))
            source = row.get("Source", "")
            if source and not is_local_source_valid(root, source):
                findings.append(Finding("warn", path, line_no, note_id, "local Source path does not exist"))
            if path.name == "basic-extra.tsv":
                extra = row.get("Extra", "").strip()
                if len(extra.split()) < 18:
                    findings.append(Finding("warn", path, line_no, note_id, "Extra is too short for a deep card"))
            if path.name == "code-question.tsv":
                code = row.get("Code", "")
                if "\\n" not in code and "\n" not in code and len(code.split()) > 12:
                    findings.append(Finding("info", path, line_no, note_id, "Code may need escaped newlines for readability"))
            important = any(tag in text for tag in ["java::interview", "jvm", "thread", "generic", "collection", "exception"])
            if important and not has_url(source + " " + row.get("Extra", "") + " " + row.get("Explanation", "")):
                findings.append(Finding("info", path, line_no, note_id, "important card has no URL reference"))
    return findings


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--topic", help="Limit audit to one topic folder")
    parser.add_argument("--fail-on-warn", action="store_true")
    parser.add_argument("--output", default="reports/anki-quality-audit.tsv")
    args = parser.parse_args()

    root = Path.cwd()
    base = root / args.topic if args.topic else root
    files = sorted(base.glob("anki/*.tsv")) if args.topic else sorted(root.glob("*/anki/*.tsv"))
    findings: list[Finding] = []
    for path in files:
        findings.extend(audit_file(root, path))

    output = root / args.output
    output.parent.mkdir(parents=True, exist_ok=True)
    with output.open("w", encoding="utf-8", newline="") as file:
        writer = csv.writer(file, delimiter="\t")
        writer.writerow(["severity", "path", "line", "id", "reason"])
        for item in findings:
            writer.writerow([item.severity, item.path.as_posix(), item.line, item.note_id, item.reason])

    counts: dict[str, int] = {}
    for item in findings:
        counts[item.severity] = counts.get(item.severity, 0) + 1
    print(f"Findings: {counts or {}}")
    print(f"Report: {output.as_posix()}")
    if counts.get("error", 0):
        return 1
    if args.fail_on_warn and counts.get("warn", 0):
        return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
