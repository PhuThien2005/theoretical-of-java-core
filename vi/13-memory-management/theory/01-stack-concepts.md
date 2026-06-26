# Quản lý bộ nhớ Java (Java Memory Management) - Phần 1

## Mục tiêu học tập (Learning Goal)

Tập tin này bao gồm một phần trọng tâm về **Quản lý bộ nhớ Java (Java Memory Management)**. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế, không phải là từ vựng riêng lẻ.

## Khái quát nội dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Stack` | Stack lưu trữ các khung phương thức (method frame), các biến cục bộ, và luồng gọi (call flow) cho mỗi luồng. |
| `Heap` | Heap lưu trữ các đối tượng được tạo ra ở thời gian chạy. |
| `Method Area / Metaspace` | Metaspace lưu trữ siêu dữ liệu lớp bên ngoài heap Java thông thường trong các JVM hiện đại. |
| `PC Register` | Thanh ghi PC theo dõi lệnh JVM hiện tại của một luồng. |
| `Native Method Stack` | Ngăn xếp lưu trữ các khung phương thức, biến cục bộ và luồng cuộc gọi cho mỗi luồng. |
| `Object lifecycle` | Vòng đời đối tượng bao gồm việc tạo ra, khả năng tiếp cận, sử dụng và cuối cùng là thu gom rác (Garbage collection). |
| `Reference variable` | Một biến tham chiếu lưu trữ một tham chiếu đến một đối tượng, chứ không phải dữ liệu của chính đối tượng đó. |
| `Strong reference` | Một tham chiếu mạnh giữ cho một đối tượng có thể tiếp cận được và ngăn nó bị thu gom rác. |

## Ghi chú chi tiết (Detailed Notes)

### Ngăn xếp (Stack)

Bộ nhớ Stack là riêng tư của từng luồng (thread-private) và được sử dụng để lưu trữ các khung thực thi phương thức (method execution frame), các biến cục bộ, các tham số và luồng gọi của một luồng duy nhất.

#### Quy tắc JVM
- Mỗi khi một luồng gọi một phương thức, một **Stack Frame** (Khung ngăn xếp) mới sẽ được đẩy (push) lên ngăn xếp của luồng đó.
- Khi phương thức hoàn thành (bằng lệnh `return` hoặc bằng cách ném ra một ngoại lệ không được xử lý), khung ngăn xếp của nó sẽ bị lấy ra (pop).
- Các biến cục bộ thuộc kiểu nguyên thủy (ví dụ: `int`, `double`, `boolean`) và các tham chiếu đến các đối tượng trên heap nằm trực tiếp trong khung ngăn xếp.
- Bộ nhớ Stack được phân bổ và giải phóng tự động theo thứ tự Vào sau - Ra trước (LIFO). Nó cực kỳ nhanh nhưng có kích thước cố định (được cấu hình qua `-Xss`).

#### Ví dụ mã nguồn: Vòng đời của khung ngăn xếp (Code Example: Stack Frame Lifecycle)
```java
public class StackDemo {
    public static void main(String[] args) {
        int a = 10; // Stored in main's stack frame
        int b = 20; // Stored in main's stack frame
        int result = add(a, b); // Pushes a new frame for add()
        System.out.println(result);
    } // main's frame is popped, stack is empty

    private static int add(int x, int y) {
        int sum = x + y; // Stored in add's stack frame
        return sum; 
    } // add's frame is popped, sum, x, and y are reclaimed
}
```

### Heap (Vùng nhớ Heap)

Bộ nhớ Heap là vùng dữ liệu chạy thời gian chạy dùng chung (shared runtime data area) nơi JVM phân bổ không gian cho tất cả các thực thể lớp (đối tượng) và mảng.

