# Practice Exercises: Data Types

This folder contains hands-on practice exercises to reinforce your understanding of Java primitive data types, type casting, overflows, and binary representation.

## Exercises

### 1. Safe Math Operations (`safe-math-operations`)
A utility to prevent silent integer overflows and data loss during casting. In Java, standard integer arithmetic can overflow without warning. This exercise tasks you with implementing safe arithmetic and casting operations that explicitly check for and throw `ArithmeticException` on overflow/underflow.
- **Goal**: Implement manual overflow checks for integer addition, multiplication, and casting from `long` to `int`.

#### Directory Structure
- [SafeMathOperations.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no03_data_types/practice/safe-math-operations/src/SafeMathOperations.java)
- [SafeMathOperationsTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no03_data_types/practice/safe-math-operations/test/SafeMathOperationsTest.java)
- [SafeMathOperations.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no03_data_types/practice/safe-math-operations/solution/SafeMathOperations.java)

---

### 2. Binary Data Parser (`binary-data-parser`)
A parser that decodes primitive values from a raw byte stream (big-endian byte array). This mimics networking or file parsing operations where structured binary data is read.
- **Goal**: Read and decode a `short`, `int`, `long`, and `boolean` from specific byte offsets, ensuring proper bit-masking and boundary checks.

#### Directory Structure
- [BinaryDataParser.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no03_data_types/practice/binary-data-parser/src/BinaryDataParser.java)
- [BinaryDataParserTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no03_data_types/practice/binary-data-parser/test/BinaryDataParserTest.java)
- [BinaryDataParser.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no03_data_types/practice/binary-data-parser/solution/BinaryDataParser.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no03_data_types
```
