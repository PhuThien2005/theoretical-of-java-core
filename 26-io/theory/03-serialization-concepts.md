# IO in Java - Part 3

## Learning Goal

This file covers a focused slice of **IO in Java**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Serialization` | The process of converting an object's state into a byte stream so it can be saved to a file or sent over a network. |
| `Deserialization` | The process of reconstructing an object from a serialized byte stream. |
| `Serializable` | A marker interface (no methods) that must be implemented by a class to make its instances eligible for serialization. |
| `serialVersionUID` | A unique 64-bit identifier used during deserialization to verify that the sender and receiver of a serialized object have loaded classes compatible with it. |
| `transient` | A field modifier indicating that the variable should not be serialized; its value is restored as the default value (e.g. `null` or `0`) during deserialization. |
| `Scanner` | A text scanner utility class used to parse primitive types and strings using regular expressions from an input stream or string. |
| `System.in` | The standard input stream (instance of `InputStream`), usually mapped to keyboard input. |
| `System.out` | The standard output stream (instance of `PrintStream`), usually mapped to console output. |
| `System.err` | The standard error stream (instance of `PrintStream`), used to print error messages to the console immediately. |

## Detailed Notes

### Serialization & Deserialization
Java object serialization allows developers to save the state of an object graph to a byte stream and reconstruct it later. 
* To make a class serializable, it must implement `java.io.Serializable`.
* Static fields represent class-level state, not object-level state, and are **not** serialized.
* If an object references other objects, the entire object graph is serialized. All referenced classes must also implement `Serializable`, or a `NotSerializableException` will be thrown at runtime.

```java
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended explicit definition
    
    private String username;
    private transient String password; // Will not be serialized!
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "User{username='" + username + "', password='" + password + "'}";
    }
}
```

### Serializing and Deserializing Code Example
We write the object using `ObjectOutputStream` and read it back using `ObjectInputStream`.

```java
import java.io.*;

public class SerializationDemo {
    public static void main(String[] args) {
        User user = new User("alice", "secret123");
        File file = new File("user.ser");
        
        // 1. Serialize
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(user);
            System.out.println("Object serialized: " + user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 2. Deserialize
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            User deserializedUser = (User) ois.readObject();
            System.out.println("Object deserialized: " + deserializedUser);
            // Prints: User{username='alice', password='null'} (password was transient!)
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
```

### Scanner & System I/O
* `System.in`, `System.out`, and `System.err` are opened by the JVM when the application starts.
* `Scanner` can wrap `System.in` to read user console input.
* **Important**: Closing a `Scanner` wrapped around `System.in` will close the underlying `System.in` stream itself. Once closed, you cannot read from `System.in` again for the remainder of the JVM execution.

```java
import java.util.Scanner;

public class ConsoleInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        if (scanner.hasNextLine()) {
            String name = scanner.nextLine();
            System.out.println("Hello, " + name);
        }
        // Avoid closing scanner if you need System.in elsewhere in the app!
    }
}
```

---

## Case Study: Customizing Serialization

### Problem
We want to encrypt a sensitive field (like `password`) when serializing, and decrypt it upon deserialization, so that it is not stored in plaintext inside the `.ser` file.

### Implementation
We can define private `writeObject` and `readObject` methods inside the `Serializable` class. Java's serialization mechanism looks for these methods via reflection and calls them instead of the default mechanism.

```java
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class SecureUser implements Serializable {
    private static final long serialVersionUID = 2L;
    
    private String username;
    private String password; // Will be serialized, but encrypted!

    public SecureUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        // Run default serialization for non-custom fields
        oos.defaultWriteObject();
        // Encrypt the password using simple Base64 for demo (use real cipher in production)
        String encryptedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        oos.writeObject(encryptedPassword);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        // Run default deserialization
        ois.defaultReadObject();
        // Read the encrypted password and decrypt it
        String encryptedPassword = (String) ois.readObject();
        this.password = new String(Base64.getDecoder().decode(encryptedPassword));
    }
}
```

---

## Common Mistakes

### 1. Missing Explicit `serialVersionUID`
If you do not specify `serialVersionUID` explicitly, the Java compiler automatically computes one at compile time based on class details (fields, methods). If you modify the class (e.g. add a minor method), the computed value will change. When deserializing older data, Java throws an `InvalidClassException`.
* **Fix**: Always define `private static final long serialVersionUID = 1L;` explicitly.

### 2. Parent Class Non-Serializable Constructor Pitfall
If a subclass implements `Serializable` but its superclass does **not**, the superclass state is not serialized. During deserialization, Java must initialize the superclass state by calling its **no-argument constructor**. If the superclass does not define a no-argument constructor, deserialization will fail at runtime with an `InvalidClassException`.

### 3. Closing Scanner wrapped around `System.in`
Closing a scanner closed `System.in`.
```java
Scanner s1 = new Scanner(System.in);
s1.close(); // Closes System.in!

Scanner s2 = new Scanner(System.in);
// s2.nextLine(); // Throws NoSuchElementException because System.in is closed!
```
