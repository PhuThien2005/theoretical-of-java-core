package no12_exception_handling.practice.chained_exception_tracker;

/**
 * A class demonstrating chained exceptions and exception unwrapping.
 */
public class ChainedExceptionTracker {

    /**
     * Simulates a low-level database query that fails.
     * Always throws a DatabaseException with message "Connection timeout".
     */
    public static void performDatabaseQuery() throws DatabaseException {
        // TODO: Throw a DatabaseException with message "Connection timeout"
    }

    /**
     * Simulates high-level order processing.
     * It calls performDatabaseQuery(), catches DatabaseException,
     * wraps it inside a BusinessException with message "Order processing failed",
     * and throws the BusinessException.
     */
    public static void processOrder() throws BusinessException {
        // TODO: Call performDatabaseQuery(). Catch DatabaseException, wrap it in BusinessException, and throw it.
    }

    /**
     * Recursively or iteratively traverses the exception chain (using getCause())
     * to find the root cause (the bottom-most exception) and returns its message.
     * If the exception has no cause, returns its own message.
     *
     * @param throwable the high-level exception
     * @return the message of the root cause exception
     */
    public static String getRootCauseMessage(Throwable throwable) {
        // TODO: Traverse getCause() to find the root exception and return its message.
        return null;
    }
}

/**
 * Custom low-level checked exception representing database failures.
 */
class DatabaseException extends Exception {
    public DatabaseException(String message) {
        // TODO: Call superclass constructor
    }
}

/**
 * Custom high-level checked exception representing business level failures.
 */
class BusinessException extends Exception {
    // TODO: Implement constructor BusinessException(String message, Throwable cause) to allow chaining
    public BusinessException(String message, Throwable cause) {
        super(null, null); // dummy call to compile
    }
}
