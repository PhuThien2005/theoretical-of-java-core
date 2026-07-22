# Thuật ngữ về Collections Framework (Collections Framework Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, hiểu lầm thường gặp và một ví dụ nhỏ.

## Khả năng lặp (Iterable)

`Iterable` là giao diện gốc (root interface) của phân cấp bộ sưu tập Java (ngoại trừ `Map`). Việc triển khai `Iterable<T>` cho phép một đối tượng làm mục tiêu của câu lệnh vòng lặp for-each cải tiến.

* **Tầm quan trọng**: Nó trừu tượng hóa cơ chế lặp, cho phép các nhà phát triển duyệt qua các cấu trúc dữ liệu tùy chỉnh bằng cú pháp sạch sẽ, dễ đọc mà không cần để lộ bố cục nút hoặc chỉ số mảng bên dưới.
* **Hiểu lầm thường gặp**: Nhầm lẫn `Iterable` với `Iterator`. `Iterable` đại diện cho một khả năng (đối tượng có thể lặp qua được và có phương thức `iterator()`), trong khi `Iterator` là đối tượng lưu trạng thái thực tế thực hiện việc duyệt qua thông qua `hasNext()` và `next()`.
* **Ví dụ nhỏ**:
  ```java
  Iterable<String> list = List.of("A", "B", "C");
  for (String s : list) { // Enhanced for-each loop works because List implements Iterable
      System.out.println(s);
  }
  ```

## Bộ sưu tập (Collection)

Một bộ sưu tập (collection) là giao diện gốc trong phân cấp bộ sưu tập, đại diện cho một nhóm các đối tượng được gọi là các phần tử. Một số bộ sưu tập cho phép các phần tử trùng lặp và số khác thì không; một số có thứ tự và số khác thì không.

* **Tầm quan trọng**: Nó định nghĩa giao ước chung (chẳng hạn như `add()`, `remove()`, `size()`, `contains()`, `isEmpty()`, và chuyển đổi sang mảng) mà tất cả các giao diện con (`List`, `Set`, `Queue`) phải hỗ trợ, tạo điều kiện thuận lợi cho các API đa hình.
* **Hiểu lầm thường gặp**: Nghĩ rằng `Map` triển khai `Collection`. Thực tế không phải vậy, vì bản đồ (map) xử lý các cặp khóa-giá trị (ánh xạ) thay vì các phần tử đơn lẻ, nghĩa là các phương thức API của chúng (`put()`, `get()`) không phù hợp với các chữ ký phương thức của `Collection`.
* **Ví dụ nhỏ**:
  ```java
  java.util.Collection<Integer> numbers = new java.util.ArrayList<>();
  numbers.add(10);
  numbers.add(20);
  System.out.println("Contains 10? " + numbers.contains(10)); // true
  ```

## Danh sách (List)

Một `List` là một bộ sưu tập có thứ tự (còn được gọi là một chuỗi sequence). Người sử dụng giao diện này có quyền kiểm soát chính xác vị trí chèn của mỗi phần tử trong danh sách, và các phần tử có thể được truy cập bằng chỉ số số nguyên của chúng.

* **Tầm quan trọng**: Nó là lựa chọn chuỗi mặc định khi thứ tự phải được bảo toàn, cho phép các phần tử trùng lặp, và yêu cầu định vị/tra cứu dựa trên chỉ số.
* **Hiểu lầm thường gặp**: Tin rằng `List` đảm bảo hiệu năng tốt cho tất cả các thao tác. `ArrayList` có độ phức tạp đọc là O(1) nhưng chèn ở giữa là O(N), trong khi `LinkedList` có độ phức tạp đọc là O(N) nhưng chèn ở đầu/cuối là O(1).
* **Ví dụ nhỏ**:
  ```java
  java.util.List<String> list = new java.util.ArrayList<>();
  list.add("First");
  list.add("Second");
  list.add("First"); // Duplicates allowed
  System.out.println("Item at index 1: " + list.get(1)); // Second
  ```

