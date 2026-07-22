# 31 - Reflection

This topic follows the master outline in [outline.md](../outline.md). The goal is to understand each concept deeply enough to explain it, recognize it in code, and answer interview-style questions.

## Study Order

- [What Is Reflection Concepts](theory/01-what-is-reflection-concepts.md)
- [Advantages And Disadvantages Of Reflection Concepts](theory/02-advantages-and-disadvantages-of-reflection-concepts.md)
- [Key Terms](terms/01-key-terms.md)

## Outline Checklist

- What is Reflection?
- Class<?>
- Get class information
- Get field
- Get method
- Get constructor
- Invoke method using reflection
- Create object using reflection
- Access private field/method
- Annotation + reflection
- Advantages and disadvantages of reflection
- Reflection in frameworks such as Spring

## Self-Check

1. Why does reflection bypass compile-time type safety, and what are the mechanisms and risks of dynamic metadata resolution at runtime?
2. Why does reflection introduce significant performance penalties compared to direct bytecode execution, and how can MethodHandles or call-site caching optimize this?
3. Why can setAccessible(true) bypass Java's access visibility controls, and how do JVM Security Managers and the Java Module System (Jigsaw) restrict this behavior?
4. Why does reflective dynamic classloading and instantiation pose severe security and stability risks (such as unsafe deserialization), and how can they be mitigated?
5. Why is reflection the critical enabler for modern Dependency Injection (DI) and Object-Relational Mapping (ORM) frameworks, and how do they use metadata annotations to manage object lifecycles?

## Anki Cards

- [Basic](anki/basic.tsv)
- [Basic Extra](anki/basic-extra.tsv)
- [Cloze](anki/cloze.tsv)
- [Code Question](anki/code-question.tsv)

## Mermaid Overview

```mermaid
flowchart TD
    A[Reflection] --> B[Definitions]
    A --> C[Rules and syntax]
    A --> D[Common mistakes]
    A --> E[Interview recall]
```

## Reference Links

- https://docs.oracle.com/javase/tutorial/reflect/
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/reflect/package-summary.html
