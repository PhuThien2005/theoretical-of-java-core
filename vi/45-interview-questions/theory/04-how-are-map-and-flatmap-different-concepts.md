# Các câu hỏi phỏng vấn Java Core phổ biến - Phần 4 (Common Java Core Interview Questions - Part 4)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các câu hỏi phỏng vấn Java Core nâng cao liên quan đến biến đổi Stream, hành vi của Optional, các lớp Map an toàn đa luồng, tính nhất quán của mã băm, cơ chế Thu gom rác (Garbage Collection) và tổ chức bộ nhớ JVM.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `How are map and flatMap different?` | `map` biến đổi từng phần tử thành một giá trị đơn lẻ (1-1); `flatMap` biến đổi từng phần tử thành một Stream rồi làm phẳng chúng lại (1-nhiều). |
| `How are orElse and orElseGet different?` | `orElse` luôn luôn đánh giá tham số đầu vào của nó; `orElseGet` đánh giá lười biếng (lazily) bằng cách sử dụng một `Supplier` chỉ khi `Optional` rỗng. |
| `How are HashMap, Hashtable, and ConcurrentHashMap different?` | `HashMap` không được đồng bộ hóa; `Hashtable` khóa toàn bộ bảng; `ConcurrentHashMap` sử dụng lock striping/CAS để đạt hiệu năng xử lý đồng thời cao. |
| `Why must overriding equals() also override hashCode()?` | Để duy trì hợp đồng rằng các đối tượng bằng nhau phải có mã băm bằng nhau, đảm bảo hành vi chính xác trong các bộ sưu tập băm (hash collections). |
| `How does Garbage Collection work?` | Thu hồi bộ nhớ của các đối tượng không thể tiếp cận (unreachable); thường sử dụng mô hình thu gom rác phân thế hệ chia heap thành Young và Old Gen. |
| `How are Stack and Heap different?` | Stack lưu trữ các biến cục bộ và các khung thực thi phương thức (trên mỗi luồng); Heap lưu trữ tất cả các đối tượng và mảng (dùng chung). |

---

## Ghi chú chi tiết (Detailed Notes)

### map() so với flatMap() (map() vs. flatMap())

Cả hai đều là các thao tác trung gian (intermediate operations) của Stream/Optional, nhưng chúng khác nhau về phong cách ánh xạ:
- **`map`**: Biến đổi `Stream<T>` thành `Stream<R>` sử dụng một hàm `T -> R`.
- **`flatMap`**: Biến đổi `Stream<T>` thành `Stream<R>` sử dụng một hàm `T -> Stream<R>`. Nó gộp (làm phẳng) nhiều stream nội bộ thành một stream duy nhất ở bên ngoài.

```java
// map: [ ["a", "b"], ["c"] ] -> [ 2, 1 ] (độ dài)
List<List<String>> list = List.of(List.of("a", "b"), List.of("c"));
Stream<Integer> sizes = list.stream().map(List::size);

// flatMap: [ ["a", "b"], ["c"] ] -> [ "a", "b", "c" ] (được làm phẳng)
Stream<String> flat = list.stream().flatMap(Collection::stream);
```

---

### Optional: orElse so với orElseGet (Optional: orElse vs. orElseGet)

- **`orElse(T other)`**: Giá trị mặc định `other` được đánh giá **sớm (eagerly)**, ngay cả khi `Optional` không rỗng.
- **`orElseGet(Supplier<? extends T> other)`**: Giá trị mặc định được đánh giá **lười biếng (lazily)** (sử dụng một biểu thức lambda) chỉ khi `Optional` rỗng.

```java
public String getDatabaseValue() {
    System.out.println("Costly DB query run!");
    return "DB_VALUE";
}

Optional<String> optional = Optional.of("Alice");
optional.orElse(getDatabaseValue());    // IN RA: "Costly DB query run!" (đánh giá sớm)
optional.orElseGet(() -> getDatabaseValue()); // KHÔNG IN RA (đánh giá lười biếng)
```

---

