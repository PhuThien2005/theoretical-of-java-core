# Generics trong Java – Lý thuyết Chi tiết (Generics in Java – Detailed Theory)

## 1. Lớp Generic (Generic Class)
- **Định nghĩa:** Một lớp khai báo một hoặc nhiều tham số kiểu (type parameters), cho phép nó hoạt động trên các đối tượng thuộc nhiều kiểu khác nhau trong khi vẫn cung cấp tính an toàn kiểu tại thời điểm biên dịch.
- **Quy tắc Java:** Danh sách tham số kiểu xuất hiện sau tên lớp, ví dụ: `class Box<T> { private T value; … }`. Tham số kiểu có thể được sử dụng ở bất kỳ nơi nào cho phép sử dụng kiểu thông thường bên trong thân lớp.
- **Trường hợp sử dụng hợp lệ:** Các container chứa bất kỳ kiểu dữ liệu nào – `Box<T>`, `Pair<K,V>`.
- **Trường hợp lỗi:** Sử dụng kiểu thô (raw type) (ví dụ: `Box` không có `<T>`) sẽ loại bỏ thông tin generic, dẫn đến các chuyển đổi không được kiểm tra và có thể xảy ra `ClassCastException` lúc chạy.

## 2. Phương thức Generic (Generic Method)
- **Định nghĩa:** Một phương thức giới thiệu các tham số kiểu của riêng nó, độc lập với các tham số kiểu của lớp.
- **Quy tắc Java:** Các tham số kiểu được khai báo trước kiểu trả về, ví dụ: `public static <T> T identity(T obj) { return obj; }`.
- **Trường hợp sử dụng hợp lệ:** Các phương thức tiện ích hoạt động với mọi kiểu dữ liệu như `Collections.max(Collection<? extends T>)` hoặc `Arrays.asList(T... a)`.
- **Trường hợp lỗi:** Bỏ qua danh sách tham số kiểu khiến phương thức quay trở lại sử dụng kiểu thô, điều này vô hiệu hóa các kiểm tra lúc biên dịch.

## 3. Giao diện Generic (Generic Interface)
- **Định nghĩa:** Một giao diện khai báo các tham số kiểu, cho phép các triển khai chỉ định các kiểu cụ thể.
- **Quy tắc Java:** Tương tự như đối với lớp – `interface Comparable<T> { int compareTo(T o); }`.
- **Trường hợp sử dụng hợp lệ:** Các hợp đồng hoạt động trên một kiểu dữ liệu cụ thể, ví dụ: `Comparator<T>`, `Iterable<T>`.
- **Trường hợp lỗi:** Triển khai dạng thô (`Comparable`) làm mất các đảm bảo generic; trình biên dịch sẽ phát ra các cảnh báo unchecked.

## 4. Tham số kiểu (Type Parameter)
- **Định nghĩa:** Một tên giữ chỗ (thường là một chữ cái in hoa đơn lẻ) đại diện cho một kiểu dữ liệu chưa xác định.
- **Quy ước chung:** `T` – kiểu dữ liệu (type), `E` – phần tử (element), `K` – khóa (key), `V` – giá trị (value), `N` – số (number), `S,U,V` – các kiểu dữ liệu bổ sung.
- **Phạm vi:** Chỉ hiển thị bên trong khai báo generic (lớp, phương thức, giao diện).

## 5. Nhiều Tham số kiểu (Multiple Type Parameters)
- **Cú pháp:** Phân tách bằng dấu phẩy, ví dụ: `class MapEntry<K, V> { private K key; private V value; }`.
- **Trường hợp sử dụng:** Các cấu trúc dữ liệu cần nhiều hơn một kiểu dữ liệu, chẳng hạn như `Map<K,V>`, `BiFunction<T,U,R>`.

## 6. Tham số kiểu có giới hạn (Bounded Type Parameter)
- **Cú pháp:** `T extends Bound` trong đó `Bound` có thể là một lớp hoặc giao diện (hoặc một tổ hợp thông qua phép toán `&`).
- **Ví dụ:** `class NumericBox<T extends Number> { private T value; }`.
- **Quy tắc:** Giới hạn này hạn chế tập hợp các kiểu dữ liệu được phép; bên trong lớp, bạn có thể gọi các phương thức được định nghĩa bởi giới hạn đó.
- **Trường hợp lỗi:** Cố gắng khởi tạo với một kiểu không liên quan (`new NumericBox<String>()`) sẽ dẫn đến lỗi biên dịch.

