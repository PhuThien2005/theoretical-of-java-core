# Thuật Ngữ Về Mảng (Array Terms)

Tài liệu này chi tiết hóa các thuật ngữ chính liên quan đến mảng (array) trong Java.

## Mảng (Array)

Mảng là một đối tượng vùng chứa có kích thước cố định, đồng nhất được cấp phát trên bộ nhớ Heap. Đồng nhất có nghĩa là tất cả các phần tử của nó phải thuộc cùng một kiểu dữ liệu.

## Chỉ Số (Index)

Một số nguyên xác định vị trí của một phần tử trong mảng. Trong Java, các chỉ số mảng bắt đầu từ số 0. Việc truy cập vào một chỉ số nằm ngoài phạm vi `[0, array.length - 1]` sẽ kích hoạt ngoại lệ `ArrayIndexOutOfBoundsException`.

## Mảng So Le (Ragged Array / Jagged Array)

Một mảng đa chiều trong đó các mảng thành viên có thể có độ dài khác nhau. Điều này khả thi vì Java biểu diễn mảng đa chiều dưới dạng "mảng của các mảng".

```java
int[][] ragged = {
    {1, 2},
    {3, 4, 5},
    {6}
};
```

## Lỗi Chênh Lệch 1 Đơn Vị (Off-by-One Error)

Một lỗi logic phổ biến khi một vòng lặp chạy thừa một lần hoặc thiếu một lần, thường xảy ra do sử dụng toán tử `<=` thay vì `<` khi kiểm tra điều kiện với độ dài của mảng.

```java
int[] arr = new int[5];
// for (int i = 0; i <= arr.length; i++) // Lỗi! Chỉ số 5 không tồn tại.
```

## Phương Thức `System.arraycopy` (`System.arraycopy`)

Một phương thức thuần bản địa (native) của Java thực hiện sao chép dữ liệu giữa các mảng ở cấp độ bộ nhớ hệ thống. Nó được tối ưu hóa cao và hoạt động nhanh hơn việc sao chép từng phần tử bằng vòng lặp Java đối với các mảng có kích thước lớn.

## Sắp Xếp Nhanh Hai Chốt (Dual-Pivot Quicksort)

Thuật toán sắp xếp được phương thức `Arrays.sort()` sử dụng cho các mảng kiểu dữ liệu nguyên thủy. Nó có hiệu suất trung bình là $O(n \log n)$ và thường chạy nhanh hơn thuật toán sắp xếp nhanh một chốt (single-pivot quicksort) tiêu chuẩn.

## Thuật Toán Sắp Xếp Timsort (Timsort)

Thuật toán sắp xếp được phương thức `Arrays.sort()` sử dụng cho các mảng kiểu đối tượng. Đây là một thuật toán sắp xếp lai, ổn định được phát triển từ sắp xếp trộn (merge sort) và sắp xếp chèn (insertion sort), được thiết kế để hoạt động tốt trên nhiều loại dữ liệu thực tế.

## Phương Thức `Arrays.deepEquals` (`Arrays.deepEquals`)

Một phương thức tiện ích thực hiện so sánh đệ quy các mảng đa chiều để kiểm tra xem chúng có bằng nhau một cách chuyên sâu hay không. Phương thức `Arrays.equals` tiêu chuẩn chỉ so sánh các phần tử ở cấp cao nhất (vốn là các tham chiếu trong mảng 2 chiều) và sẽ thất bại đối với các cấu trúc mảng lồng nhau.

## Ngoại Lệ `ArrayIndexOutOfBoundsException` (`ArrayIndexOutOfBoundsException`)

Một ngoại lệ runtime được ném ra khi mã nguồn cố gắng truy cập vào một chỉ số mảng là số âm, hoặc lớn hơn hoặc bằng độ dài của mảng.
