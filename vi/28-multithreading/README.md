# 28 - Đa luồng (Multithreading)

Chủ đề này tuân theo đề mục chính trong [outline.md](../outline.md). Mục tiêu là hiểu sâu sắc từng khái niệm để có thể giải thích, nhận diện trong mã nguồn (code) và trả lời các câu hỏi phỏng vấn.

## Trình tự học tập (Study Order)

- [Khái niệm về Tiến trình so với Luồng (Process Vs Thread Concepts)](theory/01-process-vs-thread-concepts.md)
- [Khái niệm về Runnable (Runnable Concepts)](theory/02-runnable-concepts.md)
- [Khái niệm về Join (Join Concepts)](theory/03-join-concepts.md)
- [Khái niệm về An toàn luồng (Thread Safety Concepts)](theory/04-thread-safety-concepts.md)
- [Thuật ngữ chính (Key Terms)](terms/01-key-terms.md)

## Danh sách đề mục (Outline Checklist)

- Tiến trình so với Luồng (Process vs Thread)
- Tạo luồng bằng cách:
  - extends Thread
  - implements Runnable
  - implements Callable
- ExecutorService
- Vòng đời của Luồng (Lifecycle of Thread):
  - New (Mới tạo)
  - Runnable (Sẵn sàng chạy)
  - Running (Đang chạy)
  - Blocked (Bị chặn)
  - Waiting (Chờ đợi)
  - Timed Waiting (Chờ đợi có thời hạn)
  - Terminated (Đã kết thúc)
- start() vs run()
- sleep
- join
- yield
- interrupt
- Luồng Daemon (Daemon thread)
- Luồng người dùng (User thread)
- Độ ưu tiên luồng (Thread priority)
- Điều kiện tranh đoạt (Race condition)
- Đoạn găng (Critical section)
- An toàn luồng (Thread safety)
- Đối tượng bất biến (Immutable object)
- Thao tác nguyên tử (Atomic operation)

## Tự kiểm tra (Self-Check)

Trước khi chuyển sang chủ đề tiếp theo, hãy xác nhận rằng bạn có thể trả lời các câu hỏi sau:
1. Tại sao một tiến trình khác với một luồng về phân bổ tài nguyên, và cấu trúc bộ nhớ JVM (Heap/Metaspace dùng chung so với Stack/PC riêng của luồng) phản ánh sự khác biệt này như thế nào?
   &rarr; Xem [Tại sao Tiến trình khác biệt so với Luồng](theory/01-process-vs-thread-concepts.md#tai-sao-tien-trinh-khac-biet-so-voi-luong-why-processes-differ-from-threads)
2. Tại sao bạn nên triển khai `Runnable` hoặc `Callable` thay vì kế thừa lớp `Thread` (và ràng buộc đơn kế thừa của Java cùng sự phân tách công việc (task decoupling) thúc đẩy thiết kế này như thế nào)?
   &rarr; Xem [Tại sao Runnable/Callable được ưa chuộng hơn kế thừa Thread](theory/02-runnable-concepts.md#tai-sao-runnablecallable-duoc-ua-chuong-hon-ke-thua-thread-why-runnablecallable-is-preferred-over-extending-thread)
3. Tại sao việc gọi `start()` trên một luồng tạo ra một ngăn xếp cuộc gọi (call stack) mới trong khi `run()` thực thi trên ngăn xếp của luồng gọi, và bộ lập lịch luồng cấp hệ điều hành (OS-level thread scheduler) được kích hoạt như thế nào?
   &rarr; Xem [Tại sao start() là bắt buộc để tạo một luồng mới](theory/02-runnable-concepts.md#tai-sao-start-la-bat-buoc-de-tao-mot-luong-moi-why-start-is-required-to-spawn-a-thread)
4. Tại sao `thread.join()` khiến luồng gọi bị chặn, và cơ chế chờ đợi và thông báo của JVM/OS nào được kích hoạt?
   &rarr; Xem [Tại sao join() chặn luồng gọi](theory/03-join-concepts.md#tai-sao-join-chan-luong-goi-why-join-blocks-the-calling-thread)
5. Tại sao tình trạng tranh chấp (race conditions) và các vấn đề về khả năng hiển thị dữ liệu (data visibility) xảy ra trong môi trường đa luồng, và một thao tác được gọi là an toàn luồng (thread-safe) có nghĩa là gì?
   &rarr; Xem [Tại sao điều kiện tranh đoạt và vấn đề hiển thị dữ liệu xảy ra](theory/04-thread-safety-concepts.md#tai-sao-dieu-kien-tranh-doat-va-van-de-hien-thi-du-lieu-xay-ra-why-race-conditions-and-data-visibility-issues-occur)

## Thẻ Anki (Anki Cards)

- [Cơ bản (Basic)](anki/basic.tsv)
- [Cơ bản Mở rộng (Basic Extra)](anki/basic-extra.tsv)
- [Điền vào chỗ trống (Cloze)](anki/cloze.tsv)
- [Câu hỏi Code (Code Question)](anki/code-question.tsv)

## Sơ đồ Mermaid tổng quan (Mermaid Overview)

```mermaid
flowchart TD
    A[Đa luồng (Multithreading)] --> B[Định nghĩa (Definitions)]
    A --> C[Quy tắc và cú pháp (Rules and syntax)]
    A --> D[Các lỗi thường gặp (Common mistakes)]
    A --> E[Gợi nhớ phỏng vấn (Interview recall)]
```

## Liên kết tham khảo (Reference Links)

- https://docs.oracle.com/javase/tutorial/essential/concurrency/
