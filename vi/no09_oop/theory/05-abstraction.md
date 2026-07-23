# Trừu Tượng (Abstraction)

Trừu tượng (Abstraction) là quá trình ẩn đi các chi tiết triển khai (Implementation details) và chỉ hiển thị các đặc tính thiết yếu (Essential features) của một đối tượng (Object). Nó tập trung vào việc đối tượng làm **gì** (what) thay vì làm điều đó **như thế nào** (how).

---

## Lớp Trừu Tượng (Abstract Class) và Phương Thức Trừu Tượng (Abstract Method)

Một **lớp trừu tượng (Abstract Class)** là một lớp được khai báo với từ khóa `abstract`. Nó đại diện cho một định nghĩa lớp chưa hoàn chỉnh được thiết kế để các lớp con cụ thể (Concrete subclass) mở rộng.

### Đặc trưng:
- **Không thể khởi tạo thực thể (No Instantiation):** Bạn không thể tạo một thực thể (Instance) trực tiếp (ví dụ: `new GraphicObject()` là không hợp lệ).
- **Phương thức trừu tượng (Abstract Method):** Các phương thức được khai báo mà không có thân phương thức (Body) (kết thúc bằng dấu chấm phẩy `;`). Chúng đóng vai trò là các trình giữ chỗ (Placeholder) bắt buộc các lớp con phải ghi đè (Override).
- **Trạng thái thực thể (Instance State):** Có thể chứa các trường thực thể (Instance field), hàm khởi tạo (Constructor), biến tĩnh (Static variable), khối mã lệnh (Block) và các phương thức cụ thể (Concrete method).
- **Nghĩa vụ của lớp con (Subclass Obligation):** Bất kỳ lớp con cụ thể nào kế thừa một lớp trừu tượng đều phải cung cấp triển khai cho tất cả các phương thức trừu tượng được thừa hưởng, nếu không thì chính lớp con đó phải được đánh dấu là `abstract`.

```java
abstract class Vehicle {
    private String brand;

    Vehicle(String brand) { this.brand = brand; } // Abstract classes can have constructors

    public String getBrand() { return brand; } // Concrete method

    abstract void accelerate(); // Abstract method
}
```

---

## Giao Diện (Interface)

Một **giao diện (Interface)** là một kiểu tham chiếu (Reference type) dùng để xác định một bản hợp đồng hành vi (Contract of behaviors). Nó không chứa trạng thái thực thể.

### Đặc trưng:
- **Từ khóa bổ nghĩa ngầm định (Implicit Modifier):**
- **`public static final`** — public static final: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`public abstract`** — public abstract: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **Không có trạng thái thực thể:** Không thể khai báo các trường thực thể hoặc hàm khởi tạo.
- **Triển khai nhiều giao diện (Multiple Implementation):** Một lớp có thể triển khai nhiều giao diện (ví dụ: `class Car implements Drivable, Flyable`), cho phép đa kế thừa hành vi (Multiple inheritance of behavior).

---

## So Sánh: Lớp Trừu Tượng vs. Giao Diện (Contrast: Abstract Class vs. Interface)

| Đặc trưng | Lớp Trừu Tượng | Giao Diện |
| :--- | :--- | :--- |
| **Kế thừa (Inheritance)** | Đơn kế thừa lớp (`extends`). | Triển khai nhiều giao diện (`implements`). |
| **Trường thực thể** | Có thể có các biến thực thể (private, protected, v.v.). | Không thể có biến thực thể. Chỉ có các hằng số `public static final`. |
| **Hàm khởi tạo** | Có thể có hàm khởi tạo (được gọi thông qua `super()`). | Không thể có hàm khởi tạo. |
| **Từ khóa chỉ định truy cập (Access Modifier)** | Các phương thức có thể là public, protected, phạm vi gói (Package-private), hoặc private. | Các phương thức mặc định là public (private được hỗ trợ từ Java 9). |
| **Ý định cốt lõi (Root Intent)** | Đại diện cho **danh tính (Identity)** (quan hệ IS-A (IS-A relationship)). Trạng thái chung và danh tính cốt lõi. | Đại diện cho **khả năng (Capability)** (quan hệ CAN-DO (CAN-DO relationship)). Bản hợp đồng lỏng lẻo cho các lớp không liên quan. |

