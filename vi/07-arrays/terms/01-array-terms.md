# Thuật Ngữ Về Mảng (Array Terms)

Tệp này chi tiết các thuật ngữ cốt lõi liên quan đến mảng (arrays) trong Java.

## Mảng (Array)

Một mảng (array) là một đối tượng chứa có kích thước cố định, đồng nhất (homogeneous) được cấp phát trên heap. Đồng nhất có nghĩa là tất cả các phần tử của nó bắt buộc phải thuộc cùng một kiểu dữ liệu.

## Chỉ Số (Index)

Một số nguyên xác định vị trí của một phần tử trong mảng. Trong Java, các chỉ số mảng bắt đầu từ số 0. Việc truy cập một chỉ số nằm ngoài khoảng `[0, array.length - 1]` sẽ kích hoạt một ngoại lệ `ArrayIndexOutOfBoundsException`.

## Mảng Răng Cưa / Mảng L lệch (Ragged Array / Jagged Array)

Một mảng đa chiều mà các mảng thành viên của nó có thể có độ dài khác nhau. Điều này khả thi vì Java biểu diễn các mảng đa chiều dưới dạng "mảng của các mảng".

```java
int[][] ragged = {
    {1, 2},
    {3, 4, 5},
    {6}
};
```

## Lỗi Lệch Một Đơn Vị (Off-by-One Error)

Một lỗi logic phổ biến khi vòng lặp duyệt qua thừa một lần hoặc thiếu một lần, thường do sử dụng toán tử `<=` thay vì `<` khi so sánh với độ dài của mảng.

```java
int[] arr = new int[5];
// for (int i = 0; i <= arr.length; i++) // Lỗi! Chỉ số 5 không tồn tại.
```

## `System.arraycopy`

Một phương thức native của Java giúp sao chép dữ liệu giữa các mảng ở cấp độ bộ nhớ hệ thống. Nó được tối ưu hóa cao và chạy nhanh hơn nhiều so với việc sao chép từng phần tử bằng vòng lặp Java thủ công đối với các mảng có kích thước lớn.

## Thuật Toán Sắp Xếp Nhanh Hai Chốt (Dual-Pivot Quicksort)

Thuật toán sắp xếp được sử dụng bởi phương thức `Arrays.sort()` dành cho các mảng có kiểu nguyên thủy. Nó có hiệu năng trung bình là $O(n \log n)$ và thường chạy nhanh hơn thuật toán quicksort một chốt tiêu chuẩn.

## Thuật Toán Timsort (Timsort)

Thuật toán sắp xếp được sử dụng bởi phương thức `Arrays.sort()` dành cho các mảng chứa kiểu đối tượng (mảng tham chiếu). Đây là một thuật toán sắp xếp lai (hybrid) ổn định được phát triển dựa trên sắp xếp trộn (merge sort) và sắp xếp chèn (insertion sort), được thiết kế để đạt hiệu năng rất tốt trên nhiều loại dữ liệu thực tế.

## `Arrays.deepEquals`

Một phương thức tiện ích thực hiện so sánh đệ quy các mảng đa chiều để kiểm tra xem chúng có bằng nhau một cách sâu sắc (deeply equal) hay không. Phương thức `Arrays.equals` tiêu chuẩn chỉ so sánh các phần tử ở cấp cao nhất (chính là các tham chiếu trong mảng 2 chiều) và sẽ thất bại đối với các cấu trúc mảng lồng nhau.

## `ArrayIndexOutOfBoundsException`

Một ngoại lệ runtime xảy ra khi chương trình cố gắng truy cập vào một chỉ số mảng bị âm, hoặc lớn hơn hoặc bằng độ dài của mảng.
