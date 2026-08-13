# NIO / NIO.2 - Part 1

| Concept | What to know |
| --- | --- |
| `Path` | An interface representing a hierarchical path to a file or directory; replaces `java.io.File` with a cleaner, more flexible API. |
| `Paths` | A utility factory class containing static methods (like `Paths.get()`) to create a `Path` from a string or URI. |
| `Files` | A utility class that operates on `Path` objects, providing static methods for file creation, deletion, copy, metadata inspection, and directory stream querying. |
| `StandardOpenOption` | An enum defining options when opening a file (e.g. `READ`, `WRITE`, `CREATE`, `APPEND`, `TRUNCATE_EXISTING`). |
| `Read/write file using Files` | Reading or writing file contents in bulk using methods like `Files.readAllLines()` or streaming via `Files.lines()`. |
| `Walk file tree` | Scanning directory structures recursively using stream-based operations like `Files.walk()`, `Files.find()`, or custom visitors with `Files.walkFileTree()`. |
| `Copy/move/delete file` | Disk operations using `Files.copy()`, `Files.move()`, and `Files.delete()` with configurable copy options (like `REPLACE_EXISTING`). |
| `Channel` | A connection to an I/O source/sink (like files or sockets) capable of performing bulk, non-blocking data transfers. |
| `Buffer` | A block of memory (container of primitive data) used as a source or destination when reading from or writing to a `Channel`. |
| `ByteBuffer` | A byte-based `Buffer` class that manages state using variables: `position`, `limit`, and `capacity`. |

## Detailed Notes

### Path and Paths (NIO.2)
`java.nio.file.Path` is the NIO.2 replacement for `java.io.File`. It represents a system-independent hierarchical path. Unlike `File`, it is an interface, and it supports sophisticated path manipulation.
* Note: Since Java 11, `Path.of(String)` is preferred over `Paths.get(String)`.
* Path manipulations do not access the file system (they are logical operations in memory).

#### Why Path and Files Are Superior to java.io.File

The legacy `java.io.File` class conflates the abstract path representation with physical file system operations, leading to poor design separation. Furthermore, many `java.io.File` methods (like `delete()` or `createNewFile()`) return a simple `boolean` on failure instead of throwing a descriptive `IOException`, which often causes developers to overlook error handling and results in silent failures. The NIO.2 `Path` interface represents a pure logical path in memory, completely separating path manipulation from filesystem access. Actual disk operations are delegated to the `java.nio.file.Files` utility class, which throws rich, specific exceptions (such as `NoSuchFileException` or `AccessDeniedException`) that force proper error handling and aid debugging.

```mermaid
graph TD
    subgraph Legacy IO (java.io.File)
        FileObj["java.io.File Class"]
        FileObj -->|Conflates| PathLogic[Logical Path Manipulation]
        FileObj -->|Conflates| DiskAccess[Physical Disk Access]
        FileObj -->|Returns| BooleanFail[Boolean on Failure]
    end
    subgraph Modern NIO.2 (java.nio.file)
        PathInt[Path Interface] -->|Pure Logic| Memory[In-Memory Representation]
        FilesUtil[Files Class] -->|Disk Operations| Disk[Actual Disk Access]
        FilesUtil -->|Throws| RichException[Descriptive IOExceptions]
    end
```

##### Code Example: Error Handling Comparison

```java
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LegacyVsNioErrorHandling {
    public static void main(String[] args) {
        // Legacy java.io.File: Silent failure prone
        File legacyFile = new File("/nonexistent/dir/file.txt");
        boolean isDeleted = legacyFile.delete(); 
        System.out.println("Legacy deleted: " + isDeleted); // Prints: Legacy deleted: false (No exception thrown!)

        // Modern java.nio.file.Path & Files: Fail-fast and descriptive
        Path nioPath = Path.of("/nonexistent/dir/file.txt");
        try {
            Files.delete(nioPath);
        } catch (IOException e) {
            System.err.println("NIO deletion failed: " + e.getClass().getSimpleName());
            // Prints: NIO deletion failed: NoSuchFileException
        }
    }
}
```

##### Cause-Effect Chain of Deletion Operations

