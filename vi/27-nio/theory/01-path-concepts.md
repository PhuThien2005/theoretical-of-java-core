# NIO / NIO.2 - Phần 1

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần trọng tâm của **NIO / NIO.2**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, thay vì chỉ là từ vựng rời rạc.

## Phạm vi đề mục (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Path` | Một giao diện biểu diễn một đường dẫn phân cấp (hierarchical path) dẫn đến một tệp hoặc thư mục; thay thế `java.io.File` với một API sạch hơn, linh hoạt hơn. |
| `Paths` | Một lớp nhà máy tiện ích (utility factory class) chứa các phương thức tĩnh (như `Paths.get()`) để tạo một đối tượng `Path` từ một chuỗi hoặc một URI. |
| `Files` | Một lớp tiện ích thao tác trên các đối tượng `Path`, cung cấp các phương thức tĩnh để tạo, xóa, sao chép tệp, kiểm tra siêu dữ liệu và truy vấn các luồng thư mục. |
| `StandardOpenOption` | Một enum xác định các tùy chọn khi mở một tập tin (ví dụ: `READ`, `WRITE`, `CREATE`, `APPEND`, `TRUNCATE_EXISTING`). |
| `Đọc/ghi tập tin bằng Files (Read/write file using Files)` | Đọc hoặc ghi toàn bộ nội dung tập tin bằng cách sử dụng các phương thức như `Files.readAllLines()` hoặc truyền luồng qua `Files.lines()`. |
| `Duyệt cây tập tin (Walk file tree)` | Quét cấu trúc thư mục một cách đệ quy bằng cách sử dụng các hoạt động dựa trên luồng như `Files.walk()`, `Files.find()`, hoặc các bộ duyệt tùy chỉnh với `Files.walkFileTree()`. |
| `Sao chép/di chuyển/xóa tập tin (Copy/move/delete file)` | Các thao tác trên đĩa bằng cách sử dụng `Files.copy()`, `Files.move()`, và `Files.delete()` với các tùy chọn sao chép có thể định cấu hình (như `REPLACE_EXISTING`). |
| `Channel` | Một kết nối đến một nguồn/đích I/O (như tệp hoặc socket) có khả năng thực hiện truyền tải dữ liệu không chặn với số lượng lớn. |
| `Buffer` | Một khối bộ nhớ (vùng chứa dữ liệu nguyên thủy) được sử dụng làm nguồn hoặc đích khi đọc từ hoặc ghi vào một kênh `Channel`. |
| `ByteBuffer` | Lớp `Buffer` dựa trên byte quản lý trạng thái bằng các biến: `position`, `limit`, và `capacity`. |

## Ghi chú chi tiết (Detailed Notes)

### Path và Paths (NIO.2)
`java.nio.file.Path` là giải pháp thay thế của NIO.2 cho `java.io.File`. Nó biểu diễn một đường dẫn phân cấp độc lập với hệ thống. Khác với `File`, nó là một giao diện và hỗ trợ các thao tác xử lý đường dẫn phức tạp.
* Lưu ý: Kể từ Java 11, `Path.of(String)` được ưa chuộng hơn `Paths.get(String)`.
* Các thao tác xử lý đường dẫn không truy cập vào hệ thống tập tin thực tế (chúng là các thao tác logic trong bộ nhớ).

#### Tại sao Path và Files lại ưu việt hơn java.io.File (Why Path and Files Are Superior to java.io.File)

Lớp cũ `java.io.File` đồng hóa (conflate) biểu diễn đường dẫn trừu tượng với các hoạt động hệ thống tập tin vật lý, dẫn đến sự phân tách thiết kế kém. Hơn nữa, nhiều phương thức của `java.io.File` (như `delete()` hoặc `createNewFile()`) chỉ trả về một giá trị `boolean` đơn giản khi thất bại thay vì ném ra một ngoại lệ `IOException` mô tả chi tiết, điều này thường khiến các nhà phát triển bỏ qua việc xử lý lỗi và dẫn đến các thất bại thầm lặng (silent failures). Giao diện `Path` của NIO.2 đại diện cho một đường dẫn logic thuần túy trong bộ nhớ, tách biệt hoàn toàn việc thao tác đường dẫn khỏi truy cập hệ thống tập tin. Các hoạt động đĩa thực tế được ủy quyền cho lớp tiện ích `java.nio.file.Files`, lớp này ném ra các ngoại lệ phong phú, cụ thể (chẳng hạn như `NoSuchFileException` hoặc `AccessDeniedException`) buộc lập trình viên phải xử lý lỗi đúng cách và hỗ trợ đắc lực cho quá trình gỡ lỗi (debugging).

