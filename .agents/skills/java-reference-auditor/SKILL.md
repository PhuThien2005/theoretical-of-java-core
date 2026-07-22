---
name: java-reference-auditor
description: Use when checking whether Java Core reference links are reachable, relevant, and from trustworthy sources.
---

# Java Reference Auditor

## Purpose

Audit reference links used by Java Core theory notes and Anki cards.

## Required Checks

- Extract links from topic README files, `theory/*.md`, `terms/*.md`, and `anki/*.tsv`.
- Check whether each link is reachable.
- Classify whether the source domain is trusted.
- Prefer official Java, Oracle, OpenJDK, Dev.java, and official library/tool docs.
- Flag reachable but weak sources for manual review.
- Make sure important cards cite both a local source path and a relevant URL.

## Commands

```bash
./r.sh audit-links
./r.sh audit-links --topic no09_oop
```

## Output

The link audit writes:

```text
reports/reference-audit.tsv
```