### Ví Dụ Mã Nguồn: Lớp Trừu Tượng vs. Giao Diện (Code Example: Abstract Class vs. Interface)
Dưới đây là một so sánh sử dụng hệ thống thanh toán và ngân hàng:

```java
// Abstract Class represents identity (IS-A)
abstract class BankAccount {
    private String accountNumber;
    protected double balance; // Can have instance variables

    BankAccount(String accountNumber, double balance) { // Can have constructors
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public double getBalance() { return balance; } // Concrete method

    abstract void processInterest(); // Abstract method
}

// Interface represents capability (CAN-DO)
interface Payable {
    int TAX_RATE_PERCENT = 10; // Implicitly public static final constant

    void pay(double amount); // Implicitly public abstract method
}

// Concrete class extending the abstract class and implementing the interface
class SavingsAccount extends BankAccount implements Payable {
    SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    void processInterest() {
        balance += balance * 0.02;
    }

    @Override
    public void pay(double amount) { // Must use public modifier
        balance -= amount;
    }
}
```

---

## Sự Tiến Hóa Của Giao Diện (Java 8 và 9+) (Evolution of Interfaces)

Ban đầu, các giao diện chỉ có thể chứa các phương thức trừu tượng. Java đã phát triển các giao diện để hỗ trợ sự phát triển của API mà không làm phá vỡ tính tương thích ngược (Backwards compatibility).

### 1. Phương Thức Mặc Định (Default Method) (Java 8+)
Các phương thức mặc định cung cấp một triển khai dự phòng (Fallback implementation) cụ thể bằng cách sử dụng từ khóa `default`. Các lớp triển khai sẽ tự động thừa hưởng chúng nhưng vẫn có thể ghi đè chúng.

```java
interface Drivable {
    void drive();
    default void park() { System.out.println("Parking vehicle"); }
}
```

## Ví Dụ Thực Tế: Vấn Đề Kim Cương và Cách Giao Diện Giải Quyết Nó (Diamond Problem)

Trong đa kế thừa (Multiple inheritance) truyền thống (như trong C++), nếu một lớp `D` kế thừa từ cả `B` và `C`, cả hai đều kế thừa từ `A`, và cả hai đều ghi đè phương thức `foo()` từ `A`, thì việc gọi `foo()` trên một thực thể của `D` sẽ mơ hồ (đây chính là vấn đề kim cương (Diamond Problem)).

Java tránh điều này bằng cách:
1. Chỉ cho phép đơn kế thừa lớp. Một lớp không bao giờ được phép mở rộng nhiều hơn một lớp.
2. Cho phép đa kế thừa giao diện. Vì các phương thức giao diện ban đầu hoàn toàn là trừu tượng (không có thân phương thức), nên không xảy ra xung đột triển khai.

### Thách Thức Trong Thời Kỳ Hiện Đại: Phương Thức Mặc Định (Java 8+)
Với các phương thức mặc định, các giao diện giờ đây có thể mang theo hành vi. Điều này đã làm tái xuất hiện một dạng xung đột triển khai (vấn đề kim cương trong giao diện) khi một lớp triển khai hai giao diện khai báo cùng một chữ ký phương thức mặc định.

```java
interface Flyer {
    default void move() { System.out.println("Flying"); }
}

interface Swimmer {
    default void move() { System.out.println("Swimming"); }
}

// class Duck implements Flyer, Swimmer {} // Compile Error: Duck inherits unrelated defaults for move()
```

### Giải Pháp
Java bắt buộc lớp triển khai phải ghi đè phương thức bị xung đột một cách rõ ràng, giải quyết sự mơ hồ này tại thời gian biên dịch (Compile-time). Bên trong phương thức ghi đè, bạn có thể viết hành vi tùy chỉnh hoặc ủy quyền (Delegation) một cách rõ ràng cho một trong các giao diện bằng cú pháp `<InterfaceName>.super.<methodName>()`.

