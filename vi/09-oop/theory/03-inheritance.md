# Tính Kế Thừa (Inheritance)

Tính kế thừa (inheritance) là một cơ chế trong lập trình hướng đối tượng (OOP) cho phép một lớp con (subclass) thừa hưởng các trường dữ liệu (field) và phương thức (method) từ một lớp cha (superclass), từ đó thúc đẩy việc tái sử dụng mã nguồn và thiết lập **quan hệ IS-A** (ví dụ: Chó LÀ MỘT (IS-A) Loài Động Vật Có Vú).

---

## Các Loại Kế Thừa Trong Java (Types of Inheritance in Java)

Java hỗ trợ ba hình thức kế thừa lớp:
1. **Đơn Kế Thừa (Single Inheritance):** Một lớp chỉ kế thừa từ duy nhất một lớp cha (`B extends A`).
2. **Kế Thừa Nhiều Cấp (Multilevel Inheritance):** Một lớp kế thừa từ một lớp con khác, tạo thành một chuỗi kế thừa (`C extends B` và `B extends A`).
3. **Kế Thừa Phân Cấp (Hierarchical Inheritance):** Nhiều lớp con cùng kế thừa từ một lớp cha duy nhất (`B extends A` và `C extends A`).

### Ví Dụ Minh Họa Code: Chuỗi Kế Thừa (Kế Thừa Nhiều Cấp) (Code Example: Inheritance Chain (Multilevel Inheritance))
Dưới đây là ví dụ mã nguồn thực tế về một chuỗi kế thừa thiết lập các quan hệ IS-A:

```java
class Animal {
    void eat() {
        System.out.println("This animal eats food.");
    }
}

class Mammal extends Animal {
    void breathe() {
        System.out.println("This mammal breathes air.");
    }
}

class Dog extends Mammal { // Chuỗi kế thừa nhiều cấp: Dog IS-A Mammal, Mammal IS-A Animal
    void bark() {
        System.out.println("Woof!");
    }
}
```

---

## Đa Kế Thừa và Bài Toán Kim Cương (Multiple Inheritance and the Diamond Problem)

Java **cấm việc đa kế thừa thông qua lớp** (ví dụ: khai báo `class C extends A, B` là không hợp lệ) để tránh **Bài Toán Kim Cương (Diamond Problem)**.

### Giải Thích Về Bài Toán Kim Cương (The Diamond Problem Explained)
Nếu lớp `A` định nghĩa phương thức `foo()`, và hai lớp `B` và `C` đều ghi đè (override) phương thức `foo()` với các hành vi khác nhau, thì một lớp `D` kế thừa đồng thời cả hai lớp `B` và `C` sẽ thừa hưởng hai triển khai xung đột nhau của `foo()`. Việc gọi câu lệnh `new D().foo()` sẽ gây ra sự mơ hồ, không thể xác định cho JVM.

Java giải quyết vấn đề này bằng cách:
1. Chỉ cho phép đơn kế thừa lớp.
2. Cho phép đa kế thừa về hành vi thông qua các **giao diện (interface)** (bởi vì các xung đột phương thức trong giao diện bắt buộc phải được giải quyết một cách rõ ràng bởi lớp triển khai).

---

## Từ Khóa `super` (The super Keyword)

Từ khóa `super` dùng để tham chiếu trực tiếp đến lớp cha gần nhất.

### 3 Cách Dùng Chính Của `super`:
1. **Gọi hàm khởi tạo (constructor) của lớp cha:** Sử dụng cú pháp `super()` hoặc `super(args)`.
   - Phải là câu lệnh đầu tiên trong hàm khởi tạo của lớp con.
   - Nếu bị lược bỏ, trình biên dịch sẽ tự động chèn lời gọi `super()` mặc định không tham số.
2. **Truy cập các trường dữ liệu bị che khuất:** Phân biệt trường của lớp con với trường của lớp cha có cùng tên (hiện tượng che giấu trường dữ liệu (field hiding)).
   ```java
   int parentVal = super.value;
   ```
3. **Gọi các phương thức bị ghi đè:** Gọi phiên bản phương thức của lớp cha từ lớp con.
   ```java
   super.displayInfo();
   ```

---

## Luồng Thực Thi Hàm Khởi Tạo (Constructor Execution Flow)

