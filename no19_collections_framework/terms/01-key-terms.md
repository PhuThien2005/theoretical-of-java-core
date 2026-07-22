# Collections Framework Terms

Use this file when a word in the theory feels too compressed. Each term has meaning, importance, confusion, and a small example.

## Iterable

Iterable is the root interface of the Java collections hierarchy (excluding `Map`). Implementing `Iterable<T>` allows an object to be the target of the enhanced for-each loop statement.

- **Why it matters**: It abstracts away the iteration mechanism, allowing developers to traverse custom data structures using a clean, readable syntax without exposing their underlying node layouts or array indices.
- **Common confusion**: Confusing `Iterable` with `Iterator`. `Iterable` represents a capability (the object can be iterated over and has an `iterator()` method), whereas `Iterator` is the actual stateful object performing the traversal via `hasNext()` and `next()`.
- **Small example**:
  ```java
  Iterable<String> list = List.of("A", "B", "C");
  for (String s : list) { // Enhanced for-each loop works because List implements Iterable
      System.out.println(s);
  }
  ```

## Collection

A collection is the root interface in the collection hierarchy representing a group of objects known as elements. Some collections allow duplicates and others do not; some are ordered and others unordered.

- **Why it matters**: It defines the shared contract (such as `add()`, `remove()`, `size()`, `contains()`, `isEmpty()`, and conversion to arrays) that all sub-interfaces (`List`, `Set`, `Queue`) must support, facilitating polymorphic APIs.
- **Common confusion**: Thinking that `Map` implements `Collection`. It does not, because maps deal with key-value pairs (mappings) rather than single elements, meaning their API methods (`put()`, `get()`) do not align with the `Collection` method signatures.
- **Small example**:
  ```java
  java.util.Collection<Integer> numbers = new java.util.ArrayList<>();
  numbers.add(10);
  numbers.add(20);
  System.out.println("Contains 10? " + numbers.contains(10)); // true
  ```

## List

A List is an ordered collection (also known as a sequence). The user of this interface has precise control over where in the list each element is inserted, and elements can be accessed by their integer index.

- **Why it matters**: It is the default sequence choice when order must be preserved, duplicates are allowed, and index-based positioning/lookup is required.
- **Common confusion**: Believing `List` guarantees good performance for all operations. `ArrayList` has O(1) read but O(N) middle insertion, while `LinkedList` has O(N) read but O(1) head/tail insertion.
- **Small example**:
  ```java
  java.util.List<String> list = new java.util.ArrayList<>();
  list.add("First");
  list.add("Second");
  list.add("First"); // Duplicates allowed
  System.out.println("Item at index 1: " + list.get(1)); // Second
  ```

## Set

A Set is a collection that contains no duplicate elements. It models the mathematical set abstraction and enforces uniqueness using object equality rules.

- **Why it matters**: It is essential for operations like filtering unique elements, verifying membership efficiently (O(1) in `HashSet`), and preventing duplicates automatically.
- **Common confusion**: Assuming all sets are unordered. While `HashSet` makes no ordering guarantees, `LinkedHashSet` preserves insertion order, and `TreeSet` maintains elements sorted by their natural order or a custom `Comparator`.
- **Small example**:
  ```java
  java.util.Set<Integer> uniqueNums = new java.util.HashSet<>();
  uniqueNums.add(5);
  uniqueNums.add(5); // Ignored as duplicate
  System.out.println("Set size: " + uniqueNums.size()); // 1
  ```

## Queue

Queue represents a collection designed for holding elements prior to processing. Typically, queues order elements in a FIFO (first-in-first-out) manner, but priority queues order elements according to a supplied comparator.

- **Why it matters**: It provides a safe buffer structure for asynchronous messaging, consumer-producer workflows, and tree/graph BFS traversals.
- **Common confusion**: Confusing `poll()` and `remove()`, or `peek()` and `element()`. The former methods return special values (`null` or `false`) if the queue is empty, whereas the latter throw exceptions, potentially causing system crashes.
- **Small example**:
  ```java
  java.util.Queue<String> queue = new java.util.LinkedList<>();
  queue.offer("Task 1");
  queue.offer("Task 2");
  System.out.println("Polled: " + queue.poll()); // Task 1
  ```

