# Quản Lý Bộ Nhớ Java - Phần 2 (Java Memory Management - Part 2)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này tập trung vào một phần chuyên sâu của **Quản Lý Bộ Nhớ Java**. Hãy học từng khái niệm dưới dạng một quy tắc thực tế trong Java, chứ không chỉ ghi nhớ thuật ngữ lý thuyết.

## Đề Cương Bao Phủ (Outline Coverage)

- **`Weak reference`** — Tham chiếu yếu (weak reference) là một khái niệm cụ thể trong Quản lý Bộ nhớ Java; tìm hiểu quy tắc JVM, các trường hợp sử dụng hợp lệ và lỗi xảy ra thay vì chỉ nhớ tên gọi.
- **`Soft reference`** — Tham chiếu mềm (soft reference) là một khái niệm cụ thể trong Quản lý Bộ nhớ Java; tìm hiểu quy tắc JVM, các trường hợp sử dụng hợp lệ và lỗi xảy ra thay vì chỉ nhớ tên gọi.
- **`Phantom reference`** — Tham chiếu ảo (phantom reference) là một khái niệm cụ thể trong Quản lý Bộ nhớ Java; tìm hiểu quy tắc JVM, các trường hợp sử dụng hợp lệ và lỗi xảy ra thay vì chỉ nhớ tên gọi.
- **`Garbage Collection`** — Thu gom rác (Garbage Collection) thu hồi bộ nhớ từ các đối tượng không còn có thể tiếp cận được.
- **`Conditions for an object to be GC'd`** — Các điều kiện để một đối tượng bị thu gom rác; tìm hiểu quy tắc JVM, các trường hợp sử dụng hợp lệ và lỗi xảy ra thay vì chỉ nhớ tên gọi.
- **`System.gc()`** — `System.gc()` là một phương thức cụ thể trong Quản lý Bộ nhớ Java; tìm hiểu quy tắc JVM, các trường hợp sử dụng hợp lệ và lỗi xảy ra thay vì chỉ nhớ tên gọi.
- **`Finalization, finalize() deprecated`** — Các cơ chế finalize, và lý do tại sao phương thức `finalize()` bị phản đối sử dụng (deprecated).
- **`Memory leak in Java`** — Rò rỉ bộ nhớ trong Java (memory leak) là một khái niệm cụ thể trong Quản lý Bộ nhớ Java; tìm hiểu quy tắc JVM, các trường hợp sử dụng hợp lệ và lỗi xảy ra thay vì chỉ nhớ tên gọi.

## Ghi Chú Chi Tiết

### Tham chiếu yếu (Weak reference)

Một **Tham Chiếu Yếu (Weak Reference)** (được đại diện bởi lớp `java.lang.ref.WeakReference`) không ngăn cản đối tượng được tham chiếu (referent) bị thu hồi bởi bộ thu gom rác (Garbage Collector).

#### Quy tắc JVM
- Nếu một đối tượng chỉ có thể tiếp cận được thông qua các tham chiếu yếu (không có đường dẫn tham chiếu mạnh hoặc tham chiếu mềm nào từ các GC Root), Garbage Collector sẽ giải phóng nó trong chu kỳ thu gom tiếp theo, bất kể bộ nhớ heap có bị thiếu hay không.
- Thường được sử dụng cho các ánh xạ siêu dữ liệu, ánh xạ chuẩn hóa (canonicalizing mapping), hoặc các bộ nhớ đệm (như lớp `java.util.WeakHashMap`).

#### Ví Dụ Mã Nguồn: Hành Vi Của WeakReference
```java
import java.lang.ref.WeakReference;

public class WeakRefDemo {
    public static void main(String[] args) {
        // Tham chiếu mạnh 'bigObject' trỏ tới một chuỗi String lớn trên heap
        String bigObject = new String("PayloadData");
        
        // Tham chiếu yếu trỏ tới cùng một đối tượng trên heap
        WeakReference<String> weakRef = new WeakReference<>(bigObject);
        
        System.out.println("Trước khi GC: " + weakRef.get()); // In ra "PayloadData"
        
        // Cắt đứt tham chiếu mạnh
        bigObject = null; 
        
        // Yêu cầu GC chạy (chỉ phục vụ mục đích minh họa, không làm điều này trong môi trường production)
        System.gc(); 
        
        // Đối tượng đã bị thu gom vì chỉ còn có thể tiếp cận qua tham chiếu yếu
        System.out.println("Sau khi GC: " + weakRef.get()); // In ra null
    }
}
```

