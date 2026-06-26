# Thuật ngữ về Generics (Generics Terms)

Sử dụng tài liệu này khi một từ ngữ trong lý thuyết có vẻ quá cô đọng. Mỗi thuật ngữ đều đi kèm ý nghĩa, tầm quan trọng, hiểu lầm phổ biến và một ví dụ nhỏ.

## Tham số kiểu (type parameter)

Một tên giữ chỗ (chẳng hạn như `T`, `E`, `K`, `V`) được khai báo trên các lớp, giao diện hoặc phương thức generic. Nó được thay thế bằng một đối số kiểu (type argument) cụ thể khi lớp được khởi tạo hoặc phương thức được gọi, cho phép cùng một đoạn mã hoạt động với các kiểu dữ liệu khác nhau trong khi vẫn bảo toàn tính an toàn kiểu dữ liệu.

- **Tầm quan trọng**: Nó cho phép trình biên dịch thực thi các ràng buộc kiểu ở thời điểm biên dịch (ví dụ: đảm bảo một `List<String>` chỉ chứa các chuỗi ký tự) mà không yêu cầu nhà phát triển phải ép kiểu thủ công, giúp ngăn chặn các ngoại lệ lúc chạy (runtime exceptions).
- **Hiểu lầm phổ biến**: Nhầm lẫn *tham số kiểu (type parameter)* với *đối số kiểu (type argument)*. Tham số kiểu là tên giữ chỗ được khai báo trong chữ ký lớp (như `T` trong `Box<T>`), trong khi đối số kiểu là kiểu dữ liệu thực tế được sử dụng khi khởi tạo lớp (như `String` trong `Box<String>`).
- **Ví dụ nhỏ**:
```java
// T is the type parameter
public class Box<T> {
    private T item;
    public void set(T item) { this.item = item; }
    public T get() { return item; }
}
```

## Tham số kiểu có giới hạn (bounded type parameter)

Một tham số kiểu giới hạn phạm vi của các đối số kiểu được phép bằng cách sử dụng từ khóa `extends` (ví dụ: `T extends Number`). Nó đảm bảo rằng bất kỳ đối số kiểu nào được cung cấp khi khởi tạo sẽ là một kiểu con của giới hạn được chỉ định.

- **Tầm quan trọng**: Việc giới hạn một tham số kiểu cho phép bạn gọi các phương thức được định nghĩa trên giới hạn đó (chẳng hạn như gọi `doubleValue()` trên một kiểu được giới hạn bởi `Number`) bên trong lớp generic, mà không cần phải ép kiểu đối tượng generic đó.
- **Hiểu lầm phổ biến**: Nghĩ rằng `extends` chỉ có nghĩa là kế thừa (Inheritance) lớp. Trong tham số kiểu có giới hạn, `extends` được sử dụng cho cả lớp và giao diện (ví dụ: `T extends Comparable<T>` trong đó `Comparable` là một giao diện). Java không sử dụng từ khóa `implements` cho các giới hạn generic.
- **Ví dụ nhỏ**:
```java
// T is bounded by Number, allowing access to Number methods
public class Calculator<T extends Number> {
    public double doubleValueOf(T value) {
        return value.doubleValue(); // Valid because T is at least a Number
    }
}
```

## Ký tự đại diện (wildcard)

Ký tự đại diện `?` đại diện cho một kiểu dữ liệu chưa xác định trong các khai báo tham số generic. Nó có thể không bị giới hạn (`<?>`), giới hạn trên (`<? extends T>`), hoặc giới hạn dưới (`<? super T>`), cho phép các phương thức chấp nhận các đối số được tham số hóa thuộc các kiểu khác nhau.

- **Tầm quan trọng**: Ký tự đại diện cho phép các nhà phát triển viết các API linh hoạt chấp nhận các kiểu generic có liên quan (ví dụ: cho phép một phương thức xử lý cả `List<Integer>` và `List<Double>` bằng cách sử dụng `List<? extends Number>`), điều mà bình thường không thể thực hiện được do tính bất biến (invariance) của generics.
- **Hiểu lầm phổ biến**: Tin rằng các khai báo ký tự đại diện hoạt động giống như các biến kiểu thông thường. Bạn không thể sử dụng một ký tự đại diện (`?`) bên trong thân phương thức để khai báo biến hoặc khởi tạo đối tượng (ví dụ: `? item = list.get(0)` là không hợp lệ).
- **Ví dụ nhỏ**:
```java
// Accepts any list of Number or its subclasses
public static void printNumbers(List<? extends Number> list) {
    for (Number n : list) {
        System.out.println(n);
    }
}
```

## PECS

