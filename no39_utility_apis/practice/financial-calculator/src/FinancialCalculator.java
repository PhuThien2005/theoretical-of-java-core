package financialcalculator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class FinancialCalculator {

    /**
     * Calculates compound interest using the formula: A = P(1 + r/n)^(n*t).
     * 
     * @param principal P - initial principal amount
     * @param annualRate r - annual interest rate (e.g. "0.05" for 5%)
     * @param years t - time in years
     * @param compoundingPeriodsPerYear n - number of times interest is compounded per year
     * @return the total amount A, rounded to 2 decimal places using RoundingMode.HALF_UP
     */
    public static BigDecimal calculateCompoundInterest(
            String principal, String annualRate, int years, int compoundingPeriodsPerYear) {
        // TODO: Implement compound interest using BigDecimal.
        // Use a scale of 10 for intermediate operations, and RoundingMode.HALF_UP.
        return null;
    }

    /**
     * Sums a list of item prices and applies a tax rate.
     * 
     * @param itemPrices list of price strings
     * @param taxRate tax rate string (e.g. "0.08" for 8%)
     * @return the total sum plus tax, rounded to 2 decimal places using RoundingMode.HALF_UP
     */
    public static BigDecimal calculateItemizedTotalWithTax(List<String> itemPrices, String taxRate) {
        // TODO: Sum the prices.
        // TODO: Compute tax and return total sum + tax, scaled to 2 decimals using RoundingMode.HALF_UP.
        return null;
    }

    /**
     * Formats the BigDecimal amount as currency for the specified Locale.
     */
    public static String formatCurrency(BigDecimal amount, Locale locale) {
        // TODO: Format using NumberFormat.getCurrencyInstance.
        return null;
    }
}
