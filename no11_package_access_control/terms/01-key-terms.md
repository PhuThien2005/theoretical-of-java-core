# Package and Access Control Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## package

A package groups related classes and gives them a namespace.

## import

`import` — import statement allows referencing classes from another package using simple names instead of fully qualified names.

## static import

Static means the member belongs to the class rather than to one particular object.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `static import` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `ClassName.member` accesses a class-level member.

## default package

A package groups related classes and gives them a namespace.

## classpath

Classpath tells the JVM and compiler where to find classes and JARs.

## module path

Module path is the module-system-aware alternative to classpath for named modules.

