# Thuật Ngữ Runtime

File này giải thích các thuật ngữ liên quan đến runtime quan trọng xuất hiện trong các ghi chú tổng quan. Các thuật ngữ này được đề cập ngắn gọn trong các file lý thuyết chính, nhưng chúng đủ quan trọng để có thẻ giải thích riêng.

## Thuật Ngữ: Bytecode

### Định Nghĩa Ngắn Gọn

Bytecode là định dạng lệnh trung gian được lưu trong các file `.class` và được JVM thực thi.

### Tại Sao Nó Quan Trọng

Bytecode là một trong những lý do chính khiến Java có tính di động. Cùng một bytecode có thể được thực thi bởi các JVM tương thích trên nhiều nền tảng khác nhau.

### Nhầm Lẫn Thường Gặp

Bytecode không phải là mã nguồn Java. Nó cũng không phải là mã máy gốc (native machine code) dành cho một CPU cụ thể.

### Ví Dụ

```bash
javac HelloWorld.java
```

Lệnh này tạo ra `HelloWorld.class`, chứa bytecode.

## Thuật Ngữ: Runtime

### Định Nghĩa Ngắn Gọn

Runtime (thời gian chạy) là giai đoạn khi một chương trình đã biên dịch thực sự đang thực thi.

### Tại Sao Nó Quan Trọng

Một số vấn đề không thể phát hiện trong quá trình biên dịch. Chúng chỉ xảy ra khi chương trình thực thi với các giá trị hoặc đầu vào thực tế.

### Nhầm Lẫn Thường Gặp

Runtime không giống với compile time (thời gian biên dịch). Compile time kiểm tra và dịch mã nguồn. Runtime thực thi chương trình đã biên dịch.

## Thuật Ngữ: Class Loading (Nạp Lớp)

### Định Nghĩa Ngắn Gọn

Nạp lớp là quá trình JVM tìm kiếm và nạp các định nghĩa lớp cần thiết cho một chương trình Java đang chạy.

### Tại Sao Nó Quan Trọng

Các chương trình Java không nạp tất cả mọi lớp có thể ngay lập tức. Các lớp chỉ được nạp khi JVM cần đến chúng.

### Nhầm Lẫn Thường Gặp

Nạp lớp không giống với tạo đối tượng. Nạp một lớp làm cho định nghĩa của nó sẵn có; tạo một đối tượng tạo ra một thực thể (instance) runtime từ lớp đó.

## Thuật Ngữ: Reachability (Khả Năng Tiếp Cận)

### Định Nghĩa Ngắn Gọn

Khả năng tiếp cận mô tả liệu một đối tượng có thể được truy cập thông qua các tham chiếu đang hoạt động từ chương trình đang chạy hay không.

### Tại Sao Nó Quan Trọng

Garbage Collection (thu gom rác) phụ thuộc vào khả năng tiếp cận. Một đối tượng không thể tiếp cận được có thể bị thu hồi.

### Nhầm Lẫn Thường Gặp

Một đối tượng trở nên không thể tiếp cận không có nghĩa là nó bị thu gom ngay lập tức. Điều đó có nghĩa là Garbage Collector có thể thu hồi nó sau đó.
