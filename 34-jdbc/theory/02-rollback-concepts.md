# JDBC - Part 2

## Learning Goal

This file covers a focused slice of **JDBC**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `rollback` |rollback cancels current transaction changes since the last commit. |
| `setAutoCommit` | A Set is a collection that rejects duplicates according to equality rules. |
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

A Set is a collection that rejects duplicates according to equality rules.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

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
