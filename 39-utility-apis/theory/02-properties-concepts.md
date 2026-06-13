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
