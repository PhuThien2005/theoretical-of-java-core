package codesmellrefactoring;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CodeSmellRefactoringTest {

    public static void main(String[] args) {
        try {
            testShippingRates();
            testShippingExceptions();
            testFileReading();
            testFileReadingExceptions();
            testReceiptGeneration();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(double expected, double actual, double delta, String message) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
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

    private static void testShippingRates() {
        assertEquals(20.0, CodeSmellRefactoring.calculateShipping("STANDARD", 10.0), 0.001, "Standard shipping calculation failed");
        assertEquals(50.0, CodeSmellRefactoring.calculateShipping("EXPRESS", 10.0), 0.001, "Express shipping calculation failed");
        assertEquals(100.0, CodeSmellRefactoring.calculateShipping("OVERNIGHT", 10.0), 0.001, "Overnight shipping calculation failed");
    }

    private static void testShippingExceptions() {
        try {
            CodeSmellRefactoring.calculateShipping("UNKNOWN", 5.0);
            throw new AssertionError("Expected IllegalArgumentException for unknown shipping method");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            CodeSmellRefactoring.calculateShipping("STANDARD", -2.0);
            throw new AssertionError("Expected IllegalArgumentException for negative weight");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testFileReading() throws IOException {
        File tempFile = File.createTempFile("test_items", ".txt");
        tempFile.deleteOnExit();

        try (FileWriter w = new FileWriter(tempFile)) {
            w.write("Apple\nBanana\nCherry\n");
        }

        List<String> items = CodeSmellRefactoring.readItemsFromFile(tempFile.getAbsolutePath());
        assertTrue(items != null, "Items list should not be null");
        assertEquals(3, items.size(), "Items size should be 3");
        assertEquals("Apple", items.get(0), "First item should be Apple");
        assertEquals("Banana", items.get(1), "Second item should be Banana");
        assertEquals("Cherry", items.get(2), "Third item should be Cherry");
    }

    private static void testFileReadingExceptions() {
        try {
            CodeSmellRefactoring.readItemsFromFile("nonexistent_file_path_12345.txt");
            throw new AssertionError("Expected IOException for non-existent file path");
        } catch (IOException e) {
            // Expected
        }
    }

    private static void testReceiptGeneration() {
        List<String> items = List.of("T-Shirt", "Jeans", "Socks");
        String receipt = CodeSmellRefactoring.generateReceipt("George", items, 0.08);
        String expected = "Receipt for: George\n- T-Shirt\n- Jeans\n- Socks\nTax Rate: 0.08\n";
        assertEquals(expected, receipt, "Receipt string matches expected format failed");
    }
}
