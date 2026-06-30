# Exercise: Financial Calculator

## Objective
Implement a high-precision `BigDecimal` calculator for safe monetary calculations, interest calculations, and receipt formatting.

## Problem Description
Double and float types are represented using IEEE 754 floating-point standards which introduce binary approximation errors (e.g. `0.1 + 0.2` becomes `0.30000000000000004`). In financial settings, this causes rounding errors and compliance violations. Java's `BigDecimal` is the standard solution.

## Requirements
Implement the following static methods in `FinancialCalculator`:
1. `BigDecimal calculateCompoundInterest(String principal, String annualRate, int years, int compoundingPeriodsPerYear)`:
   - Calculate interest using formula: $A = P \times (1 + r/n)^{nt}$
   - All intermediate divisions and exponentiations must retain a scale of at least 10 digits and use `RoundingMode.HALF_UP` to prevent precision loss.
   - Return the result rounded to a scale of 2 digits using `RoundingMode.HALF_UP`.

2. `BigDecimal calculateItemizedTotalWithTax(List<String> itemPrices, String taxRate)`:
   - Take a list of string prices, parse them as `BigDecimal`.
   - Sum them up.
   - Calculate tax: `sum * taxRate`.
   - Return `total = sum + tax` rounded to 2 digits using `RoundingMode.HALF_UP`.

3. `String formatCurrency(BigDecimal amount, Locale locale)`:
   - Format the currency amount using `java.text.NumberFormat.getCurrencyInstance(locale)`.
