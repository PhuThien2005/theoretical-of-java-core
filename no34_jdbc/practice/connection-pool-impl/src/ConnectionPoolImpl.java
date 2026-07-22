package no34_jdbc.practice.connection_pool_impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Starter template for a basic database connection pool prototype.
 */
public class ConnectionPoolImpl {

    private final List<Connection> freeConnections = new ArrayList<>();
    private final List<Connection> busyConnections = new ArrayList<>();
    private final int maxConnections;

    public ConnectionPoolImpl(int initSize, int maxConnections) {
        this.maxConnections = maxConnections;
        // TODO: Pre-warm the pool by adding initSize connections to freeConnections
    }

    /**
     * Borrows a connection from the pool.
     */
    public synchronized Connection getConnection() throws SQLException {
        // TODO: Implement borrowing logic
        return null;
    }

    /**
     * Returns a borrowed connection back to the pool.
     */
    public synchronized void releaseConnection(Connection conn) {
        // TODO: Return connection from busyConnections to freeConnections
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
                    return proxy == args[0]; // Safe identity comparison preventing NPE
                } else if ("hashCode".equals(name)) {
                    return System.identityHashCode(proxy);
                }
                return null;
            }
        );
    }
}
