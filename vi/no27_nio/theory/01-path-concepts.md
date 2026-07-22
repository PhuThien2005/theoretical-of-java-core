# NIO / NIO.2 - Phần 1 (NIO / NIO.2 - Part 1)

## Mục tiêu học tập

Tài liệu này tập trung vào một phần trọng tâm của **NIO / NIO.2**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc Java thực tế, thay vì chỉ học các từ vựng rời rạc.

## Đề cương chi tiết

- **`Path`** — Path: Một giao diện đại diện cho một đường dẫn phân cấp đến một tệp hoặc thư mục; thay thế `java.io.File` bằng một API sạch sẽ và linh hoạt hơn.
- **`Paths`** — Paths: Một lớp tiện ích nhà máy chứa các phương thức tĩnh (như `Paths.get()`) để tạo một `Path` từ một chuỗi hoặc URI.
- **`Files`** — Files: Một lớp tiện ích hoạt động trên các đối tượng `Path`, cung cấp các phương thức tĩnh để tạo, xóa, sao chép tệp, kiểm tra siêu dữ liệu, và truy vấn luồng thư mục.
- **`StandardOpenOption`** — StandardOpenOption: Một enum định nghĩa các tùy chọn khi mở một tệp (ví dụ: `READ`, `WRITE`, `CREATE`, `APPEND`, `TRUNCATE_EXISTING`).
- **`Read/write file using Files`** — Đọc/ghi tệp bằng Files (Read/write file using Files): Đọc hoặc ghi nội dung tệp hàng loạt bằng các phương thức như `Files.readAllLines()` hoặc truyền luồng (streaming) qua `Files.lines()`.
- **`Walk file tree`** — Duyệt cây tệp (Walk file tree): Quét các cấu trúc thư mục một cách đệ quy bằng các thao tác dựa trên luồng như `Files.walk()`, `Files.find()`, hoặc các trình truy cập tùy chỉnh với `Files.walkFileTree()`.
- **`Copy/move/delete file`** — Sao chép/di chuyển/xóa tệp (Copy/move/delete file): Các thao tác trên đĩa sử dụng `Files.copy()`, `Files.move()`, và `Files.delete()` với các tùy chọn sao chép cấu hình được (như `REPLACE_EXISTING`).
- **`Channel`** — Kênh (Channel): Một kết nối đến một nguồn/đích I/O (như tệp hoặc socket) có khả năng thực hiện truyền dữ liệu hàng loạt, không chặn (non-blocking).
- **`Buffer`** — Bộ đệm (Buffer): Một khối bộ nhớ (bộ chứa dữ liệu nguyên thủy) được sử dụng làm nguồn hoặc đích khi đọc từ hoặc ghi vào một `Channel`.
- **`ByteBuffer`** — ByteBuffer: Một lớp `Buffer` dựa trên byte quản lý trạng thái bằng các biến: `position`, `limit`, và `capacity`.

## Ghi chú chi tiết

### Path và Paths (NIO.2)
`java.nio.file.Path` là sự thay thế của NIO.2 cho `java.io.File`. Nó đại diện cho một đường dẫn phân cấp độc lập với hệ thống. Khác với `File`, nó là một giao diện và nó hỗ trợ các thao tác đường dẫn phức tạp.
* Lưu ý: Kể từ Java 11, `Path.of(String)` được ưu tiên hơn `Paths.get(String)`.
* Các thao tác đường dẫn không truy cập vào hệ thống tệp (chúng là các thao tác logic trong bộ nhớ).

#### Tại sao Path và Files vượt trội hơn java.io.File

Lớp cũ `java.io.File` nhập nhằng giữa việc biểu diễn đường dẫn trừu tượng với các thao tác vật lý trên hệ thống tệp, dẫn đến việc phân chia thiết kế kém. Hơn nữa, nhiều phương thức của `java.io.File` (như `delete()` hoặc `createNewFile()`) trả về một giá trị `boolean` đơn giản khi thất bại thay vì ném ra một ngoại lệ `IOException` chi tiết, điều này thường khiến các nhà phát triển bỏ qua việc xử lý lỗi và dẫn đến các thất bại âm thầm. Giao diện `Path` của NIO.2 đại diện cho một đường dẫn logic thuần túy trong bộ nhớ, tách biệt hoàn toàn thao tác đường dẫn khỏi truy cập hệ thống tệp. Các thao tác thực tế trên đĩa được ủy quyền cho lớp tiện ích `java.nio.file.Files`, vốn ném ra các ngoại lệ phong phú và cụ thể (chẳng hạn như `NoSuchFileException` hoặc `AccessDeniedException`), buộc lập trình viên phải xử lý lỗi đúng cách và hỗ trợ đắc lực cho việc gỡ lỗi.

> Xem thêm: Chi tiết về java.io.File trong I/O cổ điển, được trình bày chi tiết trong [Ch.26 - IO](../../no26_io/theory/01-file-concepts.md).