Các hàm khởi tạo **không được kế thừa** bởi lớp con. Tuy nhiên, khi một đối tượng lớp con được khởi tạo:
1. Hàm khởi tạo của lớp con bắt đầu thực thi.
2. Nó lập tức ủy quyền ngược lên chuỗi kế thừa bằng cách thực thi `super()`.
3. Sự ủy quyền này tiếp tục cho đến khi chạm đến lớp cao nhất (`java.lang.Object`), nơi hàm khởi tạo của nó được chạy.
4. Các hàm khởi tạo sẽ thực thi tuần tự từ lớp cha cao nhất xuống dần đến lớp con.

> [!WARNING]
> Nếu một lớp cha không định nghĩa hàm khởi tạo mặc định không tham số (vì nó chỉ có các hàm khởi tạo có tham số), thì hàm khởi tạo của lớp con **bắt buộc** phải gọi `super(args)` một cách tường minh ngay tại dòng đầu tiên của nó, nếu không sẽ xảy ra lỗi biên dịch.

---

## Quy Tắc Ghi Đè Phương Thức (Method Overriding Rules)

Ghi đè phương thức (method overriding) xảy ra khi một lớp con cung cấp một triển khai cụ thể cho một phương thức đã được định nghĩa trong lớp cha của nó.

### Ví Dụ Minh Họa Code: Ghi Đè Với `@Override` (Code Example: Overriding with @Override)
Việc sử dụng chú thích (annotation) `@Override` cho phương thức ở lớp con sẽ chỉ dẫn trình biên dịch kiểm tra xem phương thức đó có khớp với chữ ký phương thức (signature) nào trong lớp cha hay không, giúp ngăn ngừa các lỗi logic do gõ sai tên.

```java
class Vehicle {
    void fuelUp() {
        System.out.println("Refueling with generic fuel.");
    }
}

class ElectricCar extends Vehicle {
    @Override // Trình biên dịch kiểm tra xem fuelUp() có tồn tại trong Vehicle hay không
    void fuelUp() {
        System.out.println("Charging the battery pack.");
    }
}
```

### Các Quy Tắc Bắt Buộc Khi Ghi Đè:
1. **Chữ Ký Phương Thức:** Phải có tên phương thức và danh sách tham số hoàn toàn giống hệt nhau.
2. **Kiểu Trả Về:** Phải giống nhau, hoặc là một **kiểu trả về đồng biến (covariant return type)** (một lớp con của kiểu trả về của lớp cha).
   ```java
   class Food {}
   class Meat extends Food {}
   class Animal { Food getFood() { return new Food(); } }
   class Tiger extends Animal { @Override Meat getFood() { return new Meat(); } } // Kiểu trả về đồng biến (Meat is Food)
   ```
3. **Phạm Vi Truy Cập (Access Modifier):** Phương thức ghi đè ở lớp con không được phép hạn chế quyền truy cập hơn so với phương thức ở lớp cha:
   - Lớp cha là `public` $\rightarrow$ Lớp con bắt buộc phải là `public`.
   - Lớp cha là `protected` $\rightarrow$ Lớp con phải là `protected` hoặc `public`.
   - Lớp cha là `default` $\rightarrow$ Lớp con phải là `default`, `protected`, hoặc `public`.
4. **Ngoại Lệ (Exceptions):** Phương thức ghi đè không được phép ném ra các **ngoại lệ kiểm tra (checked exception)** mới hoặc có phạm vi rộng hơn. Nó có thể ném ra ít hơn, các ngoại lệ kiểm tra hẹp hơn, hoặc bất kỳ ngoại lệ không kiểm tra (unchecked exception) nào.
5. **Các phương thức không thể ghi đè:**
   - Các phương thức được đánh dấu từ khóa `final` hoặc `private` không thể bị ghi đè.
   - Các hàm khởi tạo không thể bị ghi đè.
   - Các phương thức tĩnh (static method) không thể bị ghi đè (xem phần Che Giấu Phương Thức bên dưới).

---

## Che Giấu Phương Thức so với Che Giấu Trường Dữ Liệu (Method Hiding vs. Field Hiding)

