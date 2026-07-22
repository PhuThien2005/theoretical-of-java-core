package no30_regex.practice.log_parser_regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Starter template for a regex log line parser.
 */
public class LogParserRegex {

    // TODO: Define and compile the Pattern matching log lines:
    // Format: "[timestamp] LEVEL [service] message"
    // Capture groups: 1 = timestamp, 2 = LEVEL, 3 = service, 4 = message
    private static final Pattern LOG_PATTERN = null;

    /**
     * Parses a single log line into a LogEntry.
     * Returns null if the line does not match the expected pattern or if line is null.
     */
    public static LogEntry parseLine(String line) {
        // TODO: Implement parsing using LOG_PATTERN and Matcher groups
        return null;
    }
}

/**
 * Structured log data object.
 */
class LogEntry {
    private final String timestamp;
    private final String level;
    private final String service;
    private final String message;

    public LogEntry(String timestamp, String level, String service, String message) {
        this.timestamp = timestamp;
        this.level = level;
        this.service = service;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getLevel() {
        return level;
    }

    public String getService() {
        return service;
    }

    public String getMessage() {
        return message;
    }
}
