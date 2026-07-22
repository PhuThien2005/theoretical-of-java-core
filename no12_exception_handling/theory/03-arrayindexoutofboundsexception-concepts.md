# Exception Handling - Part 3

## Learning Goal

This file covers a focused slice of **Exception Handling**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `ArrayIndexOutOfBoundsException` | Thrown when an array is accessed with an illegal index (negative or >= size). |
| `StringIndexOutOfBoundsException` | Thrown by String methods (charAt, substring) when index is out of bounds. |
| `ClassCastException` | Thrown when casting an object reference to a type it does not inherit or implement. |
| `NumberFormatException` | Subclass of IllegalArgumentException thrown when parsing an invalid numeric string. |
| `ArithmeticException` | Thrown for exceptional arithmetic conditions, like integer division by zero. |
| `IllegalArgumentException` | Thrown when a method receives an argument that is invalid or inappropriate. |
| `IllegalStateException` | Thrown when the environment or object state is inappropriate for the requested operation. |
| `IOException` | Base class for checked I/O failure exceptions (network, file system, etc.). |

## Detailed Notes

### ArrayIndexOutOfBoundsException

Thrown to indicate that an array has been accessed with an illegal index. The index is either negative or greater than or equal to the size of the array.

Practical check:

- Define `ArrayIndexOutOfBoundsException` in one sentence.
- Recognize `ArrayIndexOutOfBoundsException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ArrayIndexOutOfBoundsException`.

Tiny example or mental model:

- Accessing `arr[arr.length]` throws `ArrayIndexOutOfBoundsException`.

#### Runnable Code Example: Triggering and Handling
```java
public class ArrayOOBDemo {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        try {
            int val = numbers[3]; // Index 3 is out of bounds (valid indices: 0, 1, 2)
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
    }
}
```

### StringIndexOutOfBoundsException

Thrown by `String` methods to indicate that an index is either negative or greater than or equal to the length of the string.

Practical check:

- Define `StringIndexOutOfBoundsException` in one sentence.
- Recognize `StringIndexOutOfBoundsException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `StringIndexOutOfBoundsException`.

Tiny example or mental model:

- Calling `"hello".charAt(5)` throws `StringIndexOutOfBoundsException`.

#### Runnable Code Example: Triggering and Handling
```java
public class StringOOBDemo {
    public static void main(String[] args) {
        String greet = "Hello";
        try {
            char ch = greet.charAt(10); // Index 10 is out of bounds (length is 5)
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Caught StringIndexOutOfBoundsException: " + e.getMessage());
        }
    }
}
```

### ClassCastException

Thrown when code has attempted to cast an object to a subclass of which it is not an instance.

Practical check:

- Define `ClassCastException` in one sentence.
- Recognize `ClassCastException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ClassCastException`.

Tiny example or mental model:

- Casting an `Integer` object to a `String` reference.

#### Runnable Code Example: Triggering and Handling
```java
public class ClassCastDemo {
    public static void main(String[] args) {
        Object obj = Integer.valueOf(100);
        try {
            String str = (String) obj; // Throws ClassCastException (Integer cannot be cast to String)
        } catch (ClassCastException e) {
            System.out.println("Caught ClassCastException: " + e.getMessage());
        }
    }
}
```

### NumberFormatException

Thrown to indicate that the application has attempted to convert a string to one of the numeric types, but that the string does not have the appropriate format.

Practical check:

- Define `NumberFormatException` in one sentence.
- Recognize `NumberFormatException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `NumberFormatException`.

Tiny example or mental model:

- Calling `Integer.parseInt("abc")` throws `NumberFormatException`.

#### Runnable Code Example: Triggering and Handling
```java
public class NumberFormatDemo {
    public static void main(String[] args) {
        String ageInput = "25a";
        try {
            int age = Integer.parseInt(ageInput);
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        }
    }
}
```

### ArithmeticException

