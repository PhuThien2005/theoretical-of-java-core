---
name: java-exercise-builder
description: Guidelines for generating and verifying Java Core coding exercises, tests, and solutions in localized subfolders.
---

# Java Exercise Builder Skill

This skill defines instructions for constructing, structuring, and verifying Java Core coding exercises.

## 1. Directory Structure
Every exercise must occupy its own directory within `<topic-folder>/practice/`:

```text
<topic-folder>/practice/<exercise-name>/
├── README.md                 # Description, instructions, and examples
├── src/
│   └── <ExerciseClass>.java  # Starter code skeleton with TODOs
├── test/
│   └── <TestClass>.java      # Test runner class with a main method
└── solution/
    └── <SolutionClass>.java  # Fully working reference solution
```

## 2. Requirements for Code Files

### A. Starter Code (`src/<ExerciseClass>.java`)
- Must compile successfully.
- Must contain `// TODO` comments detailing what the student needs to complete.
- Methods should return dummy/default values (e.g., `return 0;`, `return null;`) so that tests fail but compile.

### B. Test Runner (`test/<TestClass>.java`)
- Must contain a `public static void main(String[] args)` method.
- Must be executable directly with `java <TestClass>.java`.
- Must contain standard assertion checks. If an assertion fails, it must throw an `AssertionError`.
- Must output `✅ All tests passed successfully!` on success and exit with status 0. On failure, print standard stack traces and exit with status 1.

### C. Solution Code (`solution/<SolutionClass>.java`)
- Must be a fully functional implementation.
- Must compile and pass all assertions in `<TestClass>.java`.
- Must include detailed inline documentation and comments explaining the "why" of critical blocks, trade-offs, and optimization opportunities.

## 3. Verification Workflow
1. Write the starter code, test, and solution.
2. Compile and run the test using the starter code template. It **must fail** the test suite.
3. Compile and run the test using the reference solution. It **must pass** successfully.
4. Record the compilation and execution output in the workspace progress log.
