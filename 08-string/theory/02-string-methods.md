# String Methods and Formatting

The `String` class provides a rich set of built-in methods. Since strings are immutable, none of these methods modify the existing string object; they always return a new string.

---

## Detailed Method Reference

Here is the comprehensive reference of core `String` methods, explaining their behaviors and edge cases.

### 1. `length()` and `isEmpty()` / `isBlank()`
- `length()`: Returns the number of Unicode code units (characters) in the string.
- `isEmpty()` (Java 6+): Returns `true` if `length() == 0`.
- `isBlank()` (Java 11+): Returns `true` if the string is empty or contains only whitespace characters (Unicode-aware).
  ```java
  "  ".isEmpty(); // false
  "  ".isBlank(); // true
  ```

### 2. Character Lookup: `charAt(int index)`
- Returns the `char` value at the specified index.
- Index bounds are `0` to `length() - 1`.
- Throws `StringIndexOutOfBoundsException` if index is negative or greater than or equal to `length()`.

### 3. Extracting: `substring(int beginIndex)` and `substring(int beginIndex, int endIndex)`
- `substring(beginIndex)`: Returns a substring starting from `beginIndex` to the end.
- `substring(beginIndex, endIndex)`: Returns a substring starting from `beginIndex` (inclusive) to `endIndex` (exclusive).
  $$\text{Length of Substring} = \text{endIndex} - \text{beginIndex}$$
- Throws `StringIndexOutOfBoundsException` if `beginIndex < 0`, `endIndex > length()`, or `beginIndex > endIndex`.

```java
String msg = "Hello World";
String sub1 = msg.substring(6);      // "World"
String sub2 = msg.substring(0, 5);   // "Hello"
System.out.println(sub1); // World
System.out.println(sub2); // Hello
```

### 4. Search and Location: `indexOf()` and `lastIndexOf()`
- Locates a character or substring. Returns `-1` if not found.
- `indexOf(String str)`: Finds the first occurrence.
- `indexOf(String str, int fromIndex)`: Starts search from `fromIndex`.
- `lastIndexOf(String str)`: Finds the last occurrence (searches backward).

### 5. Validation Checks: `contains()`, `startsWith()`, `endsWith()`
- `contains(CharSequence s)`: Returns `true` if the sequence exists.
- `startsWith(String prefix)` / `endsWith(String suffix)`: Matches start or end. Null inputs throw `NullPointerException`.

```java
String text = "Java Programming";
boolean hasJava = text.contains("Java"); // true
boolean hasKotlin = text.contains("Kotlin"); // false
System.out.println(hasJava);   // true
System.out.println(hasKotlin); // false
```

### 6. Whitespace Cleanup: `trim()` vs. `strip()`
- `trim()`: Removes leading/trailing characters with code points less than or equal to ASCII space (`U+0020`). It fails to clean up Unicode whitespaces (like `\u00A0` non-breaking space).
- `strip()` (Java 11+): Uses `Character.isWhitespace()` to identify and remove all Unicode-compliant whitespaces.
- `stripLeading()` / `stripTrailing()` (Java 11+): Cleans only one end of the string.

### Deep-Dive: Mechanical Differences Between trim() and strip()

The mechanical difference between `String.trim()` and `String.strip()` lies in how they define and identify whitespace characters. The legacy `trim()` method, designed in Java 1.0, determines whitespace strictly by checking if a character's Unicode code point value is less than or equal to the ASCII space character (`U+0020`). Consequently, it fails to remove any modern Unicode-defined whitespace characters that reside at higher code points, such as the non-breaking space (`U+00A0`) or the em space (`U+2003`). In contrast, the `strip()` method introduced in Java 11 queries the `Character.isWhitespace(int)` method, which checks the character against the official Unicode standard database. This makes `strip()` fully Unicode-aware, ensuring that modern internationalized applications correctly clean up non-ASCII whitespace characters that `trim()` would silently ignore.

#### Whitespace Comparison Matrix

