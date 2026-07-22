package no34_jdbc.practice.transaction_rollback_service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Reference solution for TransactionRollbackServiceSolution.
 * 
 * JDBC Transactions:
 * - By default, connections operate in auto-commit mode (each statement is committed individually).
 * - Call `setAutoCommit(false)` to initiate a transaction block.
 * - Call `commit()` to persist updates.
 * - Call `rollback()` to discard changes when errors occur.
 * - Always restore the original auto-commit state in a `finally` block.
 */
public class TransactionRollbackServiceSolution {

    public static void executeTransaction(Connection conn, List<String> sqlStatements) throws SQLException {
        if (conn == null || sqlStatements == null || sqlStatements.isEmpty()) {
            return;
        }

        boolean originalAutoCommit = conn.getAutoCommit();
        
        try {
            // Step 1: Disable auto-commit to start transaction
            conn.setAutoCommit(false);

            // Step 2: Execute all statements
            try (Statement stmt = conn.createStatement()) {
                for (String sql : sqlStatements) {
                    stmt.executeUpdate(sql);
                }
            }

            // Step 3: Commit if everything succeeded
            conn.commit();
        } catch (SQLException e) {
            // Step 4: Rollback changes on exception
            try {
                conn.rollback();
            } catch (SQLException re) {
                // Suppress rollback errors or chain them
            }
            throw e; // Re-throw original exception
        } finally {
            // Step 5: Restore original connection configuration
            try {
                conn.setAutoCommit(originalAutoCommit);
            } catch (SQLException e) {
                // Ignore
            }
        }
    }
}
