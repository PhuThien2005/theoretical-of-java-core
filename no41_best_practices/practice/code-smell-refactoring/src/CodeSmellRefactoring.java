package codesmellrefactoring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CodeSmellRefactoring {

    /**
     * Calculates shipping fees based on method and weight.
     * Legacy code used a messy if-else chain with magic numbers:
     * <code>
     * if (method.equalsIgnoreCase("STANDARD")) {
     *     return weight * 1.5 + 5.0;
     * } else if (method.equalsIgnoreCase("EXPRESS")) {
     *     return weight * 3.5 + 15.0;
     * } else if (method.equalsIgnoreCase("OVERNIGHT")) {
     *     return weight * 7.0 + 30.0;
     * } else {
     *     return -1.0; // bad error code!
     * }
     * </code>
     */
    public static double calculateShipping(String method, double weight) {
        // TODO: Refactor using clean code: constants, better error handling (throw exception instead of returning -1.0),
        // and a clean map or switch instead of complex if-else chains.
        return 0.0;
    }

    /**
     * Reads a list of items from a file.
     * Legacy code leaked file handles and swallowed exceptions:
     * <code>
     * try {
     *     BufferedReader r = new BufferedReader(new FileReader(filePath));
     *     List<String> list = new ArrayList<>();
     *     String line;
     *     while ((line = r.readLine()) != null) {
     *         list.add(line);
     *     }
     *     return list;
     * } catch (Exception e) {
     *     System.out.println("Error reading file!");
     *     return null; // swallowed exception, return null is dangerous!
     * }
     * </code>
     */
    public static List<String> readItemsFromFile(String filePath) throws IOException {
        // TODO: Refactor to prevent resource leaks (use try-with-resources)
        // and throw/rethrow informative IOException instead of swallowing and returning null.
        return null;
    }

    /**
     * Generates a formatted receipt string for the customer.
     * Legacy code concatenated strings in a loop and used magic tax rates:
     * <code>
     * String receipt = "Receipt for: " + customerName + "\n";
     * for (String item : items) {
     *     receipt += "- " + item + "\n"; // Inefficient loop concatenation!
     * }
     * receipt += "Tax Rate: " + 0.08 + "\n"; // Magic number!
     * return receipt;
     * </code>
     */
    public static String generateReceipt(String customerName, List<String> items, double taxRate) {
        // TODO: Refactor using StringBuilder and final constants for defaults.
        return "";
    }
}
