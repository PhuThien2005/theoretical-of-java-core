# Exercise: Linked List Cycle Detector

## Objective
Implement Floyd's Cycle-Finding Algorithm (also known as the "Tortoise and the Hare" algorithm) to detect if a singly linked list contains a cycle.

## Requirements
1. **Node Structure**:
   - Define a class `Node` with:
     - `int val`
     - `Node next`

2. **Cycle Detection**:
   - Implement `boolean hasCycle(Node head)`:
     - Return `false` if the list is empty or has only one element without a cycle.
     - Use a slow pointer (`slow`) and a fast pointer (`fast`).
     - `slow` moves one node at a time.
     - `fast` moves two nodes at a time.
     - If `slow` and `fast` meet at the exact same object reference, return `true`.
     - If `fast` or `fast.next` becomes `null`, return `false`.
     - **Constraint**: Must use $O(1)$ auxiliary memory (in-place detection without storing visited nodes in a set/list).
