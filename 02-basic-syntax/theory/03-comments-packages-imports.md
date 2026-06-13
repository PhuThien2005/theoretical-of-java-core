# Comments, Packages, And Imports

Comments, packages, and imports do not usually change business logic directly, but they make code understandable and organized.

## Comments

Java supports single-line and multi-line comments.

Single-line:

```java
// This prints a greeting.
System.out.println("Hello");
```

Multi-line:

```java
/*
 This is a longer explanation.
 It can span multiple lines.
*/
```

Documentation comment:

```java
/**
 * Calculates the total price.
 */
public double calculateTotal() {
    return 0;
}
```

Documentation comments can be used by tools such as `javadoc`.

## Good Comments

Good comments explain why code exists or clarify non-obvious decisions.

Useful:

```java
// Use BigDecimal because money calculations must avoid floating-point rounding issues.
```

Not useful:

```java
// Add 1 to count
count = count + 1;
```

The second comment only repeats the code.

## Packages

A package groups related classes and prevents name conflicts.

Example:

```java
package com.example.learning;
```

Package names are usually lowercase and often use a reversed domain name style:

```text
com.company.project.module
```

## Imports

An import lets you use a class from another package without writing its full name every time.

Without import:

```java
java.util.Scanner scanner = new java.util.Scanner(System.in);
```

With import:

```java
import java.util.Scanner;

Scanner scanner = new Scanner(System.in);
```

## Wildcard Imports

Java allows wildcard imports:

```java
import java.util.*;
```

This imports classes from the `java.util` package, but not from subpackages. Beginners should prefer explicit imports because they are clearer.

## Import Does Not Copy Code

An import statement does not paste code into your file. It only tells the compiler where to find a type by its simple name.

## Common Mistakes

- Writing imports below the class declaration.
- Using package names with uppercase letters.
- Thinking `import java.util.*` imports subpackages.
- Adding comments that repeat obvious code.

### Common Mistake: Import Below Class Declaration

```java
// Compile error: import must appear before class declaration
public class Demo {
    import java.util.Scanner;   // ← wrong position
}
```

Correct order: `package` → `import` → `class`.

```java
package com.example;
import java.util.Scanner;

public class Demo {
    // ...
}
```

### Common Mistake: Uppercase Package Name

```java
package Com.Example.Learning;  // wrong — should be all lowercase
```

```java
package com.example.learning;  // correct
```

### Common Mistake: Expecting Wildcard to Cover Subpackages

```java
import java.util.*;  // imports ArrayList, HashMap, etc.
// Does NOT import java.util.concurrent.locks.Lock
// The line below still fails to compile:
Lock lock = new ReentrantLock();  // error: cannot find symbol
```

Fix: add `import java.util.concurrent.locks.Lock;` explicitly.

### Common Mistake: Comment That Repeats Code

```java
// Bad — just repeats what the code says
count = count + 1;  // increment count by 1

// Good — explains the business reason
count = count + 1;  // retry counter: max retries defined by MAX_RETRY in config
```

## Case Study: Correct File Structure

```java
// File: OrderService.java
package com.example.shop;          // 1. package — first non-comment line

import java.util.ArrayList;        // 2. imports — before class
import java.util.List;

/**
 * Manages customer orders.        // 3. javadoc comment on the class
 * Handles creation and retrieval.
 */
public class OrderService {        // 4. class — name matches file name

    /**
     * Returns all pending orders.
     * @return list of order IDs
     */
    public List<Integer> getPendingOrders() {
        // Use ArrayList — fast random access, acceptable for small order sets
        return new ArrayList<>();
    }
}
```
