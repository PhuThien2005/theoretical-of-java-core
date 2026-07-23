# Inner Class and Nested Class Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## nested class

`nested class` — A class defined inside another enclosing class, enabling logical grouping.

## static nested class

Static means the member belongs to the class rather than to one particular object.

Why it matters: Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Common confusion: learners often memorize `static nested class` as a word but cannot explain what problem it solves or what rule it changes.

Small example: `ClassName.member` accesses a class-level member.

## inner class

`inner class` — A non-static nested class associated with an instance of its enclosing class.

## local class

`local class` — A class defined inside a method or block, scoped locally to that block.

## anonymous class

`anonymous class` — An unnamed inner class declared and instantiated in a single expression.

## variable capture

`variable capture` — Inner/local classes capturing local variables from enclosing scope if they are effectively final.