### Che Giấu Phương Thức (Method Hiding - Áp Dụng Với Phương Thức Tĩnh)
Nếu một lớp con định nghĩa một phương thức tĩnh có cùng chữ ký với một phương thức tĩnh trong lớp cha, phương thức của lớp cha sẽ bị **che giấu (hidden)** chứ không phải bị ghi đè.
- Các phương thức tĩnh được phân giải tại thời điểm biên dịch dựa trên **kiểu của biến tham chiếu**, chứ không dựa vào đối tượng thực tế lúc runtime trong bộ nhớ Heap.
- Việc đặt chú thích `@Override` trên một phương thức tĩnh sẽ gây ra lỗi biên dịch.

### Che Giấu Trường Dữ Liệu (Field Hiding)
Nếu một lớp con khai báo một trường dữ liệu trùng tên với một trường trong lớp cha, trường của lớp cha sẽ bị che giấu.
- Các trường dữ liệu **không có tính đa hình**. Chúng được phân giải tĩnh tại thời điểm biên dịch dựa trên kiểu của biến tham chiếu.

```java
class Parent { int value = 10; }
class Child extends Parent { int value = 20; }

Parent p = new Child();
System.out.println(p.value); // In ra 10 (được phân giải theo Kiểu Tham Chiếu Parent)
```

## Đánh Giá Sâu: Quy Tắc Thiết Kế Kế Thừa (Deep Review: Inheritance Design Rules)

Kế thừa là một công cụ mạnh mẽ nhưng rất dễ bị lạm dụng. Nó chỉ nên đại diện cho một mối quan hệ **is-a** ổn định, bền vững, chứ không chỉ đơn thuần là "tôi muốn tái sử dụng một số đoạn mã có sẵn".

Sử dụng kế thừa khi:

- Lớp con thực sự là một phiên bản chuyên biệt hóa của lớp cha.
- Lớp cha định nghĩa các hành vi mà các lớp con có thể kế thừa hoặc ghi đè một cách an toàn.
- Hệ thống phân cấp lớp ổn định và ít có khả năng cần nhiều biến thể không liên quan.

Ưu tiên sử dụng quan hệ bao hàm (composition) khi:

- Bạn chỉ muốn tái sử dụng triển khai code có sẵn.
- Mối quan hệ giữa hai thực thể là "has-a" (có một) chứ không phải là "is-a" (là một).
- Các hành vi khác nhau cần được hoán đổi cho nhau một cách độc lập.

```java
class Engine { }

class Car {
    private final Engine engine; // composition: Car có một Engine
}
```

### Các Bẫy Ghi Đè so với Che Giấu (Overriding vs Hiding Traps)

- Các phương thức thực thể (instance method) có tính đa hình và có thể bị ghi đè.
- Các phương thức tĩnh bị che giấu, không phải bị ghi đè.
- Các trường dữ liệu bị che giấu, không phải bị ghi đè.
- Các hàm khởi tạo không bao giờ được kế thừa.
- Các phương thức private không thể bị ghi đè vì lớp con không thể nhìn thấy chúng.

---

## Ví Dụ Thực Tế: Tại sao việc gọi một phương thức bị ghi đè từ hàm khởi tạo lại nguy hiểm (Case Study: Why calling an overridden method from a constructor is dangerous)

Khi một đối tượng lớp con được khởi tạo, hàm khởi tạo của lớp cha sẽ thực thi trước. Nếu hàm khởi tạo của lớp cha gọi một phương thức thực thể mà lớp con đã ghi đè, thì triển khai của phương thức đó ở lớp con sẽ chạy **trước khi** hàm khởi tạo của lớp con kịp khởi tạo các trường dữ liệu của riêng nó.

### Kịch Bản Ví Dụ
Hãy xem điều gì xảy ra khi chúng ta khởi tạo một đối tượng của lớp `Child`:

```java
class Parent {
    Parent() {
        System.out.println("Parent Constructor starts.");
        printState(); // Gọi phương thức đa hình từ hàm khởi tạo!
    }

    void printState() {
        System.out.println("Parent state: active");
    }
}

class Child extends Parent {
    private String customConfig = "InitializedChildConfig"; // Khởi tạo trường trực tiếp

    Child() {
        System.out.println("Child Constructor runs.");
    }

    @Override
    void printState() {
        // Nguy hiểm: customConfig vẫn là null ở đây vì các bộ khởi tạo và hàm khởi tạo của Child chưa được chạy!
        System.out.println("Child config length: " + (customConfig != null ? customConfig.length() : "NULL"));
    }
}

public class Main {
    public static void main(String[] args) {
        new Child();
    }
}
```

