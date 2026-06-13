# JDBC - Part 1

## Learning Goal

This file covers a focused slice of **JDBC**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `What is JDBC?` | JDBC is the Java API for connecting to relational databases. |
| `Driver` |Driver is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `DriverManager` |DriverManager is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Connection` |Connection represents an active database connection used to create statements and manage transactions. |
| `Statement` |Statement executes static SQL but should not be used with untrusted input. |
| `PreparedStatement` |PreparedStatement precompiles SQL with placeholders and binds values safely. |
| `CallableStatement` |CallableStatement calls stored procedures through JDBC. |
| `ResultSet` | ResultSet represents a database result set, providing access to retrieved data. |
| `Transaction:` | Transaction is a group of related rules in JDBC that groups several related details. |
| `commit` |commit makes current transaction changes permanent. |

## Detailed Notes

### What is JDBC?

JDBC is the Java API for connecting to relational databases.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `What is JDBC?` in one sentence.
- Recognize `What is JDBC?` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `What is JDBC?`.

Tiny example or mental model:

- `PreparedStatement` binds values safely with placeholders.

### Driver

Driver is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Driver` in one sentence.
- Recognize `Driver` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Driver`.

Tiny example or mental model:

- When reading code, ask: what does `Driver` change, allow, reject, or clarify?

### DriverManager

DriverManager is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `DriverManager` in one sentence.
- Recognize `DriverManager` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `DriverManager`.

Tiny example or mental model:

- When reading code, ask: what does `DriverManager` change, allow, reject, or clarify?

### Connection

Connection represents an active database connection used to create statements and manage transactions.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Connection` in one sentence.
- Recognize `Connection` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Connection`.

Tiny example or mental model:

- When reading code, ask: what does `Connection` change, allow, reject, or clarify?

### Statement

Statement executes static SQL but should not be used with untrusted input.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Statement` in one sentence.
- Recognize `Statement` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Statement`.

Tiny example or mental model:

- When reading code, ask: what does `Statement` change, allow, reject, or clarify?

### PreparedStatement

PreparedStatement precompiles SQL with placeholders and binds values safely.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `PreparedStatement` in one sentence.
- Recognize `PreparedStatement` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `PreparedStatement`.

Tiny example or mental model:

- `PreparedStatement` binds values safely with placeholders.

### CallableStatement

CallableStatement calls stored procedures through JDBC.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `CallableStatement` in one sentence.
- Recognize `CallableStatement` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `CallableStatement`.

Tiny example or mental model:

- When reading code, ask: what does `CallableStatement` change, allow, reject, or clarify?

### ResultSet

ResultSet represents a database result set, providing sequential access to retrieved rows.

It matters because it maintains a cursor pointing to its current row of data, which is initially positioned before the first row. You must call `next()` to advance the cursor and retrieve data.

Practical check:

- Define `ResultSet` in one sentence.
- Recognize `ResultSet` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ResultSet`.

Tiny example or mental model:

- When reading code, ask: what does `ResultSet` change, allow, reject, or clarify?

### Transaction:

Transaction is a group of related rules in JDBC that groups several related details.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Transaction:` in one sentence.
- Recognize `Transaction:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Transaction:`.

Tiny example or mental model:

- When reading code, ask: what does `Transaction:` change, allow, reject, or clarify?

### commit

commit makes current transaction changes permanent.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `commit` in one sentence.
- Recognize `commit` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `commit`.

Tiny example or mental model:

- When reading code, ask: what does `commit` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Code Examples

### Querying with PreparedStatement and ResultSet
```java
String sql = "SELECT id, name, email FROM users WHERE status = ?";
try (Connection conn = dataSource.getConnection();
     PreparedStatement stmt = conn.prepareStatement(sql)) {
     
    stmt.setString(1, "ACTIVE");
    try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println("User: " + id + " - " + name);
        }
    }
}
```

### Stored Procedure call with CallableStatement
```java
try (Connection conn = dataSource.getConnection();
     CallableStatement stmt = conn.prepareCall("{call get_user_salary(?, ?)}")) {
     
    stmt.setInt(1, 101); // Input parameter
    stmt.registerOutParameter(2, java.sql.Types.DOUBLE); // Output parameter
    stmt.execute();
    double salary = stmt.getDouble(2);
    System.out.println("Salary: " + salary);
}
```

## Common Mistakes

- **Forgetting to Close Resources**: If Connection, Statement, or ResultSet are not closed (e.g. not using try-with-resources), it can exhaust the database connection pool or cursor limits quickly.
- **SQL Injection with Statement**: Concatenating strings to build SQL statements (e.g., `"SELECT * FROM users WHERE name = '" + name + "'"`) instead of using placeholders (`?`) in a `PreparedStatement`.
- **Reading ResultSet before calling next()**: The cursor is initially positioned before the first row, so calling `rs.getString(1)` without calling `rs.next()` first will throw an `SQLException`.
