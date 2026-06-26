# Generics – Phần 1: Lớp, Phương thức, Giao diện Generic, Tham số kiểu, Giới hạn (Generics – Part 1: Generic Class, Method, Interface, Type Parameters, Bounds)

## 1. Lớp Generic (Generic Class)

**Định nghĩa:** Một lớp khai báo một hoặc nhiều tham số kiểu (type parameters) được đặt trong dấu `<>` ngay sau tên lớp. Tham số này hoạt động như một trình giữ chỗ (placeholder) cho một kiểu dữ liệu cụ thể được cung cấp khi khởi tạo đối tượng.

**Quy tắc Java:**
```java
class Box<T> {
    private T value;
    public Box(T value) { this.value = value; }
    public T get() { return value; }
}
```
- `T` có thể được sử dụng ở bất kỳ nơi nào cho phép sử dụng kiểu dữ liệu thông thường bên trong thân lớp (trường dữ liệu, tham số phương thức, kiểu trả về).
- Trình biên dịch kiểm tra tính chính xác của kiểu dữ liệu tại thời điểm biên dịch; mã byte (bytecode) thu được sẽ sử dụng kiểu thô `Object` (hoặc giới hạn của nó) sau quá trình xóa bỏ kiểu (type erasure).

**Trường hợp sử dụng hợp lệ:**
- `Box<String>`, `Box<Integer>` — các container chứa kiểu dữ liệu đơn lẻ.
- `Pair<K, V>` — cấu trúc giữ dữ liệu chứa nhiều kiểu dữ liệu khác nhau.
- `Optional<T>` (trong thư viện JDK) — bao bọc các giá trị có thể null một cách an toàn.

**Trường hợp lỗi:**
```java
Box rawBox = new Box("hello");   // raw type – no compile-time check
rawBox = new Box(42);            // silently allowed; ClassCastException risk later
Integer n = (Integer) rawBox.get(); // runtime ClassCastException
```
Việc sử dụng kiểu thô (raw type) sẽ vô hiệu hóa toàn bộ cơ chế an toàn của generic. Hãy luôn cung cấp các đối số kiểu dữ liệu.

---

## 2. Phương thức Generic (Generic Method)

**Định nghĩa:** Một phương thức giới thiệu các tham số kiểu của riêng nó, độc lập với bất kỳ tham số kiểu nào ở cấp độ lớp.

**Quy tắc Java:**
- Các tham số kiểu được đặt **trước kiểu trả về**.
```java
public static <T> T identity(T obj) { return obj; }
public static <T extends Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) >= 0 ? a : b;
}
```
- Trình biên dịch tự động suy luận `T` từ đối số truyền vào tại vị trí gọi; bạn cũng có thể chỉ định nó một cách rõ ràng: `MyUtil.<String>identity("hi")`.

**Trường hợp sử dụng hợp lệ:**
- `Collections.max(Collection<? extends T>)` — hoạt động cho bất kỳ kiểu phần tử so sánh được nào.
- `Arrays.asList(T... a)` — chuyển đổi varargs thành một danh sách có kiểu dữ liệu xác định.
- Các phương thức tiện ích/trợ giúp cần khả năng tái sử dụng trên nhiều kiểu dữ liệu khác nhau.

**Trường hợp lỗi:**
```java
// Missing <T> — compiler falls back to raw types
public static Object broken(Object obj) { return obj; }  // no generic safety
```
Nếu thiếu khai báo tham số kiểu, trình biên dịch không thể thực thi tính nhất quán của kiểu giữa các tham số và kiểu trả về.

---

## 3. Giao diện Generic (Generic Interface)

**Định nghĩa:** Một giao diện khai báo các tham số kiểu, bắt buộc các triển khai của nó phải hoạt động với một kiểu dữ liệu cụ thể.

**Quy tắc Java:**
```java
interface Transformer<T, R> {
    R transform(T input);
}

class StringToInt implements Transformer<String, Integer> {
    public Integer transform(String s) { return s.length(); }
}
```
- Các triển khai phải cung cấp các kiểu cụ thể (ví dụ: `Transformer<String, Integer>`) hoặc tiếp tục giữ generic (ví dụ: `class Proxy<T, R> implements Transformer<T, R>`).

**Các ví dụ JDK quan trọng:**
- `Comparable<T>` — thứ tự tự nhiên của đối tượng.
- `Iterable<T>` — hỗ trợ vòng lặp for-each.
- `Comparator<T>` — cơ chế sắp xếp ngoài.
- `Function<T, R>` — hàm một tham số đầu vào.

