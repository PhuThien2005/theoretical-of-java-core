# Đóng Gói (Encapsulation)

Đóng gói (encapsulation) là một nguyên lý của lập trình hướng đối tượng (OOP) nhằm gộp dữ liệu (các trường thể hiện - instance fields) và các phương thức hoạt động trên dữ liệu đó thành một khối duy nhất (một lớp - class), đồng thời giới hạn quyền truy cập trực tiếp vào một số thành phần của đối tượng. Khái niệm này còn được gọi là **ẩn giấu dữ liệu (data hiding)**.

---

## Các Từ Khóa Đặc Tả Truy Cập và Các Cấp Độ Phạm Vi (Access Modifiers and Scope Levels)

Java cung cấp các từ khóa đặc tả truy cập (access modifier) để giới hạn khả năng hiển thị ở hai cấp độ:
1. **Cấp độ lớp (Class-level):** Một lớp cấp cao nhất (top-level class) chỉ có thể được khai báo là `public` hoặc mặc định (package-private). Nó không thể được khai báo là `private` hoặc `protected` (ngoại trừ các lớp lồng nhau/lớp nội bộ).
2. **Cấp độ thành viên (Member-level):** Các trường, phương thức và hàm khởi dựng (constructor) có thể sử dụng cả bốn cấp độ hiển thị.

- **`private`** — private: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`default`** — default: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`protected`** — protected: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`public`** — public: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

### Giải Thích Các Phạm Vi Chính:
- **`private`** — private: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`default`** — default: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`protected`** — protected: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
  *Lưu ý:* Một lớp con ở package khác chỉ có thể truy cập thành viên `protected` của lớp cha thông qua cơ chế kế thừa (sử dụng biến tham chiếu thuộc kiểu lớp con), chứ không thể thông qua biến tham chiếu thuộc kiểu lớp cha.
- **`public`** — public: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

---

## Mẫu Thiết Kế Getter và Setter (Getter and Setter Design Patterns)

Để tương tác với các trường private, các lớp sẽ cung cấp các phương thức truy xuất công khai (getter) và phương thức thay đổi trạng thái (setter).

### Ví Dụ Về Đóng Gói Tiêu Chuẩn
Dưới đây là một lớp minh họa tính đóng gói tiêu chuẩn: các trường private, một hàm khởi dựng có tham số, và các phương thức getter/setter công khai để truy cập và sửa đổi trạng thái một cách có kiểm soát.

```java
public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        setSalary(salary); // Bắt buộc kiểm chứng dữ liệu trong quá trình tạo đối tượng
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
            throw new IllegalArgumentException("Lương không thể là số âm.");
        }
    }
}
```

Mẫu thiết kế này đem lại nhiều lợi ích cho việc thiết kế phần mềm:

### 1. Kiểm Chứng Dữ Liệu (Data Validation)
Các phương thức setter có thể kiểm tra dữ liệu đầu vào để ngăn chặn các trạng thái không hợp lệ của đối tượng:
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
- **Lớp Chỉ Đọc (Read-Only):** Cung cấp các phương thức getter nhưng không cung cấp setter. Hữu ích cho việc tạo ra các vùng chứa trạng thái có tính chất bất biến.
- **Lớp Chỉ Ghi (Write-Only):** Cung cấp các phương thức setter nhưng không cung cấp getter (ví dụ: cập nhật mật khẩu cơ sở dữ liệu chỉ ghi hoặc hệ thống ghi log).

### 3. Sao Chép Phòng Thủ (Defensive Copying - Quan Trọng Với Kiểu Tham Chiếu)
Việc cung cấp getter cho các đối tượng có thể thay đổi (như danh sách ArrayList hoặc mảng) làm phá vỡ tính đóng gói vì bên gọi có thể sửa đổi trực tiếp đối tượng thông qua tham chiếu được trả về. Các phương thức getter nên trả về các bản sao phòng thủ (defensive copy):

```java
class Team {
    private List<String> members = new ArrayList<>();

    // Getter dễ bị tổn thương:
    // public List<String> getMembers() { return this.members; } // Bên gọi có thể gọi getMembers().clear()!

    // Getter đã đóng gói:
    public List<String> getMembers() {
        return new ArrayList<>(this.members); // Trả về một bản sao
    }
}
```

---

## Lợi Ích Của Đóng Gói

