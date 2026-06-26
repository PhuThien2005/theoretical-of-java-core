# 34 - JDBC

Chủ đề này tuân theo đề mục chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện trong mã nguồn (code) và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập (Study Order)

- [Khái niệm về JDBC (What Is Jdbc Concepts)](theory/01-what-is-jdbc-concepts.md)
- [Khái niệm về Rollback (Rollback Concepts)](theory/02-rollback-concepts.md)
- [Thuật ngữ chính (Key Terms)](terms/01-key-terms.md)

## Danh sách đề mục (Outline Checklist)

- JDBC là gì? (What is JDBC?)
- Driver
- DriverManager
- Connection
- Statement
- PreparedStatement
- CallableStatement
- ResultSet
- Giao dịch (Transaction):
  - commit
  - rollback
  - setAutoCommit
- Xử lý lô (Batch processing)
- Lỗi chèn mã SQL (SQL Injection)
- Nhóm kết nối cơ bản (Basic Connection Pool)
- DataSource
- Thao tác CRUD bằng JDBC (CRUD using JDBC)

## Tự kiểm tra (Self-Check)

1. Tại sao `PreparedStatement` ngăn chặn lỗi chèn mã SQL (SQL injection), và nó tận dụng bộ nhớ đệm kế hoạch truy vấn (query plan cache) của cơ sở dữ liệu để cải thiện hiệu năng so với `Statement` như thế nào?
2. Tại sao việc vô hiệu hóa tự động commit (auto-commit) lại ghi đè lên hành vi mặc định của cơ sở dữ liệu, và tại sao việc kiểm soát thủ công này lại quan trọng để duy trì các ranh giới ACID của giao dịch?
3. Tại sao các `Savepoint` cơ sở dữ liệu cho phép khôi phục từng phần (partial rollbacks), và cơ chế cơ sở cũng như tác động của nó lên các mức cô lập giao dịch khi thực thi khôi phục từng phần là gì?
4. Tại sao các nhóm kết nối cơ sở dữ liệu (như HikariCP) mang lại hiệu năng vượt trội, và cách chúng tái sử dụng các kết nối vật lý để tránh các bắt tay TCP (TCP handshakes) và chi phí xác thực cơ sở dữ liệu như thế nào?
5. Tại sao các tài nguyên JDBC (`Connection`, `Statement`, `ResultSet`) phải được đóng theo thứ tự ngược lại một cách nghiêm ngặt so với khi tạo ra chúng, và khối try-with-resources ngăn ngừa rò rỉ tài nguyên dưới tác động của bộ thu gom rác (Garbage collection) JVM như thế nào?

## Thẻ Anki (Anki Cards)

- [Cơ bản (Basic)](anki/basic.tsv)
- [Cơ bản Mở rộng (Basic Extra)](anki/basic-extra.tsv)
- [Điền vào chỗ trống (Cloze)](anki/cloze.tsv)
- [Câu hỏi Code (Code Question)](anki/code-question.tsv)

## Sơ đồ Mermaid tổng quan (Mermaid Overview)

```mermaid
flowchart TD
    A[JDBC] --> B[Định nghĩa (Definitions)]
    A --> C[Quy tắc và cú pháp (Rules and syntax)]
    A --> D[Các lỗi thường gặp (Common mistakes)]
    A --> E[Gợi nhớ phỏng vấn (Interview recall)]
```

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/jdbc/
- https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/package-summary.html
