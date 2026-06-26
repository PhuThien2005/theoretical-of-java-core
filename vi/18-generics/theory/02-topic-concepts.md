# Generics – Phần 2: Ký tự đại diện, PECS, Generics với Collection, Xóa bỏ kiểu (Generics – Part 2: Wildcards, PECS, Generics with Collections, Type Erasure)

## 1. Ký tự đại diện (Wildcards)

Một **ký tự đại diện (wildcard)** (`?`) đại diện cho một kiểu dữ liệu chưa xác định ở vị trí của đối số kiểu generic. Không giống như một tham số kiểu có tên (`T`), ký tự đại diện không thể được tham chiếu bằng tên — nó hoàn toàn vô danh.

### 1.1 Ký tự đại diện không giới hạn <?> (Unbounded Wildcard <?>)

**Ý nghĩa:** Bất kỳ kiểu dữ liệu nào. Danh sách có thể chứa `String`, `Integer`, hay bất kỳ đối tượng nào khác.

**Khi nào sử dụng:** Khi bạn chỉ cần **đọc** các phần tử dưới dạng kiểu `Object`, hoặc khi mã nguồn hoàn toàn độc lập với kiểu dữ liệu.

```java
void printAll(List<?> list) {
    for (Object o : list) System.out.println(o);
}
```

**Đọc (Read):** ✅ Trả về kiểu `Object`.  
**Ghi (Write):** ❌ Không thể thêm bất kỳ phần tử nào (ngoại trừ `null`) vì kiểu thực tế chưa được xác định.

```java
List<?> list = new ArrayList<String>();
list.add("hello");   // COMPILE ERROR – type unknown, unsafe
list.add(null);      // OK – null is always safe
```

---

### 1.2 Ký tự đại diện giới hạn trên <? extends T> (Upper-Bounded Wildcard <? extends T>)

**Ý nghĩa:** Một kiểu con (subtype) chưa xác định của `T`. Danh sách này đóng vai trò là **nhà sản xuất (producer)** các giá trị thuộc kiểu `T`.

**Đọc (Read):** ✅ Trả về kiểu `T` (hoặc một kiểu con của `T`).  
**Ghi (Write):** ❌ Không thể thêm phần tử (ngoại trừ `null`) — vì kiểu con cụ thể chưa được xác định.

```java
void sumNumbers(List<? extends Number> numbers) {
    double sum = 0;
    for (Number n : numbers) sum += n.doubleValue(); // safe: n is-a Number
}

// Call with any subtype:
sumNumbers(List.of(1, 2, 3));          // List<Integer>
sumNumbers(List.of(1.1, 2.2));         // List<Double>
```

**Trường hợp lỗi:**
```java
List<? extends Number> nums = new ArrayList<Integer>();
nums.add(42);    // COMPILE ERROR – could be Double, Long, etc.; unsafe
```

---

### 1.3 Ký tự đại diện giới hạn dưới <? super T> (Lower-Bounded Wildcard <? super T>)

**Ý nghĩa:** Một kiểu cha (supertype) chưa xác định của `T`. Danh sách này đóng vai trò là **người tiêu dùng (consumer)** các giá trị thuộc kiểu `T`.

**Ghi (Write):** ✅ Có thể thêm một cách an toàn kiểu `T` hoặc bất kỳ kiểu con nào của `T`.  
**Đọc (Read):** ⚠️ Chỉ trả về kiểu `Object` — vì kiểu thực tế bên trên `T` chưa được xác định.

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

**Trường hợp lỗi:**
```java
List<? super Integer> list = new ArrayList<Number>();
Integer n = list.get(0);   // COMPILE ERROR – actual element could be any Number
Object o  = list.get(0);   // OK – Object is always a safe assignment
```

---

## 2. PECS — Producer Extends, Consumer Super

**Quy tắc ghi nhớ:** *PECS — Producer Extends, Consumer Super* (Nhà sản xuất dùng Extends, Người tiêu dùng dùng Super)

| Vai trò | Ký tự đại diện | Có thể đọc giá trị kiểu xác định? | Có thể thêm giá trị kiểu xác định? |
|------|----------|-----------------------|----------------------|
| Nhà sản xuất (Producer) | `<? extends T>` | ✅ Có (`T`) | ❌ Không |
| Người tiêu dùng (Consumer) | `<? super T>` | ❌ Chỉ `Object` | ✅ Có (`T`) |

**Quy tắc quyết định:**
- Nếu một tham số đóng vai trò **sản xuất** (bạn đọc các giá trị `T` ra từ nó) → dùng `<? extends T>`.
- Nếu một tham số đóng vai trò **tiêu thụ** (bạn ghi các giá trị `T` vào trong nó) → dùng `<? super T>`.
- Nếu cần cả đọc và ghi → sử dụng kiểu chính xác `T` (không dùng ký tự đại diện).

