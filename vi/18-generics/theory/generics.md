# Kiểu Chung (Generics) trong Java – Lý Thuyết Chi Tiết

## 1. Lớp Generic (Generic Class)
- **Định nghĩa**: Một lớp khai báo một hoặc nhiều tham số kiểu dữ liệu, cho phép nó hoạt động trên các đối tượng thuộc nhiều kiểu khác nhau trong khi vẫn cung cấp tính an toàn kiểu dữ liệu tại thời điểm biên dịch (compile-time type safety).
- **Quy tắc Java**: Danh sách tham số kiểu dữ liệu xuất hiện sau tên lớp, ví dụ `class Box<T> { private T value; … }`. Tham số kiểu dữ liệu có thể được sử dụng ở bất kỳ nơi nào cho phép kiểu dữ liệu thông thường bên trong thân lớp.
- **Trường hợp sử dụng hợp lệ**: Các lớp chứa (container) giữ bất kỳ kiểu dữ liệu nào – `Box<T>`, `Pair<K,V>`.
- **Trường hợp lỗi**: Sử dụng một kiểu thô (raw type - `Box` mà không có `<T>`) sẽ loại bỏ thông tin generic, dẫn đến các chuyển đổi không được kiểm tra (unchecked conversion) và có thể gây ra ngoại lệ ép kiểu sai `ClassCastException` tại thời điểm chạy.

## 2. Phương thức Generic (Generic Method)
- **Định nghĩa**: Một phương thức giới thiệu các tham số kiểu dữ liệu của riêng nó, độc lập với các tham số kiểu dữ liệu của lớp.
- **Quy tắc Java**: Các tham số kiểu dữ liệu được khai báo trước kiểu trả về, ví dụ `public static <T> T identity(T obj) { return obj; }`.
- **Trường hợp sử dụng hợp lệ**: Các phương thức tiện ích như `Collections.max(Collection<? extends T>)` hoặc `Arrays.asList(T... a)` hoạt động trên mọi kiểu dữ liệu.
- **Trường hợp lỗi**: Việc bỏ qua danh sách tham số kiểu dữ liệu khiến phương thức quay trở lại sử dụng các kiểu thô, làm vô hiệu hóa các bước kiểm tra tại thời điểm biên dịch.

## 3. Giao diện Generic (Generic Interface)
- **Định nghĩa**: Một giao diện khai báo các tham số kiểu dữ liệu, cho phép các lớp triển khai chỉ định kiểu dữ liệu cụ thể.
- **Quy tắc Java**: Tương tự như lớp – `interface Comparable<T> { int compareTo(T o); }`.
- **Trường hợp sử dụng hợp lệ**: Các hợp đồng hoạt động trên một kiểu dữ liệu cụ thể, ví dụ: `Comparator<T>`, `Iterable<T>`.
- **Trường hợp lỗi**: Triển khai dạng thô (`Comparable`) làm mất đi các đảm bảo của generic; trình biên dịch sẽ đưa ra các cảnh báo chưa được kiểm tra (unchecked warning).

## 4. Tham số kiểu dữ liệu (Type Parameter)
- **Định nghĩa**: Một tên giữ chỗ (thường là một chữ cái in hoa duy nhất) đại diện cho một kiểu dữ liệu chưa xác định.
- **Các quy ước phổ biến**: `T` – kiểu dữ liệu (type), `E` – phần tử (element), `K` – khóa (key), `V` – giá trị (value), `N` – số (number), `S, U, V` – nhiều kiểu dữ liệu.
- **Phạm vi (Scope)**: Chỉ hiển thị bên trong khai báo generic (lớp, phương thức, giao diện).

## 5. Nhiều Tham Số Kiểu Dữ Liệu (Multiple Type Parameters)
- **Cú pháp**: Phân tách bằng dấu phẩy, ví dụ `class MapEntry<K, V> { private K key; private V value; }`.
- **Trường hợp sử dụng**: Các cấu trúc dữ liệu cần nhiều hơn một kiểu dữ liệu, chẳng hạn như `Map<K,V>`, `BiFunction<T,U,R>`.

