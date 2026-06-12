# Common Java Core Interview Questions - Part 1

## Learning Goal

This file covers a focused slice of **Common Java Core Interview Questions**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `How are JVM, JDK, and JRE different?` | The JVM executes bytecode and manages runtime services such as memory, JIT, and GC. |
| `Does Java pass references?` |Does Java pass references? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `What is the difference between == and .equals()?` | equals() defines logical equality between objects. |
| `Why is String immutable?` |Why is String immutable? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `How are String, StringBuilder, and StringBuffer different?` |How are String, StringBuilder, and StringBuffer different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `How does HashMap work?` | A Map stores key-value pairs and retrieves values by key. |
| `What improvements did HashMap have in Java 8?` | A Map stores key-value pairs and retrieves values by key. |
| `How are ArrayList and LinkedList different?` | A List is an ordered collection that can contain duplicates and supports positional access. |

## Detailed Notes

### How are JVM, JDK, and JRE different?

The JVM executes bytecode and manages runtime services such as memory, JIT, and GC.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `How are JVM, JDK, and JRE different?` in one sentence.
- Recognize `How are JVM, JDK, and JRE different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are JVM, JDK, and JRE different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are JVM, JDK, and JRE different?` change, allow, reject, or clarify?

### Does Java pass references?

Does Java pass references? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Does Java pass references?` in one sentence.
- Recognize `Does Java pass references?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Does Java pass references?`.

Tiny example or mental model:

- When reading code, ask: what does `Does Java pass references?` change, allow, reject, or clarify?

### What is the difference between == and .equals()?

equals() defines logical equality between objects.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `What is the difference between == and .equals()?` in one sentence.
- Recognize `What is the difference between == and .equals()?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is the difference between == and .equals()?`.

Tiny example or mental model:

- When reading code, ask: what does `What is the difference between == and .equals()?` change, allow, reject, or clarify?

### Why is String immutable?

Why is String immutable? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Why is String immutable?` in one sentence.
- Recognize `Why is String immutable?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Why is String immutable?`.

Tiny example or mental model:

- When reading code, ask: what does `Why is String immutable?` change, allow, reject, or clarify?

### How are String, StringBuilder, and StringBuffer different?

How are String, StringBuilder, and StringBuffer different? is a specific concept in Common Java Core Interview Questions; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `How are String, StringBuilder, and StringBuffer different?` in one sentence.
- Recognize `How are String, StringBuilder, and StringBuffer different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are String, StringBuilder, and StringBuffer different?`.

Tiny example or mental model:

- When reading code, ask: what does `How are String, StringBuilder, and StringBuffer different?` change, allow, reject, or clarify?

### How does HashMap work?

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `How does HashMap work?` in one sentence.
- Recognize `How does HashMap work?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How does HashMap work?`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### What improvements did HashMap have in Java 8?

A Map stores key-value pairs and retrieves values by key.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `What improvements did HashMap have in Java 8?` in one sentence.
- Recognize `What improvements did HashMap have in Java 8?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What improvements did HashMap have in Java 8?`.

Tiny example or mental model:

- `Map<String, Integer> scores = new HashMap<>();` maps keys to values.

### How are ArrayList and LinkedList different?

A List is an ordered collection that can contain duplicates and supports positional access.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `How are ArrayList and LinkedList different?` in one sentence.
- Recognize `How are ArrayList and LinkedList different?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `How are ArrayList and LinkedList different?`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
