# Skill - Java Reference Auditor

## Goal

Make Java Core theory and Anki cards source-backed, link-checkable, and trustworthy.

## When To Use

Use this skill when adding or reviewing reference links in:

- `README.md`
- `theory/*.md`
- `terms/*.md`
- `anki/*.tsv`

## Rules

- Prefer official Java, Oracle, OpenJDK, and Dev.java sources.
- Use official library/tool docs for non-core tools.
- Do not use a link only because it is reachable.
- A reference must support the exact claim or behavior being taught.
- Put URLs in `## Reference Links` for theory files.
- Put local source paths and URLs in Anki `Source` fields.
- Run `./r.sh audit-links` before finalizing.

## Commands

```bash
./r.sh audit-links
./r.sh audit-links --topic 09-oop
```
