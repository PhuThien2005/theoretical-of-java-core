# Collections Framework - Phần 5 (Collections Framework - Part 5)

## Mục tiêu học tập

Tài liệu này tập trung vào một phần trọng tâm của **Collections Framework** bao gồm các bản đồ đồng thời (`ConcurrentHashMap`), các bản đồ chuyên dụng (`WeakHashMap`, `IdentityHashMap`), các bản đồ được sắp xếp/điều hướng, và các mô hình duyệt của trình lặp.

## Đề cương chi tiết

| Khái niệm | Điều cần biết |
| --- | --- |
| `ConcurrentHashMap` | ConcurrentHashMap: Bản đồ an toàn luồng (thread-safe Map) hiệu năng cao sử dụng đồng bộ hóa đầu xô (bucket head synchronization) và các thao tác CAS (Compare-And-Swap) thay vì khóa toàn bộ bản đồ. |
| `WeakHashMap` | WeakHashMap: Bản đồ có các khóa được bao bọc trong `WeakReference`, cho phép chúng bị thu gom rác khi không còn được sử dụng bên ngoài bản đồ. |
| `IdentityHashMap` | IdentityHashMap: Bản đồ so sánh các khóa bằng cách sử dụng so sánh bằng tham chiếu (`==`) thay vì bằng bằng logic (`equals()`). |
| `SortedMap` | SortedMap: Giao diện đại diện cho một Bản đồ được sắp xếp theo thứ tự tăng dần của các khóa. |
| `NavigableMap` | NavigableMap: Mở rộng SortedMap, bổ sung các phương thức tìm kiếm/điều hướng như `lowerEntry()`, `floorKey()`, v.v. |
| `Iterator` | Trình lặp (Iterator): Giao diện cung cấp khả năng duyệt qua bộ sưu tập theo chiều tiến với sự hỗ trợ xóa phần tử an toàn. |
| `ListIterator` | ListIterator: Trình lặp hai chiều cho các đối tượng List cho phép duyệt tiến/lùi và sửa đổi phần tử. |

## Ghi chú chi tiết

### ConcurrentHashMap

`ConcurrentHashMap` cung cấp khả năng đồng thời hoàn toàn cho các thao tác đọc và khả năng đồng thời cao cho các thao tác ghi.
- **Cơ chế khóa**: Trong Java 7, nó sử dụng khóa phân đoạn (segment locking). Từ Java 8 trở đi, nó sử dụng khóa cấp nút (node-level locking - chỉ đồng bộ hóa trên nút đầu tiên của một xô bucket/bin) và các thao tác CAS (Compare-And-Swap) cho các xô trống. Điều này cho phép nhiều luồng ghi vào các xô khác nhau một cách đồng thời.
- **An toàn với Null**: Từ chối các khóa và giá trị `null`.

**Ví dụ mã nguồn chạy được:**
```java
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> inventory = new ConcurrentHashMap<>();
        inventory.put("Widget A", 100);

        // Atomic write operations
        inventory.computeIfPresent("Widget A", (key, val) -> val - 5);
        inventory.putIfAbsent("Widget B", 50);

        System.out.println("Inventory: " + inventory); // {Widget A=95, Widget B=50}
    }
}
```

### WeakHashMap

Một Map dựa trên bảng băm với các khóa được bao bọc trong `WeakReference`. Khi một khóa không còn được tham chiếu ở nơi khác, nó sẽ bị thu gom rác, và mục nhập tương ứng sau đó sẽ tự động bị xóa khỏi bản đồ.
- **Trường hợp sử dụng điển hình**: Bộ đệm cache, bảng tra cứu siêu dữ liệu (metadata), hoặc các đăng ký trình lắng nghe (listener registry) nơi việc ánh xạ không nên giữ cho các đối tượng tiếp tục sống.

**Ví dụ mã nguồn chạy được:**
```java
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) throws InterruptedException {
        Map<Object, String> weakMap = new WeakHashMap<>();
        Object key = new Object(); // Strong reference to the key

        weakMap.put(key, "Cached Metadata");
        System.out.println("Before GC: " + weakMap.containsKey(key)); // true

        key = null; // Clear the strong reference
        System.gc(); // Request GC execution
        Thread.sleep(100); // Give GC time to run

        System.out.println("After GC: " + weakMap.isEmpty()); // true (key was garbage collected)
    }
}
```

### IdentityHashMap

Một `IdentityHashMap` so sánh các khóa bằng cách sử dụng so sánh bằng tham chiếu (`key1 == key2`) thay vì so sánh bằng đối tượng (`key1.equals(key2)`). Nó cố tình không phải là một triển khai Map đa mục đích thông thường.
- **Trường hợp sử dụng điển hình**: Các thuật toán duyệt đồ thị để phát hiện chu trình, tuần tự hóa đồ thị đối tượng, hoặc duy trì các thực thể đối tượng chính xác.

