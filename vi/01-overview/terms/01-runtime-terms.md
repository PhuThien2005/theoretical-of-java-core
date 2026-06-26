# Điều khoản thời gian chạy (Runtime) (Runtime Terms)

Tệp này giải thích các thuật ngữ quan trọng liên quan đến thời gian chạy xuất hiện trong các ghi chú tổng quan. Những thuật ngữ này tuy ngắn gọn trong các hồ sơ lý thuyết chính, nhưng chúng đủ quan trọng để xứng đáng có những thẻ giải thích tập trung.

## Thuật ngữ: Mã byte (Term: Bytecode)

### Định nghĩa ngắn gọn (Short Definition)

Mã byte là định dạng lệnh trung gian được lưu trữ trong các tệp `.class` và được JVM thực thi.

### Tại sao nó quan trọng (Why It Matters)

Bytecode là một trong những lý do chính khiến Java có thể di động được. Cùng một mã byte có thể được thực thi bởi các JVM tương thích trên các nền tảng khác nhau.

### Nhầm lẫn chung (Common Confusion)

Bytecode không phải là mã nguồn Java. Nó cũng không phải là mã máy gốc cho một CPU cụ thể.

### Ví dụ (Example)

```bash
javac HelloWorld.java
```

Lệnh này tạo ra `HelloWorld.class` , chứa mã byte.

## Thuật ngữ: Thời gian chạy (Term: Runtime)

### Định nghĩa ngắn gọn (Short Definition)

Thời gian chạy là giai đoạn khi một chương trình được biên dịch thực sự đang thực thi.

### Tại sao nó quan trọng (Why It Matters)

Một số vấn đề không được hiển thị trong quá trình biên dịch. Chúng chỉ xảy ra khi chương trình thực thi với giá trị thực hoặc đầu vào thực.

### Nhầm lẫn chung (Common Confusion)

Thời gian chạy không giống như thời gian biên dịch (Compile time). Biên dịch kiểm tra thời gian và dịch mã nguồn. Thời gian chạy thực thi chương trình đã biên dịch.

## Học kỳ: Đang tải lớp (Term: Class Loading)

### Định nghĩa ngắn gọn (Short Definition)

Tải lớp là quá trình JVM tìm và tải các định nghĩa lớp cần thiết cho một chương trình Java đang chạy.

### Tại sao nó quan trọng (Why It Matters)

Các chương trình Java không tải mọi lớp có thể ngay lập tức. Các lớp được tải khi JVM cần chúng.

### Nhầm lẫn chung (Common Confusion)

Tải lớp không giống như tạo đối tượng. Việc tải một lớp sẽ làm cho định nghĩa của nó có sẵn; việc tạo một đối tượng sẽ tạo một phiên bản thời gian chạy từ một lớp.

## Thuật ngữ: Khả năng tiếp cận (Reachability) (Term: Reachability)

### Định nghĩa ngắn gọn (Short Definition)

Khả năng tiếp cận mô tả liệu một đối tượng vẫn có thể được truy cập thông qua các tham chiếu hoạt động từ chương trình đang chạy hay không.

### Tại sao nó quan trọng (Why It Matters)

Việc thu gom rác (Garbage Collection) phụ thuộc vào khả năng tiếp cận. Một đối tượng không thể truy cập có thể được thu hồi.

### Nhầm lẫn chung (Common Confusion)

Một đối tượng không thể truy cập được không có nghĩa là nó sẽ được thu thập ngay lập tức. Điều đó có nghĩa là Người thu gom rác có thể lấy lại nó sau.
