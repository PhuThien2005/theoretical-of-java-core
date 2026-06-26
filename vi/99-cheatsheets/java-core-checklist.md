# Danh sách kiểm tra Java Core (Java Core Checklist)

## Nên thuộc (Nên thuộc)

- JVM, JRE, JDK
- Kiểu nguyên thủy so với kiểu tham chiếu (Primitive vs reference type)
- `==` so với `.equals()`
- `static`, `final`, `this`, `super`
- Hàm khởi tạo (Constructor)
- Nạp chồng (Overloading) so với Ghi đè (Overriding)
- Đóng gói (Encapsulation), Kế thừa (Inheritance), Đa hình (Polymorphism), Trừu tượng (Abstraction)
- Giao diện (Interface) so với Lớp trừu tượng (Abstract class)
- List, Set, Map
- Ngoại lệ Checked so với Unchecked (Checked vs unchecked exception)
- Kiểu Generic (Generic type)
- Lambda và giao diện chức năng (Lambda and functional interface)
- Đường ống Stream (Stream pipeline)
- Try-with-resources

## Cú pháp hay dùng (Cú pháp hay dùng)

```java
if (condition) {
    // ...
} else {
    // ...
}

for (int i = 0; i < 10; i++) {
    // ...
}

for (String item : items) {
    // ...
}

try {
    // ...
} catch (Exception e) {
    // ...
}
```

## Câu hỏi phỏng vấn Intern/Fresher (Intern/Fresher Interview Questions)

- Java có phải là truyền tham chiếu (pass-by-reference) không?
- Vì sao String là bất biến (immutable)?
- `ArrayList` và `LinkedList` khác nhau như thế nào?
- Ý tưởng hoạt động của `HashMap` như thế nào?
- Khi nào cần ghi đè (override) `equals()` và `hashCode()`?
- `final`, `finally`, `finalize` khác nhau như thế nào?
- An toàn đa luồng (Thread safety) là gì?
