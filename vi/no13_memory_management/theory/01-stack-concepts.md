# Quản Lý Bộ Nhớ Java (Java Memory Management) - Phần 1

## Mục Tiêu Học Tập

File này tập trung vào một phần cụ thể của **Quản Lý Bộ Nhớ Java**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tiễn, không phải từ vựng đơn thuần.

## Các Khái Niệm Được Đề Cập

- **`Stack`** — Stack: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Heap`** — Heap: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Vùng Phương Thức / Metaspace`** — Vùng Phương Thức / Metaspace: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`PC Register`** — PC Register: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Native Method Stack`** — Native Method Stack: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Vòng đời đối tượng`** — Vòng đời đối tượng: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Biến tham chiếu`** — Biến tham chiếu: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Tham chiếu mạnh`** — Tham chiếu mạnh: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

## Ghi Chú Chi Tiết

### Stack (Ngăn Xếp)

Bộ nhớ Stack là riêng của từng luồng (thread-private), dùng để lưu trữ khung thực thi phương thức, biến cục bộ, tham số, và luồng gọi của một luồng đơn.

#### Quy Tắc JVM
- Mỗi khi một luồng gọi phương thức, một **Stack Frame** mới được đẩy lên stack của luồng đó.
- Khi phương thức hoàn thành (qua `return` hoặc ném ngoại lệ chưa được xử lý), stack frame của nó được lấy ra.
- Biến cục bộ kiểu nguyên thủy (ví dụ: `int`, `double`, `boolean`) và tham chiếu đến đối tượng trên heap nằm trực tiếp trong stack frame.
- Bộ nhớ Stack được phân bổ và thu hồi tự động theo thứ tự LIFO (Last-In-First-Out). Nó cực kỳ nhanh nhưng có kích thước cố định (được cấu hình qua `-Xss`).

#### Ví Dụ Code: Vòng Đời Stack Frame
```java
public class StackDemo {
    public static void main(String[] args) {
        int a = 10; // Lưu trong stack frame của main
        int b = 20; // Lưu trong stack frame của main
        int result = add(a, b); // Đẩy một frame mới cho add()
        System.out.println(result);
    } // Frame của main được lấy ra, stack rỗng

    private static int add(int x, int y) {
        int sum = x + y; // Lưu trong stack frame của add
        return sum; 
    } // Frame của add được lấy ra, sum, x, y được thu hồi
}
```

### Heap (Vùng Nhớ Heap)

Bộ nhớ Heap là vùng dữ liệu runtime được chia sẻ, nơi JVM phân bổ không gian cho tất cả các thực thể lớp (đối tượng) và mảng.

#### Quy Tắc JVM
- Tất cả các đối tượng Java, bất kể được tạo ở đâu, đều nằm trên Heap.
- Bộ nhớ Heap được chia sẻ giữa tất cả các luồng, có nghĩa là các đối tượng có thể được truy cập đồng thời (điều này đòi hỏi đồng bộ hóa để đảm bảo an toàn luồng — thread-safety).
- Không giống bộ nhớ stack, phân bổ heap là động và không theo thứ tự LIFO.
- Các đối tượng trên heap không bị thu hồi ngay khi phương thức thoát. Thay vào đó, chúng vẫn ở trên heap cho đến khi không còn đến được và Bộ Thu Gom Rác thu hồi chúng.
- Kích thước Heap được cấu hình bằng các flag JVM như `-Xms` (kích thước ban đầu) và `-Xmx` (kích thước tối đa). Vượt quá giới hạn sẽ dẫn đến `java.lang.OutOfMemoryError: Java heap space`.

#### Ví Dụ Code: Phân Bổ Stack và Heap
```java
public class MemoryAllocationDemo {
    public static void main(String[] args) {
        int localPrimitive = 42; // Giá trị 42 được lưu trên Stack
        
        // Biến tham chiếu 'customer' nằm trên Stack.
        // Đối tượng 'Customer' thực sự được phân bổ trên Heap.
        Customer customer = new Customer("Alice", 30);
        
        modifyCustomer(customer);
    }

    private static void modifyCustomer(Customer cust) {
        // 'cust' là bản sao của biến tham chiếu, trỏ đến cùng một đối tượng trên heap.
        cust.setAge(31); // Thay đổi trạng thái đối tượng trên Heap
    }
}

class Customer {
    private String name; // Tham chiếu đến đối tượng String trên Heap
    private int age;     // Trường nguyên thủy, lưu trên Heap như một phần của đối tượng Customer

    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAge(int age) { this.age = age; }
}
```

