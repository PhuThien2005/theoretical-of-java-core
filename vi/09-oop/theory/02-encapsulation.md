# Đóng Gói (Encapsulation)

Đóng gói (encapsulation) là nguyên lý OOP thực hiện gom nhóm dữ liệu (các trường thực thể - instance fields) và các phương thức thao tác trên dữ liệu đó vào một đơn vị duy nhất (một lớp - class), đồng thời giới hạn quyền truy cập trực tiếp vào một số thành phần của đối tượng. Khái niệm này còn được gọi là **ẩn giấu dữ liệu (data hiding)**.

---

## Các Bổ Từ Truy Cập Và Các Cấp Độ Phạm Vi (Access Modifiers and Scope Levels)

Java cung cấp các bổ từ truy cập (access modifiers) để giới hạn phạm vi hiển thị ở hai cấp độ:
1. **Cấp độ lớp (Class-level):** Một lớp ngoài cùng (top-level class) chỉ có thể được khai báo là `public` hoặc mặc định (default / package-private). Lớp ngoài cùng không được phép khai báo là `private` hoặc `protected` (ngoại trừ các lớp lồng nhau / lớp nội bộ - nested/inner classes).
2. **Cấp độ thành viên (Member-level):** Các trường dữ liệu, phương thức và constructor có thể sử dụng cả bốn cấp độ hiển thị.

| Bổ từ truy cập (Modifier) | Bên trong cùng Class | Bên trong cùng Package | Lớp con ở Package khác | Mọi nơi (World) |
| :--- | :---: | :---: | :---: | :---: |
| **`private`** | Có | Không | Không | Không |
| **`default`** (không bổ từ) | Có | Có | Không | Không |
| **`protected`** | Có | Có | Có (qua kế thừa) | Không |
| **`public`** | Có | Có | Có | Có |

### Chi Tiết Về Các Phạm Vi Key:
- **`private`:** Giới hạn quyền truy cập nghiêm ngặt chỉ dành cho các thành viên bên trong lớp định nghĩa nó. Đây là cấp độ được khuyến nghị hàng đầu cho tất cả các biến thực thể.
- **`default` (package-private):** Các thành viên chỉ có thể được truy cập bởi các lớp nằm trong cùng một package.
- **`protected`:** Có thể được truy cập bởi các lớp trong cùng package, và bởi các lớp con (subclasses) nằm ở các package khác.
  *Lưu ý:* Một lớp con ở package khác chỉ có thể truy cập thành viên `protected` của lớp cha thông qua cơ chế kế thừa (sử dụng biến tham chiếu thuộc kiểu lớp con), chứ không thể truy cập qua biến tham chiếu thuộc kiểu lớp cha.
- **`public`:** Quyền truy cập hoàn toàn không bị giới hạn.

---

## Các Mẫu Thiết Kế Getter Và Setter (Getter and Setter Design Patterns)

Để tương tác với các trường dữ liệu private, các lớp sẽ bộc lộ các phương thức truy cập công khai (getters - accessor methods) và các phương thức thiết lập công khai (setters - mutator methods).

### Ví dụ Đóng Gói Tiêu Chuẩn
Dưới đây là một lớp minh họa cấu trúc đóng gói tiêu chuẩn: các trường dữ liệu private, một parameterized constructor, và các phương thức getter/setter công khai để truy cập và sửa đổi trạng thái một cách có kiểm soát.

```java
public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        setSalary(salary); // Bắt buộc kiểm tra hợp lệ ngay khi tạo đối tượng
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        } else {
            throw new IllegalArgumentException("Lương không được phép bị âm.");
        }
    }
}
```

Mẫu thiết kế này mang lại nhiều lợi ích thiết kế phần mềm:

### 1. Kiểm Tra Tính Hợp Lệ Dữ Liệu (Data Validation)
Các phương thức Setter có thể chặn các đầu vào không hợp lệ để ngăn chặn trạng thái đối tượng bị sai lệch:
```java
public void setAge(int age) {
    if (age >= 0 && age <= 120) {
        this.age = age;
    } else {
        throw new IllegalArgumentException("Tuổi không hợp lệ: " + age);
    }
}
```