## Tập hợp (Set)

Một `Set` là một bộ sưu tập không chứa các phần tử trùng lặp. Nó mô phỏng sự trừu tượng hóa tập hợp toán học và thực thi tính duy nhất bằng cách sử dụng các quy tắc bằng nhau của đối tượng.

* **Tầm quan trọng**: Nó rất cần thiết cho các thao tác như lọc các phần tử duy nhất, xác minh tư cách thành viên một cách hiệu quả (O(1) trong `HashSet`), và tự động ngăn chặn các phần tử trùng lặp.
* **Hiểu lầm thường gặp**: Giả định rằng tất cả các tập hợp (set) đều không có thứ tự. Trong khi `HashSet` không đảm bảo thứ tự, `LinkedHashSet` bảo toàn thứ tự chèn, và `TreeSet` duy trì các phần tử được sắp xếp theo thứ tự tự nhiên của chúng hoặc một bộ so sánh `Comparator` tùy chỉnh.
* **Ví dụ nhỏ**:
  ```java
  java.util.Set<Integer> uniqueNums = new java.util.HashSet<>();
  uniqueNums.add(5);
  uniqueNums.add(5); // Ignored as duplicate
  System.out.println("Set size: " + uniqueNums.size()); // 1
  ```

## Hàng đợi (Queue)

`Queue` đại diện cho một bộ sưu tập được thiết kế để giữ các phần tử trước khi xử lý. Thông thường, các hàng đợi sắp xếp các phần tử theo kiểu FIFO (vào trước, ra trước), nhưng các hàng đợi ưu tiên (priority queue) sắp xếp các phần tử theo một bộ so sánh được cung cấp.

* **Tầm quan trọng**: Nó cung cấp một cấu trúc vùng đệm an toàn cho nhắn tin bất đồng bộ, luồng công việc giữa người sản xuất và người tiêu dùng (consumer-producer workflow), và các phép duyệt BFS trên cây/đồ thị.
* **Hiểu lầm thường gặp**: Nhầm lẫn giữa `poll()` và `remove()`, hoặc giữa `peek()` và `element()`. Các phương thức trước trả về các giá trị đặc biệt (`null` hoặc `false`) nếu hàng đợi trống, trong khi các phương thức sau ném ra ngoại lệ, có khả năng gây treo hệ thống.
* **Ví dụ nhỏ**:
  ```java
  java.util.Queue<String> queue = new java.util.LinkedList<>();
  queue.offer("Task 1");
  queue.offer("Task 2");
  System.out.println("Polled: " + queue.poll()); // Task 1
  ```

## Hàng đợi hai đầu (Deque)

`Deque` là một hàng đợi hai đầu hỗ trợ chèn và xóa phần tử ở cả hai đầu. Nó mở rộng `Queue` và có thể được sử dụng làm cả hàng đợi FIFO và ngăn xếp LIFO.

* **Tầm quan trọng**: Nó phục vụ như một sự thay thế hiện đại cho lớp `Stack` đã cũ, cung cấp hành vi ngăn xếp/hàng đợi nhanh hơn và sạch sẽ hơn (thông qua `ArrayDeque`).
* **Hiểu lầm thường gặp**: Sử dụng `Stack` thay vì `Deque`. `Stack` là lớp cũ, an toàn luồng (được đồng bộ hóa synchronized), và mở rộng `Vector`, làm lộ ra các thao tác chèn/xóa dựa trên chỉ số vốn vi phạm các nguyên tắc của ngăn xếp.
* **Ví dụ nhỏ**:
  ```java
  java.util.Deque<String> stack = new java.util.ArrayDeque<>();
  stack.push("Base");
  stack.push("Top");
  System.out.println("Popped: " + stack.pop()); // Top
  ```

## Bản đồ (Map)

Một `Map` là một đối tượng ánh xạ các khóa (key) sang các giá trị (value). Một bản đồ không thể chứa các khóa trùng lặp; mỗi khóa có thể ánh xạ đến tối đa một giá trị.

