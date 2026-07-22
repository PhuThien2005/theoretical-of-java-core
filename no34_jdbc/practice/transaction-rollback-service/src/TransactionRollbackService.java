package no34_jdbc.practice.transaction_rollback_service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Starter template for a JDBC Transaction Rollback manager.
 */
public class TransactionRollbackService {

    /**
     * Executes a list of SQL updates as a single atomic transaction.
     * 
     * Requirements:
     * - Retrieve and store the original auto-commit state of the connection.
     * - Disable auto-commit on the connection (conn.setAutoCommit(false)).
     * - Create a Statement and execute each SQL update in the list.
     * - If all updates succeed, commit the transaction (conn.commit()).
     * - If any SQLException occurs:
     *   - Roll back the transaction (conn.rollback()).
     *   - Re-throw the exception.
     * - In a finally block, restore the original auto-commit state.
     *
     * @param conn the active JDBC database Connection
     * @param sqlStatements list of SQL update statements to execute
     * @throws SQLException on database errors
     */
    public static void executeTransaction(Connection conn, List<String> sqlStatements) throws SQLException {
        // TODO: Implement transaction boundary logic with commit, rollback, and auto-commit restore
    }
}
