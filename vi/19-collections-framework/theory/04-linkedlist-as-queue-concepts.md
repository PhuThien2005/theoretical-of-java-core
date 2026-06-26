# Collections Framework - Phần 4 (Collections Framework - Part 4)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần nội dung trọng tâm về **Collections Framework** bao gồm các thao tác với hàng đợi (Queue), các triển khai bản đồ (Map) (`HashMap`, `LinkedHashMap`, `TreeMap`, `Hashtable`), và các hành vi FIFO/LIFO.

## Các khái niệm bao phủ (Outline Coverage)

| Khái niệm | Những điều cần biết |
| --- | --- |
| `LinkedList as Queue` | LinkedList triển khai giao diện Queue, cung cấp một hàng đợi FIFO tiêu chuẩn được hỗ trợ bởi một danh sách liên kết kép. |
| `FIFO` | Thứ tự vào trước ra trước (First-In-First-Out) nơi các phần tử được chèn ở cuối (tail) và được xóa ở đầu (head). |
| `LIFO` | Thứ tự vào sau ra trước (Last-In-First-Out) (hành vi của Stack) nơi các phần tử được thêm và xóa ở cùng một đầu. |
| `HashMap` | Bảng băm dựa trên xô (bucket) cây đỏ đen và danh sách liên kết ánh xạ các khóa tới các giá trị. Không có thứ tự, cho phép chứa một khóa null. |
| `LinkedHashMap` | Triển khai bảng băm và danh sách liên kết kép của giao diện Map, bảo toàn thứ tự chèn hoặc thứ tự truy cập. |
| `TreeMap` | Triển khai NavigableMap dựa trên cây Đỏ Đen, sắp xếp các khóa theo thứ tự tự nhiên hoặc qua một Comparator tùy chỉnh. |
| `Hashtable` | Bản đồ đồng bộ hóa cũ (legacy synchronized map). Từ chối các khóa và giá trị null. Đã lỗi thời. |

---

## Ghi chú chi tiết (Detailed Notes)

### LinkedList làm Queue (LinkedList as Queue)

`LinkedList` triển khai giao diện `Queue`, cho phép nó hoạt động như một cấu trúc FIFO. Vì nó là một danh sách liên kết kép, việc thêm vào hàng đợi (`offer()`) và lấy ra khỏi hàng đợi (`poll()`) là các thao tác `O(1)` cực kỳ hiệu quả.

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

### FIFO so với LIFO (FIFO vs LIFO)

- **FIFO (First-In-First-Out)**: Các phần tử được xử lý theo đúng thứ tự chúng được đưa vào. Được sử dụng cho lập lịch công việc (job scheduling), bộ đệm (buffering), v.v.
- **LIFO (Last-In-First-Out)**: Phần tử mới nhất sẽ được xử lý trước. Được sử dụng cho ngăn xếp cuộc gọi (call stacks), bộ đệm hoàn tác/làm lại (undo/redo buffers). Trong Java, hãy sử dụng `Deque` (ví dụ: `ArrayDeque`) cho các ngăn xếp LIFO thay vì lớp `Stack` cũ.

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

`HashMap` lưu trữ các cặp khóa-giá trị bằng bảng băm.
- **Cấu trúc nội bộ**: Một mảng chứa các xô (buckets) dạng Node/Entry. Khi xảy ra xung đột băm (collisions), các mục nhập được lưu trữ trong một danh sách liên kết. Kể từ Java 8+, nếu kích thước của một xô vượt quá 8 và tổng dung lượng bảng tối thiểu là 64, danh sách liên kết sẽ được chuyển đổi thành Cây Đỏ Đen ("cấu trúc cây - treeified") để cải thiện hiệu năng trong trường hợp xấu nhất từ `O(N)` về `O(log N)`.
- **Duyệt qua**: Không đảm bảo thứ tự.

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

`LinkedHashMap` kế thừa `HashMap` nhưng duy trì một danh sách liên kết kép chạy qua tất cả các mục nhập (entries) của nó.
- **Các chế độ sắp xếp**:
  1. **Thứ tự chèn (Insertion Order)**: Thứ tự lặp khớp với trình tự chèn các phần tử (mặc định).
  2. **Thứ tự truy cập (Access Order)**: Thứ tự lặp khớp với trình tự truy cập gần nhất (từ phần tử ít được truy cập nhất đến phần tử được truy cập gần đây nhất). Rất hữu ích để xây dựng bộ nhớ đệm (caches).

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

`TreeMap` là một triển khai Cây Đỏ Đen của giao diện `NavigableMap`.
- **Thứ tự**: Được sắp xếp theo thứ tự tự nhiên của các khóa hoặc theo một `Comparator` tùy chỉnh.
- **Hạn chế**: Các khóa phải có khả năng so sánh được với nhau và không được phép là `null`.

**Ví dụ Code có thể chạy (Runnable Code Example):**
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

`Hashtable` là một lớp bản đồ đồng bộ hóa cũ.
- **Đồng bộ hóa**: Sử dụng đồng bộ hóa thô (coarse-grained synchronization) trên mọi phương thức.
- **An toàn với Null**: Ném ra `NullPointerException` nếu có bất kỳ khóa hoặc giá trị nào là `null`.
- **Lỗi thời**: Tránh sử dụng lớp này. Hãy sử dụng `ConcurrentHashMap` cho các bản đồ cần xử lý đồng thời, hoặc `HashMap` cho các bản đồ không cần đồng thời.

