# Trừu tượng (Abstraction)

Trừu tượng (Abstraction) là quá trình ẩn đi các chi tiết triển khai bên trong và chỉ hiển thị các tính năng thiết yếu của một đối tượng. Nó tập trung vào việc đối tượng làm **cái gì** (what) thay vì làm điều đó **như thế nào** (how).

---

## Lớp Trừu tượng và Phương thức Trừu tượng (Abstract Classes and Abstract Methods)

Một **Lớp Trừu tượng (Abstract Class)** là một lớp được khai báo với từ khóa `abstract`. Nó đại diện cho một định nghĩa lớp chưa hoàn chỉnh được thiết kế để kế thừa bởi các lớp con cụ thể (concrete subclasses).

### Các đặc điểm (Characteristics)
- **Không thể khởi tạo (No Instantiation):** Bạn không thể tạo trực tiếp một thực thể (ví dụ: `new GraphicObject()` là không hợp lệ).
- **Phương thức trừu tượng (Abstract Methods):** Các phương thức được khai báo không có thân hàm (kết thúc bằng dấu chấm phẩy `;`). Chúng hoạt động như các trình giữ chỗ (placeholders) bắt buộc các lớp con phải ghi đè chúng.
- **Trạng thái thể hiện (Instance State):** Có thể chứa các trường thể hiện (instance fields), hàm khởi dựng (constructors), biến tĩnh (static variables), các khối lệnh (blocks), và các phương thức cụ thể (concrete methods).
- **Nghĩa vụ của lớp con (Subclass Obligation):** Bất kỳ lớp con cụ thể nào kế thừa một lớp trừu tượng đều phải cung cấp các triển khai cho tất cả các phương thức trừu tượng được kế thừa, hoặc chính lớp con đó phải được đánh dấu là `abstract`.

```java
abstract class Vehicle {
    private String brand;

    Vehicle(String brand) { this.brand = brand; } // Abstract classes can have constructors

    public String getBrand() { return brand; } // Concrete method

    abstract void accelerate(); // Abstract method
}
```

---

## Giao diện (Interfaces)

Một **Giao diện (Interface)** là một kiểu tham chiếu dùng để đặc tả một bản hợp đồng về các hành vi. Nó không chứa trạng thái thể hiện.

### Các đặc điểm (Characteristics)
- **Từ khóa bổ nghĩa ngầm định (Implicit Modifiers):**
  - Tất cả các trường dữ liệu ngầm định là **`public static final`** (hằng số).
  - Tất cả các phương thức trừu tượng ngầm định là **`public abstract`**.
- **Không có trạng thái thể hiện:** Không thể khai báo các trường thể hiện hoặc hàm khởi dựng.
- **Triển khai nhiều giao diện (Multiple Implementation):** Một lớp có thể triển khai nhiều giao diện (ví dụ: `class Car implements Drivable, Flyable`), cho phép đa kế thừa về mặt hành vi.

---

## Đối chiếu: Lớp Trừu tượng so với Giao diện (Contrast: Abstract Class vs. Interface)

| Đặc trưng | Lớp Trừu tượng (Abstract Class) | Giao diện (Interface) |
| :--- | :--- | :--- |
| **Kế thừa** | Kế thừa đơn lớp (`extends`). | Triển khai nhiều giao diện (`implements`). |
| **Trường thể hiện** | Có thể có các biến thể hiện (private, protected, v.v.). | Không thể có biến thể hiện. Chỉ có các hằng số `public static final`. |
| **Hàm khởi dựng** | Có thể có hàm khởi dựng (được gọi thông qua `super()`). | Không thể có hàm khởi dựng. |
| **Phạm vi truy cập** | Các phương thức có thể là public, protected, package-private, hoặc private. | Các phương thức mặc định là public (private được hỗ trợ từ Java 9). |
| **Mục đích cốt lõi** | Đại diện cho **bản sắc/danh tính** (quan hệ IS-A). Trạng thái chung và danh tính cốt lõi. | Đại diện cho **khả năng** (quan hệ CAN-DO). Hợp đồng lỏng lẻo cho các lớp không liên quan. |

### Ví dụ Code: Lớp Trừu tượng so với Giao diện (Code Example: Abstract Class vs. Interface)
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

## Sự tiến hóa của Giao diện (Java 8 và 9+) (Evolution of Interfaces (Java 8 and 9+))

Ban đầu, các giao diện chỉ có thể chứa các phương thức trừu tượng. Java đã phát triển giao diện để hỗ trợ sự phát triển của API mà không làm phá vỡ khả năng tương thích ngược.

