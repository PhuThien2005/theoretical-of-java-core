# Variable Terms

## Term: Scope

### Short Definition

Scope is the region of code where a name can be accessed.

### Why It Matters

Scope prevents temporary variables from leaking into unrelated code.

### Common Confusion

Scope is not the same as lifetime. Scope is about where a variable is visible; lifetime is about how long it exists.

## Term: Lifetime

### Short Definition

Lifetime is how long a variable or object exists.

### Why It Matters

Lifetime helps explain why local variables disappear after method execution and why object fields remain while the object exists.

### Common Confusion

A variable can be out of scope before the object it referenced is collected.

## Term: Magic Number

### Short Definition

A magic number is a literal number used directly in code without a name explaining its meaning.

### Why It Matters

Magic numbers make code harder to read and maintain.

### Example

`MAX_RETRY_COUNT` is clearer than writing `3` in many places.
