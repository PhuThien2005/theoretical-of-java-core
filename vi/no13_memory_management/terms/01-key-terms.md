# Thuật Ngữ Quản Lý Bộ Nhớ Java (Java Memory Management Terms)

Dùng file này khi một từ trong phần lý thuyết cảm thấy quá súc tích. Mỗi thuật ngữ bao gồm ý nghĩa, tầm quan trọng, điểm nhầm lẫn và một ví dụ nhỏ.

## stack (ngăn xếp)

Vùng bộ nhớ riêng của từng luồng (thread-private), hoạt động theo kiểu LIFO (Last-In-First-Out — Vào sau ra trước), được JVM dùng để lưu trữ khung gọi phương thức (stack frame), biến cục bộ kiểu nguyên thủy, và các tham chiếu đối tượng trong quá trình thực thi.

Tại sao quan trọng: Lưu trữ ngữ cảnh thực thi phương thức trên stack cho phép phân bổ và thu hồi cực nhanh. Nó đảm bảo sự cô lập của từng luồng và ngăn các biến cục bộ của luồng này rò rỉ sang luồng khác, đảm bảo an toàn thực thi.

Nhầm lẫn phổ biến: Nhiều học viên cho rằng *tất cả* biến kiểu nguyên thủy đều nằm trên stack. Thực tế, chỉ có biến nguyên thủy cục bộ được khai báo bên trong phương thức mới nằm trên stack; các trường thực thể (instance field) — dù là kiểu nguyên thủy — đều nằm trên heap bên trong đối tượng chứa chúng.

Ví dụ nhỏ:
```java
void compute() {
    int localVal = 42; // Được lưu trực tiếp trong stack frame của compute().
}
```

## heap (vùng nhớ heap)

Vùng dữ liệu runtime được chia sẻ trong bộ nhớ JVM, nơi các thực thể lớp (đối tượng) và mảng được phân bổ động. Đây là mục tiêu chính của Bộ Thu Gom Rác (Garbage Collector).

Tại sao quan trọng: Heap cho phép các đối tượng tồn tại xuyên suốt nhiều lần gọi phương thức và được chia sẻ giữa các luồng. Phân bổ động là thiết yếu vì kích thước và thời gian tồn tại của đối tượng thường không biết tại thời điểm biên dịch.

Nhầm lẫn phổ biến: Tin rằng bộ nhớ heap được giải phóng ngay khi phương thức thoát hoặc khi tham chiếu ra khỏi phạm vi. Các đối tượng trên heap chỉ được thu hồi bất đồng bộ bởi Bộ Thu Gom Rác khi chúng không còn đến được từ bất kỳ GC Root nào.

Ví dụ nhỏ:
```java
Customer c = new Customer("Alice"); // 'c' ở trên stack; đối tượng Customer ở trên heap.
```

## metaspace

Vùng bộ nhớ native (được giới thiệu trong Java 8 để thay thế PermGen) lưu trữ siêu dữ liệu lớp (class metadata), định nghĩa phương thức, bảng phương thức, chi tiết annotation và bộ nhớ hằng số runtime.

Tại sao quan trọng: Chuyển siêu dữ liệu ra khỏi heap sang bộ nhớ native ngăn chặn lỗi `java.lang.OutOfMemoryError: PermGen space`. Metaspace có thể tự động mở rộng để phù hợp với các lớp được tải.

Nhầm lẫn phổ biến: Nghĩ rằng các trường static nằm trong Metaspace. Thực tế, các trường static (bao gồm tham chiếu và nguyên thủy) được phân bổ trên Java heap bên trong đối tượng `java.lang.Class` của lớp đó.

Ví dụ nhỏ:
```java
// Metaspace chứa siêu dữ liệu reflection/class cho MyClass:
Class<?> clazz = Class.forName("com.example.MyClass");
```

## strong reference (tham chiếu mạnh)

Kiểu tham chiếu mặc định trong Java. Bất kỳ tham chiếu đối tượng nào được tạo qua phép gán thông thường (ví dụ: `Object obj = new Object()`) đều là tham chiếu mạnh.

