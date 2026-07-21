# Khung Tập Hợp (Collections Framework) - Phần 2

## Mục Tiêu Học Tập

Tài liệu này tập trung vào một phần trọng tâm của **Khung Tập Hợp (Collections Framework)**. Hãy nghiên cứu từng khái niệm dưới dạng quy tắc thực tế trong Java, không chỉ đơn thuần là lý thuyết từ vựng.

## Tóm Tắt Nội Dung (Outline Coverage)

- **`ArrayList`** — Một `List` là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí.
- **`LinkedList`** — Một `List` là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí.
- **`Vector`** — `Vector` là một khái niệm cụ thể trong Khung Tập Hợp; hãy tìm hiểu quy tắc Java của nó, các trường hợp sử dụng hợp lệ và kịch bản thất bại của nó thay vì chỉ ghi nhớ tên gọi.
- **`Stack`** — Ngăn xếp (Stack) lưu trữ các khung phương thức (Method frame), biến cục bộ và luồng gọi cho mỗi luồng (Thread).
- **`So sánh ArrayList và LinkedList`** — Một `List` là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí.
- **`Khi nào nên sử dụng List?`** — Một `List` là một tập hợp có thứ tự có thể chứa các phần tử trùng lặp và hỗ trợ truy cập theo vị trí.
- **`HashSet`** — Một `Set` là một tập hợp từ chối các phần tử trùng lặp theo quy tắc bằng nhau (equality rules).
- **`LinkedHashSet`** — Một `Set` là một tập hợp từ chối các phần tử trùng lặp theo quy tắc bằng nhau.

## Ghi Chú Chi Tiết

### ArrayList

Một `ArrayList` là một triển khai mảng có thể thay đổi kích thước của giao diện `List`.
- **Cấu trúc bên trong**: Được hỗ trợ bởi một mảng Java tiêu chuẩn. Khi mảng bị đầy, nó sẽ tự động tăng kích thước bằng cách tạo một mảng mới (thường có dung lượng gấp `1.5` lần dung lượng cũ) và sao chép các phần tử sang mảng mới thông qua `System.arraycopy()`.
- **Độ phức tạp**: Truy cập ngẫu nhiên theo chỉ số mất thời gian $O(1)$. Thêm/xóa phần tử ở cuối có độ phức tạp phân bổ (amortized) là $O(1)$, nhưng thêm/xóa phần tử ở giữa yêu cầu dịch chuyển các phần tử, mất thời gian $O(N)$.

### LinkedList

Một `LinkedList` là triển khai danh sách liên kết đôi (Doubly-linked list) của giao diện `List` và `Deque`.
- **Cấu trúc bên trong**: Được cấu thành từ các nút (Node), mỗi nút chứa tham chiếu đến phần tử thực tế, nút tiếp theo (`next`) và nút trước đó (`prev`).
- **Độ phức tạp**: Thời gian tìm kiếm là $O(N)$, vì nó phải duyệt từ đầu hoặc cuối để xác định vị trí chỉ số. Thêm và xóa ở đầu/cuối mất thời gian $O(1)$, và thêm/xóa tại một vị trí trình lặp (iterator) đã biết trước mất thời gian $O(1)$.

### Vector

`Vector` là một phiên bản cũ, an sau luồng (Thread-safe) của `ArrayList`.
- **Đồng bộ hóa**: Mọi phương thức công khai đều được đồng bộ hóa riêng lẻ (`synchronized`), dẫn đến chi phí tranh chấp luồng (Thread-contention) cực lớn.
- **Lỗi thời**: Hầu như đã lỗi thời. Đối với ứng dụng đơn luồng, hãy dùng `ArrayList`. Đối với ứng dụng đa luồng, hãy ưu tiên dùng `CopyOnWriteArrayList` hoặc bao bọc danh sách bằng `Collections.synchronizedList()`.

### Stack

Lớp `Stack` đại diện cho một ngăn xếp đối tượng hoạt động theo nguyên lý vào sau ra trước (Last-In-First-Out - LIFO).
- **Lỗi thiết kế**: `Stack` kế thừa từ `Vector`, điều này có nghĩa là nó thừa hưởng tất cả các hoạt động của danh sách (như chèn/xóa phần tử tại chỉ số bất kỳ), vi phạm tính trừu tượng của ngăn xếp. Nó cũng được đồng bộ hóa, gây ảnh hưởng đến hiệu năng.
- **Thay thế**: Hãy sử dụng các triển khai `Deque` như `ArrayDeque` cho các thao tác với ngăn xếp.

