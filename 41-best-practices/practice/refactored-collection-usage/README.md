# Exercise: Refactored Collection Usage

## Objective
Optimize Java Collections framework declarations, initializations, and operations following Java performance and safety best practices.

## Requirements
Improve the following collection patterns in `RefactoredCollectionUsage`:
1. **Interface Programming**: Always declare variables using collection interfaces (e.g. `List`, `Map`, `Set`) rather than concrete implementations (e.g. `ArrayList`, `HashMap`).
2. **Immutable Collections**: For predefined, fixed-size read-only collections, use modern factory methods (`List.of`, `Map.of`, `Set.of`) instead of initializing empty mutable collections and writing multiple `.add()` calls.
3. **Map Pre-allocation (Initial Capacity)**: When initializing a `HashMap` or `HashSet` with a known target size, always specify the initial capacity to prevent rehash and resizing performance penalties.
   - The formula for initial capacity is `(int) (expectedSize / 0.75f) + 1` to prevent load factor threshold crossings.
4. **List to Array Conversion**: Convert lists to arrays using type-safe and modern methods (`list.toArray(String[]::new)` or `list.toArray(new String[0])` which is faster than `new String[list.size()]` due to JVM optimizations).
