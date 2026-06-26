# Quản lý bộ nhớ Java (Java Memory Management) - Phần 2

## Mục tiêu học tập (Learning Goal)

Tập tin này bao gồm một phần trọng tâm về **Quản lý bộ nhớ Java (Java Memory Management)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng riêng lẻ.

## Khái quát nội dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Weak reference` | Tham chiếu yếu (Weak reference) là một khái niệm cụ thể trong Quản lý bộ nhớ Java; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ tên. |
| `Soft reference` | Tham chiếu mềm (Soft reference) là một khái niệm cụ thể trong Quản lý bộ nhớ Java; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ tên. |
| `Phantom reference` | Tham chiếu bóng ma (Phantom reference) là một khái niệm cụ thể trong Quản lý bộ nhớ Java; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ tên. |
| `Garbage Collection` | Thu gom rác (Garbage Collection) thu hồi bộ nhớ từ các đối tượng không còn có thể tiếp cận được. |
| `Conditions for an object to be GC'd` | Điều kiện để một đối tượng bị GC là một khái niệm cụ thể trong Quản lý bộ nhớ Java; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ tên. |
| `System.gc()` | System.gc() là một khái niệm cụ thể trong Quản lý bộ nhớ Java; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ tên. |
| `Finalization, finalize() deprecated` | Quá trình dọn dẹp finalize() bị loại bỏ (Lưu ý: Mô tả gốc tiếng Anh mô tả sai từ khóa final, nghĩa là biến, phương thức, lớp hoặc tham số bị hạn chế thay đổi sau đó theo một cách cụ thể). |
| `Memory leak in Java` | Rò rỉ bộ nhớ trong Java (Memory leak in Java) là một khái niệm cụ thể trong Quản lý bộ nhớ Java; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ, và chế độ thất bại thay vì chỉ nhớ tên. |

## Ghi chú chi tiết (Detailed Notes)

### Tham chiếu yếu (Weak reference)

Một **Tham chiếu yếu** (được đại diện bởi `java.lang.ref.WeakReference`) không ngăn cản đối tượng tham chiếu của nó bị thu hồi bởi Bộ thu gom rác.

#### Quy tắc JVM
- Nếu một đối tượng chỉ có thể tiếp cận được thông qua các tham chiếu yếu (không có đường dẫn tham chiếu mạnh hoặc mềm nào từ các GC Root), GC sẽ xóa nó trong chu kỳ thu gom tiếp theo, bất kể bộ nhớ heap có bị thiếu hay không.
- Thường được sử dụng cho các ánh xạ siêu dữ liệu (metadata mapping), ánh xạ chuẩn hóa (canonicalizing mapping), hoặc bộ nhớ đệm (chẳng hạn như trong `java.util.WeakHashMap`).

#### Ví dụ mã nguồn: Hành vi của WeakReference (Code Example: WeakReference Behavior)
```java
import java.lang.ref.WeakReference;

public class WeakRefDemo {
    public static void main(String[] args) {
        // Strong reference 'bigObject' points to a large String on the heap
        String bigObject = new String("PayloadData");
        
        // Weak reference pointing to the same heap object
        WeakReference<String> weakRef = new WeakReference<>(bigObject);
        
        System.out.println("Before GC: " + weakRef.get()); // Prints "PayloadData"
        
        // Sever the strong reference
        bigObject = null; 
        
        // Request GC (strictly for demonstration, do not do this in production)
        System.gc(); 
        
        // The object has been collected because it was only weakly reachable
        System.out.println("After GC: " + weakRef.get()); // Prints null
    }
}
```

### Tham chiếu mềm (Soft reference)

Một **Tham chiếu mềm** (được đại diện bởi `java.lang.ref.SoftReference`) là kiểu tham chiếu mạnh hơn tham chiếu yếu, được thiết kế cho bộ nhớ đệm nhạy cảm với bộ nhớ (memory-sensitive caching).

#### Quy tắc JVM
- Một đối tượng có thể tiếp cận mềm (chỉ có các tham chiếu mềm trỏ đến nó) sẽ sống sót qua các chu kỳ Thu gom rác tiêu chuẩn.
- JVM sẽ chỉ thu hồi các đối tượng được tham chiếu mềm nếu nó sắp hết bộ nhớ (thường là ngay trước khi ném ra lỗi `OutOfMemoryError`).
- Các triển khai JVM sẽ cố gắng xóa các đối tượng được tham chiếu mềm đã ở trạng thái nhàn rỗi (idle) lâu nhất.