#### Quy tắc JVM
- Tất cả các đối tượng Java, bất kể chúng được tạo ra ở đâu, đều nằm trên Heap.
- Bộ nhớ heap được chia sẻ giữa tất cả các luồng, nghĩa là các đối tượng có thể được truy cập đồng thời (điều này yêu cầu đồng bộ hóa để đảm bảo an toàn luồng - thread-safety).
- Khác với bộ nhớ stack, việc phân bổ trên heap là động và không tuân theo LIFO.
- Các đối tượng trên heap không được thu hồi ngay lập tức khi một phương thức kết thúc. Thay vào đó, chúng vẫn tồn tại trên heap cho đến khi chúng không còn có thể tiếp cận được và Bộ thu gom rác (Garbage Collector - GC) thu hồi chúng.
- Kích thước Heap được cấu hình bằng các cờ JVM như `-Xms` (kích thước ban đầu) và `-Xmx` (kích thước tối đa). Việc vượt quá kích thước này dẫn đến lỗi `java.lang.OutOfMemoryError: Java heap space`.

#### Ví dụ mã nguồn: Phân bổ Stack so với Heap (Code Example: Stack vs. Heap Allocation)
```java
public class MemoryAllocationDemo {
    public static void main(String[] args) {
        int localPrimitive = 42; // Value 42 is stored on the Stack
        
        // The reference variable 'customer' is on the Stack.
        // The actual 'Customer' object is allocated on the Heap.
        Customer customer = new Customer("Alice", 30);
        
        modifyCustomer(customer);
    }

    private static void modifyCustomer(Customer cust) {
        // 'cust' is a copy of the reference variable, pointing to the same heap object.
        cust.setAge(31); // Mutates the object on the Heap
    }
}

class Customer {
    private String name; // Reference to String object on Heap
    private int age;     // Primitive field, stored on Heap as part of the Customer object

    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAge(int age) { this.age = age; }
}
```

### Vùng phương thức / Metaspace (Method Area / Metaspace)

Vùng phương thức (Method Area) là vùng bộ nhớ JVM dùng chung lưu trữ siêu dữ liệu cấp lớp (class-level metadata). Trong các JVM HotSpot hiện đại (Java 8+), vùng này được triển khai dưới dạng **Metaspace**.

#### Quy tắc JVM
- Metaspace lưu trữ thông tin cấu trúc lớp: định nghĩa lớp, bytecode của phương thức, mã hàm dựng, bể hằng số thời gian chạy (runtime constant pool), các chú thích và bảng phương thức.
- Từ Java 8, Metaspace được phân bổ ngoài bộ nhớ gốc native memory (off-heap) thay vì heap Java tiêu chuẩn. Điều này ngăn ngừa các vấn đề về giới hạn tải lớp vốn phổ biến với PermGen trước đây.
- Mặc dù theo mặc định nó có thể tự động tăng dung lượng, kích thước của nó vẫn có thể bị hạn chế bằng cách sử dụng `-XX:MaxMetaspaceSize`.
- Khi các trình nạp lớp (classloader) được thu gom rác, siêu dữ liệu lớp tương ứng của chúng trong Metaspace cũng sẽ được hủy tải (unload).

### Thanh ghi PC (PC Register)

Mỗi luồng có một Thanh ghi Bộ đếm Chương trình (Program Counter - PC) riêng biệt.

#### Quy tắc JVM
- Nếu luồng đang thực thi một phương thức Java phi bản địa (non-native), Thanh ghi PC sẽ giữ địa chỉ của lệnh JVM hiện đang được thực thi.
- Nếu luồng đang thực thi một phương thức bản địa (native method), giá trị của Thanh ghi PC là không xác định (undefined).
- Thanh ghi PC rất nhẹ và không tăng dung lượng. Nó cực kỳ quan trọng cho việc lập lịch luồng (thread scheduling) và chuyển đổi ngữ cảnh (context switching), cho phép các luồng tiếp tục thực thi từ chính xác lệnh nơi chúng bị tạm dừng.

### Ngăn xếp phương thức bản địa (Native Method Stack)

