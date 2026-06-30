# Practice Exercises: Interview Questions

This folder contains hands-on practice exercises to reinforce your understanding of common Java and Data Structures & Algorithms (DSA) interview questions.

## Exercises

### 1. Linked List Cycle Detector (`linked-list-cycle-detector`)
Implement Floyd's Cycle-Finding Algorithm (slow and fast pointers) to detect if a linked list contains a cycle:
- **Algorithm**: Initialize two pointers (slow moving 1 step at a time, fast moving 2 steps). If they meet, a cycle exists. If fast reaches the end, no cycle exists.
- **Space Complexity**: Must be O(1) auxiliary space (do not use a HashSet/HashMap).

#### Directory Structure
- [LinkedListCycleDetector.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/45-interview-questions/practice/linked-list-cycle-detector/src/LinkedListCycleDetector.java)
- [LinkedListCycleDetectorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/45-interview-questions/practice/linked-list-cycle-detector/test/LinkedListCycleDetectorTest.java)
- [LinkedListCycleDetector.java (Solution)](file:///home/fhu_thjen/projects/learning-java/45-interview-questions/practice/linked-list-cycle-detector/solution/LinkedListCycleDetector.java)

---

### 2. Custom HashMap Implementation (`custom-hash-map-impl`)
Build a minimal working hash map from scratch:
- **Hash Table**: Maintain a bucket array of linked list nodes to handle hash collisions (chaining method).
- **Operations**: Implement `put(K key, V value)`, `get(K key)`, `remove(K key)`, `size()`, and handle key overwrites.

#### Directory Structure
- [CustomHashMapImpl.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/45-interview-questions/practice/custom-hash-map-impl/src/CustomHashMapImpl.java)
- [CustomHashMapImplTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/45-interview-questions/practice/custom-hash-map-impl/test/CustomHashMapImplTest.java)
- [CustomHashMapImpl.java (Solution)](file:///home/fhu_thjen/projects/learning-java/45-interview-questions/practice/custom-hash-map-impl/solution/CustomHashMapImpl.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository to test your implementations:

```bash
python3 scripts/verify_exercise.py 45-interview-questions
```