#### Ví dụ mã nguồn: Sử dụng SoftReference (Code Example: SoftReference Usage)
```java
import java.lang.ref.SoftReference;

public class SoftRefDemo {
    public static void main(String[] args) {
        String data = new String("CachedValue");
        SoftReference<String> softRef = new SoftReference<>(data);
        
        data = null; // Sever the strong reference
        
        System.gc(); // Suggest GC
        
        // Survives standard GC because memory is not low
        System.out.println("Soft reference get: " + softRef.get()); // Prints "CachedValue"
    }
}
```

### Tham chiếu bóng ma (Phantom reference)

Một **Tham chiếu bóng ma** (được đại diện bởi `java.lang.ref.PhantomReference`) là kiểu tham chiếu yếu nhất, được sử dụng để dọn dẹp sau khi đối tượng đã chết (post-mortem cleanup).

#### Quy tắc JVM
- Khác với tham chiếu Yếu và Mềm, việc gọi `.get()` trên một `PhantomReference` **luôn luôn trả về `null`**.
- Nó phải được tạo cùng với một `ReferenceQueue` (hàng đợi tham chiếu).
- Khi JVM xác định một đối tượng chỉ có thể tiếp cận được dưới dạng bóng ma, nó sẽ đưa tham chiếu bóng ma vào hàng đợi. Nhà phát triển có thể thăm dò hàng đợi để thực hiện các hành động dọn dẹp trước khi đối tượng thực sự bị xóa khỏi bộ nhớ (như giải phóng bộ nhớ native ngoài heap).
- Khác với các đối tượng có phương thức finalize, bộ nhớ không tự động được giải phóng; tham chiếu bóng ma phải được xóa thông qua `phantomRef.clear()` để cho phép thu hồi hoàn toàn.

### Thu gom rác (Garbage Collection)

Thu gom rác (GC) là quá trình quản lý bộ nhớ tự động trong JVM nhằm thu hồi bộ nhớ heap bị chiếm dụng bởi các đối tượng mà ứng dụng không còn có thể tiếp cận được.

#### Quy tắc JVM
- GC hoạt động bất đồng bộ ở chế độ nền. Nó tìm kiếm các đối tượng không thể tiếp cận, giải phóng bộ nhớ của chúng và có thể dồn (compact) heap để ngăn chặn phân mảnh.
- Ứng dụng sẽ tạm dừng hoặc trải qua các khoảng dừng (Stop-The-World) tùy thuộc vào thuật toán GC được sử dụng (ví dụ: G1, ZGC, Parallel GC).

### Điều kiện để một đối tượng bị thu gom rác (Conditions for an object to be GC'd)

Một đối tượng đủ điều kiện để bị thu gom rác nếu nó không còn có thể tiếp cận được từ bất kỳ **GC Root** nào.

#### GC Root là gì?
- Các biến cục bộ và tham số trong ngăn xếp (stack) của các luồng (Thread) đang hoạt động.
- Các trường tĩnh (static field) của các lớp đã được tải.
- Các tham chiếu toàn cục và cục bộ JNI (Java Native Interface).
- Trình nạp lớp hệ thống (system class loader) và các tham chiếu nội bộ đang hoạt động của JVM.

#### Đảo cô lập (Islands of Isolation)
- Nếu Đối tượng A tham chiếu đến Đối tượng B, và Đối tượng B tham chiếu đến Đối tượng A, chúng trỏ chéo lẫn nhau.
- Nếu cả A và B đều không thể tiếp cận được từ bất kỳ GC Root nào, chúng sẽ tạo thành một **đảo cô lập**.
- GC sẽ thu hồi cả hai đối tượng này, mặc dù chúng có các biến tham chiếu phi null trỏ vào nhau.

#### Ví dụ mã nguồn: Điều kiện đủ điều kiện GC & Tham chiếu vòng (Code Example: GC Eligibility & Circular Reference)
```java
public class GCEligibilityDemo {
    public static void main(String[] args) {
        Node n1 = new Node("First");
        Node n2 = new Node("Second");
        
        n1.next = n2;
        n2.next = n1; // n1 and n2 reference each other (circular dependency)
        
        n1 = null; // "First" is still reachable via n2.next
        // "First" is NOT eligible for GC yet.
        
        n2 = null; // "Second" is no longer reachable from main's stack.
        // n1 and n2 are now isolated from the GC Roots.
        // Both Node objects are now eligible for Garbage Collection.
    }
}

class Node {
    String name;
    Node next;
    Node(String name) { this.name = name; }
}
```

### System.gc()

Gọi `System.gc()` hoặc `Runtime.getRuntime().gc()` gợi ý rằng JVM nên dành nỗ lực để tái chế các đối tượng không sử dụng.

