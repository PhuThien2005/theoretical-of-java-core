# Các Khái Niệm Java Hiện Đại Cần Biết - Phần 2

## Mục Tiêu Học Tập

File này trình bày về Sequenced Collections và String Templates. Hãy học từng khái niệm như một quy tắc Java thực tế.

## Nội Dung Đề Cương

| Khái niệm | Cần biết |
| --- | --- |
| `Sequenced Collections` | Các interface được giới thiệu trong Java 21 đại diện cho các collection có thứ tự gặp gỡ (encounter order) xác định. |
| `String templates từng là tính năng xem trước; hiện tại không nên dùng như một tính năng ổn định` | Trạng thái và các lựa chọn thay thế cho tính năng xem trước String Templates của Java 21 đã bị xóa. |

---

## Ghi Chú Chi Tiết

### Sequenced Collections

Sequenced Collections (tập hợp có thứ tự - được giới thiệu trong Java 21) thống nhất các collection có phần tử đầu và cuối xác định, cung cấp API chuẩn để truy xuất, thay đổi và xem theo thứ tự ngược.

- **Phân Cấp Interface**:
  - `SequencedCollection<E>` (được mở rộng bởi `List`, `Deque`, và `SequencedSet`)
  - `SequencedSet<E>` (được mở rộng bởi `LinkedHashSet` và `SortedSet`)
  - `SequencedMap<K, V>` (được mở rộng bởi `LinkedHashMap` và `SortedMap`)

- **Ví dụ chạy được**:
  ```java
  SequencedCollection<String> coll = new ArrayList<>(List.of("one", "two", "three"));

  // Truy xuất thống nhất
  String first = coll.getFirst(); // "one"
  String last = coll.getLast();   // "three"

  // Thay đổi thống nhất
  coll.addFirst("zero");
  coll.addLast("four");

  // Xem ngược (chạy trong thời gian O(1) không cần sao chép phần tử)
  SequencedCollection<String> reversed = coll.reversed();
  System.out.println(reversed.getFirst()); // "four"
  ```

- **Lỗi Thường Gặp / Trường Hợp Thất Bại**:
  - **Collection Rỗng**: Gọi `getFirst()` hoặc `getLast()` trên collection rỗng ném ra `NoSuchElementException` tại runtime.
  - **Tính Biến Đổi Của Reversed**: Collection trả về bởi `reversed()` là view (chế độ xem), không phải bản sao. Thay đổi view ngược trực tiếp cũng thay đổi collection gốc.

---

### String templates từng là tính năng xem trước; hiện tại không nên dùng như một tính năng ổn định

String Templates (ví dụ: `STR."Hello \{name}"`) được giới thiệu như tính năng xem trước trong Java 21. Tuy nhiên, do phản hồi, chúng đã bị **xóa** trong các phiên bản tiếp theo (Java 22+) và không tiến hành chuẩn hóa.

- **Thực Hành Đúng**:
  - Tránh dùng String Templates (`STR.`) trong bất kỳ code chuẩn hoặc production nào, vì chúng sẽ gây lỗi biên dịch trong các phiên bản JDK hiện đại.
  - **Các Lựa Chọn Thay Thế Chuẩn**:
    Dùng nối chuỗi truyền thống, `String.format()`, hoặc phương thức instance `String.formatted()`:
    ```java
    String name = "Alice";
    
    // Nối chuỗi
    String message1 = "Hello " + name;

    // String.format
    String message2 = String.format("Hello %s", name);

    // String.formatted (lựa chọn thay thế gọn nhất)
    String message3 = "Hello %s".formatted(name);
    ```
