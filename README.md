# Learning Java

This repository is organized as a Java Core learning system: detailed theory notes, small practice files, and Anki flashcards for spaced repetition.

## How To Study

1. Open each numbered topic folder in order.
2. Start with the topic `README.md`; it acts as the index for that topic.
3. Read the detailed files inside `theory/`.
4. Review the flashcards inside the same topic's `anki/` folder.
5. Sync TSV files into Anki with AnkiConnect, or import them manually.
6. Practice with small Java files in `practice/small-exercises`.
7. Build mini projects in `extras/mini-projects` when the basics feel comfortable.

## Topic Folder Layout

For larger topics, do not put everything into one giant README. Use this structure:

```text
01-overview/
├── README.md
├── theory/
│   ├── 01-what-is-java.md
│   ├── 02-jvm-jre-jdk.md
│   └── ...
└── anki/
    ├── basic.tsv
    ├── basic-extra.tsv
    └── cloze.tsv
```

`README.md` is the entry point. Detailed theory belongs in `theory/*.md`. Anki cards belong in the same topic folder under `anki/`.

## Running Simple Java Files

```bash
cd practice/small-exercises
javac HelloWorld.java
java HelloWorld
```

If a file contains multiple non-public classes, compile the file that contains the `main` method:

```bash
javac ArrayPractice.java
java ArrayPractice
```

## Quick Anki Sync

Use the short helper script from the repository root:

```bash
./r.sh check
./r.sh topic 01-overview
./r.sh
```

More commands:

```bash
./r.sh dry
./r.sh dry-topic 01-overview
./r.sh probe
```

## 45 Java Core Topics

- `01-overview`: Java overview.
- `02-basic-syntax`: Basic syntax.
- `03-data-types`: Data types.
- `04-variables-constants`: Variables and constants.
- `05-operators`: Operators.
- `06-control-flow`: Control flow.
- `07-arrays`: Arrays.
- `08-string`: String.
- `09-oop`: Object-oriented programming.
- `10-modifiers`: Java modifiers.
- `11-package-access-control`: Packages and access control.
- `12-exception-handling`: Exception handling.
- `13-memory-management`: Java memory management.
- `14-object-class`: The `Object` class.
- `15-inner-nested-class`: Inner and nested classes.
- `16-enum`: Enum.
- `17-annotation`: Annotation.
- `18-generics`: Generics.
- `19-collections-framework`: Collections Framework.
- `20-comparable-comparator`: Comparable and Comparator.
- `21-lambda-expression`: Lambda expressions.
- `22-functional-interface`: Functional interfaces.
- `23-stream-api`: Stream API.
- `24-optional`: Optional.
- `25-date-time-api`: Date and Time API.
- `26-io`: Java IO.
- `27-nio`: NIO / NIO.2.
- `28-multithreading`: Multithreading.
- `29-synchronization-concurrency`: Synchronization and concurrency.
- `30-regex`: Regular expressions.
- `31-reflection`: Reflection.
- `32-classloader`: ClassLoader.
- `33-module-system`: Java Module System.
- `34-jdbc`: JDBC.
- `35-networking`: Networking.
- `36-security-basic`: Basic security.
- `37-jvm-advanced`: Advanced JVM.
- `38-build-compile-run`: Build, compile, and run.
- `39-utility-apis`: Common utility APIs.
- `40-modern-java-concepts`: Modern Java concepts.
- `41-best-practices`: Java best practices.
- `42-design-principles`: Basic design principles.
- `43-design-patterns`: Common Java design patterns.
- `44-unit-testing`: Basic unit testing.
- `45-interview-questions`: Common Java Core interview questions.

## Supporting Folders

- `00-setup`: JDK, IDE, compile/run setup.
- `skills`: Skills and rules for generating Java Core theory and Anki cards.
- `agents`: Agent prompts for generating topic notes and flashcards.
- `anki-guides`: Global Anki workflow and TSV templates.
- `scripts`: Utility scripts, including AnkiConnect sync.
- `practice`: Small Java practice files.
- `extras`: Extra topics and mini projects.
- `99-cheatsheets`: Fast review notes.

## Java Core Goal

After this roadmap, you should be able to:

- Explain how Java runs from `.java` source code to `.class` bytecode.
- Write console programs with variables, conditions, loops, methods, and arrays.
- Understand and apply OOP concepts.
- Choose and use common collections.
- Handle exceptions, files, dates, and time.
- Read and write simple lambda and stream code.
- Build small console projects.
