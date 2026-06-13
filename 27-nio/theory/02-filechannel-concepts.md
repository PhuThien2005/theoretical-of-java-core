# NIO / NIO.2 - Part 2

## Learning Goal

This file covers a focused slice of **NIO / NIO.2**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `FileChannel` | A thread-safe, high-performance channel for reading, writing, mapping, and locking files; supports random access operations. |
| `Basic Selector` | A multiplexor of selectable channels, enabling a single thread to manage and monitor multiple non-blocking network channels (sockets). |
| `Basic Asynchronous IO` | Non-blocking file I/O operations that run asynchronously, returning a `Future` or executing a `CompletionHandler` callback upon completion. |

## Detailed Notes

### FileChannel
`java.nio.channels.FileChannel` is a channel for reading, writing, mapping, and manipulating files.
* **Obtaining a FileChannel**: You can open one directly via `FileChannel.open(path, options)`, or obtain one from legacy streams: `fileInputStream.getChannel()` or `fileOutputStream.getChannel()`.
* **Random Access**: Unlike streams, which are sequential, `FileChannel` has a `position(long)` method that allows you to jump to any index in the file for reading or writing.
* **File Locking**: `FileChannel` supports locking file regions via `lock()` (blocks until lock is acquired) or `tryLock()` (non-blocking, returns null if already locked). Locks can be shared (read-only) or exclusive (write-only).

```java
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("temp.txt", "rw");
             FileChannel channel = file.getChannel()) {
            
            // 1. Write to channel
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            buf.put("Hello FileChannel".getBytes());
            buf.flip();
            
            while (buf.hasRemaining()) {
                channel.write(buf);
            }
            
            // 2. Read from specific position
            channel.position(6); // Jump to index 6 (skips "Hello ")
            buf.clear();
            int bytesRead = channel.read(buf);
            
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get()); // Prints "FileChannel"
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### Basic Selector (Non-blocking I/O)
A `Selector` allows a single thread to monitor multiple selectable network channels (e.g. `SocketChannel`, `ServerSocketChannel`) for events like connections (`OP_ACCEPT`), readiness to read (`OP_READ`), or readiness to write (`OP_WRITE`).
* **Multiplexing**: This is the foundation of high-scalability web servers (like Netty), eliminating the need for a thread-per-connection architecture.
* **Important**: Selectors **only** work with classes inheriting from `SelectableChannel`. File channels do **not** inherit from `SelectableChannel` and cannot be placed in non-blocking mode or registered with a selector.

```java
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false); // Non-blocking mode is REQUIRED for Selector!
        
        // Register channel for connections
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        // Non-blocking select event loop
        while (true) {
            int readyChannels = selector.select(1000); // Wait up to 1 second
            if (readyChannels == 0) continue;
            
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // Accept connection
                } else if (key.isReadable()) {
                    // Read data
                }
                keyIterator.remove(); // Must remove handled key!
            }
            break; // Break demo
        }
        serverChannel.close();
        selector.close();
    }
}
```

### Basic Asynchronous I/O
NIO.2 introduced asynchronous channels (e.g. `AsynchronousFileChannel`) which execute operations in a background thread pool.
* **Methods of retrieval**:
  1. **Future-based**: The read/write method returns a `Future<Integer>` object. Calling `.get()` blocks until done.
  2. **Callback-based**: You pass a `CompletionHandler<Integer, Attachment>` which executes its `completed()` or `failed()` callback when done.

```java
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsyncFileRead {
    public static void main(String[] args) throws Exception {
        Path path = Path.of("temp.txt");
        try (AsynchronousFileChannel asyncChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            
            // Read asynchronously starting at position 0
            Future<Integer> operation = asyncChannel.read(buffer, 0);
            
            // Wait for completion (simulated wait)
            while (!operation.isDone()) {
                Thread.sleep(10);
            }
            
            buffer.flip();
            System.out.println("Bytes read: " + operation.get());
        }
    }
}
```

---

## Case Study: High-Performance File I/O with Direct Memory Mapping

### Problem
Reading and writing very large files (e.g., 1GB) can exhaust JVM heap memory or cause high GC overhead if loaded into byte arrays.

### Solution: Memory-Mapped Files
By using `FileChannel.map()`, we map a region of a file directly into physical memory (virtual memory space outside the JVM heap), returning a `MappedByteBuffer`.
* **Zero-Copy**: The operating system maps disk pages directly to memory pages, bypassing the need to copy data between kernel buffer and JVM heap buffer.
* **Direct Access**: Modifications to the `MappedByteBuffer` are automatically propagated to the underlying file by the OS.

```java
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMapCaseStudy {
    public static void main(String[] args) throws Exception {
        try (RandomAccessFile file = new RandomAccessFile("largefile.dat", "rw");
             FileChannel channel = file.getChannel()) {
             
            // Map 10MB of the file directly to memory
            long mapSize = 10 * 1024 * 1024; // 10MB
            MappedByteBuffer out = channel.map(FileChannel.MapMode.READ_WRITE, 0, mapSize);
            
            // Write directly to memory map
            for (int i = 0; i < mapSize; i++) {
                out.put((byte) 'A');
            }
            System.out.println("Finished writing 10MB to mapped file.");
        }
    }
}
```

---

## Common Mistakes

### 1. Trying to Register a `FileChannel` with a `Selector`
`FileChannel` does not implement `SelectableChannel`. It cannot be set to non-blocking mode (`configureBlocking(false)`) and attempting to register it with a selector throws an `IllegalBlockingModeException`.
* **Rule**: Selectors are purely for network-based and selectable channel types (like Sockets).

### 2. Assuming File Locks are Cross-JVM Blockers
File locks in Java (`FileChannel.lock()`) are JVM-wide. Different threads within the **same** JVM can still access the file concurrently despite the lock, and some operating systems treat these locks as advisory, meaning non-cooperating processes on the same OS can still bypass them.
* **Fix**: Do not rely on file locking for thread synchronization within the same JVM; use standard concurrent locks instead.