```
Legacy File.delete() called on missing file 
  └── Returns false (No exception raised)
        └── Developer forgets to check return value
              └── Program proceeds under false assumption of deletion
                    └── Silent failure propagates, leading to potential data corruption/logic errors

NIO.2 Files.delete() called on missing file
  └── Throws NoSuchFileException
        └── JVM interrupts normal execution flow
              └── Caller is forced to catch/handle the exception
                    └── Application fails fast, providing a clear stack trace for diagnostic debugging
```

#### Key Path Operations:
* `resolve(Path)`: Joins two paths. If the argument is an absolute path, it simply returns the argument.
* `relativize(Path)`: Computes the relative path between two paths (the "distance" between them).
* `normalize()`: Resolves redundant elements like `.` (current directory) and `..` (parent directory).

```java
import java.nio.file.Path;

public class PathDemo {
    public static void main(String[] args) {
        Path p1 = Path.of("/home/user/docs");
        Path p2 = Path.of("project/readme.txt");
        
        // Resolve (merge)
        Path resolved = p1.resolve(p2);
        System.out.println("Resolved: " + resolved); // /home/user/docs/project/readme.txt
        
        // Relativize (distance)
        Path start = Path.of("/home/user");
        Path end = Path.of("/home/user/docs/photos");
        System.out.println("Relative: " + start.relativize(end)); // docs/photos
        
        // Normalize (clean up)
        Path dirty = Path.of("/home/user/docs/../photos/./temp");
        System.out.println("Normalized: " + dirty.normalize()); // /home/user/photos/temp
    }
}
```

### Files Utility Class
The `java.nio.file.Files` class operates on `Path` instances and actually communicates with the filesystem.
* File streams returned by `Files.lines()`, `Files.list()`, `Files.walk()`, and `Files.find()` wrap underlying system resources (directory streams) and **must be closed** via try-with-resources.

```java
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class FilesDemo {
    public static void main(String[] args) {
        Path source = Path.of("source.txt");
        Path target = Path.of("dest.txt");
        
        try {
            // Copy with options
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            
            // Delete safely
            boolean deleted = Files.deleteIfExists(target);
            System.out.println("Deleted: " + deleted);
            
            // Streaming file lines (MUST close via try-with-resources)
            Path logPath = Path.of("app.log");
            try (Stream<String> lines = Files.lines(logPath)) {
                lines.filter(line -> line.contains("ERROR"))
                     .forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

### Walk File Tree
`Files.walk(Path)` returns a lazy stream of paths, traversing directory structures depth-first. To prevent infinite loops or memory issues, you can specify a max depth.
Alternatively, `Files.walkFileTree()` takes a `FileVisitor` subclass to handle fine-grained traversal control.

```java
// Streaming directory tree
try (Stream<Path> stream = Files.walk(Path.of("src"), 3)) { // Max depth 3
    stream.forEach(System.out::println);
} catch (IOException e) {
    e.printStackTrace();
}
```

### Buffer State Variables (position, limit, capacity)
NIO data transfers happen through channels and buffers. A `Buffer` is an in-memory block container.
* `capacity`: The total size of the buffer (fixed on creation).
* `position`: The index of the next element to read or write.
* `limit`: The index of the first element that should **not** be read or written.

#### State Transitions:
* `flip()`: Prepares the buffer for reading after writing. Sets `limit = position`, then `position = 0`.
* `clear()`: Prepares the buffer for writing after reading. Sets `position = 0`, `limit = capacity` (pointers reset; data is not cleared).
* `rewind()`: Resets `position` to 0, allowing re-reading of the data already in the buffer (leaves `limit` unchanged).

#### Why Buffer flip() is Required and State Pointer Transitions

A Java NIO `Buffer` is a single block of memory managed by a single set of read/write cursors: `position`, `limit`, and `capacity`. When writing data into the buffer, the `position` pointer advances toward the `limit` (which initially equals `capacity`) to track the next index to write. If a reader tries to read from the buffer immediately after writing without shifting modes, the buffer will try to read from the current `position` onwards, resulting in reading uninitialized/empty bytes or hitting the limit immediately. Calling `flip()` transitions the buffer from write-mode to read-mode by setting the `limit` to the current `position` (marking the exact boundary of valid written data) and resetting `position` back to `0` so that reading starts from the beginning of the written data.

```mermaid
graph TD
    subgraph Write Mode (Initial state)
        W_Pos[position = 2]
        W_Lim[limit = 10]
        W_Cap[capacity = 10]
        DataW["['H', 'i', _, _, _, _, _, _, _, _]"]
    end
    subgraph After flip() (Read Mode)
        R_Pos[position = 0]
        R_Lim[limit = 2]
        R_Cap[capacity = 10]
        DataR["['H', 'i' | limit boundary, _, _, _, _, _, _, _]"]
    end
    Write Mode -->|flip() call| After flip()
