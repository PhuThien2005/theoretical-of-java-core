# Control Flow Terms

This file expands terms that appear throughout control flow explanations.

## Control Flow

Control flow is the order in which statements execute. Basic Java code runs top to bottom, but branches, loops, exceptions, and method calls can change that order.

## Branch

A branch is a path selected by a condition or a value. `if`, `else`, and `switch` create branches.

## Condition

A condition is a boolean expression used to decide whether a branch or loop should run.

Java conditions must be `boolean`. Unlike some languages, Java does not use `0` as false or nonzero numbers as true.

## Iteration

An iteration is one pass through a loop body.

If a loop prints five numbers, it usually has five iterations.

## Loop Body

The loop body is the block of code repeated by a loop.

## Loop Termination

Loop termination is the moment a loop stops. It can happen because the condition becomes false, a `break` runs, a `return` exits the method, or an exception interrupts execution.

## Infinite Loop

An infinite loop is a loop that does not naturally stop. It may be intentional, such as a server waiting for requests, or accidental, such as forgetting to update a counter.

## Fall-Through

Fall-through happens in traditional `switch` statements when one `case` continues into the next because there is no `break`, `return`, or other exit.

## Guard Clause

A guard clause handles a special or invalid case early, often with `return`, so the main logic can stay less nested.

```java
if (user == null) {
    return;
}
```

## Early Exit

Early exit means leaving a block, loop, or method before reaching its natural end. `break`, `continue`, and `return` are early-exit tools.

## Exhaustive

Exhaustive means every possible case is covered. A switch expression must be exhaustive because it has to produce a value.

## Label

A label names a statement, commonly an outer loop. Labeled `break` and labeled `continue` can target that named loop.

Labels are useful in nested loops, but overusing them can make code feel jumpy and harder to follow.
