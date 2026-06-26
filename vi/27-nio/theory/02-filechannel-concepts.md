# NIO / NIO.2 - Phần 2

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần trọng tâm của **NIO / NIO.2**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, thay vì chỉ là từ vựng rời rạc.

## Phạm vi đề mục (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `FileChannel` | Một kênh an toàn luồng (thread-safe), hiệu năng cao để đọc, ghi, ánh xạ và khóa các tệp; hỗ trợ các thao tác truy cập ngẫu nhiên (random access). |
| `Basic Selector` | Một bộ ghép kênh (multiplexor) của các kênh có thể lựa chọn, cho phép một luồng duy nhất quản lý và giám sát nhiều kênh mạng không chặn (các socket). |
| `Basic Asynchronous IO` | Các hoạt động I/O tệp không chặn chạy bất đồng bộ, trả về một đối tượng `Future` hoặc thực thi một hàm gọi lại `CompletionHandler` khi hoàn tất. |

## Ghi chú chi tiết (Detailed Notes)

### FileChannel
`java.nio.channels.FileChannel` là một kênh để đọc, ghi, ánh xạ và thao tác các tập tin.
* **Lấy một FileChannel**: Bạn có thể mở trực tiếp một kênh thông qua `FileChannel.open(path, options)`, hoặc lấy một kênh từ các luồng cũ: `fileInputStream.getChannel()` hoặc `fileOutputStream.getChannel()`.
* **Truy cập ngẫu nhiên (Random Access)**: Khác với các luồng (streams) vốn có tính tuần tự, `FileChannel` có phương thức `position(long)` cho phép bạn nhảy đến bất kỳ chỉ mục nào trong tệp để thực hiện việc đọc hoặc ghi.
* **Khóa file (File Locking)**: `FileChannel` hỗ trợ khóa các vùng của tệp thông qua `lock()` (chặn cho đến khi lấy được khóa) hoặc `tryLock()` (không chặn, trả về null nếu tệp đã bị khóa). Các khóa có thể được chia sẻ (chỉ đọc - read-only) hoặc độc quyền (chỉ ghi - write-only).

```java
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("temp.txt", "rw");
             FileChannel channel = file.getChannel()) {
            
            // 1. Ghi vào kênh channel
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            buf.put("Hello FileChannel".getBytes());
            buf.flip();
            
            while (buf.hasRemaining()) {
                channel.write(buf);
            }
            
            // 2. Đọc từ một vị trí cụ thể
            channel.position(6); // Nhảy đến chỉ mục 6 (bỏ qua "Hello ")
            buf.clear();
            int bytesRead = channel.read(buf);
            
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char) buf.get()); // In ra "FileChannel"
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### Tại sao các mô hình luồng BIO và NIO khác nhau đối với I/O chặn và không chặn (Why BIO and NIO Thread Models Differ for Blocking vs Non-Blocking I/O)

Các API I/O truyền thống của Java (BIO) hoạt động trên mô hình đồng bộ, chặn (blocking) trong đó mọi thao tác I/O đều chặn luồng đang thực thi cho đến khi dữ liệu được đọc hoặc ghi hoàn toàn. Khi một máy chủ xử lý nhiều máy khách (clients) bằng BIO, nó buộc phải phân bổ một luồng riêng cho mỗi kết nối của máy khách để ngăn máy khách chậm chạp chặn tất cả các máy khách khác. Mô hình "một luồng cho mỗi kết nối" (thread-per-connection) này mở rộng rất kém vì mỗi luồng tiêu tốn dung lượng bộ nhớ hệ thống đáng kể (thường là kích thước ngăn xếp stack 1MB) và gây ra quá tải CPU do liên tục phải chuyển đổi ngữ cảnh luồng (thread context switching). Java NIO giải quyết hạn chế này bằng cách giới thiệu các kênh không chặn (non-blocking channels), trong đó các thao tác như `read()` hoặc `write()` trả về ngay lập tức với số lượng byte được chuyển tải (có thể bằng không) thay vì phải đợi dữ liệu sẵn sàng. Thiết kế này cho phép một nhóm nhỏ các luồng đang hoạt động—hoặc thậm chí chỉ một luồng đơn lẻ—quản lý hiệu quả hàng nghìn kết nối hoạt động đồng thời.

```mermaid
graph TD
    subgraph BIO Chặn (Mô hình luồng cho mỗi kết nối - Thread-per-Connection)
        Client1[Client 1] -->|Gây chặn| Thread1[Luồng 1 (Thread 1)]
        Client2[Client 2] -->|Gây chặn| Thread2[Luồng 2 (Thread 2)]
        Client3[Client 3] -->|Gây chặn| Thread3[Luồng 3 (Thread 3)]
        Thread1 -->|Ngủ/Rảnh rỗi| CPU_BIO[Quá tải chuyển đổi ngữ cảnh CPU]
    end
    subgraph NIO Không chặn (I/O đa lộ - Multiplexed I/O)
        ClientN1[Client 1] --> Channel1[Kênh 1 (Channel 1)]
        ClientN2[Client 2] --> Channel2[Kênh 2 (Channel 2)]
        ClientN3[Client 3] --> Channel3[Kênh 3 (Channel 3)]
        Channel1 & Channel2 & Channel3 -->|Được đăng ký| Sel[Bộ chọn Selector]
        Sel -->|Được giám sát bởi| SingleThread[Một luồng duy nhất]
        SingleThread -->|Hiệu quả| CPU_NIO[Xử lý CPU]
    end
