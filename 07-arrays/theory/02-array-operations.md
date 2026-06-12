# Array Operations

The `java.util.Arrays` class and system native methods provide high-performance operations for copying, sorting, searching, and comparing arrays.

---

## Detailed Mechanics of Copying Arrays

Java provides multiple ways to copy arrays, each with different performance characteristics and syntax.

### 1. `System.arraycopy()`
This is a low-level, native static method of the `System` class. It is highly optimized because it copies memory blocks directly at the OS/hardware level.

**Syntax:**
```java
System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
```
- `src`: Source array.
- `srcPos`: Starting position index in the source array.
- `dest`: Destination array.
- `destPos`: Starting position index in the destination array.
- `length`: Number of elements to copy.

**Key characteristics:**
- You must pre-allocate the destination array with sufficient size.
- Throws `NullPointerException` if source or destination is `null`.
- Throws `IndexOutOfBoundsException` if indices exceed array sizes.
- Throws `ArrayStoreException` if the runtime types of source and destination arrays are incompatible.

### 2. `Arrays.copyOf()`
Creates a new array copy from index `0` up to `newLength`.

```java
int[] copy = Arrays.copyOf(original, newLength);
```
- If `newLength` is larger than `original.length`, the remaining slots are filled with default values (`0`, `false`, or `null`).
- If `newLength` is smaller, the array is truncated.
- Under the hood, this method calls `System.arraycopy` after allocating the new array.

### 3. `Arrays.copyOfRange()`
Copies a specific range of the array.

```java
int[] rangeCopy = Arrays.copyOfRange(original, fromIndex, toIndex);
```
- `fromIndex`: Inclusive start index.
- `toIndex`: Exclusive end index.
- If `toIndex` is greater than the array length, the new array is padded with default values.
- Throws `IllegalArgumentException` if `fromIndex > toIndex`.
- Throws `ArrayIndexOutOfBoundsException` if `fromIndex < 0` or `fromIndex > original.length`.

---

## Sorting Arrays: Algorithms and Stability

The `Arrays.sort()` method is overloaded to sort primitive types and object types.

### 1. Primitive Array Sorting (Dual-Pivot Quicksort)
For primitive arrays (`int[]`, `double[]`, etc.), `Arrays.sort()` uses a **Dual-Pivot Quicksort** algorithm.
- **Time Complexity:** Average $O(n \log n)$, Worst-case $O(n^2)$ (though highly optimized to avoid worst-case scenarios).
- **Stability:** **Unstable** (may reorder equal elements). Since primitives do not have identity, stability does not matter.

### 2. Object Array Sorting (Timsort)
For object arrays (`String[]`, custom classes), `Arrays.sort()` uses **Timsort** (a hybrid of Merge Sort and Insertion Sort).
- **Time Complexity:** Average and Worst-case $O(n \log n)$, Best-case $O(n)$ when the array is already sorted.
- **Stability:** **Stable** (preserves the relative order of equal elements).
- **Prerequisite:** The objects inside the array must implement the `Comparable` interface, or you must pass a custom `Comparator` to specify the ordering logic.

### 3. Sorting Ranges
You can sort a specific sub-array using:
```java
Arrays.sort(arr, fromIndex, toIndex); // toIndex is exclusive
```

---

## Searching: Binary Search Math

`Arrays.binarySearch()` is an $O(\log n)$ search algorithm.

> [!IMPORTANT]
> The array **must be sorted** in ascending order before calling `binarySearch()`. If the array is not sorted, the returned index is undefined and unpredictable.

### Return Value Calculations:
1. **If Key is Found:** Returns the positive index (0-based) where the element resides.
2. **If Key is Not Found:** Returns a negative value calculated as:
   $$\text{Return Value} = -(\text{insertionPoint}) - 1$$
   Where `insertionPoint` is the index where the key *would be* inserted to maintain sorted order.

**Example Math:**
```java
int[] arr = {10, 20, 30, 40, 50};

int index1 = Arrays.binarySearch(arr, 30); // Found at index 2. Returns 2.
int index2 = Arrays.binarySearch(arr, 25); // Not found. Should be inserted at index 2 (between 20 and 30).
                                           // Returns: -(2) - 1 = -3.
int index3 = Arrays.binarySearch(arr, 5);  // Not found. Should be inserted at index 0.
                                           // Returns: -(0) - 1 = -1.
int index4 = Arrays.binarySearch(arr, 60); // Not found. Should be inserted at index 5.
                                           // Returns: -(5) - 1 = -6.
```

---

## Comparing Arrays (Shallow vs. Deep)

Using `==` on array variables only compares their references (addresses on the stack).

### 1. `Arrays.equals()`
Compares two 1D arrays for content equality:
- Returns `true` if both arrays contain the same number of elements, and all corresponding pairs of elements are equal (using `==` for primitives, and `.equals()` for objects).
- It is null-safe (handles comparisons where one or both arrays are `null`).

### 2. `Arrays.deepEquals()`
Compares two multidimensional arrays.
- `Arrays.equals()` on a 2D array compares the references of the inner row arrays. If the rows reside at different heap addresses, it returns `false` even if the values are identical.
- `Arrays.deepEquals()` recursively traverses the nested arrays, comparing values at the lowest levels.

```java
int[][] matrix1 = {{1, 2}};
int[][] matrix2 = {{1, 2}};

System.out.println(Arrays.equals(matrix1, matrix2));     // false (inner row addresses differ)
System.out.println(Arrays.deepEquals(matrix1, matrix2)); // true (contents compared recursively)
```
