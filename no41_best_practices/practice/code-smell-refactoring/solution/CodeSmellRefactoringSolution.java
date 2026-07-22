package codesmellrefactoring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CodeSmellRefactoringSolution {

    // 1. Avoid Magic Numbers: Define clear, final constants.
    public static final double STANDARD_RATE = 1.5;
    public static final double STANDARD_BASE = 5.0;
    
    public static final double EXPRESS_RATE = 3.5;
    public static final double EXPRESS_BASE = 15.0;
    
    public static final double OVERNIGHT_RATE = 7.0;
    public static final double OVERNIGHT_BASE = 30.0;

    // A map lookup replaces the fragile, long if-else chain.
    private static final Map<String, ShippingCostCalculator> SHIPPING_STRATEGIES = Map.of(
        "STANDARD", new ShippingCostCalculator(STANDARD_RATE, STANDARD_BASE),
        "EXPRESS", new ShippingCostCalculator(EXPRESS_RATE, EXPRESS_BASE),
        "OVERNIGHT", new ShippingCostCalculator(OVERNIGHT_RATE, OVERNIGHT_BASE)
    );

    private record ShippingCostCalculator(double rate, double baseFee) {
        public double calculate(double weight) {
            return (weight * rate) + baseFee;
        }
    }

    /**
     * Calculates shipping fees based on method and weight.
     * Refactored: Replaced magic numbers with constants, replaced if-else with a Map strategy,
     * and throws explicit, typed exceptions rather than returning magic error codes (-1.0).
     */
    public static double calculateShipping(String method, double weight) {
        if (method == null) {
            throw new IllegalArgumentException("Shipping method cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        String normalizedMethod = method.toUpperCase().trim();
        ShippingCostCalculator calculator = SHIPPING_STRATEGIES.get(normalizedMethod);
        
        if (calculator == null) {
            throw new IllegalArgumentException("Unknown shipping method: " + method);
        }

        return calculator.calculate(weight);
    }

    /**
     * Reads a list of items from a file.
     * Refactored: Uses try-with-resources to prevent file leaks (closes BufferedReader automatically),
     * and propagates IOException with context rather than swallowing it and returning a risky null.
     */
    public static List<String> readItemsFromFile(String filePath) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }

        List<String> list = new ArrayList<>();
        // Try-with-resources handles auto-closing of BufferedReader in case of success or exception.
        try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = r.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            // Rethrow with contextual details rather than swallowing and logging.
            throw new IOException("Failed to read items from file at path: " + filePath, e);
        }
        return list;
    }

    /**
     * Generates a formatted receipt string for the customer.
     * Refactored: Uses StringBuilder to prevent high GC overhead in string concatenation loops,
     * and takes taxRate explicitly with bounds validation.
     */
    public static String generateReceipt(String customerName, List<String> items, double taxRate) {
        if (customerName == null) {
            throw new IllegalArgumentException("Customer name cannot be null");
        }
        if (items == null) {
            throw new IllegalArgumentException("Items list cannot be null");
        }
        if (taxRate < 0.0) {
            throw new IllegalArgumentException("Tax rate cannot be negative");
        }

        // Using StringBuilder prevents generating multiple intermediate immutable String instances in memory.
        StringBuilder receiptBuilder = new StringBuilder();
        receiptBuilder.append("Receipt for: ").append(customerName).append("\n");
        for (String item : items) {
            receiptBuilder.append("- ").append(item).append("\n");
        }
        receiptBuilder.append("Tax Rate: ").append(taxRate).append("\n");
        
        return receiptBuilder.toString();
    }
}