### Tham chiếu mềm (Soft reference)

Một **Tham Chiếu Mềm (Soft Reference)** (được đại diện bởi lớp `java.lang.ref.SoftReference`) là một kiểu tham chiếu mạnh hơn tham chiếu yếu, được thiết kế cho các bộ nhớ đệm nhạy cảm với dung lượng bộ nhớ.

#### Quy tắc JVM
- Một đối tượng chỉ có thể tiếp cận được qua tham chiếu mềm (softly reachable) sẽ sống sót qua các chu kỳ thu gom rác thông thường.
- JVM sẽ chỉ thu hồi các đối tượng được tham chiếu mềm nếu nó sắp hết bộ nhớ (thông thường là ngay trước khi ném ra ngoại lệ `OutOfMemoryError`).
- Các triển khai của JVM sẽ cố gắng giải phóng các đối tượng tham chiếu mềm đã ở trạng thái nhàn rỗi (idle) lâu nhất.

#### Ví Dụ Mã Nguồn: Sử Dụng SoftReference
```java
import java.lang.ref.SoftReference;

public class SoftRefDemo {
    public static void main(String[] args) {
        String data = new String("CachedValue");
        SoftReference<String> softRef = new SoftReference<>(data);
        
        data = null; // Cắt đứt tham chiếu mạnh
        
        System.gc(); // Đề xuất GC chạy
        
        // Sống sót qua chu kỳ GC thông thường vì bộ nhớ chưa bị thiếu
        System.out.println("Giá trị tham chiếu mềm: " + softRef.get()); // In ra "CachedValue"
    }
}
```

### Tham chiếu ảo (Phantom reference)

Một **Tham Chiếu Ảo (Phantom Reference)** (được đại diện bởi lớp `java.lang.ref.PhantomReference`) là kiểu tham chiếu yếu nhất, được sử dụng cho việc dọn dẹp tài nguyên hậu kỳ (post-mortem cleanup).

#### Quy tắc JVM
- Khác với tham chiếu yếu và tham chiếu mềm, việc gọi phương thức `.get()` trên một `PhantomReference` **luôn luôn trả về `null`**.
- Nó bắt buộc phải được tạo cùng với một hàng đợi tham chiếu `ReferenceQueue`.
- Khi JVM xác định một đối tượng chỉ có thể tiếp cận qua tham chiếu ảo, nó sẽ đưa tham chiếu ảo đó vào hàng đợi. Lập trình viên có thể kiểm tra hàng đợi này để thực hiện các hành động dọn dẹp trước (như giải phóng bộ nhớ native ngoài heap).
- Khác với các đối tượng sử dụng finalize, bộ nhớ của đối tượng không tự động được giải phóng; tham chiếu ảo phải được xóa bỏ thông qua phương thức `phantomRef.clear()` để cho phép thu hồi bộ nhớ hoàn toàn.

### Thu Gom Rác (Garbage Collection)

Thu gom rác (Garbage Collection - GC) là quá trình quản lý bộ nhớ tự động trong JVM nhằm thu hồi bộ nhớ heap bị chiếm dụng bởi các đối tượng không còn có thể tiếp cận được bởi ứng dụng.

#### Quy tắc JVM
- GC hoạt động bất đồng bộ ở chế độ nền. Nó tìm kiếm các đối tượng không thể tiếp cận, giải phóng bộ nhớ của chúng và có thể nén bộ nhớ heap để ngăn chặn hiện tượng phân mảnh.
- Ứng dụng sẽ bị dừng hoặc gặp các khoảng dừng tạm thời (Stop-The-World) tùy thuộc vào thuật toán GC được sử dụng (ví dụ: G1, ZGC, Parallel GC).

### Các Điều Kiện Để Một Đối Tượng Bị Thu Gom Rác

Một đối tượng đủ điều kiện bị thu gom rác nếu nó không còn có thể tiếp cận được từ bất kỳ **GC Root** nào.

#### GC Root là gì?
- Các biến cục bộ và tham số trong các ngăn xếp luồng (thread stack) đang hoạt động.
- Các trường tĩnh của các lớp đã được tải.
- Các tham chiếu toàn cục và cục bộ JNI (Java Native Interface).
- Trình tải lớp của hệ thống (system class loader) và các tham chiếu nội bộ đang hoạt động của JVM.

