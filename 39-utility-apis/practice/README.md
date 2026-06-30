# Practice Exercises: Utility APIs

This folder contains hands-on practice exercises to reinforce your understanding of core Java utility APIs, specifically `BigDecimal` for safe financial math, and locales/formatting.

## Exercises

### 1. Financial Calculator (`financial-calculator`)
A helper class to perform safe, high-precision financial operations using `BigDecimal`:
- **Rounding and Math**: Learn why floating-point math (`double`/`float`) is unsafe for monetary calculations.
- **BigDecimal API**: Perform addition, subtraction, division with rounding modes, scale settings, and formatting.

#### Directory Structure
- [FinancialCalculator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/39-utility-apis/practice/financial-calculator/src/FinancialCalculator.java)
- [FinancialCalculatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/39-utility-apis/practice/financial-calculator/test/FinancialCalculatorTest.java)
- [FinancialCalculator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/39-utility-apis/practice/financial-calculator/solution/FinancialCalculator.java)

---

### 2. System Property Dumper (`system-property-dumper`)
A utility that dumps system environment information formatted for different locales:
- **Locales & Formatting**: Learn to extract Java system property info and format dates, currencies, and numbers according to specific user locales (e.g. `US`, `FRANCE`, `VIETNAM`).

#### Directory Structure
- [SystemPropertyDumper.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/39-utility-apis/practice/system-property-dumper/src/SystemPropertyDumper.java)
- [SystemPropertyDumperTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/39-utility-apis/practice/system-property-dumper/test/SystemPropertyDumperTest.java)
- [SystemPropertyDumper.java (Solution)](file:///home/fhu_thjen/projects/learning-java/39-utility-apis/practice/system-property-dumper/solution/SystemPropertyDumper.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository to test your implementations:

```bash
python3 scripts/verify_exercise.py 39-utility-apis
```
