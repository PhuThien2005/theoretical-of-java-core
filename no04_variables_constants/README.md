# 04 - Variables And Constants

## What You Should Learn

By the end of this topic, you should be able to:

- Explain what a variable is.
- Distinguish local, instance, and static variables.
- Explain what `final` means for variables.
- Understand constants and naming conventions.
- Understand default values for fields vs local variables.
- Use `var` correctly.
- Explain variable scope and lifetime at a beginner level.
- Connect variables to the stack/heap mental model.

## Study Order

1. [Variable Categories](theory/01-variable-categories.md)
2. [Final Variables And Constants](theory/02-final-and-constants.md)
3. [Default Values, Scope, And Lifetime](theory/03-default-scope-lifetime.md)
4. [`var` And Type Inference](theory/04-var-type-inference.md)

## Term Notes

- [Variable Terms](terms/01-variable-terms.md)

## Big Picture

```mermaid
flowchart TD
    A[Variables in Java] --> B[Local variables]
    A --> C[Instance variables]
    A --> D[Static variables]
    A --> E[Final variables]
    E --> F[Constants]
    B --> G[Method/block scope]
    C --> H[Belong to object]
    D --> I[Belong to class]
```

## Self-Check

- Why must a local variable be definitely assigned before use, while instance and static fields receive default values?
- What is the difference in lifetime, memory location, and storage mechanisms between local variables, instance variables, and static variables?
- Why does `final` prevent re-assignment, and how does it enable compiler optimizations like inlining and constant folding?
- Why are constants in Java typically declared as `static final`?
- Why is local variable type inference (`var`) restricted to local variables and not allowed for fields or method parameter/return types?
- How does scope limit variable visibility, and how does scope differ from variable lifetime?

## Anki Cards

- [Basic cards](anki/basic.tsv)
- [Basic Extra cards](anki/basic-extra.tsv)
- [Cloze cards](anki/cloze.tsv)
- [Code Question cards](anki/code-question.tsv)

## My Notes

-

## Reference Links

- https://docs.oracle.com/javase/tutorial/java/nutsandbolts/variables.html
