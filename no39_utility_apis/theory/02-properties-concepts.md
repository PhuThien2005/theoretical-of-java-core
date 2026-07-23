# Some Common Utility APIs - Part 2

## Learning Goal

This file covers localized resources, properties configuration, custom string formatting, and input parsing utility APIs. Study each concept as a practical Java rule.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Properties` | Text-based key-value configuration class (`java.util.Properties`). |
| `ResourceBundle` | Internationalization class (`java.util.ResourceBundle`) for loading localized translation files. |
| `Locale` | Culture/Geography representation class (`java.util.Locale`). |
| `Currency` | ISO 4217 Currency representation (`java.util.Currency`). |
| `Formatter` | Formatting formatting utility (`java.util.Formatter`) for print layout conversions. |
| `Scanner` | Tokenized text/stream parser (`java.util.Scanner`). |

---

## Detailed Notes

### Properties

`java.util.Properties` is a subclass of `Hashtable` used to store key-value configurations where both keys and values are Strings. Properties can easily write to or read from `.properties` text streams.

- **Runnable Example**:
  ```java
  Properties props = new Properties();
  
  // Load properties from a file
  try (InputStream input = new FileInputStream("config.properties")) {
      props.load(input);
  } catch (IOException ex) {
      System.out.println("Config file not found.");
  }

  // Get properties with a fallback default value
  String dbUser = props.getProperty("db.username");
  String dbPort = props.getProperty("db.port", "3306"); // defaults to 3306 if key not present
  ```

- **Common Mistake / Failure Mode**:
  - **Using Map put/get**: Properties implements `Map<Object, Object>` due to inheriting from `Hashtable`. This allows putting non-String keys or values via the generic `.put(key, value)` method. Doing so breaks the Properties configuration design and triggers a `ClassCastException` if you attempt to write the properties out using `store()` or `list()`. Always use `setProperty(String, String)` and `getProperty(String)`.

### Why Direct Map Manipulation of Properties is Dangerous

`java.util.Properties` was introduced in JDK 1.0 as a subclass of `java.util.Hashtable`. In modern Java design, this inheritance model is widely considered a violation of the Liskov Substitution Principle (LSP). Because it inherits from `Hashtable`, `Properties` exposes standard `Map` methods like `put(Object, Object)` and `putAll(Map)`.

#### The Type Safety Violation
The contract of `Properties` specifies that both keys and values must be of type `java.lang.String`. However, because the inherited `put()` method takes `Object` parameters, Java cannot prevent compile-time insertions of non-String keys or values:
```java
Properties props = new Properties();
props.put("port", 8080); // Compiles perfectly! (Autoboxed to Integer)
```

#### Under-the-Hood Failure Mechanisms
When you attempt to write these properties to disk using `store(OutputStream, String)` or print them with `list(PrintStream)`, these methods iterate over the keys and values, casting them to `String`. If a non-String object is encountered, the JVM throws a `ClassCastException` at runtime, causing a silent configuration bug to crash the application during serialization.

#### LSP Violation Analogy
Think of a standard mailbox (`Map<Object, Object>`) which can accept letters, magazines, packages, and garbage. `Properties` is like a mailbox specifically labeled for paper letters only. Because the mailbox slot is physical (`put(Object, Object)`), someone can still drop a brick (a non-String object) inside it. When the mail carrier (`store()`) tries to open the mailbox and expects only letters to process, they get injured by the brick (`ClassCastException`).

```mermaid
flowchart TD
    subgraph PropertiesInheritance ["LSP Violation: Properties inherits Hashtable"]
        Hashtable["Hashtable&lt;Object,Object&gt;"] -->|Inherits put()| Properties["Properties (Expects String keys/values)"]
    end
    subgraph Execution ["Runtime Serialization Flow"]
        Properties -->|put('port', 8080)| MapState["Internal Map contains String key and Integer value"]
        MapState -->|store() called| Loop["Iterate and cast keys/values to String"]
        Loop -->|Cast 8080 (Integer) to String| Crash["ClassCastException (Crash!)"]
    end
