# JDBC Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## driver

A database driver is a software library (implementing `java.sql.Driver`) that translates generic JDBC API calls into the proprietary network protocol of a specific database engine (e.g. PostgreSQL, MySQL, Oracle).

* **Why it matters**: It abstracts database-specific communication, allowing a Java application to switch databases simply by changing the driver class and connection URL.
* **Common confusion**: Developers think the driver is part of the standard JDK. It is actually a third-party dependency (e.g. JDBC jar file) that must be added to the runtime classpath.
* **Small example**:
  ```java
  // Load PostgreSQL Driver dynamically
  Class.forName("org.postgresql.Driver");
  ```

## Connection

A `java.sql.Connection` object represents a physical database session/socket through which SQL statements are executed and transactions are committed or rolled back.

* **Why it matters**: It is the control anchor for transactional behavior. You disable auto-commit and call commit or rollback on this object.
* **Common confusion**: Believing a `Connection` is lightweight. Creating a connection requires establishing a network socket and authenticating, which is why they should be pooled and reused.
* **Small example**:
  ```java
  try (Connection conn = dataSource.getConnection()) {
      conn.setAutoCommit(false);
      // Run queries...
      conn.commit();
  }
  ```

## PreparedStatement

A `java.sql.PreparedStatement` is a precompiled SQL statement object that accepts placeholders (`?`) for input parameters, separating query syntax from data.

* **Why it matters**: It prevents SQL injection vulnerabilities by treating parameters strictly as literals and improves performance by leveraging cached query plans.
* **Common confusion**: Thinking that `PreparedStatement` compiles the SQL inside Java. The compilation and optimization happen entirely on the database server.
* **Small example**:
  ```java
  String sql = "SELECT * FROM products WHERE price > ?";
  PreparedStatement pstmt = conn.prepareStatement(sql);
  pstmt.setDouble(1, 19.99); // Safe parameter binding
  ```

## ResultSet

A `java.sql.ResultSet` represents the tabular output stream returned by a database query, maintaining a cursor pointing to the current row of data.

* **Why it matters**: It provides sequential read access to database records, allowing values to be retrieved by column index or label.
* **Common confusion**: Attempting to read values from a new `ResultSet` immediately. The cursor is positioned *before* the first row, so you must call `next()` to advance the cursor before fetching columns.
* **Small example**:
  ```java
  try (ResultSet rs = stmt.executeQuery("SELECT name FROM users")) {
      while (rs.next()) {
          System.out.println(rs.getString("name"));
      }
  }
  ```

## transaction

A database transaction is a logical unit of work grouping multiple SQL modifications together that complies with ACID properties (Atomicity, Consistency, Isolation, Durability).

* **Why it matters**: Guarantees data integrity by ensuring that either all changes are permanently saved (`commit`) or all changes are reverted (`rollback`) if an error occurs.
* **Common confusion**: Assuming transactions are active automatically. By default, JDBC connections are in auto-commit mode, which executes each statement as an independent transaction.
* **Small example**:
  ```java
  conn.setAutoCommit(false); // Begin transaction
  // Multiple database updates...
  conn.commit(); // End transaction
  ```

## SQL injection

SQL injection is a security vulnerability where untrusted user input is directly concatenated into a SQL statement, altering the query's syntax tree and executing unauthorized commands.

* **Why it matters**: An attacker can bypass authentication, read or extract database tables, or destroy data on the server.
* **Common confusion**: Thinking that escaping quotes manually is a reliable defense against SQL injection. Safe parameterized inputs (via `PreparedStatement`) is the only robust solution.
* **Small example**:
  ```java
  // UNSAFE: Dynamic SQL injection target
  String sql = "SELECT * FROM users WHERE user = '" + input + "'";
  ```

## DataSource

A `javax.sql.DataSource` is the standard Java interface for obtaining database connections, serving as a clean alternative to the legacy `DriverManager` class.

* **Why it matters**: It abstracts connection properties (URLs, credentials) and is typically configured to manage a connection pool behind the scenes, improving application performance.
* **Common confusion**: Thinking `DataSource` is a connection pool itself. It is merely a factory interface; concrete classes like `HikariDataSource` implement it to provide pooling capabilities.
* **Small example**:
  ```java
  HikariDataSource ds = new HikariDataSource();
  ds.setJdbcUrl("jdbc:postgresql://localhost/db");
  Connection conn = ds.getConnection(); // Gets connection from the pool
  ```
