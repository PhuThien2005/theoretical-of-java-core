import java.sql.Connection;
import java.sql.SQLException;

/**
 * Test runner for ConnectionPoolImpl.
 */
public class ConnectionPoolImplTest {

    public static void main(String[] args) {
        try {
            testPoolInitialization();
            testBorrowAndRelease();
            testExpansionToMaxLimit();
            testLimitExceededThrows();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testPoolInitialization() {
        ConnectionPoolImpl pool = new ConnectionPoolImpl(2, 5);
        assertEquals(2, pool.getFreeCount(), "Pre-warmed free connections");
        assertEquals(0, pool.getBusyCount(), "Initially 0 busy connections");
    }

    private static void testBorrowAndRelease() throws SQLException {
        ConnectionPoolImpl pool = new ConnectionPoolImpl(2, 5);

        // Borrow
        Connection conn = pool.getConnection();
        assertEquals(1, pool.getFreeCount(), "Free count drops to 1");
        assertEquals(1, pool.getBusyCount(), "Busy count increases to 1");

        // Release
        pool.releaseConnection(conn);
        assertEquals(2, pool.getFreeCount(), "Free count restored to 2");
        assertEquals(0, pool.getBusyCount(), "Busy count drops to 0");
    }

    private static void testExpansionToMaxLimit() throws SQLException {
        // Init = 2, Max = 4
        ConnectionPoolImpl pool = new ConnectionPoolImpl(2, 4);

        // Borrow 2 (depletes initial pre-warmed connections)
        Connection c1 = pool.getConnection();
        Connection c2 = pool.getConnection();
        assertEquals(0, pool.getFreeCount(), "Idle count 0");
        assertEquals(2, pool.getBusyCount(), "Active count 2");

        // Borrow 3rd (forces dynamic creation of a new connection)
        Connection c3 = pool.getConnection();
        assertEquals(0, pool.getFreeCount(), "Idle count remains 0");
        assertEquals(3, pool.getBusyCount(), "Active count increases to 3");

        // Borrow 4th (reaches max limit)
        Connection c4 = pool.getConnection();
        assertEquals(4, pool.getBusyCount(), "Active count reaches max capacity (4)");
    }

    private static void testLimitExceededThrows() throws SQLException {
        ConnectionPoolImpl pool = new ConnectionPoolImpl(2, 2);

        // Borrow 2
        pool.getConnection();
        pool.getConnection();

        // Try borrowing 3rd (limit reached)
        try {
            pool.getConnection();
            throw new AssertionError("Borrowing beyond max limit must throw SQLException");
        } catch (SQLException e) {
            // Expected
        }
    }
}
