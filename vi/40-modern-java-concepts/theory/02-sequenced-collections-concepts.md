# Các Khái Niệm Java Hiện Đại Cần Biết - Phần 2 (Modern Java Concepts To Know - Part 2)

## Mục Tiêu Học Tập (Learning Goal)

Tài liệu này trình bày các khái niệm về Sequenced Collections và String Templates. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Khái Quát Nội Dung (Outline Coverage)

| Khái niệm (Concept) | Điều cần biết (What to know) |
| --- | --- |
| `Sequenced Collections` | Các giao diện được giới thiệu trong Java 21 đại diện cho các bộ sưu tập có thứ tự gặp gỡ được xác định (defined encounter order). |
| `String templates were once preview; currently they should not be used as a stable feature` | Trạng thái và các giải pháp thay thế cho tính năng xem trước Mẫu chuỗi (String Templates) đã bị loại bỏ của Java 21. |

---

## Ghi Chú Chi Tiết (Detailed Notes)

### Sequenced Collections (Bộ sưu tập có thứ tự)

Sequenced Collections (được giới thiệu từ Java 21) đồng nhất các bộ sưu tập có phần tử đầu tiên và cuối cùng được xác định rõ ràng, cung cấp một API tiêu chuẩn cho việc truy xuất, sửa đổi và các chế độ xem đảo ngược thứ tự (reverse-order views).

- **Phân cấp Giao diện (Interface Hierarchy)**:
  - `SequencedCollection<E>` (được kế thừa bởi `List`, `Deque`, và `SequencedSet`)
  - `SequencedSet<E>` (được kế thừa bởi `LinkedHashSet` và `SortedSet`)
  - `SequencedMap<K, V>` (được kế thừa bởi `LinkedHashMap` và `SortedMap`)

- **Ví dụ có thể chạy được (Runnable Example)**:
  ```java
  SequencedCollection<String> coll = new ArrayList<>(List.of("one", "two", "three"));

  // Uniform retrieval
  String first = coll.getFirst(); // "one"
  String last = coll.getLast();   // "three"

  // Uniform modification
  coll.addFirst("zero");
  coll.addLast("four");

  // Reverse view (runs in O(1) time without copying elements)
  SequencedCollection<String> reversed = coll.reversed();
  System.out.println(reversed.getFirst()); // "four"
  ```

- **Sai lầm thường gặp / Chế độ thất bại (Common Mistake / Failure Mode)**:
  - **Bộ sưu tập Rỗng (Empty Collections)**: Gọi `getFirst()` hoặc `getLast()` trên một bộ sưu tập rỗng sẽ ném ra ngoại lệ `NoSuchElementException` trong thời gian chạy.
  - **Tính khả biến đảo ngược (Reversed Mutability)**: Bộ sưu tập được trả về bởi `reversed()` là một chế độ xem (view), không phải một bản sao (copy). Việc sửa đổi chế độ xem đảo ngược sẽ trực tiếp làm thay đổi bộ sưu tập gốc cơ sở.

---

### Mẫu chuỗi từng là tính năng xem trước; hiện tại không nên sử dụng như một tính năng ổn định (String templates were once preview; currently they should not be used as a stable feature)

Mẫu chuỗi (String Templates - ví dụ: `STR."Hello \{name}"`) được giới thiệu dưới dạng tính năng xem trước (preview feature) trong Java 21. Tuy nhiên, dựa trên phản hồi của cộng đồng, chúng đã bị **loại bỏ** trong các bản phát hành tiếp theo (Java 22+) và không được tiến hành chuẩn hóa.

- **Thực hành đúng (Correct Practice)**:
  - Tránh sử dụng Mẫu chuỗi (`STR.`) trong bất kỳ mã nguồn tiêu chuẩn hoặc mã chạy thực tế (production code) nào, vì chúng sẽ gây ra lỗi biên dịch trong các phiên bản JDK hiện đại.
  - **Các giải pháp thay thế tiêu chuẩn (Standard Alternatives)**:
    Sử dụng phép nối chuỗi truyền thống, `String.format()`, hoặc phương thức thực thể `String.formatted()` (phương thức này sạch sẽ nhất):
    ```java
    String name = "Alice";
    
    // Concatenation
    String message1 = "Hello " + name;

    // String.format
    String message2 = String.format("Hello %s", name);

    // String.formatted (cleanest alternative)
    String message3 = "Hello %s".formatted(name);
    ```
