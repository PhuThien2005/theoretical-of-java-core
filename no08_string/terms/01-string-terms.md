# String Terms

This file details key terms related to strings in Java.

## String Pool

A special memory region within the Heap where the JVM stores unique string literals. It helps save memory by sharing identical string objects rather than creating duplicates.

## Immutability

The state of an object where its value or internal data cannot be modified after it is created. `String` is immutable, meaning any method that appears to modify it actually returns a new `String` object.

## String Literal

A sequence of characters enclosed in double quotes (e.g., `"hello"`) written directly in the source code. String literals are automatically stored in the String Pool by the compiler and JVM.

## Interning

The process of placing a string into the String Pool. If a string is created at runtime (e.g., via user input or `new String()`), calling its `.intern()` method returns a reference to that string from the pool, adding it first if it isn't already there.

## Compact Strings (Java 9+)

An internal optimization in the JVM. Before Java 9, strings were stored as `char[]` arrays (2 bytes per character). From Java 9 onwards, strings are stored as `byte[]` arrays along with a coder flag (1 byte for Latin-1 characters, 2 bytes for UTF-16 characters), reducing memory usage by up to 50% for typical Western languages.

## Text Block

A multi-line string literal introduced in Java 15, declared with triple quotes (`"""`). It preserves formatting and eliminates the need for escaping most common characters (like double quotes and newlines).

## StringBuilder

A mutable, non-thread-safe character sequence. It is designed for efficient, single-threaded string manipulations (like appending and inserting in loops).

## StringBuffer

A mutable, thread-safe character sequence. All of its major methods are synchronized, which ensures correctness when accessed by multiple threads but introduces performance overhead.
