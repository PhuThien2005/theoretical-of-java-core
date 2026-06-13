# JDBC - Part 2

## Learning Goal

This file covers a focused slice of **JDBC**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `rollback` |rollback cancels current transaction changes since the last commit. |
| `setAutoCommit` | setAutoCommit configures whether SQL statements are committed automatically or grouped into transactions. |
| `Batch processing` |Batch processing is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `SQL Injection` | SQL injection happens when untrusted input changes the meaning of a SQL command. |
| `Basic Connection Pool` |Basic Connection Pool is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `DataSource` |DataSource is a configurable factory for database connections, often backed by a pool. |
| `CRUD using JDBC` | JDBC is the Java API for connecting to relational databases. |

## Detailed Notes

### rollback

rollback cancels current transaction changes since the last commit.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `rollback` in one sentence.
- Recognize `rollback` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `rollback`.

Tiny example or mental model:

- When reading code, ask: what does `rollback` change, allow, reject, or clarify?

### setAutoCommit

setAutoCommit determines whether statements are executed in auto-commit mode (which commits each SQL statement immediately) or manual-commit mode (which groups statements into transactions).

It matters because for multi-step transactional operations (e.g., bank transfer), auto-commit must be disabled (`setAutoCommit(false)`) to ensure atomic execution.

Practical check:

- Define `setAutoCommit` in one sentence.
- Recognize `setAutoCommit` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `setAutoCommit`.

Tiny example or mental model:

- When reading code, ask: what does `setAutoCommit` change, allow, reject, or clarify?

### Batch processing

Batch processing is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Batch processing` in one sentence.
- Recognize `Batch processing` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Batch processing`.

Tiny example or mental model:

- When reading code, ask: what does `Batch processing` change, allow, reject, or clarify?

### SQL Injection

SQL injection happens when untrusted input changes the meaning of a SQL command.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `SQL Injection` in one sentence.
- Recognize `SQL Injection` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `SQL Injection`.

Tiny example or mental model:

- When reading code, ask: what does `SQL Injection` change, allow, reject, or clarify?

### Basic Connection Pool

Basic Connection Pool is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Basic Connection Pool` in one sentence.
- Recognize `Basic Connection Pool` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic Connection Pool`.

Tiny example or mental model:

- When reading code, ask: what does `Basic Connection Pool` change, allow, reject, or clarify?

### DataSource

DataSource is a configurable factory for database connections, often backed by a pool.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `DataSource` in one sentence.
- Recognize `DataSource` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `DataSource`.

Tiny example or mental model:

- When reading code, ask: what does `DataSource` change, allow, reject, or clarify?

### CRUD using JDBC

JDBC is the Java API for connecting to relational databases.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `CRUD using JDBC` in one sentence.
- Recognize `CRUD using JDBC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `CRUD using JDBC`.

Tiny example or mental model:

- `PreparedStatement` binds values safely with placeholders.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Code Examples

### Transaction Management (commit and rollback)
```java
Connection conn = null;
try {
    conn = dataSource.getConnection();
    conn.setAutoCommit(false); // Enable manual transaction control
    
    try (PreparedStatement withdraw = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
         PreparedStatement deposit = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?")) {
         
        withdraw.setDouble(1, 100.0);
        withdraw.setInt(2, 1);
        withdraw.executeUpdate();
        
        deposit.setDouble(1, 100.0);
        deposit.setInt(2, 2);
        deposit.executeUpdate();
        
        conn.commit(); // Commit if both succeed
    }
} catch (Exception e) {
    if (conn != null) {
        try {
            conn.rollback(); // Rollback on error
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
} finally {
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
```

### Batch Processing
```java
String sql = "INSERT INTO logs (message, created_at) VALUES (?, ?)";
try (Connection conn = dataSource.getConnection();
     PreparedStatement stmt = conn.prepareStatement(sql)) {
     
    conn.setAutoCommit(false);
    
    for (int i = 0; i < 1000; i++) {
        stmt.setString(1, "Log message " + i);
        stmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
        stmt.addBatch();
        
        if (i % 100 == 0) {
            stmt.executeBatch(); // Execute every 100 items
        }
    }
    stmt.executeBatch(); // Execute remaining items
    conn.commit();
}
```

## Common Mistakes

- **Assuming Auto-Commit is Off by Default**: By default, new database connections are in auto-commit mode. You must explicitly call `conn.setAutoCommit(false)` to begin a transaction.
- **Forgetting to Commit**: If auto-commit is disabled and you execute insert/update statements, you must call `conn.commit()`. If you don't call it, the database will discard the changes when the connection is closed or garbage-collected.
- **Not Handling Rollback Exceptions**: If an error occurs during transaction execution, calling `conn.rollback()` can also throw a `SQLException`. This should be handled properly in a nested try-catch block inside the main catch block.