Ngăn xếp phương thức bản địa là một ngăn xếp riêng tư của từng luồng dành cho các phương thức được viết bằng các ngôn ngữ không phải Java (thường là C hoặc C++) được gọi thông qua Giao diện Bản địa Java (Java Native Interface - JNI).

#### Quy tắc JVM
- Khi một phương thức Java gọi một phương thức native, ngữ cảnh thực thi sẽ chuyển sang Ngăn xếp phương thức bản địa.
- Tương tự như stack Java, nó là riêng tư của từng luồng và có thể ném ra `StackOverflowError` nếu vượt quá độ sâu gọi phương thức native.

### Vòng đời đối tượng (Object lifecycle)

Vòng đời của một đối tượng bao gồm các giai đoạn riêng biệt sau:

1. **Khởi tạo (Creation)**: Bộ nhớ được phân bổ trên heap, các biến thực thể được khởi tạo và hàm dựng (constructor) được thực thi.
2. **Khả năng tiếp cận (Reachability)**: Đối tượng có thể được sử dụng bởi ứng dụng miễn là có một chuỗi các tham chiếu từ một luồng đang hoạt động (GC Root) đến đối tượng đó.
3. **Không thể tiếp cận / Đủ điều kiện nhận GC (Unreachability / GC Eligibility)**: Khi đối tượng không còn có thể tiếp cận được từ bất kỳ GC Root nào, nó trở nên đủ điều kiện để bị thu gom rác.
4. **Chạy phương thức hủy (Finalization)** (Đã bị loại bỏ - Deprecated): Nếu đối tượng định nghĩa phương thức `finalize()`, phương thức này có thể được chạy trước khi thu hồi.
5. **Thu hồi (Reclamation)**: Bộ thu gom rác thu hồi bộ nhớ trên heap.

### Biến tham chiếu (Reference variable)

Một biến tham chiếu là một biến lưu trữ địa chỉ bộ nhớ (tham chiếu) của một đối tượng trên heap, chứ không phải bản thân đối tượng đó.

#### Quy tắc JVM
- Khai báo một biến tham chiếu (ví dụ: `Customer c;`) sẽ dành chỗ trên stack hoặc heap cho một con trỏ, được khởi tạo thành `null`.
- Java hoàn toàn là **truyền tham trị (pass-by-value)**. Khi một tham chiếu đối tượng được truyền vào một phương thức, chính tham chiếu đó (con trỏ địa chỉ bộ nhớ) sẽ được sao chép. Biến tham chiếu ban đầu của bên gọi không thể bị thay đổi bởi phương thức, nhưng trạng thái của đối tượng mà nó trỏ tới thì có thể bị sửa đổi.

```java
public class PassByValueDemo {
    public static void main(String[] args) {
        Customer c1 = new Customer("Bob", 25);
        reassign(c1);
        System.out.println(c1.getName()); // Prints "Bob" - original reference was not changed
        
        modify(c1);
        System.out.println(c1.getName()); // Prints "Charlie" - object state was mutated
    }

    private static void reassign(Customer c) {
        c = new Customer("Dave", 40); // Only changes the local copied parameter 'c'
    }

    private static void modify(Customer c) {
        c.setName("Charlie"); // Modifies the object pointed to by the reference
    }
}
```

### Tham chiếu mạnh (Strong reference)

Một tham chiếu mạnh là kiểu tham chiếu mặc định trong Java. Bất kỳ đối tượng nào được tạo bằng phép gán tiêu chuẩn (ví dụ: `Object obj = new Object();`) đều được tham chiếu mạnh.

