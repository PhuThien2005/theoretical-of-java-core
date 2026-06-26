# Thuật ngữ Quản lý bộ nhớ Java (Java Memory Management Terms)

Sử dụng tập tin này khi một từ trong lý thuyết có cảm giác quá ngắn gọn. Mỗi thuật ngữ đều có ý nghĩa, tầm quan trọng, điểm dễ nhầm lẫn và một ví dụ nhỏ.

## stack (ngăn xếp)

Một vùng nhớ riêng tư của luồng (thread-private), hoạt động theo cơ chế LIFO (Vào sau - Ra trước / Last-In-First-Out), được JVM sử dụng để lưu trữ các khung cuộc gọi phương thức (method call frame), các biến cục bộ kiểu nguyên thủy (primitive) và các tham chiếu đối tượng trong quá trình thực thi chương trình.

Tại sao nó quan trọng: Việc lưu trữ ngữ cảnh thực thi phương thức trên stack cho phép phân bổ và giải phóng bộ nhớ cực kỳ nhanh chóng. Nó đảm bảo sự cô lập luồng và ngăn các biến cục bộ của luồng rò rỉ sang các luồng khác, mang lại sự an toàn khi thực thi.

Điểm nhầm lẫn phổ biến: Nhiều người học giả định rằng *tất cả* các biến kiểu nguyên thủy đều nằm trên stack. Trên thực tế, chỉ các biến nguyên thủy cục bộ được khai báo bên trong các phương thức mới nằm trên stack; các trường thực thể (instance field) (ngay cả kiểu nguyên thủy) đều nằm trên heap bên trong đối tượng chứa chúng.

Ví dụ nhỏ:
```java
void compute() {
    int localVal = 42; // Được lưu trữ trực tiếp trong stack frame của compute().
}
```

## heap (vùng nhớ heap)

Một vùng dữ liệu chạy thời gian chạy dùng chung (shared runtime data area) trong bộ nhớ JVM nơi các thực thể lớp (đối tượng) và mảng được phân bổ động. Đây là mục tiêu chính của Bộ thu gom rác (Garbage Collector - GC).

Tại sao nó quan trọng: Heap cho phép các đối tượng tồn tại qua các lần gọi phương thức và được chia sẻ giữa các luồng. Việc phân bổ động là cực kỳ quan trọng vì kích thước và thời gian tồn tại của đối tượng thường không được biết trước ở thời điểm biên dịch.

Điểm nhầm lẫn phổ biến: Tin rằng bộ nhớ heap sẽ được giải phóng ngay khi một phương thức kết thúc hoặc khi một tham chiếu đi ra ngoài phạm vi (out of scope). Các đối tượng trên heap chỉ được thu hồi một cách bất đồng bộ bởi Bộ thu gom rác khi chúng không còn có thể tiếp cận được từ bất kỳ GC Root nào.

Ví dụ nhỏ:
```java
Customer c = new Customer("Alice"); // 'c' nằm trên stack; đối tượng Customer nằm trên heap.
```

## metaspace

Một vùng nhớ gốc (native memory) (được giới thiệu từ Java 8 để thay thế cho PermGen) dùng để lưu trữ siêu dữ liệu lớp (class metadata), định nghĩa phương thức, bảng phương thức (method table), chi tiết chú thích (annotation) và bể hằng số thời gian chạy (runtime constant pool).

Tại sao nó quan trọng: Việc chuyển siêu dữ liệu ra khỏi heap sang bộ nhớ native giúp ngăn ngừa hiện tượng sập ứng dụng do giới hạn của `java.lang.OutOfMemoryError: PermGen space`. Metaspace có thể tăng dung lượng động để phù hợp với các lớp được tải vào.

Điểm nhầm lẫn phổ biến: Nghĩ rằng các trường tĩnh (static field) nằm trong Metaspace. Thực tế, các trường tĩnh (bao gồm cả tham chiếu và biến nguyên thủy) được phân bổ trên Java heap bên trong đối tượng `java.lang.Class` đại diện cho lớp được tải đó.

Ví dụ nhỏ:
```java
// Metaspace giữ siêu dữ liệu phản chiếu/lớp cho MyClass:
Class<?> clazz = Class.forName("com.example.MyClass");
```

## strong reference (tham chiếu mạnh)

Kiểu tham chiếu mặc định trong Java. Bất kỳ tham chiếu đối tượng nào được tạo ra thông qua phép gán tiêu chuẩn (ví dụ: `Object obj = new Object()`) đều là một tham chiếu mạnh.

