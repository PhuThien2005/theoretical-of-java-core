# 33 - Hệ thống Mô-đun Java (Java Module System)

Chủ đề này tuân theo đề mục chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện trong mã nguồn (code) và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập (Study Order)

- [Khái niệm về Mô-đun (What Is A Module Concepts)](theory/01-what-is-a-module-concepts.md)
- [Thuật ngữ chính (Key Terms)](terms/01-key-terms.md)

## Danh sách đề mục (Outline Checklist)

- Mô-đun là gì? (What is a module?)
- module-info.java
- requires
- exports
- opens
- Mô-đun có tên (Named module)
- Mô-đun không tên (Unnamed module)
- Mô-đun tự động (Automatic module)
- Đóng gói cấp mô-đun (Module-level encapsulation)
- Module path so với Classpath (Module path vs Classpath)

## Tự kiểm tra (Self-Check)

Trước khi chuyển sang thẻ Anki, hãy xác nhận bạn có thể trả lời các câu hỏi sau:
1. Tại sao Java 9 giới thiệu Hệ thống Mô-đun (Project Jigsaw), và các vấn đề về bảo mật/độ tin cậy nào của classpath đã được giải quyết?
2. Sự khác biệt giữa `exports` và `opens` trong `module-info.java` là gì, và `opens` ảnh hưởng đến cơ chế phản chiếu (reflection) như thế nào?
3. Module Path khác với Classpath như thế nào (nạp lớp dạng mô-đun (modular classloading) so với nạp lớp phẳng (flat classloading))?
4. Mô-đun tự động (automatic modules) và mô-đun không tên (unnamed modules) là gì, và chúng làm thế nào để làm cầu nối chuyển đổi cho các thư viện cũ (legacy libraries)?
5. Tại sao các phụ thuộc vòng (cyclic dependencies) và chia tách gói (split packages) bị cấm nghiêm ngặt trong Hệ thống Mô-đun Java?

## Thẻ Anki (Anki Cards)

- [Cơ bản (Basic)](anki/basic.tsv)
- [Cơ bản Mở rộng (Basic Extra)](anki/basic-extra.tsv)
- [Điền vào chỗ trống (Cloze)](anki/cloze.tsv)
- [Câu hỏi Code (Code Question)](anki/code-question.tsv)

## Sơ đồ Mermaid tổng quan (Mermaid Overview)

```mermaid
flowchart TD
    A[Hệ thống Mô-đun Java (Java Module System)] --> B[Định nghĩa (Definitions)]
    A --> C[Quy tắc và cú pháp (Rules and syntax)]
    A --> D[Các lỗi thường gặp (Common mistakes)]
    A --> E[Gợi nhớ phỏng vấn (Interview recall)]
```

## Liên kết tham khảo (Reference Links)

- https://dev.java/learn/modules/
- https://docs.oracle.com/javase/specs/jls/se21/html/jls-7.html#jls-7.7