```mermaid
graph TD
    subgraph Legacy IO (Classic I/O cũ - java.io.File)
        FileObj["Lớp java.io.File"]
        FileObj -->|Đồng hóa| PathLogic[Thao tác đường dẫn Logic]
        FileObj -->|Đồng hóa| DiskAccess[Truy cập đĩa vật lý]
        FileObj -->|Trả về| BooleanFail[Boolean khi thất bại]
    end
    subgraph Modern NIO.2 (NIO.2 hiện đại - java.nio.file)
        PathInt[Giao diện Path] -->|Logic thuần túy| Memory[Biểu diễn trong bộ nhớ]
        FilesUtil[Lớp Files] -->|Thao tác đĩa| Disk[Truy cập đĩa thực tế]
        FilesUtil -->|Ném ra| RichException[Ngoại lệ IOException mô tả chi tiết]
    end
```

##### Ví dụ mã nguồn: So sánh xử lý lỗi (Code Example: Error Handling Comparison)

```java
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LegacyVsNioErrorHandling {
    public static void main(String[] args) {
        // Lớp java.io.File cũ: Dễ xảy ra lỗi thầm lặng
        File legacyFile = new File("/nonexistent/dir/file.txt");
        boolean isDeleted = legacyFile.delete(); 
        System.out.println("Legacy deleted: " + isDeleted); // In ra: Legacy deleted: false (Không ném ra ngoại lệ!)

        // Giao diện java.nio.file.Path & Files hiện đại: Thất bại nhanh (fail-fast) và mô tả rõ ràng
        Path nioPath = Path.of("/nonexistent/dir/file.txt");
        try {
            Files.delete(nioPath);
        } catch (IOException e) {
            System.err.println("NIO deletion failed: " + e.getClass().getSimpleName());
            // In ra: NIO deletion failed: NoSuchFileException
        }
    }
}
```

##### Chuỗi nguyên nhân - kết quả của thao tác xóa (Cause-Effect Chain of Deletion Operations)

```
Phương thức File.delete() cũ được gọi trên file không tồn tại
  └── Trả về false (Không ném ra ngoại lệ)
        └── Lập trình viên quên kiểm tra giá trị trả về
              └── Chương trình tiếp tục chạy dưới giả định sai lầm rằng tệp đã bị xóa
                    └── Lỗi thầm lặng lan truyền, dẫn đến nguy cơ hỏng dữ liệu/lỗi logic

NIO.2 Files.delete() được gọi trên file không tồn tại
  └── Ném ra NoSuchFileException
        └── JVM ngắt luồng thực thi bình thường
              └── Người gọi buộc phải bắt (catch)/xử lý ngoại lệ
                    └── Ứng dụng thất bại nhanh (fail-fast), cung cấp stack trace rõ ràng cho việc gỡ lỗi
```

#### Các thao tác chính với Path (Key Path Operations):
* `resolve(Path)`: Hợp nhất hai đường dẫn. Nếu đối số truyền vào là một đường dẫn tuyệt đối, nó chỉ đơn giản trả về chính đối số đó.
* `relativize(Path)`: Tính toán đường dẫn tương đối giữa hai đường dẫn (khoảng cách logic giữa chúng).
* `normalize()`: Phân giải các phần tử dư thừa như `.` (thư mục hiện tại) và `..` (thư mục cha).

```java
import java.nio.file.Path;

public class PathDemo {
    public static void main(String[] args) {
        Path p1 = Path.of("/home/user/docs");
        Path p2 = Path.of("project/readme.txt");
        
        // Resolve (hợp nhất)
        Path resolved = p1.resolve(p2);
        System.out.println("Resolved: " + resolved); // /home/user/docs/project/readme.txt
        
        // Relativize (tính khoảng cách tương đối)
        Path start = Path.of("/home/user");
        Path end = Path.of("/home/user/docs/photos");
        System.out.println("Relative: " + start.relativize(end)); // docs/photos
        
        // Normalize (dọn dẹp đường dẫn)
        Path dirty = Path.of("/home/user/docs/../photos/./temp");
        System.out.println("Normalized: " + dirty.normalize()); // /home/user/photos/temp
    }
}
```

### Lớp tiện ích Files (Files Utility Class)
Lớp `java.nio.file.Files` hoạt động trên các thực thể `Path` và thực sự giao tiếp với hệ thống tập tin.
* Các luồng tập tin được trả về bởi `Files.lines()`, `Files.list()`, `Files.walk()`, và `Files.find()` bao bọc các tài nguyên hệ thống cơ sở (luồng thư mục) và **phải được đóng lại** thông qua khối try-with-resources.

