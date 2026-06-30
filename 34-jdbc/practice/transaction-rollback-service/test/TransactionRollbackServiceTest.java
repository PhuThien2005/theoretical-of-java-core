import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * Test runner for TransactionRollbackService using JDK dynamic proxies.
 */
public class TransactionRollbackServiceTest {

    public static void main(String[] args) {
        try {
            testSuccessfulTransaction();
            testFailedTransactionRollback();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testSuccessfulTransaction() throws SQLException {
        boolean[] commitCalled = {false};
        boolean[] rollbackCalled = {false};
        boolean[] autoCommitState = {true}; // Starts at true (default JDBC state)
        int[] autoCommitStateChanges = {0};

        // Create a mocked Statement proxy
        Statement stmtProxy = (Statement) Proxy.newProxyInstance(
            Statement.class.getClassLoader(),
            new Class<?>[] { Statement.class },
            (proxy, method, args) -> {
                if ("executeUpdate".equals(method.getName())) {
                    return 1; // successful update
                }
                if ("close".equals(method.getName())) {
                    return null;
                }
                return null;
            }
        );

        // Create a mocked Connection proxy
        Connection connProxy = (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[] { Connection.class },
            (proxy, method, args) -> {
                String methodName = method.getName();
                if ("getAutoCommit".equals(methodName)) {
                    return autoCommitState[0];
                } else if ("setAutoCommit".equals(methodName)) {
                    boolean state = (Boolean) args[0];
                    if (state != autoCommitState[0]) {
                        autoCommitState[0] = state;
                        autoCommitStateChanges[0]++;
                    }
                    return null;
                } else if ("commit".equals(methodName)) {
                    commitCalled[0] = true;
                    return null;
                } else if ("rollback".equals(methodName)) {
                    rollbackCalled[0] = true;
                    return null;
                } else if ("createStatement".equals(methodName)) {
                    return stmtProxy;
                }
                return null;
            }
        );

        List<String> updates = Arrays.asList("INSERT INTO users VALUES (1)", "UPDATE stats SET val = 2");
        TransactionRollbackService.executeTransaction(connProxy, updates);

        assertTrue(commitCalled[0], "commit() must be called on successful transaction");
        assertFalse(rollbackCalled[0], "rollback() must NOT be called on successful transaction");
        assertTrue(autoCommitState[0], "Original auto-commit state (true) must be restored");
        assertEquals(2, autoCommitStateChanges[0], "Auto-commit should change twice (disabled then restored)");
    }

    private static void testFailedTransactionRollback() throws SQLException {
        boolean[] commitCalled = {false};
        boolean[] rollbackCalled = {false};
        boolean[] autoCommitState = {true};

        Statement stmtProxy = (Statement) Proxy.newProxyInstance(
            Statement.class.getClassLoader(),
            new Class<?>[] { Statement.class },
            (proxy, method, args) -> {
                if ("executeUpdate".equals(method.getName())) {
                    throw new SQLException("Database execution error simulated!");
                }
                return null;
            }
        );

        Connection connProxy = (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[] { Connection.class },
            (proxy, method, args) -> {
                String methodName = method.getName();
                if ("getAutoCommit".equals(methodName)) {
                    return autoCommitState[0];
                } else if ("setAutoCommit".equals(methodName)) {
                    autoCommitState[0] = (Boolean) args[0];
                    return null;
                } else if ("commit".equals(methodName)) {
                    commitCalled[0] = true;
                    return null;
                } else if ("rollback".equals(methodName)) {
                    rollbackCalled[0] = true;
                    return null;
                } else if ("createStatement".equals(methodName)) {
                    return stmtProxy;
                }
                return null;
            }
        );

        List<String> updates = Arrays.asList("INSERT INTO users VALUES (1)");
        try {
            TransactionRollbackService.executeTransaction(connProxy, updates);
            throw new AssertionError("executeTransaction should re-throw SQLException");
        } catch (SQLException e) {
            // Expected exception
        }

        assertFalse(commitCalled[0], "commit() must NOT be called on failed transaction");
        assertTrue(rollbackCalled[0], "rollback() must be called on failed transaction");
        assertTrue(autoCommitState[0], "Original auto-commit state (true) must be restored");
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }
}
