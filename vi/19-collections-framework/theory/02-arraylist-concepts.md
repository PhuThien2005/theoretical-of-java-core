# Collections Framework - Phần 2 (Collections Framework - Part 2)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần nội dung trọng tâm về **Collections Framework**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, chứ không chỉ là từ vựng rời rạc.

## Các khái niệm bao phủ (Outline Coverage)

| Khái niệm | Những điều cần biết |
| --- | --- |
| `ArrayList` | List là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập dựa trên vị trí. |
| `LinkedList` | List là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập dựa trên vị trí. |
| `Vector` | Vector là một khái niệm cụ thể trong Collections Framework; tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và cơ chế phát hiện lỗi thay vì chỉ nhớ tên của nó. |
| `Stack` | Stack là một cấu trúc dữ liệu lưu trữ các khung phương thức (method frames), các biến cục bộ và luồng gọi của từng luồng (thread). |
| `Comparing ArrayList and LinkedList` | List là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập dựa trên vị trí. |
| `When to use List?` | List là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập dựa trên vị trí. |
| `HashSet` | Set là một tập hợp từ chối các phần tử trùng lặp theo quy tắc bằng nhau. |
| `LinkedHashSet` | Set là một tập hợp từ chối các phần tử trùng lặp theo quy tắc bằng nhau. |

---

## Ghi chú chi tiết (Detailed Notes)

### ArrayList

Một `ArrayList` là một triển khai mảng có thể thay đổi kích thước (resizable-array) của giao diện `List`.
- **Cấu trúc nội bộ**: Được hỗ trợ bởi một mảng Java tiêu chuẩn. Khi mảng bị đầy, nó tự động mở rộng bằng cách tạo một mảng mới (thường có dung lượng gấp `1.5` lần dung lượng cũ) và sao chép các phần tử sang mảng mới thông qua phương thức `System.arraycopy()`.
- **Độ phức tạp**: Truy cập ngẫu nhiên theo chỉ mục là O(1). Thêm/xóa các phần tử ở cuối danh sách có độ phức tạp trung bình khấu hao là O(1), nhưng thêm/xóa ở giữa yêu cầu dịch chuyển các phần tử, có độ phức tạp là O(N).

### LinkedList

Một `LinkedList` là một triển khai danh sách liên kết kép (doubly-linked list) của các giao diện `List` và `Deque`.
- **Cấu trúc nội bộ**: Được cấu thành từ các nút (nodes), nơi mỗi nút giữ một tham chiếu đến phần tử, nút kế tiếp `next`, và nút trước đó `prev`.
- **Độ phức tạp**: Thời gian tra cứu là O(N), vì nó phải duyệt từ đầu hoặc cuối danh sách để tìm vị trí chỉ mục. Việc chèn và xóa ở đầu/cuối danh sách là O(1), và chèn/xóa tại một vị trí iterator đã biết là O(1).

### Vector

`Vector` là một phiên bản cũ (legacy), an toàn luồng (thread-safe) của `ArrayList`.
- **Đồng bộ hóa**: Mỗi phương thức public của nó đều được đồng bộ hóa `synchronized` riêng lẻ, dẫn đến chi phí tranh chấp luồng cực kỳ lớn.
- **Lỗi thời**: Gần như đã lỗi thời. Đối với ứng dụng đơn luồng, hãy sử dụng `ArrayList`. Đối với ứng dụng đa luồng, hãy ưu tiên sử dụng `CopyOnWriteArrayList` hoặc bao bọc danh sách thông qua `Collections.synchronizedList()`.

### Stack

Lớp `Stack` đại diện cho một ngăn xếp các đối tượng hoạt động theo nguyên lý vào sau ra trước (LIFO).
- **Lỗi thiết kế**: Lớp `Stack` kế thừa lớp `Vector`, điều này có nghĩa là nó kế thừa tất cả các thao tác của danh sách (như chèn/xóa dựa trên chỉ mục tại các vị trí tùy ý), vi phạm tính trừu tượng (Abstraction) của ngăn xếp. Nó cũng sử dụng cơ chế đồng bộ hóa, gây ảnh hưởng xấu đến hiệu năng.
- **Thay thế**: Hãy sử dụng các triển khai `Deque` như `ArrayDeque` cho các thao tác với ngăn xếp.

### So sánh ArrayList và LinkedList (Comparing ArrayList and LinkedList)

