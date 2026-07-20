# Kiểu chung (Generics) – Phần 1: Lớp tổng quát (Generic Class), Phương thức (Method), Giao diện (Interface), Tham số kiểu (Type Parameters), Giới hạn (Bounds)

## 1. Lớp tổng quát (Generic Class)

**Định nghĩa:** Một lớp khai báo một hoặc nhiều tham số kiểu (type parameters) được đặt trong dấu `<>` phía sau tên lớp. Tham số này đóng vai trò như một trình giữ chỗ cho một kiểu dữ liệu cụ thể (concrete type) được cung cấp khi khởi tạo (instantiation).

**Quy tắc Java:**
```java
class Box<T> {
    private T value;
    public Box(T value) { this.value = value; }
    public T get() { return value; }
}
```
- `T` có thể được sử dụng ở bất kỳ nơi nào cho phép một kiểu dữ liệu thông thường bên trong thân lớp (trường dữ liệu (fields), tham số phương thức (method parameters), kiểu trả về (return types)).
- Trình biên dịch (compiler) kiểm tra tính đúng đắn của kiểu dữ liệu tại thời điểm biên dịch (compile time); mã byte (bytecode) sẽ sử dụng kiểu nguyên bản (raw type) `Object` (hoặc giới hạn) sau khi xóa kiểu (type erasure).

**Các trường hợp sử dụng hợp lệ:**
- `Box<String>`, `Box<Integer>` — các bộ chứa đơn kiểu (single-typed containers).
- `Pair<K, V>` — các đối tượng lưu trữ dữ liệu (data holders) với nhiều kiểu dữ liệu.
- `Optional<T>` (JDK) — bao bọc các giá trị có thể null (nullable values) một cách an toàn.

**Trường hợp lỗi (Failure mode):**
```java
Box rawBox = new Box("hello");   // raw type – no compile-time check
rawBox = new Box(42);            // silently allowed; ClassCastException risk later
Integer n = (Integer) rawBox.get(); // runtime ClassCastException
```
Việc sử dụng kiểu nguyên bản sẽ vô hiệu hóa tất cả các tính năng an toàn kiểu (type safety) của kiểu chung. Luôn luôn cung cấp các đối số kiểu (type arguments).

---

## 2. Phương thức tổng quát (Generic Method)

**Định nghĩa:** Một phương thức tự khai báo các tham số kiểu của riêng nó, độc lập với bất kỳ tham số kiểu nào ở cấp độ lớp.

**Quy tắc Java:**
- Các tham số kiểu được đặt **trước kiểu trả về**.
```java
public static <T> T identity(T obj) { return obj; }
public static <T extends Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) >= 0 ? a : b;
}
```
- Trình biên dịch sẽ suy luận (infer) tham số kiểu từ đối số tại nơi gọi (call-site argument); bạn cũng có thể chỉ định nó một cách tường minh: `MyUtil.<String>identity("hi")`.

**Các trường hợp sử dụng hợp lệ:**
- `Collections.max(Collection<? extends T>)` — hoạt động với bất kỳ kiểu phần tử có thể so sánh được (comparable element type) nào.
- `Arrays.asList(T... a)` — chuyển đổi các tham số biến đổi (varargs) thành một danh sách có kiểu dữ liệu xác định (typed list).
- Các phương thức tiện ích/bổ trợ (utility/helper methods) có khả năng tái sử dụng trên nhiều kiểu dữ liệu khác nhau.

> Xem thêm: Sự kết hợp chặt chẽ giữa Generics và Java Collections Framework, được trình bày chi tiết trong [Ch.19 - Collections Framework](../../19-collections-framework/README.md).

**Trường hợp lỗi (Failure mode):**
```java
// Missing <T> — compiler falls back to raw types
public static Object broken(Object obj) { return obj; }  // no generic safety
```
Nếu không có khai báo tham số kiểu, trình biên dịch không thể bắt buộc tính nhất quán về kiểu (type consistency) giữa các tham số và kiểu trả về.

---

## 3. Giao diện tổng quát (Generic Interface)

**Định nghĩa:** Một giao diện khai báo các tham số kiểu, bắt buộc các lớp triển khai (implementations) phải làm việc với một kiểu dữ liệu cụ thể.

**Quy tắc Java:**
```java
interface Transformer<T, R> {
    R transform(T input);
}

class StringToInt implements Transformer<String, Integer> {
    public Integer transform(String s) { return s.length(); }
}
```
- Các lớp triển khai phải cung cấp kiểu dữ liệu cụ thể (`Transformer<String, Integer>`) hoặc tiếp tục giữ tính chất tổng quát (`class Proxy<T, R> implements Transformer<T, R>`).

