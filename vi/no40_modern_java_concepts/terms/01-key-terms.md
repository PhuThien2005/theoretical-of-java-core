# Thuật Ngữ Các Khái Niệm Java Hiện Đại Cần Biết (Modern Java Concepts Key Terms)

Sử dụng tài liệu này khi một từ khóa trong lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ đi kèm.

## Record (Bản ghi)

Một `record` (bản ghi) là một lớp Java đặc biệt giúp khai báo nhanh các lớp dùng để lưu trữ dữ liệu bất biến (immutable data carrier).

- **Tầm quan trọng**: Giảm thiểu tối đa mã nguồn rườm rà (boilerplate code) như getter, `equals()`, `hashCode()` và `toString()`. Nó đảm bảo tính toàn vẹn của dữ liệu trong quá trình truyền tải giữa các tầng ứng dụng.
- **Nhầm lẫn phổ biến**: Lầm tưởng rằng các trường của `record` có thể thay đổi được (mutable) hoặc `record` có thể kế thừa các lớp khác. Thực tế, các trường của `record` luôn là `private final` và lớp `record` ngầm định là `final` (không thể bị kế thừa).
- **Ví dụ nhỏ**:
  ```java
  public record Point(int x, int y) {}
  ```

## Sealed Class (Lớp Kín)

Một `sealed class` (lớp kín) hoặc `sealed interface` giới hạn những lớp hoặc giao diện khác có thể kế thừa (extend) hoặc triển khai (implement) nó.

- **Tầm quan trọng**: Cho phép lập trình viên kiểm soát chặt chẽ phân cấp kế thừa, cung cấp khả năng đóng gói ở mức độ cao hơn và giúp trình biên dịch thực hiện kiểm tra đầy đủ (exhaustiveness checks) trong các biểu thức khớp mẫu.
- **Nhầm lẫn phổ biến**: Nghĩ rằng bất kỳ lớp nào cũng kế thừa được một lớp kín. Chỉ có các lớp được liệt kê cụ thể trong mệnh đề `permits` mới được phép kế thừa nó, và các lớp con đó bắt buộc phải được khai báo là `final`, `sealed`, hoặc `non-sealed`.
- **Ví dụ nhỏ**:
  ```java
  public sealed class Shape permits Circle, Square {}
  public final class Circle extends Shape {}
  public final class Square extends Shape {}
  ```

## Pattern Matching (Khớp Mẫu)

`pattern matching` (khớp mẫu) là một cơ chế cho phép kiểm tra một giá trị có cấu trúc hoặc kiểu cụ thể, sau đó trích xuất trực tiếp thông tin từ giá trị đó một cách an toàn và gọn gàng.

- **Tầm quan trọng**: Loại bỏ việc phải thực hiện ép kiểu thủ công (explicit casting) sau khi kiểm tra kiểu bằng toán tử `instanceof` hoặc trong các câu lệnh `switch`.
- **Nhầm lẫn phổ biến**: Nghĩ rằng khớp mẫu chỉ dùng được với toán tử `instanceof`. Trong các phiên bản Java hiện đại, khớp mẫu đã được mở rộng cho biểu thức `switch` và bản ghi (`record patterns`).
- **Ví dụ nhỏ**:
  ```java
  if (obj instanceof String s) {
      System.out.println(s.toLowerCase()); // Không cần ép kiểu (String) obj nữa
  }
  ```

## Text Block (Khối Văn Bản)

`text block` (khối văn bản) là một chuỗi ký tự nhiều dòng, bắt đầu bằng ba dấu nháy kép `"""` và kết thúc bằng ba dấu nháy kép `"""`, giúp biểu diễn chuỗi nhiều dòng mà không cần các ký tự thoát dòng (`\n`).

- **Tầm quan trọng**: Giúp viết các mã nguồn lồng nhau (như JSON, SQL, HTML) bên trong Java một cách dễ đọc, dễ bảo trì và giữ nguyên định dạng thụt lề một cách trực quan.
- **Nhầm lẫn phổ biến**: Nhầm lẫn về cách tính khoảng thụt đầu dòng (indentation). Trình biên dịch Java tự động loại bỏ các khoảng trắng thụt lề chung (common white space prefix) của tất cả các dòng để giữ chuỗi sạch sẽ.
- **Ví dụ nhỏ**:
  ```java
  String json = """
      {
          "name": "Alice",
          "age": 20
      }
      """;
  ```

## Virtual Thread (Luồng Ảo)

`virtual thread` (luồng ảo - được giới thiệu chính thức từ Java 21) là các luồng trọng lượng nhẹ (lightweight threads) được quản lý bởi JVM thay vì hệ điều hành.

- **Tầm quan trọng**: Cho phép ứng dụng chạy hàng triệu luồng đồng thời với chi phí bộ nhớ cực kỳ thấp, giải quyết triệt để nút thắt cổ chai về hiệu năng của mô hình "mỗi luồng một yêu cầu" (thread-per-request) truyền thống.
- **Nhầm lẫn phổ biến**: Lầm tưởng rằng luồng ảo chạy nhanh hơn luồng hệ điều hành thông thường cho các tác vụ tính toán nặng (CPU-bound). Thực tế, luồng ảo chỉ giúp tối ưu hóa hiệu năng vượt trội cho các tác vụ bị nghẽn bởi I/O (I/O-bound) như truy vấn database hoặc gọi API.
- **Ví dụ nhỏ**:
  ```java
  Thread.startVirtualThread(() -> {
      System.out.println("Running in virtual thread");
  });
  ```

## Sequenced Collection (Tập Hợp Có Thứ Tự)

`sequenced collection` là các giao diện (interface) được giới thiệu trong Java 21 đại diện cho các tập hợp có thứ tự gặp gỡ (encounter order) xác định rõ ràng từ đầu đến cuối.

- **Tầm quan trọng**: Thống nhất các API truy xuất, sửa đổi phần tử đầu/cuối và đảo ngược thứ tự cho tất cả các cấu trúc dữ liệu có thứ tự (như `List`, `Deque`, `LinkedHashSet`).
- **Nhầm lẫn phổ biến**: Nghĩ rằng gọi `.reversed()` sẽ tạo ra một bản sao mới của tập hợp. Thực tế, nó chỉ trả về một chế độ xem đảo ngược (reverse view) có độ phức tạp thời gian là $O(1)$ và các sửa đổi trên view này vẫn ghi trực tiếp vào tập hợp gốc.
- **Ví dụ nhỏ**:
  ```java
  SequencedCollection<String> list = new ArrayList<>(List.of("A", "B", "C"));
  String first = list.getFirst(); // "A"
  String last = list.getLast();   // "C"
  ```
