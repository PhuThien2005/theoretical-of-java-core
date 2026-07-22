# Skill - Anki Card Quality Review

## Goal

Keep Java Core Anki cards useful, specific, and non-generic.

## When To Use

Use this skill after generating or expanding Anki TSV files.

## Rules

- One card should test one idea.
- Avoid vague cards that only say a concept matters.
- Avoid repeated template wording across unrelated concepts.
- `Extra` should explain meaning, why it matters, common confusion, and a small example when useful.
- `Explanation` in code cards should explain the rule behind the answer.
- Important or exact Java behavior should have a source URL.
- Run `./r.sh audit-cards` before finalizing.

## Commands

```bash
./r.sh audit-cards
./r.sh audit-cards --topic no09_oop
```