Tại sao quan trọng: Tham chiếu mạnh báo hiệu cho Bộ Thu Gom Rác biết rằng một đối tượng đang được sử dụng tích cực. Chừng nào đối tượng còn đến được thông qua chuỗi tham chiếu mạnh từ bất kỳ GC Root nào, nó sẽ không bao giờ bị thu gom, dù bộ nhớ đang ở trạng thái cực kỳ căng thẳng.

Nhầm lẫn phổ biến: Cho rằng xóa tham chiếu mạnh (ví dụ: `obj = null`) sẽ xóa đối tượng ngay lập tức. Nó chỉ đánh dấu đối tượng là đủ điều kiện để GC; việc thu hồi xảy ra sau đó trong một chu kỳ GC.

Ví dụ nhỏ:
```java
List<String> list = new ArrayList<>(); // 'list' là tham chiếu mạnh; ArrayList sẽ không bị thu gom.
```

## weak reference (tham chiếu yếu)

Tham chiếu đối tượng (được biểu diễn bởi `java.lang.ref.WeakReference`) không ngăn đối tượng tham chiếu bị thu hồi bởi Bộ Thu Gom Rác.

Tại sao quan trọng: Tham chiếu yếu ngăn rò rỉ bộ nhớ trong các cấu trúc dạng cache, nơi bạn muốn siêu dữ liệu liên kết được thu hồi tự động ngay khi đối tượng đích không còn được tham chiếu mạnh ở bất kỳ đâu khác.

Nhầm lẫn phổ biến: Quên kiểm tra `null` trước khi dereferencing kết quả `weakRef.get()`. Vì GC có thể xóa tham chiếu yếu bất cứ lúc nào, việc gọi phương thức trên đối tượng trả về mà không kiểm tra null sẽ gây `NullPointerException`.

Ví dụ nhỏ:
```java
WeakReference<Customer> weakCustomer = new WeakReference<>(new Customer("Bob"));
// Nếu không có tham chiếu mạnh nào tồn tại, chu kỳ GC tiếp theo sẽ xóa đối tượng Customer.
```

## garbage collection (thu gom rác)

Tiến trình nền tự động của JVM, giám sát bộ nhớ heap, xác định các đối tượng không còn đến được từ bất kỳ GC Root nào, và thu hồi bộ nhớ của chúng để ngăn cạn kiệt bộ nhớ.

Tại sao quan trọng: Thu hồi bộ nhớ tự động giải phóng lập trình viên khỏi việc theo dõi bộ nhớ thủ công, tránh lỗi double-free và con trỏ lơ lửng (dangling pointer) phổ biến trong các ngôn ngữ như C/C++.

Nhầm lẫn phổ biến: Tin rằng gọi `System.gc()` buộc JVM thực hiện thu gom rác hoàn toàn ngay lập tức. Đây chỉ là một gợi ý, và JVM có thể bỏ qua hoàn toàn tùy vào cấu hình và trạng thái hiện tại.

Ví dụ nhỏ:
```java
String data = new String("temp");
data = null; // Đối tượng String giờ không còn đến được và đủ điều kiện để thu gom rác.
```

## memory leak (rò rỉ bộ nhớ)

Trạng thái mà ứng dụng vô tình giữ lại tham chiếu mạnh đến các đối tượng không còn cần thiết, ngăn Bộ Thu Gom Rác thu hồi bộ nhớ của chúng.

Tại sao quan trọng: Rò rỉ bộ nhớ không được kiểm soát dần dần làm cạn kiệt bộ nhớ heap khả dụng, dẫn đến suy giảm hiệu suất nghiêm trọng, GC dừng thường xuyên, và cuối cùng là `java.lang.OutOfMemoryError` gây lỗi chết.

Nhầm lẫn phổ biến: Nghĩ rằng rò rỉ bộ nhớ không thể xảy ra trong Java vì có Thu Gom Rác tự động. Nếu một đường dẫn tham chiếu mạnh tồn tại từ GC Root (ví dụ: một collection static), GC không thể thu hồi bộ nhớ đó.

Ví dụ nhỏ:
```java
class Cache {
    private static final List<Object> leakList = new ArrayList<>();
    public void add(Object obj) { leakList.add(obj); } // Các đối tượng được thêm vào không bao giờ bị xóa, gây rò rỉ bộ nhớ.
}
```
