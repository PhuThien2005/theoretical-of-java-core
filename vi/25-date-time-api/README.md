# 25 - API Ngày và Giờ (Date and Time API)

Chủ đề này tuân theo đề mục chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện trong mã nguồn (code) và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập (Study Order)

- [Khái niệm về Ngày (Date Concepts)](theory/01-date-concepts.md)
- [Khái niệm về Khoảng thời gian (Period Concepts)](theory/02-period-concepts.md)
- [Thuật ngữ chính (Key Terms)](terms/01-key-terms.md)

## Danh sách đề mục (Outline Checklist)

- Date
- Calendar
- SimpleDateFormat
- LocalDate
- LocalTime
- LocalDateTime
- ZonedDateTime
- OffsetDateTime
- Instant
- Duration
- Period
- DateTimeFormatter
- ZoneId
- Phân tích cú pháp ngày/giờ (Parse date/time)
- Định dạng ngày/giờ (Format date/time)
- So sánh ngày/giờ (Compare date/time)
- Cộng/trừ ngày/giờ (Add/subtract date/time)
- Múi giờ (Timezone)

## Tự kiểm tra (Self-Check)

- Tại sao API ngày và lịch cũ (`java.util.Date`, `java.util.Calendar`, `java.text.SimpleDateFormat`) lại bị lỗi thiết kế (ví dụ: tính khả biến (mutability), lệch chỉ mục (index bias), các vấn đề về an toàn luồng (thread-safety))?
- Tại sao các đối tượng ngày-giờ hiện đại trong Java 8 (như `LocalDate`, `LocalTime`, `ZonedDateTime`) được thiết kế để bất biến (immutable) và an toàn luồng (thread-safe), và mẫu thiết kế (pattern) nào được sử dụng để lấy các thể hiện (instance) đã được sửa đổi?
- Sự khác biệt trong biểu diễn và quy tắc múi giờ (timezone) giữa `OffsetDateTime`, `ZonedDateTime` và `Instant` là gì?
- Tại sao `java.time.format.DateTimeFormatter` tránh được các lỗi bất đồng bộ/đồng thời (concurrency) của `SimpleDateFormat`?
- Sự khác biệt trong biểu diễn và hành vi giữa `Period` và `Duration` là gì, đặc biệt là khi được cộng vào một đối tượng thời gian có nhận biết múi giờ như `ZonedDateTime` qua các thời điểm chuyển đổi giờ mùa hè (Daylight Saving Time - DST)?
- Làm thế nào `ZonedDateTime` xử lý các ngày-giờ cục bộ không hợp lệ hoặc bị trùng lặp do quá trình chuyển đổi giờ mùa hè (DST) khỏi đầu hoặc kết thúc?

## Thẻ Anki (Anki Cards)

- [Cơ bản (Basic)](anki/basic.tsv)
- [Cơ bản Mở rộng (Basic Extra)](anki/basic-extra.tsv)
- [Điền vào chỗ trống (Cloze)](anki/cloze.tsv)
- [Câu hỏi Code (Code Question)](anki/code-question.tsv)

## Sơ đồ Mermaid tổng quan (Mermaid Overview)

```mermaid
flowchart TD
    A[API Ngày và Giờ (Date and Time API)] --> B[Định nghĩa (Definitions)]
    A --> C[Quy tắc và cú pháp (Rules and syntax)]
    A --> D[Các lỗi thường gặp (Common mistakes)]
    A --> E[Gợi nhớ phỏng vấn (Interview recall)]
```

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/datetime/
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/package-summary.html
