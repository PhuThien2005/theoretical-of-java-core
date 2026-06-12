# Comparable and Comparator - Part 1

## Learning Goal

This file covers a focused slice of **Comparable and Comparator**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Comparable` | Comparable defines natural ordering inside the class being compared. |
| `compareTo` |compareTo is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Comparator` | Comparator defines external custom ordering for objects. |
| `compare` |compare is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Natural ordering` |Natural ordering is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Custom ordering` |Custom ordering is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Sort List object` | A List is an ordered collection that can contain duplicates and supports positional access. |
| `Sort by multiple criteria` |Sort by multiple criteria is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Comparator.comparing` | Comparator defines external custom ordering for objects. |
| `thenComparing` |thenComparing is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name. |

## Detailed Notes

### Comparable

Comparable defines natural ordering inside the class being compared.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Comparable` in one sentence.
- Recognize `Comparable` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Comparable`.

Tiny example or mental model:

- When reading code, ask: what does `Comparable` change, allow, reject, or clarify?

### compareTo

compareTo is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `compareTo` in one sentence.
- Recognize `compareTo` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `compareTo`.

Tiny example or mental model:

- When reading code, ask: what does `compareTo` change, allow, reject, or clarify?

### Comparator

Comparator defines external custom ordering for objects.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Comparator` in one sentence.
- Recognize `Comparator` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Comparator`.

Tiny example or mental model:

- When reading code, ask: what does `Comparator` change, allow, reject, or clarify?

### compare

compare is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `compare` in one sentence.
- Recognize `compare` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `compare`.

Tiny example or mental model:

- When reading code, ask: what does `compare` change, allow, reject, or clarify?

### Natural ordering

Natural ordering is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Natural ordering` in one sentence.
- Recognize `Natural ordering` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Natural ordering`.

Tiny example or mental model:

- When reading code, ask: what does `Natural ordering` change, allow, reject, or clarify?

### Custom ordering

Custom ordering is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Custom ordering` in one sentence.
- Recognize `Custom ordering` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Custom ordering`.

Tiny example or mental model:

- When reading code, ask: what does `Custom ordering` change, allow, reject, or clarify?

### Sort List object

A List is an ordered collection that can contain duplicates and supports positional access.

It matters because choosing the wrong data structure changes correctness, performance, and duplicate-handling behavior. A common confusion is memorizing class names without knowing lookup order, equality rules, or iteration behavior.

Practical check:

- Define `Sort List object` in one sentence.
- Recognize `Sort List object` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Sort List object`.

Tiny example or mental model:

- `List<String> names = new ArrayList<>();` stores ordered elements.

### Sort by multiple criteria

Sort by multiple criteria is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Sort by multiple criteria` in one sentence.
- Recognize `Sort by multiple criteria` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Sort by multiple criteria`.

Tiny example or mental model:

- When reading code, ask: what does `Sort by multiple criteria` change, allow, reject, or clarify?

### Comparator.comparing

Comparator defines external custom ordering for objects.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `Comparator.comparing` in one sentence.
- Recognize `Comparator.comparing` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Comparator.comparing`.

Tiny example or mental model:

- When reading code, ask: what does `Comparator.comparing` change, allow, reject, or clarify?

### thenComparing

thenComparing is a specific concept in Comparable and Comparator; learn its Java rule, valid use cases, and failure mode rather than only its name.

Use it to predict the exact Java rule, the allowed form, and the failure mode. Review it with a tiny example instead of memorizing only the label.

Practical check:

- Define `thenComparing` in one sentence.
- Recognize `thenComparing` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `thenComparing`.

Tiny example or mental model:

- When reading code, ask: what does `thenComparing` change, allow, reject, or clarify?

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
