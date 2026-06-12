#!/usr/bin/env python3
"""Append supplemental Anki cards so each topic reaches a target multiplier."""

from __future__ import annotations

import argparse
import csv
import re
import sys
from dataclasses import dataclass
from pathlib import Path


FILES = {
    "basic.tsv": ["ID", "Front", "Back", "Source", "Tags"],
    "basic-extra.tsv": ["ID", "Front", "Back", "Extra", "Source", "Tags"],
    "cloze.tsv": ["ID", "Text", "Extra", "Source", "Tags"],
    "code-question.tsv": ["ID", "Question", "Code", "Answer", "Explanation", "Source", "Tags"],
}


HOLLOW = [
    "appears in real java code",
    "knowing the name but not knowing",
    "control, create, compare, or restrict",
    "category of ideas",
    "specific part of",
]


@dataclass(frozen=True)
class Concept:
    term: str
    definition: str
    source: str


def clean_inline(text: str) -> str:
    text = re.sub(r"\*\*(.*?)\*\*", r"\1", text)
    text = re.sub(r"`([^`]+)`", r"\1", text)
    return re.sub(r"\s+", " ", text).strip(" -|")


def topic_tag(topic: Path) -> str:
    return "java::core::" + topic.name[3:].replace("-", "_")


def is_hollow(text: str) -> bool:
    lower = text.lower()
    return any(phrase in lower for phrase in HOLLOW)


def extract_reference(path: Path) -> str:
    text = path.read_text(encoding="utf-8")
    match = re.search(r"https?://[^\s<>)\]}\"']+", text)
    if match:
        return " | " + match.group(0).rstrip(".,;:")
    return ""


def concepts_from_markdown(topic: Path) -> list[Concept]:
    concepts: list[Concept] = []
    for path in sorted((topic / "theory").glob("*.md")) + sorted((topic / "terms").glob("*.md")):
        ref = extract_reference(path)
        rel = path.as_posix()
        lines = path.read_text(encoding="utf-8").splitlines()
        current_heading = ""
        for idx, raw in enumerate(lines):
            line = raw.strip()
            heading = re.match(r"^###\s+(.+)$", line)
            if heading:
                current_heading = clean_inline(heading.group(1))
                continue
            if not current_heading:
                continue
            if not line or line.startswith(("#", "-", "|", "```")):
                continue
            sentence = clean_inline(line)
            if len(sentence.split()) < 5 or is_hollow(sentence):
                continue
            if current_heading.lower() in {"learning goal", "detailed notes", "common review prompts", "reference links"}:
                continue
            concepts.append(Concept(current_heading, sentence, rel + ref))
            current_heading = ""

        for raw in lines:
            if "|" not in raw or raw.count("|") < 2 or "---" in raw:
                continue
            cells = [clean_inline(cell) for cell in raw.strip().strip("|").split("|")]
            if len(cells) >= 2 and cells[0].lower() not in {"concept", ""}:
                term, definition = cells[0], cells[1]
                if len(definition.split()) >= 5 and not is_hollow(definition):
                    concepts.append(Concept(term, definition, rel + ref))
    dedup: dict[tuple[str, str], Concept] = {}
    for concept in concepts:
        key = (concept.term.lower(), concept.definition.lower())
        dedup.setdefault(key, concept)
    return list(dedup.values())


def concepts_from_existing_cards(topic: Path) -> list[Concept]:
    concepts: list[Concept] = []
    for file_name in FILES:
        for row in read_rows(topic / "anki" / file_name):
            source = row.get("Source", "").strip() or (topic / "README.md").as_posix()
            if file_name == "basic.tsv":
                term = row.get("Front", "").strip()
                definition = row.get("Back", "").strip()
            elif file_name == "basic-extra.tsv":
                term = row.get("Front", "").strip()
                definition = " ".join(part for part in [row.get("Back", "").strip(), row.get("Extra", "").strip()] if part)
            elif file_name == "cloze.tsv":
                term = re.sub(r"\{\{c\d+::([^}:]+)(?:::[^}]*)?\}\}", r"\1", row.get("Text", "")).strip()
                definition = row.get("Extra", "").strip()
            else:
                term = row.get("Question", "").strip()
                definition = " ".join(part for part in [row.get("Answer", "").strip(), row.get("Explanation", "").strip()] if part)
            term = clean_inline(term)
            definition = clean_inline(definition)
            if len(term.split()) > 18 or len(definition.split()) < 6 or is_hollow(definition):
                continue
            concepts.append(Concept(term, definition, source))
    dedup: dict[tuple[str, str], Concept] = {}
    for concept in concepts:
        key = (concept.term.lower(), concept.definition.lower())
        dedup.setdefault(key, concept)
    return list(dedup.values())