```java
class Duck implements Flyer, Swimmer {
    @Override
    public void move() {
        // Option 1: Provide a brand-new behavior
        System.out.println("Walking like a duck");

        // Option 2: Explicitly delegate to Flyer's default behavior
        Flyer.super.move();

        // Option 3: Explicitly delegate to Swimmer's default behavior
        Swimmer.super.move();
    }
}
```

### 2. Phương Thức Tĩnh (Static Method) (Java 8+)
Được sử dụng để định nghĩa các phương thức tiện ích (Utility method) trực tiếp trên giao diện. Chúng không thể bị ghi đè và phải được gọi bằng tên giao diện:
```java
interface Drivable {
    static boolean isValidSpeed(int speed) { return speed > 0; }
}
// Called via: Drivable.isValidSpeed(50)
```

### 3. Phương Thức Riêng Tư (Private Method) (Java 9+)
Cho phép chia sẻ mã bổ trợ (Helper code) chung giữa nhiều phương thức mặc định hoặc phương thức tĩnh trong giao diện mà không làm lộ chúng cho các lớp triển khai.
- **Phương thức thực thể riêng tư (Private Instance Method):** Có thể được truy cập bởi các phương thức mặc định.
- **Phương thức tĩnh riêng tư (Private Static Method):** Có thể được truy cập bởi các phương thức mặc định, phương thức tĩnh và phương thức tĩnh riêng tư.
```java
interface Drivable {
    default void start() { log("Start"); }
    default void stop() { log("Stop"); }

    private void log(String message) { // Private helper method
        System.out.println("LOG: " + message);
    }
}
```

## Xem Xét Chuyên Sâu: Lựa Chọn Giữa Lớp Trừu Tượng Và Giao Diện (Choosing Between Abstract Class And Interface)

Hãy chọn giao diện khi bạn muốn mô tả một khả năng mà nhiều lớp không liên quan có thể cung cấp.

```java
interface Payable {
    void pay(int amount);
}
```

Hãy chọn lớp trừu tượng khi các lớp con chia sẻ danh tính, trạng thái và một phần triển khai.

```java
abstract class Account {
    private int balance;

    Account(int openingBalance) {
        this.balance = openingBalance;
    }

    abstract void withdraw(int amount);
}
```

### Tiến Hóa Giao Diện (Interface Evolution)

Các phương thức mặc định cho phép các nhà phát triển giao diện thêm hành vi mới mà không lập tức làm phá vỡ mọi lớp triển khai. Điều này rất hữu ích cho tiến hóa API (API Evolution), nhưng các phương thức mặc định vẫn cần được sử dụng một cách cẩn thận. Quá nhiều phương thức mặc định có thể biến một giao diện thành một lớp cơ sở một phần đầy khó hiểu.

### Danh Sách Kiểm Tra Trừu Tượng (Abstraction Checklist)

- Liệu trừu tượng đã ẩn đi các chi tiết triển khai không cần thiết chưa?
- Tên gọi có mô tả hành vi một cách rõ ràng không?
- Các đối tượng gọi có đang phụ thuộc vào trừu tượng thay vì các lớp cụ thể không?
- Nhiều triển khai khác nhau có thể thay thế cho nhau một cách an toàn không?
- Các phương thức mặc định có được sử dụng để hỗ trợ tiến hóa API thay vì rải rác logic chung ở khắp mọi nơi không?

---

## Các Sai Lầm Thường Gặp (Common Mistakes)

### 1. Cố Gắng Khởi Tạo Thực Thể Của Một Lớp Trừu Tượng Hoặc Giao Diện
Việc cố gắng khởi tạo trực tiếp một lớp trừu tượng hoặc giao diện sẽ dẫn đến lỗi thời gian biên dịch. Chúng là những bản hợp đồng chưa hoàn thiện và phải được mở rộng hoặc triển khai bởi các lớp cụ thể trước.
```java
abstract class Shape {}
// Shape s = new Shape(); // Compile Error: Shape is abstract; cannot be instantiated
```