| Thao tác | ArrayList | LinkedList | Lưu ý |
| --- | --- | --- | --- |
| **Truy xuất Get (chỉ mục)** | `O(1)` | `O(N)` | ArrayList tính toán độ lệch mảng trực tiếp. |
| **Chèn/Xóa (Cuối)** | `O(1)` (Khấu hao) | `O(1)` | ArrayList có thể kích hoạt resize; LinkedList chỉ cập nhật con trỏ. |
| **Chèn/Xóa (Đầu)**| `O(N)` | `O(1)` | ArrayList phải dịch chuyển các phần tử; LinkedList chỉ cập nhật con trỏ đầu. |
| **Chèn/Xóa (Giữa)**| `O(N)` | `O(N)` | LinkedList phải duyệt tới chỉ mục ở giữa; ArrayList dịch chuyển các phần tử. |
| **Chi phí Bộ nhớ (Overhead)** | Thấp (mảng liên tục) | Cao (3 tham chiếu mỗi nút) | LinkedList tạo một đối tượng bao bọc cho từng phần tử. |

#### Thân thiện với Bộ nhớ & Bộ nhớ đệm (Memory & Cache Friendliness)
`ArrayList` lưu trữ các phần tử trong một khối bộ nhớ liên tục. Điều này hoàn toàn phù hợp với cấu trúc bộ nhớ đệm CPU hiện đại: việc tải một phần tử sẽ đồng thời kéo các phần tử liền kề vào bộ nhớ đệm L2/L3 (tính cục bộ không gian - spatial locality). Các nút của `LinkedList` có thể nằm rải rác khắp nơi trên heap, gây ra hiện tượng cache miss liên tục khi duyệt qua danh sách.

### Khi nào nên dùng List? (When to use List?)
Sử dụng một `List` khi:
1. Thứ tự của các phần tử cần được bảo toàn.
2. Cho phép các phần tử trùng lặp.
3. Yêu cầu truy xuất phần tử theo chỉ mục.
*Lựa chọn mặc định: Hãy luôn ưu tiên `ArrayList` trừ khi bạn có các yêu cầu đã được xác minh rõ ràng về việc chèn/xóa liên tục ở đầu danh sách.*

### HashSet

Một `HashSet` là một Set được hỗ trợ bởi một thực thể `HashMap`.
- **Thứ tự**: Không đưa ra đảm bảo nào về thứ tự lặp của các phần tử. Thứ tự có thể thay đổi khi các phần tử mới được thêm vào.
- **Hiệu năng**: Độ phức tạp thời gian O(1) cho các hoạt động cơ bản (`add`, `remove`, `contains`), giả định rằng có một hàm băm (hash function) tốt.
- **Giá trị null**: Cho phép chứa một phần tử `null`.

### LinkedHashSet

`LinkedHashSet` là một triển khai bảng băm và danh sách liên kết kép của giao diện `Set`.
- **Thứ tự**: Bảo toàn thứ tự chèn. Việc lặp qua sẽ trả về các phần tử theo đúng thứ tự chúng được thêm vào.
- **Hiệu năng**: Hoạt động O(1), chậm hơn một chút so với `HashSet` do chi phí duy trì các con trỏ danh sách liên kết kép.

---

**Ví dụ Code có thể chạy (So sánh hiệu năng & Thứ tự) (Runnable Code Example (Performance & Order Comparison)):**
```java
import java.util.*;

public class ListSetComparison {
    public static void main(String[] args) {
        // 1. Insertion order comparison
        Set<String> hashSet = new HashSet<>();
        Set<String> linkedHashSet = new LinkedHashSet<>();
        
        List<String> fruits = List.of("Orange", "Apple", "Banana");
        hashSet.addAll(fruits);
        linkedHashSet.addAll(fruits);
        
        System.out.println("HashSet (arbitrary order): " + hashSet);
        System.out.println("LinkedHashSet (insertion order): " + linkedHashSet);
        
        // 2. Performance Comparison (ArrayList vs LinkedList lookup)
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        int count = 100_000;
        
        for (int i = 0; i < count; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        
        long start = System.nanoTime();
        int val1 = arrayList.get(count / 2);
        long arrayListTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        int val2 = linkedList.get(count / 2);
        long linkedListTime = System.nanoTime() - start;
        
        System.out.println("ArrayList mid-lookup time: " + arrayListTime + " ns");
        System.out.println("LinkedList mid-lookup time: " + linkedListTime + " ns");
    }
}
```

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Tin rằng LinkedList luôn nhanh hơn cho việc thêm phần tử (Believing LinkedList is always faster for additions)
Một quan niệm sai lầm phổ biến là `LinkedList` luôn vượt trội khi thêm các phần tử. Mặc dù việc cập nhật các con trỏ là O(1), nhưng việc tìm kiếm chỉ mục để chèn phần tử lại mất thời gian O(N). Hơn nữa, vì mỗi lần chèn đều tạo ra một đối tượng nút mới, `LinkedList` gây ra nhiều hoạt động thu dọn rác (garbage collection) hơn nhiều so với `ArrayList`.

