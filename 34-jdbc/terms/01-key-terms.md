# JDBC Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## driver

driver is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `driver` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `driver` change, allow, reject, or clarify?

## Connection

Connection represents an active database connection used to create statements and manage transactions.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `Connection` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `Connection` change, allow, reject, or clarify?

## PreparedStatement

PreparedStatement precompiles SQL with placeholders and binds values safely.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `PreparedStatement` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `PreparedStatement` binds values safely with placeholders.

## ResultSet

A Set is a collection that rejects duplicates according to equality rules.

Why it matters: It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Common confusion: learners often memorize `ResultSet` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `ResultSet` change, allow, reject, or clarify?

## transaction

transaction is a specific concept in JDBC; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `transaction` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `transaction` change, allow, reject, or clarify?

## SQL injection

SQL injection happens when untrusted input changes the meaning of a SQL command.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `SQL injection` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `SQL injection` change, allow, reject, or clarify?

## DataSource

DataSource is a configurable factory for database connections, often backed by a pool.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `DataSource` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `DataSource` change, allow, reject, or clarify?