| Whitespace Character | Code Point | trim() Action | strip() Action | Technical Reason |
| :--- | :--- | :--- | :--- | :--- |
| ASCII Space | `U+0020` | Removes | Removes | Code point $\le$ `U+0020` |
| Tab (`\t`) | `U+0009` | Removes | Removes | Code point $\le$ `U+0020` |
| Non-Breaking Space | `U+00A0` | **Ignores** | **Removes** | Code point > `U+0020`, but recognized as whitespace by Unicode |
| Em Space | `U+2003` | **Ignores** | **Removes** | Code point > `U+0020`, but recognized as whitespace by Unicode |

#### Unicode Whitespace Demonstration Code Example

```java
// String containing Unicode Em Space (\u2003)
String input = "\u2003Java Core\u2003";

System.out.println("Original length: " + input.length()); // Output: 11
System.out.println("trim() length: " + input.trim().length()); // Output: 11 (ignored!)
System.out.println("strip() length: " + input.strip().length()); // Output: 9 (removed!)
```

#### Cause-Effect Chain of Unicode Whitespace Processing
Unicode character `\u2003` (Em Space, value `0x2003`) $\rightarrow$ Evaluated by `trim()` $\rightarrow$ Checks if `0x2003 <= 0x20` (evaluates to `false`) $\rightarrow$ `trim()` ignores character $\rightarrow$ Evaluated by `strip()` $\rightarrow$ Calls `Character.isWhitespace(0x2003)` $\rightarrow$ Returns `true` based on Unicode properties $\rightarrow$ `strip()` removes character.

### 7. Conversions: `toLowerCase()` and `toUpperCase()`
- Converts characters using locale-specific rules. Be careful: `"title".toUpperCase()` in the Turkish locale produces `TİTLE` instead of `TITLE`.

### 8. Replacement: `replace()` vs. `replaceAll()`
- `replace(char oldChar, char newChar)`: Replaces all occurrences of a character.
- `replace(CharSequence target, CharSequence replacement)`: Replaces all matching substrings. **Does not use regular expressions.**
- `replaceAll(String regex, String replacement)`: Replaces matches of a **regular expression**.
- `replaceFirst(String regex, String replacement)`: Replaces only the first match of a regular expression.

```java
String src = "apple.orange.banana";

// replace() treats "." as a literal string
String r1 = src.replace(".", "-"); 
System.out.println(r1); // Output: apple-orange-banana

// replaceAll() treats "." as a regex wildcard (matches any character)
String r2 = src.replaceAll(".", "-"); 
System.out.println(r2); // Output: -------------------
```

### 9. Splitting: `split(String regex)` and `split(String regex, int limit)`
Splits strings around regular expression matches.
- `split(regex)`: Discards trailing empty strings.
- `split(regex, limit)`:
  - If `limit > 0`: The pattern is applied at most `limit - 1` times, resulting in a maximum array size of `limit`.
  - If `limit < 0`: The pattern is applied as many times as possible, and trailing empty strings are **not** discarded.
  - If `limit == 0`: Identical to `split(regex)` (trailing empty strings are discarded).

```java
String s = "a:b:c::";
s.split(":").length;    // 3 -> {"a", "b", "c"} (trailing empty strings discarded)
s.split(":", -1).length; // 5 -> {"a", "b", "c", "", ""} (empty strings preserved)
s.split(":", 2).length;  // 2 -> {"a", "b:c::"} (capped at 2 splits)
```

### 10. Converting to Character Array: `toCharArray()`
- Returns a newly allocated character array whose length is the length of this string, containing the character sequence represented by the string.

```java
String word = "Java";
char[] chars = word.toCharArray();
for (char c : chars) {
    System.out.print(c + " "); // Output: J a v a 
}
System.out.println();
```

---

## String Concatenation and Compiler Optimizations

### Compile-time Evaluation
If you concatenate literals, the compiler evaluates them at compile-time and places the final string directly in the bytecode:
```java
String s = "a" + "b" + "c"; // Compiled as: String s = "abc";
```

### Dynamic Evaluation (Variables)
If the expression contains variables, Java evaluates them at runtime:
- **Java 8 and earlier:** Translated to `new StringBuilder().append(a).append(b).toString()`.
- **Java 9 and later:** Uses `invokedynamic` calling `StringConcatFactory.makeConcatWithTemplate()`. This decouples the concatenation strategy from bytecode, allowing the JVM to optimize the operation dynamically.

