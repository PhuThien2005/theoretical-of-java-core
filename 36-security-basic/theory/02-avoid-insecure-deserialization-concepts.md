# Basic Security - Part 2

## Learning Goal

This file covers a focused slice of **Basic Security**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Avoid insecure deserialization` | Serialization converts an object graph into bytes so it can be stored or transferred. |

## Detailed Notes

### Avoid insecure deserialization

Insecure deserialization occurs when untrusted serialized data is parsed, potentially leading to Remote Code Execution (RCE), denial of service, or privilege escalation.

It matters because Java deserialization dynamically instantiates classes and configures their state without executing standard constructors. An attacker can construct a payload containing nested "gadget classes" that execute malicious code upon deserialization (e.g. inside `readObject()`).

Practical check:

- Define `Avoid insecure deserialization` in one sentence.
- Recognize `Avoid insecure deserialization` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Avoid insecure deserialization`.

Tiny example or mental model:

- When reading code, ask: what does `Avoid insecure deserialization` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Code Examples

### Using ObjectInputFilter (Java 9+) to prevent insecure deserialization
```java
try (FileInputStream fileIn = new FileInputStream("object.ser");
     ObjectInputStream in = new ObjectInputStream(fileIn)) {
     
    // Configure filter to only allow specific classes (whitelist approach)
    ObjectInputFilter filter = ObjectInputFilter.Config.createFilter("com.example.model.*;java.base/*;!*");
    in.setObjectInputFilter(filter);
    
    MyModel model = (MyModel) in.readObject();
    System.out.println("Object loaded safely: " + model);
}
```

## Common Mistakes

- **Deserializing Untrusted Byte Streams**: Deserializing user-supplied data from requests or database columns without strict validation. This can allow attackers to perform remote code execution if vulnerable libraries (gadgets) exist in the classpath.
- **Not using transient for sensitive fields**: Forgetting to mark fields containing passwords, tokens, or keys as `transient`. This causes them to be serialized and written to the byte stream, risking exposure.
- **Failing to use ObjectInputFilter**: Allowing arbitrary class graphs to be instantiated during deserialization. Always use class filters to restrict deserialization to a known whitelist of safe classes.