```

#### Cause-Effect Chain of Map Abuse
```text
Properties inherits Hashtable → put(Object, Object) is exposed → Non-String object is inserted into Properties → Compile completes successfully → store() is called during application shutdown → JVM attempts to cast non-String to String → ClassCastException thrown → Configuration fails to save
```

#### Runnable Example
```java
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLspDemo {
    public static void main(String[] args) {
        Properties props = new Properties();

        // CORRECT: setProperty guarantees type safety
        props.setProperty("db.user", "admin");

        // DANGEROUS: put() bypasses the Properties String contract
        props.put("db.port", 3306); // Bypasses compiler warning, autoboxed to Integer

        // Reading via getProperty returns null if cast is required
        String portValue = props.getProperty("db.port");
        System.out.println("getProperty('db.port'): " + portValue); // Prints null because it is not a String!

        // Attempting to serialize properties
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // This throws ClassCastException because db.port is an Integer, not a String
            props.store(out, "Application Configuration");
        } catch (ClassCastException e) {
            System.out.println("Caught expected ClassCastException during store(): " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

### ResourceBundle

`java.util.ResourceBundle` holds locale-specific resource files (typically translations) allowing you to internationalize (I18N) your application easily.

- **Runnable Example**:
  ```java
  // Loads MessagesBundle_fr_FR.properties
  Locale frenchLocale = new Locale("fr", "FR");
  ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", frenchLocale);
  
  String greeting = bundle.getString("welcome_message");
  ```

- **Common Mistake / Failure Mode**:
  - **Missing Base Bundle**: When looking up translation keys, if a specific locale properties file is not found, Java searches up the locale inheritance chain (e.g. `fr_FR` -> `fr` -> Default System Locale -> Base Bundle). If the key is not in any properties file, or if the base resource bundle file (`MessagesBundle.properties`) is entirely missing from the classpath, the JVM throws a runtime `MissingResourceException`. Always bundle a default base properties file.

---

### Locale

A `java.util.Locale` object represents a specific geographical, political, or cultural region. It is used to format numbers, dates, currency, and select translations.

- **Runnable Example**:
  ```java
  // Predefined constants
  Locale us = Locale.US;

  // Builder pattern (safest way to construct custom locales)
  Locale custom = new Locale.Builder().setLanguage("en").setRegion("GB").build();

  // Parsing a language tag (IETF BCP 47)
  Locale parsed = Locale.forLanguageTag("vi-VN");
  ```

- **Common Mistake / Failure Mode**:
  - **Constructor Misuse**: Instantiating a locale as `new Locale("en_US")` by passing the language tag combined. The legacy constructor expects language (`"en"`) and region (`"US"`) as separate arguments. Passing `"en_US"` results in an invalid locale with language `"en_us"` and no region, failing to load matching resource bundles. Use `Locale.forLanguageTag("en-US")` instead.

---

### Currency

The `java.util.Currency` class represents an ISO 4217 currency.

- **Runnable Example**:
  ```java
  Currency usd = Currency.getInstance("USD");
  Locale locale = Locale.FRANCE;
  
  // Outputs "USD" or currency symbol "$" based on formatter locale
  String symbol = usd.getSymbol(locale); 
  ```

- **Common Mistake / Failure Mode**:
  - Calling `Currency.getInstance(locale)` for a locale that represents a country with no official currency or an invalid region tag, which throws an `IllegalArgumentException`.

---

### Formatter

`java.util.Formatter` produces localized text strings formatted with C-style print layouts.

- **Runnable Example**:
  ```java
  StringBuilder sb = new StringBuilder();
  try (Formatter formatter = new Formatter(sb, Locale.US)) {
      formatter.format("Item: %s | Price: $%,.2f", "Laptop", 1249.99);
  }
  // sb now contains: "Item: Laptop | Price: $1,249.99"
  ```

- **Common Mistake / Failure Mode**:
  - **Conversion Mismatch**: Matching the wrong specifier with an argument (e.g., `%d` for a double or `%f` for an integer) throws a runtime `IllegalFormatConversionException`.
  - **Leaking Streams**: When constructing `Formatter` around files or output streams, failing to close the formatter leaks file descriptors. Always wrap in try-with-resources.

---

### Scanner

`java.util.Scanner` is a simple text parser that breaks input streams into tokens using a delimiter (default is whitespace) and parses primitives or strings.

- **Runnable Example**:
  ```java
  String input = "10 20 30";
  try (Scanner scanner = new Scanner(input)) {
      while (scanner.hasNextInt()) {
          int value = scanner.nextInt();
      }
  }
  ```

- **Common Mistake / Failure Mode**:
  - **The nextLine() Pitfall**: Reading a primitive value (like `nextInt()`) and then calling `nextLine()` to read text. Primitive reading methods consume only the token, leaving the newline character `\n` in the buffer. The subsequent `nextLine()` consumes the empty newline immediately, skipping the actual line of input:
    ```java
    Scanner scanner = new Scanner(System.in);
    int age = scanner.nextInt();    // Consumes int, leaves '\n' in buffer
    scanner.nextLine();             // CRITICAL: Consume the leftover newline
    String name = scanner.nextLine(); // Now correctly waits for text input
    ```
  - **System.in Shutdown**: Closing a Scanner wrapped around `System.in` (e.g. `scanner.close()`) closes `System.in` itself. Since you cannot reopen standard input in a running JVM, any subsequent attempt to read from `System.in` will crash. Avoid closing Scanners that wrap `System.in`.

### Why the Scanner nextLine() Pitfall Occurs

The `java.util.Scanner` class is a token-based parser. Understanding the scanner's internal cursor and buffer mechanics is essential to avoid common input-skipping bugs.

#### The Mechanics of Token vs. Line Processing
- **Token-based methods** (`nextInt()`, `nextDouble()`, `next()`):
  These methods skip any leading delimiters (whitespace, tabs, or newlines by default), scan the buffer to read characters matching their pattern, and stop reading immediately after the matching pattern. They **do not consume the trailing delimiter** (such as the newline character `\n` generated when the user presses Enter).
- **Line-based methods** (`nextLine()`):
  This method reads the buffer from the current cursor position until it encounters a line feed (`\n` or `\r\n`), consumes the entire line including the line feed, but returns only the text *before* the line feed.

#### Cursor and Buffer Step-by-Step
When a user inputs `42` and presses Enter, the input stream buffer contains:
`['4', '2', '\n']`

1. **`nextInt()` is called**:
   - The scanner reads `'4'` and `'2'`.
   - It parses them into the integer `42` and returns it.
   - The cursor stops *before* `'\n'`. The buffer remains: `['\n']`.
2. **`nextLine()` is called**:
   - The scanner starts reading from the current cursor position.
   - It immediately encounters `'\n'`.
   - It consumes the `'\n'` and clears it from the buffer.
   - Since there was no text before `'\n'`, it returns an empty string `""` immediately without waiting for new console input.

#### Buffer State Analogy
Imagine a conveyor belt carrying packages (tokens) separated by plastic spacers (delimiters like `\n`). 
`nextInt()` is like a robotic arm that only grabs the package (`42`), leaving the spacer (`\n`) on the belt. 
`nextLine()` is like a sweep arm that clears everything off the belt up to the next spacer. If the spacer (`\n`) is already the next item on the belt, the sweep arm immediately finishes and reports it swept up nothing, leaving you with an empty result.

```mermaid
flowchart TD
    subgraph Step1 ["Buffer State: User enters '42\\n'"]
        B1["[ '4', '2', '\\n' ]"]
    end
    subgraph Step2 ["After nextInt(): Cursor stops before '\\n'"]
        B2["[ '\\n' ]"]
        cur2["Cursor position"] --> B2
    end
    subgraph Step3 ["After nextLine(): '\\n' is consumed, returns empty String"]
        B3["[ ]"]
        res["Returns empty string: ''"]
    end
    Step1 -->|nextInt() consumes '42'| Step2
    Step2 -->|nextLine() consumes '\\n'| Step3
```

#### Cause-Effect Chain of the nextLine() Pitfall
```text
User inputs '42\n' → nextInt() reads only '42' → '\n' is left at the head of the buffer → nextLine() is invoked → Scanner immediately detects and consumes '\n' → nextLine() returns empty string → Code continues without waiting for new input
```

#### Runnable Example
```java
import java.util.Scanner;

public class ScannerPitfallDemo {
    public static void main(String[] args) {
        String inputBuffer = "42\nJohn Doe\n";
        
        // Simulating the failure mode
        try (Scanner buggyScanner = new Scanner(inputBuffer)) {
            int age = buggyScanner.nextInt(); // Consumes "42", leaves "\n"
            String name = buggyScanner.nextLine(); // Consumes "\n" immediately, returning empty!
            
            System.out.println("Age: " + age);
            System.out.println("Buggy Name (should be John Doe): '" + name + "'");
        }

        // Simulating the correct approach
        try (Scanner correctScanner = new Scanner(inputBuffer)) {
            int age = correctScanner.nextInt(); // Consumes "42", leaves "\n"
            correctScanner.nextLine();          // CRITICAL: Consume and discard the leftover newline
            String name = correctScanner.nextLine(); // Now reads "John Doe"
            
            System.out.println("Age: " + age);
            System.out.println("Correct Name: '" + name + "'");
        }
    }
}
```

