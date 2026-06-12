---
name: anki-card-quality-reviewer
description: Use when reviewing Java Core Anki TSV cards for specificity, usefulness, source quality, and non-generic explanations.
---

# Anki Card Quality Reviewer

## Purpose

Prevent shallow, repetitive, or weak Anki cards from entering the study deck.

## Required Checks

- Verify TSV headers and stable IDs.
- Flag generic template wording.
- Flag `Extra` fields that do not explain deeply.
- Flag code cards whose code is unreadable or only a placeholder.
- Flag important cards that lack a source URL.
- Verify local source paths exist.

## Commands

```bash
./r.sh audit-cards
./r.sh audit-cards --topic 09-oop
```

## Output

The quality audit writes:

```text
reports/anki-quality-audit.tsv
```
