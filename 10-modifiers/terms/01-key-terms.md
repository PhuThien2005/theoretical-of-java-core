# Modifiers in Java Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## access modifier

access modifier is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `access modifier` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `access modifier` change, allow, reject, or clarify?

## non-access modifier

non-access modifier is a specific concept in Modifiers in Java; learn its Java rule, valid use cases, and failure mode rather than only its name.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `non-access modifier` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `non-access modifier` change, allow, reject, or clarify?

## static

Static means the member belongs to the class rather than to one particular object.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `static` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `ClassName.member` accesses a class-level member.

## final

Final means the variable, method, class, or parameter is restricted from later change in a specific way.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `final` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `final int limit = 10;` cannot be reassigned.

## abstract

Abstract means incomplete by design: subclasses or implementations must provide missing behavior.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `abstract` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `abstract` change, allow, reject, or clarify?

## volatile

Volatile gives visibility guarantees for a variable shared between threads, but it does not make compound operations atomic.

Why it matters: It matters because concurrent code can look correct in single-thread tests but fail under timing pressure. A common confusion is assuming visibility, ordering, and atomicity are the same guarantee.

Common confusion: learners often memorize `volatile` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `volatile` change, allow, reject, or clarify?

## transient

Transient marks a field that should be skipped during Java serialization.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `transient` as a word but cannot explain what problem it solves or what rule it changes.

Small example: When reading code, ask: what does `transient` change, allow, reject, or clarify?
