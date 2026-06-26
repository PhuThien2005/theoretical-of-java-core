# Collections Framework - Phần 5 (Collections Framework - Part 5)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần nội dung trọng tâm về **Collections Framework** bao gồm các bản đồ đồng thời (`ConcurrentHashMap`), các bản đồ chuyên biệt (`WeakHashMap`, `IdentityHashMap`), các bản đồ sorted/navigable, và các mô hình duyệt phần tử bằng iterator.

## Các khái niệm bao phủ (Outline Coverage)

| Khái niệm | Những điều cần biết |
| --- | --- |
| `ConcurrentHashMap` | Bản đồ an toàn luồng hiệu năng cao sử dụng cơ chế đồng bộ hóa nút đầu xô (bucket head) và các thao tác CAS (Compare-And-Swap) thay vì khóa toàn bộ bản đồ. |
| `WeakHashMap` | Bản đồ với các khóa được bao bọc trong `WeakReference`, cho phép chúng được thu dọn rác khi không còn được sử dụng ở bên ngoài bản đồ. |
| `IdentityHashMap` | Bản đồ so sánh các khóa bằng cách sử dụng đồng nhất tham chiếu (`==`) thay vì bằng nhau về mặt logic (`equals()`). |
| `SortedMap` | Giao diện đại diện cho một Map được sắp xếp theo thứ tự tăng dần của các khóa của nó. |
| `NavigableMap` | Kế thừa SortedMap, bổ sung thêm các phương thức tìm kiếm/điều hướng như `lowerEntry()`, `floorKey()`, v.v. |
| `Iterator` | Giao diện cung cấp khả năng duyệt qua các tập hợp chỉ theo chiều tiến với sự hỗ trợ xóa phần tử an toàn. |
| `ListIterator` | Iterator hai chiều cho các đối tượng List cho phép duyệt tiến/lùi và sửa đổi phần tử. |

---

## Ghi chú chi tiết (Detailed Notes)

### ConcurrentHashMap

`ConcurrentHashMap` cung cấp khả năng đồng thời hoàn toàn cho các thao tác đọc và khả năng đồng thời cao cho các thao tác ghi.
- **Cơ chế khóa**: Trong Java 7, nó sử dụng cơ chế khóa phân đoạn (segment locking). Kể từ Java 8, nó sử dụng cơ chế khóa cấp nút (node-level locking - chỉ đồng bộ hóa trên nút đầu tiên của một xô/ngăn chứa) và các thao tác CAS (Compare-And-Swap) cho các xô trống. Điều này cho phép nhiều luồng ghi vào các xô khác nhau đồng thời.
- **An toàn với Null**: Từ chối các khóa và giá trị là `null`.

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

Một Map dựa trên bảng băm với các khóa được bao bọc trong `WeakReference`. Khi một khóa không còn được tham chiếu ở bất kỳ nơi nào khác, nó sẽ bị thu dọn rác (garbage collected), và mục nhập tương ứng sau đó sẽ được tự động xóa khỏi bản đồ.
- **Trường hợp sử dụng điển hình**: Bộ nhớ đệm (cách), bảng tra cứu siêu dữ liệu, hoặc các đăng ký lắng nghe (listener registries) nơi việc ánh xạ không nên giữ cho các đối tượng tiếp tục sống.

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

Một `IdentityHashMap` so sánh các khóa bằng cách sử dụng đồng nhất tham chiếu (`key1 == key2`) thay vì so sánh bằng nhau của đối tượng (`key1.equals(key2)`). Lớp này được thiết kế không phải để thay thế các triển khai Map thông thường.
- **Trường hợp sử dụng điển hình**: Các thuật toán duyệt đồ thị để phát hiện chu kỳ (cycle), tuần tự hóa đồ thị đối tượng, hoặc duy trì các thực thể đối tượng chính xác.

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

- **SortedMap**: Giao diện duy trì các khóa của nó theo thứ tự đã được sắp xếp.
- **NavigableMap**: Kế thừa `SortedMap` và bổ sung thêm các phương thức như `lowerEntry()`, `floorKey()`, `ceilingKey()`, và `higherKey()` để tìm các kết quả khớp gần nhất liên quan đến một khóa cho trước. `TreeMap` là triển khai chính của giao diện này.

### Iterator so với ListIterator (Iterator vs ListIterator)

- **Iterator**: Có thể duyệt qua bất kỳ `Collection` nào theo chiều tiến. Hỗ trợ xóa các phần tử trong quá trình lặp.
- **ListIterator**: Kế thừa `Iterator`. Chỉ có thể duyệt qua các triển khai của `List`. Hỗ trợ duyệt hai chiều (`hasPrevious()`, `previous()`), thay thế phần tử (`set()`), thêm phần tử (`add()`), và lấy chỉ mục hiện tại.

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

## Case Study: Bộ đếm Đa luồng Hiệu năng cao sử dụng ConcurrentHashMap (Case Study: High-Concurrency Counter using ConcurrentHashMap)

Khi xây dựng các bộ tổng hợp đa luồng, việc sử dụng các khối synchronized hoặc `synchronizedMap` sẽ tạo ra các nút thắt cổ chai về hiệu năng rất nghiêm trọng. Việc sử dụng `ConcurrentHashMap` kết hợp với `LongAdder` cho phép cập nhật bộ đếm đồng thời cực kỳ hiệu quả.

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

## Các lỗi thường gặp (Common Mistakes)

