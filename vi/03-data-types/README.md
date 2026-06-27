# 03 - Kiểu Dữ Liệu (Data Types)

## Những Gì Bạn Cần Học

Sau khi học xong chủ đề này, bạn có thể:

- Liệt kê 8 kiểu dữ liệu nguyên thủy của Java.
- Giải thích sự khác biệt giữa kiểu nguyên thủy (primitive type) và kiểu tham chiếu (reference type).
- Hiểu về literal và giá trị mặc định ở mức cơ bản.
- Giải thích chuyển đổi mở rộng (widening casting) và chuyển đổi thu hẹp (narrowing casting).
- Hiểu về lớp bọc (wrapper class), tự đóng hộp (autoboxing), và tháo hộp (unboxing).
- Giải thích `null` và `NullPointerException`.
- Sử dụng `==` và `.equals()` đúng cách.

## Thứ Tự Học

1. [Kiểu Nguyên Thủy](theory/01-primitive-types.md)
2. [Kiểu Tham Chiếu Và Mô Hình Bộ Nhớ](theory/02-reference-types.md)
3. [Literal, Casting Và Hành Vi Số](theory/03-literals-casting-numeric-behavior.md)
4. [Lớp Bọc, Boxing, Null Và So Sánh Bằng](theory/04-wrappers-null-equality.md)

## Bức Tranh Tổng Thể

```mermaid
flowchart TD
    A[Kiểu dữ liệu Java] --> B[Kiểu nguyên thủy]
    A --> C[Kiểu tham chiếu]
    B --> D[byte short int long]
    B --> E[float double]
    B --> F[char boolean]
    C --> G[String]
    C --> H[Array]
    C --> I[Class - Object]
    C --> J[Interface - Enum]
    C --> K[Lớp bọc]
```

## Thuật Ngữ Chính

- Kiểu nguyên thủy (Primitive type)
- Kiểu tham chiếu (Reference type)
- Giá trị chữ (Literal)
- Chuyển kiểu (Casting)
- Mở rộng (Widening)
- Thu hẹp (Narrowing)
- Lớp bọc (Wrapper class)
- Tự đóng hộp (Autoboxing)
- Tháo hộp (Unboxing)
- `null`
- `==`
- `.equals()`
- Stack
- Heap
- Integer Cache
- Bù hai (Two's complement)
- `NullPointerException`

## Tự Kiểm Tra

- Tại sao `String` không phải là kiểu nguyên thủy?
  → Xem [Tại Sao String Không Phải Nguyên Thủy](theory/02-reference-types.md#why-string-is-not-a-primitive)
- Tại sao `int` có thể lưu trực tiếp nhưng `String` được truy cập qua tham chiếu?
  → Xem [Mô Hình Bộ Nhớ Stack Và Heap](theory/02-reference-types.md#stack-and-heap-memory-model)
- Sự khác biệt giữa `int` và `Integer` là gì?
  → Xem [So Sánh Đầy Đủ int vs Integer](theory/04-wrappers-null-equality.md#int-vs-integer-full-comparison)
- Tại sao chuyển đổi thu hẹp có thể mất dữ liệu?
  → Xem [Tại Sao Thu Hẹp Có Thể Mất Dữ Liệu](theory/03-literals-casting-numeric-behavior.md#why-narrowing-can-lose-data)
- Tại sao so sánh nội dung String thường nên dùng `.equals()`?
  → Xem [Tại Sao Dùng .equals() Cho Nội Dung String](theory/04-wrappers-null-equality.md#why-use-equals-for-string-content)
- Tại sao tháo hộp một wrapper null gây ra ngoại lệ?
  → Xem [Tại Sao Tháo Hộp null Ném NullPointerException](theory/04-wrappers-null-equality.md#why-unboxing-null-throws-nullpointerexception)

## Thẻ Anki

- [Thẻ cơ bản](anki/basic.tsv)
- [Thẻ cơ bản có giải thích thêm](anki/basic-extra.tsv)
- [Thẻ Cloze](anki/cloze.tsv)
- [Thẻ câu hỏi code](anki/code-question.tsv)

## Ghi Chú Của Tôi

-

## Liên Kết Tham Khảo

- https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
