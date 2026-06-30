# Practice Exercises: Comparable & Comparator

This folder contains hands-on practice exercises to reinforce your understanding of Java's sorting capabilities: the `Comparable` interface (defining natural ordering) and the `Comparator` interface (defining custom or multi-criteria ordering).

## Exercises

### 1. Multi-Criteria Employee Sorter (`multi-criteria-employee-sorter`)
Java 8+ introduces default and static methods on `Comparator` (like `comparing()` and `thenComparing()`) that allow you to chain comparisons cleanly without writing nested if-else logic.
- **Goal**: Sort a list of `Employee` objects first by department (alphabetical), then by salary (descending), and finally by age (ascending).

#### Directory Structure
- [MultiCriteriaEmployeeSorter.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/20-comparable-comparator/practice/multi-criteria-employee-sorter/src/MultiCriteriaEmployeeSorter.java)
- [MultiCriteriaEmployeeSorterTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/20-comparable-comparator/practice/multi-criteria-employee-sorter/test/MultiCriteriaEmployeeSorterTest.java)
- [MultiCriteriaEmployeeSorter.java (Solution)](file:///home/fhu_thjen/projects/learning-java/20-comparable-comparator/practice/multi-criteria-employee-sorter/solution/MultiCriteriaEmployeeSorter.java)

---

### 2. Custom Sort Heap (`custom-sort-heap`)
A Heap (represented in Java by `PriorityQueue`) retrieves the "highest priority" element first. The priority can be determined either by the elements' natural ordering (implementing `Comparable`) or by a dynamic `Comparator` supplied during construction.
- **Goal**: Implement a `CustomSortHeap` wrapper class that supports both natural sorting and custom comparator-based sorting.

#### Directory Structure
- [CustomSortHeap.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/20-comparable-comparator/practice/custom-sort-heap/src/CustomSortHeap.java)
- [CustomSortHeapTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/20-comparable-comparator/practice/custom-sort-heap/test/CustomSortHeapTest.java)
- [CustomSortHeap.java (Solution)](file:///home/fhu_thjen/projects/learning-java/20-comparable-comparator/practice/custom-sort-heap/solution/CustomSortHeap.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 20-comparable-comparator
```
