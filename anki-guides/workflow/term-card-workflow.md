# Term Explanation Workflow

Use this workflow when a theory file mentions terms but does not explain them deeply enough.

## Goal

Avoid shallow memorization. A learner should not only recall the word, but also understand:

- What the term means.
- Why it matters.
- Where it appears in Java.
- What it is commonly confused with.
- One small example or mental model.

## Folder Structure

Each topic may have a `terms/` folder:

```text
no01_overview/
├── README.md
├── theory/
├── terms/
│   └── 01-runtime-terms.md
└── anki/
```

Use `terms/*.md` when terms would make the main theory file too noisy.

## When To Create A Terms File

Create a terms file when:

- A theory file lists many terms but explains them briefly.
- A term is important for interviews.
- A term is easy to confuse with another term.
- The term appears in diagrams or commands but is not explained step by step.
- Understanding the term helps later topics.

Examples:

- JVM
- Bytecode
- Runtime
- Compile time
- Class loading
- Runtime library
- JIT warm-up
- Reachability
- LTS

## Terms File Template

```md
# Runtime Terms

## Term: Bytecode

### Short Definition

Bytecode is the intermediate instruction format stored in `.class` files and executed by the JVM.

### Why It Matters

Bytecode is what makes Java portable across platforms with compatible JVMs.

### Common Confusion

Bytecode is not Java source code and not native machine code.

### Example

`javac HelloWorld.java` produces `HelloWorld.class`, which contains bytecode.

### Cards To Create

- Basic definition card.
- Basic Extra confusion card.
- Cloze exact recall card.
- Code Question card if there is a command or code snippet.
```

## Card Creation Rules

For each important term, create at least:

1. One Basic definition card.
2. One Basic Extra "why it matters" or "common confusion" card.
3. One Cloze card for exact recall.

If the term appears in code, commands, output, or a compile/run flow, also create:

4. One Code Question card.

## ID Naming

Use a stable term-oriented ID:

```text
overview-term-basic-bytecode
overview-term-extra-bytecode-confusion
overview-term-cloze-bytecode
overview-term-code-bytecode-command
```

Prefer readable IDs over purely numeric IDs for term cards.

## Tags

Use normal topic tags plus `java::term`:

```text
java::core::overview java::term
```

Add `java::interview` if it is a common interview term.

## Quality Checklist

- Does the term have a short definition?
- Does it explain why the term matters?
- Does it mention common confusion?
- Is the source set to the `terms/*.md` file?
- Are cards spread across Basic, Basic Extra, Cloze, and Code Question when appropriate?
