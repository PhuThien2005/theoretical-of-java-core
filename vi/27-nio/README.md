# 27 - NIO / NIO.2

Chủ đề này tuân theo đề mục chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện trong mã nguồn (code) và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập (Study Order)

- [Khái niệm về Path (Path Concepts)](theory/01-path-concepts.md)
- [Khái niệm về FileChannel (Filechannel Concepts)](theory/02-filechannel-concepts.md)
- [Thuật ngữ chính (Key Terms)](terms/01-key-terms.md)

## Danh sách đề mục (Outline Checklist)

- Path
- Paths
- Files
- StandardOpenOption
- Đọc/ghi tập tin bằng Files (Read/write file using Files)
- Duyệt cây tập tin (Walk file tree)
- Sao chép/di chuyển/xóa tập tin (Copy/move/delete file)
- Kênh (Channel)
- Bộ đệm (Buffer)
- ByteBuffer
- FileChannel
- Bộ chọn cơ bản (Basic Selector)
- I/O bất đồng bộ cơ bản (Basic Asynchronous IO)

## Thẻ Anki (Anki Cards)

- [Cơ bản (Basic)](anki/basic.tsv)
- [Cơ bản Mở rộng (Basic Extra)](anki/basic-extra.tsv)
- [Điền vào chỗ trống (Cloze)](anki/cloze.tsv)
- [Câu hỏi Code (Code Question)](anki/code-question.tsv)

## Tự kiểm tra (Self-Check)

Trước khi chuyển sang chủ đề tiếp theo, hãy xác nhận rằng bạn có thể trả lời các câu hỏi sau:
1. Sự khác biệt chính giữa Classic I/O (BIO) và New I/O (NIO) trong Java là gì liên quan đến chặn (blocking) so với không chặn (non-blocking), hướng luồng (stream-oriented) so với hướng bộ đệm (buffer-oriented), và khả năng mở rộng luồng (thread scaling)?
2. Làm thế nào một `Buffer` trong NIO quản lý trạng thái bằng các con trỏ `position`, `limit`, và `capacity`, và tại sao phải gọi `flip()` khi chuyển đổi từ chế độ ghi sang chế độ đọc?
3. Tại sao các tập tin ánh xạ bộ nhớ (memory-mapped files) (`FileChannel.map` trả về `MappedByteBuffer`) lại cực kỳ nhanh đối với các thao tác đọc/ghi tập tin lớn, và cách chúng tận dụng bộ nhớ ảo (virtual memory) cùng việc bỏ qua bộ nhớ đệm trang của hệ điều hành (OS page cache bypass) như thế nào?
4. Làm thế nào một `Selector` cho phép thực hiện I/O không chặn đa lộ (multiplexed non-blocking I/O), cho phép một luồng duy nhất giám sát nhiều kênh mạng cùng lúc?
5. Tại sao `Path` và `Files` (NIO.2) cung cấp khả năng báo cáo ngoại lệ tốt hơn, xử lý liên kết tượng trưng (symbolic link), và truy cập siêu dữ liệu (metadata) tốt hơn so với `java.io.File` cũ?

## Sơ đồ Mermaid tổng quan (Mermaid Overview)

```mermaid
flowchart TD
    A[NIO / NIO.2] --> B[Định nghĩa (Definitions)]
    A --> C[Quy tắc và cú pháp (Rules and syntax)]
    A --> D[Các lỗi thường gặp (Common mistakes)]
    A --> E[Gợi nhớ phỏng vấn (Interview recall)]
```

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/package-summary.html
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/package-summary.html