### Vùng Phương Thức (Method Area) / Metaspace

Vùng Phương Thức là vùng bộ nhớ JVM được chia sẻ, lưu trữ siêu dữ liệu cấp lớp. Trong các JVM HotSpot hiện đại (Java 8+), vùng này được triển khai dưới dạng **Metaspace**.

#### Quy Tắc JVM
- Metaspace lưu thông tin cấu trúc lớp: định nghĩa lớp, bytecode phương thức, code constructor, bộ nhớ hằng số runtime, annotation, và bảng phương thức.
- Kể từ Java 8, Metaspace được phân bổ trong bộ nhớ native (ngoài heap) thay vì Java heap tiêu chuẩn. Điều này ngăn các vấn đề giới hạn nạp lớp phổ biến với PermGen.
- Mặc dù có thể tự động mở rộng theo mặc định, kích thước của nó có thể bị giới hạn bằng `-XX:MaxMetaspaceSize`.
- Khi các class loader bị thu gom rác, siêu dữ liệu lớp tương ứng của chúng trong Metaspace sẽ được gỡ bỏ.

### PC Register (Thanh Ghi Bộ Đếm Chương Trình)

Mỗi luồng có Thanh Ghi PC (Program Counter) riêng.

#### Quy Tắc JVM
- Nếu luồng đang thực thi một phương thức Java không phải native, PC Register chứa địa chỉ của lệnh JVM đang được thực thi.
- Nếu luồng đang thực thi một phương thức native, giá trị PC Register không xác định.
- PC Register nhẹ và không tự mở rộng. Nó quan trọng cho lập lịch luồng và chuyển ngữ cảnh, cho phép luồng tiếp tục thực thi từ đúng lệnh mà chúng đã dừng lại.

### Native Method Stack (Ngăn Xếp Phương Thức Gốc)

Native Method Stack là ngăn xếp riêng của từng luồng, dành riêng cho các phương thức viết bằng ngôn ngữ không phải Java (thường là C hoặc C++) được gọi qua Java Native Interface (JNI).

#### Quy Tắc JVM
- Khi một phương thức Java gọi một phương thức native, ngữ cảnh thực thi chuyển sang Native Method Stack.
- Giống như Java stack, nó là riêng của từng luồng và có thể ném `StackOverflowError` nếu độ sâu gọi native vượt quá giới hạn.

### Vòng Đời Đối Tượng (Object Lifecycle)

Vòng đời của một đối tượng bao gồm nhiều giai đoạn khác nhau:

1. **Tạo (Creation)**: Bộ nhớ được phân bổ trên heap, biến thực thể được khởi tạo, constructor thực thi.
2. **Khả năng truy cập (Reachability)**: Đối tượng có thể được ứng dụng sử dụng miễn là có chuỗi tham chiếu từ luồng đang hoạt động (GC Root) đến đối tượng.
3. **Không thể truy cập / Đủ điều kiện GC**: Khi đối tượng không còn đến được từ bất kỳ GC Root nào, nó trở nên đủ điều kiện để Thu Gom Rác.
4. **Finalization (Deprecated — Không dùng nữa)**: Nếu đối tượng định nghĩa phương thức `finalize()`, nó có thể được chạy trước khi thu hồi.
5. **Thu hồi (Reclamation)**: Bộ Thu Gom Rác thu hồi bộ nhớ trên heap.

### Biến Tham Chiếu (Reference Variable)

Biến tham chiếu là biến lưu trữ địa chỉ bộ nhớ (tham chiếu) của một đối tượng trên heap, thay vì bản thân đối tượng.

#### Quy Tắc JVM
- Khai báo biến tham chiếu (ví dụ: `Customer c;`) dành không gian trên stack hoặc heap cho một con trỏ, được khởi tạo bằng `null`.
- Java hoàn toàn là **pass-by-value** (truyền theo giá trị). Khi tham chiếu đối tượng được truyền vào phương thức, bản thân tham chiếu (con trỏ địa chỉ bộ nhớ) được sao chép. Biến tham chiếu của caller không thể bị gán lại bởi phương thức, nhưng trạng thái của đối tượng mà nó trỏ đến có thể bị thay đổi.

```java
public class PassByValueDemo {
    public static void main(String[] args) {
        Customer c1 = new Customer("Bob", 25);
        reassign(c1);
        System.out.println(c1.getName()); // In "Bob" - tham chiếu gốc không bị thay đổi
        
        modify(c1);
        System.out.println(c1.getName()); // In "Charlie" - trạng thái đối tượng đã bị thay đổi
    }

    private static void reassign(Customer c) {
        c = new Customer("Dave", 40); // Chỉ thay đổi tham số 'c' cục bộ đã sao chép
    }

    private static void modify(Customer c) {
        c.setName("Charlie"); // Thay đổi đối tượng được trỏ đến bởi tham chiếu
    }
}
```