## 7. Ký tự đại diện (?) (Wildcards (?))
### 7.1 Ký tự đại diện không giới hạn – ? (Unbounded Wildcard – ?)
- **Ý nghĩa:** Kiểu chưa xác định. Hữu ích khi bạn chỉ cần đọc dữ liệu từ một tập hợp.
- **Ví dụ:** `void printAll(List<?> list) { for (Object o : list) System.out.println(o); }`.

### 7.2 Ký tự đại diện giới hạn trên – <? extends T> (Upper‑Bounded Wildcard – <? extends T>)
- **Ý nghĩa:** Một kiểu con chưa xác định của `T`.
- **PECS (Producer Extends):** Sử dụng khi đối tượng generic đóng vai trò **sản xuất (produces)** ra các giá trị thuộc kiểu `T`.
- **Ví dụ:** `List<? extends Number> numbers = List.of(1, 2.5); // chỉ đọc`
- **Trường hợp lỗi:** Bạn không thể thêm các phần tử (ngoại trừ `null`) vì kiểu con chính xác chưa được xác định.

### 7.3 Ký tự đại diện giới hạn dưới – <? super T> (Lower‑Bounded Wildcard – <? super T>)
- **Ý nghĩa:** Một kiểu cha chưa xác định của `T`.
- **PECS (Consumer Super):** Sử dụng khi đối tượng generic đóng vai trò **tiêu thụ (consumes)** các giá trị thuộc kiểu `T`.
- **Ví dụ:** `List<? super Integer> ints = new ArrayList<Number>(); ints.add(10);`
- **Trường hợp lỗi:** Khi đọc dữ liệu, bạn chỉ nhận được kiểu `Object` vì kiểu cha chính xác chưa được xác định.

## 8. PECS – Producer Extends, Consumer Super
- **Nguyên tắc hướng dẫn:** 
  - Nếu một generic **sản xuất** ra các giá trị &rarr; sử dụng `extends`.
  - Nếu nó **tiêu thụ** các giá trị &rarr; sử dụng `super`.
- **Các API điển hình:**
  - `Collections.copy(List<? super T> dest, List<? extends T> src)`
  - `Stream<T> map(Function<? super T, ? extends R>)`

## 9. Generics với Collection (Generics with Collections)
| Collection | Khai báo điển hình | Lý do |
|------------|--------------------|--------|
| `List` | `List<E>` – `E` là kiểu phần tử. | Cho phép thêm/lấy phần tử an toàn kiểu. |
| `Set` | `Set<E>` – không trùng lặp các phần tử kiểu `E`. | Cho phép thêm/lấy phần tử an toàn kiểu. |
| `Map` | `Map<K,V>` – `K` khóa, `V` giá trị. | Cho phép kiểm tra ở thời điểm biên dịch đối với cả kiểu khóa và kiểu giá trị. |
| `Queue` | `Queue<E>` – ngữ nghĩa hàng đợi FIFO. | Cho phép thêm/lấy phần tử an toàn kiểu. |
| `Deque` | `Deque<E>` – hàng đợi hai đầu. | Cho phép thêm/lấy phần tử an toàn kiểu. |
| `Optional` | `Optional<T>` – container chứa một giá trị có thể vắng mặt. | Cho phép thêm/lấy phần tử an toàn kiểu. |

**Ví dụ:**
```java
List<String> names = new ArrayList<>();
Map<Integer, String> idToName = new HashMap<>();
Queue<Runnable> tasks = new ArrayDeque<>();
```
Khi bạn cần sự linh hoạt, bạn có thể sử dụng các ký tự đại diện:
```java
void processAll(List<? extends Number> numbers) { ... }
void addAll(List<? super Integer> ints) { ints.add(1); }
```

