# Thuật ngữ các câu hỏi phỏng vấn Java Core phổ biến (Common Java Core Interview Questions Terms)

Hãy sử dụng tài liệu này khi một từ ngữ trong phần lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, sự nhầm lẫn phổ biến và một ví dụ nhỏ.

## JDK (JDK)

Java Development Kit (JDK) là môi trường phát triển phần mềm được sử dụng để phát triển các ứng dụng Java. Nó chứa JRE (Java Runtime Environment) cùng với các công cụ phát triển như trình biên dịch (`javac`), trình gỡ lỗi (`jdb`) và các công cụ tài liệu.

- **Tại sao nó quan trọng**: Các lập trình viên cần JDK để biên dịch mã nguồn (`.java`) thành bytecode (`.class`). Không có nó, bạn không thể xây dựng các dự án Java cục bộ hoặc chạy các công cụ phân tích hiệu năng (profiling tools).
- **Sự nhầm lẫn phổ biến**: Nhầm lẫn JDK với JRE. Người dùng cuối chỉ cần JRE để chạy các chương trình Java, trong khi lập trình viên cần toàn bộ JDK để biên dịch và gỡ lỗi.
- **Ví dụ nhỏ**: Biên dịch một lớp bằng cách sử dụng `javac HelloWorld.java` từ dòng lệnh yêu cầu cài đặt JDK.

## JRE (JRE)

Java Runtime Environment (JRE) là một phần của Java SDK chứa JVM, các thư viện lõi và các tệp hỗ trợ khác cần thiết để chạy các chương trình Java đã biên dịch.

- **Tại sao nó quan trọng**: JRE cung cấp các yêu cầu tối thiểu để thực thi một ứng dụng Java trên máy khách.
- **Sự nhầm lẫn phổ biến**: Giả định rằng JRE có thể biên dịch code. Nó chứa JVM và các thư viện lớp nhưng thiếu trình biên dịch `javac` và các công cụ phát triển khác.
- **Ví dụ nhỏ**: Chạy một chương trình Java đã biên dịch sẵn bằng lệnh `java HelloWorld` yêu cầu cài đặt JRE (JRE cũng được tích hợp sẵn bên trong JDK).

## JVM (JVM)

Java Virtual Machine (JVM) là một máy tính trừu tượng cho phép máy tính chạy chương trình Java. Nó nạp, xác thực, thực thi bytecode và cung cấp môi trường thực thi thời gian chạy.

- **Tại sao nó quan trọng**: Nó chịu trách nhiệm cho tính độc lập nền tảng ("Viết một lần, chạy mọi nơi"). JVM chuyển đổi bytecode trung lập với nền tảng thành các lệnh máy cụ thể của hệ điều hành.
- **Sự nhầm lẫn phổ biến**: Nghĩ rằng bản thân JVM là độc lập với nền tảng. Mặc dù bytecode là độc lập với nền tảng, mỗi hệ điều hành (Windows, Linux, macOS) yêu cầu triển khai JVM cụ thể của riêng nó.
- **Ví dụ nhỏ**: JVM xử lý phân bổ bộ nhớ heap và tự động chạy bộ thu gom rác (Garbage Collection) khi bộ nhớ cạn kiệt.

## HashSet (HashSet)

Một bộ sưu tập (collection) lưu trữ các phần tử duy nhất. Nó được hỗ trợ bởi một `HashMap` bên dưới và không duy trì bất kỳ thứ tự chèn nào.

- **Tại sao nó quan trọng**: Cho phép độ phức tạp thời gian O(1) cho các thao tác kiểm tra, thêm và xóa trong khi ngăn các phần tử trùng lặp đi vào set.
- **Sự nhầm lẫn phổ biến**: Giả định rằng `HashSet` duy trì thứ tự chèn hoặc được sắp xếp. Trong thực tế, nó không đưa ra bất kỳ đảm bảo nào về thứ tự của các phần tử theo thời gian.
- **Ví dụ nhỏ**: `Set<String> set = new HashSet<>(List.of("A", "A", "B"));` tạo ra một set chứa `["A", "B"]`.

## Comparable (Comparable)

Một giao diện chức năng (functional interface - `java.lang.Comparable`) được triển khai bởi một lớp để xác định thứ tự tự nhiên (natural ordering) của nó. Nó chứa một phương thức duy nhất `compareTo(T o)`.