### 1. Phương thức Mặc định (Java 8+) (1. Default Methods (Java 8+))
Các phương thức mặc định cung cấp một triển khai cụ thể dự phòng bằng cách sử dụng từ khóa `default`. Các lớp triển khai sẽ tự động kế thừa chúng nhưng vẫn có thể ghi đè nếu muốn.

```java
interface Drivable {
    void drive();
    default void park() { System.out.println("Parking vehicle"); }
}
```

## Case Study: Vấn đề Kim cương và Cách Giao diện Giải quyết Nó (Case Study: Diamond Problem and How Interfaces Solve It)

Trong đa kế thừa truyền thống (như trong C++), nếu một lớp `D` kế thừa từ cả `B` và `C`, cả hai đều kế thừa từ `A`, và cả hai đều ghi đè phương thức `foo()` của `A`, thì việc gọi `foo()` trên thực thể của `D` sẽ mơ hồ ("Vấn đề Kim cương").

Java tránh điều này bằng cách:
1. Chỉ cho phép kế thừa đơn lớp. Một lớp không bao giờ có thể kế thừa nhiều hơn một lớp.
2. Cho phép triển khai nhiều giao diện. Vì các phương thức giao diện ban đầu hoàn toàn là trừu tượng (không có thân hàm), nên không có xung đột về mặt triển khai.

### Thách thức Hiện đại: Phương thức Mặc định (Java 8+) (The Modern Challenge: Default Methods (Java 8+))
Với phương thức mặc định, các giao diện giờ đây có thể mang theo hành vi. Điều này đã giới thiệu lại một dạng xung đột triển khai (Vấn đề Kim cương của Giao diện) khi một lớp triển khai hai giao diện khai báo cùng một chữ ký phương thức mặc định.

```java
interface Flyer {
    default void move() { System.out.println("Flying"); }
}

interface Swimmer {
    default void move() { System.out.println("Swimming"); }
}

// class Duck implements Flyer, Swimmer {} // Compile Error: Duck inherits unrelated defaults for move()
```

### Giải pháp (The Solution)
Java bắt buộc lớp triển khai phải ghi đè rõ ràng phương thức xung đột đó, giải quyết sự mơ hồ ngay tại thời điểm biên dịch. Bên trong phương thức ghi đè, bạn có thể viết hành vi tùy chỉnh hoặc ủy quyền rõ ràng cho một trong các giao diện bằng cú pháp `<InterfaceName>.super.<methodName>()`.

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

### 2. Phương thức Tĩnh (Java 8+) (2. Static Methods (Java 8+))
Được sử dụng để định nghĩa các phương thức tiện ích trực tiếp trên giao diện. Chúng không thể bị ghi đè và phải được gọi bằng tên giao diện:
```java
interface Drivable {
    static boolean isValidSpeed(int speed) { return speed > 0; }
}
// Called via: Drivable.isValidSpeed(50)
```

### 3. Phương thức Riêng tư (Java 9+) (3. Private Methods (Java 9+))
Cho phép chia sẻ mã trợ giúp chung giữa nhiều phương thức mặc định hoặc tĩnh trong giao diện mà không cần để lộ chúng ra các lớp triển khai.
- **Phương thức thể hiện riêng tư (Private Instance Methods):** Có thể được truy cập bởi các phương thức mặc định.
- **Phương thức tĩnh riêng tư (Private Static Methods):** Có thể được truy cập bởi các phương thức mặc định, phương thức tĩnh và phương thức tĩnh riêng tư khác.
```java
interface Drivable {
    default void start() { log("Start"); }
    default void stop() { log("Stop"); }

    private void log(String message) { // Private helper method
        System.out.println("LOG: " + message);
    }
}
```

## Xem xét chuyên sâu: Lựa chọn giữa Lớp Trừu tượng và Giao diện (Deep Review: Choosing Between Abstract Class And Interface)

Chọn một giao diện khi bạn muốn mô tả một khả năng (capability) mà nhiều lớp không liên quan có thể cung cấp.

```java
interface Payable {
    void pay(int amount);
}
```

Chọn một lớp trừu tượng khi các lớp con chia sẻ chung bản sắc/danh tính, trạng thái và một phần triển khai.

```java
abstract class Account {
    private int balance;

    Account(int openingBalance) {
        this.balance = openingBalance;
    }

    abstract void withdraw(int amount);
}
```

### Sự tiến hóa của Giao diện (Interface Evolution)