## 6. Tham Số Kiểu Bị Giới Hạn (Bounded Type Parameter)
- **Cú pháp**: `T extends Bound` trong đó `Bound` có thể là một lớp hoặc giao diện (hoặc kết hợp thông qua toán tử `&`).
- **Ví dụ**: `class NumericBox<T extends Number> { private T value; }`.
- **Quy tắc**: Ranh giới giới hạn tập hợp các kiểu dữ liệu được phép; bên trong lớp, bạn có thể gọi các phương thức được định nghĩa bởi ranh giới đó.
- **Trường hợp lỗi**: Cố gắng khởi tạo với một kiểu dữ liệu không liên quan (`new NumericBox<String>()`) sẽ dẫn đến lỗi biên dịch.

## 7. Ký tự đại diện (`?`) (Wildcards)
### 7.1 Ký tự đại diện không giới hạn – `?` (Unbounded Wildcard)
- **Ý nghĩa**: Kiểu dữ liệu chưa xác định. Hữu ích khi bạn chỉ cần đọc từ một bộ sưu tập (collection).
- **Ví dụ**: `void printAll(List<?> list) { for (Object o : list) System.out.println(o); }`.

### 7.2 Ký tự đại diện giới hạn trên – `<? extends T>` (Upper-Bounded Wildcard)
- **Ý nghĩa**: Một phân lớp (subtype) chưa xác định kế thừa từ `T`.
- **PECS (Producer Extends)**: Sử dụng khi đối tượng generic **sản xuất** (produce) các giá trị thuộc kiểu `T`.
- **Ví dụ**: `List<? extends Number> numbers = List.of(1, 2.5); // chỉ đọc (read‑only)`
- **Trường hợp lỗi**: Bạn không thể thêm các phần tử (ngoại trừ `null`) vì phân lớp chính xác chưa được xác định.

### 7.3 Ký tự đại diện giới hạn dưới – `<? super T>` (Lower-Bounded Wildcard)
- **Ý nghĩa**: Một siêu lớp (supertype) chưa xác định của `T`.
- **PECS (Consumer Super)**: Sử dụng khi đối tượng generic **tiêu thụ** (consume) các giá trị thuộc kiểu `T`.
- **Ví dụ**: `List<? super Integer> ints = new ArrayList<Number>(); ints.add(10);`
- **Trường hợp lỗi**: Khi đọc, bạn chỉ nhận được kiểu `Object` vì siêu lớp chính xác chưa được xác định.

## 8. PECS – Producer Extends, Consumer Super
- **Hướng dẫn**: 
  - Nếu một generic **sản xuất** (produce) các giá trị &rarr; sử dụng `extends`.
  - Nếu nó **tiêu thụ** (consume) các giá trị &rarr; sử dụng `super`.
- **Các API điển hình**:
  - `Collections.copy(List<? super T> dest, List<? extends T> src)`
  - `Stream<T> map(Function<? super T, ? extends R>)`

## 9. Generic với Collection
| Bộ sưu tập | Khai báo điển hình | Lý do |
|------------|--------------------|--------|
| `List` | `List<E>` – `E` là kiểu phần tử. | Cho phép thêm/lấy ra một cách an toàn kiểu dữ liệu. |
| `Set` | `Set<E>` – không có phần tử trùng lặp thuộc kiểu `E`. | |
| `Map` | `Map<K,V>` – `K` khóa, `V` giá trị. | Cho phép kiểm tra kiểu dữ liệu của cả khóa và giá trị tại thời điểm biên dịch. |
| `Queue` | `Queue<E>` – ngữ nghĩa vào trước ra trước (FIFO). | |
| `Deque` | `Deque<E>` – hàng đợi hai đầu (double-ended queue). | |
| `Optional` | `Optional<T>` – lớp chứa cho giá trị có thể bị khuyết (vắng mặt). | |

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

