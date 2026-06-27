# Các Câu Hỏi Phỏng Vấn Java Core Thường Gặp - Phần 4 (Common Java Core Interview Questions - Part 4)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày các câu hỏi phỏng vấn Java Core nâng cao liên quan đến các phép biến đổi Stream, hành vi của lớp Optional, các lớp Map an toàn luồng, tính nhất quán của mã băm, cơ chế hoạt động của Bộ thu gom rác (Garbage Collection) và cách tổ chức bộ nhớ của JVM.

## Khung Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Nội dung cần biết (What to know) |
| --- | --- |
| `How are map and flatMap different?` | `map` biến đổi từng phần tử thành một giá trị đơn lẻ (quan hệ 1-1); `flatMap` biến đổi từng phần tử thành một Stream và làm phẳng chúng lại thành một Stream duy nhất (quan hệ 1-nhiều). |
| `How are orElse and orElseGet different?` | `orElse` luôn luôn đánh giá tham số truyền vào; `orElseGet` chỉ đánh giá một cách trì hoãn (lazy) bằng cách sử dụng một `Supplier` khi đối tượng `Optional` rỗng. |
| `How are HashMap, Hashtable, and ConcurrentHashMap different?` | `HashMap` không được đồng bộ hóa; `Hashtable` thực hiện khóa trên toàn bộ bảng; `ConcurrentHashMap` sử dụng phân đoạn khóa (lock striping) và các phép toán CAS để đạt hiệu năng đồng thời cao. |
| `Why must overriding equals() also override hashCode()?` | Để duy trì ràng buộc rằng các đối tượng bằng nhau thì phải có mã băm bằng nhau, đảm bảo hành vi chính xác trong các bộ sưu tập băm (hash collections). |
| `How does Garbage Collection work?` | Thu hồi bộ nhớ của các đối tượng không còn khả năng tiếp cận (unreachable); thông thường sử dụng lý thuyết phân thế hệ chia bộ nhớ heap thành thế hệ Trẻ (Young Gen) và thế hệ Già (Old Gen). |
| `How are Stack and Heap different?` | Stack lưu trữ các biến cục bộ và các khung thực thi phương thức (dành riêng cho từng luồng); Heap lưu trữ tất cả các đối tượng và mảng (chia sẻ chung giữa các luồng). |

---

## Ghi Chú Chi Tiết (Detailed Notes)

### map() vs. flatMap()

Cả hai đều là các hoạt động trung gian của Stream/Optional, nhưng chúng khác nhau về phong cách ánh xạ:
- **`map`**: Biến đổi `Stream<T>` thành `Stream<R>` bằng cách sử dụng một hàm chuyển đổi `T -> R`.
- **`flatMap`**: Biến đổi `Stream<T>` thành `Stream<R>` bằng cách sử dụng một hàm chuyển đổi `T -> Stream<R>`. Nó gộp (làm phẳng) nhiều stream con bên trong thành một stream lớn duy nhất bên ngoài.

```java
// map: [ ["a", "b"], ["c"] ] -> [ 2, 1 ] (kích thước của các danh sách con)
List<List<String>> list = List.of(List.of("a", "b"), List.of("c"));
Stream<Integer> sizes = list.stream().map(List::size);

// flatMap: [ ["a", "b"], ["c"] ] -> [ "a", "b", "c" ] (được làm phẳng)
Stream<String> flat = list.stream().flatMap(Collection::stream);
```

---

### Optional: `orElse` vs. `orElseGet`

- **`orElse(T other)`**: Giá trị mặc định `other` được đánh giá **ngay lập tức (eagerly)**, ngay cả khi đối tượng `Optional` không rỗng.
- **`orElseGet(Supplier<? extends T> other)`**: Giá trị mặc định được đánh giá **trì hoãn (lazily)** (thông qua một biểu thức lambda) chỉ khi đối tượng `Optional` thực sự rỗng.

```java
public String getDatabaseValue() {
    System.out.println("Costly DB query run!");
    return "DB_VALUE";
}

Optional<String> optional = Optional.of("Alice");
optional.orElse(getDatabaseValue());    // IN RA: "Costly DB query run!" (đánh giá ngay lập tức)
optional.orElseGet(() -> getDatabaseValue()); // KHÔNG IN RA (đánh giá trì hoãn)
```

---

### HashMap vs. Hashtable vs. ConcurrentHashMap