```java
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class FilesDemo {
    public static void main(String[] args) {
        Path source = Path.of("source.txt");
        Path target = Path.of("dest.txt");
        
        try {
            // Sao chép kèm tùy chọn ghi đè
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            
            // Xóa an toàn
            boolean deleted = Files.deleteIfExists(target);
            System.out.println("Deleted: " + deleted);
            
            // Đọc luồng các dòng của tệp (PHẢI đóng qua try-with-resources)
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

### Duyệt cây tập tin (Walk File Tree)
`Files.walk(Path)` trả về một luồng Path dạng lười (lazy stream), duyệt qua các cấu trúc thư mục theo chiều sâu (depth-first). Để ngăn ngừa các vòng lặp vô hạn hoặc các vấn đề về bộ nhớ, bạn có thể chỉ định một độ sâu tối đa (max depth).
Ngoài ra, `Files.walkFileTree()` nhận một lớp con của `FileVisitor` để xử lý việc kiểm soát duyệt cây một cách chi tiết hơn.

```java
// Duyệt luồng cây thư mục
try (Stream<Path> stream = Files.walk(Path.of("src"), 3)) { // Độ sâu tối đa là 3
    stream.forEach(System.out::println);
} catch (IOException e) {
    e.printStackTrace();
}
```

### Các biến trạng thái của Buffer (position, limit, capacity) (Buffer State Variables (position, limit, capacity))
Quá trình truyền tải dữ liệu trong NIO xảy ra thông qua các kênh (channels) và bộ đệm (buffers). Một `Buffer` là một khối chứa bộ nhớ trong.
* `capacity`: Kích thước tổng của bộ đệm (cố định khi tạo).
* `position`: Chỉ mục của phần tử tiếp theo để đọc hoặc ghi.
* `limit`: Chỉ mục của phần tử đầu tiên **không nên** được đọc hoặc ghi.

#### Các chuyển đổi trạng thái (State Transitions):
* `flip()`: Chuẩn bị bộ đệm cho việc đọc sau khi ghi. Thiết lập `limit = position`, sau đó gán `position = 0`.
* `clear()`: Chuẩn bị bộ đệm cho việc ghi sau khi đọc. Thiết lập `position = 0`, `limit = capacity` (các con trỏ được đặt lại; dữ liệu cũ không bị xóa).
* `rewind()`: Đặt lại `position` về 0, cho phép đọc lại dữ liệu đã có trong bộ đệm (giữ nguyên `limit`).

#### Tại sao Buffer flip() lại bắt buộc và các chuyển đổi con trỏ trạng thái (Why Buffer flip() is Required and State Pointer Transitions)

Một `Buffer` trong Java NIO là một khối bộ nhớ duy nhất được quản lý bởi một bộ con trỏ đọc/ghi duy nhất: `position`, `limit`, và `capacity`. Khi ghi dữ liệu vào bộ đệm, con trỏ `position` tiến dần về phía `limit` (ban đầu bằng `capacity`) để theo dõi chỉ mục tiếp theo sẽ ghi. Nếu người đọc cố gắng đọc từ bộ đệm ngay sau khi ghi mà không chuyển đổi chế độ, bộ đệm sẽ cố gắng đọc từ chỉ mục `position` hiện tại trở đi, dẫn đến việc đọc các byte chưa khởi tạo/trống (uninitialized/empty bytes) hoặc đụng giới hạn (limit) ngay lập tức. Việc gọi `flip()` chuyển đổi bộ đệm từ chế độ ghi sang chế độ đọc bằng cách đặt `limit` thành `position` hiện tại (đánh dấu chính xác ranh giới của dữ liệu hợp lệ đã được ghi) và đặt lại `position` về `0` để quá trình đọc bắt đầu từ đầu dữ liệu đã ghi.

```mermaid
graph TD
    subgraph Chế độ ghi - Write Mode (Trạng thái ban đầu)
        W_Pos[position = 2]
        W_Lim[limit = 10]
        W_Cap[capacity = 10]
        DataW["['H', 'i', _, _, _, _, _, _, _, _]"]
    end
    subgraph Sau khi gọi flip() (Chế độ đọc - Read Mode)
        R_Pos[position = 0]
        R_Lim[limit = 2]
        R_Cap[capacity = 10]
        DataR["['H', 'i' | ranh giới limit, _, _, _, _, _, _, _]"]
    end
    Chế độ ghi - Write Mode -->|gọi flip()| Sau khi gọi flip()
```

##### Ví dụ mã nguồn: Nguy cơ khi quên gọi flip() (Code Example: The Danger of Forgetting flip())

```java
import java.nio.ByteBuffer;

