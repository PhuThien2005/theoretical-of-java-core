package patternmatchingrecord;

import java.util.List;

public class PatternMatchingRecordTest {

    public static void main(String[] args) {
        try {
            testBasicPriceCalculations();
            testValidationExceptions();
            testLoyaltyPointsVIPPercentage();
            testLoyaltyPointsElite();
            testLoyaltyPointsOther();
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

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testBasicPriceCalculations() {
        var customer = new PatternMatchingRecord.Customer("Alice", PatternMatchingRecord.CustomerType.REGULAR);
        var items = List.of(
            new PatternMatchingRecord.Item("Book", 15.0),
            new PatternMatchingRecord.Item("Pen", 5.0)
        );

        // Case 1: No Discount
        var orderNoDiscount = new PatternMatchingRecord.Order("1", customer, items, new PatternMatchingRecord.NoDiscount());
        assertEquals(20.0, PatternMatchingRecord.calculateFinalPrice(orderNoDiscount), 0.001, "No discount should match sum of items");

        // Case 2: Flat Discount
        var orderFlat = new PatternMatchingRecord.Order("2", customer, items, new PatternMatchingRecord.FlatDiscount(7.5));
        assertEquals(12.5, PatternMatchingRecord.calculateFinalPrice(orderFlat), 0.001, "Flat discount subtraction failed");

        // Case 3: Flat Discount exceeding total
        var orderFlatExceed = new PatternMatchingRecord.Order("3", customer, items, new PatternMatchingRecord.FlatDiscount(25.0));
        assertEquals(0.0, PatternMatchingRecord.calculateFinalPrice(orderFlatExceed), 0.001, "Flat discount exceeding total should result in 0.0");

        // Case 4: Percentage Discount
        var orderPercent = new PatternMatchingRecord.Order("4", customer, items, new PatternMatchingRecord.PercentageDiscount(0.20));
        assertEquals(16.0, PatternMatchingRecord.calculateFinalPrice(orderPercent), 0.001, "Percentage discount calculation failed");
    }

    private static void testValidationExceptions() {
        var customer = new PatternMatchingRecord.Customer("Bob", PatternMatchingRecord.CustomerType.REGULAR);
        
        // Negative item price
        var invalidItems = List.of(new PatternMatchingRecord.Item("Vase", -10.0));
        var orderInvalidPrice = new PatternMatchingRecord.Order("5", customer, invalidItems, new PatternMatchingRecord.NoDiscount());
        try {
            PatternMatchingRecord.calculateFinalPrice(orderInvalidPrice);
            throw new AssertionError("Expected IllegalArgumentException for negative item price");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Negative flat discount
        var items = List.of(new PatternMatchingRecord.Item("Laptop", 1000.0));
        var orderInvalidFlat = new PatternMatchingRecord.Order("6", customer, items, new PatternMatchingRecord.FlatDiscount(-5.0));
        try {
            PatternMatchingRecord.calculateFinalPrice(orderInvalidFlat);
            throw new AssertionError("Expected IllegalArgumentException for negative flat discount");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Percentage discount rate out of bounds
        var orderInvalidPercent = new PatternMatchingRecord.Order("7", customer, items, new PatternMatchingRecord.PercentageDiscount(1.2));
        try {
            PatternMatchingRecord.calculateFinalPrice(orderInvalidPercent);
            throw new AssertionError("Expected IllegalArgumentException for rate > 1.0");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testLoyaltyPointsVIPPercentage() {
        var vip = new PatternMatchingRecord.Customer("Charlie", PatternMatchingRecord.CustomerType.VIP);
        var items = List.of(new PatternMatchingRecord.Item("Phone", 500.0));
        
        // VIP + 20% discount: price is 400.0. Points = 400.0 * 0.20 * 100 = 8000 points.
        var order = new PatternMatchingRecord.Order("8", vip, items, new PatternMatchingRecord.PercentageDiscount(0.20));
        assertEquals(8000, PatternMatchingRecord.calculateLoyaltyPoints(order), "VIP with percentage discount points matching failed");
    }

    private static void testLoyaltyPointsElite() {
        var elite = new PatternMatchingRecord.Customer("Diana", PatternMatchingRecord.CustomerType.ELITE);
        var items = List.of(new PatternMatchingRecord.Item("Monitor", 300.0));

        // ELITE + 10% discount: price is 270.0. Points = 270.0 * 2.0 = 540 points.
        var order1 = new PatternMatchingRecord.Order("9", elite, items, new PatternMatchingRecord.PercentageDiscount(0.10));
        assertEquals(540, PatternMatchingRecord.calculateLoyaltyPoints(order1), "ELITE customer points calculation failed");

        // ELITE + flat discount: price is 250.0. Points = 250.0 * 2.0 = 500 points.
        var order2 = new PatternMatchingRecord.Order("10", elite, items, new PatternMatchingRecord.FlatDiscount(50.0));
        assertEquals(500, PatternMatchingRecord.calculateLoyaltyPoints(order2), "ELITE customer points calculation failed with flat discount");
    }

    private static void testLoyaltyPointsOther() {
        // VIP with flat discount: price is 90.0. Points = 90.0 * 0.5 = 45 points.
        var vip = new PatternMatchingRecord.Customer("Eva", PatternMatchingRecord.CustomerType.VIP);
        var items = List.of(new PatternMatchingRecord.Item("Shoes", 100.0));
        var orderVipFlat = new PatternMatchingRecord.Order("11", vip, items, new PatternMatchingRecord.FlatDiscount(10.0));
        assertEquals(45, PatternMatchingRecord.calculateLoyaltyPoints(orderVipFlat), "VIP customer points calculation failed with non-percentage discount");

        // Regular customer: price is 100.0. Points = 100.0 * 0.1 = 10 points.
        var regular = new PatternMatchingRecord.Customer("Frank", PatternMatchingRecord.CustomerType.REGULAR);
        var orderRegular = new PatternMatchingRecord.Order("12", regular, items, new PatternMatchingRecord.NoDiscount());
        assertEquals(10, PatternMatchingRecord.calculateLoyaltyPoints(orderRegular), "Regular customer points calculation failed");
    }
}
