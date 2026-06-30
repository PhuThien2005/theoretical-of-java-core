package financialcalculator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class FinancialCalculatorTest {

    public static void main(String[] args) {
        try {
            testCompoundInterest();
            testItemizedTotal();
            testCurrencyFormatting();
            testValidationExceptions();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: [" + expected + "], Actual: [" + actual + "]");
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testCompoundInterest() {
        // P = 1000, rate = 5% (0.05), compounded quarterly (4 times a year) for 5 years.
        // A = 1000 * (1 + 0.05/4)^(4*5) = 1000 * (1.0125)^20 ≈ 1282.0372... -> 1282.04
        BigDecimal result = FinancialCalculator.calculateCompoundInterest("1000.00", "0.05", 5, 4);
        assertEquals(new BigDecimal("1282.04"), result, "Compound interest calculation failed");
    }

    private static void testItemizedTotal() {
        // Sum: 10.00 + 15.50 + 4.99 = 30.49
        // Tax: 30.49 * 0.08 = 2.4392
        // Total: 30.49 + 2.4392 = 32.9292 -> scaled to 2 decimals: 32.93
        List<String> prices = List.of("10.00", "15.50", "4.99");
        BigDecimal result = FinancialCalculator.calculateItemizedTotalWithTax(prices, "0.08");
        assertEquals(new BigDecimal("32.93"), result, "Itemized total with tax calculation failed");
    }

    private static void testCurrencyFormatting() {
        BigDecimal amount = new BigDecimal("100.50");
        String formattedUS = FinancialCalculator.formatCurrency(amount, Locale.US);
        
        // Assert that the formatted currency contains the components (e.g. '$', '100', '.', '50')
        // We use contains to avoid minor JDK localized space/symbol variations
        assertTrue(formattedUS.contains("$") && formattedUS.contains("100") && formattedUS.contains("50"),
                "US currency formatting failed: " + formattedUS);
    }

    private static void testValidationExceptions() {
        try {
            FinancialCalculator.calculateCompoundInterest(null, "0.05", 5, 4);
            throw new AssertionError("Expected IllegalArgumentException for null principal");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            FinancialCalculator.calculateItemizedTotalWithTax(List.of("10.00", "invalid"), "0.08");
            throw new AssertionError("Expected NumberFormatException or IllegalArgumentException for invalid price");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
