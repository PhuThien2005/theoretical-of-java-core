# Thuật ngữ Collections Framework (Collections Framework Terms)

Sử dụng tài liệu này khi một từ ngữ trong lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Iterable

Iterable là giao diện gốc (root interface) của hệ thống phân cấp collections trong Java (ngoại trừ `Map`). Việc triển khai `Iterable<T>` cho phép một đối tượng trở thành mục tiêu của câu lệnh vòng lặp for-each mở rộng.

- **Tầm quan trọng**: Nó trừu tượng (Abstraction) hóa cơ chế lặp, cho phép các nhà phát triển duyệt qua các cấu trúc dữ liệu tùy chỉnh bằng cú pháp sạch sẽ, dễ đọc mà không cần để lộ bố cục nút (node) hoặc chỉ mục mảng bên dưới.
- **Hiểu lầm phổ biến**: Nhầm lẫn giữa `Iterable` và `Iterator`. `Iterable` đại diện cho một khả năng (đối tượng có thể được lặp qua và có phương thức `iterator()`), trong có `Iterator` là đối tượng có trạng thái thực sự thực hiện việc duyệt qua thông qua các phương thức `hasNext()` và `next()`.
- **Ví dụ nhỏ**:
  ```java
  Iterable<String> list = List.of("A", "B", "C");
  for (String s : list) { // Enhanced for-each loop works because List implements Iterable
      System.out.println(s);
  }
  ```

## Collection

Collection là giao diện gốc trong phân cấp tập hợp đại diện cho một nhóm các đối tượng được gọi là các phần tử (elements). Một số bộ sưu tập cho phép chứa các phần tử trùng lặp và một số bộ sưu tập khác thì không; một số có thứ tự và một số khác thì không.

- **Tầm quan trọng**: Nó định nghĩa hợp đồng chung (chẳng hạn như `add()`, `remove()`, `size()`, `contains()`, `isEmpty()`, và chuyển đổi sang mảng) mà tất cả các giao diện con (`List`, `Set`, `Queue`) phải hỗ trợ, tạo điều kiện cho các API đa hình.
- **Hiểu lầm phổ biến**: Nghĩ rằng `Map` triển khai `Collection`. Thực tế không phải vậy, vì các bản đồ (maps) xử lý các cặp khóa-giá trị (key-value) chứ không phải các phần tử đơn lẻ, có nghĩa là các phương thức API của chúng (`put()`, `get()`) không khớp với chữ ký phương thức của `Collection`.
- **Ví dụ nhỏ**:
  ```java
  java.util.Collection<Integer> numbers = new java.util.ArrayList<>();
  numbers.add(10);
  numbers.add(20);
  System.out.println("Contains 10? " + numbers.contains(10)); // true
  ```

## List

Một List là một tập hợp có thứ tự (còn được gọi là một chuỗi - sequence). Người sử dụng giao diện này có quyền kiểm soát chính xác vị trí chèn của từng phần tử trong danh sách và có thể truy cập các phần tử bằng chỉ mục số nguyên của chúng.

- **Tầm quan trọng**: Đây là lựa chọn chuỗi mặc định khi thứ tự phần tử cần được bảo toàn, cho phép trùng lặp và yêu cầu định vị/tra cứu dựa trên chỉ mục.
- **Hiểu lầm phổ biến**: Tin rằng `List` đảm bảo hiệu năng tốt cho mọi hoạt động. `ArrayList` có độ phức tạp đọc là O(1) nhưng chèn ở giữa là O(N), trong khi `LinkedList` có độ phức tạp đọc là O(N) nhưng chèn ở đầu/cuối là O(1).
- **Ví dụ nhỏ**:
  ```java
  java.util.List<String> list = new java.util.ArrayList<>();
  list.add("First");
  list.add("Second");
  list.add("First"); // Duplicates allowed
  System.out.println("Item at index 1: " + list.get(1)); // Second
  ```

## Set

Một Set là một tập hợp không chứa các phần tử trùng lặp. Nó mô phỏng lại khái niệm tập hợp toán học và thực thi tính duy nhất bằng cách sử dụng các quy tắc bằng nhau của đối tượng (equality rules).