**Ví dụ JDK thực tế:**
```java
// src produces T → extends; dest consumes T → super
public static <T> void copy(List<? super T> dest, List<? extends T> src) {
    for (T item : src) dest.add(item);
}
```

**Ví dụ Stream:**
```java
// map: Function<? super T, ? extends R>
// T is consumed (in) → super; R is produced (out) → extends
Stream<String> names = Stream.of("alice", "bob");
Stream<Integer> lengths = names.map(s -> s.length());
```

**Lỗi thường gặp:** Sử dụng `<? extends T>` khi bạn cần chèn thêm các phần tử:
```java
List<? extends Number> nums = new ArrayList<>();
nums.add(1);   // COMPILE ERROR — cannot add to upper-bounded wildcard
```

---

## 3. Generics với Collection (Generics with Collections)

Mỗi giao diện Collection trong Java đều là generic. Việc hiểu rõ tham số kiểu của chúng giúp bạn nắm bắt hoàn toàn giao kèo sử dụng của chúng.

| Giao diện | Khai báo | Ràng buộc chính |
|-----------|-------------|----------------|
| `List<E>` | `interface List<E>` | Có thứ tự, dựa trên chỉ mục, cho phép trùng lặp phần tử |
| `Set<E>` | `interface Set<E>` | Không cho phép trùng lặp (thông qua `equals`/`hashCode`) |
| `Map<K,V>` | `interface Map<K,V>` | Khóa là duy nhất; mỗi khóa đi kèm một giá trị |
| `Queue<E>` | `interface Queue<E>` | FIFO (Vào trước ra trước); `peek`/`poll` từ đầu hàng |
| `Deque<E>` | `interface Deque<E>` | Hai đầu; có thể dùng làm ngăn xếp (stack) hoặc hàng đợi (queue) |
| `Optional<T>` | `class Optional<T>` | Chứa 0 hoặc 1 giá trị; giúp tránh null |

**Sử dụng ký tự đại diện với Collection:**
```java
// Read from any List of Numbers → ? extends
double totalScore(List<? extends Number> scores) { ... }

// Write Integers to any list that can hold them → ? super
void addDefaults(List<? super Integer> list) { list.add(0); }

// Process any list of any type → ?
void logAll(List<?> items) { items.forEach(System.out::println); }
```

**Toán tử kim cương (`<>`):** Kể từ Java 7, đối số kiểu ở vế phải có thể được tự động suy luận:
```java
List<String> names   = new ArrayList<>();   // compiler infers ArrayList<String>
Map<String, Integer> freq = new HashMap<>();
```

**Tiện ích Collections và generics:**
```java
Collections.sort(List<T> list)          // T must implement Comparable<? super T>
Collections.max(Collection<? extends T>)
Collections.unmodifiableList(List<? extends T>)
```

---

## 4. Xóa bỏ kiểu (Type Erasure)

**Định nghĩa:** Trình biên dịch Java loại bỏ tất cả các thông tin kiểu generic sau khi kiểm tra kiểu. Mã byte (bytecode) thu được chỉ chứa các **kiểu thô (raw types)** và **các phép ép kiểu được chèn vào**.

**Các bước trình biên dịch thực hiện:**
1. Thay thế tất cả các tham số kiểu bằng giới hạn trên của chúng (hoặc `Object` nếu không có giới hạn).
2. Chèn các phép ép kiểu tường minh ở bất kỳ nơi nào một giá trị có kiểu được lấy ra.
3. Tạo ra các phương thức bắc cầu (bridge methods) khi cần thiết để bảo toàn tính đa hình (Polymorphism).

**Kết quả trong mã byte:**
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

**Hệ quả của việc xóa bỏ kiểu:**

| Những điều bạn không thể làm | Tại sao |
|--------------------|-----|
| `if (obj instanceof List<String>)` | Kiểu generic không xác định được khi chạy (runtime) |
| `new T[10]` | Không thể tạo mảng generic |
| `new T()` | Không thể khởi tạo thực thể của tham số kiểu |
| Nạp chồng các phương thức chỉ khác nhau ở kiểu generic | Sau khi xóa bỏ kiểu, chúng có chữ ký hoàn toàn giống nhau |
| Bắt ngoại lệ generic: `catch (SomeException<T> e)` | Không hợp lệ — thông tin generic đã bị xóa bỏ |

**Ví dụ — xung đột nạp chồng (lỗi biên dịch):**
```java
void process(List<String> list) { }
void process(List<Integer> list) { }   // COMPILE ERROR: same erasure List
```

**Các giải pháp thay thế:**
- Truyền `Class<T> clazz` như một token để tạo các thực thể thông qua `clazz.getDeclaredConstructor().newInstance()`.
- Sử dụng `Array.newInstance(clazz, size)` đối với mảng.

## Tại sao Java sử dụng cơ chế Xóa bỏ kiểu (Why Java Uses Type Erasure)

