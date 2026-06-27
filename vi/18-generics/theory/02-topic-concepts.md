# Generics – Phần 2: Ký Tự Đại Diện (Wildcards), PECS, Generics với Cấu Trúc Tập Hợp (Collections), Xóa Bỏ Kiểu (Type Erasure)

## 1. Ký Tự Đại Diện (Wildcards)

Một **ký tự đại diện** (wildcard) (`?`) đại diện cho một kiểu dữ liệu chưa xác định ở vị trí đối số kiểu generic. Không giống như tham số kiểu (type parameter) có tên (`T`), ký tự đại diện không thể được tham chiếu bằng tên — nó là vô danh.

### 1.1 Ký Tự Đại Diện Không Giới Hạn (Unbounded Wildcard) `<?>`

**Ý nghĩa:** Bất kỳ kiểu dữ liệu nào. Danh sách có thể chứa `String`, `Integer`, hoặc bất kỳ thứ gì.

**Khi nào sử dụng:** Khi bạn chỉ cần **đọc** các phần tử dưới dạng `Object`, hoặc khi mã nguồn thực sự không phụ thuộc vào kiểu dữ liệu.

```java
void printAll(List<?> list) {
    for (Object o : list) System.out.println(o);
}
```

**Đọc:** ✅ Trả về `Object`.  
**Ghi:** ❌ Không thể thêm bất kỳ phần tử nào (ngoại trừ `null`) vì kiểu thực tế chưa được xác định.

```java
List<?> list = new ArrayList<String>();
list.add("hello");   // COMPILE ERROR – type unknown, unsafe
list.add(null);      // OK – null is always safe
```

---

### 1.2 Ký Tự Đại Diện Giới Hạn Trên (Upper-Bounded Wildcard) `<? extends T>`

**Ý nghĩa:** Một kiểu con (subtype) chưa xác định của `T`. Danh sách này đóng vai trò là **nhà sản xuất** (producer) các giá trị `T`.

**Đọc:** ✅ Trả về `T` (hoặc một kiểu con).  
**Ghi:** ❌ Không thể thêm (ngoại trừ `null`) — kiểu con chính xác chưa được xác định.

```java
void sumNumbers(List<? extends Number> numbers) {
    double sum = 0;
    for (Number n : numbers) sum += n.doubleValue(); // safe: n is-a Number
}

// Call with any subtype:
sumNumbers(List.of(1, 2, 3));          // List<Integer>
sumNumbers(List.of(1.1, 2.2));         // List<Double>
```

**Trường hợp thất bại:**
```java
List<? extends Number> nums = new ArrayList<Integer>();
nums.add(42);    // COMPILE ERROR – could be Double, Long, etc.; unsafe
```

---

### 1.3 Ký Tự Đại Diện Giới Hạn Dưới (Lower-Bounded Wildcard) `<? super T>`

**Ý nghĩa:** Một kiểu cha (supertype) chưa xác định của `T`. Danh sách này đóng vai trò là **bên tiêu thụ** (consumer) các giá trị `T`.

**Ghi:** ✅ Có thể thêm `T` hoặc bất kỳ kiểu con nào của `T` một cách an toàn.  
**Đọc:** ⚠️ Chỉ trả về `Object` — kiểu thực tế phía trên `T` chưa được xác định.

```java
void addNumbers(List<? super Integer> list) {
    list.add(1);   // safe: Integer fits in Integer, Number, or Object
    list.add(2);
}

// Call with supertypes:
addNumbers(new ArrayList<Integer>());  // OK
addNumbers(new ArrayList<Number>());   // OK
addNumbers(new ArrayList<Object>());   // OK
```

**Trường hợp thất bại:**
```java
List<? super Integer> list = new ArrayList<Number>();
Integer n = list.get(0);   // COMPILE ERROR – actual element could be any Number
Object o  = list.get(0);   // OK – Object is always a safe assignment
```

---

## 2. PECS — Producer Extends, Consumer Super (Nhà Sản Xuất Dùng Extends, Bên Tiêu Thụ Dùng Super)

**Mẹo ghi nhớ (Mnemonic):** _PECS — Producer Extends, Consumer Super_