### HashMap so với Hashtable và ConcurrentHashMap (HashMap vs. Hashtable vs. ConcurrentHashMap)

- **`HashMap`**: Không đồng bộ hóa, chấp nhận một khóa `null` và nhiều giá trị `null`. Hiệu năng cao cho các thao tác đơn luồng hoặc đồng bộ hóa từ bên ngoài.
- **`Hashtable`**: Lớp cũ (legacy). Đồng bộ hóa mọi phương thức trên toàn bộ thực thể map. Hiệu năng xử lý đồng thời kém. Từ chối khóa/giá trị `null`.
- **`ConcurrentHashMap`**: Hiệu năng xử lý đồng thời cao. Trong Java 8+, nó sử dụng sự kết hợp của Compare-And-Swap (CAS) và các khóa synchronized ở cấp độ bucket/nút (lock striping), cho phép đọc và ghi đồng thời trên các bucket khác nhau. Từ chối khóa/giá trị `null`.

---

### Tại sao equals() và hashCode() phải được ghi đè cùng nhau (Why equals() and hashCode() must be overridden together)

Nếu bạn ghi đè `equals()`, bạn bắt buộc phải ghi đè `hashCode()`.
- **Hợp đồng (The Contract)**: Nếu `o1.equals(o2)` là `true`, thì `o1.hashCode() == o2.hashCode()` bắt buộc phải là `true`.
- **Hệ quả của sự vi phạm**: Nếu bạn vi phạm điều này, việc đặt một đối tượng vào `HashMap` hoặc `HashSet` sẽ dẫn đến trùng lặp khóa hoặc thất bại khi tìm kiếm. Bộ sưu tập băm sẽ ánh xạ các khóa bằng nhau vào các bucket khác nhau vì mã băm của chúng khác nhau.

---

### Thu gom rác (GC) hoạt động như thế nào (How Garbage Collection (GC) works)

Thu gom rác (Garbage Collection) tự động thu hồi bộ nhớ heap được cấp phát cho các đối tượng không còn có thể tiếp cận được từ bất kỳ **GC Roots** nào (các stack luồng đang hoạt động, biến tĩnh, tham chiếu JNI).

- **Thuyết GC phân thế hệ (Generational GC Theory)**: Hầu hết các đối tượng chết trẻ. Do đó, bộ nhớ heap của JVM được chia thành:
  1. **Thế hệ trẻ (Young Generation)**: Được chia nhỏ thành Eden và các vùng survivor (S0, S1). Các đợt Minor GC diễn ra ở đây thường xuyên và rất nhanh.
  2. **Thế hệ già (Old Generation)**: Giữ các đối tượng có tuổi thọ cao. Các đợt Major/Full GC diễn ra ở đây ít thường xuyên hơn và mất nhiều thời gian hơn.

---

### Bộ bộ nhớ Stack so với Heap (Stack vs. Heap Memory)

- **Stack**:
  - Bộ nhớ được cấp phát trên mỗi luồng.
  - Lưu trữ các biến cục bộ, các con trỏ tham chiếu và các khung stack (stack frames) cho các cuộc gọi phương thức.
  - Việc cấp phát/giải phóng tuân theo cấu trúc LIFO (Vào sau ra trước - Last-In-First-Out) và được xử lý tự động. Rất nhanh.
- **Heap**:
  - Bộ nhớ dùng chung cho tất cả các luồng.
  - Lưu trữ tất cả các đối tượng và mảng.
  - Được quản lý bởi Bộ thu gom rác (Garbage Collector). Việc cấp phát và dọn dẹp chậm hơn.

---

## Các lỗi thường gặp & Cạm bẫy (Common Mistakes & Traps)

### 1. Truy vấn Cơ sở dữ liệu/API bên trong orElse() (Database/API query inside orElse())
Gọi một truy vấn lấy dữ liệu DB bên trong `orElse(...)` sẽ chạy truy vấn đó mọi lúc, ngay cả khi giá trị trong Optional đã tồn tại:
```java
// Truy vấn DB chạy ngay cả khi user đã được lưu đệm trong Optional!
User u = optionalUser.orElse(db.fetchDefaultUser()); 
```
Thay vào đó, hãy sử dụng `orElseGet()`:
```java
User u = optionalUser.orElseGet(() -> db.fetchDefaultUser());
```