#### Quy tắc JVM
- Đây đơn thuần chỉ là một **lời gợi ý** hoặc yêu cầu gửi đến JVM. JVM có quyền lựa chọn bỏ qua cuộc gọi hoàn toàn (ví dụ: nếu được cấu hình với cờ `-XX:+DisableExplicitGC`).
- Không có gì đảm bảo rằng GC sẽ chạy ngay lập tức, cũng như không đảm bảo tất cả các đối tượng đủ điều kiện sẽ được thu hồi khi gọi.
- Việc gọi `System.gc()` rất tốn kém hiệu năng và có thể làm đóng băng các luồng của ứng dụng trong các đợt thu gom lớn (major collection).

### Quá trình finalize, phương thức finalize() bị loại bỏ (Finalization, finalize() deprecated)

Phương thức `finalize()` ban đầu được thiết kế để thực hiện việc dọn dẹp tài nguyên trước khi một đối tượng bị thu hồi.

#### Quy tắc JVM
- `finalize()` đã bị **loại bỏ kể từ Java 9** (deprecated) và bị vô hiệu hóa/loại bỏ hoàn toàn trong các phiên bản hiện đại.
- **Tại sao nó thất bại**: Nó mang lại thời gian thực thi không thể đoán trước, chi phí hiệu năng nghiêm trọng, làm đình trệ bộ thu gom rác và các lỗ hổng bảo mật (lỗi finalize attack nơi các đối tượng được tạo ra một phần có thể được hồi sinh).
- **Các giải pháp thay thế hiện đại**:
  - Triển khai `java.lang.AutoCloseable` và sử dụng câu lệnh **try-with-resources** để dọn dẹp tài nguyên một cách xác định (tệp tin, socket).
  - Sử dụng `java.lang.ref.Cleaner` hoặc tham chiếu bóng ma cho việc dọn dẹp tài nguyên native ngoài heap không xác định.

#### Ví dụ mã nguồn: Giải pháp thay thế hiện đại bằng try-with-resources (Code Example: Modern try-with-resources Alternative)
```java
public class ResourceDemo {
    public static void main(String[] args) {
        // Deterministic cleanup using try-with-resources
        try (MyResource resource = new MyResource()) {
            resource.doWork();
        } // resource.close() is automatically called here, even if exceptions occur
    }
}

class MyResource implements AutoCloseable {
    public void doWork() {
        System.out.println("Working...");
    }

    @Override
    public void close() {
        System.out.println("Resource closed and cleaned up!");
    }
}
```

### Rò rỉ bộ nhớ trong Java (Memory leak in Java)

Rò rỉ bộ nhớ trong Java xảy ra khi ứng dụng giữ lại các tham chiếu mạnh đến các đối tượng không còn cần thiết, ngăn cản Bộ thu gom rác thu hồi chúng.

---

## Case Study: Rò rỉ bộ nhớ trong bộ nhớ đệm — static HashMap phình to vô hạn (Case Study: Memory leak in a cache — static HashMap that grows forever)

### Kịch bản (Scenario)
Một ứng dụng sử dụng bộ nhớ đệm (cache) trong bộ nhớ để lưu trữ dữ liệu phiên (session) của người dùng. Để có thể truy cập toàn cục, cache được triển khai dưới dạng một `static HashMap`. Tuy nhiên, khi người dùng đăng xuất hoặc phiên hết hạn, các khóa không bao giờ bị xóa khỏi bản đồ (map).

```java
import java.util.HashMap;
import java.util.Map;

public class SessionCacheLeak {
    // A static variable lives as long as the class is loaded (typically the lifetime of the JVM).
    // It serves as a permanent GC Root. Any object stored in this map remains strongly reachable.
    private static final Map<String, UserSession> activeSessions = new HashMap<>();

    public static void userLoggedIn(String userId, UserSession session) {
        activeSessions.put(userId, session);
    }

    // Bug: Users log out, but we forget to call activeSessions.remove(userId).
    public static void userLoggedOut(String userId) {
        // Missing: activeSessions.remove(userId);
    }
}

class UserSession {
    private byte[] data = new byte[1024 * 1024]; // 1 MB session payload
}
```

### Hậu quả (The Consequence)
Vì `activeSessions` là một trường tĩnh, nó là một GC Root. Mỗi đối tượng `UserSession` được thêm vào sẽ vẫn có thể tiếp cận mạnh mãi mãi, ngay cả khi người dùng đã đăng xuất. Nếu ứng dụng xử lý hàng nghìn lượt đăng nhập mỗi ngày, heap cuối cùng sẽ bị lấp đầy, gây ra lỗi `java.lang.OutOfMemoryError: Java heap space`.

