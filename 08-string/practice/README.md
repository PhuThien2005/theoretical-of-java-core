# Practice Exercises: Strings

This folder contains hands-on practice exercises to reinforce your understanding of Java strings, character manipulation, array-backed buffers, and text analysis.

## Exercises

### 1. Custom String Builder (`custom-string-builder`)
Java's `String` class is immutable, meaning operations like concatenation create new objects. Behind the scenes, Java uses helper classes like `StringBuilder` to accumulate characters efficiently. In this exercise, you will build your own lightweight version of `StringBuilder` backed by a resizing character array.
- **Goal**: Implement dynamic buffer resizing, string and character appends, and state tracking.

#### Directory Structure
- [CustomStringBuilder.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/08-string/practice/custom-string-builder/src/CustomStringBuilder.java)
- [CustomStringBuilderTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/08-string/practice/custom-string-builder/test/CustomStringBuilderTest.java)
- [CustomStringBuilder.java (Solution)](file:///home/fhu_thjen/projects/learning-java/08-string/practice/custom-string-builder/solution/CustomStringBuilder.java)

---

### 2. Palindrome & Anagram Analyzer (`palindrome-anagram-analyzer`)
Analyzing text is a common task in software engineering. You will implement utility methods to analyze strings for two classic structures: palindromes and anagrams.
- **Goal**: Implement character-by-character string comparisons, two-pointer parsing, and frequency counting while ignoring whitespace, punctuation, and casing.

#### Directory Structure
- [PalindromeAnagramAnalyzer.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/08-string/practice/palindrome-anagram-analyzer/src/PalindromeAnagramAnalyzer.java)
- [PalindromeAnagramAnalyzerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/08-string/practice/palindrome-anagram-analyzer/test/PalindromeAnagramAnalyzerTest.java)
- [PalindromeAnagramAnalyzer.java (Solution)](file:///home/fhu_thjen/projects/learning-java/08-string/practice/palindrome-anagram-analyzer/solution/PalindromeAnagramAnalyzer.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 08-string
```