```

##### Ví dụ mã nguồn: Cấu hình Socket chặn so với Kênh không chặn (Code Example: Blocking Socket vs Non-Blocking Channel Configuration)

```java
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class BioVsNioSockets {
    public static void main(String[] args) throws IOException {
        // BIO: Server socket luôn chặn trên phương thức accept()
        try (ServerSocket bioServer = new ServerSocket(8080)) {
            System.out.println("BIO Server waiting (blocking)...");
            // Socket clientSocket = bioServer.accept(); // Chặn việc thực thi tại đây cho đến khi có client kết nối
        }

        // NIO: Server socket channel có thể được cấu hình ở chế độ không chặn
        try (ServerSocketChannel nioServer = ServerSocketChannel.open()) {
            nioServer.bind(new InetSocketAddress(8081));
            nioServer.configureBlocking(false); // Kích hoạt chế độ không chặn
            System.out.println("NIO Server waiting (non-blocking)...");
            
            SocketChannel clientChannel = nioServer.accept(); // Trả về lập tức (null nếu chưa có client nào kết nối)
            if (clientChannel == null) {
                System.out.println("No connections yet. Thread can perform other tasks.");
            }
        }
    }
}
```

##### Chuỗi nguyên nhân - kết quả của việc chặn luồng trong BIO so với NIO (Cause-Effect Chain of Thread Blocking in BIO vs NIO)

```
[Xử lý kết nối trong BIO]
Client kết nối và chuyển sang trạng thái rảnh rỗi (idle)
  └── Luồng máy chủ gọi socket.read()
        └── Luồng bị chặn, chuyển sang trạng thái WAITING/BLOCKED của hệ điều hành
              └── OS thực hiện chuyển đổi ngữ cảnh CPU để chạy các luồng khác
                    └── Tiêu tốn bộ nhớ cao (1MB/luồng) + CPU Thrashing -> Máy chủ crash dưới tải cao

[Xử lý kết nối trong NIO]
Client kết nối và chuyển sang trạng thái rảnh rỗi (idle)
  └── Luồng máy chủ gọi channel.read()
        └── Phương thức trả về 0 byte đã đọc ngay lập tức (không chặn)
              └── Luồng không bị chặn và sẵn sàng xử lý các kênh đang hoạt động khác
                    └── Luồng đơn lẻ xử lý hàng nghìn client rảnh rỗi -> Khả năng mở rộng cao và tốn ít bộ nhớ
