package no25_date_time_api.practice.global_meeting_scheduler;

/**
 * Test runner for GlobalMeetingScheduler.
 */
public class GlobalMeetingSchedulerTest {

    public static void main(String[] args) {
        try {
            testNewYorkToUtc();
            testTokyoToUtc();
            testInvalidInputs();
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
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testNewYorkToUtc() {
        // June 30 is in Daylight Saving Time (EDT, UTC-4) in New York
        // 14:00 EDT = 18:00 UTC
        String result = GlobalMeetingScheduler.formatMeetingTime(
            "2026-06-30 14:00", 
            "America/New_York", 
            "UTC"
        );
        assertEquals("2026-06-30 18:00 UTC", result, "NY to UTC (EDT)");
    }

    private static void testTokyoToUtc() {
        // Tokyo (JST, UTC+9) has no Daylight Saving Time
        // 09:00 JST = 00:00 UTC
        String result = GlobalMeetingScheduler.formatMeetingTime(
            "2026-07-01 09:00", 
            "Asia/Tokyo", 
            "UTC"
        );
        assertEquals("2026-07-01 00:00 UTC", result, "Tokyo to UTC");
    }

    private static void testInvalidInputs() {
        // Invalid date-time format
        assertEquals(null, GlobalMeetingScheduler.formatMeetingTime("invalid-time", "America/New_York", "UTC"), "Invalid date format");
        
        // Invalid zone ID
        assertEquals(null, GlobalMeetingScheduler.formatMeetingTime("2026-06-30 14:00", "Invalid/Zone", "UTC"), "Invalid source zone ID");
        assertEquals(null, GlobalMeetingScheduler.formatMeetingTime("2026-06-30 14:00", "America/New_York", "BadZone"), "Invalid target zone ID");

        // Null parameters
        assertEquals(null, GlobalMeetingScheduler.formatMeetingTime(null, "America/New_York", "UTC"), "Null date");
    }
}