### 2. Ghi Đè Phương Thức Giao Diện Mà Không Có Từ Khóa `public`
Tất cả các phương thức được khai báo trong một giao diện đều ngầm định là `public`. Khi một lớp triển khai một phương thức giao diện, nó phải khai báo phương thức đó là `public` một cách tường minh. Việc bỏ qua từ khóa `public` sẽ mặc định phương thức của lớp về phạm vi gói, vốn là một quyền truy cập yếu hơn và sẽ làm thất bại quá trình biên dịch.
```java
interface Drivable {
    void drive();
}

class Car implements Drivable {
    // void drive() {} // Compile Error: attempting to assign weaker access privileges; was public
    
    @Override
    public void drive() {} // Correct
}
```

### 3. Cố Gắng Khai Báo Trường Thực Thể Trong Giao Diện
Mọi biến được khai báo trong một giao diện đều ngầm định là `public static final` (một hằng số). Nó phải được khởi tạo ngay lập tức và không thể sửa đổi. Khai báo các trường thực thể thông thường trong giao diện là điều không thể.
```java
interface Config {
    int TIMEOUT; // Compile Error: variable TIMEOUT might not have been initialized
}
```

### Liên Kết Tham Khảo (Reference Links)

- Hướng dẫn Java của Oracle - Giao diện (Interfaces): https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html
- Hướng dẫn Java của Oracle - Định nghĩa một giao diện (Defining an interface): https://docs.oracle.com/javase/tutorial/java/IandI/interfaceDef.html
- Hướng dẫn Java của Oracle - Các phương thức và lớp trừu tượng (Abstract methods and classes): https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
- Hướng dẫn Java của Oracle - Các phương thức mặc định (Default methods): https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html
- Tổng quan về OOP trên Dev.java: https://dev.java/learn/oop/

---

## Tại Sao Java Sử Dụng Giao Diện Thay Vì Đa Kế Thừa Lớp (Why Java Uses Interfaces Instead of Multiple Class Inheritance)

Các nhà thiết kế của Java đã cố tình cấm đa kế thừa lớp vì việc cho phép `class D extends B, C` — trong đó cả `B` và `C` ghi đè độc lập một phương thức từ một tổ tiên chung `A` — sẽ tạo ra sự mơ hồ không có cách giải quyết an toàn và xác định: trình biên dịch và JVM sẽ cần chọn một triển khai này thay vì triển khai kia mà không có cơ sở nguyên tắc nào cho sự lựa chọn đó, dẫn đến hành vi không thể dự đoán được gọi là **vấn đề kim cương**. Các ngôn ngữ như C++ cho phép đa kế thừa lớp nhưng yêu cầu cú pháp phân giải mơ hồ rõ ràng, gây thêm độ phức tạp đáng kể cho cả người viết trình biên dịch và lập trình viên. Java đã giải quyết vấn đề này bằng cách giới hạn mỗi lớp chỉ có một quan hệ `extends` duy nhất trong khi cho phép vô số quan hệ `implements`. Các giao diện ban đầu không mang theo triển khai nào — chỉ có chữ ký phương thức — vì vậy việc triển khai hai giao diện có cùng tên phương thức không tạo ra sự mơ hồ: lớp triển khai chỉ đơn giản là có nghĩa vụ cung cấp một triển khai đáp ứng cả hai bản hợp đồng hành vi. Khi Java 8 giới thiệu các phương thức mặc định (các triển khai cụ thể bên trong giao diện), vấn đề kim cương lại xuất hiện dưới một dạng mới: nếu hai giao diện đều khai báo một phương thức mặc định có cùng chữ ký và một lớp triển khai cả hai, nó sẽ thừa hưởng hai triển khai xung đột nhau. Java giải quyết vấn đề này bằng cách từ chối biên dịch một lớp như vậy trừ khi nó ghi đè phương thức bị xung đột một cách rõ ràng, buộc lập trình viên phải giải quyết sự mơ hồ một cách chủ động bằng cách sử dụng logic tùy chỉnh hoặc cú pháp ủy quyền `InterfaceName.super.method()`.

### Mô Hình Tư Duy (Mental Model)