Thrown when an exceptional arithmetic condition has occurred. For example, an integer "divide by zero" throws this exception.

Practical check:

- Define `ArithmeticException` in one sentence.
- Recognize `ArithmeticException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ArithmeticException`.

Tiny example or mental model:

- Integer division: `10 / 0` throws `ArithmeticException`.

#### Runnable Code Example: Triggering and Handling
```java
public class ArithmeticDemo {
    public static void main(String[] args) {
        try {
            int result = 42 / 0; // Integer division by zero
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        }
    }
}
```

### IllegalArgumentException

Thrown to indicate that a method has been passed an illegal or inappropriate argument.

Practical check:

- Define `IllegalArgumentException` in one sentence.
- Recognize `IllegalArgumentException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `IllegalArgumentException`.

Tiny example or mental model:

- Passing a negative age to a `setAge(int age)` method that requires age >= 0.

#### Runnable Code Example: Triggering and Handling
```java
public class IllegalArgumentDemo {
    public static void setPercentage(int val) {
        if (val < 0 || val > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
    }

    public static void main(String[] args) {
        try {
            setPercentage(150);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught IllegalArgumentException: " + e.getMessage());
        }
    }
}
```

### IllegalStateException

Signals that a method has been invoked at an illegal or inappropriate time. In other words, the Java environment or Java object is not in an appropriate state for the requested operation.

Practical check:

- Define `IllegalStateException` in one sentence.
- Recognize `IllegalStateException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `IllegalStateException`.

Tiny example or mental model:

- Starting a Thread that has already been started.

#### Runnable Code Example: Triggering and Handling
```java
public class IllegalStateDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("Running"));
        thread.start();
        try {
            thread.start(); // Throws IllegalThreadStateException (subclass of IllegalStateException)
        } catch (IllegalStateException e) {
            System.out.println("Caught IllegalStateException: " + e.getMessage());
        }
    }
}
```

### IOException

Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.

Practical check:

- Define `IOException` in one sentence.
- Recognize `IOException` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `IOException`.

Tiny example or mental model:

- Reading from a socket stream after the remote host closed the connection.

#### Runnable Code Example: Triggering and Handling
```java
import java.io.FileInputStream;
import java.io.IOException;

public class IODemo {
    public static void main(String[] args) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("does_not_exist.txt");
            fis.read();
        } catch (IOException e) { // Checked exception handled
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }
}
```

## Common Mistakes

### 1. Floating-Point Division by Zero
Unlike integer division, dividing a floating-point number (`float` or `double`) by zero does **not** throw an `ArithmeticException`. Instead, it evaluates to special values: `Infinity`, `-Infinity`, or `NaN` (Not a Number).
```java
public class FloatingPointZeroDemo {
    public static void main(String[] args) {
        double a = 1.0 / 0.0; // Evaluates to Double.POSITIVE_INFINITY
        double b = 0.0 / 0.0; // Evaluates to Double.NaN

        System.out.println("a: " + a); // Prints Infinity
        System.out.println("b: " + b); // Prints NaN
        
        // No exception is thrown!
    }
}
```

### 2. Multi-step Casting and ClassCastException
Casting an object reference to a parent class first (like `Object`) does not bypass runtime type checks. JVM tracks the actual runtime type of the object, so casting to an incompatible subclass will still throw `ClassCastException`.
```java
Object number = Integer.valueOf(42);
// Compiles fine because number is an Object, but crashes at runtime!
String str = (String) number; 
```

### 3. Parse Nulls via NumberFormatException
Passing a `null` reference to `Integer.parseInt(str)` or `Double.parseDouble(str)` throws a `NullPointerException`, not a `NumberFormatException`. Make sure you handle nulls or check before parsing.
```java
String input = null;
try {
    Integer.parseInt(input);
} catch (NumberFormatException e) {
    // This catch block is bypassed! NullPointerException is thrown instead.
}
```

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
