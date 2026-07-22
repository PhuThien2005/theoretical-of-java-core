# Practice Exercises: Design Principles

This folder contains hands-on practice exercises to reinforce your understanding of software design principles (specifically SOLID principles) and structural design.

## Exercises

### 1. SOLID Violations Refactoring (`solid-violations-refactoring`)
Refactor a legacy order processing system that violates multiple SOLID principles:
- **Single Responsibility Principle (SRP)**: The order processor handles calculation, database persistence, and email notification.
- **Open-Closed Principle (OCP)**: Adding a new payment type or discount requires editing the core processing class.
- **Dependency Inversion Principle (DIP)**: The processor instantiates concrete database helper and email sender instances directly.

#### Directory Structure
- [SolidViolationsRefactoring.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no42_design_principles/practice/solid-violations-refactoring/src/SolidViolationsRefactoring.java)
- [SolidViolationsRefactoringTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no42_design_principles/practice/solid-violations-refactoring/test/SolidViolationsRefactoringTest.java)
- [SolidViolationsRefactoring.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no42_design_principles/practice/solid-violations-refactoring/solution/SolidViolationsRefactoring.java)

---

### 2. Composite Pattern Design (`composite-pattern-design`)
Implement structural composition using interfaces to model a file system:
- **Component**: Create a common interface/class representing files and directories.
- **Leaf**: Implement a file class that contains a name and a size.
- **Composite**: Implement a directory class that can contain other components, calculates its size dynamically by aggregating children sizes, and recursively prints the tree structure.

#### Directory Structure
- [CompositePatternDesign.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no42_design_principles/practice/composite-pattern-design/src/CompositePatternDesign.java)
- [CompositePatternDesignTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no42_design_principles/practice/composite-pattern-design/test/CompositePatternDesignTest.java)
- [CompositePatternDesign.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no42_design_principles/practice/composite-pattern-design/solution/CompositePatternDesign.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository to test your implementations:

```bash
python3 scripts/verify_exercise.py no42_design_principles
```