### Tham Chiếu Mạnh (Strong Reference)

Tham chiếu mạnh là kiểu tham chiếu mặc định trong Java. Bất kỳ đối tượng nào được tạo bằng phép gán thông thường (ví dụ: `Object obj = new Object();`) đều được tham chiếu mạnh.

#### Quy Tắc JVM
- Chừng nào đối tượng còn đến được thông qua ít nhất một đường tham chiếu mạnh bắt đầu từ GC Root, nó sẽ **không bao giờ** bị thu gom rác.
- Kể cả khi JVM đang thiếu bộ nhớ nghiêm trọng và sắp ném `OutOfMemoryError`, nó vẫn không thu hồi các đối tượng được tham chiếu mạnh.

---

## Lỗi Thường Gặp

### 1. Nghĩ Rằng Biến Nguyên Thủy Luôn Nằm Trên Stack
Một bẫy phỏng vấn rất phổ biến là khẳng định rằng tất cả biến nguyên thủy đều nằm trên stack.
**Quy tắc:** Vị trí của biến nguyên thủy được xác định bởi *nơi* nó được khai báo:
- **Biến nguyên thủy cục bộ** (khai báo trong phương thức) nằm trên **Stack**.
- **Biến nguyên thủy thực thể** (khai báo là trường lớp) nằm trên **Heap** như một phần của đối tượng chứa chúng.
- **Biến nguyên thủy static** (khai báo là trường static) nằm trên **Heap** bên trong đối tượng `java.lang.Class`.

### 2. Giả Sử `obj = null` Giải Phóng Bộ Nhớ Ngay Lập Tức
Đặt biến tham chiếu thành `null` không kích hoạt thu gom rác ngay lập tức.
**Quy tắc:** Gán lại tham chiếu thành `null` chỉ đơn giản là phá vỡ kết nối tham chiếu đó. Nếu đó là tham chiếu mạnh cuối cùng đến đối tượng trên heap, đối tượng trở thành *đủ điều kiện* để GC. GC sẽ thu hồi bộ nhớ bất đồng bộ vào một thời điểm không xác định trong tương lai.

### 3. Nhầm Lẫn StackOverflowError với OutOfMemoryError
- **StackOverflowError**: Gây ra bởi cạn kiệt stack luồng (thường do đệ quy vô hạn). Kích thước stack nhỏ (thường 1MB) và xử lý các khung gọi.
- **OutOfMemoryError: Java heap space**: Gây ra bởi cạn kiệt heap (tạo quá nhiều đối tượng đang hoạt động). Kích thước heap lớn hơn nhiều và xử lý lưu trữ dữ liệu.

### 4. Tin Rằng Trường Static Nằm Trong Metaspace
Kể từ Java 8, biến static (cả nguyên thủy và tham chiếu đối tượng) được phân bổ trên Java Heap, cụ thể là bên trong thực thể `java.lang.Class` của lớp đó. Metaspace chỉ lưu siêu dữ liệu mô tả bản thân lớp, không phải giá trị thực tế hoặc thực thể của biến static.

## Câu Hỏi Ôn Tập Phổ Biến

- Những khái niệm nào ở đây là quy tắc tại thời điểm biên dịch (compile-time)?
- Những khái niệm nào ảnh hưởng đến hành vi lúc chạy (runtime)?
- Những khái niệm nào có khả năng là bẫy trong phỏng vấn?

## Tại Sao Kiểu Nguyên Thủy Sống Trên Stack Hay Heap

JVM xác định vị trí phân bổ bộ nhớ vật lý của biến nguyên thủy hoàn toàn dựa trên phạm vi khai báo của chúng chứ không phải kiểu dữ liệu. Biến nguyên thủy cục bộ được khai báo trong một phương thức được lưu trực tiếp trong stack frame của luồng đó vì vòng đời LIFO của stack gắn liền với thực thi phương thức, cho phép phân bổ và thu hồi ngay lập tức. Ngược lại, biến nguyên thủy thực thể được khai báo là trường của lớp nằm trên Heap bên trong khối bộ nhớ được phân bổ cho đối tượng cha. Tương tự, biến nguyên thủy static là trường cấp lớp và được phân bổ trong đối tượng siêu dữ liệu Class nằm trên Heap. Vòng đời dựa trên frame của stack tránh được chi phí Thu Gom Rác cho biến cục bộ, nhưng các biến được phân bổ trên heap phải tồn tại xuyên suốt các lần gọi phương thức và do đó phụ thuộc vào Bộ Thu Gom Rác để dọn dẹp.

