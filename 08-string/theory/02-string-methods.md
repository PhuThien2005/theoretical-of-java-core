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

### 4. Search and Location: `indexOf()` and `lastIndexOf()`
- Locates a character or substring. Returns `-1` if not found.
- `indexOf(String str)`: Finds the first occurrence.
- `indexOf(String str, int fromIndex)`: Starts search from `fromIndex`.
- `lastIndexOf(String str)`: Finds the last occurrence (searches backward).

### 5. Validation Checks: `contains()`, `startsWith()`, `endsWith()`
- `contains(CharSequence s)`: Returns `true` if the sequence exists.
- `startsWith(String prefix)` / `endsWith(String suffix)`: Matches start or end. Null inputs throw `NullPointerException`.

### 6. Whitespace Cleanup: `trim()` vs. `strip()`
- `trim()`: Removes leading/trailing characters with code points less than or equal to ASCII space (`U+0020`). It fails to clean up Unicode whitespaces (like `\u00A0` non-breaking space).
- `strip()` (Java 11+): Uses `Character.isWhitespace()` to identify and remove all Unicode-compliant whitespaces.
- `stripLeading()` / `stripTrailing()` (Java 11+): Cleans only one end of the string.

### 7. Conversions: `toLowerCase()` and `toUpperCase()`
- Converts characters using locale-specific rules. Be careful: `"title".toUpperCase()` in the Turkish locale produces `TİTLE` instead of `TITLE`.

### 8. Replacement: `replace()` vs. `replaceAll()`
- `replace(char oldChar, char newChar)`: Replaces all occurrences of a character.
- `replace(CharSequence target, CharSequence replacement)`: Replaces all matching substrings. **Does not use regular expressions.**
- `replaceAll(String regex, String replacement)`: Replaces matches of a **regular expression**.
- `replaceFirst(String regex, String replacement)`: Replaces only the first match of a regular expression.

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