```

### Bộ chọn Selector cơ bản (I/O không chặn) (Basic Selector (Non-blocking I/O))
Một `Selector` cho phép một luồng duy nhất giám sát nhiều kênh mạng có thể lựa chọn (ví dụ: `SocketChannel`, `ServerSocketChannel`) đối với các sự kiện như yêu cầu kết nối (`OP_ACCEPT`), sẵn sàng đọc (`OP_READ`), hoặc sẵn sàng ghi (`OP_WRITE`).
* **Đa lộ hóa (Multiplexing)**: Đây là nền tảng của các máy chủ web có khả năng mở rộng cực cao (như Netty), loại bỏ hoàn toàn sự cần thiết của kiến trúc một luồng cho mỗi kết nối.
* **Quan trọng**: Các Selector **chỉ** hoạt động với các lớp kế thừa (Inheritance) từ `SelectableChannel`. Các kênh tệp (file channels) **không** kế thừa từ `SelectableChannel` và không thể đặt ở chế độ không chặn hoặc đăng ký với một selector.

#### Tại sao cơ chế đa lộ Selector hoạt động và tại sao FileChannel không thể sử dụng nó (Why Selector Multiplexing Works and Why FileChannel Cannot Use It)

Cơ chế `Selector` triển khai I/O đa lộ bằng cách tích hợp với phân hệ thăm dò sự kiện (event-polling subsystem) gốc của hệ điều hành (như `epoll` trên Linux, `kqueue` trên macOS, hoặc `IOCP` trên Windows). Các nhân hệ điều hành này giám sát các thẻ mô tả tệp của socket đối với các thay đổi bất đồng bộ trong bộ đệm mạng và chỉ đánh thức luồng selector khi một kênh sẵn sàng để đọc, ghi hoặc chấp nhận kết nối. Tuy nhiên, hệ thống tập tin được thiết kế khác với các socket mạng; các tập tin theo mặt khái niệm luôn "sẵn sàng" để được đọc hoặc ghi vì dữ liệu được lưu trữ tĩnh trên đĩa, và các hệ thống tập tin OS tiêu chuẩn không hỗ trợ chế độ không chặn gốc cho các thẻ mô tả tệp thông thường. Vì `FileChannel` không thể đưa vào trạng thái không chặn, nó không kế thừa từ `SelectableChannel` và không thể đăng ký với một `Selector`. Việc cố gắng đăng ký hoặc cấu hình một `FileChannel` là không chặn sẽ dẫn đến các ngoại lệ thời gian chạy, nghĩa là bất kỳ hoạt động truy cập tệp không chặn nào thay vào đó phải được giả lập bằng cách sử dụng `AsynchronousFileChannel` được hỗ trợ bởi các luồng làm việc nền (background worker threads).

```mermaid
graph TD
    subgraph Đăng ký Selector (Selector Registration)
        Sel[Selector]
        SocketCh[SocketChannel] -->|Kế thừa SelectableChannel| Sel
        SrvSocketCh[ServerSocketChannel] -->|Kế thừa SelectableChannel| Sel
        FileCh[FileChannel] -->|Không kế thừa SelectableChannel| Err["Ném ra IllegalBlockingModeException / Lỗi biên dịch"]
    end
    subgraph Phân hệ Hệ điều hành (OS Subsystem)
        Sel -->|Đa lộ hóa qua epoll/kqueue| OS_Kernel[Vòng lặp sự kiện Nhân OS]
        OS_Kernel -->|Giám sát| NetBuf[Các Socket Mạng]
        OS_Kernel -.->|Không hỗ trợ không chặn| DiskFile[Các tệp trên đĩa]
    end