### Giải pháp khắc phục (The Fixes)
1. **Xóa bỏ rõ ràng (Explicit Removal)**: Đảm bảo mã dọn dẹp được thực thi bên trong một khối `finally` hoặc thông qua listener dọn dẹp:
   ```java
   public static void userLoggedOut(String userId) {
       activeSessions.remove(userId);
   }
   ```
2. **WeakHashMap**: Sử dụng `java.util.WeakHashMap` nếu thời gian tồn tại của phiên gắn liền với các tham chiếu bên ngoài đến các khóa. Khi khóa không còn được tham chiếu mạnh ở nơi nào khác, mục dữ liệu trong map sẽ bị GC xóa bỏ.
3. **Chính sách trục xuất (Eviction Policies)**: Sử dụng một thư viện cache có giới hạn như Guava Cache hoặc Caffeine với các giới hạn trục xuất dựa trên thời gian hoặc kích thước.

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Quên kiểm tra giá trị null trên WeakReference (Failing to check for null on WeakReference)
Các nhà phát triển thường quên rằng GC có thể xóa một `WeakReference` bất kỳ lúc nào. Việc gọi `weakRef.get().someMethod()` mà không kiểm tra xem `get()` có trả về `null` hay không sẽ dẫn đến một `NullPointerException`.
**Sửa đổi**:
```java
Object value = weakRef.get();
if (value != null) {
    // Safe to use
}
```

### 2. Tạo WeakReference với Hằng chuỗi (Creating WeakReference with String Literals)
Nếu bạn truyền một chuỗi hằng (string literal) vào một `WeakReference` (ví dụ: `new WeakReference<>("literal")`), nó sẽ *không bao giờ* bị thu gom rác. Điều này là do các chuỗi hằng được lưu trữ trong Bể Hằng Chuỗi (String Constant Pool - nơi giữ các tham chiếu mạnh đến chúng).

### 3. Dựa vào `finalize()` để dọn dẹp tài nguyên (Relying on finalize() for Cleanup)
Giả định rằng `finalize()` sẽ chạy một cách đáng tin cậy hoặc nhanh chóng là một sai lầm lớn. Nó có thể không bao giờ thực thi nếu JVM thoát trước khi GC chạy. Hãy luôn luôn sử dụng `try-with-resources`.

### 4. Nghĩ rằng các Đảo cô lập không thể bị GC (Thinking Islands of Isolation Cannot be GC'd)
Nghĩ rằng bất kỳ sự phụ thuộc vòng nào (như Đối tượng A tham chiếu B, và B tham chiếu A) sẽ ngăn cản việc GC là một sai lầm. Khả năng tiếp cận được truy vết từ các GC Root; nếu toàn bộ nhóm bị ngắt kết nối khỏi các GC Root, toàn bộ đảo cô lập đó sẽ bị thu gom.

## Các câu hỏi ôn tập thường gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc thời gian biên dịch (compile-time rule)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime behavior)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn?

## Tại sa các kiểu Tham chiếu khác nhau tồn tại (Why Different Reference Types Exist)

Java cung cấp các mức độ mạnh tham chiếu khác nhau để cho phép các nhà phát triển kiểm soát tinh vi đối với vòng đời đối tượng và ngăn ngừa rò rỉ bộ nhớ trong khi tối ưu hóa bộ nhớ đệm hoặc các thủ tục dọn dẹp. Tham chiếu mạnh ngăn cản Bộ thu gom rác thu hồi một đối tượng ngay cả khi bộ nhớ bị quá tải nghiêm trọng, điều này cần thiết cho dữ liệu đang hoạt động nhưng lại nguy hiểm cho các bộ nhớ đệm tạm thời. Tham chiếu mềm hoạt động như một bộ đệm, cho phép các đối tượng tồn tại trong quá trình thực thi bình thường nhưng tự động thu hồi chúng ngay trước khi JVM ném ra lỗi OutOfMemoryError, làm cho chúng trở nên lý tưởng cho các bộ nhớ đệm nhạy cảm với bộ nhớ. Tham chiếu yếu bị xóa trong chu kỳ thu gom tiếp theo nếu đối tượng được tham chiếu không có đường dẫn tiếp cận mạnh hơn, điều này hoàn hảo để liên kết siêu dữ liệu với các đối tượng (ví dụ: WeakHashMap) mà không kéo dài vòng đời của chúng. Tham chiếu bóng ma là kiểu yếu nhất, luôn trả về null khi gọi get() và chỉ phục vụ mục đích thông báo cho nhà phát triển thông qua một ReferenceQueue khi một đối tượng đã được thu gom hoàn toàn để việc dọn dẹp bộ nhớ native ngoài heap có thể diễn ra an toàn.