```mermaid
graph TD
    subgraph Legacy IO (java.io.File)
        FileObj["Lớp java.io.File"]
        FileObj -->|Nhập nhằng| PathLogic[Thao tác Đường dẫn Logic]
        FileObj -->|Nhập nhằng| DiskAccess[Truy cập Đĩa Vật lý]
        FileObj -->|Trả về| BooleanFail[Boolean khi thất bại]
    end
    subgraph Modern NIO.2 (java.nio.file)
        PathInt[Giao diện Path] -->|Logic thuần túy| Memory[Biểu diễn trong Bộ nhớ]
        FilesUtil[Lớp Files] -->|Thao tác trên Đĩa| Disk[Truy cập Đĩa Thực tế]
        FilesUtil -->|Ném ra| RichException[Ngoại lệ IOException mô tả chi tiết]
    end
```

##### Ví dụ mã nguồn: So sánh xử lý lỗi

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

##### Chuỗi nguyên nhân - kết quả của các thao tác xóa

```
Gọi File.delete() kiểu cũ trên tệp không tồn tại
  └── Trả về false (Không có ngoại lệ nào được ném ra)
        └── Nhà phát triển quên kiểm tra giá trị trả về
              └── Chương trình tiếp tục chạy dưới giả định sai lầm rằng tệp đã được xóa
                    └── Lỗi âm thầm lan rộng, dẫn đến nguy cơ hỏng dữ liệu/lỗi logic

Gọi Files.delete() của NIO.2 trên tệp không tồn tại
  └── Ném ra NoSuchFileException
        └── JVM ngắt luồng thực thi thông thường
              └── Trình gọi buộc phải bắt/xử lý ngoại lệ
                    └── Ứng dụng thất bại ngay lập tức (fail-fast), cung cấp dấu vết ngăn xếp (stack trace) rõ ràng để gỡ lỗi phân tích
```

#### Các thao tác đường dẫn chính:
* `resolve(Path)`: Ghép hai đường dẫn. Nếu đối số là một đường dẫn tuyệt đối, nó chỉ đơn giản trả về đối số đó.
* `relativize(Path)`: Tính toán đường dẫn tương đối giữa hai đường dẫn (khoảng cách giữa chúng).
* `normalize()`: Giải quyết các phần tử thừa như `.` (thư mục hiện tại) và `..` (thư mục cha).

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

### Lớp tiện ích Files
Lớp `java.nio.file.Files` hoạt động trên các thực thể `Path` và thực sự giao tiếp với hệ thống tệp.
* Các luồng tệp được trả về bởi `Files.lines()`, `Files.list()`, `Files.walk()`, và `Files.find()` bao bọc tài nguyên hệ thống bên dưới (luồng thư mục) và **phải được đóng** thông qua cấu trúc try-with-resources.

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

### Duyệt cây tệp (Walk File Tree)
`Files.walk(Path)` trả về một luồng các đường dẫn dạng lazy, duyệt qua các cấu trúc thư mục theo chiều sâu (depth-first). Để ngăn ngừa vòng lặp vô hạn hoặc các vấn đề về bộ nhớ, bạn có thể chỉ định một độ sâu tối đa (max depth).
Ngoài ra, `Files.walkFileTree()` nhận một lớp con `FileVisitor` để xử lý kiểm soát duyệt chi tiết hơn.

```java
// Streaming directory tree
try (Stream<Path> stream = Files.walk(Path.of("src"), 3)) { // Max depth 3
    stream.forEach(System.out::println);
} catch (IOException e) {
    e.printStackTrace();
}
```

### Các biến trạng thái bộ đệm (position, limit, capacity)
Các hoạt động truyền dữ liệu NIO diễn ra thông qua các kênh (channel) và bộ đệm (buffer). Một `Buffer` là một bộ chứa khối bộ nhớ trong bộ nhớ.
* `capacity`: Kích thước tổng thế của bộ đệm (cố định khi tạo).
* `position`: Chỉ số của phần tử tiếp theo sẽ được đọc hoặc ghi.
* `limit`: Chỉ số của phần tử đầu tiên **không** được phép đọc hoặc ghi.

#### Chuyển đổi Trạng thái:
* `flip()`: Chuẩn bị bộ đệm cho việc đọc sau khi ghi. Đặt `limit = position`, sau đó đặt `position = 0`.
* `clear()`: Chuẩn bị bộ đệm cho việc ghi sau khi đọc. Đặt `position = 0`, `limit = capacity` (các con trỏ được đặt lại; dữ liệu không bị xóa).
* `rewind()`: Đặt lại `position` về 0, cho phép đọc lại dữ liệu đã có trong bộ đệm (giữ nguyên `limit`).

#### Tại sao phương thức flip() của bộ đệm là bắt buộc và sự chuyển đổi các con trỏ trạng thái