### So sánh ArrayList và LinkedList

**Truy cập (theo chỉ số)** — ArrayList mất thời gian `O(1)`, trong khi LinkedList mất `O(N)`. ArrayList sử dụng dịch chuyển địa chỉ trực tiếp trong mảng.

**Thêm/Xóa (ở Cuối)** — ArrayList mất `O(1)` (Phân bổ), LinkedList mất `O(1)`. ArrayList có thể kích hoạt tăng kích thước; LinkedList cập nhật các con trỏ.

**Thêm/Xóa (ở Đầu)** — ArrayList mất `O(N)`, LinkedList mất `O(1)`. ArrayList dịch chuyển tất cả phần tử; LinkedList cập nhật con trỏ đầu.

**Thêm/Xóa (ở Giữa)** — ArrayList và LinkedList đều mất `O(N)`. LinkedList phải duyệt đến vị trí giữa; ArrayList dịch chuyển phần tử.

**Chi phí bộ nhớ bổ sung** — ArrayList có chi phí thấp do dùng mảng liên tục, trong khi LinkedList có chi phí cao do cần 3 tham chiếu mỗi nút. LinkedList tạo ra một đối tượng bao bọc cho mỗi phần tử.

#### Thân thiện với Bộ nhớ & Bộ đệm CPU
`ArrayList` lưu trữ các phần tử trong một khối bộ nhớ liên tục. Điều này hoàn toàn phù hợp với cấu trúc bộ đệm CPU hiện đại: việc tải một phần tử sẽ kéo theo các phần tử liền kề vào bộ đệm L2/L3 (tính cục bộ không gian - Spatial locality). Trái lại, các nút của `LinkedList` có thể nằm rải rác khắp nơi trên heap, gây ra hiện tượng lỡ bộ đệm CPU (Cache miss) khi duyệt danh sách.

### Khi nào nên sử dụng List?
Sử dụng một `List` khi:
1. Cần bảo toàn thứ tự phần tử.
2. Chấp nhận các phần tử trùng lặp.
3. Yêu cầu truy xuất phần tử dựa trên chỉ số (Index).
*Lựa chọn mặc định: Luôn ưu tiên dùng `ArrayList` trừ khi bạn có yêu cầu được kiểm chứng rõ ràng về việc chèn/xóa liên tục ở đầu danh sách.*

### HashSet

Một `HashSet` là một tập hợp (Set) được hỗ trợ bởi một thực thể `HashMap`.
- **Thứ tự**: Không đảm bảo thứ tự duyệt phần tử. Thứ tự có thể thay đổi khi các phần tử mới được thêm vào.
- **Hiệu năng**: Độ phức tạp thời gian là $O(1)$ cho các thao tác cơ bản (`add`, `remove`, `contains`), với điều kiện là hàm băm hoạt động tốt.
- **Giá trị null**: Cho phép một phần tử `null`.

### LinkedHashSet

`LinkedHashSet` là một triển khai bảng băm kết hợp danh sách liên kết đôi của giao diện `Set`.
- **Thứ tự**: Duy trì thứ tự chèn. Việc duyệt sẽ trả về các phần tử theo thứ tự chúng được thêm vào.
- **Hiệu năng**: Các thao tác có độ phức tạp $O(1)$, chậm hơn một chút so với `HashSet` do chi phí duy trì các con trỏ của danh sách liên kết đôi.

---

**Ví dụ Code có thể chạy được (So sánh Hiệu năng & Thứ tự):**
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

## Các lỗi thường gặp

### 1. Tin rằng LinkedList luôn nhanh hơn khi thêm phần tử
Một quan niệm sai lầm phổ biến là `LinkedList` luôn vượt trội khi thêm phần tử. Mặc dù việc cập nhật các con trỏ mất thời gian $O(1)$, nhưng việc tìm chỉ số để thực hiện chèn lại mất thời gian $O(N)$. Hơn nữa, vì mỗi thao tác chèn tạo ra một đối tượng nút mới, `LinkedList` gây ra nhiều hoạt động thu gom rác (GC) hơn nhiều so với `ArrayList`.

