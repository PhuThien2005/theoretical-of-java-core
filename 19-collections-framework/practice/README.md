# Practice Exercises: Collections Framework

This folder contains hands-on practice exercises to reinforce your understanding of Java collections, map behaviors, double-ended queues (Deque), and custom collection extensions.

## Exercises

### 1. LRU Cache (`lru-cache`)
A Least Recently Used (LRU) Cache discards the least recently accessed items first when it reaches its maximum capacity. In Java, `LinkedHashMap` provides access-order iteration and a lifecycle hook `removeEldestEntry` that makes implementing an LRU cache extremely simple.
- **Goal**: Extend `LinkedHashMap` to implement an LRU cache with a maximum capacity.

#### Directory Structure
- [LruCache.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/19-collections-framework/practice/lru-cache/src/LruCache.java)
- [LruCacheTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/19-collections-framework/practice/lru-cache/test/LruCacheTest.java)
- [LruCache.java (Solution)](file:///home/fhu_thjen/projects/learning-java/19-collections-framework/practice/lru-cache/solution/LruCache.java)

---

### 2. Bracket Matching Stack (`bracket-matching-stack`)
Stacks are useful for keeping track of nested structures. In Java, rather than using the legacy `Stack` class, it is best practice to use `java.util.Deque` backed by `ArrayDeque` for single-threaded stack operations due to better performance.
- **Goal**: Implement a utility that uses a `Deque` to validate whether parentheses, brackets, and braces in a code string are correctly balanced.

#### Directory Structure
- [BracketMatchingStack.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/19-collections-framework/practice/bracket-matching-stack/src/BracketMatchingStack.java)
- [BracketMatchingStackTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/19-collections-framework/practice/bracket-matching-stack/test/BracketMatchingStackTest.java)
- [BracketMatchingStack.java (Solution)](file:///home/fhu_thjen/projects/learning-java/19-collections-framework/practice/bracket-matching-stack/solution/BracketMatchingStack.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 19-collections-framework
```