- **`HashMap`**: Không được đồng bộ hóa, chấp nhận một khóa `null` và nhiều giá trị `null`. Đạt hiệu năng cao cho các ứng dụng đơn luồng hoặc khi được đồng bộ hóa từ bên ngoài.
- **`Hashtable`**: Lớp di sản cổ điển. Đồng bộ hóa tất cả các phương thức trên toàn bộ thực thể map, dẫn đến hiệu năng đồng thời kém. Từ chối các khóa/giá trị `null`.
- **`ConcurrentHashMap`**: Hỗ trợ đồng thời cao. Từ Java 8+, nó kết hợp các phép toán So sánh và Tráo đổi (Compare-And-Swap - CAS) cùng khối khóa `synchronized` ở cấp độ từng thùng/nút (cơ chế phân đoạn khóa - lock striping), cho phép nhiều luồng cùng đọc và ghi trên các thùng khác nhau mà không gây nghẽn. Từ chối các khóa/giá trị `null`.

---

### Tại sao equals() và hashCode() phải được ghi đè cùng nhau (Why equals() and hashCode() must be overridden together)

Nếu bạn ghi đè phương thức `equals()`, bạn bắt buộc phải ghi đè phương thức `hashCode()`.
- **Ràng buộc (The Contract)**: Nếu `o1.equals(o2)` trả về `true`, thì `o1.hashCode() == o2.hashCode()` bắt buộc phải trả về `true`.
- **Hệ quả của sự vi phạm**: Nếu bạn vi phạm điều này, việc thêm đối tượng vào `HashMap` hoặc `HashSet` sẽ dẫn đến các khóa bị trùng lặp hoặc thất bại khi tìm kiếm phần tử. Bộ sưu tập băm sẽ ánh xạ các khóa bằng nhau này vào các thùng khác nhau vì mã băm của chúng khác nhau.

---

### Bộ thu gom rác (GC) hoạt động như thế nào (How Garbage Collection (GC) works)

Bộ thu gom rác tự động thu hồi vùng nhớ heap được cấp phát cho các đối tượng không còn khả năng tiếp cận từ bất kỳ **Gốc GC (GC Roots)** nào (ngăn xếp luồng đang hoạt động, các biến tĩnh static, các tham chiếu JNI).

- **Lý thuyết Phân thế hệ GC (Generational GC Theory)**: Phần lớn các đối tượng đều chết trẻ. Do đó, vùng nhớ heap của JVM được chia thành:
  1. **Thế hệ Trẻ (Young Generation)**: Được chia nhỏ thành vùng Eden và các vùng sống sót (Survivor spaces - S0, S1). Các đợt dọn rác phụ (Minor GC) xảy ra ở đây thường xuyên và diễn ra rất nhanh.
  2. **Thế hệ Già (Old Generation)**: Chứa các đối tượng sống lâu. Các đợt dọn rác chính/toàn phần (Major/Full GC) xảy ra ở đây ít thường xuyên hơn và mất nhiều thời gian hơn.

---

### Bộ nhớ Stack vs. Heap (Stack vs. Heap Memory)

- **Ngăn xếp (Stack)**:
  - Vùng nhớ được cấp phát riêng cho từng luồng (thread).
  - Lưu trữ các biến cục bộ, các con trỏ tham chiếu và các khung ngăn xếp (stack frames) cho các lượt gọi phương thức.
  - Việc cấp phát/giải phóng tuân theo cấu trúc LIFO (Vào sau - Ra trước) và được xử lý hoàn toàn tự động. Tốc độ truy cập rất nhanh.
- **Heap**:
  - Vùng nhớ dùng chung cho tất cả các luồng.
  - Lưu trữ toàn bộ các đối tượng và mảng.
  - Được quản lý bởi Bộ thu gom rác. Việc cấp phát bộ nhớ và dọn dẹp diễn ra chậm hơn.

---

## Các Sai Lầm Phổ Biến & Cạm Bẫy (Common Mistakes & Traps)

### 1. Thực hiện truy vấn Cơ sở dữ liệu/API bên trong `orElse()`
Việc gọi một phương thức truy xuất DB bên trong `orElse(...)` sẽ chạy truy vấn đó trong mọi trường hợp, ngay cả khi giá trị cần tìm đã tồn tại trong Optional:
```java
// Lời gọi cơ sở dữ liệu vẫn chạy ngay cả khi user đã được cache trong Optional!
User u = optionalUser.orElse(db.fetchDefaultUser()); 
```
Thay vào đó, hãy sử dụng `orElseGet()`:
```java
User u = optionalUser.orElseGet(() -> db.fetchDefaultUser());
```

