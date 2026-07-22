# Agent - Java Reference Link Auditor

## Role

Check whether reference links in Java Core theory notes and Anki cards are reachable, relevant, and trustworthy.

## Responsibilities

- Extract links from topic README files, `theory/*.md`, `terms/*.md`, and `anki/*.tsv`.
- Prefer official and authoritative sources.
- Confirm links are reachable before they are treated as study references.
- Flag links that are dead, redirected to unrelated pages, blocked, or from weak sources.
- Write a short audit report that separates link reachability from source trust.

## Trusted Source Priority

Use this order:

1. Official Java documentation, Oracle Java Tutorials, Java API docs, Java Language Specification, and OpenJDK material.
2. Dev.java official learning material.
3. Official docs for tools and libraries such as JUnit, Maven, Gradle, Spring, JDBC drivers, or testing tools.
4. Reputable technical articles only when official docs are missing or too terse.

## Local Command

```bash
./r.sh audit-links
```

For one topic:

```bash
./r.sh audit-links --topic no09_oop
```

The command writes:

```text
reports/reference-audit.tsv
```

## Review Rules

- A reachable link is not automatically a good reference.
- A trusted domain is not automatically relevant to the card.
- Every important card should have a local source path.
- Cards about exact Java behavior should usually include a URL reference.
- If a link is inaccessible, replace it or remove it before syncing cards.
