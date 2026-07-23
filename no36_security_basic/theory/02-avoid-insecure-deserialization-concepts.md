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

---

## Why Insecure Deserialization Enables Remote Code Execution

Java's default serialization mechanism, when deserializing a byte stream via `ObjectInputStream.readObject()`, automatically instantiates all classes referenced in the stream, configures their fields from the byte data, and calls lifecycle methods like `readObject()` if they exist. The critical danger is that this happens before the application has had any opportunity to validate the data — the class graph is materialized first and checked afterward.

An attacker who can inject a crafted serialized byte stream exploits this by constructing a payload containing a chain of "gadget classes" — classes already present in the JVM's classpath (in common libraries like Apache Commons Collections, Spring, or Groovy) that, when their lifecycle methods are called during deserialization, execute attacker-controlled commands. This class of attack is known as a "deserialization gadget chain."

In a real attack, the attacker chains multiple `readObject()` and `hashCode()` calls across several objects such that the final call in the chain invokes `Runtime.exec("malicious_command")` — achieving Remote Code Execution (RCE) with no exploit of the network layer required.

`ObjectInputFilter` (introduced in Java 9) provides a class-whitelist mechanism: you define which classes are allowed to be deserialized, and the JVM rejects any class not on the whitelist during stream parsing — before instantiation occurs. This breaks gadget chains at the class-level boundary.

### Mental Model: Deserialization Gadget Chain
```
Attacker crafts malicious byte stream:
[SerializedPayload] = [GadgetClass1 → GadgetClass2 → GadgetClass3 → Runtime.exec("cmd")]
                                                                              |
ObjectInputStream.readObject() instantiates all classes                       |
    → GadgetClass1.readObject() calls hashCode() on GadgetClass2             |
    → GadgetClass2.hashCode() invokes compare() on GadgetClass3              |
    → GadgetClass3.compare() calls Runtime.exec("rm -rf /")  ←--------------+
         ↑ Arbitrary OS command executed!

With ObjectInputFilter:
[SerializedPayload] → Filter checks: is GadgetClass1 in whitelist? NO → REJECT
→ Exception thrown before any class is instantiated → No code execution
```

### Code Example: ObjectInputFilter Whitelist to Prevent RCE
```java
import java.io.*;

public class SafeDeserializationDemo {
    public static Object safeDeserialize(byte[] data) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data))) {
            // Define a strict class whitelist — only allow known safe classes
            ObjectInputFilter safeFilter = ObjectInputFilter.Config.createFilter(
                "com.example.model.*;java.lang.String;java.util.ArrayList;!*"
                //  ^^ Allow these    ^^ Allow String      ^^ Reject everything else
            );
            in.setObjectInputFilter(safeFilter);

            return in.readObject();
            // If payload contains GadgetClass from Apache Commons → filter rejects it
            // → InvalidClassException thrown → No RCE
        }
    }
}
```

### Cause-Effect Chain
Untrusted byte stream passed to `ObjectInputStream.readObject()` &rarr; JVM instantiates all classes in stream without validation &rarr; Gadget class `readObject()` chain invoked &rarr; `Runtime.exec()` called with attacker command &rarr; RCE achieved. With filter: stream class checked against whitelist &rarr; Unknown class rejected before instantiation &rarr; Gadget chain never executes.

## Reference Links

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/ObjectInputFilter.html (ObjectInputFilter JavaDoc)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/ObjectInputStream.html (ObjectInputStream JavaDoc)
- https://docs.oracle.com/javase/tutorial/security/ (Java Security Tutorial)

