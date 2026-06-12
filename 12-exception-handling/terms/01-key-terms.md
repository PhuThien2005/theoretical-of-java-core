# Exception Handling Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## checked exception

A checked exception must be handled or declared by compiler rules.

Why it matters: It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Common confusion: learners often memorize `checked exception` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `checked exception` change, allow, reject, or clarify?

## unchecked exception

An unchecked exception is not required to be caught or declared.

Why it matters: It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Common confusion: learners often memorize `unchecked exception` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `unchecked exception` change, allow, reject, or clarify?

## runtime exception

An exception represents an abnormal condition that a program may catch or propagate.

Why it matters: It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Common confusion: learners often memorize `runtime exception` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `runtime exception` change, allow, reject, or clarify?

## finally

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `finally` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `final int limit = 10;` cannot be reassigned.

## exception propagation

An exception represents an abnormal condition that a program may catch or propagate.

Why it matters: It matters because exception behavior decides whether failures are handled locally, propagated, or allowed to stop the program. A common confusion is treating every exception the same instead of separating recoverable conditions from programming bugs.

Common confusion: learners often memorize `exception propagation` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `exception propagation` change, allow, reject, or clarify?

## try-with-resources

Try-with-resources automatically closes resources that implement AutoCloseable.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `try-with-resources` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `try { ... } catch (IOException ex) { ... }` handles a specific failure path.