```
Diamond Problem (class inheritance — Java PROHIBITS):

        A
       / \
      B   C         B.foo() and C.foo() are different implementations
       \ /
        D           D.foo() — ambiguous! Which parent's foo() runs? No answer.

Java's Solution (interface inheritance):

  interface Flyable { void move(); }    // no implementation
  interface Swimmer { void move(); }    // no implementation
  class Duck implements Flyable, Swimmer {
      @Override public void move() { ... }  // Duck MUST provide ONE explicit implementation
  }

Default Method Conflict (Java 8+ — still resolved at compile time):

  interface Flyable { default void move() { "fly"; } }
  interface Swimmer { default void move() { "swim"; } }
  class Duck implements Flyable, Swimmer {
      // COMPILE ERROR unless Duck overrides move()
      @Override public void move() {
          Flyer.super.move();  // OR: Swimmer.super.move();  OR: custom
      }
  }
```

### Ví Dụ Mã Nguồn

```java
interface Flyable {
    default void move() { System.out.println("Flying"); }
}

interface Swimmer {
    default void move() { System.out.println("Swimming"); }
}

// class Duck implements Flyable, Swimmer {} // Compile Error: inherits unrelated defaults for move()

class Duck implements Flyable, Swimmer {
    @Override
    public void move() {
        // Must resolve the conflict explicitly
        Flyable.super.move(); // delegates to Flyable's default
    }
}

public class Main {
    public static void main(String[] args) {
        new Duck().move();
    }
}
// Output:
// Flying
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

Java cấm `class D extends B, C` (đa kế thừa lớp)
→ Loại bỏ vấn đề kim cương: không có chuyện hai lớp cha cùng cung cấp các ô bảng phương thức ảo (Vtable slot) cụ thể bị xung đột cho cùng một phương thức
→ Các giao diện chỉ có phương thức trừu tượng có thể được triển khai nhiều giao diện — không có xung đột vì không tồn tại triển khai
→ Java 8 giới thiệu các phương thức mặc định: các giao diện giờ đây có thể mang theo triển khai
→ Hai giao diện có cùng chữ ký phương thức mặc định sẽ tạo ra một xung đột mới
→ Trình biên dịch Java từ chối biên dịch trừ khi lớp triển khai ghi đè phương thức đó một cách tường minh
→ Lập trình viên phải sử dụng `InterfaceName.super.method()` hoặc cung cấp logic tùy chỉnh
→ Xung đột luôn được giải quyết tại thời gian biên dịch — không bao giờ bị mơ hồ tại thời gian chạy (Runtime)

---

## Tại Sao Lớp Trừu Tượng Và Giao Diện Phục Vụ Các Mục Đích Thiết Kế Khác Nhau (Why Abstract Classes and Interfaces Serve Different Design Purposes)

Sự lựa chọn giữa một lớp trừu tượng và một giao diện là một quyết định mang tính **ngữ nghĩa (Semantic)** về việc kiểu dữ liệu đại diện cho cái gì, chứ không đơn thuần là một quyết định kỹ thuật về việc cần những tính năng ngôn ngữ nào. Một lớp trừu tượng mô phỏng một **danh tính "là một" (is-a identity)**: nó đại diện cho một danh mục đối tượng thực tế, có thể nhận biết được, cùng chia sẻ trạng thái, vòng đời (Lifecycle) và một phần hành vi (ví dụ: mọi `BankAccount` đều có số dư và số tài khoản bất kể đó là tài khoản tiết kiệm hay tài khoản vãng lai). Bởi vì một lớp trừu tượng có thể khai báo các trường thực thể và hàm khởi tạo, nó là ngôi nhà tự nhiên cho trạng thái có thể thay đổi (Mutable state) chung mà tất cả các lớp con cần thừa hưởng và xây dựng dựa trên đó; các lớp con gọi `super(...)` để khởi tạo trạng thái chung đó trước khi thêm trạng thái của riêng mình. Một giao diện mô phỏng một **khả năng "có thể làm" (can-do capability)**: nó mô tả một vai trò hoặc một bản hợp đồng hành vi mà các lớp hoàn toàn không liên quan có thể đáp ứng (ví dụ: cả `Robot` và `Human` đều có thể là `Payable`, nhưng chúng không chia sẻ bất kỳ danh tính hoặc trạng thái chung nào). Quy tắc thiết kế để giải quyết sự lựa chọn này là **nguyên lý thay thế Liskov (Liskov Substitution Principle) cùng với sự hiện diện của trạng thái chung**: nếu trừu tượng mang các trường thực thể, yêu cầu một hàm khởi tạo, hoặc đại diện cho một phân cấp danh tính ổn định mà từ đó các lớp con thực sự thừa hưởng trạng thái và hành vi, hãy sử dụng một lớp trừu tượng; nếu trừu tượng là một bản hợp đồng hành vi thuần túy nên được áp dụng trên các kiểu dữ liệu không liên quan mà không ép buộc một vị trí đơn kế thừa, hãy sử dụng một giao diện. Trong Java hiện đại (8+), các giao diện hỗ trợ các phương thức mặc định và phương thức tĩnh, làm thu hẹp khoảng cách — nhưng chúng vẫn không thể giữ trạng thái thực thể, thứ vốn là ranh giới cứng giữa hai cơ chế này.

### Mô Hình Tư Duy

```
ABSTRACT CLASS — use when there is shared identity + state:

  abstract class BankAccount {
      private double balance;       // shared state — instance field
      private String accountNumber; // shared state — instance field
      BankAccount(String acct, double bal) { ... } // shared constructor

      public double getBalance() { return balance; } // shared concrete method

      abstract void processInterest();               // specialization point
  }

  SavingsAccount extends BankAccount  → IS-A BankAccount, inherits balance/accountNumber
  CheckingAccount extends BankAccount → IS-A BankAccount, inherits balance/accountNumber

