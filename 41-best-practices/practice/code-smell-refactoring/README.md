# Exercise: Code Smell Refactoring

## Objective
Refactor a legacy order/shipping calculator containing multiple severe code smells:
1. **Resource Leak**: Classic I/O operations without try-with-resources.
2. **Magic Numbers**: Numeric values directly embedded in calculations.
3. **OCP Violations**: A long `if-else` block parsing strings to compute dynamic shipping fees.
4. **Poor Exception Handling**: Swallowing exceptions or returning generic, uninformative values without context.
5. **Inefficient Loop Concatenations**: Concatenating strings inside a loop instead of using a `StringBuilder`.

## Requirements
Refactor `CodeSmellRefactoring` to solve all of these code smells while keeping its functional behavior exactly the same as expected by the tests.
- Define explicit constants for magic numbers (e.g. tax rates, base fees).
- Use **try-with-resources** for reading inputs.
- Use a `StringBuilder` for loop string generation.
- Rethrow descriptive exceptions or wrap them properly rather than swallowing.
- Replace the long `if-else` chain with a cleaner mapping strategy or structured lookups.