#### Quy tắc JVM
- Miễn là một đối tượng còn có thể tiếp cận được thông qua ít nhất một đường dẫn chứa các tham chiếu mạnh bắt đầu từ một GC Root, nó sẽ **không bao giờ** bị thu gom rác.
- Ngay cả khi JVM đang chạy trong tình trạng cực kỳ thiếu bộ nhớ và chuẩn bị ném ra `OutOfMemoryError`, nó cũng không thu hồi các đối tượng được tham chiếu mạnh.

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Nghĩ rằng các kiểu nguyên thủy luôn nằm trên Stack (Thinking Primitives Always Live on the Stack)
Một cái bẫy phỏng vấn rất phổ biến là phát biểu rằng tất cả các biến kiểu nguyên thủy đều nằm trên stack.
**Quy tắc:** Vị trí của một biến nguyên thủy được quyết định hoàn toàn bởi *nơi* nó được khai báo:
- **Biến nguyên thủy cục bộ** (khai báo bên trong phương thức) nằm trên **Stack**.
- **Biến nguyên thủy thực thể** (khai báo làm trường của lớp) nằm trên **Heap** như một phần của đối tượng chứa nó.
- **Biến nguyên thủy tĩnh** (khai báo làm trường static) nằm trên **Heap** bên trong đối tượng `java.lang.Class`.

### 2. Giả định `obj = null` giải phóng bộ nhớ ngay lập tức (Assuming obj = null Instantly Frees Memory)
Việc gán một biến tham chiếu thành `null` không kích hoạt việc thu gom rác ngay lập tức.
**Quy tắc:** Gán lại một tham chiếu thành `null` chỉ đơn giản là cắt đứt kết nối tham chiếu cụ thể đó. Nếu đó là tham chiếu mạnh cuối cùng dẫn đến đối tượng trên heap, đối tượng đó sẽ trở nên *đủ điều kiện* để bị GC thu gom. GC sẽ thu hồi bộ nhớ một cách bất đồng bộ vào một thời điểm không thể đoán trước trong tương lai.

### 3. Nhầm lẫn StackOverflowError với OutOfMemoryError (Confusing StackOverflowError with OutOfMemoryError)
- **StackOverflowError**: Gây ra bởi sự cạn kiệt ngăn xếp của luồng (thường gặp khi đệ quy vô hạn). Kích thước stack khá nhỏ (thường là 1MB) và xử lý các khung cuộc gọi phương thức.
- **OutOfMemoryError: Java heap space**: Gây ra bởi sự cạn kiệt bộ nhớ heap (tạo ra quá nhiều đối tượng đang hoạt động). Kích thước heap lớn hơn rất nhiều và xử lý việc lưu trữ dữ liệu.

### 4. Tin rằng các trường tĩnh nằm trong Metaspace (Believing Static Fields Live in Metaspace)
Từ Java 8, các biến tĩnh (cả kiểu nguyên thủy và tham chiếu đối tượng) được phân bổ trên Java Heap, cụ thể là bên trong thực thể `java.lang.Class` của lớp đó. Metaspace chỉ lưu trữ siêu dữ liệu mô tả bản thân lớp đó, chứ không lưu trữ các giá trị thực tế hoặc thực thể của các biến tĩnh.

## Các câu hỏi ôn tập thường gặp (Common Review Prompts)

- Khái niệm nào ở đây là quy tắc thời gian biên dịch (compile-time rule)?
- Khái niệm nào ở đây ảnh hưởng đến hành vi thời gian chạy (runtime behavior)?
- Khái niệm nào ở đây dễ là bẫy phỏng vấn?

## Tại sao các kiểu Nguyên thủy nằm trên Stack hoặc Heap (Why Primitives Live on the Stack or Heap)

JVM quyết định phân bổ bộ nhớ vật lý của các biến nguyên thủy hoàn toàn dựa trên phạm vi khai báo (scope) của chúng chứ không phải kiểu dữ liệu của chúng. Các biến nguyên thủy cục bộ được khai báo bên trong một phương thức được lưu trữ trực tiếp bên trong khung ngăn xếp của luồng đó vì vòng đời LIFO của stack gắn liền với việc thực thi phương thức, cho phép phân bổ và giải phóng bộ nhớ tức thời. Ngược lại, các biến nguyên thủy thực thể được khai báo là các trường của một lớp sẽ nằm trên Heap bên trong khối bộ nhớ được phân bổ cho đối tượng cha. Tương tự, các biến nguyên thủy tĩnh là các trường cấp lớp và được phân bổ bên trong đối tượng siêu dữ liệu Lớp (Class metadata object) nằm trên Heap. Vòng đời dựa trên khung ngăn xếp của stack tránh được chi phí Thu gom rác cho các biến cục bộ, nhưng các biến được phân bổ trên heap phải tồn tại qua các lần gọi phương thức và do đó dựa vào Bộ thu gom rác để dọn dẹp.

