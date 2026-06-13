# ClassLoader - Part 1

## Learning Goal

This file covers a focused slice of **ClassLoader**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Class loading process` |Class loading process is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Bootstrap ClassLoader` | A ClassLoader loads class definitions into the JVM. |
| `Platform/Extension ClassLoader` | A ClassLoader loads class definitions into the JVM. |
| `Application ClassLoader` | A ClassLoader loads class definitions into the JVM. |
| `Parent delegation model` |Parent delegation model is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Dynamic class loading` |Dynamic class loading is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Class.forName` |Class.forName is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Classpath` | Classpath tells the JVM and compiler where to find classes and JARs. |
| `Basic JAR loading` |Basic JAR loading is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Class loading process

Class loading process is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Class loading process` in one sentence.
- Recognize `Class loading process` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Class loading process`.

Tiny example or mental model:

- When reading code, ask: what does `Class loading process` change, allow, reject, or clarify?

### Bootstrap ClassLoader

A ClassLoader loads class definitions into the JVM.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Bootstrap ClassLoader` in one sentence.
- Recognize `Bootstrap ClassLoader` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Bootstrap ClassLoader`.

Tiny example or mental model:

- When reading code, ask: what does `Bootstrap ClassLoader` change, allow, reject, or clarify?

### Platform/Extension ClassLoader

A ClassLoader loads class definitions into the JVM.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Platform/Extension ClassLoader` in one sentence.
- Recognize `Platform/Extension ClassLoader` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Platform/Extension ClassLoader`.

Tiny example or mental model:

- When reading code, ask: what does `Platform/Extension ClassLoader` change, allow, reject, or clarify?

### Application ClassLoader

A ClassLoader loads class definitions into the JVM.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Application ClassLoader` in one sentence.
- Recognize `Application ClassLoader` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Application ClassLoader`.

Tiny example or mental model:

- When reading code, ask: what does `Application ClassLoader` change, allow, reject, or clarify?

### Parent delegation model

Parent delegation model is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Parent delegation model` in one sentence.
- Recognize `Parent delegation model` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Parent delegation model`.

Tiny example or mental model:

- When reading code, ask: what does `Parent delegation model` change, allow, reject, or clarify?

### Dynamic class loading

Dynamic class loading is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Dynamic class loading` in one sentence.
- Recognize `Dynamic class loading` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Dynamic class loading`.

Tiny example or mental model:

- When reading code, ask: what does `Dynamic class loading` change, allow, reject, or clarify?

### Class.forName

Class.forName is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Class.forName` in one sentence.
- Recognize `Class.forName` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Class.forName`.

Tiny example or mental model:

- When reading code, ask: what does `Class.forName` change, allow, reject, or clarify?

### Classpath

Classpath tells the JVM and compiler where to find classes and JARs.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Classpath` in one sentence.
- Recognize `Classpath` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Classpath`.

Tiny example or mental model:

- When reading code, ask: what does `Classpath` change, allow, reject, or clarify?

### Basic JAR loading

Basic JAR loading is a specific concept in ClassLoader; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Basic JAR loading` in one sentence.
- Recognize `Basic JAR loading` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic JAR loading`.

Tiny example or mental model:

- When reading code, ask: what does `Basic JAR loading` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
## Code Examples

### Dynamic class loading
```java
// Load a driver class at runtime
Class<?> driverClass = Class.forName("com.example.jdbc.Driver");
Object driverInstance = driverClass.getDeclaredConstructor().newInstance();
```

### Class.forName usage
```java
// Load and initialize a class, triggering static initializers
Class.forName("com.example.Config", true, Thread.currentThread().getContextClassLoader());
```

## Common Mistakes

- **Forgetting to close resources**: When using custom class loaders, always close them to avoid memory leaks.
- **Misunderstanding parent delegation**: Overriding `findClass` without delegating to the parent can cause `ClassNotFoundException` for core classes.
