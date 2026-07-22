# 45 - Common Java Core Interview Questions

This topic follows the master outline in [outline.md](../outline.md). The goal is to understand each concept deeply enough to explain it, recognize it in code, and answer interview-style questions.

## Study Order

- [How Are Jvm Jdk And Jre Different Concepts](theory/01-how-are-jvm-jdk-and-jre-different-concepts.md)
- [How Does Hashset Remove Duplicates Concepts](theory/02-how-does-hashset-remove-duplicates-concepts.md)
- [How Are Comparable And Comparator Different Concepts](theory/03-how-are-comparable-and-comparator-different-concepts.md)
- [How Are Map And Flatmap Different Concepts](theory/04-how-are-map-and-flatmap-different-concepts.md)
- [Key Terms](terms/01-key-terms.md)

## Outline Checklist

- How are JVM, JDK, and JRE different?
- Does Java pass references?
- What is the difference between == and .equals()?
- Why is String immutable?
- How are String, StringBuilder, and StringBuffer different?
- How does HashMap work?
- What improvements did HashMap have in Java 8?
- How are ArrayList and LinkedList different?
- How does HashSet remove duplicates?
- How are final, finally, and finalize different?
- How are checked and unchecked exceptions different?
- How are abstract class and interface different?
- How are overload and override different?
- Can static methods be overridden?
- Are constructors inherited?
- How are this and super different?
- How are Comparable and Comparator different?
- How are fail-fast and fail-safe iterators different?
- How are volatile and synchronized different?
- What is deadlock?
- How are Thread start() and run() different?
- How are sleep() and wait() different?
- How are notify() and notifyAll() different?
- Is Stream API lazy?
- How are map and flatMap different?
- How are orElse and orElseGet different?
- How are HashMap, Hashtable, and ConcurrentHashMap different?
- Why must overriding equals() also override hashCode()?
- How does Garbage Collection work?
- How are Stack and Heap different?

## Self-Check

Before moving to the next topic, verify that you can answer these questions:
1. Why do the JDK, JRE, and JVM serve different software development lifecycle purposes, and what are their runtime and compiler-level distinctions?
   &rarr; See [Why JDK, JRE, and JVM Differ](theory/01-how-are-jvm-jdk-and-jre-different-concepts.md#why-jdk-jre-and-jvm-differ)
2. Why does `HashSet` remove duplicates, and how does it leverage backing `HashMap` put operations to enforce unique element constraints?
   &rarr; See [Why HashSet Leverages HashMap to Remove Duplicates](theory/02-how-does-hashset-remove-duplicates-concepts.md#why-hashset-leverages-hashmap-to-remove-duplicates)
3. Why do `Comparable` and `Comparator` serve different sorting design purposes, and what is the difference between natural ordering vs custom external sorting rules?
   &rarr; See [Why Comparable and Comparator Differ in Sorting Design](theory/03-how-are-comparable-and-comparator-different-concepts.md#why-comparable-and-comparator-differ-in-sorting-design)
4. Why do `map` and `flatMap` operations in Streams have different transformation signatures, and what does it mean to flatten a stream structure?
   &rarr; See [Why map and flatMap Stream Operations Differ](theory/04-how-are-map-and-flatmap-different-concepts.md#why-map-and-flatmap-stream-operations-differ)
5. Why does Java compile time generic type verification differ from runtime execution behavior, and what runtime casting bugs can occur due to raw types?
   &rarr; See [Why Generic Compile-Time Verification Differs from Runtime](theory/04-how-are-map-and-flatmap-different-concepts.md#why-generic-compile-time-verification-differs-from-runtime)

## Anki Cards

- [Basic](anki/basic.tsv)
- [Basic Extra](anki/basic-extra.tsv)
- [Cloze](anki/cloze.tsv)
- [Code Question](anki/code-question.tsv)

## Mermaid Overview

```mermaid
flowchart TD
    A[Common Java Core Interview Questions] --> B[Definitions]
    A --> C[Rules and syntax]
    A --> D[Common mistakes]
    A --> E[Interview recall]
```

## Reference Links

- https://docs.oracle.com/javase/tutorial/
- https://dev.java/learn/