#### Đảo Cô Lập (Islands of Isolation)
- Nếu Đối tượng A tham chiếu đến Đối tượng B, và Đối tượng B tham chiếu ngược lại Đối tượng A, chúng tạo ra một vòng tham chiếu chéo.
- Nếu cả A và B đều không thể tiếp cận được từ bất kỳ GC Root nào, chúng sẽ tạo thành một **đảo cô lập (island of isolation)**.
- GC sẽ thu hồi bộ nhớ của cả hai đối tượng này, ngay cả khi chúng có các biến tham chiếu khác null trỏ vào nhau.

#### Ví Dụ Mã Nguồn: Điều Kiện Đủ Để GC & Vòng Tham Chiếu Chéo
```java
public class GCEligibilityDemo {
    public static void main(String[] args) {
        Node n1 = new Node("First");
        Node n2 = new Node("Second");
        
        n1.next = n2;
        n2.next = n1; // n1 và n2 tham chiếu lẫn nhau (phụ thuộc vòng)
        
        n1 = null; // Đối tượng "First" vẫn có thể tiếp cận được qua n2.next
        // "First" chưa đủ điều kiện để bị GC thu hồi.
        
        n2 = null; // Đối tượng "Second" không còn có thể tiếp cận từ ngăn xếp của main.
        // Cả n1 và n2 hiện tại đều đã bị cô lập khỏi các GC Root.
        // Cả hai đối tượng Node này hiện tại đều đủ điều kiện bị thu gom rác.
    }
}

class Node {
    String name;
    Node next;
    Node(String name) { this.name = name; }
}
```

### System.gc()

Việc gọi `System.gc()` hoặc `Runtime.getRuntime().gc()` là một đề xuất JVM thực hiện nỗ lực thu gom các đối tượng không sử dụng.

#### Quy tắc JVM
- Đây đơn thuần chỉ là một **gợi ý** hoặc một yêu cầu gửi tới JVM. JVM có quyền hoàn toàn bỏ qua lời gọi này (ví dụ: nếu cấu hình với tham số `-XX:+DisableExplicitGC`).
- Không có bất kỳ đảm bảo nào rằng GC sẽ chạy ngay lập tức, hoặc tất cả các đối tượng đủ điều kiện sẽ được thu hồi sau khi gọi phương thức này.
- Gọi `System.gc()` cực kỳ tốn kém và có thể làm đóng băng các luồng của ứng dụng trong các chu kỳ thu gom rác lớn (major collection).

### Cơ Chế Finalize và Việc finalize() Bị Phản Đối Sử Dụng (Deprecated)

Phương thức `finalize()` ban đầu được thiết kế để thực hiện việc dọn dẹp tài nguyên trước khi một đối tượng bị Garbage Collector giải phóng.

#### Quy tắc JVM
- `finalize()` đã bị **phản đối sử dụng (deprecated) kể từ Java 9** và bị vô hiệu hóa/loại bỏ hoàn toàn trong các phiên bản Java hiện đại.
- **Tại sao nó thất bại**: Nó gây ra thời gian thực thi không thể đoán trước, chi phí hiệu năng nặng nề, làm chậm bộ thu gom rác, và tạo ra các lỗ hổng bảo mật (ví dụ như tấn công hồi sinh đối tượng - finalize attack nơi các đối tượng được tạo một nửa có thể được hồi sinh).
- **Các Giải Pháp Thay Thế Hiện Đại**:
  - Triển khai giao diện `java.lang.AutoCloseable` và sử dụng câu lệnh **try-with-resources** để dọn dẹp tài nguyên một cách xác định (như đóng file, đóng socket).
  - Sử dụng lớp `java.lang.ref.Cleaner` hoặc các tham chiếu ảo (phantom reference) cho việc dọn dẹp tài nguyên native một cách không xác định.