- **Tại sao nó quan trọng**: Cho phép các đối tượng của lớp triển khai được tự động sắp xếp bởi các lớp tiện ích bộ sưu tập như `Collections.sort()` hoặc `Arrays.sort()`.
- **Sự nhầm lẫn phổ biến**: Nhầm lẫn `Comparable` với `Comparator`. `Comparable` định nghĩa việc sắp xếp bên trong chính lớp đó, trong khi `Comparator` được định nghĩa bên ngoài.
- **Ví dụ nhỏ**: Triển khai `Comparable<Student>` để sắp xếp học sinh một cách tự nhiên theo thuộc tính số `id` của họ.

## Comparator (Comparator)

Một interface (`java.util.Comparator`) được sử dụng để định nghĩa các quy tắc sắp xếp tùy chỉnh bên ngoài các đối tượng được sắp xếp. Nó chứa phương thức `compare(T o1, T o2)`.

- **Tại sao nó quan trọng**: Cho phép nhiều góc nhìn sắp xếp khác nhau (ví dụ: sắp xếp theo tên, sau đó theo tuổi) mà không cần sửa đổi mã nguồn của lớp ban đầu.
- **Sự nhầm lẫn phổ biến**: Tin rằng bạn chỉ có thể có một `Comparator` duy nhất cho mỗi lớp. Bạn có thể định nghĩa bao nhiêu comparator tùy chỉnh tùy thích, thường bằng cách sử dụng biểu thức lambda.
- **Ví dụ nhỏ**: `list.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));` sắp xếp một danh sách theo tên.

## Map (Map)

Một đối tượng ánh xạ các khóa (keys) tới các giá trị (values). Một map không thể chứa các khóa trùng lặp; mỗi khóa có thể ánh xạ tới tối đa một giá trị.

- **Tại sao nó quan trọng**: Cung cấp khả năng tra cứu khóa-giá trị cực kỳ hiệu quả, đây là yêu cầu cơ bản trong hầu hết các hệ thống phần mềm.
- **Sự nhầm lẫn phổ biến**: Nghĩ rằng `Map` kế thừa interface `Collection`. Thực tế là không; nó là một phân cấp interface riêng biệt trong package `java.util`.
- **Ví dụ nhỏ**: `Map<String, String> map = new HashMap<>(); map.put("user1", "Alice");`

## FlatMap (FlatMap)

Một thao tác trung gian (intermediate operation) trong Stream API giúp chuyển đổi một stream chứa các bộ sưu tập/stream thành một stream phẳng duy nhất chứa các phần tử.

- **Tại sao nó quan trọng**: Giải quyết vấn đề làm việc với các bộ sưu tập lồng nhau (chẳng hạn như `List<List<T>>`) bằng cách làm phẳng chúng thành một stream duy nhất của các giá trị `T` để xử lý.
- **Sự nhầm lẫn phổ biến**: Nghĩ rằng `flatMap` chỉ đơn thuần là một phiên bản chạy nhanh hơn của `map`. Thao tác `map` biến đổi từng phần tử theo tỷ lệ 1-1, trong khi `flatMap` biến đổi mỗi phần tử thành một stream và gộp các stream đó lại với nhau.
- **Ví dụ nhỏ**: `List<String> flat = nestedLists.stream().flatMap(List::stream).collect(Collectors.toList());`

## Generics (Generics)

Một tính năng ngôn ngữ được giới thiệu từ Java 5 cho phép các kiểu dữ liệu (các lớp và phương thức) được tham số hóa, cung cấp tính an toàn kiểu dữ liệu ở thời điểm biên dịch.

- **Tại sao nó quan trọng**: Phát hiện các lỗi kiểu dữ liệu ở thời điểm biên dịch thay vì ném ra ngoại lệ `ClassCastException` ở thời điểm chạy, loại bỏ nhu cầu ép kiểu thủ công.
- **Sự nhầm lẫn phổ biến**: Tin rằng thông tin kiểu generic tồn tại ở thời điểm chạy. Do Cơ chế xóa bỏ kiểu (Type Erasure), các tham số kiểu generic sẽ bị loại bỏ trong quá trình biên dịch.
- **Ví dụ nhỏ**: Sử dụng `List<String> list = new ArrayList<>();` thay vì một `List` thô (raw list) đảm bảo rằng chỉ các chuỗi mới có thể được thêm vào.