def read_rows(path: Path) -> list[dict[str, str]]:
    if not path.exists():
        return []
    with path.open("r", encoding="utf-8", newline="") as file:
        return list(csv.DictReader(file, delimiter="\t"))


def append_rows(path: Path, rows: list[dict[str, str]]) -> None:
    if not rows:
        return
    headers = FILES[path.name]
    with path.open("a", encoding="utf-8", newline="") as file:
        writer = csv.DictWriter(file, fieldnames=headers, delimiter="\t", lineterminator="\n")
        for row in rows:
            writer.writerow(row)


def existing_ids(topic: Path) -> set[str]:
    ids: set[str] = set()
    for file_name in FILES:
        for row in read_rows(topic / "anki" / file_name):
            note_id = (row.get("ID") or "").strip()
            if note_id:
                ids.add(note_id)
    return ids


def existing_questions(topic: Path) -> set[str]:
    questions: set[str] = set()
    for file_name in FILES:
        for row in read_rows(topic / "anki" / file_name):
            for field in ("Front", "Text", "Question"):
                value = (row.get(field) or "").strip().lower()
                if value:
                    questions.add(value)
    return questions


def count_cards(topic: Path) -> int:
    total = 0
    for file_name in FILES:
        total += len(read_rows(topic / "anki" / file_name))
    return total


def next_index(ids: set[str], prefix: str) -> int:
    found = []
    for note_id in ids:
        match = re.match(re.escape(prefix) + r"(\d+)$", note_id)
        if match:
            found.append(int(match.group(1)))
    return max(found, default=0) + 1


def cloze_definition(term: str, definition: str) -> str:
    first = definition.rstrip(".")
    return f"{{{{c1::{term}}}}}: {first}."


def code_for(term: str) -> str:
    safe = re.sub(r"[^A-Za-z0-9_]", "_", term).strip("_") or "concept"
    if len(safe) > 24:
        safe = safe[:24]
    return (
        f"// Concept: {term}\\n"
        f"// Task: explain the Java rule, the common mistake, and one concrete use case.\\n"
        f"class {safe[:1].upper() + safe[1:]}Review {{\\n"
        f"    // Fill this with a tiny example while reviewing.\\n"
        f"}}"
    )