---

## Case Study: Thiết kế Bộ nhớ đệm Ít được Sử dụng Gần đây (LRU) (Case Study: Designing a Least Recently Used (LRU) Cache)

Một bộ nhớ đệm LRU sẽ loại bỏ các phần tử ít được sử dụng gần đây nhất trước tiên khi đạt đến giới hạn dung lượng. Chúng ta có thể triển khai điều này một cách dễ dàng bằng cách kế thừa `LinkedHashMap` và ghi đè phương thức `removeEldestEntry()`.

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

## Các lỗi thường gặp (Common Mistakes)

### 1. Nhầm lẫn phương thức Queue: poll() so với remove() (Queue method confusion: poll() vs remove())
Việc gọi `remove()` trên một Queue trống sẽ ném ra ngoại lệ `NoSuchElementException`. Ngược lại, `poll()` trả về `null`. Các tình huống hàng đợi rỗng có thể gây sập chương trình nếu nhà phát triển sử dụng nhầm lẫn các phương thức này.

### 2. Sửa đổi khóa trong một HashMap (Modifying keys in a HashMap)
Nếu các thuộc tính của khóa bị thay đổi sau khi chèn vào bản đồ khiến `hashCode()` của khóa thay đổi, mục nhập đó sẽ không thể truy xuất được nữa. Bản đồ sẽ tìm kiếm nó ở nhầm xô (bucket) và trả về `null`.

### 3. Giả định rằng LinkedHashMap được sắp xếp (Assuming LinkedHashMap is sorted)
Các nhà phát triển đôi khi nhầm lẫn `LinkedHashMap` (duy trì thứ tự chèn/truy cập) với `TreeMap` (duy trì thứ tự sắp xếp theo chữ cái/tự nhiên).

---

## Các câu hỏi ôn tập phổ biến (Common Review Prompts)

- Triển khai map nào từ chối khóa null? (TreeMap, Hashtable)
- Sự khác biệt giữa `peek()` và `element()` là gì? (`peek()` trả về null khi hàng đợi trống, `element()` ném ra ngoại lệ)
- HashMap xử lý xung đột băm như thế nào kể từ Java 8? (Sử dụng danh sách liên kết tối đa 8 phần tử, sau đó chuyển thành cây đỏ đen nếu dung lượng bảng >= 64)

---

## Tại sao Equals và HashCode phải được ghi đè cùng nhau (Why Equals and HashCode Must Be Overridden Together)

Trong collections framework của Java, các cấu trúc dựa trên băm như `HashMap` và `HashSet` dựa vào một hợp đồng nghiêm ngặt giữa `equals()` và `hashCode()` để lưu trữ và truy xuất các phần tử. Theo hợp đồng được định nghĩa trong lớp `java.lang.Object`, nếu hai đối tượng bằng nhau theo phương thức `equals(Object)`, chúng bắt buộc phải tạo ra cùng một kết quả số nguyên từ phương thức `hashCode()`. Khi bạn ghi đè `equals()` nhưng không ghi đè `hashCode()`, JVM sẽ sử dụng triển khai mặc định từ lớp `Object`, vốn tạo ra mã băm thường dựa trên địa chỉ bộ nhớ của đối tượng. Do đó, hai thực thể khóa giống nhau về mặt logic sẽ tạo ra các mã băm khác nhau và được ánh xạ tới các chỉ mục xô (bucket indices) khác nhau trong bảng nội bộ. Khi cố gắng truy xuất một giá trị bằng cách sử dụng một thực thể khóa tương đương về mặt logic nhưng là đối tượng khác, `HashMap.get(key)` tính toán một chỉ mục xô khác, khiến nó tìm kiếm nhầm xô và trả về `null`, dẫn đến việc trùng lặp khóa, mất dữ liệu và rò rỉ bộ nhớ một cách âm thầm.

### Mô hình tư duy (Mental Model)

Nếu không ghi đè `hashCode()`, hai đối tượng bằng nhau về mặt logic sẽ kết thúc ở các xô khác nhau:
```text
Key A ("John", ID 5) -> hashCode() = 9876 -> ánh xạ tới Bucket 2
Key B ("John", ID 5) -> hashCode() = 5432 -> ánh xạ tới Bucket 7

Bảng HashMap Nội bộ:
Bucket 2: [ Key A ("John", ID 5) -> "Value X" ]
Bucket 7: [ Key B ("John", ID 5) -> "Value Y" ] (Khóa trùng lặp được tạo!)

HashMap.get(Key B) tìm kiếm Bucket 7.
Nếu chúng ta chỉ lưu trữ Key A, get(Key B) tìm kiếm trong Bucket 7, không thấy gì, và trả về null!
```

### Ví dụ Code (Code Example)

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

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
Chỉ ghi đè `equals()`
  → Các đối tượng khóa giống nhau về mặt logic tạo ra các mã băm khác nhau
  → `map.put()` và `map.get()` tính toán các chỉ mục xô khác nhau
  → `HashMap` tìm kiếm ở các xô khác nhau cho cùng một khóa logic
  → `get()` trả về null và `put()` chèn các phần tử trùng lặp
  → Gây ra sai lệch cấu trúc dữ liệu và rò rỉ dữ liệu trong Map.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html#hashCode() (Hợp đồng Object.hashCode)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashMap.html (Tài liệu về HashMap)