- **Tầm quan trọng**: Nó rất cần thiết cho các hoạt động như lọc các phần tử duy nhất, xác minh tư cách thành viên một cách hiệu quả (O(1) trong `HashSet`) và tự động ngăn chặn việc trùng lặp phần tử.
- **Hiểu lầm phổ biến**: Giả định rằng tất cả các set đều không có thứ tự. Trong khi `HashSet` không đưa ra đảm bảo nào về thứ tự sắp xếp, thì `LinkedHashSet` bảo toàn thứ tự chèn phần tử, và `TreeSet` duy trì các phần tử được sắp xếp theo thứ tự tự nhiên của chúng hoặc một bộ so sánh `Comparator` tùy chỉnh.
- **Ví dụ nhỏ**:
  ```java
  java.util.Set<Integer> uniqueNums = new java.util.HashSet<>();
  uniqueNums.add(5);
  uniqueNums.add(5); // Ignored as duplicate
  System.out.println("Set size: " + uniqueNums.size()); // 1
  ```

## Queue

Queue đại diện cho một tập hợp được thiết kế để chứa các phần tử trước khi xử lý. Thông thường, các hàng đợi sắp xếp các phần tử theo cơ chế FIFO (vào trước ra trước), nhưng hàng đợi ưu tiên (priority queues) sắp xếp các phần tử theo một bộ so sánh được cung cấp.

- **Tầm quan trọng**: Nó cung cấp một cấu trúc vùng đệm an toàn cho nhắn tin bất đồng bộ (asynchronous messaging), quy trình làm việc giữa nhà sản xuất và người tiêu dùng (consumer-producer workflows) và các thuật toán duyệt cây/đồ thị theo chiều rộng (BFS).
- **Hiểu lầm phổ biến**: Nhầm lẫn giữa `poll()` và `remove()`, hoặc `peek()` và `element()`. Các phương thức trước sẽ trả về các giá trị đặc biệt (`null` hoặc `false`) nếu hàng đợi trống, trong khi các phương thức sau ném ra ngoại lệ, có khả năng gây sập hệ thống.
- **Ví dụ nhỏ**:
  ```java
  java.util.Queue<String> queue = new java.util.LinkedList<>();
  queue.offer("Task 1");
  queue.offer("Task 2");
  System.out.println("Polled: " + queue.poll()); // Task 1
  ```

## Deque

Deque là một hàng đợi hai đầu hỗ trợ chèn và xóa phần tử ở cả hai đầu. Nó kế thừa `Queue` và có thể được sử dụng làm cả hàng đợi FIFO và ngăn xếp LIFO (vào sau ra trước).

- **Tầm quan trọng**: Nó hoạt động như một sự thay thế hiện đại cho lớp `Stack` đã lỗi thời, cung cấp hành vi ngăn xếp/hàng đợi nhanh hơn và sạch sẽ hơn (thông qua `ArrayDeque`).
- **Hiểu lầm phổ biến**: Sử dụng `Stack` thay vì `Deque`. Lớp `Stack` là một lớp cũ (legacy), an toàn luồng (Thread) bằng cách đồng bộ hóa (`synchronized`), kế thừa `Vector`, điều này để lộ các thao tác chèn/xóa dựa trên chỉ mục vốn vi phạm các nguyên tắc hoạt động của ngăn xếp.
- **Ví dụ nhỏ**:
  ```java
  java.util.Deque<String> stack = new java.util.ArrayDeque<>();
  stack.push("Base");
  stack.push("Top");
  System.out.println("Popped: " + stack.pop()); // Top
  ```

## Map

Một Map là một đối tượng ánh xạ các khóa (keys) tới các giá trị (values). Một bản đồ không thể chứa các khóa trùng lặp; mỗi khóa có thể ánh xạ tới tối đa một giá trị.