```

##### Ví dụ mã nguồn: Cố gắng đăng ký FileChannel (Code Example: FileChannel Registration Attempt)

```java
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SelectorFileChannelFailure {
    public static void main(String[] args) {
        try (Selector selector = Selector.open();
             FileChannel fileChannel = FileChannel.open(Path.of("temp.txt"), StandardOpenOption.READ)) {
            
            // FileChannel KHÔNG có phương thức configureBlocking(false).
            // Cố gắng đăng ký nó trực tiếp:
            System.out.println("Attempting to register FileChannel with Selector...");
            // fileChannel.register(selector, SelectionKey.OP_READ); // LỖI BIÊN DỊCH: phương thức register() không được định nghĩa trên FileChannel
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

##### Chuỗi nguyên nhân - kết quả của việc đăng ký FileChannel với Selector (Cause-Effect Chain of FileChannel Selector Registration)

```
Thiết kế hệ thống tập tin OS coi các tệp trên đĩa là luôn sẵn sàng
  └── Java FileChannel không kế thừa từ SelectableChannel
        └── FileChannel thiếu các phương thức configureBlocking() và register()
              └── Cố gắng đăng ký FileChannel với một Selector thất bại tại thời điểm biên dịch
                    └── Lập trình viên phải sử dụng AsynchronousFileChannel hoặc các luồng tùy chỉnh để tránh chặn luồng chính
```

```java
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false); // Chế độ không chặn là BẮT BUỘC đối với Selector!
        
        // Đăng ký kênh cho sự kiện kết nối
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        // Vòng lặp sự kiện chọn không chặn
        while (true) {
            int readyChannels = selector.select(1000); // Đợi tối đa 1 giây
            if (readyChannels == 0) continue;
            
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // Chấp nhận kết nối
                } else if (key.isReadable()) {
                    // Đọc dữ liệu
                }
                keyIterator.remove(); // Bắt buộc phải xóa key đã xử lý!
            }
            break; // Dừng demo
        }
        serverChannel.close();
        selector.close();
    }
}
```

### I/O bất đồng bộ cơ bản (Basic Asynchronous I/O)
NIO.2 đã giới thiệu các kênh bất đồng bộ (ví dụ: `AsynchronousFileChannel`) thực hiện các hoạt động trong một nhóm luồng nền.
* **Các phương pháp nhận kết quả**:
  1. **Dựa trên Future**: Phương thức read/write trả về một đối tượng `Future<Integer>`. Gọi `.get()` sẽ chặn cho đến khi hoàn thành.
  2. **Dựa trên Callback**: Bạn truyền một lớp `CompletionHandler<Integer, Attachment>` thực thi mã gọi lại `completed()` hoặc `failed()` khi hoàn tất.

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
            
            // Đọc bất đồng bộ bắt đầu từ vị trí 0
            Future<Integer> operation = asyncChannel.read(buffer, 0);
            
            // Đợi hoàn thành (giả lập chờ đợi)
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

## Nghiên cứu tình huống: I/O file hiệu năng cao với Ánh xạ bộ nhớ trực tiếp (Case Study: High-Performance File I/O with Direct Memory Mapping)

### Bài toán (Problem)
Đọc và ghi các tệp rất lớn (ví dụ: 1GB) có thể làm cạn kiệt bộ nhớ JVM heap hoặc gây ra chi phí gom rác (GC overhead) cao nếu tải chúng vào các mảng byte.

### Giải pháp: Các file ánh xạ bộ nhớ (Solution: Memory-Mapped Files)
Bằng cách sử dụng `FileChannel.map()`, chúng ta ánh xạ một vùng của tệp trực tiếp vào bộ nhớ vật lý (không gian bộ nhớ ảo nằm ngoài JVM heap), trả về một `MappedByteBuffer`.
* **Không sao chép (Zero-Copy)**: Hệ điều hành ánh xạ các trang đĩa trực tiếp vào các trang bộ nhớ, bỏ qua việc sao chép dữ liệu giữa bộ đệm hạt nhân và bộ đệm JVM heap.
* **Truy cập trực tiếp**: Các sửa đổi đối với `MappedByteBuffer` được hệ điều hành tự động lan truyền vào tập tin cơ sở.

#### Tại sao ánh xạ bộ nhớ lại nhanh và cơ chế không sao chép (Zero-Copy) (Why Memory-Mapping is Fast and the Zero-Copy Mechanism)

Trong các thao tác I/O tập tin Java tiêu chuẩn (sử dụng luồng hoặc đọc kênh tiêu chuẩn), việc đọc một khối dữ liệu đòi hỏi quy trình "sao chép kép": trước tiên OS đọc dữ liệu từ đĩa vào bộ nhớ đệm trang của không gian hạt nhân (kernel-space page cache), sau đó sao chép nó qua ranh giới không gian người dùng vào một mảng byte bên trong JVM heap. Thao tác sao chép thứ hai này làm phát sinh chi phí CPU đáng kể, sử dụng băng thông bộ nhớ và áp lực gom rác (garbage collection pressure) lớn khi đọc các tệp lớn. `FileChannel.map()` giải quyết điểm nghẽn này bằng cách sử dụng cuộc gọi hệ thống `mmap()` gốc của hệ điều hành, ánh xạ trực tiếp các khối nhị phân của tệp vào một vùng của không gian địa chỉ bộ nhớ ảo của ứng dụng. `MappedByteBuffer` kết quả nằm bên ngoài JVM heap tiêu chuẩn, cho phép chương trình Java truy cập các byte của tệp trực tiếp từ bộ nhớ đệm trang OS mà không cần bất kỳ thao tác sao chép bộ nhớ sang bộ nhớ nào. Việc truy cập trực tiếp này hoạt động ở tốc độ phần cứng gốc, và hệ điều hành xử lý việc đẩy các trang bộ nhớ đã sửa đổi ngược trở lại ổ đĩa vật lý một cách bất đồng bộ trong chế độ nền.

```mermaid
graph TD
    subgraph I/O tiêu chuẩn (Sao chép kép - Double-Copy)
        Disk[Ổ đĩa vật lý] -->|1. Sao chép| KernelBuf[Bộ nhớ đệm trang nhân OS]
        KernelBuf -->|2. Sao chép| JVMHeap[Bộ nhớ đệm JVM Heap]
        JVMHeap -->|Đọc| UserApp[Mã ứng dụng Java]
    end
    subgraph I/O ánh xạ bộ nhớ (Sao chép không - Zero-Copy)
        Disk2[Ổ đĩa vật lý] -->|1. Lỗi trang nạp trang OS| PageCache[Bộ nhớ đệm trang OS / RAM vật lý]
        PageCache <-->|Ánh xạ qua bộ nhớ ảo| MappedBuf[MappedByteBuffer trong không gian địa chỉ ảo]
        UserApp2[Mã ứng dụng Java] <-->|Đọc/Ghi trực tiếp| MappedBuf
    end
```

##### Ví dụ mã nguồn: Đọc và ghi ánh xạ bộ nhớ (Code Example: Memory-Mapped Reading and Writing)

```java
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedZeroCopyDemo {
    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("mapped_temp.dat", "rw");
             FileChannel channel = file.getChannel()) {
            
            long size = 1024 * 1024; // 1MB
            
            // Ánh xạ tệp trực tiếp vào bộ nhớ ảo (chế độ READ_WRITE)
            MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, size);
            
            // Ghi trực tiếp vào bộ nhớ đệm trang
            mappedBuffer.put(0, (byte) 'Z');
            mappedBuffer.put(100, (byte) 'A');
            
            // Đọc trực tiếp từ bộ nhớ đệm trang
            System.out.println("Byte at 0: " + (char) mappedBuffer.get(0)); // In ra: Z
            System.out.println("Byte at 100: " + (char) mappedBuffer.get(100)); // In ra: A
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

##### Chuỗi nguyên nhân - kết quả của việc truy cập file ánh xạ bộ nhớ (Cause-Effect Chain of Memory-Mapped File Access)

```
Gọi FileChannel.map()
  └── OS ánh xạ các byte tệp tới địa chỉ bộ nhớ ảo qua cuộc gọi hệ thống mmap()
        └── Đối tượng MappedByteBuffer được tạo trỏ tới địa chỉ ảo bên ngoài JVM heap
              └── Ứng dụng truy cập các phần tử bộ đệm (ví dụ: mappedBuffer.get())
                    └── Lỗi trang (Page Fault) được kích hoạt nếu trang chưa được lưu trong RAM vật lý
                          └── OS tải trang từ đĩa trực tiếp vào bộ nhớ đệm trang OS (OS Page Cache)
                                └── Luồng JVM đọc bộ nhớ trực tiếp từ Page Cache không cần sao chép sang JVM heap
```

```java
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMapCaseStudy {
    public static void main(String[] args) throws Exception {
        try (RandomAccessFile file = new RandomAccessFile("largefile.dat", "rw");
             FileChannel channel = file.getChannel()) {
             
            // Ánh xạ 10MB của tệp trực tiếp vào bộ nhớ
            long mapSize = 10 * 1024 * 1024; // 10MB
            MappedByteBuffer out = channel.map(FileChannel.MapMode.READ_WRITE, 0, mapSize);
            
            // Ghi trực tiếp vào memory map
            for (int i = 0; i < mapSize; i++) {
                out.put((byte) 'A');
            }
            System.out.println("Finished writing 10MB to mapped file.");
        }
    }
}
```

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Cố gắng đăng ký một FileChannel với một Selector (Trying to Register a FileChannel with a Selector)
`FileChannel` không triển khai `SelectableChannel`. Nó không thể đặt ở chế độ không chặn (`configureBlocking(false)`) và việc cố gắng đăng ký nó với một selector sẽ ném ra ngoại lệ `IllegalBlockingModeException`.
* **Quy tắc**: Các Selector chỉ dành riêng cho các loại kênh có thể lựa chọn và dựa trên mạng (như Sockets).

### 2. Giả định khóa file có thể chặn giữa các JVM (Assuming File Locks are Cross-JVM Blockers)
Khóa tập tin trong Java (`FileChannel.lock()`) có phạm vi toàn JVM. Các luồng khác nhau trong **cùng một** JVM vẫn có thể truy cập tệp đồng thời bất kể có khóa hay không, và một số hệ điều hành coi các khóa này là khóa khuyến nghị (advisory locks), nghĩa là các tiến trình không hợp tác trên cùng một hệ điều hành vẫn có thể bỏ qua chúng để ghi đè.
* **Giải pháp**: Không dựa vào khóa tập tin để đồng bộ hóa luồng trong cùng một JVM; hãy sử dụng các khóa đồng thời tiêu chuẩn thay thế.

---

## Liên kết tham khảo (Reference Links)

- [Tài liệu chính thức về FileChannel trong Java](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/channels/FileChannel.html)
- [Tài liệu chính thức về Selector trong Java](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/channels/Selector.html)
- [Tài liệu chính thức về AsynchronousFileChannel trong Java](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/channels/AsynchronousFileChannel.html)
