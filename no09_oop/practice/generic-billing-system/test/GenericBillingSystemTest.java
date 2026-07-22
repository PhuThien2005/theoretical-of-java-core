package no09_oop.practice.generic_billing_system;

/**
 * Test runner for GenericBillingSystem.
 */
public class GenericBillingSystemTest {

    public static void main(String[] args) {
        try {
            testPhysicalProduct();
            testServiceSubscription();
            testBillingCalculation();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 0.000001) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + "\nExpected: [" + expected + "]\nActual:   [" + actual + "]");
        }
    }

    private static void testPhysicalProduct() {
        // Laptop base price $1000, 10% discount, shipping $15
        // Price should be: (1000 * 0.90) + 15 = 915.0
        PhysicalProduct product = new PhysicalProduct("Laptop", 1000.0, 10.0, 15.0);
        assertEquals(915.0, product.getPrice(), "Laptop price check");
        assertEquals("Laptop", product.getDescription(), "Laptop description check");
        assertEquals(10.0, product.getDiscountPercentage(), "Laptop discount percentage");
    }

    private static void testServiceSubscription() {
        // Netflix flat monthly fee $15.99
        ServiceSubscription sub = new ServiceSubscription("Netflix", 15.99);
        assertEquals(15.99, sub.getPrice(), "Netflix subscription price check");
        assertEquals("Netflix", sub.getDescription(), "Netflix description check");
    }

    private static void testBillingCalculation() {
        Billable[] items = new Billable[] {
            new PhysicalProduct("Book", 20.0, 20.0, 3.99), // (20 * 0.80) + 3.99 = 19.99
            new ServiceSubscription("Spotify", 9.99),       // 9.99
            null,                                            // Should be skipped safely
            new PhysicalProduct("Headphones", 50.0, 0.0, 0.0) // 50.0
        };

        // Total should be: 19.99 + 9.99 + 50.0 = 79.98
        double total = GenericBillingSystem.calculateTotal(items);
        assertEquals(79.98, total, "Total billing calculation");
    }
}