Một `Buffer` trong Java NIO là một khối bộ nhớ đơn lẻ được quản lý bởi một tập hợp duy nhất các con trỏ đọc/ghi: `position`, `limit`, và `capacity`. Khi ghi dữ liệu vào bộ đệm, con trỏ `position` di chuyển tiến về phía `limit` (ban đầu bằng `capacity`) để theo dõi chỉ số tiếp theo cần ghi. Nếu bộ đọc cố gắng đọc từ bộ đệm ngay sau khi ghi mà không chuyển đổi chế độ, bộ đệm sẽ cố gắng đọc từ vị trí `position` hiện tại trở đi, dẫn đến việc đọc các byte chưa được khởi tạo/trống hoặc chạm giới hạn ngay lập tức. Gọi `flip()` chuyển đổi bộ đệm từ chế độ ghi sang chế độ đọc bằng cách đặt `limit` bằng `position` hiện tại (đánh dấu ranh giới chính xác của dữ liệu hợp lệ đã ghi) và đặt lại `position` về `0` để việc đọc bắt đầu từ đầu dữ liệu đã ghi.

```mermaid
graph TD
    subgraph WriteMode [Chế độ Ghi (Trạng thái ban đầu)]
        W_Pos[position = 2]
        W_Lim[limit = 10]
        W_Cap[capacity = 10]
        DataW["['H', 'i', _, _, _, _, _, _, _, _]"]
    end
    subgraph AfterFlip [Sau khi gọi flip() (Chế độ Đọc)]
        R_Pos[position = 0]
        R_Lim[limit = 2]
        R_Cap[capacity = 10]
        DataR["['H', 'i' | ranh giới limit, _, _, _, _, _, _, _]"]
    end
    WriteMode -->|gọi flip()| AfterFlip
```

##### Ví dụ mã nguồn: Nguy hiểm của việc quên flip()

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

##### Chuỗi nguyên nhân - kết quả của việc ghi và đọc mà không có flip()

```
Ghi 'H' và 'i' vào bộ đệm
  └── position tiến từ 0 đến 2 (limit giữ nguyên là 10)
        └── Cố gắng đọc trực tiếp bộ đệm (không gọi flip())
              └── get() bắt đầu đọc từ position 2 đến limit 10
                    └── Đọc từ chỉ số 2 đến 9 (dữ liệu bộ đệm chưa khởi tạo) thay vì 'H' và 'i'
                          └── Trả về dữ liệu rác hoặc trống, để lại 'H' và 'i' chưa được đọc

Ghi 'H' và 'i' vào bộ đệm
  └── position tiến từ 0 đến 2 (limit giữ nguyên là 10)
        └── Gọi flip()
              └── limit được đặt bằng 2 (đánh dấu kết thúc dữ liệu hợp lệ), position được đặt lại về 0
                    └── get() bắt đầu đọc từ position 0 đến limit 2
                          └── Đọc chính xác 'H' ở chỉ số 0 và 'i' ở chỉ số 1, dừng lại ở limit 2
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

## Các lỗi thường gặp

### 1. Rò rỉ tài nguyên với `Files.lines()`, `walk()`, hoặc `list()`
Các phương thức này trả về các stream duy trì tay cầm hệ thống tệp đang hoạt động. Nếu bạn không đóng chúng, bạn sẽ bị rò rỉ các mô tả tệp (file descriptor), cuối cùng gây ra các ngoại lệ "Too many open files".
* **Khắc phục**: Luôn bao bọc các stream này trong câu lệnh try-with-resources.

### 2. Bẫy resolve với đường dẫn tuyệt đối
Gọi `path1.resolve(path2)` chỉ đơn giản trả về `path2` nếu `path2` là đường dẫn tuyệt đối, điều này có thể gây ngạc nhiên cho các nhà phát triển đang mong đợi một kết quả ghép đường dẫn.
```java
Path base = Path.of("/home/user");
Path target = Path.of("/etc/config");
System.out.println(base.resolve(target)); // Prints /etc/config, NOT /home/user/etc/config!
```

### 3. Quên gọi flip() trước khi đọc bộ đệm
Sau khi ghi dữ liệu vào một bộ đệm, vị trí `position` của bộ đệm sẽ trỏ đến chỉ số trống tiếp theo. Nếu bạn cố gắng đọc từ nó ngay lập tức mà không gọi `flip()`, bạn sẽ đọc các byte chưa khởi tạo/trống cho đến giới hạn limit (hoặc không đọc được gì nếu position đã ở limit).
* **Quy tắc**: Luôn gọi `buffer.flip()` trước khi đọc từ một bộ đệm, và `buffer.clear()` trước khi ghi vào nó một lần nữa.

---

## Liên kết tham khảo

- [Official Java Path Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Path.html)
- [Official Java Files Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html)
- [Official Java Buffer Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/Buffer.html)