Các phương thức mặc định giúp các tác giả thiết kế giao diện thêm hành vi mới mà không lập tức làm hỏng tất cả các lớp đang triển khai. Điều này rất hữu ích cho sự tiến hóa của API, nhưng các phương thức mặc định vẫn cần được sử dụng cẩn thận. Việc có quá nhiều phương thức mặc định có thể biến giao diện thành một lớp cơ sở chung gây nhầm lẫn.

### Danh sách kiểm tra Tính Trừu tượng (Abstraction Checklist)

- Bản trừu tượng có ẩn đi các chi tiết triển khai không cần thiết không?
- Tên gọi có mô tả hành vi một cách rõ ràng không?
- Các phía gọi có đang phụ thuộc vào bản trừu tượng thay vì các lớp cụ thể không?
- Có thể thay thế nhiều triển khai khác nhau một cách an toàn không?
- Các phương thức mặc định được sử dụng để hỗ trợ sự tiến hóa của API hay chỉ đơn thuần để chứa mã logic dùng chung ở khắp mọi nơi?

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Cố gắng khởi tạo một Lớp Trừu tượng hoặc Giao diện (Trying to Instantiate an Abstract Class or Interface)
Cố gắng khởi tạo trực tiếp một lớp trừu tượng hoặc giao diện sẽ dẫn đến lỗi biên dịch. Chúng là những bản hợp đồng chưa hoàn chỉnh và trước hết phải được kế thừa hoặc triển khai bởi các lớp cụ thể.
```java
abstract class Shape {}
// Shape s = new Shape(); // Compile Error: Shape is abstract; cannot be instantiated
```

### 2. Ghi đè phương thức Giao diện mà thiếu phạm vi truy cập public (Overriding Interface Methods Without the public Modifier)
Tất cả các phương thức được khai báo trong một giao diện đều ngầm định là `public`. Khi một lớp triển khai phương thức giao diện, nó phải khai báo rõ ràng là `public`. Việc bỏ qua `public` sẽ đưa phương thức lớp về package-private, đây là một phạm vi truy cập hẹp hơn và sẽ làm hỏng quá trình biên dịch.
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

### 3. Cố gắng khai báo các Trường thể hiện trong Giao diện (Trying to Declare Instance Fields in Interfaces)
Mỗi biến được khai báo trong một giao diện đều ngầm định là `public static final` (hằng số). Nó phải được khởi tạo giá trị ngay lập tức và không thể sửa đổi. Khai báo các trường thể hiện thông thường trong một giao diện là điều bất khả thi.
```java
interface Config {
    int TIMEOUT; // Compile Error: variable TIMEOUT might not have been initialized
}
```

### Liên kết tham khảo (Reference Links)

- Oracle Java Tutorials - Interfaces: https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html
- Oracle Java Tutorials - Defining an interface: https://docs.oracle.com/javase/tutorial/java/IandI/interfaceDef.html
- Oracle Java Tutorials - Abstract methods and classes: https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
- Oracle Java Tutorials - Default methods: https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html
- Dev.java OOP overview: https://dev.java/learn/oop/

---

## Tại sao Java sử dụng Giao diện thay vì Đa kế thừa Lớp (Why Java Uses Interfaces Instead of Multiple Class Inheritance)

Các nhà thiết kế của Java đã cố tình cấm đa kế thừa lớp vì việc cho phép `class D extends B, C` — trong đó cả `B` và `C` đều ghi đè độc lập một phương thức từ một tổ tiên chung `A` — tạo ra một sự mơ hồ không có cách giải quyết an toàn và tất định nào: trình biên dịch và JVM sẽ phải chọn một triển khai này thay vì triển khai kia mà không có cơ sở nguyên tắc nào cho sự lựa chọn đó, dẫn đến hành vi khó lường được gọi là **Vấn đề Kim cương (Diamond Problem)**. Các ngôn ngữ như C++ cho phép đa kế thừa lớp nhưng yêu cầu cú pháp phân giải rõ ràng, gây tăng đáng kể độ phức tạp cho cả người viết trình biên dịch và lập trình viên. Java giải quyết vấn đề này bằng cách giới hạn mỗi lớp chỉ có một mối quan hệ `extends` duy nhất trong khi cho phép không giới hạn các mối quan hệ `implements`. Ban đầu, các giao diện không mang theo triển khai — chỉ có chữ ký phương thức — do đó việc triển khai hai giao diện có cùng tên phương thức không tạo ra sự mơ hồ nào: lớp triển khai chỉ đơn giản có nghĩa vụ cung cấp một triển khai đáp ứng cả hai hợp đồng. Khi Java 8 giới thiệu các phương thức mặc định (`default` methods) (triển khai cụ thể bên trong giao diện), vấn đề kim cương lại xuất hiện ở một dạng mới: nếu hai giao diện cùng khai báo một phương thức `default` có cùng chữ ký và một lớp triển khai cả hai, nó sẽ kế thừa hai triển khai xung đột. Java giải quyết điều này bằng cách từ chối biên dịch một lớp như vậy trừ khi nó ghi đè rõ ràng phương thức xung đột, buộc lập trình viên phải giải quyết sự mơ hồ một cách chủ động bằng cách sử dụng logic tùy chỉnh hoặc cú pháp ủy quyền `InterfaceName.super.method()`.