### 2. Vi phạm các giả định về khả năng tiếp cận của GC (Violating GC reachability assumptions)
Giả định sai lầm rằng việc gán một tham chiếu bằng `null` sẽ buộc GC chạy ngay lập tức. Việc gán `u = null` chỉ làm cho đối tượng *đủ điều kiện* để được GC thu dọn. Việc dọn dẹp thực tế xảy ra khi JVM chạy bộ thu gom rác.

---

## Tại sao các thao tác Stream map và flatMap khác nhau (Why map and flatMap Stream Operations Differ)

Java Stream cung cấp `map` và `flatMap` để hỗ trợ các cấu trúc biến đổi khác nhau cho các phần tử trong một đường dẫn (pipeline).

Thao tác `map` nhận một hàm ánh xạ `T -> R` và áp dụng nó lên từng phần tử riêng lẻ, tạo ra một `Stream<R>` có cùng kích thước với stream đầu vào (một ánh xạ 1-1 nghiêm ngặt).

Ngược lại, `flatMap` nhận một hàm ánh xạ `T -> Stream<R>`, chuyển đổi mỗi phần tử đầu vào thành một sub-stream mới, và sau đó làm phẳng tất cả các sub-stream được tạo ra thành một stream bên ngoài liền mạch duy nhất. Điều này cho phép `flatMap` xử lý các biến đổi 1-nhiều hoặc ánh xạ các bộ sưu tập lồng nhau (như `List<List<T>>`) thành một cấu trúc danh sách phẳng.

Bên dưới lớp vỏ, `flatMap` tạo và đóng nhiều stream trung gian, điều này có thể phát sinh thêm một chút chi phí hiệu năng so với việc ánh xạ trực tiếp phần tử-phần tử của `map`.

### Mô hình tư duy (Mental Model)

```text
  Stream đầu vào: [ [A, B], [C, D] ]
  
  Map (List::size):
    [A, B] ---> 2
    [C, D] ---> 2
    Stream đầu ra: [ 2, 2 ] (Không thay đổi cấu trúc, 1-1)
  
  FlatMap (Collection::stream):
    [A, B] ---> Stream[A, B] \
                             +---> Stream đầu ra: [ A, B, C, D ]
    [C, D] ---> Stream[C, D] /     (Được làm phẳng thành một stream duy nhất)
```

### Ví dụ Code (Code Example)

Ví dụ dưới đây minh họa cách `map` và `flatMap` xử lý một danh sách lồng nhau của các chuỗi khác nhau như thế nào.

```java
import java.util.List;
import java.util.stream.Collectors;

public class StreamMappingDemo {
    public static void main(String[] args) {
        List<List<String>> nested = List.of(List.of("A", "B"), List.of("C"));

        // map() giữ nguyên cấu trúc lồng nhau: List<List<String>> -> List<Integer>
        List<Integer> lengths = nested.stream().map(List::size).collect(Collectors.toList());
        System.out.println(lengths); // Output: [2, 1]

        // flatMap() làm phẳng cấu trúc lồng nhau: List<List<String>> -> List<String>
        List<String> flat = nested.stream().flatMap(List::stream).collect(Collectors.toList());
        System.out.println(flat);    // Output: [A, B, C]
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)


```text
Phần tử Stream T được xử lý bởi thao tác trung gian
  → NẾU gọi `map(T -> R)`: hàm trả về R trực tiếp; R được chuyển tiếp xuống dưới
  → NẾU gọi `flatMap(T -> Stream<R>)`: hàm tạo ra `Stream<R>` lồng nhau
  → Bộ chia tách `spliterator` nội bộ của FlatMap duyệt qua từng phần tử của stream lồng nhau
  → Các phần tử R lồng nhau riêng lẻ được đưa vào đường dẫn stream bên ngoài một cách tuần tự
  → Tài nguyên stream lồng nhau được đóng lại, tạo ra một đầu ra `Stream<R>` phẳng duy nhất.