### 1. Vòng lặp tham chiếu mạnh giá trị trong WeakHashMap (WeakHashMap Strong Value Reference Loop)
Nếu giá trị trong một `WeakHashMap` chứa một tham chiếu mạnh (strong reference) quay ngược lại khóa, khóa đó sẽ không bao giờ bị thu dọn rác. Điều này làm rò rỉ bộ nhớ một cách âm thầm và làm mất đi toàn bộ mục đích của `WeakHashMap`.

### 2. Sửa đổi tập hợp trong quá trình lặp bằng các phương thức của Collection (Modifying collections during iteration with Collection methods)
Việc sử dụng `list.remove()` thay vì `iterator.remove()` bên trong một vòng lặp iterator đang hoạt động sẽ kích hoạt ngoại lệ `ConcurrentModificationException`. Luôn sửa đổi thông qua các phương thức của chính iterator đó.

### 3. Giả định rằng mọi thao tác trên ConcurrentHashMap đều có tính nguyên tử (Assuming ConcurrentHashMap operations are all atomic)
Mặc dù các thao tác đọc và ghi đơn lẻ trên `ConcurrentHashMap` là an toàn luồng và có tính nguyên tử, các chuỗi thao tác (ví dụ: `if (!map.containsKey(key)) { map.put(key, value); }`) thì KHÔNG có tính nguyên tử. Thay vào đó, hãy sử dụng `putIfAbsent()`, `compute()`, hoặc `computeIfAbsent()` để đảm bảo tính nguyên tử.

---

## Các câu hỏi ôn tập phổ biến (Common Review Prompts)

- ConcurrentHashMap sử dụng cơ chế gì kể từ Java 8 thay thế cho khóa phân đoạn segment locks? (Các thao tác CAS và đồng bộ hóa trên các nút đầu xô (synchronized bucket head nodes))
- Tại sao IdentityHashMap sử dụng `==`? (Để so sánh tính đồng nhất tham chiếu thay vì bằng nhau logic của equals, rất hữu ích cho các thuật toán duyệt đồ thị hoặc bộ đệm cấp hệ thống)
- Iterator nào hỗ trợ việc duyệt lùi? (ListIterator)

---

## Tại sao ConcurrentHashMap tránh cơ chế Khóa toàn cục (Why ConcurrentHashMap Avoids Global Locking)

Các bản đồ đồng bộ hóa truyền thống (chẳng hạn như `Hashtable` hoặc bộ bao bọc được trả về bởi `Collections.synchronizedMap()`) đạt được sự an toàn luồng bằng cách khóa toàn bộ cấu trúc dữ liệu trong quá trình đọc và ghi, tạo ra các nút thắt cổ chai về hiệu năng cực kỳ nghiêm trọng. Để khắc phục hạn chế này, `ConcurrentHashMap` áp dụng chiến lược phân tách khóa (lock-striping) nơi các thao tác đọc hoàn toàn không dùng khóa (lock-free), và các thao tác ghi chỉ thực hiện đồng bộ hóa ở cấp độ từng xô (bucket) riêng lẻ. Khi chèn một phần tử vào một xô trống, bản đồ sử dụng lệnh CPU Compare-And-Swap (CAS) để đặt nút đó một cách nguyên tử mà không cần lấy khóa phần mềm. Nếu xô đích không trống, luồng ghi sẽ lấy khóa chỉ trên nút đầu (head node) của xô cụ thể đó bằng cách sử dụng khối lệnh `synchronized` Java tiêu chuẩn, cho phép các luồng khác đọc hoặc ghi vào các xô khác đồng thời. Ngoài ra, `ConcurrentHashMap` từ chối các khóa và giá trị là `null` để ngăn chặn sự mơ hồ trong các ngữ cảnh đồng thời; nếu cho phép các giá trị `null`, ta sẽ không thể phân biệt một cách an toàn liệu một khóa có giá trị được ánh xạ là `null` hay khóa đó đơn giản là không tồn tại trong bản đồ, do việc gọi `containsKey(key)` ngay sau `get(key)` không có tính nguyên tử và dễ xảy ra tranh chấp dữ liệu (race conditions).

### Mô hình tư duy (Mental Model)

Khóa chi tiết (Fine-grained locking) nhắm vào các xô riêng lẻ thay vì khóa toàn bộ bản đồ:
```text
Hashtable / Synchronized Map:
[Khóa đang hoạt động] -> Khóa toàn bộ mảng [Bucket 0 | Bucket 1 | Bucket 2 | Bucket 3] (Chặn tất cả các luồng)

ConcurrentHashMap:
Bucket 0 (Trống): [ (Không có Nút) ] -> Luồng ghi chèn nút sử dụng CAS (Không có Khóa!)
Bucket 1 (Hoạt động): [ Nút đầu *Bị Khóa* ] -> Luồng ghi chỉ khóa nút đầu.
Bucket 2 (Hoạt động): [ Nút đầu ] -> Các luồng đọc duyệt qua không cần khóa.
Bucket 3 (Hoạt động): [ Nút đầu ] -> Các luồng ghi khác khóa bucket 3 đồng thời.
```

### Ví dụ Code (Code Example)

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

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
Khởi chạy thao tác ghi đồng thời
  → Xô trống?
  → Sử dụng thao tác CAS để ghi nút không cần khóa
  → Xô đã bị chiếm?
  → CHỈ đồng bộ hóa trên nút đầu xô (bucket head node)
  → Cho phép các thao tác ghi đồng thời vào các xô khác nhau
  → Từ chối các khóa/giá trị null để tránh sự mơ hồ về tranh chấp dữ liệu của get()/containsKey()
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ConcurrentHashMap.html (Tài liệu API ConcurrentHashMap)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Collections.html#synchronizedMap(java.util.Map) (Lớp bao bọc synchronizedMap)