## Deque

Deque is a double-ended queue that supports element insertion and removal at both ends. It extends `Queue` and can be used as both a FIFO queue and a LIFO stack.

- **Why it matters**: It serves as a modern replacement for the legacy `Stack` class, providing faster and cleaner stack/queue behavior (via `ArrayDeque`).
- **Common confusion**: Using `Stack` instead of `Deque`. `Stack` is legacy, thread-safe (synchronized), and extends `Vector`, which exposes index-based insertions/deletions that violate stack principles.
- **Small example**:
  ```java
  java.util.Deque<String> stack = new java.util.ArrayDeque<>();
  stack.push("Base");
  stack.push("Top");
  System.out.println("Popped: " + stack.pop()); // Top
  ```

## Map

A Map is an object that maps keys to values. A map cannot contain duplicate keys; each key can map to at most one value.

- **Why it matters**: It is the primary structure for fast dictionary lookups, caching, and key-value pairings using logical keys.
- **Common confusion**: Believing `Map` is a subtype of `Collection` or `Iterable`. Maps are traversed by accessing their collection views: `keySet()`, `values()`, or `entrySet()`.
- **Small example**:
  ```java
  java.util.Map<String, String> userRoles = new java.util.HashMap<>();
  userRoles.put("Alice", "Admin");
  userRoles.put("Bob", "User");
  System.out.println("Alice's role: " + userRoles.get("Alice")); // Admin
  ```

## Iterator

An Iterator is an object that enables sequential traversal of a collection, providing methods to fetch the next element, check for more elements, and safely remove elements.

- **Why it matters**: It provides a uniform way to iterate over different data structures while allowing safe element removal during loop execution.
- **Common confusion**: Attempting to modify a collection using its own methods (like `list.remove()`) during iteration, which causes a crash. Modifications must be done through `iterator.remove()`.
- **Small example**:
  ```java
  java.util.List<Integer> list = new java.util.ArrayList<>(java.util.List.of(1, 2, 3));
  java.util.Iterator<Integer> it = list.iterator();
  while (it.hasNext()) {
      if (it.next() == 2) {
          it.remove(); // Safely removes element 2 from list
      }
  }
  ```

## fail-fast

fail-fast is a design pattern or behavior where a system immediately terminates its operation and throws an error upon detecting structural modification of its state during traversal.

- **Why it matters**: It helps detect programming bugs early by preventing the system from continuing in an unstable, corrupted, or non-deterministic state.
- **Common confusion**: Thinking that `fail-fast` prevents concurrent programming. Fail-fast iterators are not thread-safe and only detect concurrent modifications on a best-effort basis using a modification counter.
- **Small example**:
  ```java
  java.util.List<String> list = new java.util.ArrayList<>(java.util.List.of("A", "B"));
  for (String s : list) {
      list.add("C"); // Triggers fail-fast behavior immediately on next loop step
  }
  ```

## ConcurrentModificationException

ConcurrentModificationException is a runtime exception thrown when a method detects concurrent modification of an object when such modification is not permissible.

- **Why it matters**: It alerts developers to improper single-thread list modifications inside loops or concurrent multi-threaded writes on standard collections.
- **Common confusion**: Assuming this exception only happens in multi-threaded environments. It is frequently thrown in single-threaded programs when modifying a collection directly during a for-each loop.
- **Small example**:
  ```java
  java.util.List<String> list = new java.util.ArrayList<>(java.util.List.of("X", "Y"));
  java.util.Iterator<String> it = list.iterator();
  list.add("Z"); // Structural modification
  try {
      it.next(); // Throws ConcurrentModificationException due to mismatch in modCount
  } catch (java.util.ConcurrentModificationException e) {
      System.out.println("Exception caught!");
  }
  ```
