import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reference solution for ConnectionPoolImplSolution.
 */
public class ConnectionPoolImplSolution {

    private final List<Connection> freeConnections = new ArrayList<>();
    private final List<Connection> busyConnections = new ArrayList<>();
    private final int maxConnections;

    public ConnectionPoolImplSolution(int initSize, int maxConnections) {
        if (initSize < 0 || maxConnections < initSize) {
            throw new IllegalArgumentException("Invalid pool sizes configuration");
        }
        this.maxConnections = maxConnections;
        
        for (int i = 0; i < initSize; i++) {
            freeConnections.add(createMockConnection());
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (!freeConnections.isEmpty()) {
            Connection conn = freeConnections.remove(0);
            busyConnections.add(conn);
            return conn;
        }

        if (freeConnections.size() + busyConnections.size() < maxConnections) {
            Connection conn = createMockConnection();
            busyConnections.add(conn);
            return conn;
        }

        throw new SQLException("Database connection limit reached. No free connections available.");
    }

    public synchronized void releaseConnection(Connection conn) {
        if (conn == null) {
            return;
        }
        if (busyConnections.remove(conn)) {
            freeConnections.add(conn);
        }
    }

    public synchronized int getFreeCount() {
        return freeConnections.size();
    }

    public synchronized int getBusyCount() {
        return busyConnections.size();
    }

    protected Connection createMockConnection() {
        return (Connection) java.lang.reflect.Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[] { Connection.class },
            (proxy, method, args) -> {
                String name = method.getName();
                if ("equals".equals(name)) {
                    return proxy == args[0]; // Identity comparison
                } else if ("hashCode".equals(name)) {
                    return System.identityHashCode(proxy);
                }
                return null;
            }
        );
    }
}