**Ví dụ mã nguồn chạy được:**
```java
import java.util.IdentityHashMap;
import java.util.Map;

public class IdentityMapDemo {
    public static void main(String[] args) {
        Map<String, String> map = new IdentityHashMap<>();
        
        String key1 = new String("key");
        String key2 = new String("key");
        
        map.put(key1, "Value A");
        map.put(key2, "Value B");
        
        // Logical contents match, but references are different
        System.out.println("Size: " + map.size()); // 2
        System.out.println("Key1 value: " + map.get(key1)); // Value A
    }
}
```

### SortedMap & NavigableMap

- **SortedMap**: Một giao diện duy trì các khóa của nó theo thứ tự đã sắp xếp.
- **NavigableMap**: Mở rộng `SortedMap` và bổ sung các phương thức như `lowerEntry()`, `floorKey()`, `ceilingKey()`, và `higherKey()` để tìm các kết quả khớp gần nhất so với một khóa nhất định. `TreeMap` là triển khai chính.

### Iterator so với ListIterator

- **Iterator**: Có thể duyệt qua bất kỳ `Collection` nào theo chiều tiến. Hỗ trợ xóa các phần tử trong quá trình lặp.
- **ListIterator**: Mở rộng `Iterator`. Chỉ có thể duyệt qua các triển khai `List`. Hỗ trợ duyệt hai chiều (`hasPrevious()`, `previous()`), thay thế phần tử (`set()`), thêm phần tử (`add()`), và truy xuất chỉ số index hiện tại.

**Ví dụ mã nguồn chạy được:**
```java
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
        ListIterator<String> lit = list.listIterator();

        // Traverse forward
        while (lit.hasNext()) {
            String element = lit.next();
            if (element.equals("B")) {
                lit.set("Updated B"); // Replace element
            }
        }

        // Traverse backward
        while (lit.hasPrevious()) {
            System.out.print(lit.previous() + " "); // C Updated B A 
        }
        System.out.println();
    }
}
```

---

## Ví Dụ Thực Tế: Bộ đếm có tính đồng thời cao sử dụng ConcurrentHashMap

Khi xây dựng các bộ tổng hợp đa luồng, việc sử dụng các khối synchronized hoặc `synchronizedMap` sẽ tạo ra các nút thắt cổ chai hiệu năng nghiêm trọng. Sử dụng `ConcurrentHashMap` kết hợp với `LongAdder` cho phép cập nhật bộ đếm đồng thời một cách cực kỳ hiệu quả.

```java
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class ConcurrentCounterApp {
    public static void main(String[] args) throws InterruptedException {
        // ConcurrentHashMap + LongAdder (preferred over AtomicInteger for high write-rate counters)
        ConcurrentHashMap<String, LongAdder> pageCounts = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Submit 1000 tasks to increment counts
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                // computeIfAbsent is thread-safe and atomic
                pageCounts.computeIfAbsent("/home", k -> new LongAdder()).increment();
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Total visits to /home: " + pageCounts.get("/home").sum()); // 1000
    }
}
```

---

## Các lỗi thường gặp

### 1. Vòng lặp tham chiếu mạnh từ giá trị đến khóa trong WeakHashMap
Nếu giá trị trong một `WeakHashMap` chứa một tham chiếu mạnh đến khóa, khóa đó sẽ không bao giờ bị thu gom rác. Điều này gây ra rò rỉ bộ nhớ trong im lặng và làm mất đi toàn bộ mục đích của `WeakHashMap`.

### 2. Sửa đổi các bộ sưu tập trong quá trình lặp bằng các phương thức của Collection
Sử dụng `list.remove()` thay vì `iterator.remove()` bên trong một vòng lặp trình lặp đang hoạt động sẽ kích hoạt ngoại lệ `ConcurrentModificationException`. Luôn sửa đổi thông qua các phương thức của chính trình lặp.

### 3. Giả định tất cả các thao tác của ConcurrentHashMap đều mang tính nguyên tử
Mặc dù các thao tác đọc và ghi riêng lẻ trên `ConcurrentHashMap` là an toàn luồng và mang tính nguyên tử, nhưng chuỗi các thao tác (ví dụ: `if (!map.containsKey(key)) { map.put(key, value); }`) thì KHÔNG mang tính nguyên tử. Hãy sử dụng `putIfAbsent()`, `compute()`, hoặc `computeIfAbsent()` thay thế để đảm bảo tính nguyên tử.

---

## Các câu hỏi ôn tập thường gặp