### Kết Quả In Ra:
```text
Parent Constructor starts.
Child config length: NULL
Child Constructor runs.
```

### Bài Học Quan Trọng
Tuyệt đối không gọi các phương thức có thể ghi đè bên trong các hàm khởi tạo. Nếu bạn bắt buộc phải gọi một phương thức để thực hiện công việc khởi tạo trong constructor, hãy đảm bảo phương thức đó là `private` hoặc `final`, điều này sẽ ngăn không cho nó bị ghi đè đa hình ở các lớp con.

---

## Các Lỗi Thường Gặp (Common Mistakes)

### 1. Nhầm Lẫn Giữa Nạp Chồng và Ghi Đè (Sai Lệch Chữ Ký Phương Thức) (Confusing Overload with Override)
If the subclass method signature differs slightly from the parent's, Java treats it as an overload, not an override. Without the `@Override` annotation, this mistake will compile silently but fail to execute polymorphically at runtime.
```java
class Printer {
    void print(String message) {}
}

class ColorPrinter extends Printer {
    // Lỗi: tham số kiểu Object thay vì String. Đây là nạp chồng chứ không phải ghi đè!
    void print(Object message) {} 
}
```

### 2. Cố Gắng Ghi Đè Phương Thức Tĩnh
Nếu một lớp con định nghĩa một phương thức tĩnh có cùng chữ ký với phương thức tĩnh của lớp cha, nó chỉ **che giấu** phương thức đó chứ không phải ghi đè. Sử dụng chú thích `@Override` trên một phương thức tĩnh sẽ gây lỗi biên dịch.
```java
class Base {
    static void display() {}
}
class Derived extends Base {
    // @Override // Lỗi Biên Dịch: phương thức tĩnh không thể bị ghi đè
    static void display() {} 
}
```

### 3. Truy Cập Các Trường Bị Che Giấu Theo Cách Đa Hình
Các trường dữ liệu được phân giải tĩnh tại thời điểm biên dịch dựa trên kiểu của biến tham chiếu, chứ không dựa trên kiểu đối tượng thực tế lúc runtime. Việc khai báo một trường trùng tên ở lớp con sẽ che giấu trường của lớp cha, rất dễ dẫn đến sự nhầm lẫn ngoài ý muốn.
```java
class Super { int val = 100; }
class Sub extends Super { int val = 200; }

Super obj = new Sub();
System.out.println(obj.val); // In ra 100 (được phân giải từ kiểu tham chiếu Super, không phải Sub)
```

### Liên Kết Tham Khảo (Reference Links)

- Hướng dẫn Java của Oracle - Kế thừa: https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html
- Hướng dẫn Java của Oracle - Ghi đè và che giấu phương thức: https://docs.oracle.com/javase/tutorial/java/IandI/override.html
- Hướng dẫn Java của Oracle - Giao diện và Kế thừa: https://docs.oracle.com/javase/tutorial/java/IandI/index.html
- Dev.java Kế thừa: https://dev.java/learn/inheritance/

---

## Tại Sao super() Phải Là Câu Lệnh Đầu Tiên Trong Hàm Khởi Tạo Của Lớp Con (Why super() Must Be the First Statement in a Subclass Constructor)

Java yêu cầu `super()` (hoặc `super(args)`) bắt buộc phải là câu lệnh đầu tiên trong hàm khởi tạo của lớp con vì một nguyên tắc đảm bảo thứ tự khởi tạo cốt lõi: mọi đối tượng trong một hệ thống phân cấp phải được khởi tạo hoàn chỉnh tuần tự từ trên xuống dưới trước khi bất kỳ đoạn mã nào của lớp con có thể tham chiếu tới `this`. Nếu hàm khởi tạo của lớp con được phép thực thi các câu lệnh trước khi gọi `super()`, nó có thể đọc dữ liệu hoặc gọi các phương thức trên `this` trong khi phần đối tượng của lớp cha — bao gồm các trường dữ liệu và các khối khởi tạo của nó — vẫn chưa được chạy, dẫn đến việc tạo ra một đối tượng ở trạng thái lấp lửng, chưa hoàn thiện và không hợp lệ. 