### Mô hình tư duy (Mental Model)
```
[ GC Root ]
     |
     +===(Strong Reference)===> [ Object A ] (Never collected)
     |
     +---(Soft Reference)--->  [ Object B ] (Collected only if heap is exhausted)
     |
     +---(Weak Reference)--->  [ Object C ] (Collected on next GC cycle)
     |
     +---(Phantom Reference)--> [ Object D ] (Always returns null; enqueued on GC)
                                    |
                                    v
                             [ ReferenceQueue ] (Handles post-mortem cleanup)
```

### Ví dụ mã nguồn (Code Example)
```java
import java.lang.ref.*;

public class ReferenceTypesDemo {
    public static void main(String[] args) {
        Object referent = new Object();
        SoftReference<Object> softRef = new SoftReference<>(referent);
        WeakReference<Object> weakRef = new WeakReference<>(referent);
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> phantomRef = new PhantomReference<>(referent, queue);

        System.out.println("Weak get: " + (weakRef.get() != null));       // Output: Weak get: true
        System.out.println("Phantom get: " + phantomRef.get());            // Output: Phantom get: null (always)
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Heap cạn kiệt
  → GC chạy
  → Tham chiếu mạnh được bảo toàn
  → Tham chiếu mềm bị xóa
  → Tham chiếu yếu bị xóa
  → Tham chiếu bóng ma được đưa vào hàng đợi
  → Lỗi OutOfMemoryError được tránh/giảm thiểu.
```


## Tại sao các Đảo cô lập có thể bị thu gom rác (Why Islands of Isolation Can Be Garbage Collected)

Các bộ thu gom rác cũ hoặc đơn giản hơn đã sử dụng cơ chế đếm tham chiếu (reference counting), cơ chế này tăng một bộ đếm bất cứ khi nào một đối tượng được tham chiếu và giảm nó khi một tham chiếu bị ngắt. Tuy nhiên, đếm tham chiếu không thể thu hồi các tham chiếu vòng (đảo cô lập) vì các tham chiếu chéo của chúng duy trì một số lượng tham chiếu lớn hơn không ngay cả khi đã ngắt kết nối hoàn toàn khỏi phần còn lại của ứng dụng. JVM giải quyết hạn chế cơ bản này bằng cách triển khai thuật toán thu gom rác truy vết (tracing garbage collection), thuật toán này bắt đầu kiểm tra khả năng tiếp cận từ các 'GC Root' được xác định trước (chẳng hạn như các khung ngăn xếp, trường tĩnh và tham chiếu JNI). Bất kỳ nhóm đối tượng nào không thể tiếp cận được bằng cách duyệt đồ thị tham chiếu bắt đầu từ các GC Root này sẽ được xác định là không thể tiếp cận, bất kể các tham chiếu chéo nội bộ giữa chúng là gì. Do đó, bộ thu gom rác thu hồi toàn bộ đảo cô lập một cách an toàn trong một chu kỳ thu gom vì không tồn tại đường dẫn nào đến chúng từ bất kỳ luồng ứng dụng đang hoạt động nào.

### Mô hình tư duy (Mental Model)
```
[ GC Root (Stack Frame) ]
            |
            x (Reference severed)
            |
    +-------v--------+
    | Object A       | <=======> [ Object B ]
    | (Count = 1)    |           (Count = 1)
    +----------------+
    
    [ Island of Isolation (Unreachable from GC Root) ]
```

### Ví dụ mã nguồn (Code Example)
```java
public class IslandOfIsolation {
    IslandOfIsolation partner;

    public static void main(String[] args) {
        IslandOfIsolation a = new IslandOfIsolation();
        IslandOfIsolation b = new IslandOfIsolation();

        a.partner = b; // a references b
        b.partner = a; // b references a

        a = null;      // sever stack reference to a
        b = null;      // sever stack reference to b
        
        // Both objects reference each other but are unreachable from GC Roots.
        System.gc(); // Tracing GC reclaims both objects successfully.
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Các tham chiếu ngăn xếp được đặt thành null
  → Quá trình duyệt GC Root bắt đầu
  → Quá trình duyệt GC không thể chạm tới Đối tượng A hoặc B
  → Các tham chiếu vòng bị bỏ qua
  → Đảo cô lập bị đánh dấu là không thể tiếp cận
  → Cả hai đối tượng đều bị thu hồi.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/ref/package-summary.html (Package java.lang.ref)
- https://docs.oracle.com/en/java/javase/21/gctuning/introduction-garbage-collection-tuning.html (Garbage Collection Tuning Guide)
