# Anki Guides

This folder contains global Anki workflow notes and TSV templates.

Actual flashcards are stored inside each topic folder:

```text
no01_overview/anki/basic.tsv
no01_overview/anki/basic-extra.tsv
no01_overview/anki/cloze.tsv
no01_overview/anki/code-question.tsv
```

Use one deck:

```text
Java Core
```

Use tags to separate topics:

```text
java::core::overview
java::core::syntax
java::core::datatype
java::interview
```

Anki creates new tags automatically when you import TSV files that contain a `Tags` column.

## Sync With AnkiConnect

Read the full guide:

- [Syncing TSV Cards With AnkiConnect](workflow/ankiconnect-sync.md)

Quick check:

```bash
python3 scripts/sync_anki.py --check-connection
```

Dry run:

```bash
python3 scripts/sync_anki.py --dry-run
```

Sync:

```bash
python3 scripts/sync_anki.py
```
