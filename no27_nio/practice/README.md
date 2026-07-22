# Practice Exercises: Java NIO (New I/O)

This folder contains hands-on practice exercises to reinforce your understanding of Java NIO.2 (`java.nio.file`), directory tree traversal using `walkFileTree()`, and high-performance channel/buffer-based I/O using `FileChannel` and Direct `ByteBuffer` allocations.

## Exercises

### 1. File Tree Duplicate Finder (`file-tree-duplicate-finder`)
Directory traversal is a common task in backup and system utilities. Java NIO.2 provides `Files.walkFileTree()` and `SimpleFileVisitor` to process files recursively.
- **Goal**: Implement a duplicate file locator that recursively traverses a directory, reads file contents, and groups matching duplicate files.

#### Directory Structure
- [FileTreeDuplicateFinder.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no27_nio/practice/file-tree-duplicate-finder/src/FileTreeDuplicateFinder.java)
- [FileTreeDuplicateFinderTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no27_nio/practice/file-tree-duplicate-finder/test/FileTreeDuplicateFinderTest.java)
- [FileTreeDuplicateFinder.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no27_nio/practice/file-tree-duplicate-finder/solution/FileTreeDuplicateFinder.java)

---

### 2. NIO Fast File Copy (`nio-fast-file-copy`)
Unlike classic streams, NIO utilizes `Channel` and `Buffer` concepts. Direct ByteBuffers bypass standard JVM heap memory and communicate directly with OS kernel memory page caches, speeding up I/O transfers.
- **Goal**: Implement a high-performance copy operation using `FileChannel` and direct `ByteBuffer` allocations.

#### Directory Structure
- [NioFastFileCopy.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no27_nio/practice/nio-fast-file-copy/src/NioFastFileCopy.java)
- [NioFastFileCopyTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no27_nio/practice/nio-fast-file-copy/test/NioFastFileCopyTest.java)
- [NioFastFileCopy.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no27_nio/practice/nio-fast-file-copy/solution/NioFastFileCopy.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no27_nio
```