### Mô hình tư duy (Mental Model)

```
Vấn đề Kim cương (kế thừa lớp — Java CẤM):

        A
       / \
      B   C         B.foo() và C.foo() là các triển khai khác nhau
       \ /
        D           D.foo() — mơ hồ! foo() của cha nào sẽ chạy? Không có câu trả lời.

Giải pháp của Java (kế thừa giao diện):

  interface Flyable { void move(); }    // không có triển khai
  interface Swimmer { void move(); }    // không có triển khai
  class Duck implements Flyable, Swimmer {
      @Override public void move() { ... }  // Duck BẮT BUỘC cung cấp MỘT triển khai rõ ràng
  }

Xung đột phương thức mặc định (Java 8+ — vẫn được giải quyết lúc biên dịch):

  interface Flyable { default void move() { "fly"; } }
  interface Swimmer { default void move() { "swim"; } }
  class Duck implements Flyable, Swimmer {
      // LỖI BIÊN DỊCH trừ khi Duck ghi đè move()
      @Override public void move() {
          Flyer.super.move();  // HOẶC: Swimmer.super.move();  HOẶC: tùy chỉnh
      }
  }
```

### Ví dụ Code (Code Example)

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
        // Phải giải quyết xung đột một cách rõ ràng
        Flyable.super.move(); // ủy quyền cho mặc định của Flyable
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

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)

Java cấm `class D extends B, C` (đa kế thừa lớp)
→ Loại bỏ Vấn đề Kim cương: không có hai lớp cha nào có thể cung cấp các ô vtable cụ thể xung đột cho cùng một phương thức
→ Các giao diện chỉ có phương thức trừu tượng có thể được triển khai đồng thời — không có xung đột vì không tồn tại triển khai
→ Java 8 giới thiệu các phương thức `default`: giao diện hiện có thể chứa triển khai
→ Hai giao diện có cùng chữ ký phương thức mặc định tạo ra một xung đột mới
→ Trình biên dịch Java từ chối biên dịch trừ khi lớp triển khai ghi đè rõ ràng phương thức đó
→ Lập trình viên phải sử dụng `InterfaceName.super.method()` hoặc cung cấp logic tùy chỉnh
→ Xung đột luôn được giải quyết tại thời điểm biên dịch — không bao giờ để mơ hồ khi chạy

---

## Tại sao Lớp Trừu tượng và Giao diện phục vụ các Mục đích Thiết kế khác nhau (Why Abstract Classes and Interfaces Serve Different Design Purposes)

