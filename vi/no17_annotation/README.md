# 17 - Chú thích (Annotation)

Chủ đề này tuân theo đề cương chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện nó trong mã nguồn và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập

- [Khái niệm Chú thích là gì](theory/01-what-is-an-annotation-concepts.md)
- [Khái niệm Documented](theory/02-documented-concepts.md)
- [Các thuật ngữ chính](terms/01-key-terms.md)

## Danh sách kiểm tra Đề cương

- Chú thích (annotation) là gì?
- Các chú thích tích hợp sẵn (built-in annotation):
  - @Override
  - @Deprecated
  - @SuppressWarnings
  - @FunctionalInterface
  - @SafeVarargs
- Các siêu chú thích (meta-annotation):
  - @Target
  - @Retention
  - @Documented
  - @Inherited
  - @Repeatable
- Chú thích tùy chỉnh (custom annotation)
- Chú thích thời điểm chạy (runtime annotation)
- Xử lý chú thích cơ bản (annotation processing)

## Tự kiểm tra (Self-Check)

Trước khi tiếp tục, hãy xác nhận rằng bạn có thể trả lời các câu hỏi khái niệm sau mà không cần tìm kiếm:
1. Tại sao chúng ta cần các siêu chú thích (meta-annotation) như `@Retention` và `@Target`?
2. Sự khác biệt giữa các chính sách lưu giữ (retention policy) `SOURCE`, `CLASS`, và `RUNTIME` ở cả cấp độ trình biên dịch và JVM là gì?
3. JVM xử lý các chú thích ở thời điểm chạy (runtime) bằng phản chiếu (reflection) như thế nào dưới cấu trúc (ví dụ: các lớp proxy động dynamic proxy class)?
4. Tại sao `@Override` được xử lý ở thời điểm biên dịch (compile-time) thay vì thời điểm chạy (runtime), và tại sao thiết kế này ngăn chặn các lỗi âm thầm trong quá trình tái cấu trúc mã nguồn (refactoring)?
5. Tại sao các chú thích chỉ có thể có các phần tử thuộc các kiểu dữ liệu cụ thể (kiểu nguyên thủy primitive, String, Class, enum, chú thích, hoặc mảng 1 chiều của chúng) mà không phải là các đối tượng tùy ý hoặc generic?
6. Tại sao `@Inherited` chỉ áp dụng cho các khai báo lớp mà không áp dụng cho giao diện (interface) hoặc phương thức, và những ảnh hưởng của ràng buộc thiết kế này là gì?

## Thẻ Anki

- [Cơ bản](anki/basic.tsv)
- [Cơ bản bổ sung](anki/basic-extra.tsv)
- [Điền vào chỗ trống](anki/cloze.tsv)
- [Câu hỏi mã nguồn](anki/code-question.tsv)

## Tổng quan Mermaid

```mermaid
flowchart TD
    A[Chú thích (Annotation)] --> B[Định nghĩa]
    A --> C[Quy tắc và cú pháp]
    A --> D[Các lỗi thường gặp]
    A --> E[Ghi nhớ phỏng vấn]
```

## Liên kết tham khảo

- https://docs.oracle.com/javase/tutorial/java/annotations/