* **Tầm quan trọng**: Nó là cấu trúc chính để tra cứu từ điển nhanh chóng, lưu đệm (caching), và ghép cặp khóa-giá trị bằng các khóa logic.
* **Hiểu lầm thường gặp**: Tin rằng `Map` là một kiểu con của `Collection` hoặc `Iterable`. Các bản đồ được duyệt qua bằng cách truy cập các dạng xem bộ sưu tập của chúng: `keySet()`, `values()`, hoặc `entrySet()`.
* **Ví dụ nhỏ**:
  ```java
  java.util.Map<String, String> userRoles = new java.util.HashMap<>();
  userRoles.put("Alice", "Admin");
  userRoles.put("Bob", "User");
  System.out.println("Alice's role: " + userRoles.get("Alice")); // Admin
  ```

## Trình lặp (Iterator)

Một `Iterator` là một đối tượng cho phép duyệt tuần tự qua một bộ sưu tập, cung cấp các phương thức để lấy phần tử tiếp theo, kiểm tra xem còn phần tử nào không, và xóa các phần tử một cách an toàn.

* **Tầm quan trọng**: Nó cung cấp một cách đồng nhất để lặp qua các cấu trúc dữ liệu khác nhau trong khi cho phép xóa phần tử an toàn trong quá trình thực thi vòng lặp.
* **Hiểu lầm thường gặp**: Cố gắng sửa đổi một bộ sưu tập bằng các phương thức của chính nó (như `list.remove()`) trong quá trình lặp, điều này gây ra lỗi treo. Các sửa đổi phải được thực hiện thông qua `iterator.remove()`.
* **Ví dụ nhỏ**:
  ```java
  java.util.List<Integer> list = new java.util.ArrayList<>(java.util.List.of(1, 2, 3));
  java.util.Iterator<Integer> it = list.iterator();
  while (it.hasNext()) {
      if (it.next() == 2) {
          it.remove(); // Safely removes element 2 from list
      }
  }
  ```

## fail-fast (thất bại nhanh)

Thất bại nhanh (fail-fast) là một mẫu thiết kế hoặc hành vi mà hệ thống lập tức chấm dứt hoạt động và ném ra lỗi ngay khi phát hiện sửa đổi cấu trúc đối với trạng thái của nó trong quá trình duyệt qua.

* **Tầm quan trọng**: Nó giúp phát hiện sớm các lỗi lập trình bằng cách ngăn hệ thống tiếp tục ở trạng thái không ổn định, bị hỏng hoặc không xác định.
* **Hiểu lầm thường gặp**: Nghĩ rằng `fail-fast` ngăn cản lập trình đồng thời. Các trình lặp fail-fast không an toàn luồng và chỉ phát hiện các sửa đổi đồng thời trên cơ sở nỗ lực tối đa bằng cách sử dụng một bộ đếm sửa đổi.
* **Ví dụ nhỏ**:
  ```java
  java.util.List<String> list = new java.util.ArrayList<>(java.util.List.of("A", "B"));
  for (String s : list) {
      list.add("C"); // Triggers fail-fast behavior immediately on next loop step
  }
  ```

## ConcurrentModificationException

`ConcurrentModificationException` là một ngoại lệ thời gian chạy được ném ra khi một phương thức phát hiện sự sửa đổi đồng thời trên một đối tượng khi mà sự sửa đổi đó không được cho phép.

* **Tầm quan trọng**: Nó cảnh báo các nhà phát triển về các sửa đổi danh sách không đúng cách trên một luồng đơn bên trong các vòng lặp hoặc các thao tác ghi đồng thời đa luồng trên các bộ sưu tập tiêu chuẩn.
* **Hiểu lầm thường gặp**: Giả định rằng ngoại lệ này chỉ xảy ra trong môi trường đa luồng. Nó thường xuyên được ném ra trong các chương trình đơn luồng khi sửa đổi trực tiếp một bộ sưu tập trong vòng lặp for-each.
* **Ví dụ nhỏ**:
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