- **Tầm quan trọng**: Đây là cấu trúc chính để tra cứu từ điển nhanh, lưu bộ nhớ đệm (caching) và ghép cặp khóa-giá trị bằng các khóa logic.
- **Hiểu lầm phổ biến**: Tin rằng `Map` là một kiểu con của `Collection` hoặc `Iterable`. Các bản đồ được duyệt qua bằng cách truy cập các dạng hiển thị tập hợp của chúng: `keySet()`, `values()`, hoặc `entrySet()`.
- **Ví dụ nhỏ**:
  ```java
  java.util.Map<String, String> userRoles = new java.util.HashMap<>();
  userRoles.put("Alice", "Admin");
  userRoles.put("Bob", "User");
  System.out.println("Alice's role: " + userRoles.get("Alice")); // Admin
  ```

## Iterator

Một Iterator là một đối tượng cho phép duyệt tuần tự qua một tập hợp, cung cấp các phương thức để lấy phần tử tiếp theo, kiểm tra xem còn phần tử nào nữa không và xóa phần tử một cách an toàn.

- **Tầm quan trọng**: Nó cung cấp một cách thống nhất để lặp qua các cấu trúc dữ liệu khác nhau trong khi cho phép xóa phần tử một cách an toàn trong quá trình thực thi vòng lặp.
- **Hiểu lầm phổ biến**: Cố gắng sửa đổi một tập hợp bằng các phương thức của chính nó (như `list.remove()`) trong khi lặp, điều này sẽ gây sập chương trình. Các sửa đổi bắt buộc phải được thực hiện thông qua `iterator.remove()`.
- **Ví dụ nhỏ**:
  ```java
  java.util.List<Integer> list = new java.util.ArrayList<>(java.util.List.of(1, 2, 3));
  java.util.Iterator<Integer> it = list.iterator();
  while (it.hasNext()) {
      if (it.next() == 2) {
          it.remove(); // Safely removes element 2 from list
      }
  }
  ```

## fail-fast

fail-fast là một mẫu thiết kế hoặc hành vi trong đó hệ thống lập tức chấm dứt hoạt động và ném ra lỗi ngay khi phát hiện ra sự thay đổi cấu trúc trạng thái của nó trong quá trình duyệt qua phần tử.

- **Tầm quan trọng**: Nó giúp phát hiện sớm các lỗi lập trình bằng cách ngăn hệ thống tiếp tục chạy ở trạng thái không ổn định, bị sai lệch hoặc không tất định.
- **Hiểu lầm phổ biến**: Nghĩ rằng `fail-fast` ngăn cản lập trình đồng thời (concurrent programming). Các iterator fail-fast không an toàn luồng và chỉ phát hiện các sửa đổi đồng thời trên cơ sở nỗ lực tốt nhất (best-effort) bằng cách sử dụng bộ đếm sửa đổi.
- **Ví dụ nhỏ**:
  ```java
  java.util.List<String> list = new java.util.ArrayList<>(java.util.List.of("A", "B"));
  for (String s : list) {
      list.add("C"); // Triggers fail-fast behavior immediately on next loop step
  }
  ```

## ConcurrentModificationException

ConcurrentModificationException là một ngoại lệ lúc chạy (runtime exception) được ném ra khi một phương thức phát hiện sự sửa đổi đồng thời trên một đối tượng khi sự sửa đổi đó không được phép.

- **Tầm quan trọng**: Nó cảnh báo các nhà phát triển về việc sửa đổi danh sách không đúng cách trên một luồng đơn bên trong các vòng lặp hoặc các thao tác ghi đa luồng đồng thời trên các tập hợp tiêu chuẩn.
- **Hiểu lầm phổ biến**: Giả định rằng ngoại lệ này chỉ xảy ra trong môi trường đa luồng. Nó thường xuyên bị ném ra trong các chương trình đơn luồng khi sửa đổi trực tiếp một tập hợp trong vòng lặp for-each.
- **Ví dụ nhỏ**:
  ```java
  java.util.List<String> list = new java.util.ArrayList<>(java.util.List.of("X", "Y"));
  java.util.Iterator<String> it = list.iterator();
  list.add("Z"); // Structural modification
  try {
      it.next(); // Throws ConcurrentModificationException due to mismatch in modCount
  } catch (java.util.ConcurrentModificationException e) {
      System.out.println("Exception caught!");
  }
  ```