1. **Tính Linh Hoạt và Dễ Bảo Trì:** Bạn có thể thay đổi cấu trúc dữ liệu nội bộ của một lớp mà không làm ảnh hưởng đến mã nguồn bên ngoài đang phụ thuộc vào nó. Ví dụ, việc thay đổi một trường từ `private int age` thành `private LocalDate birthDate` hoàn toàn có thể thực hiện được trong khi vẫn giữ nguyên chữ ký phương thức `getAge()` bằng cách tính toán số tuổi trực tiếp tại thời điểm gọi.
2. **Liên Kết Lỏng Lẻo (Loose Coupling):** Giảm thiểu sự phụ thuộc giữa các lớp, giúp các thành phần trở nên mô-đun hóa và dễ dàng viết unit test hơn.
3. **Bảo Mật:** Bảo vệ các trường thông tin khỏi các sửa đổi tùy tiện từ bên ngoài lớp.

## Đi Sâu: Đóng Gói Không Chỉ Đơn Thuần Là Viết Getter Và Setter

Đóng gói không phải là thói quen tự động tạo ra getter và setter cho mọi trường dữ liệu. Nó là thói quen bảo vệ các bất biến của đối tượng (object invariant) đằng sau một API được thiết kế có chủ đích.

Một thiết kế yếu sẽ để lộ mọi trường dữ liệu một cách gián tiếp:

```java
class User {
    private String password;
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

Một thiết kế mạnh mẽ hơn sẽ thể hiện hành vi thay vì trạng thái thô:

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

## Lỗi Thường Gặp

### 1. Vô Tư Tiết Lộ Mọi Trường Bằng Getter/Setter
Việc tự động tạo getter và setter công khai cho tất cả các trường biến lớp đó thành một túi chứa trạng thái toàn cục (global state bag), cho phép mã bên ngoài vi phạm các quy tắc của đối tượng. Các phương thức setter chỉ nên tồn tại nếu việc sửa đổi được phép về mặt logic và đã được kiểm chứng.

### 2. Làm Rò Rỉ Tham Chiếu Nội Bộ (Phá Vỡ Tính Đóng Gói)
Việc cung cấp các phương thức getter cho các cấu trúc dữ liệu có thể thay đổi được bên trong (ví dụ: `List`, `Map`, mảng) cho phép bên gọi thay đổi trực tiếp các bộ sưu tập nội bộ đó.
```java
class Wallet {
    private List<Coin> coins = new ArrayList<>();
    // Sai lầm: trả về tham chiếu trực tiếp
    public List<Coin> getCoins() { return coins; } 
}
// Bên gọi có thể thực hiện: wallet.getCoins().clear(); // vượt qua sự kiểm soát của Wallet!
```
**Khắc phục:** Trả về một wrapper không thể sửa đổi (unmodifiable wrapper) hoặc một bản sao phòng thủ:
```java
public List<Coin> getCoins() { return Collections.unmodifiableList(coins); }
```

### 3. Để Các Trường Ở Trạng Tính Package-Private (Quên Từ Khóa `private`)
Việc bỏ qua các từ khóa đặc tả truy cập sẽ khiến chúng mặc định là package-private, cho phép bất kỳ lớp nào khác trong cùng package sửa đổi trực tiếp các trường. Luôn khai báo các trường là `private` theo mặc định.
```java
class Account {
    double balance; // Thiếu private! Bất kỳ lớp nào trong package cũng có thể ghi account.balance = -9999;
}
```

### Bảng Kiểm Tra Tính Đóng Gói (Encapsulation Checklist)

- Giữ các trường ở trạng thái private theo mặc định.
- Cung cấp các phương thức mô tả hành vi, chứ không chỉ mô tả nơi lưu trữ dữ liệu.
- Kiểm chứng dữ liệu đầu vào trước khi thay đổi trạng thái đối tượng.
- Trả về các bản sao phòng thủ đối với các đối tượng nội bộ có thể thay đổi được.
- Tránh các setter có thể đưa đối tượng vào trạng thái không hợp lệ.
- Ưu tiên sử dụng các đối tượng bất biến khi trạng thái không cần phải thay đổi.

### Liên Kết Tham Chiếu

- Hướng dẫn của Oracle Java - Các khái niệm OOP: https://docs.oracle.com/javase/tutorial/java/concepts/
- Hướng dẫn của Oracle Java - Lớp và Đối tượng: https://docs.oracle.com/javase/tutorial/java/javaOO/index.html
- Tổng quan về OOP trên Dev.java: https://dev.java/learn/oop/

---

## Tại Sao Các Biến Thể Hiện Nên Để Ở Trạng Thái Private

Khi một biến thể hiện (instance variable) được khai báo là `private`, trình biên dịch Java sẽ bắt buộc giới hạn truy cập ở cấp độ mã nguồn: bất kỳ nỗ lực nào nhằm đọc hoặc ghi trường đó từ bên ngoài lớp định nghĩa nó đều tạo ra lỗi biên dịch (compile-time error) trước khi bất kỳ byte mã bytecode nào được tạo ra. Đây là sự đảm bảo ở cấp độ ngôn ngữ lập trình, chứ không phải đảm bảo ở cấp độ JVM — bản thân JVM không ngăn cản việc truy cập trường thông qua bytecode; nó chỉ kiểm tra các cờ truy cập (access flags) được mã hóa trong file `.class` khi lớp đó được tải và liên kết. Trong thực tế, cơ chế phản chiếu (reflection) (`Field.setAccessible(true)`) có thể bỏ qua các cờ đó và tiếp cận các trường `private` tại thời điểm chạy (runtime), nghĩa là JVM không thực sự ngăn chặn hoàn toàn mọi truy cập — nó chỉ từ chối truy cập thông qua cách gọi thông thường. Những gì ngôn ngữ bắt buộc là không có file class được biên dịch hợp lệ nào có thể tham chiếu đến một trường `private` của một lớp khác mà không gây ra lỗi trình biên dịch; chỉ có mã bytecode được tạo thủ công hoặc mã phản chiếu có chủ đích mới có thể vượt qua điều này. Do đó, việc đặt các trường ở trạng thái `private` sẽ khóa lại luồng phát triển thông thường: mọi sự thay đổi trạng thái phải đi qua các phương thức mà bạn chủ động cung cấp, cho phép bạn kiểm chứng dữ liệu đầu vào, duy trì các bất biến và thay đổi biểu diễn nội bộ mà không cần chạm vào bất kỳ mã gọi nào bên ngoài.

### Mô Hình Tư Duy

```
[Mã Bên Ngoài]                 [Lớp Account]
      |                              |
      |  account.balance = -999;     |
      |----------------------------> X  <-- Lỗi Biên Dịch (bắt buộc ở cấp ngôn ngữ)
      |                              |
      |  account.setBalance(-999);   |
      |----------------------------> [setBalance()]
      |                              |  if (amount >= 0) this.balance = amount;
      |                              |  else throw IllegalArgumentException
      |                              |
      |  // Vượt qua bằng Phản Chiếu: |
      |  f.setAccessible(true);      |
      |  f.set(acc, -999);      ---> [JVM: kiểm tra cờ AccessibleObject]
      |                              |  cờ == true → JVM cho phép ghi
      |                              |  (JVM không ngăn cản; ngôn ngữ đã ngăn chặn khi biên dịch)
