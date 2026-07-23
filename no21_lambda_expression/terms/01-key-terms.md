# Lambda Expression Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## lambda

A lambda expression is a compact function-like block used where a functional interface is expected.

Why it matters: It matters because modern Java APIs use function-style pipelines heavily. A common confusion is forgetting which operations are lazy and which operation actually triggers execution.

Common confusion: learners often memorize `lambda` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `n -> n > 0` is a lambda used as a predicate.

## target typing

`target typing` — The compiler infers the type of a lambda expression based on target context.

## method reference

`method reference` — Shorthand syntax (::) for a lambda that simply invokes an existing method.

## variable capture

`variable capture` — Inner/local classes capturing local variables from enclosing scope if they are effectively final.

## effectively final

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `effectively final` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `final int limit = 10;` cannot be reassigned.