```


---

## Tại sao việc xác thực Generic lúc biên dịch khác với thời điểm chạy (Why Generic Compile-Time Verification Differs from Runtime)

Generics trong Java được thiết kế với mục tiêu tương thích ngược, dẫn đến sự khác biệt lớn giữa việc xác thực kiểu ở thời điểm biên dịch và thực thi kiểu ở thời điểm chạy.

Tại thời điểm biên dịch, trình biên dịch `javac` thực hiện kiểm tra kiểu dữ liệu nghiêm ngặt để đảm bảo rằng các đối tượng được đưa vào một bộ sưu tập được tham số hóa tuân thủ các đối số kiểu đã chỉ định. Tuy nhiên, sau khi quá trình biên dịch hoàn tất, trình biên dịch sẽ thực hiện **Cơ chế xóa bỏ kiểu (Type Erasure)**, loại bỏ tất cả các tham số kiểu generic khỏi các tệp class và thay thế chúng bằng các biên thô của chúng (thường là `Object`).

Do đó, JVM thực thi bytecode chứa các kiểu thô (raw types), nghĩa là nó không biết về các ràng buộc generic tại thời điểm chạy. Nếu một lập trình viên sử dụng các kiểu thô hoặc ép kiểu không an toàn để vượt qua xác thực ở thời điểm biên dịch, họ có thể đưa các đối tượng không tương thích vào các bộ sưu tập generic, một hiện tượng được gọi là Ô nhiễm bộ nhớ heap (Heap Pollution), cuối cùng sẽ kích hoạt ngoại lệ `ClassCastException` tại thời điểm chạy khi các phần tử được lấy ra và ép kiểu ngầm.

### Mô hình tư duy (Mental Model)

```text
  Thời điểm biên dịch (Kiểm tra kiểu nghiêm ngặt)
    List<String> list = new ArrayList<>();
    list.add("Hello"); // OK
    list.add(123);     // Lỗi biên dịch!
         │
         ▼ (Type Erasure bởi javac)
  Runtime Bytecode JVM (Kiểu thô / Xóa về Object)
    List list = new ArrayList();
    list.add("Hello"); // OK
    list.add(123);     // OK ở runtime! (Heap Pollution)
    String s = (String) list.get(1); // Ném ra ClassCastException!
```

### Ví dụ Code (Code Example)

Đoạn mã dưới đây minh họa cách bỏ qua các kiểm tra generic ở thời điểm biên dịch dẫn đến ô nhiễm heap và ngoại lệ runtime `ClassCastException`.

```java
import java.util.*;

public class GenericsErasureDemo {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("Safe");
        List raw = strings; // tham chiếu thô (raw reference)
        raw.add(100); // Gây ô nhiễm bộ nhớ heap (Heap Pollution)

        try {
            String s = strings.get(1); // Ép kiểu ngầm định ném ngoại lệ
        } catch (ClassCastException e) {
            System.out.println("Failed: " + e.getMessage());
            // Output: Failed: class java.lang.Integer cannot be cast to class java.lang.String
        }
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)


```text
Thời điểm biên dịch: javac xác thực các kiểu generic trên các bộ sưu tập được tham số hóa
  → Type Erasure: trình biên dịch loại bỏ tham số kiểu, thay thế bằng Object/biên
  → Trình biên dịch chèn các lệnh ép kiểu ngầm định (ví dụ: checkcast) tại các thao tác đọc bộ sưu tập
  → Thời điểm chạy: Mã sử dụng tham chiếu kiểu thô hoặc ép kiểu không an toàn để thêm kiểu phần tử sai
  → Phần tử được chèn thành công vào mảng đối tượng bên dưới (không có kiểm tra kiểu JVM)
  → Thao tác đọc thực thi lệnh checkcast trên đối tượng không khớp
  → JVM ném ngoại lệ ClassCastException do không tương thích kiểu dữ liệu lúc chạy.
```