### 2. Sử dụng Stack thay vì Deque (Using Stack instead of Deque)
Việc sử dụng lớp `java.util.Stack` cũ phát sinh thêm chi phí đồng bộ hóa và thiết kế hướng đối tượng không tốt (để lộ các phương thức danh sách). Thay vào đó, hãy sử dụng `Deque<Integer> stack = new ArrayDeque<>();`.

### 3. Sửa đổi khóa trong một HashSet (Modifying keys in a HashSet)
Nếu bạn chèn một đối tượng khả biến (mutable) vào một `HashSet`, và sau đó sửa đổi các trường của đối tượng đó khiến `hashCode()` của nó thay đổi, phần tử đó sẽ bị thất lạc bên trong Set. Mọi nỗ lực tìm kiếm nó bằng cách sử dụng `contains()` sẽ trả về `false`, vì Java tìm kiếm trong xô (bucket) tương ứng với mã băm mới, trong khi đối tượng vẫn nằm trong xô tương ứng với mã băm cũ.

## Các câu hỏi ôn tập phổ biến (Common Review Prompts)

- Khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch?
- Khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Khái niệm nào ở đây dễ là những bẫy câu hỏi phỏng vấn?

---

## Tại sao ArrayList thay đổi kích thước tăng 1.5 lần (Why ArrayList Resizes by 1.5x)

Khi một `ArrayList` vượt quá dung lượng hiện tại của nó, nó phải thay đổi kích thước (resize) để chứa các phần tử mới. Triển khai JDK của `ArrayList` tăng dung lượng của nó thêm 50% (1.5x) bằng cách sử dụng công thức `newCapacity = oldCapacity + (oldCapacity >> 1)`. Hệ số tăng trưởng 1.5 này thể hiện một điểm ngọt toán học (mathematical sweet spot) trong kỹ thuật hệ thống máy tính: tăng lên gấp 2 lần sẽ lãng phí quá nhiều bộ nhớ và ngăn cản việc tái sử dụng bộ nhớ trong các lần cấp phát tiếp theo, trong khi hệ số gần 1.0 sẽ gây ra việc resize liên tục, tốn kém. Vì các mảng Java được cấp phát dưới dạng các khối bộ nhớ liên tục trên heap của JVM, kích thước của chúng là bất biến một khi đã được tạo ra. Do đó, để thay đổi kích thước, JVM phải cấp phát một mảng hoàn toàn mới có kích thước lớn hơn và sao chép mọi tham chiếu hiện có sang mảng mới thông qua `Arrays.copyOf()` (ủy quyền cho phương thức gốc `System.arraycopy()`), khiến thao tác resize có độ phức tạp là `O(N)` trong trường hợp xấu nhất.

### Mô hình tư duy (Mental Model)

Khi mảng hỗ trợ phía sau bị đầy, một mảng lớn hơn được cấp phát và các phần tử được sao chép:
```text
Mảng hỗ trợ (Đầy, Dung lượng 4):
[ A ] [ B ] [ C ] [ D ]  (Vùng nhớ heap liên tục)
  |     |     |     |
  v     v     v     v
Cấp phát mảng mới (kích thước gấp 1.5x = Dung lượng 6):
[ A ] [ B ] [ C ] [ D ] [   ] [   ]
  |     |     |     |     ^     ^
  +-----+-----+-----+-----+-----+---- (Các phần tử được sao chép qua System.arraycopy)
```

### Ví dụ Code (Code Example)

```java
import java.util.ArrayList;
import java.lang.reflect.Field;

public class ArrayListResizeDemo {
    public static void main(String[] args) throws Exception {
        ArrayList<Integer> list = new ArrayList<>(4);
        System.out.println("Initial size: " + list.size()); // Initial size: 0
        
        list.add(1); list.add(2); list.add(3); list.add(4);
        // Under the hood, capacity is 4. Adding 5th element triggers grow()
        list.add(5);
        
        // Reflectively check the backing array capacity
        Field elementDataField = ArrayList.class.getDeclaredField("elementData");
        elementDataField.setAccessible(true);
        Object[] elementData = (Object[]) elementDataField.get(list);
        System.out.println("New capacity after 1.5x resize: " + elementData.length);
        // New capacity after 1.5x resize: 6
    }
}
```

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
`ArrayList` đạt đến giới hạn dung lượng
  → Thao tác thêm phần tử kích hoạt hàm trợ giúp nội bộ `grow()`
  → Phép dịch bit tính toán dung lượng mới = dung lượng cũ + dung lượng cũ/2 (1.5x)
  → JVM cấp phát mảng liên tục mới trên Heap
  → `System.arraycopy()` sao chép tất cả các phần tử (chi phí O(N))
  → Tham chiếu mảng hỗ trợ cũ bị loại bỏ cho quá trình Thu dọn rác (Garbage Collection).
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html (Tài liệu API lớp ArrayList)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html#copyOf(T%5B%5D,int) (Phương thức Arrays.copyOf)