Một từ viết tắt của **Producer Extends, Consumer Super** (Nhà sản xuất dùng Extends, Người tiêu dùng dùng Super), giúp hướng dẫn việc lựa chọn giới hạn ký tự đại diện (wildcard bounds). Sử dụng `? extends T` khi cấu trúc generic sản xuất ra dữ liệu (chỉ đọc), và `? super T` khi nó tiêu thụ dữ liệu (chỉ ghi).

- **Tầm quan trọng**: Nó giải quyết sự xung đột giữa tính bất biến của generic và tính linh hoạt của kiểu con (subtyping). Việc tuân theo PECS cho phép bạn viết các phương thức tiện ích có thể tái sử dụng để đọc từ và ghi vào các tập hợp thuộc các cấp phân cấp khác nhau mà không gặp lỗi an toàn kiểu dữ liệu.
- **Hiểu lầm phổ biến**: Cố gắng sử dụng `? extends T` cho các tập hợp nơi bạn cần chèn thêm phần tử. Một danh sách giới hạn bởi nhà sản xuất (`? extends T`) là chỉ đọc vì trình biên dịch không thể xác định lớp con chính xác lúc chạy, khiến tất cả các thao tác thêm mới (ngoại trừ `null`) đều bị báo lỗi biên dịch.
- **Ví dụ nhỏ**:
```java
// dest consumes elements (super), src produces elements (extends)
public static <T> void copy(List<? super T> dest, List<? extends T> src) {
    for (T item : src) {
        dest.add(item); // Safe write to dest, safe read from src
    }
}
```

## Xóa bỏ kiểu (type erasure)

Quá trình xảy ra tại thời điểm biên dịch mà qua đó trình biên dịch Java loại bỏ tất cả các đối số kiểu generic khỏi khai báo lớp, giao diện và phương thức generic. Mã byte (bytecode) thu được chỉ chứa các kiểu thô (raw types), với các phép ép kiểu ngầm định được chèn vào nơi cần thiết.

- **Tầm quan trọng**: Nó đảm bảo tính tương thích ngược, cho phép mã nguồn generic sau khi biên dịch có thể chạy mượt mà trên các phiên bản JVM cũ hơn và tương tác với các thư viện trước Java 5 vốn chỉ sử dụng kiểu thô.
- **Hiểu lầm phổ biến**: Nghĩ rằng thông tin kiểu generic bị mất hoàn toàn. Trong khi các tham số kiểu trên các biến và thực thể bị xóa bỏ, siêu dữ liệu của chính lớp đó (ví dụ: `class MyList<T>`) vẫn được giữ lại trong định nghĩa tệp class và có thể được truy cập thông qua phản chiếu (reflection).
- **Ví dụ nhỏ**:
```java
// What you write:
List<String> list = new ArrayList<>();
list.add("hello");
String s = list.get(0);

// What the compiler generates in bytecode:
List list = new ArrayList();
list.add("hello");
String s = (String) list.get(0); // Inserted cast
```

## Kiểu thô (raw type)

Tên của một lớp hoặc giao diện generic được sử dụng mà không đi kèm bất kỳ đối số kiểu nào (ví dụ: sử dụng `List` thay vì `List<String>`). Các kiểu thô hoạt động giống như trước Java 5, coi tất cả các tham số kiểu là giới hạn trên của chúng (thường là `Object`).

- **Tầm quan trọng**: Các kiểu thô cho phép mã nguồn cũ (legacy code) chạy trên các phiên bản Java hiện đại. Tuy nhiên, chúng vô hiệu hóa tất cả các kiểm tra an toàn kiểu dữ liệu lúc biên dịch, chuyển các lỗi không khớp kiểu thành các ngoại lệ tại thời điểm chạy (runtime exceptions).
- **Hiểu lầm phổ biến**: Tin rằng kiểu thô và ký tự đại diện không giới hạn (unbounded wildcards) là hoàn toàn giống nhau. Mặc dù cả hai đều có thể tham chiếu đến bất kỳ kiểu nào, `List` (kiểu thô) cho phép bạn thêm bất kỳ đối tượng nào (không an toàn), trong khi `List<?>` (ký tự đại diện) ngăn cản việc thêm phần tử (an toàn), giúp thực thi các bất biến lúc biên dịch.
- **Ví dụ nhỏ**:
```java
List rawList = new ArrayList(); // Raw type
rawList.add("String");
rawList.add(Integer.valueOf(100)); // Compiles, but risks ClassCastException on read

List<?> wildcardList = new ArrayList<String>();
// wildcardList.add("String"); // Compile Error: compiler prevents additions for safety
```
