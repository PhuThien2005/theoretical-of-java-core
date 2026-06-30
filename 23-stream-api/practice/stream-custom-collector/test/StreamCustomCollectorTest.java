import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

/**
 * Test runner for StreamCustomCollector.
 */
public class StreamCustomCollectorTest {

    public static void main(String[] args) {
        try {
            testZeroDeviation();
            testCalculatedDeviation();
            testEmptyOrSingleElement();
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

    private static void testZeroDeviation() {
        List<Double> values = Arrays.asList(10.0, 10.0, 10.0, 10.0);
        double stdDev = values.stream().collect(StreamCustomCollector.toStandardDeviation());
        assertEquals(0.0, stdDev, "Standard deviation of constant values must be 0.0");
    }

    private static void testCalculatedDeviation() {
        // Values: 2, 4, 4, 4, 5, 5, 7, 9
        // Mean = (2+4+4+4+5+5+7+9)/8 = 40/8 = 5.0
        // Variance = [(2-5)^2 + 3*(4-5)^2 + 2*(5-5)^2 + (7-5)^2 + (9-5)^2]/8
        //          = [9 + 3*1 + 0 + 4 + 16]/8 = [9 + 3 + 4 + 16]/8 = 32/8 = 4.0
        // StdDev = sqrt(4.0) = 2.0
        List<Double> values = Arrays.asList(2.0, 4.0, 4.0, 4.0, 5.0, 5.0, 7.0, 9.0);
        double stdDev = values.stream().collect(StreamCustomCollector.toStandardDeviation());
        assertEquals(2.0, stdDev, "Calculated standard deviation matches 2.0");
    }

    private static void testEmptyOrSingleElement() {
        List<Double> empty = Arrays.asList();
        double stdDevEmpty = empty.stream().collect(StreamCustomCollector.toStandardDeviation());
        assertEquals(0.0, stdDevEmpty, "Empty list std dev should be 0.0");

        List<Double> single = Arrays.asList(42.0);
        double stdDevSingle = single.stream().collect(StreamCustomCollector.toStandardDeviation());
        assertEquals(0.0, stdDevSingle, "Single element std dev should be 0.0");
    }
}