Generics trong Java được giới thiệu từ Java 5 để cung cấp tính an toàn kiểu dữ liệu tại thời điểm biên dịch. Vào thời điểm đó, hàng tỷ dòng mã byte Java cũ đã đang chạy trên các hệ thống sản xuất. Nếu JVM được thiết kế lại để thực thi generics lúc chạy (tương tự như reified generics của C#), các thư viện cũ trước thời kỳ generic sẽ không tương thích với môi trường chạy mới, yêu cầu một khối lượng biên dịch lại khổng lồ. Để bảo toàn khả năng tương thích ngược nghiêm ngặt, Java đã chọn cơ chế xóa bỏ kiểu, một thiết kế nơi các tham số kiểu chỉ tồn tại lúc biên dịch cho các kiểm tra an toàn, và bị trình biên dịch loại bỏ trước khi tạo ra các tệp class tiêu chuẩn. Do đó, mã byte được biên dịch sử dụng các kiểu thô và các phép ép kiểu ngầm định, cho phép mã byte cũ và mã generic mới chạy song song trên cùng một máy ảo mà không cần sửa đổi.

### Mô hình tư duy (Mental Model)

```text
Thời điểm biên dịch (Kiểm tra an toàn):
[List<String>] ---> Cho phép thêm "hello" ---> Từ chối thêm 123 (Lỗi biên dịch)

       |
       | Biên dịch (Xóa bỏ kiểu & Chèn phép ép kiểu)
       v

Thời điểm chạy (Thực thi bởi JVM):
[List] (ArrayList thô chứa các Object) ---> [Object: "hello"]
                                             |
                                             v (Phép ép kiểu ngầm định được chèn bởi trình biên dịch)
                                        (String) value
```

### Ví dụ Code (Code Example)

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

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
Mục tiêu: Chạy mã byte cũ song song với mã generic
  → Áp dụng cơ chế Xóa bỏ kiểu
  → Các tham số kiểu generic bị xóa thành các giới hạn/Object trong quá trình biên dịch
  → Mã byte của JVM chỉ chứa các kiểu thô và các phép ép kiểu được chèn vào
  → Các JVM cũ hơn có thể thực thi mã byte mà không cần biết về generics.
```


## Tại sao Generics có tính Bất biến và Cách PECS giải quyết nó (Why Generics Are Invariant and How PECS Solves It)

Trong Java, các mảng có tính đồng biến (covariant), nghĩa là `Integer[]` là một kiểu con của `Number[]`. Tuy nhiên, các kiểu generic có tính bất biến (invariant); ví dụ, `List<Integer>` không phải là một kiểu con của `List<Number>`, ngay cả khi `Integer` kế thừa từ `Number`. Nếu generics có tính đồng biến, bạn có thể gán một `List<Integer>` cho một tham chiếu `List<Number>`, và sau đó gọi `list.add(1.5)` (một số double) trên tham chiếu đó, làm ô nhiễm danh sách integer lúc chạy với các phần tử không hợp lệ. Để khôi phục tính linh hoạt trong khi vẫn duy trì tính an toàn kiểu dữ liệu, Java cung cấp các ký tự đại diện theo quy tắc PECS: Producer Extends, Consumer Super. Tính đồng biến với `? extends T` đảm bảo rằng chúng ta có thể đọc các phần tử từ một nhà sản xuất một cách an toàn vì chúng được đảm bảo có kiểu tối thiểu là `T`, trong khi tính nghịch biến (contravariance) với `? super T` đảm bảo chúng ta có thể ghi các phần tử `T` vào một người tiêu dùng một cách an toàn vì cấu trúc đó được đảm bảo chứa `T` hoặc các kiểu cha của nó.

### Mô hình tư duy (Mental Model)

```text
Tính bất biến (Khớp kiểu nghiêm ngặt):
List<Number>  <--- Không có mối quan hệ --->  List<Integer>

Giải pháp PECS cho tính Linh hoạt:
                       +-------------------------+
                       |   List<? extends Number> |  <--- Chỉ đọc (Đồng biến - Covariant)
                       +-------------------------+
                                    ^
                                    | (Cho phép trỏ tới)
                        List<Integer> hoặc List<Double>

                       +-------------------------+
                       |   List<? super Integer>  |  <--- Chỉ ghi (Nghịch biến - Contravariant)
                       +-------------------------+
                                    ^
                                    | (Cho phép trỏ tới)
                          List<Number> hoặc List<Object>
```

### Ví dụ Code (Code Example)

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

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)


```text
Tính đồng biến cho phép gán các kiểu con
  → Việc ghi các kiểu cha tùy ý vào tham chiếu làm hỏng tập hợp
  → Generics được thiết kế bất biến
  → Hạn chế các API quá mức
  → PECS được giới thiệu
  → Sử dụng extends để đọc an toàn (đồng biến) và super để ghi an toàn (nghịch biến).
```

