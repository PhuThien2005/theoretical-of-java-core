# NIO / NIO.2 - Part 1

## Learning Goal

This file covers a focused slice of **NIO / NIO.2**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

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
