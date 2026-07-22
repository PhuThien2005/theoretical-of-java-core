package financialcalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FinancialCalculatorSolution {

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
        if (principal == null || annualRate == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        if (years < 0 || compoundingPeriodsPerYear <= 0) {
            throw new IllegalArgumentException("Years must be non-negative and compounding periods must be positive");
        }

        BigDecimal p = new BigDecimal(principal);
        BigDecimal r = new BigDecimal(annualRate);
        BigDecimal n = new BigDecimal(compoundingPeriodsPerYear);
        int nt = compoundingPeriodsPerYear * years;

        // Intermediate rate division with scale of 10 to avoid precision loss
        BigDecimal rateOverPeriods = r.divide(n, 10, RoundingMode.HALF_UP);
        BigDecimal factor = BigDecimal.ONE.add(rateOverPeriods);

        // Exponentiation using BigDecimal.pow(int)
        BigDecimal poweredFactor = factor.pow(nt);

        // Multiply by principal and round final result to 2 decimals
        BigDecimal total = p.multiply(poweredFactor);
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Sums a list of item prices and applies a tax rate.
     * 
     * @param itemPrices list of price strings
     * @param taxRate tax rate string (e.g. "0.08" for 8%)
     * @return the total sum plus tax, rounded to 2 decimal places using RoundingMode.HALF_UP
     */
    public static BigDecimal calculateItemizedTotalWithTax(List<String> itemPrices, String taxRate) {
        if (itemPrices == null || taxRate == null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (String price : itemPrices) {
            if (price == null || price.trim().isEmpty()) {
                throw new IllegalArgumentException("Price string cannot be null or empty");
            }
            sum = sum.add(new BigDecimal(price));
        }

        BigDecimal taxRateDec = new BigDecimal(taxRate);
        BigDecimal tax = sum.multiply(taxRateDec);
        BigDecimal total = sum.add(tax);

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Formats the BigDecimal amount as currency for the specified Locale.
     */
    public static String formatCurrency(BigDecimal amount, Locale locale) {
        if (amount == null || locale == null) {
            throw new IllegalArgumentException("Amount and locale cannot be null");
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(amount);
    }
}
