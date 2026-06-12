# Array Basics

An array in Java is a container object that holds a fixed number of values of a single type. The length of an array is established when the array is created, and after creation, its length is fixed.

---

## Declaration and Initialization

An array must be declared, allocated, and optionally initialized before it can be used.

### Declaration Syntax
There are two syntaxes for declaring an array variable:
1. **Type-first (Preferred):** Placing the brackets after the type.
   ```java
   int[] numbers;
   ```
2. **Variable-first (C/C++ Style):** Placing the brackets after the variable name.
   ```java
   int scores[];
   ```
   *Note:* The type-first syntax is highly preferred because it clearly keeps the type information (`int[]` meaning "integer array") separate from the variable name.

### Allocation and Default Values
Arrays are objects, which means they are created in the **Heap** memory using the `new` keyword. You must specify the size (length) of the array upon allocation:

```java
numbers = new int[5]; // Allocates space for 5 integers
```

When an array is allocated, the JVM automatically initializes all of its elements to their default values:

| Type | Default Value |
| :--- | :--- |
| `byte`, `short`, `int`, `long` | `0` |
| `float`, `double` | `0.0` |
| `char` | `\u0000` (null character) |
| `boolean` | `false` |
| Reference Types (Objects, Strings) | `null` |

---

## Initialization Options

You can initialize arrays using three main patterns:

### 1. Allocation with Default Values
```java
int[] arr = new int[3]; // Elements are {0, 0, 0}
arr[0] = 10;
arr[1] = 20;
```

### 2. Array Initializer (Shortcut Syntax)
Used when the values are known at the time of declaration:
```java
int[] arr = {10, 20, 30}; // Size is automatically inferred as 3
```
> [!WARNING]
> This shortcut syntax is only valid in the variable declaration statement. You cannot use it for re-assignment:
> ```java
> int[] arr;
> // arr = {10, 20, 30}; // Compilation Error!
> arr = new int[]{10, 20, 30}; // Valid re-assignment
> ```

### 3. Anonymous Array Syntax
Used to declare, allocate, and initialize an array on the fly (often when passing an array to a method):
```java
printScores(new int[]{90, 85, 95});
```

---

## One-Dimensional Arrays and Traversing

Elements of a 1D array are accessed using index positions from `0` to `length - 1`. The size of the array is read-only and accessed via the `length` property:

```java
int size = arr.length; // Property (no parenthesis)
```

### Traversing Methods

#### 1. Standard `for` Loop
Allows modifying elements, traversing backwards, or stepping through indices.
```java
for (int i = 0; i < arr.length; i++) {
    arr[i] = arr[i] * 2; // Modification is possible
}
```

#### 2. Enhanced `for` (foreach) Loop
Read-only, sequential access from start to finish. You **cannot** use it to modify the elements of primitive arrays, nor can you access index numbers.
```java
for (int val : arr) {
    System.out.println(val); // Cannot modify elements or get current index
}
```

---

## Multidimensional Arrays (Arrays of Arrays)

Java does not have true multi-dimensional contiguous arrays. Instead, a multi-dimensional array is an **array of arrays**.

### Two-Dimensional Arrays
A 2D array can be visualized as a grid of rows and columns.

```java
int[][] matrix = new int[3][4]; // 3 rows, 4 columns
```

You can initialize a 2D array inline:
```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};
```

### Ragged (Jagged) Arrays
Because multidimensional arrays are arrays of arrays, each row can point to an array of a different length.

```java
int[][] ragged = new int[3][]; // Allocate 3 rows, columns are left unallocated (null)
ragged[0] = new int[2];        // Row 0 has 2 columns
ragged[1] = new int[4];        // Row 1 has 4 columns
ragged[2] = new int[1];        // Row 2 has 1 column
```

### Traversing a 2D Array
```java
for (int i = 0; i < matrix.length; i++) { // matrix.length returns number of rows
    for (int j = 0; j < matrix[i].length; j++) { // matrix[i].length returns columns in row i
        System.out.print(matrix[i][j] + " ");
    }
}
```

---

## Array of Objects

An array of objects stores **references** to objects, not the objects themselves.

```java
String[] names = new String[3]; // Allocates 3 references in heap, all initialized to null
// names[0].toLowerCase();      // Throws NullPointerException!

names[0] = new String("Alice");
names[1] = "Bob";
names[2] = "Charlie";
```

### Memory Layout Comparison
*   **Primitive Array (`int[]`):** The array object in the heap contains the raw values (`10`, `20`, etc.) directly inside the contiguous memory block.
*   **Object Array (`String[]`):** The array object in the heap contains memory addresses (pointers) to the actual objects stored elsewhere in the heap.

---

## Utility Operations: Printing and Cloning

### Printing Arrays
Calling `System.out.println(arr)` on an array prints its class name hashcode representation (e.g., `[I@1a2b3c4d`). To print readable contents:
*   **1D Array:** Use `Arrays.toString(arr)`
*   **2D Array:** Use `Arrays.deepToString(matrix)`

```java
int[] arr = {1, 2, 3};
System.out.println(Arrays.toString(arr)); // Output: [1, 2, 3]

int[][] matrix = {{1, 2}, {3, 4}};
System.out.println(Arrays.deepToString(matrix)); // Output: [[1, 2], [3, 4]]
```

### Cloning Arrays
Calling `clone()` on a 1D array creates a copy of the array:
```java
int[] copy = arr.clone();
```
*   **For Primitive Arrays:** Performs a deep copy of elements (independent arrays).
*   **For Object/Multidimensional Arrays:** Performs a **shallow copy**. It copies the references, meaning changes to objects inside the cloned array will be visible in the original array.

---

## Passing Arrays to Methods

In Java, arguments are passed by value. When passing an array to a method, you are passing **the value of the reference** (the memory pointer):
*   Reassigning the array variable inside the method does **not** affect the caller's reference.
*   Modifying elements inside the array **does** affect the caller's array because both point to the same heap object.

```java
void modifyArray(int[] arr) {
    arr[0] = 99; // Caller will see this change!
    arr = new int[]{5, 6, 7}; // Caller will NOT see this reassignment!
}
```