### 2. Kiểm Soát Chỉ Đọc (Read-Only) và Chỉ Ghi (Write-Only)
- **Lớp Chỉ Đọc (Read-Only Class):** Chỉ bộc lộ các phương thức getter và không cung cấp setter. Rất hữu ích để tạo ra các container chứa trạng thái bất biến.
- **Lớp Chỉ Ghi (Write-Only Class):** Chỉ bộc lộ các phương thức setter và không cung cấp getter (ví dụ: cập nhật thông tin mật khẩu bảo mật hoặc hệ thống ghi log chỉ ghi).

### 3. Sao Chép Phòng Thủ (Defensive Copying - Cực kỳ quan trọng đối với kiểu tham chiếu)
Việc bộc lộ các getter cho các đối tượng khả biến (như mảng hoặc danh sách list) sẽ phá vỡ tính đóng gói vì bên gọi có thể sửa đổi trực tiếp đối tượng thông qua tham chiếu được trả về. Các getter nên trả về các bản sao phòng thủ (defensive copies):

```java
class Team {
    private List<String> members = new ArrayList<>();

    // Getter dễ bị tổn thương:
    // public List<String> getMembers() { return this.members; } // Bên gọi có thể chạy lệnh getMembers().clear()!

    // Getter được đóng gói an toàn:
    public List<String> getMembers() {
        return new ArrayList<>(this.members); // Trả về một bản sao độc lập
    }
}
```

---

## Lợi Ích Của Tính Đóng Gói (Benefits of Encapsulation)

1. **Tính Linh Hoạt và Dễ Bảo Trì (Flexibility and Maintainability):** Bạn có thể thay đổi cấu trúc dữ liệu nội bộ của một lớp mà không làm ảnh hưởng đến mã nguồn bên ngoài đang phụ thuộc vào nó. Ví dụ, việc thay đổi một trường từ `private int age` thành `private LocalDate birthDate` hoàn toàn có thể thực hiện được trong khi chữ ký phương thức `getAge()` vẫn hoạt động bình thường bằng cách tính toán tuổi động ngay khi gọi.
2. **Liên Kết Lỏng Lẻo (Loose Coupling):** Giảm thiểu sự phụ thuộc lẫn nhau giữa các lớp, giúp các thành phần trở nên mô-đun hóa và dễ thực hiện kiểm thử đơn vị (unit test) hơn.
3. **Bảo Mật (Security):** Bảo vệ các trường dữ liệu khỏi các sửa đổi tùy tiện ngoài ý muốn từ bên ngoài lớp.

## Đi Sâu: Đóng Gói Không Chỉ Đơn Thuần Là Viết Getters Và Setters (Deep Review: Encapsulation Is More Than Getters And Setters)

Đóng gói không phải là thói quen tự động tạo ra các phương thức getter và setter cho mọi trường dữ liệu. Nó là thói quen bảo vệ các bất biến (invariants) của đối tượng đằng sau một API được thiết kế có chủ đích.

Một thiết kế yếu kém sẽ bộc lộ gián tiếp mọi trường dữ liệu ra ngoài:

```java
class User {
    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

Một thiết kế mạnh mẽ hơn sẽ bộc lộ hành vi thay vì bộc lộ trạng thái thô:

```java
class User {
    private String passwordHash;