**Các ví dụ tiêu biểu trong JDK:**
- `Comparable<T>` — thứ tự toàn phần (total ordering).
- `Iterable<T>` — hỗ trợ vòng lặp for-each.
- `Comparator<T>` — thứ tự ngoài (external ordering).
- `Function<T, R>` — hàm một đối số (single-argument function).

**Trường hợp lỗi (Failure mode):**
```java
class Broken implements Comparable {   // raw Comparable – no type safety
    public int compareTo(Object o) { ... }
}
// compareTo now accepts any Object; the compiler cannot catch:
broken.compareTo(42);   // no compile error even for wrong type
```

---

## 4. Quy ước đặt tên tham số kiểu (Type Parameter Conventions)

**Định nghĩa:** Một tên trình giữ chỗ được khai báo trong `<>` đại diện cho một kiểu dữ liệu chưa xác định trong một khai báo tổng quát.

**Các quy ước ký tự đơn tiêu chuẩn:**
| Ký tự | Ý nghĩa |
|--------|---------|
| `T` | Kiểu dữ liệu (chung) |
| `E` | Phần tử (bộ sưu tập) |
| `K` | Khóa (bản đồ) |
| `V` | Giá trị (bản đồ) |
| `N` | Số |
| `R` | Kiểu trả về (hàm) |
| `S`, `U` | Kiểu thứ hai, thứ ba (nhiều tham số) |

**Phạm vi (Scope):** Tham số kiểu chỉ có hiệu lực bên trong lớp/phương thức/giao diện tổng quát nơi nó được khai báo.

**Lưu ý thực tế:** Các tên như `T1`, `T2` hoặc những tên mang tính mô tả (`Source`, `Destination`) đều được chấp nhận, nhưng quy ước sử dụng một ký tự đơn vẫn chiếm ưu thế trong các API của JDK và là tiêu chuẩn được mong đợi khi duyệt mã nguồn (code reviews).

---

## 5. Nhiều tham số kiểu (Multiple Type Parameters)

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

**Các trường hợp sử dụng:**
- `Map<K, V>` — ánh xạ kiểu khóa sang kiểu giá trị.
- `BiFunction<T, U, R>` — hàm nhận vào hai kiểu dữ liệu đầu vào và trả về một kiểu dữ liệu đầu ra.
- `Either<L, R>` (phổ biến trong các thư viện lập trình chức năng) — lưu giữ một trong hai lựa chọn thay thế.

**Quy tắc:** Tất cả các tham số kiểu phải là các định danh phân biệt được phân tách bằng dấu phẩy. Thứ tự của chúng chỉ quan trọng trong cách lớp sử dụng chúng ở bên trong.

---

## 6. Tham số kiểu có giới hạn (Bounded Type Parameter): `<T extends Bound>`

**Định nghĩa:** Giới hạn tập hợp các đối số kiểu hợp lệ thành một kiểu `T` phải là kiểu con (subtype) của `Bound`.

**Cú pháp:**
```java
// Upper bound – single class or interface
<T extends Number>

// Multiple bounds – class must come first, then interfaces
<T extends Number & Comparable<T> & Serializable>
```

**Tại sao giới hạn trên (upper bounds) lại quan trọng:**
Bên trong lớp hoặc phương thức, bạn có thể **gọi các phương thức của giới hạn** trên `T`:
```java
public static <T extends Number> double sum(List<T> list) {
    double total = 0;
    for (T n : list) total += n.doubleValue(); // doubleValue() defined on Number
    return total;
}
```
Nếu không có giới hạn, `n.doubleValue()` sẽ gây ra lỗi biên dịch — khi đó `T` sẽ bị coi là `Object`.

**Trường hợp lỗi (Failure mode):**
```java
sum(List.of("a", "b"));    // compile error: String does not extend Number
new NumericBox<String>();   // compile error
```

**Ký tự đại diện (Wildcard) so với tham số có giới hạn:**
- `<T extends Number>` khai báo một biến kiểu có tên mới — sử dụng trong các phương thức khi bạn cần tham chiếu đến `T` nhiều lần.
- `<? extends Number>` là một ký tự đại diện vô danh (anonymous wildcard) — sử dụng trong các tham số phương thức khi bạn chỉ cần đọc dữ liệu.

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/java/generics/types.html
- https://docs.oracle.com/javase/tutorial/java/generics/methods.html
- https://docs.oracle.com/javase/tutorial/java/generics/bounded.html
- https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html