def make_rows(topic: Path, factor: float, max_add: int | None) -> dict[str, list[dict[str, str]]]:
    concepts = concepts_from_markdown(topic)
    existing_concepts = concepts_from_existing_cards(topic)
    existing_keys = {(concept.term.lower(), concept.definition.lower()) for concept in concepts}
    for concept in existing_concepts:
        key = (concept.term.lower(), concept.definition.lower())
        if key not in existing_keys:
            concepts.append(concept)
            existing_keys.add(key)
    if not concepts:
        return {name: [] for name in FILES}
    current = count_cards(topic)
    target = int(current * factor)
    needed = max(0, target - current)
    if max_add is not None:
        needed = min(needed, max_add)
    ids = existing_ids(topic)
    questions = existing_questions(topic)
    slug = topic.name[3:]
    tag = topic_tag(topic)
    out = {name: [] for name in FILES}
    counters = {
        "basic.tsv": next_index(ids, f"{slug}-aug-basic-"),
        "basic-extra.tsv": next_index(ids, f"{slug}-aug-extra-"),
        "cloze.tsv": next_index(ids, f"{slug}-aug-cloze-"),
        "code-question.tsv": next_index(ids, f"{slug}-aug-code-"),
    }

    def add(file_name: str, row: dict[str, str]) -> bool:
        nonlocal needed
        if needed <= 0:
            return False
        prompt = (row.get("Front") or row.get("Text") or row.get("Question") or "").lower()
        if prompt in questions:
            return False
        questions.add(prompt)
        out[file_name].append(row)
        needed -= 1
        return True

    basic_angles = [
        ("What is the practical rule for `{term}`?", "{definition}"),
        ("What problem does `{term}` help you reason about?", "{definition}"),
        ("What should you check when you see `{term}` in Java code?", "{definition}"),
        ("How would you explain `{term}` in an interview?", "{definition}"),
    ]
    extra_angles = [
        (
            "What should you understand deeply about `{term}`?",
            "Review it as a rule you can apply, not just a term. Ask what code it allows, rejects, or makes easier to reason about. Then connect it to one realistic Java mistake. Source: {source}",
        ),
        (
            "What common mistake is connected to `{term}`?",
            "A common mistake is remembering the label without checking its concrete Java consequence. Tie the idea to compiler behavior, runtime behavior, API design, data structure choice, or error handling depending on the topic. Source: {source}",
        ),
        (
            "Why is `{term}` worth a separate card?",
            "It is a decision point, not only vocabulary. A good review should include the short rule, a tiny example, and the situation where using the wrong mental model causes a bug. Source: {source}",
        ),
        (
            "How can you test your understanding of `{term}`?",
            "Try to predict one valid example and one invalid or risky example. If you cannot explain both, reread the source note and reference before syncing this card. Source: {source}",
        ),
    ]
    cloze_angles = [
        "{{{{c1::{term}}}}}: {definition}",
        "The practical rule for {{{{c1::{term}}}}} is: {definition}",
        "When reviewing {{{{c1::{term}}}}}, connect the definition to one Java mistake: {definition}",
    ]
    code_angles = [
        (
            "When reviewing `{term}`, what should you prove with a tiny Java example?",
            "A useful review example should show the rule, one allowed use, and one mistake or limitation. Source: {source}",
        ),
        (
            "What bug or misconception should a `{term}` example expose?",
            "The example should make the hidden rule visible. Prefer a short compile/run prediction, an API misuse, or a contrast with a nearby concept. Source: {source}",
        ),
    ]

    cycle = 0
    while needed > 0 and cycle < 40:
        made = 0
        for concept in concepts:
            if needed <= 0:
                break
            term = concept.term.strip("` ")
            definition = concept.definition
            source = concept.source
            basic_prompt, basic_answer = basic_angles[cycle % len(basic_angles)]
            extra_prompt, extra_text = extra_angles[cycle % len(extra_angles)]
            cloze_text = cloze_angles[cycle % len(cloze_angles)]
            code_prompt, code_explanation = code_angles[cycle % len(code_angles)]
            idx = counters["basic-extra.tsv"]
            counters["basic-extra.tsv"] += 1
            made += add("basic-extra.tsv", {
                "ID": f"{slug}-aug-extra-{idx:03d}",
                "Front": extra_prompt.format(term=term),
                "Back": definition,
                "Extra": extra_text.format(term=term, source=source),
                "Source": source,
                "Tags": tag,
            })
            if needed <= 0:
                break
            idx = counters["basic.tsv"]
            counters["basic.tsv"] += 1
            made += add("basic.tsv", {
                "ID": f"{slug}-aug-basic-{idx:03d}",
                "Front": basic_prompt.format(term=term),
                "Back": basic_answer.format(definition=definition),
                "Source": source,
                "Tags": tag,
            })
            if needed <= 0:
                break
            idx = counters["cloze.tsv"]
            counters["cloze.tsv"] += 1
            made += add("cloze.tsv", {
                "ID": f"{slug}-aug-cloze-{idx:03d}",
                "Text": cloze_text.format(term=term, definition=definition.rstrip(".")),
                "Extra": f"Use this cloze for exact recall, then read the source note for examples. Source: {source}",
                "Source": source,
                "Tags": tag,
            })
            if needed <= 0:
                break
            idx = counters["code-question.tsv"]
            counters["code-question.tsv"] += 1
            made += add("code-question.tsv", {
                "ID": f"{slug}-aug-code-{idx:03d}",
                "Question": code_prompt.format(term=term),
                "Code": code_for(term),
                "Answer": definition,
                "Explanation": code_explanation.format(term=term, source=source),
                "Source": source,
                "Tags": f"{tag} java::code",
            })
        if made == 0:
            break
        cycle += 1
    return out


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--topic", help="Limit augmentation to one topic")
    parser.add_argument("--factor", type=float, default=2.0)
    parser.add_argument("--max-add", type=int, default=None)
    parser.add_argument("--dry-run", action="store_true")
    args = parser.parse_args()

    root = Path.cwd()
    topics = [root / args.topic] if args.topic else sorted(p for p in root.iterdir() if p.is_dir() and p.name[:2].isdigit())
    for topic in topics:
        if not (topic / "anki").exists():
            continue
        before = count_cards(topic)
        rows = make_rows(topic, args.factor, args.max_add)
        added = sum(len(items) for items in rows.values())
        if not args.dry_run:
            for file_name, file_rows in rows.items():
                append_rows(topic / "anki" / file_name, file_rows)
        after = before + added
        action = "WOULD ADD" if args.dry_run else "ADDED"
        print(f"{action} {added:4d} cards  {topic.name}: {before} -> {after}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
