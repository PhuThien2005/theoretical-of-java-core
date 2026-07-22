# 33 - Hệ thống Mô-đun Java (Java Module System)

Chủ đề này tuân theo đề cương chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện nó trong mã nguồn và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập

- [Khái niệm Mô-đun là gì](theory/01-what-is-a-module-concepts.md)
- [Các thuật ngữ chính](terms/01-key-terms.md)

## Danh sách kiểm tra Đề cương

- Mô-đun (module) là gì?
- module-info.java
- requires
- exports
- opens
- Mô-đun có tên (named module)
- Mô-đun không tên (unnamed module)
- Mô-đun tự động (automatic module)
- Đóng gói ở cấp độ mô-đun
- Đường dẫn mô-đun (module path) so với Đường dẫn lớp (classpath)

## Tự kiểm tra (Self-Check)

Trước khi chuyển sang các thẻ Anki, hãy xác nhận rằng bạn có thể trả lời các câu hỏi sau:
1. Tại sao Java 9 giới thiệu Hệ thống Mô-đun (Project Jigsaw), và nó đã giải quyết những vấn đề gì về bảo mật/độ tin cậy của classpath?
2. Sự khác biệt giữa `exports` và `opens` trong `module-info.java` là gì, và `opens` ảnh hưởng đến phản chiếu (reflection) như thế nào?
3. Đường dẫn mô-đun (Module Path) khác với Đường dẫn lớp (Classpath) như thế nào (nạp lớp dạng mô-đun modular classloading so với nạp lớp phẳng flat classloading)?
4. Các mô-đun tự động và mô-đun không tên là gì, và chúng làm cầu nối chuyển đổi như thế nào cho các thư viện cũ (legacy library)?
5. Tại sao các phụ thuộc vòng lặp (cyclic dependency) và phân tách gói (split package) bị cấm nghiêm ngặt dưới Hệ thống Mô-đun Java?

## Thẻ Anki

- [Cơ bản](anki/basic.tsv)
- [Cơ bản bổ sung](anki/basic-extra.tsv)
- [Điền vào chỗ trống](anki/cloze.tsv)
- [Câu hỏi mã nguồn](anki/code-question.tsv)

## Tổng quan Mermaid

```mermaid
flowchart TD
    A[Hệ thống Mô-đun Java (Java Module System)] --> B[Định nghĩa]
    A --> C[Quy tắc và cú pháp]
    A --> D[Các lỗi thường gặp]
    A --> E[Ghi nhớ phỏng vấn]
```

## Liên kết tham khảo

- https://dev.java/learn/modules/
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-7.html#jls-7.7