- ConcurrentHashMap sử dụng gì từ Java 8 thay thế cho khóa phân đoạn? (các thao tác CAS và các nút đầu xô synchronized)
- Tại sao IdentityHashMap sử dụng `==`? (Để so sánh danh tính thực thể thay vì so sánh bằng logic equals, hữu ích cho việc duyệt đồ thị hoặc bộ đệm cache cấp hệ thống)
- Trình lặp nào hỗ trợ đi giật lùi? (ListIterator)

## Tại sao ConcurrentHashMap tránh việc khóa toàn cục

Các bản đồ đồng bộ truyền thống (chẳng hạn như `Hashtable` hoặc bộ bao bọc được trả về bởi `Collections.synchronizedMap()`) đạt được tính an toàn luồng bằng cách khóa toàn bộ cấu trúc dữ liệu trong quá trình đọc và ghi, tạo ra các nút thắt cổ chai hiệu năng nghiêm trọng. Để khắc phục hạn chế này, `ConcurrentHashMap` áp dụng chiến lược phân tách khóa (lock-striping) trong đó các thao tác đọc hoàn toàn không dùng khóa (lock-free), và các thao tác ghi đồng bộ hóa ở cấp độ từng xô (bucket) riêng lẻ. Khi chèn một phần tử vào một xô trống, bản đồ sử dụng chỉ thị CPU So sánh và Hoán đổi (Compare-And-Swap - CAS) để đặt nút một cách nguyên tử mà không cần lấy khóa phần mềm. Nếu xô đích không trống, luồng ghi chỉ giành lấy khóa trên nút đầu (head node) của xô cụ thể đó bằng cách sử dụng một khối `synchronized` tiêu chuẩn của Java, cho phép các luồng khác đọc hoặc ghi vào các xô khác một cách đồng thời. Ngoài ra, `ConcurrentHashMap` từ chối các khóa và giá trị `null` để ngăn ngừa sự mơ hồ trong các ngữ cảnh đồng thời; nếu các giá trị `null` được cho phép, chúng ta sẽ không thể phân biệt một cách an toàn liệu một khóa có giá trị ánh xạ là `null` hay khóa đó chỉ đơn giản là không tồn tại trong bản đồ, do việc gọi `containsKey(key)` ngay sau `get(key)` là không nguyên tử và dễ xảy ra tình trạng tranh đoạt dữ liệu (race condition).

### Mô hình tư duy

Khóa hạt mịn (fine-grained locking) nhắm vào các xô riêng lẻ thay vì khóa toàn bộ bản đồ:
```text
Hashtable / Bản đồ được đồng bộ hóa (Synchronized Map):
[Khóa hoạt động] -> Khóa toàn bộ mảng [Xô 0 | Xô 1 | Xô 2 | Xô 3] (Chặn tất cả các luồng)

ConcurrentHashMap:
Xô 0 (Trống): [ (Không có nút) ] -> Luồng ghi chèn nút bằng CAS (Không dùng khóa!)
Xô 1 (Hoạt động): [ Nút đầu *Bị khóa* ] -> Luồng ghi chỉ khóa nút đầu.
Xô 2 (Hoạt động): [ Nút đầu ] -> Các luồng đọc duyệt qua không cần khóa.
Xô 3 (Hoạt động): [ Nút đầu ] -> Các luồng ghi khác khóa xô 3 một cách đồng thời.
```

### Ví dụ mã nguồn

```java
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapLockingDemo {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        // 1. Concurrent lock-free writes to different buckets
        map.put("KeyA", "ValueA"); // Hash maps to Bucket X
        map.put("KeyB", "ValueB"); // Hash maps to Bucket Y
        
        System.out.println("Map content: " + map); // Map content: {KeyA=ValueA, KeyB=ValueB}

        // 2. Demonstration of NullPointerException for null keys/values
        try {
            map.put("KeyC", null);
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException for null value");
            // Caught NullPointerException for null value
        }
        
        try {
            map.put(null, "ValueC");
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException for null key");
            // Caught NullPointerException for null key
        }
    }
}
```

### Chuỗi nguyên nhân - kết quả

```text
Khởi tạo ghi đồng thời ➔ Xô trống? ➔ Sử dụng thao tác CAS để ghi nút không cần khóa ➔ Xô đã có phần tử? ➔ Chỉ đồng bộ hóa trên nút đầu của xô ➔ Cho phép ghi đồng thời vào các xô khác nhau ➔ Từ chối các khóa/giá trị null để tránh sự mơ hồ do tranh đoạt dữ liệu của get()/containsKey()
```

## Liên kết tham khảo

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ConcurrentHashMap.html (ConcurrentHashMap API docs)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collections.html#synchronizedMap(java.util.Map) (synchronizedMap wrapper)