**Trường hợp lỗi:**
```java
class Broken implements Comparable {   // raw Comparable – no type safety
    public int compareTo(Object o) { ... }
}
// compareTo now accepts any Object; the compiler cannot catch:
broken.compareTo(42);   // no compile error even for wrong type
```

---

## 4. Quy ước Đặt tên Tham số kiểu (Type Parameter Conventions)

**Định nghĩa:** Một tên giữ chỗ được khai báo trong dấu `<>` đại diện cho một kiểu dữ liệu chưa xác định bên trong một khai báo generic.

**Các quy ước chữ cái đơn tiêu chuẩn:**
| Ký tự | Ý nghĩa |
|--------|---------|
| `T` | Kiểu dữ liệu (nói chung - Type) |
| `E` | Phần tử (trong các Collection - Element) |
| `K` | Khóa (trong Map - Key) |
| `V` | Giá trị (trong Map - Value) |
| `N` | Số (Number) |
| `R` | Kiểu trả về (trong Hàm/Function - Return type) |
| `S`, `U` | Kiểu thứ hai, thứ ba (khi có nhiều tham số) |

**Phạm vi (Scope):** Tham số kiểu chỉ có giá trị sử dụng bên trong lớp/phương thức/giao diện generic nơi nó được khai báo.

**Lưu ý thực tế:** Các tên như `T1`, `T2` hoặc các tên mang tính mô tả (`Source`, `Destination`) được phép sử dụng nhưng quy ước chữ cái đơn chiếm ưu thế trong các API JDK và luôn được mong đợi trong các buổi duyệt mã (code reviews).

---

## 5. Nhiều Tham số kiểu (Multiple Type Parameters)

**Cú pháp:**
```java
class Pair<K, V> {
    private final K key;
    private final V value;
    public Pair(K key, V value) { this.key = key; this.value = value; }
    public K getKey()   { return key; }
    public V getValue() { return value; }
}
```

**Trường hợp sử dụng:**
- `Map<K, V>` — ánh xạ kiểu khóa tới kiểu giá trị.
- `BiFunction<T, U, R>` — hàm nhận vào hai kiểu dữ liệu đầu vào và trả về một kiểu dữ liệu đầu ra.
- `Either<L, R>` (phổ biến trong các thư viện lập trình hàm) — lưu giữ một trong hai giá trị thay thế.

**Quy tắc:** Tất cả các tham số kiểu phải là các định danh riêng biệt được phân tách bằng dấu phẩy. Thứ tự chỉ quan trọng trong cách lớp sử dụng chúng bên trong.

---

## 6. Tham số kiểu có giới hạn: <T extends Bound> (Bounded Type Parameter: <T extends Bound>)

**Định nghĩa:** Hạn chế tập hợp các đối số kiểu hợp lệ đối với kiểu `T` phải là một kiểu con của `Bound`.

**Cú pháp:**
```java
// Giới hạn trên – một lớp hoặc giao diện duy nhất
<T extends Number>

// Nhiều giới hạn – lớp phải đứng đầu tiên, sau đó đến các giao diện
<T extends Number & Comparable<T> & Serializable>
```

**Tại sao giới hạn trên lại quan trọng:**
Bên trong lớp/phương thức, bạn có thể **gọi các phương thức của giới hạn đó** trên `T`:
```java
public static <T extends Number> double sum(List<T> list) {
    double total = 0;
    for (T n : list) total += n.doubleValue(); // doubleValue() defined on Number
    return total;
}
```
Nếu không có giới hạn, `n.doubleValue()` sẽ báo lỗi biên dịch — khi đó `T` sẽ được coi là `Object`.

**Trường hợp lỗi:**
```java
sum(List.of("a", "b"));    // compile error: String does not extend Number
new NumericBox<String>();   // compile error
```

**Ký tự đại diện so với tham số kiểu có giới hạn:**
- `<T extends Number>` khai báo một biến kiểu có tên mới — sử dụng trong các phương thức khi bạn cần tham chiếu tới `T` nhiều lần.
- `<? extends Number>` là một ký tự đại diện vô danh — sử dụng trong các tham số phương thức khi bạn chỉ cần đọc dữ liệu.

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/java/generics/types.html
- https://docs.oracle.com/javase/tutorial/java/generics/methods.html
- https://docs.oracle.com/javase/tutorial/java/generics/bounded.html
- https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html