public class FlipRequirementDemo {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10); // capacity=10, position=0, limit=10
        
        // Ghi hai byte
        buf.put((byte) 'H');
        buf.put((byte) 'i'); // position trở thành 2
        
        // SAI: Đọc không gọi flip()
        System.out.println("Remaining bytes without flip: " + buf.remaining()); // In ra: 8
        System.out.print("Data read without flip: ");
        while (buf.hasRemaining()) {
            System.out.print((char) buf.get()); // Đọc các byte chưa khởi tạo ở chỉ mục từ 2 đến 9
        }
        System.out.println();
        
        // Thiết lập lại con trỏ để thực hiện ví dụ đúng
        buf.position(2); // Đặt lại vị trí như sau khi ghi
        
        // ĐÚNG: Gọi flip() trước khi đọc
        buf.flip(); // limit trở thành 2, position đặt về 0
        System.out.println("Remaining bytes after flip: " + buf.remaining()); // In ra: 2
        System.out.print("Data read after flip: ");
        while (buf.hasRemaining()) {
            System.out.print((char) buf.get()); // In ra: Hi
        }
        System.out.println();
    }
}
```

##### Chuỗi nguyên nhân - kết quả của việc ghi và đọc mà không flip() (Cause-Effect Chain of Writing and Reading Without flip())

```
Ghi 'H' và 'i' vào bộ đệm
  └── position tăng từ 0 lên 2 (limit giữ nguyên ở 10)
        └── Cố gắng đọc bộ đệm trực tiếp (không gọi flip())
              └── get() bắt đầu đọc từ position 2 đến limit 10
                    └── Đọc từ chỉ mục 2 đến 9 (dữ liệu bộ đệm chưa khởi tạo) thay vì 'H' và 'i'
                          └── Trả về dữ liệu trống/rác, bỏ lại 'H' và 'i' chưa được đọc

Ghi 'H' và 'i' vào bộ đệm
  └── position tăng từ 0 lên 2 (limit giữ nguyên ở 10)
        └── Gọi flip()
              └── limit được gán bằng 2 (đánh dấu kết thúc dữ liệu hợp lệ), position được reset về 0
                    └── get() bắt đầu đọc từ position 0 đến limit 2
                          └── Đọc chính xác 'H' ở chỉ mục 0 và 'i' ở chỉ mục 1, dừng lại ở limit 2
```

```java
import java.nio.ByteBuffer;

public class BufferStateDemo {
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10); // capacity = 10, position = 0, limit = 10
        
        // Ghi vào bộ đệm
        buf.put((byte) 'H');
        buf.put((byte) 'i'); // position hiện tại là 2
        
        // Chuyển sang chế độ đọc
        buf.flip(); // limit được gán bằng 2, position được đặt về 0
        
        while (buf.hasRemaining()) {
            System.out.print((char) buf.get()); // Đọc 'H' và 'i'. position trở thành 2.
        }
        System.out.println();
        
        // Clear để sẵn sàng ghi lại
        buf.clear(); // position đặt về 0, limit đặt về 10. Sẵn sàng để ghi.
    }
}
```

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Rò rỉ tài nguyên với Files.lines(), walk(), hoặc list() (Resource Leaks with Files.lines(), walk(), or list())
Các phương thức này trả về các luồng (streams) duy trì các thẻ quản lý hệ thống tập tin (filesystem handles) hoạt động. Nếu bạn không đóng chúng, bạn sẽ bị rò rỉ các bộ mô tả tệp (file descriptors), cuối cùng gây ra ngoại lệ "Too many open files".
* **Giải pháp**: Luôn bao bọc các luồng này trong một câu lệnh try-with-resources.

### 2. Bẫy phân giải tuyệt đối (The Absolute Resolve Trap)
Gọi `path1.resolve(path2)` sẽ chỉ đơn giản trả về `path2` nếu `path2` là tuyệt đối, điều này có thể gây ngạc nhiên cho các lập trình viên đang mong đợi một đầu ra được hợp nhất.
```java
Path base = Path.of("/home/user");
Path target = Path.of("/etc/config");
System.out.println(base.resolve(target)); // In ra /etc/config, KHÔNG PHẢI /home/user/etc/config!
```

### 3. Quên flip() trước khi đọc một buffer (Forgetting to flip() before reading a buffer)
Sau khi ghi dữ liệu vào một bộ đệm, con trỏ `position` của bộ đệm trỏ đến chỉ mục trống tiếp theo. Nếu bạn cố gắng đọc từ nó ngay lập tức mà không gọi `flip()`, bạn sẽ đọc các byte chưa khởi tạo/trống cho đến giới hạn limit (hoặc không đọc được gì nếu position đã ở ranh giới limit).
* **Quy tắc**: Luôn gọi `buffer.flip()` trước khi đọc từ bộ đệm, và `buffer.clear()` trước khi ghi lại vào nó.

---

## Liên kết tham khảo (Reference Links)

- [Tài liệu chính thức về Path trong Java](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Path.html)
- [Tài liệu chính thức về Files trong Java](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html)
- [Tài liệu chính thức về Buffer trong Java](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/Buffer.html)
