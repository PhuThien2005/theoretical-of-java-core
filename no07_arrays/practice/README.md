# Practice Exercises: Arrays

This folder contains hands-on practice exercises to reinforce your understanding of Java 1D and 2D arrays, indexing, boundary checks, and in-place manipulation algorithms.

## Exercises

### 1. In-Place Array Rotator (`inplace-array-rotator`)
A classic algorithm challenge where you must rotate a 1D array of integers to the right by `k` steps in-place (using $O(1)$ extra space).
- **Goal**: Implement the three-step array reversal technique to rotate elements efficiently without allocating helper arrays.

#### Directory Structure
- [InPlaceArrayRotator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no07_arrays/practice/inplace-array-rotator/src/InPlaceArrayRotator.java)
- [InPlaceArrayRotatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no07_arrays/practice/inplace-array-rotator/test/InPlaceArrayRotatorTest.java)
- [InPlaceArrayRotator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no07_arrays/practice/inplace-array-rotator/solution/InPlaceArrayRotator.java)

---

### 2. Matrix Transposer (`matrix-transposer`)
2D arrays in Java are represented as "arrays of arrays". This exercise guides you through transposing an $N \times N$ square matrix in-place by swapping elements across the diagonal, and sorting the individual rows.
- **Goal**: Perform coordinate-based index swaps in nested loops and manipulate 2D array rows.

#### Directory Structure
- [MatrixTransposer.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no07_arrays/practice/matrix-transposer/src/MatrixTransposer.java)
- [MatrixTransposerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no07_arrays/practice/matrix-transposer/test/MatrixTransposerTest.java)
- [MatrixTransposer.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no07_arrays/practice/matrix-transposer/solution/MatrixTransposer.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no07_arrays
```