## 10. Xóa kiểu dữ liệu (Type Erasure)
- **Điều gì xảy ra**: Tại thời điểm biên dịch, thông tin kiểu generic bị loại bỏ. Mã byte (bytecode) chỉ chứa **kiểu thô (raw type)** và các phép ép kiểu sẽ được chèn vào những nơi cần thiết.
- **Hệ quả**:
  - Không có kiểm tra kiểu generic tại thời điểm chạy.
  - Bạn không thể nạp chồng (overload) các phương thức chỉ khác nhau bởi tham số kiểu generic.
  - `instanceof` không thể được sử dụng với một kiểu generic (ví dụ `if (obj instanceof List<String>)` là không hợp lệ).

## 11. Kiểu Thô (Raw Types)
- **Định nghĩa**: Việc sử dụng một lớp hoặc giao diện generic mà không chỉ định các đối số kiểu dữ liệu, ví dụ `List raw = new ArrayList();`.
- **Tác động**: Làm vô hiệu hóa tính an toàn của generic, kích hoạt các cảnh báo chưa được kiểm tra (unchecked warning) và có thể gây ra lỗi ép kiểu `ClassCastException` tại thời điểm chạy.
- **Khi nào cần tránh**: Hầu như luôn luôn; chỉ sử dụng khi tương tác với mã nguồn cũ (legacy code) từ trước thời kỳ generic (trước Java 5).

## 12. Các Hạn Chế Của Generic
| Hạn chế | Giải thích | Biện pháp thay thế (Work-around) |
|------------|-------------|------------|
| **Không có mảng generic** | `new T[10]` không hợp lệ vì cơ chế xóa kiểu. | Sử dụng `List<T>` hoặc `Array.newInstance(clazz, size)` với một thẻ `Class<T>`. |
| **Không có kiểu dữ liệu nguyên thủy generic** | Các tham số kiểu phải là các kiểu tham chiếu (reference type). | Sử dụng các lớp bao bọc (wrapper class) (`Integer`, `Double`). |
| **Không có trường tĩnh thuộc kiểu tham số** | Các thành viên tĩnh thuộc về lớp chứ không thuộc về một đối số kiểu cụ thể nào. | Sử dụng các trường phi tĩnh hoặc thu nhận kiểu bằng một đối số `Class<T>`. |
| **Không thể tạo các lớp con generic từ các lớp phi generic với các đối số kiểu cụ thể** | Ví dụ: `class MyStringList extends ArrayList<String>` được phép, nhưng sau đó bạn không thể coi nó là `ArrayList<T>`. | |
| **Giới hạn suy luận kiểu** | Các generic lồng nhau phức tạp có thể yêu cầu chỉ định đối số kiểu rõ ràng. | Cung cấp các tham số kiểu rõ ràng hoặc sử dụng các phương thức bổ trợ. |

## 13. Ví Dụ Tổng Hợp
```java
public class Pair<K, V> {
    private final K key;
    private final V value;
    public Pair(K key, V value) { this.key = key; this.value = value; }
    public K getKey() { return key; }
    public V getValue() { return value; }
}

// Sử dụng các tham số kiểu bị giới hạn và quy tắc PECS
public static <T extends Number> double sum(List<? extends T> numbers) {
    double total = 0;
    for (T n : numbers) total += n.doubleValue(); // safe: T is a Number
    return total;
}

public static void addIntegers(List<? super Integer> list) {
    list.add(1); // safe: list can accept Integer or any of its supertypes
}
```
**Các trường hợp lỗi:**
- Truyền một `List<Object>` vào phương thức `sum` – lỗi biên dịch vì `Object` không kế thừa từ `Number`.
- Cố gắng thêm phần tử vào một `List<? extends Number>` – lỗi biên dịch: không thể thêm bất kỳ phần tử nào ngoại trừ `null`.

---
**Tóm lại**: Generic mang lại tính định kiểu tĩnh (static typing) mạnh mẽ cho các bộ sưu tập và các API trong Java, đồng thời bảo toàn tính tương thích ngược thông qua cơ chế xóa kiểu dữ liệu. Việc hiểu rõ các quy tắc, ranh giới, ký tự đại diện và vị trí thích hợp của chúng (PECS) giúp ngăn ngừa các lỗi phổ biến như ép kiểu không được kiểm tra và lỗi ép kiểu `ClassCastException` khi chạy chương trình.
