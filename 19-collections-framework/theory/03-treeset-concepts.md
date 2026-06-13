# Collections Framework - Part 3

## Learning Goal

This file covers a focused slice of **Collections Framework**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `TreeSet` | A Set is a collection that rejects duplicates according to equality rules. |
| `SortedSet` | A Set is a collection that rejects duplicates according to equality rules. |
| `NavigableSet` | A Set is a collection that rejects duplicates according to equality rules. |
| `When to use Set?` | A Set is a collection that rejects duplicates according to equality rules. |
| `Duplicate removal mechanism` |Duplicate removal mechanism is a specific concept in Collections Framework; learn its Java rule, valid use cases, and failure mode rather than only its name. |
| `Role of equals() and hashCode()` | equals() defines logical equality between objects. |
| `PriorityQueue` |PriorityQueue removes elements by priority rather than insertion order. |
| `ArrayDeque` |ArrayDeque is a resizable-array Deque often preferred for stack or queue behavior. |

## Detailed Notes

### TreeSet

`TreeSet` is a `NavigableSet` implementation backed by a `TreeMap` instance.
- **Ordering**: Sorted according to natural ordering (implementing `Comparable`) or a custom `Comparator` passed at construction.
- **Complexity**: O(log N) for core operations (`add`, `remove`, `contains`).
- **Restrictions**: Does not allow `null` elements (throws `NullPointerException` because it needs to sort/compare elements).

### SortedSet & NavigableSet

- **SortedSet**: An interface representing a Set sorted in ascending order. Provides operations like `first()`, `last()`, and range views `subSet(from, to)`.
- **NavigableSet**: Extends `SortedSet` and adds routing/estimation methods like `lower()`, `floor()`, `ceiling()`, and `higher()` to find closest matches, as well as `pollFirst()` and `pollLast()`.

### When to use Set?

Use a Set when duplicates are unacceptable.
- **HashSet**: Default choice. Fast O(1) operations, no order guarantee.
- **LinkedHashSet**: Use when you need to maintain insertion order.
- **TreeSet**: Use when you need elements to be sorted or need navigation methods.

### Comparing HashSet vs TreeSet vs LinkedHashSet

| Property | HashSet | TreeSet | LinkedHashSet |
| --- | --- | --- | --- |
| **Internal Structure** | HashMap | TreeMap (Red-Black tree) | HashMap + Doubly-Linked List |
| **Time Complexity** | O(1) | O(log N) | O(1) |
| **Iteration Order** | Undefined | Sorted | Insertion Order |
| **Null Elements** | Allowed (one) | Rejected (NullPointerException)| Allowed (one) |

### Duplicate Removal Mechanism

How Sets determine duplicates:
- **HashSet / LinkedHashSet**: Check if `obj1.hashCode() == obj2.hashCode()`. If hashes match, they call `obj1.equals(obj2)`. If `equals` returns true, it's rejected as a duplicate.
- **TreeSet**: Checks sorting order. Calls `comparator.compare(obj1, obj2)` or `obj1.compareTo(obj2)`. If it returns `0`, the element is rejected as a duplicate. **Note**: TreeSet completely ignores `equals()` and `hashCode()` for duplicate detection.

### Role of equals() and hashCode()

For `HashSet` and `HashMap` to work correctly:
1. **Reflexive**: `x.equals(x)` must be true.
2. **Symmetric**: If `x.equals(y)` is true, then `y.equals(x)` must be true.
3. **Transitive**: If `x.equals(y)` and `y.equals(z)` are true, `x.equals(z)` must be true.
4. **Consistency**: If `x.equals(y)` is true, it remains true unless fields change.
5. **hashCode contract**: If `x.equals(y)` is true, `x.hashCode() == y.hashCode()` MUST be true. If `x.equals(y)` is false, their hashcodes do not have to be different (but should be for performance).

### PriorityQueue

An unbounded priority queue based on a binary heap.
- **Ordering**: Head of the queue is the least element according to sorting.
- **Performance**: O(log N) for insertions (`offer`) and deletions (`poll`); O(1) for retrieval (`peek`).
- **Nulls**: Rejects `null`.

### ArrayDeque

A resizable-array implementation of the `Deque` interface.
- **Performance**: Circular array implementation. Faster than `Stack` when used as a stack, and faster than `LinkedList` when used as a queue.
- **Capacity**: No capacity limits; grows as needed. Rejects `null`.

---

**Runnable Code Example (TreeSet with Custom Comparator & PriorityQueue):**
```java
import java.util.*;

public class SetQueueExample {
    static class Person implements Comparable<Person> {
        String name;
        int age;
        
        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        @Override
        public int compareTo(Person other) {
            return this.name.compareTo(other.name); // Sort by name
        }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }

    public static void main(String[] args) {
        // 1. TreeSet using natural ordering (Comparable -> name)
        Set<Person> peopleByName = new TreeSet<>();
        peopleByName.add(new Person("Charlie", 30));
        peopleByName.add(new Person("Alice", 25));
        peopleByName.add(new Person("Bob", 35));
        System.out.println("Sorted by Name (Natural): " + peopleByName);
        
        // 2. TreeSet using custom Comparator (by age)
        Set<Person> peopleByAge = new TreeSet<>(Comparator.comparingInt(p -> p.age));
        peopleByAge.addAll(peopleByName);
        System.out.println("Sorted by Age (Comparator): " + peopleByAge);
        
        // 3. PriorityQueue max-heap behavior
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.offer(10);
        maxHeap.offer(30);
        maxHeap.offer(20);
        
        System.out.print("PriorityQueue polling: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " "); // 30 20 10
        }
        System.out.println();
    }
}
```

---

## Common Mistakes

### 1. Inconsistent compareTo and equals
If `compareTo` returns `0` for two objects, but `equals` returns `false`, inserting them into a `TreeSet` will cause the second element to be discarded. Always make sure `(x.compareTo(y) == 0) == x.equals(y)`.

### 2. Inserting non-Comparable elements into TreeSet or PriorityQueue
If you instantiate `new TreeSet<>()` and try to add custom objects that do not implement `Comparable` (without passing a custom `Comparator` to the constructor), a `ClassCastException` is thrown at runtime on the first addition.

### 3. Iterating a PriorityQueue expecting order
Calling `for (Integer i : priorityQueue)` or using an `Iterator` does NOT traverse the queue in priority order. The iterator traverses the underlying binary heap array directly, which is not sorted. To retrieve elements in order, you must poll them sequentially: `while(!pq.isEmpty()) { pq.poll(); }`.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?
