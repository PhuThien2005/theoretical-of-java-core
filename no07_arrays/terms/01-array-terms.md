# Array Terms

This file details key terms related to arrays in Java.

## Array

An array is a fixed-size, homogeneous container object allocated on the heap. Homogeneous means all its elements must be of the same type.

## Index

An integer specifying the position of an element in an array. In Java, array indices are 0-based. Accessing an index outside `[0, array.length - 1]` triggers an `ArrayIndexOutOfBoundsException`.

## Ragged Array (Jagged Array)

A multidimensional array where the member arrays can be of different lengths. This is possible because Java represents multidimensional arrays as "arrays of arrays".

```java
int[][] ragged = {
    {1, 2},
    {3, 4, 5},
    {6}
};
```

## Off-by-One Error

A common logical bug where a loop iterates one time too many or one time too few, often caused by using `<=` instead of `<` when checking against the array length.

```java
int[] arr = new int[5];
// for (int i = 0; i <= arr.length; i++) // Bug! Index 5 does not exist.
```

## `System.arraycopy`

A native Java method that copies data between arrays at the system memory level. It is highly optimized and faster than copying elements one by one via a Java loop for large arrays.

## Dual-Pivot Quicksort

The sorting algorithm used by `Arrays.sort()` for primitive type arrays. It has a $O(n \log n)$ average performance and is typically faster than standard single-pivot quicksort.

## Timsort

The sorting algorithm used by `Arrays.sort()` for object type arrays. It is a stable, hybrid sorting algorithm derived from merge sort and insertion sort, designed to perform well on many kinds of real-world data.

## `Arrays.deepEquals`

A utility method that recursively compares multidimensional arrays to check if they are deeply equal. Standard `Arrays.equals` only compares the top-level elements (which are references in a 2D array) and will fail for nested structures.

## `ArrayIndexOutOfBoundsException`

A runtime exception thrown when code attempts to access an array index that is negative, or greater than or equal to the array's length.