### 2. Sử dụng Stack thay vì Deque
Sử dụng lớp cũ `java.util.Stack` gây ra chi phí đồng bộ hóa không cần thiết và là một thiết kế hướng đối tượng tồi (làm lộ các phương thức của danh sách). Thay vào đó, hãy sử dụng `Deque<Integer> stack = new ArrayDeque<>();`.

### 3. Sửa đổi các khóa trong một HashSet
Nếu bạn chèn một đối tượng có thể thay đổi (Mutable object) vào một `HashSet`, và sau đó sửa đổi các trường của đối tượng đó khiến mã băm `hashCode()` của nó thay đổi, phần tử đó sẽ bị thất lạc bên trong Set. Các nỗ lực tìm kiếm nó bằng phương thức `contains()` sẽ trả về `false`, vì Java tìm kiếm ở ngăn chứa (Bucket) tương ứng với mã băm mới, trong khi đối tượng vẫn nằm ở ngăn chứa tương ứng với mã băm cũ.

## Các Câu Hỏi Ôn Tập Thường Gặp

- Những khái niệm nào ở đây là các quy tắc tại thời điểm biên dịch?
- Những khái niệm nào ở đây ảnh hưởng đến hành vi tại thời điểm chạy?
- Những khái niệm nào ở đây dễ trở thành bẫy khi phỏng vấn?

## Tại sao ArrayList Tăng Kích thước Thêm 1.5 Lần

Khi một `ArrayList` vượt quá dung lượng hiện tại của nó, nó phải thay đổi kích thước để chứa các phần tử mới. Triển khai JDK của `ArrayList` tăng kích thước dung lượng của nó thêm 50% (1.5 lần) bằng cách sử dụng công thức `newCapacity = oldCapacity + (oldCapacity >> 1)`. Hệ số tăng trưởng 1.5 này đại diện cho một điểm cân bằng toán học tối ưu trong kỹ thuật hệ thống máy tính: tăng lên gấp 2 lần sẽ lãng phí quá nhiều bộ nhớ và ngăn cản việc tái sử dụng bộ nhớ trong các phân bổ tiếp theo, trong khi một hệ số gần 1.0 sẽ gây ra các lần tăng kích thước thường xuyên và tốn kém. Vì các mảng trong Java được phân bổ dưới dạng các khối bộ nhớ liên tục trên heap của JVM, kích thước của chúng là bất biến sau khi được tạo. Do đó, để thay đổi kích thước, JVM phải phân bổ một mảng hoàn toàn mới có kích thước lớn hơn và sao chép mọi tham chiếu hiện có sang thông qua `Arrays.copyOf()` (chuyển tiếp tới phương thức gốc `System.arraycopy()`), làm cho thao tác thay đổi kích thước có độ phức tạp là $O(N)$ trong trường hợp xấu nhất.

### Mô hình Tư duy

Khi mảng nền bị đầy, một mảng lớn hơn được phân bổ và các phần tử được sao chép:
```text
Mảng nền (Đầy, Dung lượng 4):
[ A ] [ B ] [ C ] [ D ]  (Bộ nhớ heap liên tục)
  |     |     |     |
  v     v     v     v
Phân bổ mảng mới (Kích thước 1.5x = Dung lượng 6):
[ A ] [ B ] [ C ] [ D ] [   ] [   ]
  |     |     |     |     ^     ^
  +-----+-----+-----+-----+-----+---- (Các phần tử được sao chép qua System.arraycopy)
```

### Ví dụ Thực Tế

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

### Chuỗi Nguyên nhân - Kết quả

`ArrayList` đạt giới hạn dung lượng &rarr; Thao tác thêm phần tử kích hoạt hàm trợ giúp nội bộ `grow()` &rarr; Phép dịch bit tính toán dung lượng mới = dung lượng cũ + dung lượng cũ / 2 (1.5 lần) &rarr; JVM phân bổ mảng liên tục mới trên Heap &rarr; `System.arraycopy()` sao chép tất cả các phần tử (chi phí $O(N)$) &rarr; Tham chiếu mảng nền cũ bị loại bỏ để chờ Thu gom rác.

## Liên kết Tham khảo

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html (Tài liệu API lớp ArrayList)
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html#copyOf(T%5B%5D,int) (Phương thức Arrays.copyOf)
