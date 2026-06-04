# Mermaid Images In Anki Cards

Anki does not reliably render Mermaid diagrams directly inside cards by default.

Use this stable workflow instead:

```text
Mermaid source in Markdown or .mmd
        ↓
export to SVG or PNG
        ↓
put exported file in <topic>/media/anki/
        ↓
sync script uploads media to Anki
        ↓
card uses <img src="file.svg">
```

## Folder Layout

```text
01-overview/
├── theory/
├── media/
│   ├── mermaid/
│   │   └── compile-runtime-flow.mmd
│   └── anki/
│       └── overview-compile-runtime-flow.svg
└── anki/
    └── basic-extra.tsv
```

## Export Mermaid To SVG

If Mermaid CLI is installed:

```bash
mmdc -i 01-overview/media/mermaid/compile-runtime-flow.mmd -o 01-overview/media/anki/overview-compile-runtime-flow.svg
```

SVG is usually good for diagrams because it stays sharp.

PNG is also fine:

```bash
mmdc -i 01-overview/media/mermaid/compile-runtime-flow.mmd -o 01-overview/media/anki/overview-compile-runtime-flow.png
```

## Add Image To A Card

Use HTML in a TSV field:

```html
<img src="overview-compile-runtime-flow.svg">
```

Example `Extra` field:

```html
Flow: source -> compiler -> bytecode -> JVM.<br><img src="overview-compile-runtime-flow.svg">
```

## Sync Media

The sync script automatically uploads files in:

```text
<topic>/media/anki/*
```

Run:

```bash
./r.sh topic 01-overview
```

## Rules

- Keep media filenames unique across the whole Anki collection.
- Prefer filenames with topic prefixes, such as `overview-compile-runtime-flow.svg`.
- Do not reference local file paths in cards.
- Reference only the media filename after it is uploaded.
- Keep Mermaid source in `media/mermaid/` so diagrams can be regenerated later.
