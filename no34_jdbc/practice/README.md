# Practice Exercises: JDBC (Java Database Connectivity)

This folder contains hands-on practice exercises to reinforce your understanding of JDBC APIs, transaction management (commit/rollback safety), and database connection pool mechanics.

## Exercises

### 1. Transaction Rollback Service (`transaction-rollback-service`)
In enterprise database applications, multiple updates must be treated as a single atomic transaction. If any update fails, the transaction must be rolled back completely to prevent data corruption.
- **Goal**: Implement a `TransactionRollbackService` that manages transaction boundaries: disabling auto-commit, executing database updates, and performing rollback on exceptions.

#### Directory Structure
- [TransactionRollbackService.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no34_jdbc/practice/transaction-rollback-service/src/TransactionRollbackService.java)
- [TransactionRollbackServiceTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no34_jdbc/practice/transaction-rollback-service/test/TransactionRollbackServiceTest.java)
- [TransactionRollbackService.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no34_jdbc/practice/transaction-rollback-service/solution/TransactionRollbackService.java)

---

### 2. Connection Pool Implementation (`connection-pool-impl`)
Creating database connections is expensive. Connection Pools maintain a cache of active connections, reuse them, and throttle connection usage.
- **Goal**: Implement a simple connection pool prototype `ConnectionPoolImpl` that pre-warms a set of connections, allows borrowing and returning, and enforces maximum limits.

#### Directory Structure
- [ConnectionPoolImpl.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no34_jdbc/practice/connection-pool-impl/src/ConnectionPoolImpl.java)
- [ConnectionPoolImplTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no34_jdbc/practice/connection-pool-impl/test/ConnectionPoolImplTest.java)
- [ConnectionPoolImpl.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no34_jdbc/practice/connection-pool-impl/solution/ConnectionPoolImpl.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no34_jdbc
```
