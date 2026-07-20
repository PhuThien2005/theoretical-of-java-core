# Khung bộ sưu tập (Collections Framework) - Phần 4

## Mục Tiêu Học Tập

Tài liệu này trình bày một phần trọng tâm của **Khung bộ sưu tập (Collections Framework)** bao gồm các hoạt động của Hàng đợi (Queue), các triển khai Map (`HashMap`, `LinkedHashMap`, `TreeMap`, `Hashtable`), và các hành vi FIFO/LIFO.

## Nội Dung Tổng Quan

| Khái niệm | Điều cần biết |
| --- | --- |
| `LinkedList as Queue` | LinkedList triển khai giao diện Queue, cung cấp một hàng đợi FIFO tiêu chuẩn được hỗ trợ bởi một danh sách liên kết kép (doubly-linked list). |
| `FIFO` | Thứ tự Vào trước - Ra trước (First-In-First-Out) trong đó các phần tử được chèn vào phần đuôi (tail) và lấy ra từ phần đầu (head). |
| `LIFO` | Thứ tự Vào sau - Ra trước (Last-In-First-Out) (hành vi ngăn xếp - Stack) trong đó các phần tử được thêm vào và lấy ra từ cùng một đầu. |
| `HashMap` | Bảng băm dựa trên cây đỏ-đen (Red-black tree) và danh sách liên kết ánh xạ khóa (key) sang giá trị (value). Không có thứ tự, cho phép một khóa null. |
| `LinkedHashMap` | Triển khai giao diện Map dựa trên bảng băm và danh sách liên kết kép, giúp bảo toàn thứ tự chèn hoặc thứ tự truy cập. |
| `TreeMap` | Triển khai NavigableMap dựa trên cây đỏ-đen, sắp xếp các khóa theo thứ tự tự nhiên hoặc thông qua một Bộ so sánh (Comparator) tùy chỉnh. |
| `Hashtable` | Lớp map đồng bộ cũ (legacy synchronized map). Từ chối các khóa null và giá trị null. Đã lỗi thời (obsolete). |

## Ghi Chú Chi Tiết

### LinkedList dưới dạng Hàng đợi (LinkedList as Queue)

`LinkedList` triển khai `Queue`, cho phép nó hoạt động như một cấu trúc FIFO. Vì nó là một danh sách liên kết kép, việc xếp hàng (`offer()`) và hủy xếp hàng (`poll()`) là các hoạt động cực kỳ hiệu quả với độ phức tạp thời gian `O(1)`.

**Ví Dụ Mã Nguồn Có Thể Chạy Được:**
```java
import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {
    public static void main(String[] args) {
        Queue<String> printerQueue = new LinkedList<>();

        // Enqueuing
        printerQueue.offer("Doc1.pdf");
        printerQueue.offer("Doc2.pdf");

        // Inspecting head
        System.out.println("Next up: " + printerQueue.peek()); // Doc1.pdf

        // Dequeuing
        System.out.println("Printing: " + printerQueue.poll()); // Doc1.pdf
        System.out.println("Printing: " + printerQueue.poll()); // Doc2.pdf

        // Queue is now empty, poll() returns null (does not throw exception)
        System.out.println("Empty poll: " + printerQueue.poll()); // null
    }
}
```

### FIFO so với LIFO

- **FIFO (Vào trước - Ra trước)**: Các phần tử được xử lý theo đúng thứ tự khi chúng đến. Được sử dụng để lên lịch tác vụ, bộ đệm, v.v.
- **LIFO (Vào sau - Ra trước)**: Phần tử mới nhất được xử lý trước tiên. Được sử dụng cho các ngăn xếp cuộc gọi (call stack), bộ đệm hoàn tác/làm lại (undo/redo). Trong Java, hãy sử dụng `Deque` (ví dụ: `ArrayDeque`) cho các ngăn xếp LIFO thay vì lớp cũ `Stack`.

**Ví Dụ Mã Nguồn Có Thể Chạy Được:**
```java
import java.util.ArrayDeque;
import java.util.Deque;

public class LifoFifoDemo {
    public static void main(String[] args) {
        // LIFO Stack using ArrayDeque
        Deque<String> stack = new ArrayDeque<>();
        stack.push("Step 1");
        stack.push("Step 2");
        System.out.println("LIFO Pop: " + stack.pop()); // Step 2

        // FIFO Queue using ArrayDeque
        Deque<String> queue = new ArrayDeque<>();
        queue.addLast("Job 1");
        queue.addLast("Job 2");
        System.out.println("FIFO Remove: " + queue.removeFirst()); // Job 1
    }
}
```

### HashMap

