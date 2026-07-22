# Practice Exercises: Basic Syntax

This folder contains hands-on practice exercises to reinforce your understanding of Java basic syntax, naming conventions, and comments.

## Exercises

### 1. Code Style Checker (`code-style-checker`)
A simple utility that validates Java naming conventions:
- **Class Names**: Should follow `PascalCase` (e.g., `StudentService`, `OrderService`).
- **Method & Variable Names**: Should follow `camelCase` (e.g., `calculateTotal`, `studentName`).
- **Constants**: Should follow `UPPER_SNAKE_CASE` (e.g., `MAX_RETRY_COUNT`, `PI`).

#### Directory Structure
- [CodeStyleChecker.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no02_basic_syntax/practice/code-style-checker/src/CodeStyleChecker.java)
- [CodeStyleCheckerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no02_basic_syntax/practice/code-style-checker/test/CodeStyleCheckerTest.java)
- [CodeStyleChecker.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no02_basic_syntax/practice/code-style-checker/solution/CodeStyleChecker.java)

---

### 2. Comments Stripper (`comments-stripper`)
A utility that strips single-line (`//`) and multi-line (`/* ... */`) comments from a given Java source code string.
- Crucially, it must **not** strip comment-like patterns inside string literals (`"..."`) or character literals (`'...'`).

#### Directory Structure
- [CommentsStripper.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no02_basic_syntax/practice/comments-stripper/src/CommentsStripper.java)
- [CommentsStripperTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no02_basic_syntax/practice/comments-stripper/test/CommentsStripperTest.java)
- [CommentsStripper.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no02_basic_syntax/practice/comments-stripper/solution/CommentsStripper.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository to test your implementations:

```bash
python3 scripts/verify_exercise.py no02_basic_syntax
```

The script will compile both the starter code and the solution code, verifying that:
1. The starter code fails the tests.
2. The solution code passes the tests.