#### Ví Dụ Mã Nguồn: Giải Pháp Thay Thế Hiện Đại try-with-resources
```java
public class ResourceDemo {
    public static void main(String[] args) {
        // Dọn dẹp xác định bằng cách sử dụng try-with-resources
        try (MyResource resource = new MyResource()) {
            resource.doWork();
        } // resource.close() tự động được gọi ở đây, ngay cả khi xảy ra ngoại lệ
    }
}

class MyResource implements AutoCloseable {
    public void doWork() {
        System.out.println("Đang hoạt động...");
    }

    @Override
    public void close() {
        System.out.println("Tài nguyên đã được đóng và dọn dẹp!");
    }
}
```

### Rò Rỉ Bộ Nhớ Trong Java (Memory Leak)

Một hiện tượng rò rỉ bộ nhớ (memory leak) trong Java xảy ra khi ứng dụng duy trì các tham chiếu mạnh (strong reference) đến các đối tượng không còn cần thiết nữa, khiến Garbage Collector không thể giải phóng bộ nhớ của chúng.

---

## Ví Dụ Thực Tế: Rò Rỉ Bộ Nhớ Trong Bộ Nhớ Đệm — HashMap Tĩnh Phình To Vô Hạn

### Kịch Bản
Một ứng dụng sử dụng bộ nhớ đệm (cache) trong bộ nhớ để lưu trữ dữ liệu phiên (session) của người dùng. Để có thể truy cập toàn cục, bộ nhớ đệm này được triển khai dưới dạng một `static HashMap`. Tuy nhiên, khi người dùng đăng xuất hoặc phiên hết hạn, các key của map không bao giờ được loại bỏ.

```java
import java.util.HashMap;
import java.util.Map;

public class SessionCacheLeak {
    // Một biến tĩnh (static) tồn tại miễn là lớp được tải (thường là vòng đời của JVM).
    // Nó hoạt động như một GC Root vĩnh viễn. Mọi đối tượng lưu trong map này đều duy trì tham chiếu mạnh.
    private static final Map<String, UserSession> activeSessions = new HashMap<>();

    public static void userLoggedIn(String userId, UserSession session) {
        activeSessions.put(userId, session);
    }

    // Bug: Người dùng đăng xuất, nhưng chúng ta quên xóa khỏi activeSessions.
    public static void userLoggedOut(String userId) {
        // Thiếu: activeSessions.remove(userId);
    }
}

class UserSession {
    private byte[] data = new byte[1024 * 1024]; // 1 MB session payload
}
```

### Hậu Quả
Bởi vì trường `activeSessions` là một trường tĩnh, nó là một GC Root. Mọi đối tượng `UserSession` được thêm vào sẽ được giữ tham chiếu mạnh vĩnh viễn, ngay cả sau khi người dùng đã đăng xuất. Nếu ứng dụng xử lý hàng nghìn lượt đăng nhập mỗi ngày, bộ nhớ heap sẽ bị lấp đầy và cuối cùng gây ra lỗi `java.lang.OutOfMemoryError: Java heap space`.

### Các Cách Khắc Phục
1. **Loại Bỏ Thủ Công Một Cách Tường Minh**: Đảm bảo mã loại bỏ phần tử được thực thi bên trong khối `finally` hoặc một bộ lắng nghe sự kiện dọn dẹp:
   ```java
   public static void userLoggedOut(String userId) {
       activeSessions.remove(userId);
   }
   ```
2. **WeakHashMap**: Sử dụng `java.util.WeakHashMap` nếu vòng đời của session liên kết chặt chẽ với các tham chiếu bên ngoài của các key. Một khi key không còn được tham chiếu mạnh ở bất kỳ nơi nào khác, mục dữ liệu trong map sẽ bị GC giải phóng.
3. **Chính Sách Loại Bỏ (Eviction Policies)**: Sử dụng các thư viện bộ đệm giới hạn dung lượng như Guava Cache hoặc Caffeine với các chính sách loại bỏ dựa trên thời gian (time-based) hoặc kích thước (size-based).

---

## Lỗi Thường Gặp

### 1. Không Kiểm Tra Null Trên WeakReference
Các lập trình viên thường quên rằng GC có thể giải phóng một `WeakReference` vào bất kỳ thời điểm nào. Việc gọi trực tiếp `weakRef.get().someMethod()` mà không kiểm tra xem `get()` có trả về `null` hay không sẽ dẫn đến ngoại lệ `NullPointerException`.
**Cách khắc phục**:
```java
Object value = weakRef.get();
if (value != null) {
    // An toàn để sử dụng
}
```