Đặc tả Ngôn ngữ Java (JLS §8.8.7) mã hóa ràng buộc này trực tiếp vào trình biên dịch: nếu không có lời gọi `super(...)` hoặc `this(...)` rõ ràng xuất hiện dưới dạng câu lệnh đầu tiên, trình biên dịch sẽ tự động chèn một lời gọi `super()` không tham số tới lớp cha gần nhất. Sự chèn ngầm định này lan truyền ngược lên toàn bộ chuỗi kế thừa: mỗi lớp cha cũng gọi `super()` của chính nó, cho đến khi chạm tới hàm khởi tạo của `java.lang.Object`. Hàm khởi tạo của `Object` thực hiện các thủ tục cấp phát bộ nhớ cuối cùng và là gốc rễ của mọi chuỗi hàm khởi tạo. Sau đó, các hàm khởi tạo trả về theo thứ tự LIFO (vào sau ra trước) — Object hoàn thành trước, sau đó đến từng lớp trung gian, và cuối cùng là lớp con — vì vậy khi phần thân hàm khởi tạo của lớp con hoàn tất, trạng thái của tất cả các lớp tổ tiên được đảm bảo đã khởi tạo hợp lệ.

### Mô Hình Tư Duy (Mental Model)

```
new SportsCar("Ferrari", 300)
        |
        v
  Hàm khởi tạo SportsCar được gọi
        |
        |-- super("Ferrari") bắt buộc phải là câu lệnh đầu tiên
        v
  Hàm khởi tạo Car được gọi
        |
        |-- super() được trình biên dịch tự động chèn ngầm định
        v
  Hàm khởi tạo Vehicle được gọi
        |
        |-- super() được trình biên dịch tự động chèn ngầm định
        v
  Hàm khởi tạo Object được gọi
        |
        Các trường của Object được khởi tạo  <-- Gốc rễ của chuỗi
        |
  Hàm khởi tạo Object trả về
        |
  Các trường của Vehicle được khởi tạo & thân hàm chạy
        |
  Các trường của Car được khởi tạo & thân hàm chạy
        |
  Các trường của SportsCar được khởi tạo & thân hàm chạy
        |
  Đối tượng được xây dựng hoàn chỉnh, tham chiếu được trả về cho bên gọi
```

### Ví Dụ Minh Họa Code (Code Example)

```java
class Vehicle {
    private String brand;

    Vehicle(String brand) {
        this.brand = brand;
        System.out.println("Vehicle constructor: brand = " + brand);
    }

    public String getBrand() { return brand; }
}

class Car extends Vehicle {
    private int topSpeed;

    Car(String brand, int topSpeed) {
        super(brand); // BẮT BUỘC phải đầu tiên — trình biên dịch thực thi quy tắc này
        this.topSpeed = topSpeed;
        System.out.println("Car constructor: topSpeed = " + topSpeed);
    }
}

class SportsCar extends Car {
    private String model;

    SportsCar(String brand, int topSpeed, String model) {
        super(brand, topSpeed); // ủy quyền cho Car, Car ủy quyền cho Vehicle, Vehicle ủy quyền cho Object
        this.model = model;
        System.out.println("SportsCar constructor: model = " + model);
    }
}

public class Main {
    public static void main(String[] args) {
        new SportsCar("Ferrari", 300, "F40");
    }
}
// Kết Quả:
// Vehicle constructor: brand = Ferrari
// Car constructor: topSpeed = 300
// SportsCar constructor: model = F40
```

### Chuỗi Nguyên Nhân - Kết Quả (Cause-Effect Chain)

Lời gọi `new SportsCar(...)` được kích hoạt
→ Hàm khởi tạo SportsCar bắt đầu; `super(brand, topSpeed)` là câu lệnh đầu tiên
→ Hàm khởi tạo Car bắt đầu; `super(brand)` là câu lệnh đầu tiên
→ Hàm khởi tạo Vehicle bắt đầu; trình biên dịch chèn lời gọi `super()` ngầm định
→ Hàm khởi tạo Object chạy đầu tiên, hoàn tất quá trình khởi tạo gốc rễ
→ Các trường và thân hàm khởi tạo của Vehicle hoàn thành
→ Các trường và thân hàm khởi tạo của Car hoàn thành
→ Các trường và thân hàm khởi tạo của SportsCar hoàn thành
→ Tham chiếu đối tượng được khởi tạo đầy đủ được trả về cho bên gọi với trạng thái của tất cả các lớp cha được đảm bảo hoàn toàn hợp lệ.
