# 26 - Vào/Ra trong Java (IO in Java)

Chủ đề này tuân theo đề mục chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện trong mã nguồn (code) và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập (Study Order)

- [Khái niệm về File (File Concepts)](theory/01-file-concepts.md)
- [Khái niệm về BufferedInputStream (Bufferedinputstream Concepts)](theory/02-bufferedinputstream-concepts.md)
- [Khái niệm về Tuần tự hóa (Serialization Concepts)](theory/03-serialization-concepts.md)
- [Thuật ngữ chính (Key Terms)](terms/01-key-terms.md)

## Danh sách đề mục (Outline Checklist)

- File
- Tạo file (Create file)
- Xóa file (Delete file)
- Kiểm tra sự tồn tại (Check existence)
- Đọc siêu dữ liệu file (Read file metadata)
- Tạo thư mục (Create directory)
- InputStream
- OutputStream
- FileInputStream
- FileOutputStream
- BufferedInputStream
- BufferedOutputStream
- Reader
- Writer
- FileReader
- FileWriter
- BufferedReader
- BufferedWriter
- ObjectInputStream
- ObjectOutputStream
- Tuần tự hóa (Serialization)
- Giải tuần tự hóa (Deserialization)
- Serializable
- serialVersionUID
- transient
- Scanner
- System.in
- System.out
- System.err

## Thẻ Anki (Anki Cards)

- [Cơ bản (Basic)](anki/basic.tsv)
- [Cơ bản Mở rộng (Basic Extra)](anki/basic-extra.tsv)
- [Điền vào chỗ trống (Cloze)](anki/cloze.tsv)
- [Câu hỏi Code (Code Question)](anki/code-question.tsv)

## Tự kiểm tra (Self-Check)

Trước khi chuyển sang chủ đề tiếp theo, hãy xác nhận rằng bạn có thể trả lời các câu hỏi sau:
1. Tại sao Java phân biệt giữa luồng byte (byte streams) và luồng ký tự (character streams), và cách ánh xạ bảng mã hóa ký tự (encoding charsets) được áp dụng dưới nền tảng (under the hood) như thế nào?
2. Tại sao `BufferedInputStream` / `BufferedOutputStream` mang lại hiệu năng vượt trội hơn đáng kể so với các thao tác luồng thô, và việc định cỡ bộ đệm (buffer sizing) của JVM tương tác với bộ nhớ đệm trang đĩa (disk page caching) của hệ điều hành như thế nào?
3. Tại sao việc tuần tự hóa (serialization) trong Java lại yêu cầu `serialVersionUID`, và những vấn đề tương thích trong thời gian biên dịch (compile-time) hoặc thời gian chạy (runtime) nào sẽ xảy ra nếu nó bị thiếu hoặc không khớp trong quá trình tiến hóa lớp (class evolution)?
4. Tại sao các trường `transient` bị loại trừ khỏi quá trình tuần tự hóa, và điều gì xảy ra với các trường `transient` trong quá trình giải tuần tự hóa (deserialization) (các quy tắc hàm dựng (constructor) hay các quy tắc khởi tạo giá trị không (zero-value initialization) sẽ được áp dụng)?
5. Tại sao cơ chế tuần tự hóa mặc định của Java lại bị coi là một nguy cơ bảo mật (security liability), và các giải pháp thay thế hiện đại hoặc chiến lược giảm thiểu là gì?

## Sơ đồ Mermaid tổng quan (Mermaid Overview)

```mermaid
flowchart TD
    A[Vào/Ra trong Java (IO in Java)] --> B[Định nghĩa (Definitions)]
    A --> C[Quy tắc và cú pháp (Rules and syntax)]
    A --> D[Các lỗi thường gặp (Common mistakes)]
    A --> E[Gợi nhớ phỏng vấn (Interview recall)]
```

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/essential/io/
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/package-summary.html