### Mô Hình Tư Duy
```
+-------------------------------------------------------------+
| Thread Stack Frame (LIFO)                                   |
| [ main() frame: localPrimitive = 100 ]                     |
| [ process() frame: tempVal = 42 ]                          |
+-------------------------------------------------------------+
                                | (Tham chiếu đến đối tượng trên heap)
                                v
+-------------------------------------------------------------+
| Heap Memory (Vòng đời động)                                |
| [ Container Object ] -------> [ instancePrimitive = 200 ]  |
| [ Class Metadata Object ] --> [ staticPrimitive = 300 ]    |
+-------------------------------------------------------------+
```

### Ví Dụ Code
```java
public class PrimitiveAllocation {
    static int staticPrimitive = 300; // Phân bổ trên Heap (trong đối tượng Class)
    int instancePrimitive = 200;      // Phân bổ trên Heap (trong phần dữ liệu đối tượng)

    public void methodScope() {
        int localPrimitive = 100;     // Phân bổ trên Stack (trong frame hiện tại)
        System.out.println(localPrimitive);      // Output: 100
        System.out.println(instancePrimitive);   // Output: 200
        System.out.println(staticPrimitive);     // Output: 300
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Phương thức được gọi → Stack frame được đẩy lên → Biến nguyên thủy cục bộ được phân bổ trên Stack → Phương thức thoát → Stack frame được lấy ra → Bộ nhớ cục bộ được thu hồi ngay lập tức không cần GC.

## Tại Sao Java Hoàn Toàn Là Pass-by-Value

Java hoàn toàn triển khai truyền theo giá trị (pass-by-value), nghĩa là JVM luôn sao chép giá trị thực sự được lưu trong một biến khi truyền nó làm tham số cho phương thức. Với kiểu dữ liệu nguyên thủy, giá trị được truyền là bản sao trực tiếp của các bit biểu diễn dữ liệu đó. Với biến tham chiếu (đối tượng), giá trị được truyền là bản sao của địa chỉ con trỏ (địa chỉ bộ nhớ) tham chiếu đến đối tượng trên heap. Do đó, gán lại tham số bên trong phương thức chỉ ghi đè bản sao cục bộ của con trỏ trên stack frame, không ảnh hưởng đến biến gốc của caller. Tuy nhiên, vì cả biến của caller và bản sao tham số đều trỏ đến cùng một vị trí đối tượng trên heap, việc thay đổi các trường của đối tượng bên trong phương thức sẽ thay đổi trạng thái heap được chia sẻ.

### Mô Hình Tư Duy
```
Stack Frame: main()               Stack Frame: modify()
+-----------------------+         +-----------------------+
| customerRef (0x7F2B)  | ----+   | parameterCopy (0x7F2B)|
+-----------------------+     |   +-----------------------+
                              |         |
                              +----+----+
                                   |
                                   v
                             Heap Memory
                             +---------------------------+
                             | Customer Object (0x7F2B)  |
                             | { name: "Alice" }         |
                             +---------------------------+
```

### Ví Dụ Code
```java
public class PassByValueEx {
    public static void main(String[] args) {
        int num = 10;
        modifyPrimitive(num);
        System.out.println("Primitive: " + num); // Output: Primitive: 10

        Customer c = new Customer("Alice");
        modifyObject(c);
        System.out.println("Object: " + c.name); // Output: Object: Bob
    }
    static void modifyPrimitive(int x) { x = 20; }
    static void modifyObject(Customer cust) {
        cust.name = "Bob"; // Thay đổi đối tượng trên heap
        cust = new Customer("Charlie"); // Gán lại bản sao cục bộ trên stack
    }
    static class Customer {
        String name;
        Customer(String n) { name = n; }
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Tham chiếu được truyền vào phương thức → JVM sao chép giá trị con trỏ vào stack frame mới → Tham số cục bộ được gán lại → Bản sao con trỏ thay đổi sang địa chỉ mới → Con trỏ của caller vẫn ở địa chỉ gốc → Tham chiếu gốc không bị ảnh hưởng.

## Liên Kết Tham Khảo

- https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.12.2 (Biến kiểu tham chiếu)
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.4.1 (Tham số hình thức)