### 2. Hiểu sai về cơ chế tiếp cận của GC
Lầm tưởng rằng việc gán một tham chiếu bằng `null` sẽ ép buộc GC chạy ngay lập tức. Lệnh gán `u = null` chỉ làm cho đối tượng đó *đủ điều kiện* để được dọn rác. Việc dọn dẹp thực tế chỉ xảy ra khi JVM chạy bộ thu gom rác.

---

## Tại sao Hoạt Động Stream map và flatMap Khác Biệt (Why map and flatMap Stream Operations Differ)

Java Streams cung cấp hai hoạt động `map` và `flatMap` để hỗ trợ các kiểu chuyển đổi cấu trúc phần tử khác nhau trong một đường dẫn dữ liệu. Hoạt động `map` nhận một hàm ánh xạ `T -> R` và áp dụng nó lên từng phần tử một cách riêng biệt, tạo ra một `Stream<R>` có cùng kích thước với stream đầu vào (ánh xạ 1-1 nghiêm ngặt). Ngược lại, `flatMap` nhận một hàm ánh xạ `T -> Stream<R>`, chuyển đổi mỗi phần tử đầu vào thành một sub-stream mới, và sau đó gộp (làm phẳng) tất cả các sub-stream này thành một stream duy nhất nằm liền mạch bên ngoài. Điều này cho phép `flatMap` xử lý các biến đổi 1-nhiều hoặc ánh xạ các bộ sưu tập lồng nhau (như `List<List<T>>`) thành một cấu trúc danh sách phẳng duy nhất. Bên dưới lớp vỏ, `flatMap` phải liên tục tạo và đóng nhiều stream trung gian, điều này có thể gây ra một chút chi phí hiệu năng so với việc ánh xạ trực tiếp phần tử-phần tử của `map`.

### Mô hình Tư duy (Mental Model)

```text
  Stream Đầu Vào: [ [A, B], [C, D] ]
  
  Map (List::size):
    [A, B] ---> 2
    [C, D] ---> 2
    Stream Đầu Ra: [ 2, 2 ] (Không thay đổi cấu trúc, ánh xạ 1-1)
  
  FlatMap (Collection::stream):
    [A, B] ---> Stream[A, B] \
                             +---> Stream Đầu Ra: [ A, B, C, D ]
    [C, D] ---> Stream[C, D] /        (Được làm phẳng thành một stream duy nhất)
```

### Ví Dụ Mã Nguồn (Code Example)

Ví dụ dưới đây minh họa cách `map` và `flatMap` xử lý một danh sách chuỗi lồng nhau theo các cách khác nhau.

```java
import java.util.List;
import java.util.stream.Collectors;

public class StreamMappingDemo {
    public static void main(String[] args) {
        List<List<String>> nested = List.of(List.of("A", "B"), List.of("C"));

        // map() giữ nguyên cấu trúc lồng nhau: List<List<String>> -> List<Integer>
        List<Integer> lengths = nested.stream().map(List::size).collect(Collectors.toList());
        System.out.println(lengths); // Kết quả: [2, 1]

        // flatMap() làm phẳng cấu trúc lồng: List<List<String>> -> List<String>
        List<String> flat = nested.stream().flatMap(List::stream).collect(Collectors.toList());
        System.out.println(flat);    // Kết quả: [A, B, C]
    }
}
```

### Chuỗi Nhân Quả (Cause-Effect Chain)

```text
Phần tử Stream T được xử lý bởi hoạt động trung gian
  → NẾU gọi map(T -> R): hàm trả về trực tiếp đối tượng R; R được truyền tiếp xuống luồng xử lý
  → NẾU gọi flatMap(T -> Stream<R>): hàm tạo ra một Stream<R> lồng bên trong
  → Bộ phân tách (spliterator) nội bộ của FlatMap duyệt qua từng phần tử của stream lồng đó
  → Từng phần tử R riêng lẻ được đưa vào đường dẫn của stream lớn bên ngoài một cách tuần tự
  → Các tài nguyên của stream lồng được đóng lại, tạo ra kết quả đầu ra là một Stream<R> phẳng duy nhất
```