Sự lựa chọn giữa lớp trừu tượng và giao diện là một quyết định mang tính **ngữ nghĩa** về việc kiểu dữ liệu đó đại diện cho cái gì, chứ không chỉ thuần túy là quyết định kỹ thuật về việc cần tính năng ngôn ngữ nào. Một lớp trừu tượng mô hình hóa một **danh tính/bản sắc is-a (is-a identity)**: nó đại diện cho một danh mục thực tế, dễ nhận biết của các đối tượng chia sẻ trạng thái, vòng đời và một phần hành vi (ví dụ: mọi `BankAccount` đều có số dư và số tài khoản bất kể đó là tài khoản tiết kiệm hay thanh toán). Bởi vì lớp trừu tượng có thể khai báo các trường thể hiện và hàm khởi dựng, nó là nơi chứa tự nhiên cho trạng thái khả biến dùng chung (shared mutable state) mà tất cả các lớp con cần kế thừa và phát triển; các lớp con gọi `super(...)` để khởi tạo trạng thái dùng chung đó trước khi thêm trạng thái của riêng mình. Một giao diện mô hình hóa một **khả năng can-do (can-do capability)**: nó mô tả một vai trò hoặc một bản hợp đồng mà các lớp hoàn toàn không liên quan có thể đáp ứng (ví dụ: cả `Robot` và `Human` đều có thể là `Payable` - có thể được trả lương, nhưng chúng không có chung danh tính hay trạng thái). Quy tắc thiết kế giúp giải quyết sự lựa chọn này là **Nguyên lý Thay thế Liskov cùng với sự xuất hiện của trạng thái dùng chung (Liskov Substitution Principle together with the presence of shared state)**: nếu bản trừu tượng mang các trường thể hiện, yêu cầu một hàm khởi dựng, hoặc đại diện cho một phân cấp danh tính ổn định mà từ đó các lớp con thực sự kế thừa trạng thái và hành vi, hãy sử dụng lớp trừu tượng; nếu bản trừu tượng chỉ đơn thuần là một hợp đồng hành vi thuần túy có thể áp dụng trên các kiểu không liên quan mà không chiếm mất slot kế thừa duy nhất, hãy sử dụng giao diện. Trong Java hiện đại (8+), các giao diện hỗ trợ các phương thức `default` và `static`, thu hẹp khoảng cách — nhưng chúng vẫn không thể chứa trạng thái thể hiện (instance state), điều này vẫn là ranh giới cứng giữa hai cơ chế này.

### Mô hình tư duy (Mental Model)

```
LỚP TRỪU TƯỢNG — sử dụng khi có chung danh tính + trạng thái:

  abstract class BankAccount {
      private double balance;       // trạng thái dùng chung — trường thể hiện
      private String accountNumber; // trạng thái dùng chung — trường thể hiện
      BankAccount(String acct, double bal) { ... } // hàm khởi dựng dùng chung

      public double getBalance() { return balance; } // phương thức cụ thể dùng chung

      abstract void processInterest();               // điểm chuyên biệt hóa
  }

  SavingsAccount extends BankAccount  → IS-A BankAccount, kế thừa balance/accountNumber
  CheckingAccount extends BankAccount → IS-A BankAccount, kế thừa balance/accountNumber

GIAO DIỆN — sử dụng khi chỉ có hợp đồng hành vi giữa các kiểu không liên quan:

  interface Payable {
      void pay(double amount);   // chỉ có hợp đồng — không có trạng thái
  }

  SavingsAccount implements Payable  → CAN-DO paying (có khả năng thanh toán)
  Robot          implements Payable  → CAN-DO paying (nhánh phân cấp không liên quan!)
  Employee       implements Payable  → CAN-DO paying (kiểu dữ liệu hoàn toàn khác!)

Quy tắc Quyết định:
  Có trạng thái thể hiện dùng chung?  → Lớp Trừu tượng
  Chỉ có hợp đồng hành vi thuần túy?   → Giao diện
  Cần cả hai?                         → Lớp Trừu tượng + (Các) Giao diện
```

### Ví dụ Code (Code Example)

```java
// Lớp trừu tượng: chung danh tính + trạng thái
abstract class Shape {
    private String color; // trạng thái thể hiện dùng chung

    Shape(String color) { this.color = color; }

    public String getColor() { return color; }

    abstract double area(); // điểm chuyên biệt hóa
}

// Giao diện: khả năng thuần túy, có thể áp dụng cho các kiểu không liên quan
interface Printable {
    void print(); // chỉ có hợp đồng hành vi
}

class Circle extends Shape implements Printable {
    private double radius;

    Circle(String color, double radius) {
        super(color); // khởi tạo trạng thái dùng chung của Shape
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

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)

Kiểu dữ liệu cần mang các trường thể hiện và hàm khởi dựng cho trạng thái dùng chung
→ Sử dụng lớp trừu tượng: các lớp con gọi `super(...)` để khởi tạo trạng thái đó
→ Slot kế thừa duy nhất bị tiêu thụ; lớp con vẫn có thể triển khai nhiều giao diện
→ Kiểu dữ liệu chỉ là một hợp đồng hành vi không có trạng thái thể hiện
→ Sử dụng giao diện: bất kỳ lớp nào từ bất kỳ hệ thống phân cấp nào đều có thể triển khai nó
→ Có thể triển khai nhiều giao diện đồng thời — không bị xung đột slot kế thừa
→ Nếu cần cả trạng thái dùng chung và khả năng đa chiều/cắt ngang (cross-cutting capability):
→ Kế thừa một lớp trừu tượng cho danh tính/trạng thái VÀ triển khai một hoặc nhiều giao diện cho các khả năng tương ứng