```

##### Code Example: The Danger of Forgetting flip()

```java
import java.nio.ByteBuffer;

public class FlipRequirementDemo {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10); // capacity=10, position=0, limit=10
        
        // Write two bytes
        buf.put((byte) 'H');
        buf.put((byte) 'i'); // position becomes 2
        
        // INCORRECT: Read without flipping
        System.out.println("Remaining bytes without flip: " + buf.remaining()); // Prints: 8
        System.out.print("Data read without flip: ");
        while (buf.hasRemaining()) {
            System.out.print((char) buf.get()); // Reads uninitialized bytes at indices 2 to 9 (prints spaces/garbage)
        }
        System.out.println();
        
        // Reset pointers for correct demo
        buf.position(2); // Set back to where it was after writing
        
        // CORRECT: Flip before reading
        buf.flip(); // limit becomes 2, position becomes 0
        System.out.println("Remaining bytes after flip: " + buf.remaining()); // Prints: 2
        System.out.print("Data read after flip: ");
        while (buf.hasRemaining()) {
            System.out.print((char) buf.get()); // Prints: Hi
        }
        System.out.println();
    }
}
```

##### Cause-Effect Chain of Writing and Reading Without flip()

```
Write 'H' and 'i' into buffer
  └── position advances from 0 to 2 (limit remains 10)
        └── Attempt to read buffer directly (without calling flip())
              └── get() starts reading from position 2 up to limit 10
                    └── Reads index 2 to 9 (uninitialized buffer data) instead of 'H' and 'i'
                          └── Returns garbage or empty data, leaving 'H' and 'i' unread

Write 'H' and 'i' into buffer
  └── position advances from 0 to 2 (limit remains 10)
        └── Call flip()
              └── limit is set to 2 (marks end of valid data), position is reset to 0
                    └── get() starts reading from position 0 up to limit 2
                          └── Correctly reads 'H' at index 0 and 'i' at index 1, stopping at limit 2
```

```java
import java.nio.ByteBuffer;

public class BufferStateDemo {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10); // capacity = 10, position = 0, limit = 10
        
        // Write to buffer
        buf.put((byte) 'H');
        buf.put((byte) 'i'); // position is now 2
        
        // Flip to read mode
        buf.flip(); // limit is set to 2, position is reset to 0
        
        while (buf.hasRemaining()) {
            System.out.print((char) buf.get()); // Reads 'H' and 'i'. position becomes 2.
        }
        System.out.println();
        
        // Clear to write again
        buf.clear(); // position is set back to 0, limit to 10. Ready to write.
    }
}
```

---

## Common Mistakes

### 1. Resource Leaks with `Files.lines()`, `walk()`, or `list()`
These methods return streams that maintain active filesystem handles. If you do not close them, you will leak file descriptors, eventually causing "Too many open files" exceptions.
* **Fix**: Always wrap these streams in a try-with-resources statement.

### 2. The Absolute Resolve Trap
Calling `path1.resolve(path2)` simply returns `path2` if `path2` is absolute, which can surprise developers expecting a merged output.
```java
Path base = Path.of("/home/user");
Path target = Path.of("/etc/config");
System.out.println(base.resolve(target)); // Prints /etc/config, NOT /home/user/etc/config!
```

### 3. Forgetting to `flip()` before reading a buffer
After writing data into a buffer, the buffer's `position` points to the next empty index. If you immediately try to read from it without calling `flip()`, you will read uninitialized/empty bytes up to the limit (or read nothing if position is at limit).
* **Rule**: Always call `buffer.flip()` before reading from a buffer, and `buffer.clear()` before writing to it again.

---

## Reference Links

- [Official Java Path Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Path.html)
- [Official Java Files Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html)
- [Official Java Buffer Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/Buffer.html)
