# Các Thuật Ngữ Câu Hỏi Phỏng Vấn Java Core Thường Gặp (Common Java Core Interview Questions Terms)

Sử dụng tài liệu này khi một từ khóa trong phần lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ đi kèm.

## JDK

Java Development Kit (JDK) là một môi trường phát triển phần mềm được sử dụng để phát triển các ứng dụng Java. Nó bao gồm JRE (Java Runtime Environment) cùng với các công cụ phát triển như trình biên dịch (`javac`), trình gỡ lỗi (`jdb`) và các công cụ tạo tài liệu.

* **Tầm quan trọng:** Các nhà phát triển cần JDK để biên dịch mã nguồn (`.java`) thành mã byte (`.class`). Nếu không có nó, bạn không thể xây dựng các dự án Java cục bộ hoặc chạy các công cụ phân tích hiệu năng (profiling).
* **Điểm dễ nhầm lẫn:** Nhầm lẫn JDK với JRE. Người dùng cuối (end-users) chỉ cần JRE để chạy các chương trình Java, trong khi các nhà phát triển yêu cầu toàn bộ JDK để biên dịch và gỡ lỗi.
* **Ví dụ nhỏ:** Việc biên dịch một lớp bằng lệnh `javac HelloWorld.java` từ dòng lệnh yêu cầu máy tính phải được cài đặt JDK.

## JRE

Java Runtime Environment (JRE) là một phần của Java SDK chứa JVM, các thư viện cốt lõi và các tệp hỗ trợ khác cần thiết để chạy các chương trình Java đã được biên dịch.

* **Tầm quan trọng:** Nó cung cấp các yêu cầu tối thiểu để thực thi một ứng dụng Java trên máy tính của khách hàng.
* **Điểm dễ nhầm lẫn:** Giả định rằng JRE có thể biên dịch mã nguồn. Nó chỉ chứa JVM và các thư viện lớp nhưng thiếu trình biên dịch `javac` và các công cụ phát triển khác.
* **Ví dụ nhỏ:** Việc chạy một chương trình Java đã được biên dịch trước bằng lệnh `java HelloWorld` yêu cầu máy tính phải có JRE (JRE cũng đã được tích hợp sẵn bên trong JDK).

## JVM

Java Virtual Machine (JVM) là một máy tính trừu tượng cho phép máy tính vật lý có thể chạy chương trình Java. Nó thực hiện tải, kiểm chứng, thực thi mã byte và cung cấp môi trường thực thi thời gian chạy.

* **Tầm quan trọng:** Nó chịu trách nhiệm cho tính độc lập nền tảng ("Viết một lần, chạy mọi nơi"). JVM chuyển đổi mã byte độc lập với nền tảng thành các chỉ thị máy cụ thể cho từng hệ điều hành.
* **Điểm dễ nhầm lẫn:** Nghĩ rằng bản thân JVM là độc lập với nền tảng. Trong khi mã byte độc lập với nền tảng, mỗi hệ điều hành (Windows, Linux, macOS) yêu cầu một bản triển khai JVM cụ thể riêng.
* **Ví dụ nhỏ:** JVM xử lý việc cấp phát bộ nhớ heap và tự động chạy Bộ thu gom rác (Garbage Collection) khi bộ nhớ sắp hết.

## HashSet

Một bộ sưu tập (collection) lưu trữ các phần tử duy nhất. Nó được hỗ trợ bởi một `HashMap` bên dưới và không duy trì bất kỳ thứ tự chèn nào.

* **Tầm quan trọng:** Cho phép độ phức tạp thời gian O(1) cho các thao tác kiểm tra, thêm và xóa phần tử, đồng thời ngăn chặn các phần tử trùng lặp đi vào tập hợp.
* **Điểm dễ nhầm lẫn:** Giả định rằng `HashSet` duy trì thứ tự chèn hoặc được sắp xếp. Trên thực tế, nó không đưa ra đảm bảo nào về thứ tự của các phần tử theo thời gian.
* **Ví dụ nhỏ:** `Set<String> set = new HashSet<>(List.of("A", "A", "B"));` tạo ra một tập hợp chỉ chứa `["A", "B"]`.

## Comparable

Một giao diện chức năng (`java.lang.Comparable`) được triển khai bởi một lớp để xác định thứ tự sắp xếp tự nhiên (natural ordering) của các đối tượng thuộc lớp đó. Nó chứa một phương thức duy nhất là `compareTo(T o)`.