### Mô hình tư duy (Mental Model)
```
+-------------------------------------------------------------+
| Thread Stack Frame (LIFO)                                   |
| [ main() frame: localPrimitive = 100 ]                     |
| [ process() frame: tempVal = 42 ]                          |
+-------------------------------------------------------------+
                                | (References heap object)
                                v
+-------------------------------------------------------------+
| Heap Memory (Dynamic Lifecycle)                            |
| [ Container Object ] -------> [ instancePrimitive = 200 ]   |
| [ Class Metadata Object ] --> [ staticPrimitive = 300 ]     |
+-------------------------------------------------------------+
```

### Ví dụ mã nguồn (Code Example)
```java
public class PrimitiveAllocation {
    static int staticPrimitive = 300; // Allocated on the Heap (inside Class object)
    int instancePrimitive = 200;      // Allocated on the Heap (inside object payload)

    public void methodScope() {
        int localPrimitive = 100;     // Allocated on the Stack (inside current frame)
        System.out.println(localPrimitive);      // Output: 100
        System.out.println(instancePrimitive);   // Output: 200
        System.out.println(staticPrimitive);     // Output: 300
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Phương thức được gọi
  → Khung ngăn xếp được đẩy (push)
  → Biến nguyên thủy cục bộ được phân bổ trên Stack
  → Phương thức thoát
  → Khung ngăn xếp được lấy ra (pop)
  → Bộ nhớ cục bộ được thu hồi ngay lập tức mà không tốn chi phí GC.
```


## Tại sao Java hoàn toàn là Truyền tham trị (Why Java Is Strictly Pass-by-Value)

Java thực thi cơ chế truyền tham trị (pass-by-value) một cách nghiêm ngặt, nghĩa là JVM luôn sao chép giá trị thực tế được lưu trữ trong một biến khi truyền nó làm tham số cho một phương thức. Đối với các kiểu dữ liệu nguyên thủy, giá trị được truyền là một bản sao trực tiếp của các bit đại diện cho chính dữ liệu đó. Đối với các biến tham chiếu (đối tượng), giá trị được truyền là một bản sao của địa chỉ con trỏ (địa chỉ bộ nhớ) tham chiếu đến đối tượng trên heap. Do đó, việc gán lại tham số bên trong phương thức chỉ đơn thuần là ghi đè lên bản sao cục bộ của con trỏ trên khung ngăn xếp, không ảnh hưởng đến biến ban đầu của bên gọi. Tuy nhiên, vì cả biến của bên gọi và bản sao tham số đều trỏ đến cùng một vị trí đối tượng trên heap, việc đột biến (mutate) các trường của đối tượng bên trong phương thức sẽ làm thay đổi trạng thái heap được chia sẻ chung.

### Mô hình tư duy (Mental Model)
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

### Ví dụ mã nguồn (Code Example)
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
        cust.name = "Bob"; // Mutates heap object
        cust = new Customer("Charlie"); // Reassigns local stack copy
    }
    static class Customer {
        String name;
        Customer(String n) { name = n; }
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Tham chiếu được truyền vào phương thức
  → JVM sao chép giá trị con trỏ lên khung ngăn xếp mới
  → Tham số cục bộ được gán lại
  → Con trỏ sao chép thay đổi sang địa chỉ mới
  → Con trỏ của bên gọi vẫn ở địa chỉ ban đầu
  → Tham chiếu ban đầu không bị ảnh hưởng.
```


## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.12.2 (Variables of Reference Type)
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.4.1 (Formal Parameters)
