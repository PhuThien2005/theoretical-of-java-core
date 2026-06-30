/**
 * Reference solution for ChainedExceptionTrackerSolution.
 * 
 * Exception chaining allows you to associate another exception with an exception.
 * The cause parameter in `Exception(String message, Throwable cause)` is used to record the 
 * underlying reason that led to the current exception.
 */
public class ChainedExceptionTrackerSolution {

    public static void performDatabaseQuery() throws DatabaseException {
        throw new DatabaseException("Connection timeout");
    }

    public static void processOrder() throws BusinessException {
        try {
            performDatabaseQuery();
        } catch (DatabaseException e) {
            // Chaining: wrap the low-level DatabaseException inside the high-level BusinessException
            throw new BusinessException("Order processing failed", e);
        }
    }

    /**
     * Traverses the exception chain iteratively by following `getCause()`
     * until we reach the bottom-most exception (which has no further cause).
     */
    public static String getRootCauseMessage(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        
        Throwable current = throwable;
        while (current.getCause() != null) {
            current = current.getCause();
        }
        
        return current.getMessage();
    }
}

/**
 * Custom low-level checked exception representing database failures.
 */
class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }
}

/**
 * Custom high-level checked exception representing business level failures.
 */
class BusinessException extends Exception {
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