---

## Detailed Formatting Options

`String.format()` and `System.out.printf()` format strings based on a pattern.

### Syntax:
$$\%[\text{argument\_index}\$][\text{flags}][\text{width}][.\text{precision}]\text{conversion}$$

### Common Conversions:
- `%s`: String representation
- `%d`: Integer
- `%f`: Floating-point
- `%tF`: Date in YYYY-MM-DD
- `%n`: Platform-specific newline

### Formatting Flags and Precision:
- `%-15s`: Left-align the string inside a 15-character wide field.
- `%05d`: Pad a number with leading zeros to make it 5 digits.
- `%.2f`: Limit a floating-point number to 2 decimal places.

```java
String.format("|%-10s|", "Java"); // "|Java      |"
String.format("%.3f", 3.14159);    // "3.142" (rounded)
String.format("%04d", 42);          // "0042"

// Detailed Formatting Example:
String name = "Alice";
int age = 30;
double gpa = 3.8567;

String formatted = String.format("Name: %s, Age: %d, GPA: %.2f", name, age, gpa);
System.out.println(formatted); // Output: Name: Alice, Age: 30, GPA: 3.86
```

---

## Text Blocks (Java 15+)

Text Blocks are multi-line string literals enclosed in triple double-quotes `"""`.

### Whitespace Stripping Algorithm
1. **Incidental Whitespace:** The compiler calculates the common leading whitespace across all lines and strips it.
2. **Essential Whitespace:** Indentation beyond the common threshold is preserved.
3. The closing `"""` determines the minimum indent. Moving it to the left preserves leading spaces.

### Special Escape Sequences in Text Blocks
- `\` (Line Continuation): Prevents inserting a newline character at the end of the line.
- `\s` (Trailing Space): Preserves trailing whitespaces on that line (which are otherwise stripped by default).

```java
String html = """
              <html>
                  <body>\
                      <p>Hello World</p>\s\s
                  </body>
              </html>
              """;
```
- The `<p>` tag line will merge with the next line due to `\`.
- The `\s\s` at the end of the `<p>` tag line preserves two trailing spaces.

---

## Common Mistakes

### 1. `StringIndexOutOfBoundsException` with `charAt` and `substring`
Java strings are zero-indexed. The bounds for `charAt(index)` are `0` to `length() - 1`. The `endIndex` of `substring(beginIndex, endIndex)` is exclusive, but it must not exceed `length()`.
```java
String s = "hello";
char c = s.charAt(5); // StringIndexOutOfBoundsException (length is 5, max index is 4)
String sub = s.substring(2, 6); // StringIndexOutOfBoundsException (endIndex 6 exceeds length)
```

### 2. Splitting on Regex Metacharacters
Using regex special characters like `.`, `|`, `+`, `*`, `?` directly in `split()` or `replaceAll()` without escaping them.
```java
String data = "a.b.c";
String[] parts = data.split("."); // Incorrect! "." matches any character.
System.out.println(parts.length); // Prints 0 because it matched and split everything away.

// Correct: Escape the dot using a double backslash
String[] correctParts = data.split("\\.");
System.out.println(correctParts.length); // Prints 3
```

### 3. Mixing up `replace` and `replaceAll`
Assuming `replace(CharSequence, CharSequence)` only replaces the first occurrence or doesn't replace all. In fact, `replace()` replaces ALL occurrences of the target literal, whereas `replaceAll()` does the same but treats the target as a regular expression.
```java
String sentence = "I love Java. Java is fun.";
// Both replace all occurrences, but replace() is faster/safer for plain text:
System.out.println(sentence.replace("Java", "Kotlin")); 
System.out.println(sentence.replaceAll("Java", "Kotlin"));
```

---

## Reference Links

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#trim() (Oracle Java API: String.trim())
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#strip() (Oracle Java API: String.strip())
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Character.html#isWhitespace(int) (Oracle Java API: Character.isWhitespace())