INTERFACE — use when there is only a behavioral contract across unrelated types:

  interface Payable {
      void pay(double amount);   // contract only — no state
  }

  SavingsAccount implements Payable  → CAN-DO paying
  Robot          implements Payable  → CAN-DO paying (unrelated hierarchy!)
  Employee       implements Payable  → CAN-DO paying (completely different type!)

Decision Rule:
  Has shared instance state?  → Abstract Class
  Pure behavioral contract?   → Interface
  Needs both?                 → Abstract Class + Interface(s)
```

### Ví Dụ Mã Nguồn

```java
// Abstract class: shared identity + state
abstract class Shape {
    private String color; // shared instance state

    Shape(String color) { this.color = color; }

    public String getColor() { return color; }

    abstract double area(); // specialization point
}

// Interface: pure capability, applicable to unrelated types
interface Printable {
    void print(); // behavioral contract only
}

class Circle extends Shape implements Printable {
    private double radius;

    Circle(String color, double radius) {
        super(color); // initializes shared Shape state
        this.radius = radius;
    }

    @Override
    public double area() { return Math.PI * radius * radius; }

    @Override
    public void print() {
        System.out.printf("Circle [color=%s, area=%.2f]%n", getColor(), area());
    }
}

public class Main {
    public static void main(String[] args) {
        Circle c = new Circle("red", 5.0);
        c.print();
        System.out.printf("Area: %.2f%n", c.area());
    }
}
// Output:
// Circle [color=red, area=78.54]
// Area: 78.54
```

### Chuỗi Nguyên Nhân - Kết Quả

Kiểu dữ liệu cần mang các trường thực thể và một hàm khởi tạo cho trạng thái chung
→ Sử dụng một lớp trừu tượng: các lớp con gọi `super(...)` để khởi tạo trạng thái đó
→ Vị trí đơn kế thừa bị chiếm dụng; lớp con vẫn có thể triển khai nhiều giao diện
→ Kiểu dữ liệu chỉ là một bản hợp đồng hành vi không có trạng thái thực thể
→ Sử dụng một giao diện: bất kỳ lớp nào từ bất kỳ phân cấp nào cũng có thể triển khai nó
→ Nhiều giao diện có thể được triển khai đồng thời — không có xung đột vị trí kế thừa
→ Nếu cần cả trạng thái chung và khả năng đan chéo (Cross-cutting capability):
→ Mở rộng một lớp trừu tượng cho danh tính/trạng thái VÀ triển khai một hoặc nhiều giao diện cho các khả năng

---
