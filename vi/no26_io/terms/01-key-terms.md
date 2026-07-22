# Thuật Ngữ IO Trong Java (Java IO Key Terms)

Sử dụng tài liệu này khi một từ khóa trong lý thuyết có vẻ quá ngắn gọn. Mỗi thuật ngữ đều có định nghĩa, tầm quan trọng, điểm dễ gây nhầm lẫn và một ví dụ nhỏ đi kèm.

## Stream (Dòng dữ liệu)

Một `Stream` (Dòng dữ liệu) trong Java IO đại diện cho một luồng truyền dữ liệu tuần tự (dưới dạng byte hoặc ký tự) từ một nguồn (source) đến một đích (destination).

- **Tầm quan trọng**: Là nền tảng cho mọi thao tác đọc/ghi dữ liệu trong Java (từ file, kết nối mạng, hoặc bộ nhớ đệm). Nó giúp trừu tượng hóa các chi tiết phần cứng cấp thấp.
- **Nhầm lẫn phổ biến**: Nhầm lẫn giữa các luồng IO (`InputStream`/`OutputStream`) với Stream API (`java.util.stream.Stream` được giới thiệu từ Java 8). Luồng IO dùng để truyền dữ liệu byte/ký tự thô, còn Stream API dùng để xử lý dữ liệu tập hợp bằng phong cách lập trình hàm.
- **Ví dụ nhỏ**: `FileInputStream` dùng để đọc dữ liệu dạng byte từ một tệp tin.

## Reader (Trình đọc ký tự)

`Reader` là một lớp trừu tượng trong gói `java.io` dùng để đọc các dòng ký tự (character streams).

- **Tầm quan trọng**: Java phân tách rõ ràng giữa đọc dữ liệu dạng byte thô (sử dụng `InputStream`) và đọc ký tự văn bản (sử dụng `Reader`). `Reader` tự động xử lý các bảng mã ký tự (character encodings) như UTF-8 hay UTF-16.
- **Nhầm lẫn phổ biến**: Sử dụng `InputStream` để đọc các tệp văn bản tiếng Việt hoặc chứa các ký tự Unicode phức tạp, dẫn đến việc ký tự bị hiển thị lỗi (corrupted text) do mỗi ký tự Unicode có thể chiếm nhiều hơn 1 byte. Hãy luôn dùng `Reader` (như `FileReader` hoặc `BufferedReader`) cho văn bản.
- **Ví dụ nhỏ**: `FileReader reader = new FileReader("document.txt");`

## Writer (Trình ghi ký tự)

`Writer` là một lớp trừu tượng trong gói `java.io` dùng để ghi các dòng ký tự.

- **Tầm quan trọng**: Tương tự như `Reader`, `Writer` đảm bảo việc chuyển đổi chính xác từ các ký tự Java sang các byte thô của tệp tin dựa trên bảng mã ký tự được chỉ định.
- **Nhầm lẫn phổ biến**: Quên gọi phương thức `flush()` hoặc `close()`. Dữ liệu ghi qua `Writer` thường được lưu tạm trong bộ nhớ đệm và chỉ thực sự được ghi xuống ổ đĩa khi tệp được đóng hoặc được xả đệm (`flush`).
- **Ví dụ nhỏ**: `FileWriter writer = new FileWriter("output.txt");`

## Buffering (Bộ đệm)

`Buffering` (Bộ đệm) là kỹ thuật lưu trữ tạm thời dữ liệu đọc/ghi vào một vùng nhớ đệm (buffer) trước khi thực hiện thao tác I/O vật lý thực tế.

- **Tầm quan trọng**: Giảm thiểu số lần truy cập trực tiếp vào ổ đĩa cứng hoặc mạng — các thao tác vật lý này có tốc độ chậm hơn hàng nghìn lần so với việc đọc/ghi trên RAM. Sử dụng `BufferedReader` hay `BufferedWriter` giúp nâng cao hiệu năng ứng dụng rõ rệt.
- **Nhầm lẫn phổ biến**: Lầm tưởng rằng cứ đọc ghi file là phải tự viết code quản lý mảng byte đệm thủ công. Java cung cấp sẵn các lớp trang trí (decorator classes) như `BufferedInputStream` và `BufferedReader` để tự động hóa việc này.
- **Ví dụ nhỏ**: Bọc một `FileReader` trong một `BufferedReader`: `new BufferedReader(new FileReader("file.txt"))`.

## Serialization (Tuần tự hóa)

`Serialization` (Tuần tự hóa) là quá trình chuyển đổi trạng thái của một đối tượng Java thành một chuỗi các byte thô để có thể lưu xuống ổ đĩa hoặc truyền qua mạng.

- **Tầm quan trọng**: Cho phép lưu trữ trạng thái của chương trình (persistence) và truyền tải các đối tượng Java giữa các hệ thống phân tán khác nhau.
- **Nhầm lẫn phổ biến**: Lầm tưởng rằng tất cả các lớp trong Java đều có thể tuần tự hóa được mặc định. Một lớp bắt buộc phải triển khai giao diện đánh dấu `java.io.Serializable` thì mới được phép thực hiện tuần tự hóa, nếu không JVM sẽ ném ra ngoại lệ `NotSerializableException`.
- **Ví dụ nhỏ**: Sử dụng `ObjectOutputStream.writeObject(obj)` để thực hiện tuần tự hóa một đối tượng.

## Transient (Từ khóa tạm thời)

`transient` là một bổ từ (modifier) áp dụng cho các biến thực thể của lớp, dùng để đánh dấu rằng trường đó không được phép tuần tự hóa.

- **Tầm quan trọng**: Bảo mật các thông tin nhạy cảm (như mật khẩu, khóa bí mật) bằng cách ngăn không cho ghi chúng xuống tệp tin trong quá trình tuần tự hóa đối tượng, hoặc bỏ qua các trường không cần thiết (như các kết nối database tạm thời).
- **Nhầm lẫn phổ biến**: Nghĩ rằng biến `transient` vẫn sẽ giữ nguyên giá trị sau khi được giải tuần tự hóa (deserialized). Khi đối tượng được khôi phục lại, các trường `transient` sẽ được thiết lập về giá trị mặc định của kiểu dữ liệu (`null` cho đối tượng, `0` cho số, `false` cho boolean).
- **Ví dụ nhỏ**:
  ```java
  public class User implements Serializable {
      private String username;
      private transient String password; // Trường này sẽ bị bỏ qua khi tuần tự hóa
  }
  ```

## serialVersionUID

`serialVersionUID` là một mã số nhận diện phiên bản duy nhất (dạng `long`) được liên kết với mỗi lớp có thể tuần tự hóa (`Serializable`).

- **Tầm quan trọng**: JVM sử dụng nó trong quá trình giải tuần tự hóa để kiểm tra xem lớp được nạp có tương thích với đối tượng đã được tuần tự hóa trước đó hay không. Nếu có sự sai lệch mã số này, JVM sẽ ném ra ngoại lệ `InvalidClassException`.
- **Nhầm lẫn phổ biến**: Không khai báo `serialVersionUID` một cách rõ ràng. Nếu bạn không khai báo, trình biên dịch Java sẽ tự động tạo ra một mã số dựa trên cấu trúc lớp. Tuy nhiên, bất kỳ thay đổi nhỏ nào trong cấu trúc lớp sau đó (như thêm phương thức) sẽ làm thay đổi mã số tự động này, khiến các đối tượng đã lưu trước đó không thể đọc được nữa.
- **Ví dụ nhỏ**:
  ```java
  private static final long serialVersionUID = 1L;
  ```