### 2. Tạo WeakReference Với Hằng Số Chuỗi (String Literals)
Nếu bạn truyền một hằng số chuỗi trực tiếp vào một `WeakReference` (ví dụ: `new WeakReference<>("literal")`), nó sẽ *không bao giờ* bị thu gom rác. Điều này là do các hằng số chuỗi được lưu trữ trong Vùng Nhớ Đệm Hằng Số Chuỗi (String Constant Pool) và luôn duy trì tham chiếu mạnh ở đó.

### 3. Dựa Vào `finalize()` Để Dọn Dẹp Tài Nguyên
Việc giả định rằng `finalize()` sẽ luôn chạy một cách đáng tin cậy hoặc nhanh chóng là một sai lầm nghiêm trọng. Nó thậm chí có thể không bao giờ được thực thi nếu JVM thoát ra trước khi chu kỳ GC chạy. Hãy luôn sử dụng cú pháp `try-with-resources`.

### 4. Nghĩ Rằng Đảo Cô Lập Không Thể Bị Thu Gom Rác
Nghĩ rằng bất kỳ phụ thuộc vòng nào (ví dụ: Đối tượng A tham chiếu đến B, và B tham chiếu đến A) sẽ ngăn cản việc GC là sai lầm. Khả năng tiếp cận được truy vết từ các GC Root; nếu toàn bộ nhóm đối tượng này bị ngắt kết nối khỏi các GC Root, toàn bộ hòn đảo cô lập sẽ bị thu hồi bộ nhớ.

## Các Câu Hỏi Ôn Tập Phổ Biến

- Khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy (runtime)?
- Khái niệm nào ở đây có khả năng là bẫy phỏng vấn?

## Tại Sao Các Kiểu Tham Chiếu Khác Nhau Lại Tồn Tại

Java cung cấp các cấp độ mạnh tham chiếu khác nhau để cho phép lập trình viên kiểm soát chi tiết vòng đời của các đối tượng, ngăn chặn rò rỉ bộ nhớ trong khi tối ưu hóa bộ nhớ đệm hoặc các quy trình dọn dẹp tài nguyên. Tham chiếu mạnh (strong reference) ngăn cản Garbage Collector thu hồi đối tượng ngay cả dưới áp lực bộ nhớ cực hạn, điều này cần thiết cho các dữ liệu đang hoạt động nhưng lại nguy hiểm đối với các bộ đệm tạm thời. Tham chiếu mềm (soft reference) hoạt động như một bộ đệm trung gian, cho phép đối tượng tồn tại trong các điều kiện thực thi bình thường nhưng tự động thu hồi ngay trước khi JVM ném ra lỗi OutOfMemoryError, khiến chúng trở nên lý tưởng cho các bộ nhớ đệm nhạy cảm với bộ nhớ. Tham chiếu yếu (weak reference) sẽ bị xóa bỏ trong chu kỳ thu gom tiếp theo nếu đối tượng tham chiếu không có đường dẫn tham chiếu mạnh nào khác, điều này hoàn hảo để liên kết siêu dữ liệu với các đối tượng (ví dụ: WeakHashMap) mà không kéo dài vòng đời của chúng. Tham chiếu ảo (phantom reference) là kiểu tham chiếu yếu nhất, luôn trả về null khi gọi phương thức get() và chỉ dùng để thông báo cho lập trình viên qua một hàng đợi `ReferenceQueue` khi một đối tượng đã hoàn toàn được thu gom, giúp cho việc dọn dẹp bộ nhớ ngoài heap (off-heap) diễn ra an toàn.

### Mô Hình Tư Duy
```
[ GC Root ]
     |
     +===(Tham chiếu mạnh)===> [ Đối tượng A ] (Không bao giờ bị thu gom)
     |
     +---(Tham chiếu mềm)--->  [ Đối tượng B ] (Chỉ bị thu gom nếu heap cạn kiệt)
     |
     +---(Tham chiếu yếu)--->  [ Đối tượng C ] (Bị thu gom ở chu kỳ GC tiếp theo)
     |
     +---(Tham chiếu ảo)---->  [ Đối tượng D ] (Luôn trả về null; xếp hàng khi GC)
                                    |
                                    v
                             [ ReferenceQueue ] (Xử lý việc dọn dẹp tài nguyên hậu kỳ)
```