* **Tầm quan trọng:** Cho phép các đối tượng của lớp triển khai nó có thể tự động được sắp xếp bởi các công cụ tiện ích bộ sưu tập như `Collections.sort()` hoặc `Arrays.sort()`.
* **Điểm dễ nhầm lẫn:** Nhầm lẫn `Comparable` với `Comparator`. `Comparable` định nghĩa quy tắc sắp xếp ngay bên trong bản thân lớp, trong khi `Comparator` được định nghĩa độc lập bên ngoài lớp.
* **Ví dụ nhỏ:** Triển khai `Comparable<Student>` để sắp xếp học sinh một cách tự nhiên theo số định danh `id` của họ.

## Comparator

Một giao diện (`java.util.Comparator`) được sử dụng để định nghĩa các quy tắc sắp xếp tùy chỉnh bên ngoài các đối tượng được sắp xếp. Nó chứa phương thức `compare(T o1, T o2)`.

* **Tầm quan trọng:** Cho phép tạo ra nhiều chế độ xem sắp xếp khác nhau (ví dụ: sắp xếp theo tên, sau đó sắp xếp theo tuổi) mà không cần sửa đổi mã nguồn của lớp gốc.
* **Điểm dễ nhầm lẫn:** Tin rằng bạn chỉ có thể có một `Comparator` cho mỗi lớp. Bạn có thể định nghĩa bao nhiêu bộ so sánh tùy chỉnh tùy ý, thường sử dụng biểu thức lambda.
* **Ví dụ nhỏ:** `list.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));` thực hiện sắp xếp một danh sách theo thuộc tính tên.

## Map

Một đối tượng ánh xạ các khóa (keys) vào các giá trị (values). Một map không thể chứa các khóa trùng lặp; mỗi khóa có thể ánh xạ đến tối đa một giá trị.

* **Tầm quan trọng:** Cung cấp khả năng tra cứu khóa-giá trị hiệu quả cao, đây là một yêu cầu cơ bản trong hầu hết các hệ thống phần mềm.
* **Điểm dễ nhầm lẫn:** Nghĩ rằng `Map` kế thừa giao diện `Collection`. Nó không kế thừa; nó là một phân cấp giao diện riêng biệt trong gói `java.util`.
* **Ví dụ nhỏ:** `Map<String, String> map = new HashMap<>(); map.put("user1", "Alice");`

## FlatMap

Một hoạt động trung gian trong Stream API giúp biến đổi một stream chứa các bộ sưu tập/stream thành một stream phẳng duy nhất chứa các phần tử thô.

* **Tầm quan trọng:** Giải quyết vấn đề khi làm việc với các bộ sưu tập lồng nhau (như `List<List<T>>`) bằng cách làm phẳng chúng thành một stream duy nhất chứa các giá trị `T` để xử lý.
* **Điểm dễ nhầm lẫn:** Nghĩ rằng `flatMap` chỉ đơn giản là một hàm `map` nhanh hơn. `map` biến đổi từng phần tử theo tỷ lệ 1-1, trong khi `flatMap` biến đổi từng phần tử thành một stream và gộp các stream đó lại với nhau.
* **Ví dụ nhỏ:** `List<String> flat = nestedLists.stream().flatMap(List::stream).collect(Collectors.toList());`

## Kiểu Generic (Generics)

Một tính năng ngôn ngữ được giới thiệu từ Java 5 cho phép tham số hóa các kiểu dữ liệu (lớp và phương thức), cung cấp tính an toàn kiểu tại thời điểm biên dịch (compile-time type safety).

* **Tầm quan trọng:** Phát hiện các lỗi kiểu dữ liệu tại thời điểm biên dịch thay vì ném ra lỗi `ClassCastException` tại thời điểm chạy, loại bỏ việc phải thực hiện ép kiểu thủ công.
* **Điểm dễ nhầm lẫn:** Tin rằng thông tin kiểu generic tồn tại tại thời điểm chạy. Do Cơ chế xóa kiểu (Type Erasure), các tham số kiểu generic sẽ bị loại bỏ hoàn toàn trong quá trình biên dịch.
* **Ví dụ nhỏ:** Việc sử dụng `List<String> list = new ArrayList<>();` thay vì một `List` thô (raw list) đảm bảo rằng chỉ các chuỗi ký tự mới có thể được thêm vào danh sách.