| Vai trò | Ký tự đại diện | Có thể đọc giá trị có kiểu? | Có thể thêm giá trị có kiểu? |
|------|----------|-----------------------|----------------------|
| Nhà sản xuất (Producer) | `<? extends T>` | ✅ Có (`T`) | ❌ Không |
| Bên tiêu thụ (Consumer) | `<? super T>` | ❌ Chỉ `Object` | ✅ Có (`T`) |

**Quy tắc quyết định:**
- Nếu một tham số **sản xuất** (bạn đọc các giá trị `T` từ nó) &rarr; `<? extends T>`.
- Nếu một tham số **tiêu thụ** (bạn ghi các giá trị `T` vào nó) &rarr; `<? super T>`.
- Nếu cần cả đọc và ghi &rarr; sử dụng kiểu chính xác `T` (không dùng ký tự đại diện).

**Ví dụ thực tế trong JDK:**
```java
// src produces T → extends; dest consumes T → super
public static <T> void copy(List<? super T> dest, List<? extends T> src) {
    for (T item : src) dest.add(item);
}
```

**Ví dụ về Stream:**
```java
// map: Function<? super T, ? extends R>
// T is consumed (in) → super; R is produced (out) → extends
Stream<String> names = Stream.of("alice", "bob");
Stream<Integer> lengths = names.map(s -> s.length());
```

**Lỗi thường gặp:** Sử dụng `<? extends T>` khi bạn cần thêm phần tử:
```java
List<? extends Number> nums = new ArrayList<>();
nums.add(1);   // COMPILE ERROR — cannot add to upper-bounded wildcard
```

---

## 3. Generics với Collections

Mọi giao diện (interface) trong Cấu trúc tập hợp (Collections Framework) của Java đều sử dụng generic. Việc hiểu rõ tham số kiểu sẽ giúp bạn làm chủ toàn bộ hợp đồng (contract) của chúng.

| Giao diện (Interface) | Khai báo | Ràng buộc chính |
|-----------|-------------|----------------|
| `List<E>` | `interface List<E>` | Có thứ tự, dựa trên chỉ số (index), cho phép trùng lặp |
| `Set<E>` | `interface Set<E>` | Không trùng lặp (thông qua `equals`/`hashCode`) |
| `Map<K,V>` | `interface Map<K,V>` | Khóa là duy nhất; mỗi khóa tương ứng với một giá trị |
| `Queue<E>` | `interface Queue<E>` | Vào trước ra trước (FIFO); `peek`/`poll` từ đầu hàng đợi |
| `Deque<E>` | `interface Deque<E>` | Hàng đợi hai đầu (Double-ended); có thể làm ngăn xếp (stack) hoặc hàng đợi (queue) |
| `Optional<T>` | `class Optional<T>` | Chứa 0 hoặc 1 giá trị; giúp tránh lỗi null |

**Sử dụng ký tự đại diện với collections:**
```java
// Read from any List of Numbers → ? extends
double totalScore(List<? extends Number> scores) { ... }

// Write Integers to any list that can hold them → ? super
void addDefaults(List<? super Integer> list) { list.add(0); }

// Process any list of any type → ?
void logAll(List<?> items) { items.forEach(System.out::println); }
```

**Toán tử kim cương (Diamond operator) (`<>`):** Từ Java 7, đối số kiểu ở vế phải có thể được tự động suy luận:
```java
List<String> names   = new ArrayList<>();   // compiler infers ArrayList<String>
Map<String, Integer> freq = new HashMap<>();
```

**Lớp tiện ích Collections và generics:**
```java
Collections.sort(List<T> list)          // T must implement Comparable<? super T>
Collections.max(Collection<? extends T>)
Collections.unmodifiableList(List<? extends T>)
```

---

## 4. Xóa Bỏ Kiểu (Type Erasure)

**Khái niệm:** Trình biên dịch Java loại bỏ tất cả thông tin kiểu generic sau khi kiểm tra kiểu. Bytecode chỉ chứa các **kiểu thô (raw types)** và **các phép ép kiểu được chèn vào**.