## 10. Xóa bỏ kiểu (Type Erasure)
- **Cơ chế hoạt động:** Tại thời điểm biên dịch, thông tin kiểu generic bị loại bỏ. Mã byte chỉ chứa **kiểu thô (raw type)** và các phép ép kiểu được chèn vào nơi cần thiết.
- **Hệ quả:**
  - Không kiểm tra kiểu generic lúc chạy.
  - Bạn không thể nạp chồng các phương thức chỉ khác nhau bởi tham số kiểu generic.
  - Không thể sử dụng `instanceof` với một kiểu generic (ví dụ `if (obj instanceof List<String>)` là bất hợp pháp).

## 11. Kiểu thô (Raw Types)
- **Định nghĩa:** Sử dụng một lớp hoặc giao diện generic mà không chỉ định các đối số kiểu, ví dụ: `List raw = new ArrayList();`.
- **Ảnh hưởng:** Vô hiệu hóa tính an toàn của generic, kích hoạt cảnh báo unchecked, và có thể gây ra lỗi `ClassCastException` lúc chạy.
- **Khi nào cần tránh:** Gần như luôn luôn; chỉ sử dụng khi tương tác với mã nguồn cũ được viết trước thời kỳ generics (Java 5).

## 12. Các hạn chế của Generic (Generic Limitations)
| Hạn chế | Giải thích | Giải pháp thay thế |
|------------|-------------|------------|
| **Không có mảng generic** | `new T[10]` là không hợp lệ vì cơ chế xóa bỏ kiểu. | Sử dụng `List<T>` hoặc `Array.newInstance(clazz, size)` với một token `Class<T>`. |
| **Không có kiểu nguyên thủy generic** | Các tham số kiểu bắt buộc phải là kiểu tham chiếu. | Sử dụng các lớp bao bọc (`Integer`, `Double`). |
| **Không có trường tĩnh thuộc kiểu tham số kiểu** | Các thành viên tĩnh thuộc về lớp, không thuộc về một đối số kiểu cụ thể nào. | Sử dụng các trường phi tĩnh hoặc chụp lại kiểu dữ liệu bằng đối số `Class<T>`. |
| **Không thể tạo lớp con generic của lớp phi generic với các đối số kiểu cụ thể** | Ví dụ: `class MyStringList extends ArrayList<String>` được phép, nhưng bạn không thể coi nó là `ArrayList<T>` sau đó. | |
| **Giới hạn của suy luận kiểu** | Các generic lồng nhau phức tạp có thể yêu cầu đối số kiểu tường minh. | Cung cấp tham số kiểu tường minh hoặc sử dụng phương thức trợ giúp. |

## 13. Tổng hợp lại – Ví dụ (Putting It All Together – Example)
```java
public class Pair<K, V> {
    private final K key;
    private final V value;
    public Pair(K key, V value) { this.key = key; this.value = value; }
    public K getKey() { return key; }
    public V getValue() { return value; }
}

// Using bounded type parameters and PECS
public static <T extends Number> double sum(List<? extends T> numbers) {
    double total = 0;
    for (T n : numbers) total += n.doubleValue(); // safe: T is a Number
    return total;
}

public static void addIntegers(List<? super Integer> list) {
    list.add(1); // safe: list can accept Integer or any of its supertypes
}
```
**Các kịch bản lỗi:**
- Truyền một `List<Object>` vào `sum` – lỗi biên dịch vì `Object` không kế thừa `Number`.
- Cố gắng thêm phần tử vào `List<? extends Number>` – lỗi lúc biên dịch: không thể thêm bất kỳ phần tử nào ngoại trừ `null`.

---
**Điểm rút ra quan trọng:** Generics cung cấp cho Java cơ chế định kiểu tĩnh mạnh mẽ cho các tập hợp và các API trong khi vẫn bảo toàn khả năng tương thích ngược thông qua cơ chế xóa bỏ kiểu. Việc hiểu rõ các quy tắc, giới hạn, ký tự đại diện và vị trí thích hợp của chúng (PECS) giúp ngăn ngừa các lỗi thường gặp như ép kiểu không được kiểm tra và lỗi `ClassCastException` khi chạy.