    public boolean matchesPassword(String rawPassword) {
        return Passwords.matches(rawPassword, passwordHash);
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!matchesPassword(oldPassword)) {
            throw new IllegalArgumentException("Mật khẩu cũ không chính xác");
        }
        this.passwordHash = Passwords.hash(newPassword);
    }
}
```

---

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Nhắm Mắt Tạo Getters/Setters Cho Mọi Trường
Việc tự động tạo getter và setter công khai cho tất cả các trường dữ liệu biến lớp đó trở thành một túi chứa trạng thái toàn cục, cho phép mã nguồn bên ngoài phá vỡ các quy tắc của đối tượng. Setter chỉ nên tồn tại nếu việc sửa đổi đó được cho phép về mặt logic và được kiểm tra hợp lệ.

### 2. Rò Rỉ Tham Chiếu Nội Bộ (Leaking Internal References - Phá vỡ tính đóng gói)
Bộc lộ các getter trả về trực tiếp tham chiếu của các cấu trúc khả biến nội bộ (ví dụ: `List`, `Map`, mảng) cho phép bên gọi thay đổi trực tiếp các tập hợp này từ bên ngoài.
```java
class Wallet {
    private List<Coin> coins = new ArrayList<>();
    // Lỗi: trả về trực tiếp tham chiếu nội bộ
    public List<Coin> getCoins() { return coins; } 
}
// Bên gọi có thể làm: wallet.getCoins().clear(); // Bỏ qua hoàn toàn sự kiểm soát của Wallet!
```
**Khắc phục:** Trả về một lớp bao bọc không thể sửa đổi (unmodifiable wrapper) hoặc một bản sao phòng thủ:
```java
public List<Coin> getCoins() { return Collections.unmodifiableList(coins); }
```

### 3. Để Các Trường Ở Phạm Vi Package-Private (Quên bổ từ `private`)
Việc bỏ qua bổ từ truy cập sẽ đưa biến về phạm vi mặc định package-private, cho phép bất kỳ lớp nào khác trong cùng package sửa đổi trực tiếp các trường dữ liệu đó. Hãy luôn khai báo các trường là `private` theo mặc định.
```java
class Account {
    double balance; // Thiếu private! Bất kỳ lớp nào trong package đều có thể ghi account.balance = -9999;
}
```

### Danh Sách Kiểm Tra Đóng Gói (Encapsulation Checklist)

- Luôn khai báo các trường dữ liệu là private theo mặc định.
- Bộc lộ các phương thức mô tả hành vi, chứ không chỉ mô tả nơi lưu trữ.
- Kiểm tra tính hợp lệ của dữ liệu đầu vào trước khi thay đổi trạng thái đối tượng.
- Trả về các bản sao phòng thủ đối với các đối tượng khả biến nội bộ.
- Tránh viết các setter có thể đưa đối tượng vào trạng thái không hợp lệ.
- Ưu tiên sử dụng các đối tượng bất biến khi trạng thái không cần phải thay đổi.

## Liên Kết Tham Khảo (Reference Links)

- Tài liệu hướng dẫn Oracle Java - Các khái niệm OOP: https://docs.oracle.com/javase/tutorial/java/concepts/
- Tài liệu hướng dẫn Oracle Java - Lớp và Đối tượng: https://docs.oracle.com/javase/tutorial/java/javaOO/index.html
- Tài liệu Dev.java - Tổng quan OOP: https://dev.java/learn/oop/

---

## Tại Sao Các Biến Thực Thể Nên Khai Báo Private (Why Instance Variables Should Be Private)

Khi một biến thực thể được khai báo là `private`, trình biên dịch Java sẽ thực thi một ranh giới truy cập nghiêm ngặt ngay ở cấp độ mã nguồn: mọi nỗ lực đọc hoặc ghi trường dữ liệu đó từ bên ngoài lớp khai báo đều tạo ra một lỗi ở thời điểm biên dịch trước khi bất kỳ byte bytecode nào được tạo ra. Đây là một sự đảm bảo ở cấp độ ngôn ngữ lập trình, chứ không phải cấp độ JVM — bản thân JVM không ngăn chặn quyền truy cập trường dữ liệu bằng bytecode; nó chỉ kiểm tra các cờ truy cập (access flags) được mã hóa trong file `.class` khi lớp đó được tải và liên kết. Trong thực tế, cơ chế Reflection (`Field.setAccessible(true)`) hoàn toàn có thể bỏ qua các cờ này và truy cập vào các trường `private` lúc runtime, nghĩa là JVM không thực sự ngăn chặn tất cả mọi quyền truy cập — nó chỉ từ chối truy cập qua các cách gọi thông thường. Những gì ngôn ngữ lập trình bắt buộc là không có file lớp được biên dịch hợp lệ nào có thể tham chiếu trực tiếp đến một trường `private` của lớp khác mà không gây ra lỗi biên dịch; chỉ có bytecode được cố ý tạo tác hoặc sử dụng reflection mới vượt qua được điều này. Việc khai báo các trường là `private` do đó giúp khóa chặt con đường phát triển thông thường: mọi sự thay đổi trạng thái bắt buộc phải đi qua các phương thức do bạn chủ động bộc lộ, cho phép bạn kiểm tra dữ liệu đầu vào, duy trì các bất biến và thay đổi biểu diễn nội bộ mà không làm ảnh hưởng đến bất kỳ mã nguồn bên gọi nào.

### Mô Hình Khái Niệm

```
[Mã nguồn bên ngoài]            [Lớp Account]
     |                              |
     |  account.balance = -999;     |
     |----------------------------> X  <-- Lỗi Biên dịch (bắt buộc ở cấp ngôn ngữ)
     |                              |
     |  account.setBalance(-999);   |
     |----------------------------> [setBalance()]
     |                              |  if (amount >= 0) this.balance = amount;
     |                              |  else throw IllegalArgumentException
     |                              |
     |  // Vượt qua bằng Reflection:|
     |  f.setAccessible(true);      |
     |  f.set(acc, -999); --------> [JVM: kiểm tra cờ AccessibleObject]
     |                              |  cờ == true → JVM cho phép ghi
     |                              |  (JVM KHÔNG ngăn chặn; ngôn ngữ đã ngăn chặn từ lúc biên dịch)
