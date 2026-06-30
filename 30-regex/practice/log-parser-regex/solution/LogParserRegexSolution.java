import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reference solution for LogParserRegexSolution.
 * 
 * Regex Capturing Groups:
 * - Parentheses `(...)` define a capturing group.
 * - Group index matches:
 *   - `group(0)` is the entire matching string.
 *   - `group(1)` matches the first set of parentheses, and so on.
 */
public class LogParserRegexSolution {

    // Pattern structure:
    // ^\[([^\]]+)\]        -> group 1: timestamp inside first brackets
    // \s+([A-Z]+)          -> group 2: Log Level (e.g. INFO, WARN)
    // \s+\[([^\]]+)\]      -> group 3: service name inside second brackets
    // \s+(.*)$             -> group 4: log message text
    private static final Pattern LOG_PATTERN = Pattern.compile("^\\[([^\\]]+)\\]\\s+([A-Z]+)\\s+\\[([^\\]]+)\\]\\s+(.*)$");

    public static LogEntry parseLine(String line) {
        if (line == null) {
            return null;
        }

        Matcher matcher = LOG_PATTERN.matcher(line);
        if (matcher.matches()) {
            return new LogEntry(
                matcher.group(1), // timestamp
                matcher.group(2), // level
                matcher.group(3), // service
                matcher.group(4)  // message
            );
        }

        return null;
    }
}

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