---

## Tại sao Việc Xác Minh Kiểu Generic Lúc Biên Dịch Khác Với Lúc Chạy (Why Generic Compile-Time Verification Differs from Runtime)

Tính năng generic của Java được thiết kế nhằm mục đích đảm bảo tính tương thích ngược, dẫn đến sự khác biệt lớn giữa việc xác minh kiểu tại thời điểm biên dịch và thực thi kiểu tại thời điểm chạy. Tại thời điểm biên dịch, trình biên dịch `javac` thực hiện kiểm tra kiểu nghiêm ngặt để đảm bảo các đối tượng được thêm vào một bộ sưu tập có kiểu tham số hóa phải tuân thủ đúng các đối số kiểu được chỉ định. Tuy nhiên, sau khi quá trình biên dịch hoàn tất, trình biên dịch sẽ thực hiện **Cơ chế xóa kiểu (Type Erasure)**, loại bỏ toàn bộ các tham số kiểu generic khỏi tệp class và thay thế chúng bằng các kiểu giới hạn thô của chúng (thường là `Object`). Kết quả là, JVM thực thi mã byte chỉ chứa các kiểu thô (raw types), nghĩa là nó hoàn toàn không biết về các ràng buộc generic tại thời điểm chạy. Nếu lập trình viên sử dụng các kiểu thô hoặc ép kiểu không an toàn để vượt qua bước xác minh lúc biên dịch, họ có thể đưa các đối tượng không tương thích vào các bộ sưu tập generic, hiện tượng này được gọi là **Ô nhiễm vùng nhớ Heap (Heap Pollution)**, và cuối cùng sẽ kích hoạt lỗi `ClassCastException` tại thời điểm chạy khi các phần tử được truy xuất và ép kiểu ngầm định.

### Mô hình Tư duy (Mental Model)

```text
  Thời Điểm Biên Dịch (Kiểm tra kiểu nghiêm ngặt)
    List<String> list = new ArrayList<>();
    list.add("Hello"); // Hợp lệ
    list.add(123);     // Lỗi biên dịch!
         │
         ▼ (Xóa kiểu bởi javac)
  Thời Điểm Chạy JVM (Kiểu thô / Trở thành Object)
    List list = new ArrayList();
    list.add("Hello"); // Hợp lệ
    list.add(123);     // Hợp lệ lúc chạy! (Ô nhiễm Heap)
    String s = (String) list.get(1); // Ném ra ClassCastException!
```

### Ví Dụ Mã Nguồn (Code Example)

Đoạn mã dưới đây minh họa việc bỏ qua kiểm tra generic lúc biên dịch dẫn đến ô nhiễm heap và gây ra lỗi `ClassCastException` tại thời điểm chạy.

```java
import java.util.*;

public class GenericsErasureDemo {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("Safe");
        List raw = strings; // tham chiếu kiểu thô (raw reference)
        raw.add(100); // Ô nhiễm vùng nhớ Heap (Heap Pollution)

        try {
            String s = strings.get(1); // Lệnh ép kiểu ngầm định sẽ bị lỗi
        } catch (ClassCastException e) {
            System.out.println("Failed: " + e.getMessage());
            // Kết quả: Failed: class java.lang.Integer cannot be cast to class java.lang.String
        }
    }
}
```

### Chuỗi Nhân Quả (Cause-Effect Chain)

```text
Thời điểm biên dịch: javac xác minh các kiểu generic trên các bộ sưu tập có kiểu tham số hóa
  → Xóa kiểu: trình biên dịch loại bỏ tham số kiểu, thay thế chúng bằng Object hoặc các kiểu giới hạn
  → Trình biên dịch chèn lệnh ép kiểu ngầm định (ví dụ: checkcast) tại các thao tác đọc bộ sưu tập
  → Thời điểm chạy: Mã nguồn sử dụng tham chiếu kiểu thô hoặc ép kiểu không an toàn để thêm phần tử sai kiểu
  → Phần tử được thêm thành công vào mảng đối tượng bên dưới (không có bước kiểm tra kiểu của JVM)
  → Thao tác đọc thực thi chỉ thị checkcast trên đối tượng không khớp kiểu
  → JVM ném ra lỗi ClassCastException do không tương thích kiểu tại thời điểm chạy
```