### Ví Dụ Mã Nguồn
```java
import java.lang.ref.*;

public class ReferenceTypesDemo {
    public static void main(String[] args) {
        Object referent = new Object();
        SoftReference<Object> softRef = new SoftReference<>(referent);
        WeakReference<Object> weakRef = new WeakReference<>(referent);
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> phantomRef = new PhantomReference<>(referent, queue);

        System.out.println("Weak get: " + (weakRef.get() != null));       // Đầu ra: Weak get: true
        System.out.println("Phantom get: " + phantomRef.get());            // Đầu ra: Phantom get: null (luôn luôn)
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Heap cạn kiệt &rarr; GC chạy &rarr; Giữ lại tham chiếu mạnh &rarr; Giải phóng tham chiếu mềm &rarr; Giải phóng tham chiếu yếu &rarr; Đưa tham chiếu ảo vào hàng đợi &rarr; Tránh được/Giảm thiểu lỗi OutOfMemoryError.

## Tại Sao Đảo Cô Lập Có Thể Bị Thu Gom Rác

Các bộ thu gom rác thế hệ cũ hoặc đơn giản sử dụng cơ chế đếm tham chiếu (reference counting), cơ chế này tăng một bộ đếm khi đối tượng được tham chiếu và giảm đi khi tham chiếu bị cắt đứt. Tuy nhiên, cơ chế đếm tham chiếu thất bại trong việc thu hồi các tham chiếu vòng (đảo cô lập) vì các liên kết chéo của chúng duy trì bộ đếm tham chiếu lớn hơn 0 ngay cả khi đã hoàn toàn ngắt kết nối khỏi phần còn lại của ứng dụng. JVM giải quyết giới hạn cơ bản này bằng cách triển khai cơ chế thu gom rác dựa trên truy vết (tracing garbage collection), bắt đầu kiểm tra khả năng tiếp cận từ các điểm mốc được định nghĩa gọi là 'GC Roots' (như các khung ngăn xếp stack, các trường tĩnh, và các tham chiếu JNI). Bất kỳ nhóm đối tượng nào không thể tiếp cận bằng cách duyệt qua đồ thị tham chiếu bắt đầu từ các GC Root này đều được xác định là không thể tiếp cận, bất kể các liên kết chéo nội bộ giữa chúng. Do đó, Garbage Collector sẽ thu hồi bộ nhớ của toàn bộ đảo cô lập một cách an toàn trong một chu kỳ thu gom rác vì không có đường dẫn nào trỏ tới chúng từ bất kỳ luồng ứng dụng nào đang hoạt động.

### Mô Hình Tư Duy
```
[ GC Root (Khung ngăn xếp) ]
            |
            x (Tham chiếu bị cắt đứt)
            |
    +-------v--------+
    | Đối tượng A    | <=======> [ Đối tượng B ]
    | (Đếm = 1)      |           (Đếm = 1)
    +----------------+
    
    [ Đảo Cô Lập (Không thể tiếp cận từ GC Root) ]
```

### Ví Dụ Mã Nguồn
```java
public class IslandOfIsolation {
    IslandOfIsolation partner;

    public static void main(String[] args) {
        IslandOfIsolation a = new IslandOfIsolation();
        IslandOfIsolation b = new IslandOfIsolation();

        a.partner = b; // a tham chiếu đến b
        b.partner = a; // b tham chiếu đến a

        a = null;      // cắt tham chiếu stack đến a
        b = null;      // cắt tham chiếu stack đến b
        
        // Cả hai đối tượng tham chiếu chéo lẫn nhau nhưng đều không thể tiếp cận từ GC Roots.
        System.gc(); // Cơ chế GC truy vết thu hồi thành công cả hai đối tượng.
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)
Các tham chiếu stack được đặt thành null &rarr; Quá trình duyệt các GC Root bắt đầu &rarr; GC không thể tiếp cận Đối tượng A hoặc B &rarr; Các tham chiếu vòng chéo bị bỏ qua &rarr; Đảo cô lập được đánh dấu là không thể tiếp cận &rarr; Cả hai đối tượng đều được thu hồi bộ nhớ.

## Liên Kết Tham Chiếu

- Đặc tả gói java.lang.ref: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/ref/package-summary.html
- Hướng dẫn điều chỉnh thu gom rác Java GC: https://docs.oracle.com/en/java/javase/21/gctuning/introduction-garbage-collection-tuning.html