**Các bước trình biên dịch thực hiện:**
1. Thay thế tất cả các tham số kiểu bằng giới hạn trên của chúng (hoặc `Object` nếu không có giới hạn).
2. Chèn các phép ép kiểu tường minh ở bất kỳ nơi nào lấy ra một giá trị có kiểu.
3. Tạo các phương thức cầu nối (bridge methods) khi cần thiết để bảo toàn tính đa hình.

**Kết quả trong bytecode:**
```java
// Source
List<String> names = new ArrayList<>();
names.add("Alice");
String first = names.get(0);

// Bytecode equivalent (after erasure)
List names = new ArrayList();
names.add("Alice");
String first = (String) names.get(0);   // cast inserted by compiler
```

**Hệ quả của xóa bỏ kiểu:**

| Những gì bạn không thể làm | Lý do |
|--------------------|-----|
| `if (obj instanceof List<String>)` | Kiểu generic không được biết đến tại thời điểm chạy (runtime) |
| `new T[10]` | Không thể tạo mảng generic |
| `new T()` | Không thể khởi tạo thực thể của tham số kiểu |
| Nạp chồng các phương thức chỉ khác nhau ở kiểu generic | Sau khi xóa bỏ kiểu, chúng có chữ ký phương thức hoàn toàn giống nhau |
| Bắt ngoại lệ generic: `catch (SomeException<T> e)` | Không hợp lệ — thông tin generic đã bị xóa bỏ |

**Giải pháp thay thế (Work-arounds):**
- Truyền `Class<T> clazz` như một token để tạo các thực thể thông qua `clazz.getDeclaredConstructor().newInstance()`.
- Sử dụng `Array.newInstance(clazz, size)` đối với mảng.

## Tại Sao Java Sử Dụng Xóa Bỏ Kiểu