`HashMap` lưu trữ các cặp khóa-giá trị bằng cách sử dụng bảng băm (hash table).
- **Cấu trúc nội bộ**: Một mảng gồm các ngăn chứa (bucket) kiểu Node/Entry. Khi xảy ra xung đột băm (collision), các phần tử được lưu trữ trong một danh sách liên kết. Từ Java 8 trở đi, nếu kích thước của một bucket vượt quá 8 và tổng dung lượng bảng tối thiểu là 64, danh sách liên kết sẽ được chuyển đổi thành Cây đỏ-đen (\"cây hóa\" - treeify) để cải thiện hiệu năng trong trường hợp xấu nhất từ `O(N)` thành `O(log N)`.
- **Duyệt phần tử (Iteration)**: Không đảm bảo thứ tự.

**Ví Dụ Mã Nguồn Có Thể Chạy Được:**
```java
import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, String> capitalMap = new HashMap<>();
        capitalMap.put("USA", "Washington D.C.");
        capitalMap.put("Japan", "Tokyo");
        capitalMap.put(null, "No Capital"); // Allows one null key

        System.out.println("Japan Capital: " + capitalMap.get("Japan")); // Tokyo
    }
}
```

### LinkedHashMap

`LinkedHashMap` kế thừa từ `HashMap` nhưng duy trì một danh sách liên kết kép đi qua tất cả các phần tử của nó.
- **Các chế độ sắp xếp thứ tự**:
  1. **Thứ tự chèn (Insertion Order)**: Việc duyệt phần tử khớp với trình tự chèn (mặc định).
  2. **Thứ tự truy cập (Access Order)**: Việc duyệt phần tử khớp với thứ tự của lần truy cập gần nhất (từ phần tử ít được truy cập nhất đến phần tử được truy cập gần đây nhất). Hữu ích cho việc xây dựng bộ nhớ đệm (cache).

**Ví Dụ Mã Nguồn Có Thể Chạy Được:**
```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {
    public static void main(String[] args) {
        // Access order set to true
        Map<Integer, String> cache = new LinkedHashMap<>(16, 0.75f, true);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");

        cache.get(1); // Access key 1

        // Iteration shows accessed items moved to the end
        System.out.println(cache); // {2=B, 3=C, 1=A}
    }
}
```

### TreeMap

`TreeMap` là một triển khai Cây đỏ-đen của `NavigableMap`.
- **Thứ tự**: Được sắp xếp theo thứ tự tự nhiên của các khóa hoặc thông qua một bộ so sánh `Comparator` tùy chỉnh.
- **Hạn chế**: Các khóa phải có khả năng so sánh được với nhau và không được là `null`.

**Ví Dụ Mã Nguồn Có Thể Chạy Được:**
```java
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Zebra", 1);
        treeMap.put("Apple", 2);
        treeMap.put("Mango", 3);

        System.out.println("Sorted Map: " + treeMap); // {Apple=2, Mango=3, Zebra=1}
        System.out.println("First Key: " + treeMap.firstKey()); // Apple
        System.out.println("Ceiling key for 'M': " + treeMap.ceilingKey("M")); // Mango
    }
}
```

### Hashtable

`Hashtable` là lớp map đồng bộ cũ (legacy synchronized map class).
- **Đồng bộ hóa**: Sử dụng đồng bộ hóa thô (coarse-grained) trên mọi phương thức.
- **An toàn null (Null Safety)**: Ném ra ngoại lệ `NullPointerException` nếu có bất kỳ khóa hoặc giá trị nào là `null`.
- **Lỗi thời**: Tránh sử dụng nó. Hãy sử dụng `ConcurrentHashMap` cho các bản đồ đồng thời (concurrent map), hoặc `HashMap` cho các bản đồ không đồng thời.

---

## Ví Dụ Thực Tế: Thiết kế bộ nhớ đệm LRU (Least Recently Used Cache)

Bộ nhớ đệm LRU sẽ loại bỏ các phần tử ít được truy cập nhất trước khi dung lượng đạt giới hạn tối đa. Chúng ta có thể triển khai điều này một cách dễ dàng bằng cách kế thừa `LinkedHashMap` và ghi đè phương thức `removeEldestEntry()`.

```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        // Enable access-order sorting (third argument = true)
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // Automatically evict when size exceeds maximum capacity
        return size() > capacity;
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        cache.put(1, "Value A");
        cache.put(2, "Value B");
        cache.put(3, "Value C");

        cache.get(1); // Access 1: Cache ordering becomes [2, 3, 1]

        cache.put(4, "Value D"); // Triggers eviction of key 2 (least recently used)

        System.out.println("Cache holds key 2: " + cache.containsKey(2)); // false
        System.out.println("Cache holds key 1: " + cache.containsKey(1)); // true
        System.out.println("Current Cache items: " + cache); // {3=Value C, 1=Value A, 4=Value D}
    }
}
```

---

## Sai Lầm Thường Gặp

### 1. Nhầm lẫn phương thức Queue: `poll()` so với `remove()`
Gọi phương thức `remove()` trên một Queue trống sẽ ném ra ngoại lệ `NoSuchElementException`. Ngược lại, `poll()` trả về `null`. Các trường hợp hàng đợi trống có thể làm hỏng chương trình nếu lập trình viên nhầm lẫn giữa hai phương thức này.

### 2. Thay đổi các khóa trong một HashMap
Nếu các thuộc tính của khóa bị thay đổi sau khi chèn phần tử sao cho giá trị `hashCode()` của khóa thay đổi, phần tử đó sẽ trở nên không thể truy cập được. Bản đồ sẽ tìm kiếm nó trong một bucket sai và trả về `null`.

### 3. Giả định rằng `LinkedHashMap` được sắp xếp
Các lập trình viên đôi khi nhầm lẫn `LinkedHashMap` (duy trì thứ tự chèn/truy cập) với `TreeMap` (duy trì thứ tự sắp xếp/thứ tự từ điển).

---

## Các Câu Hỏi Ôn Tập Thường Gặp

- Những triển khai map nào từ chối khóa null? (TreeMap, Hashtable)
- Sự khác biệt giữa `peek()` và `element()` là gì? (`peek()` trả về null khi hàng đợi trống, `element()` ném ra ngoại lệ)
- HashMap xử lý xung đột băm như thế nào kể từ Java 8? (Danh sách liên kết lên đến 8 phần tử, sau đó chuyển thành cây đỏ-đen nếu dung lượng bảng tối thiểu là 64)

## Tại Sao Phải Ghi Đè Đồng Thời Equals Và HashCode

> Xem thêm: Sự liên kết chặt chẽ và nguyên tắc (contract) giữa equals và hashCode, được trình bày chi tiết trong [Ch.14 - Object Class](../../14-object-class/theory/02-contract-of-equals-concepts.md).

Trong khung bộ sưu tập của Java, các cấu trúc dựa trên băm như `HashMap` và `HashSet` dựa vào một hợp đồng nghiêm ngặt giữa `equals()` và `hashCode()` để lưu trữ và truy xuất các phần tử. Theo hợp đồng được định nghĩa trong `java.lang.Object`, nếu hai đối tượng bằng nhau theo phương thức `equals(Object)`, chúng phải tạo ra kết quả số nguyên hoàn toàn giống nhau từ `hashCode()`. Khi bạn ghi đè `equals()` nhưng không ghi đè `hashCode()`, JVM sẽ sử dụng triển khai mặc định từ lớp `Object`, lớp này tạo ra mã băm thường dựa trên địa chỉ bộ nhớ của đối tượng. Do đó, hai thể hiện khóa tương đương về mặt logic sẽ tạo ra các mã băm khác nhau và được ánh xạ tới các chỉ mục bucket khác nhau trong bảng nội bộ. Khi cố gắng truy xuất một giá trị bằng cách sử dụng một thể hiện khóa bằng nhau về mặt logic nhưng là đối tượng khác, `HashMap.get(key)` sẽ tính toán một chỉ mục bucket khác, khiến nó tìm kiếm trong một bucket sai và trả về `null`, dẫn đến các khóa bị trùng lặp, mất dữ liệu và rò rỉ bộ nhớ ngầm.

### Mô Hình Tư Duy

Nếu không ghi đè `hashCode()`, hai đối tượng bằng nhau về mặt logic sẽ kết thúc ở các bucket khác nhau:
```text
Khóa A ("John", ID 5) -> hashCode() = 9876 -> ánh xạ tới Bucket 2
Khóa B ("John", ID 5) -> hashCode() = 5432 -> ánh xạ tới Bucket 7

Bảng HashMap nội bộ:
Bucket 2: [ Khóa A ("John", ID 5) -> "Value X" ]
Bucket 7: [ Khóa B ("John", ID 5) -> "Value Y" ] (Khóa trùng lặp được tạo ra!)

HashMap.get(Khóa B) tìm kiếm ở Bucket 7.
Nếu chúng ta chỉ lưu trữ Khóa A, get(Khóa B) sẽ tìm trong Bucket 7, không thấy gì và trả về null!
```

### Ví Dụ Mã Nguồn

```java
import java.util.HashMap;
import java.util.Objects;

public class EqualsHashCodeContractDemo {
    static class BadKey {
        private final int id;
        
        public BadKey(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BadKey badKey = (BadKey) o;
            return id == badKey.id;
        }

        // hashCode() is intentionally NOT overridden, inheriting Object.hashCode()
    }

    public static void main(String[] args) {
        HashMap<BadKey, String> map = new HashMap<>();
        BadKey key1 = new BadKey(101);
        BadKey key2 = new BadKey(101);
        
        map.put(key1, "Engineer A");
        
        // Output results demonstrating contract violation
        System.out.println("Are keys equal? " + key1.equals(key2)); // Are keys equal? true
        System.out.println("Key 1 hashCode: " + key1.hashCode());
        System.out.println("Key 2 hashCode: " + key2.hashCode()); // Different values!
        System.out.println("Retrieve via key2: " + map.get(key2)); // Retrieve via key2: null
        System.out.println("Map size: " + map.size()); // Map size: 1
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả

```text
Chỉ ghi đè equals() → Các đối tượng khóa bằng nhau về mặt logic tạo ra các mã băm khác nhau → map.put() và map.get() tính toán các chỉ mục bucket khác nhau → HashMap tìm kiếm ở các bucket khác nhau cho cùng một khóa logic → get() trả về null và put() chèn các phần tử trùng lặp → Xảy ra lỗi cấu trúc và rò rỉ dữ liệu trong Map
```

## Liên Kết Tham Khảo

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html#hashCode() (Object.hashCode contract)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashMap.html (HashMap documentation)