```

### Ví Dụ Mã Nguồn

```java
public class Account {
    private double balance; // private: trình biên dịch bắt buộc giới hạn truy cập

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
            throw new IllegalArgumentException("Số dư không thể âm: " + amount);
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Account acc = new Account(500.0);

        // acc.balance = -999; // Lỗi Biên Dịch: balance có quyền truy cập private trong Account

        acc.setBalance(200.0);
        System.out.println(acc.getBalance()); // Đầu ra: 200.0

        // Vượt qua giới hạn bằng phản chiếu (JVM không ngăn cản ở runtime nếu dùng setAccessible)
        java.lang.reflect.Field f = Account.class.getDeclaredField("balance");
        f.setAccessible(true);
        f.set(acc, -999.0); // JVM cho phép — giới hạn của ngôn ngữ không còn tác dụng ở đây
        System.out.println(acc.getBalance()); // Đầu ra: -999.0  ← bất biến bị vi phạm!
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

Trường dữ liệu được khai báo là `private`
→ Trình biên dịch Java từ chối mọi truy cập `obj.field` từ bên ngoài lớp tại thời điểm biên dịch
→ Tất cả các thay đổi từ bên ngoài bắt buộc phải đi qua các phương thức setter công khai
→ Các phương thức setter có thể kiểm chứng dữ liệu đầu vào và đảm bảo các bất biến của lớp
→ Cấu trúc biểu diễn nội bộ có thể thay đổi tự do mà không làm hỏng mã nguồn của các lớp gọi nó
→ Các cờ truy cập JVM trong file `.class` ghi nhận trạng thái `private`, nhưng `setAccessible(true)` qua phản chiếu có thể bỏ qua chúng tại thời điểm chạy
→ Sự bắt buộc của ngôn ngữ (tại thời điểm biên dịch) là lớp bảo vệ thực sự; sự bắt buộc của JVM là một kiểm tra mềm hơn tại thời điểm chạy mà cơ chế phản chiếu có thể ghi đè.