Generics trong Java được giới thiệu từ Java 5 nhằm cung cấp tính năng an toàn kiểu dữ liệu tại thời điểm biên dịch. Vào thời điểm đó, hàng tỷ dòng mã bytecode Java cũ (legacy) đã và đang chạy trên các hệ thống sản xuất. Nếu JVM được thiết kế lại để thực thi generics tại thời điểm chạy (tương tự như reified generics của C#), các thư viện cũ trước thời kỳ generics sẽ không tương thích với các môi trường thực thi mới hơn, đòi hỏi phải biên dịch lại trên quy mô lớn. Để bảo toàn tính tương thích ngược nghiêm ngặt, Java đã chọn giải pháp xóa bỏ kiểu (type erasure), một thiết kế nơi các tham số kiểu chỉ tồn tại ở thời điểm biên dịch để kiểm tra an toàn, và bị trình biên dịch loại bỏ trước khi tạo ra các tệp class tiêu chuẩn. Do đó, bytecode sau khi biên dịch sẽ sử dụng các kiểu thô và các phép ép kiểu ngầm định, cho phép bytecode cũ và mã generic mới chạy song song trên cùng một máy ảo mà không cần sửa đổi.

### Mô Hình Tư Duy (Mental Model)

```text
Thời điểm biên dịch (Kiểm tra an toàn):
[List<String>] ---> Cho phép thêm "hello" ---> Từ chối thêm 123 (Lỗi biên dịch)

       |
       | Biên dịch (Xóa bỏ kiểu & Chèn phép ép kiểu)
       v

Thời điểm chạy (Thực thi JVM):
[List] (ArrayList thô chứa các Object) ---> [Object: "hello"]
                                             |
                                             v (Phép ép kiểu ngầm định được chèn bởi trình biên dịch)
                                        (String) value
```

### Ví Dụ Mã Nguồn (Code Example)

```java
import java.util.ArrayList;
import java.util.List;

public class TypeErasureExplanation {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Java Generics");
        
        // At compile-time, get(0) is checked to be String.
        // At runtime, the bytecode performs a cast: (String) list.get(0)
        String value = list.get(0); 
        System.out.println(value); // Output: Java Generics
        
        // Demonstrating that runtime class ignores generic type
        System.out.println(list.getClass() == ArrayList.class); // Output: true
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

Mục tiêu: Chạy bytecode cũ song song với mã generic &rarr; Áp dụng Xóa bỏ kiểu &rarr; Các tham số kiểu generic bị xóa thành các giới hạn/Object trong quá trình biên dịch &rarr; Bytecode của JVM chỉ chứa các kiểu thô và các phép ép kiểu được chèn vào &rarr; Các JVM cũ hơn có thể thực thi bytecode mà không cần biết về generics.

## Tại Sao Generics Có Tính Bất Biến và PECS Giải Quyết Nó Như Thế Nào

Trong Java, mảng có tính đồng biến (covariant), nghĩa là `Integer[]` là một kiểu con của `Number[]`. Tuy nhiên, các kiểu generic lại có tính bất biến (invariant); ví dụ, `List<Integer>` không phải là kiểu con của `List<Number>`, mặc dù `Integer` kế thừa từ `Number`. Nếu generics có tính đồng biến, bạn có thể gán một `List<Integer>` cho một tham chiếu `List<Number>`, và sau đó gọi `list.add(1.5)` (một số kiểu double) trên tham chiếu đó, làm hỏng danh sách kiểu số nguyên tại thời điểm chạy với các phần tử không hợp lệ. Để khôi phục tính linh hoạt trong khi vẫn duy trì an toàn kiểu dữ liệu, Java cung cấp các ký tự đại diện theo quy tắc PECS: Producer Extends, Consumer Super. Sự đồng biến với `? extends T` đảm bảo rằng chúng ta có thể đọc các phần tử từ một nhà sản xuất một cách an toàn vì chúng được đảm bảo ít nhất là thuộc kiểu `T`, trong khi tính nghịch biến (contravariance) với `? super T` đảm bảo chúng ta có thể ghi các phần tử `T` vào một bên tiêu thụ một cách an toàn vì cấu trúc được đảm bảo chứa `T` hoặc các kiểu cha của nó.

### Mô Hình Tư Duy (Mental Model)

```text
Tính bất biến (Khớp kiểu nghiêm ngặt):
List<Number>  <--- Không có mối quan hệ --->  List<Integer>

Giải pháp PECS cho tính linh hoạt:
                       +-------------------------+
                       |   List<? extends Number> |  <--- Chỉ Đọc (Đồng biến)
                       +-------------------------+
                                    ^
                                    | (Cho phép trỏ đến)
                        List<Integer> hoặc List<Double>

                       +-------------------------+
                       |   List<? super Integer>  |  <--- Chỉ Ghi (Nghịch biến)
                       +-------------------------+
                                    ^
                                    | (Cho phép trỏ đến)
                          List<Number> hoặc List<Object>
```

### Ví Dụ Mã Nguồn (Code Example)

```java
import java.util.ArrayList;
import java.util.List;

public class PecsExplanation {
    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>();
        ints.add(10);
        ints.add(20);

        // 1. Invariance prevention: List<Number> nums = ints; // Compile Error

        // 2. Producer Extends (Read from list):
        List<? extends Number> producer = ints;
        Number num = producer.get(0); // Safe read: guaranteed to be Number
        System.out.println(num); // Output: 10
        // producer.add(5.5); // Compile Error: Write forbidden

        // 3. Consumer Super (Write to list):
        List<Number> numList = new ArrayList<>();
        List<? super Integer> consumer = numList;
        consumer.add(42); // Safe write: Integer is a subtype of Number/Object
        System.out.println(numList.get(0)); // Output: 42
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

Tính đồng biến cho phép gán các kiểu con &rarr; Việc ghi các kiểu cha tùy ý vào tham chiếu làm hỏng tập hợp &rarr; Generics được thiết kế có tính bất biến &rarr; Hạn chế các API quá nhiều &rarr; PECS được giới thiệu &rarr; Sử dụng extends để đọc an toàn (đồng biến) và super để ghi an toàn (nghịch biến).

## Liên Kết Tham Khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/java/generics/wildcards.html
- https://docs.oracle.com/javase/tutorial/java/generics/upperBounded.html
- https://docs.oracle.com/javase/tutorial/java/generics/lowerBounded.html
- https://docs.oracle.com/javase/tutorial/java/generics/erasure.html
- https://docs.oracle.com/javase/tutorial/java/generics/wildcardGuidelines.html
- https://docs.oracle.com/javase/tutorial/java/generics/subtyping.html