Tại sao nó quan trọng: Tham chiếu mạnh báo hiệu cho Bộ thu gom rác biết rằng đối tượng đang được sử dụng tích cực. Miễn là một đối tượng còn có thể tiếp cận được thông qua một chuỗi các tham chiếu mạnh từ bất kỳ GC Root nào, nó sẽ không bao giờ bị thu gom, ngay cả khi bộ nhớ bị quá tải nghiêm trọng.

Điểm nhầm lẫn phổ biến: Giả định rằng việc xóa một tham chiếu mạnh (ví dụ: `obj = null`) sẽ lập tức xóa đối tượng. Việc đó chỉ đánh dấu đối tượng là đủ điều kiện để GC thu gom; việc thu hồi thực tế diễn ra sau đó trong một chu kỳ GC.

Ví dụ nhỏ:
```java
List<String> list = new ArrayList<>(); // 'list' là một tham chiếu mạnh; ArrayList sẽ không bị thu gom.
```

## weak reference (tham chiếu yếu)

Một tham chiếu đối tượng (được đại diện bởi `java.lang.ref.WeakReference`) không ngăn cản đối tượng được tham chiếu của nó bị thu hồi bởi Bộ thu gom rác.

Tại sao nó quan trọng: Tham chiếu yếu ngăn ngừa rò rỉ bộ nhớ trong các cấu trúc giống như bộ nhớ đệm (cache), nơi bạn muốn siêu dữ liệu liên quan được tự động thu hồi ngay khi đối tượng mục tiêu không còn có thể tiếp cận mạnh (strongly reachable) ở bất kỳ nơi nào khác.

Điểm nhầm lẫn phổ biến: Quên kiểm tra giá trị `null` trước khi giải tham chiếu `weakRef.get()`. Bởi vì GC có thể xóa một tham chiếu yếu bất kỳ lúc nào, việc gọi một phương thức trên đối tượng được trả về mà không kiểm tra null sẽ kích hoạt một `NullPointerException`.

Ví dụ nhỏ:
```java
WeakReference<Customer> weakCustomer = new WeakReference<>(new Customer("Bob"));
// Nếu không tồn tại tham chiếu mạnh nào khác, chu kỳ GC tiếp theo sẽ xóa đối tượng Customer.
```

## garbage collection (thu gom rác)

Quá trình nền tự động của JVM theo dõi bộ nhớ heap, xác định các đối tượng không còn có thể tiếp cận từ bất kỳ GC Root nào và thu hồi bộ nhớ của chúng để ngăn chặn cạn kiệt bộ nhớ.

Tại sao nó quan trọng: Việc tự động thu hồi bộ nhớ giải phóng các nhà phát triển khỏi việc theo dõi bộ nhớ thủ công, tránh được các lỗi giải phóng bộ nhớ hai lần (double-free) và con trỏ lơ lửng (dangling pointer) vốn phổ biến trong các ngôn ngữ như C/C++.

Điểm nhầm lẫn phổ biến: Tin rằng việc gọi `System.gc()` sẽ buộc JVM ngay lập tức thực hiện một đợt thu gom rác toàn phần (full GC). Đây chỉ là một lời gợi ý, JVM có thể bỏ qua hoàn toàn dựa trên cấu hình và trạng thái hoạt động của nó.

Ví dụ nhỏ:
```java
String data = new String("temp");
data = null; // Đối tượng String bây giờ không thể tiếp cận và đủ điều kiện để thu gom rác.
```

## memory leak (rò rỉ bộ nhớ)

Trạng thái ứng dụng vô tình giữ lại các tham chiếu mạnh đến các đối tượng không còn cần thiết, ngăn cản Bộ thu gom rác thu hồi bộ nhớ của chúng.

Tại sao nó quan trọng: Các lỗi rò rỉ bộ nhớ nếu không được kiểm soát sẽ dần dần làm cạn kiệt bộ nhớ heap có sẵn, dẫn đến suy giảm hiệu năng nghiêm trọng, các đợt tạm dừng GC thường xuyên, và cuối cùng là lỗi nghiêm trọng `java.lang.OutOfMemoryError`.

Điểm nhầm lẫn phổ biến: Nghĩ rằng rò rỉ bộ nhớ không thể xảy ra trong Java vì đã có cơ chế thu gom rác tự động. Nếu một đường dẫn tham chiếu mạnh tồn tại từ một GC Root (ví dụ: một bộ sưu tập static), GC không thể thu hồi bộ nhớ đó.

Ví dụ nhỏ:
```java
class Cache {
    private static final List<Object> leakList = new ArrayList<>();
    public void add(Object obj) { leakList.add(obj); } // Các đối tượng được thêm vào không bao giờ bị xóa bỏ, gây rò rỉ bộ nhớ.
}
```
