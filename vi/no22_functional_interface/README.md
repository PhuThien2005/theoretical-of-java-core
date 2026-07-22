# 22 - Giao diện chức năng (Functional Interface)

Chủ đề này tuân theo đề cương chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện nó trong mã nguồn và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập

- [Khái niệm Predicate T](theory/01-predicate-t-concepts.md)
- [Khái niệm Kết quả đầu ra là gì](theory/02-what-is-the-output-concepts.md)
- [Các thuật ngữ chính](terms/01-key-terms.md)

## Danh sách kiểm tra Đề cương

- Predicate<T>
- Function<T, R>
- Consumer<T>
- Supplier<T>
- UnaryOperator<T>
- BinaryOperator<T>
- BiPredicate<T, U>
- BiFunction<T, U, R>
- BiConsumer<T, U>
- Đầu vào là gì?
- Kết quả đầu ra là gì?
- Khi nào sử dụng giao diện nào?

## Thẻ Anki

- [Cơ bản](anki/basic.tsv)
- [Cơ bản bổ sung](anki/basic-extra.tsv)
- [Điền vào chỗ trống](anki/cloze.tsv)
- [Câu hỏi mã nguồn](anki/code-question.tsv)

## Tổng quan Mermaid

```mermaid
flowchart TD
    A[Giao diện chức năng (Functional Interface)] --> B[Định nghĩa]
    A --> C[Quy tắc và cú pháp]
    A --> D[Các lỗi thường gặp]
    A --> E[Ghi nhớ phỏng vấn]
```

## Tự kiểm tra (Self-Check)

Dưới đây là 5 câu hỏi khái niệm chuyên sâu để kiểm tra sự hiểu biết của bạn về các giao diện chức năng. Câu trả lời có thể được tìm thấy trong các tệp lý thuyết:

1. Tại sao chú thích `@FunctionalInterface` là tùy chọn, và những lợi ích biên dịch nào mà nó mang lại?
2. Các kiểu giao ước chức năng của `Predicate`, `Function`, `Consumer`, và `Supplier` khác nhau như thế nào về hình dạng đầu vào/đầu ra và mục đích của chúng?
3. Tại sao chúng ta cần các chuyên biệt hóa kiểu nguyên thủy (như `IntPredicate`, `LongFunction`, `DoubleConsumer`) thay vì chỉ sử dụng các lớp bao bọc generic, và chúng ngăn ngừa chi phí đóng hộp (boxing) như thế nào?
4. Các giao diện chức năng tận dụng các phương thức mặc định (default method) như thế nào để kết hợp và liên kết chức năng?
5. Quy chuẩn Ngôn ngữ Java (JLS) đếm các phương thức trừu tượng cho một giao diện chức năng như thế nào, và các quy tắc chính xác liên quan đến các phương thức ghi đè từ `java.lang.Object` là gì?

## Liên kết tham khảo

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/package-summary.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/FunctionalInterface.html
