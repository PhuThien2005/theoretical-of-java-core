# TreeSet, SortedSet, NavigableSet & Priority Queue

## Ordered Set Abstractions Overview

In the Java Collections Framework, the `Set<E>` interface enforces element uniqueness. For domain problems requiring elements to **not only be unique but also automatically maintain a sorted ordering**, Java provides specialized sub-interfaces: `SortedSet<E>` and `NavigableSet<E>`, concrete-implemented via `TreeSet<E>`.

---

## Deep Architectural Analysis of `TreeSet`

### 1. Red-Black Tree Backing Infrastructure
`TreeSet` is implemented on top of a self-balancing Binary Search Tree — specifically an instance of `TreeMap<E, Object>`.

```mermaid
graph TD
    Root[20 (Black)] --> Left[10 (Black)]
    Root --> Right[30 (Black)]
    Left --> LeftLeft[5 (Red)]
    Left --> LeftRight[15 (Red)]
```

- **Self-Balancing Invariants**: Enforces that tree height remains bounded at $O(\log N)$.
- **Algorithmic Complexity**: Search (`contains`), insertion (`add`), and deletion (`remove`) operations are guaranteed $O(\log N)$ time complexity.

---

### 2. Uniqueness Rule: `compareTo()` vs `equals()`

> [!IMPORTANT]
> `TreeSet` DOES NOT use `equals()` or `hashCode()` to evaluate element equivalence. Instead, it relies strictly on `compareTo()` (from `Comparable`) or `compare()` (from `Comparator`).

- **TreeSet Equivalence Contract**: 
  If `compareTo(a, b) == 0`, `TreeSet` considers elements $a$ and $b$ to be **identical** and rejects adding $b$, even if `a.equals(b)` evaluates to `false`.
- **Consistency with Equals Requirement**:
  The `compareTo()` implementation should ideally be consistent with `equals()`:
  $$(a.\text{compareTo}(b) == 0) \iff (a.\text{equals}(b) == \text{true})$$
  If violated, `TreeSet` functions correctly structurally, but violates the general contract of the `Set` interface.

---

## `NavigableSet` Interface & Directional Search Operations

`NavigableSet<E>` extends `SortedSet<E>` with directional search methods:

| Method | Semantic Behavior |
| :--- | :--- |
| `lower(e)` | Returns greatest element $< e$, or `null` if none. |
| `floor(e)` | Returns greatest element $\le e$, or `null` if none. |
| `ceiling(e)` | Returns smallest element $\ge e$, or `null` if none. |
| `higher(e)` | Returns smallest element $> e$, or `null` if none. |
| `pollFirst()` | Retrieves and removes smallest element (first in tree). |
| `pollLast()` | Retrieves and removes largest element (last in tree). |
| `subSet(from, fromInc, to, toInc)` | Returns bounded range view with inclusive/exclusive flags. |

---

## Priority Queue (`PriorityQueue`) & `ArrayDeque`

### 1. `PriorityQueue<E>`
`PriorityQueue` is an unbounded priority queue based on a **Binary Min-Heap** stored as a array (`Object[] queue`).

- **Operational Mechanics**: The smallest element (by natural or comparator order) is located at `head`.
- **Complexity**: `offer()` and `poll()` take $O(\log N)$; `peek()` takes $O(1)$.
- **Iteration Caveat**: Iterating over a `PriorityQueue` via Iterator **does NOT guarantee sorted order**. Only sequential `poll()` invocations extract elements in order.

### 2. `ArrayDeque<E>`
`ArrayDeque` implements `Deque` backed by a **Resizable Circular Array**.
- Zero node object allocation overhead.
- Outperforms `Stack` (no `synchronized` locks) and `LinkedList` (CPU cache locality).
- Recommended default for LIFO Stacks and FIFO Queues.

---

## Detailed Set Implementations Comparison

| Metric | `HashSet` | `LinkedHashSet` | `TreeSet` |
| :--- | :--- | :--- | :--- |
| **Data Structure** | Hash Table (`HashMap`) | Hash Table + Doubly-Linked List | Red-Black Tree (`TreeMap`) |
| **Complexity** | $O(1)$ | $O(1)$ | $O(\log N)$ |
| **Ordering** | Unordered | Insertion Order | Sorted Order |
| **`null` Elements** | Permits 1 `null` | Permits 1 `null` | **Prohibits `null`** (throws `NullPointerException`) |
| **Equality Basis** | `hashCode()` & `equals()` | `hashCode()` & `equals()` | `compareTo()` / `compare()` |

---

## Executable Code Example

```java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class TreeSetNavigableDemo {
    public static void main(String[] args) {
        // 1. NavigableSet API Demo
        NavigableSet<Integer> scores = new TreeSet<>();
        scores.add(60);
        scores.add(75);
        scores.add(85);
        scores.add(90);

        System.out.println("Scores: " + scores);
        System.out.println("Greatest <= 80 (floor): " + scores.floor(80)); // 75
        System.out.println("Smallest > 75 (higher): " + scores.higher(75)); // 85

        // 2. PriorityQueue Demo (Min-Heap)
        Queue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(40);
        minHeap.offer(10);
        minHeap.offer(25);

        System.out.print("Polling PriorityQueue: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " "); // Prints 10 25 40
        }
        System.out.println();

        // 3. ArrayDeque as Stack (LIFO)
        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("Action 1");
        undoStack.push("Action 2");
        System.out.println("Undo action: " + undoStack.pop()); // Action 2
    }
}
```

---

## Common Pitfalls & Interview Traps

1. **Adding `null` to a `TreeSet`**:
   - *Fact*: Immediately throws `NullPointerException` because `TreeSet` must invoke `null.compareTo(...)` to evaluate tree node placement.

2. **Iterating `PriorityQueue` with For-Each**:
   - *Fact*: A `for (int item : priorityQueue)` loop iterates over the internal Min-Heap array layout directly, which is un-ordered. Must use `while (!queue.isEmpty()) queue.poll()`.