```

### Ví Dụ Code Minh Họa

```java
public class Account {
    private double balance; // private: trình biên dịch bắt buộc giới hạn quyền truy cập

    public Account(double openingBalance) {
        setBalance(openingBalance);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double amount) {
        if (amount >= 0) {
            this.balance = amount;
        } else {
            throw new IllegalArgumentException("Số dư không được phép bị âm: " + amount);
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Account acc = new Account(500.0);

        // acc.balance = -999; // Lỗi biên dịch: balance has private access in Account

        acc.setBalance(200.0);
        System.out.println(acc.getBalance()); // Kết quả: 200.0

        // Vượt qua kiểm tra bằng Reflection (JVM không ngăn chặn lúc runtime nếu setAccessible được dùng)
        java.lang.reflect.Field f = Account.class.getDeclaredField("balance");
        f.setAccessible(true);
        f.set(acc, -999.0); // JVM cho phép thao tác này — lớp bảo vệ của ngôn ngữ đã không còn tác dụng
        System.out.println(acc.getBalance()); // Kết quả: -999.0  ← bất biến đã bị phá vỡ!
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Trường dữ liệu được khai báo `private`
&rarr; Trình biên dịch Java từ chối mọi truy cập dạng `obj.field` từ bên ngoài lớp ngay tại thời điểm biên dịch
&rarr; Tất cả các thay đổi từ bên ngoài bắt buộc phải đi qua các phương thức setter công khai
&rarr; Các phương thức setter có thể kiểm tra tính hợp lệ của đầu vào và duy trì tính toàn vẹn của lớp
&rarr; Cấu trúc biểu diễn nội bộ có thể tự do thay đổi mà không làm lỗi các bên gọi
&rarr; Các cờ truy cập JVM trong file `.class` ghi nhận thuộc tính `private`, nhưng lệnh `setAccessible(true)` thông qua reflection sẽ vượt qua chúng lúc runtime
&rarr; Sự bắt buộc của ngôn ngữ (lúc biên dịch) mới là lớp bảo vệ thực sự; sự bắt buộc của JVM là một bước kiểm tra runtime mềm hơn mà reflection có thể ghi đè.
