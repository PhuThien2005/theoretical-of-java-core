package no30_regex.practice.log_parser_regex;

/**
 * Test runner for LogParserRegex.
 */
public class LogParserRegexTest {

    public static void main(String[] args) {
        try {
            testValidLogParsing();
            testInvalidLogParsing();
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

    private static void testValidLogParsing() {
        String logLine = "[2026-06-30 14:22:10] INFO [UserService] User login successful for username=alice";
        LogEntry entry = LogParserRegex.parseLine(logLine);

        if (entry == null) {
            throw new AssertionError("Failed to parse a valid log line");
        }

        assertEquals("2026-06-30 14:22:10", entry.getTimestamp(), "Timestamp group");
        assertEquals("INFO", entry.getLevel(), "LogLevel group");
        assertEquals("UserService", entry.getService(), "Service/Logger name group");
        assertEquals("User login successful for username=alice", entry.getMessage(), "Message group");
    }

    private static void testInvalidLogParsing() {
        // Missing brackets around timestamp
        assertEquals(null, LogParserRegex.parseLine("2026-06-30 14:22:10 INFO [UserService] msg"), "Invalid timestamp brackets");

        // Missing log level
        assertEquals(null, LogParserRegex.parseLine("[2026-06-30] [UserService] msg"), "Missing Level");

        // Null line
        assertEquals(null, LogParserRegex.parseLine(null), "Null parsing");
    }
}
