# 11 - Package and Access Control

This topic follows the master outline in [outline.md](../outline.md). The goal is to understand each concept deeply enough to explain it, recognize it in code, and answer interview-style questions.

## Study Order

- [What Is A Package Concepts](theory/01-what-is-a-package-concepts.md)
- [Key Terms](terms/01-key-terms.md)

## Outline Checklist

- What is a package?
- Create package
- Import package
- import static
- Default package
- Package naming convention
- Access between packages
- Classpath
- Basic module path

## Self-Check

- Why does Java use packages for namespace isolation and reverse DNS naming conventions?
- Why should the default package be avoided in production environments?
- Why do static imports increase code readability but also risk naming collisions?
- Why does Classpath differ from Module path regarding package access constraints?
- Why does default (package-private) access control exist, and how does it prevent external classes from accessing package-internal implementation details?
- Why does the Java compiler/runtime enforce a strict relationship between a class's package declaration and its physical directory structure?

## Anki Cards

- [Basic](anki/basic.tsv)
- [Basic Extra](anki/basic-extra.tsv)
- [Cloze](anki/cloze.tsv)
- [Code Question](anki/code-question.tsv)

## Mermaid Overview

```mermaid
flowchart TD
    A[Package and Access Control] --> B[Definitions]
    A --> C[Rules and syntax]
    A --> D[Common mistakes]
    A --> E[Interview recall]
```

## Reference Links

- https://docs.oracle.com/javase/tutorial/java/package/packages.html
- https://docs.oracle.com/javase/tutorial/java/package/usepkgs.html
