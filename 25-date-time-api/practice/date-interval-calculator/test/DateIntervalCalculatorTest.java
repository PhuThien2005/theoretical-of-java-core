import java.time.LocalDate;

/**
 * Test runner for DateIntervalCalculator.
 */
public class DateIntervalCalculatorTest {

    public static void main(String[] args) {
        try {
            testSameWeekInterval();
            testWeekendCrossing();
            testSingleDayIntervals();
            testInvalidIntervals();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(long expected, long actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testSameWeekInterval() {
        // Monday (June 1, 2026) to Wednesday (June 3, 2026) -> 3 days: Mon, Tue, Wed
        LocalDate start = LocalDate.of(2026, 6, 1);
        LocalDate end = LocalDate.of(2026, 6, 3);
        assertEquals(3, DateIntervalCalculator.calculateWorkingDays(start, end), "Mon to Wed same week");
    }

    private static void testWeekendCrossing() {
        // Friday (June 5, 2026) to Tuesday (June 9, 2026) -> 3 days: Fri, Mon, Tue (skips Sat, Sun)
        LocalDate start = LocalDate.of(2026, 6, 5);
        LocalDate end = LocalDate.of(2026, 6, 9);
        assertEquals(3, DateIntervalCalculator.calculateWorkingDays(start, end), "Fri to Tue crossing weekend");
    }

    private static void testSingleDayIntervals() {
        // Mon to Mon -> 1 day
        LocalDate mon = LocalDate.of(2026, 6, 1);
        assertEquals(1, DateIntervalCalculator.calculateWorkingDays(mon, mon), "Mon to Mon");

        // Sun to Sun -> 0 days
        LocalDate sun = LocalDate.of(2026, 6, 7);
        assertEquals(0, DateIntervalCalculator.calculateWorkingDays(sun, sun), "Sun to Sun");
    }

    private static void testInvalidIntervals() {
        LocalDate start = LocalDate.of(2026, 6, 9);
        LocalDate end = LocalDate.of(2026, 6, 5);
        
        // Start date is after End date -> 0 days
        assertEquals(0, DateIntervalCalculator.calculateWorkingDays(start, end), "Start after End");

        // Null checks -> 0 days
        assertEquals(0, DateIntervalCalculator.calculateWorkingDays(null, end), "Null start");
        assertEquals(0, DateIntervalCalculator.calculateWorkingDays(start, null), "Null end");
    }
}
