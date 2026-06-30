import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Test runner for SalesReportingAggregator.
 */
public class SalesReportingAggregatorTest {

    public static void main(String[] args) {
        try {
            testSumByCategory();
            testPartitionHighValueSales();
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
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertDoubleEquals(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 0.000001) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testSumByCategory() {
        List<Sale> sales = Arrays.asList(
            new Sale("Bob", "Electronics", 500.0),
            new Sale("Alice", "Furniture", 1000.0),
            new Sale("Bob", "Electronics", 200.0),
            new Sale("Charlie", "Furniture", 300.0)
        );

        Map<String, Double> categorySums = SalesReportingAggregator.sumByCategory(sales);

        assertEquals(2, categorySums.size(), "Map size matches 2");
        assertDoubleEquals(700.0, categorySums.get("Electronics"), "Electronics sum matches");
        assertDoubleEquals(1300.0, categorySums.get("Furniture"), "Furniture sum matches");
    }

    private static void testPartitionHighValueSales() {
        Sale s1 = new Sale("Bob", "Electronics", 150.0);
        Sale s2 = new Sale("Alice", "Furniture", 1000.0);
        Sale s3 = new Sale("Bob", "Electronics", 50.0);
        Sale s4 = new Sale("Charlie", "Furniture", 300.0);

        List<Sale> sales = Arrays.asList(s1, s2, s3, s4);

        // Partition with threshold 200.0
        Map<Boolean, List<Sale>> partitioned = SalesReportingAggregator.partitionHighValueSales(sales, 200.0);

        // High value (>= 200): s2, s4
        List<Sale> highValue = partitioned.get(true);
        assertEquals(2, highValue.size(), "2 high value sales");
        assertEquals(true, highValue.contains(s2) && highValue.contains(s4), "High value contains correct sales");

        // Low value (< 200): s1, s3
        List<Sale> lowValue = partitioned.get(false);
        assertEquals(2, lowValue.size(), "2 low value sales");
        assertEquals(true, lowValue.contains(s1) && lowValue.contains(s3), "Low value contains correct sales");
    }
}
