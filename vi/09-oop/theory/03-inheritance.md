# Kế thừa (Inheritance)

Kế thừa (Inheritance) là cơ chế lập trình hướng đối tượng (OOP) nơi một lớp con (subclass) thừa hưởng các trường dữ liệu (fields) và phương thức (methods) từ lớp cha (superclass), giúp thúc đẩy việc tái sử dụng mã nguồn và thiết lập **mối quan hệ IS-A (IS-A relationship)** (ví dụ: Dog IS-A Mammal - Chó là một loài động vật có vú).

---

## Các kiểu kế thừa trong Java (Types of Inheritance in Java)

Java hỗ trợ ba hình thức kế thừa lớp:
1. **Kế thừa đơn (Single Inheritance):** Một lớp kế thừa từ duy nhất một lớp cha (`B extends A`).
2. **Kế thừa đa cấp (Multilevel Inheritance):** Một lớp kế thừa từ một lớp con khác, tạo thành một chuỗi kế thừa (`C extends B` và `B extends A`).
3. **Kế thừa phân cấp (Hierarchical Inheritance):** Nhiều lớp con cùng kế thừa từ một lớp cha (`B extends A` và `C extends A`).

### Ví dụ Code: Chuỗi kế thừa (Kế thừa đa cấp) (Code Example: Inheritance Chain (Multilevel Inheritance))
Dưới đây là một ví dụ code cụ thể về chuỗi kế thừa để thiết lập mối quan hệ IS-A:

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

class Dog extends Mammal { // Multilevel inheritance chain: Dog IS-A Mammal, Mammal IS-A Animal
    void bark() {
        System.out.println("Woof!");
    }
}
```

---

## Đa kế thừa và Vấn đề Kim cương (Multiple Inheritance and the Diamond Problem)

Java **cấm đa kế thừa thông qua các lớp** (ví dụ: `class C extends A, B` là không hợp lệ) để tránh **Vấn đề Kim cương (Diamond Problem)**.

### Giải thích về Vấn đề Kim cương (The Diamond Problem Explained)
Nếu lớp `A` định nghĩa một phương thức `foo()`, và cả hai lớp `B` và `C` đều ghi đè `foo()` với các hành vi khác nhau, một lớp `D` kế thừa cả `B` và `C` sẽ nhận hai cài đặt xung đột của `foo()`. Việc gọi `new D().foo()` sẽ gây ra sự mơ hồ cho JVM.

Java giải quyết vấn đề này bằng cách:
1. Chỉ cho phép kế thừa đơn lớp.
2. Cho phép đa kế thừa hành vi thông qua **giao diện (interfaces)** (vì các xung đột phương thức trong interface bắt buộc phải được giải quyết tường minh bởi lớp triển khai).

---

## Từ khóa super (The super Keyword)

Từ khóa `super` tham chiếu trực tiếp đến lớp cha gần nhất.

### 3 Cách dùng chính của super (3 Main Uses of super)
1. **Để gọi hàm khởi dựng của lớp cha:** `super()` hoặc `super(args)`.
   - Phải là câu lệnh đầu tiên trong hàm khởi dựng của lớp con.
   - Nếu bị bỏ qua, trình biên dịch sẽ tự động chèn một lời gọi `super()` không tham số mặc định.
2. **Để truy cập các trường dữ liệu bị ẩn:** Phân biệt các trường dữ liệu của lớp con với các trường của lớp cha có tên trùng khớp (Ẩn trường dữ liệu - Field Hiding).
   ```java
   int parentVal = super.value;
   ```
3. **Để gọi các phương thức bị ghi đè:** Gọi phiên bản phương thức của lớp cha từ lớp con.
   ```java
   super.displayInfo();
   ```

---

## Luồng thực thi của Hàm khởi dựng (Constructor Execution Flow)

Hàm khởi dựng (Constructor) **không được kế thừa** bởi các lớp con. Tuy nhiên, khi một đối tượng lớp con được khởi tạo:
1. Hàm khởi dựng lớp con bắt đầu thực thi.
2. Nó lập tức ủy quyền ngược lên chuỗi kế thừa bằng cách thực thi `super()`.
3. Sự ủy quyền này đạt đến lớp cao nhất (`java.lang.Object`), hàm khởi dựng của lớp này sẽ chạy.
4. Các hàm khởi dựng thực thi theo thứ tự từ lớp cha cao nhất xuống đến lớp con.

> [!WARNING]
> Nếu lớp cha không định nghĩa hàm khởi dựng mặc định không tham số (vì nó chỉ có các hàm khởi dựng có tham số), hàm khởi dựng của lớp con **bắt buộc** phải gọi `super(args)` một cách tường minh ở dòng đầu tiên của nó, nếu không sẽ xảy ra lỗi biên dịch (compile-time error).

---

## Quy tắc Ghi đè Phương thức (Method Overriding Rules)

Ghi đè phương thức (Method Overriding) xảy ra khi một lớp con cung cấp một triển khai cụ thể cho một phương thức đã được định nghĩa trong lớp cha của nó.

### Ví dụ Code: Ghi đè với @Override (Code Example: Overriding with @Override)
Việc chú thích phương thức lớp con với `@Override` sẽ yêu cầu trình biên dịch xác minh rằng phương thức đó khớp với chữ ký (signature) trong lớp cha, giúp ngăn ngừa các lỗi do viết sai chính tả.

```java
class Vehicle {
    void fuelUp() {
        System.out.println("Refueling with generic fuel.");
    }
}

class ElectricCar extends Vehicle {
    @Override // Compiler verifies that fuelUp() exists in Vehicle
    void fuelUp() {
        System.out.println("Charging the battery pack.");
    }
}
```

### Các quy tắc thiết yếu khi Ghi đè (Essential Rules for Overriding)
1. **Chữ ký phương thức (Method Signature):** Phải có tên phương thức và danh sách tham số hoàn toàn giống nhau.
2. **Kiểu trả về (Return Type):** Phải giống nhau, hoặc là **kiểu trả về đồng biến (covariant return type)** (một lớp con của kiểu trả về của lớp cha).
   ```java
   class Food {}
   class Meat extends Food {}
   class Animal { Food getFood() { return new Food(); } }
   class Tiger extends Animal { @Override Meat getFood() { return new Meat(); } } // Covariant return type (Meat is Food)
   ```
3. **Phạm vi truy cập (Access Modifier):** Phương thức ghi đè không được phép có phạm vi hạn chế hơn phương thức bị ghi đè:
   - Lớp cha là `public` $\rightarrow$ Lớp con bắt buộc phải là `public`.
   - Lớp cha là `protected` $\rightarrow$ Lớp con phải là `protected` hoặc `public`.
   - Lớp cha là `default` $\rightarrow$ Lớp con phải là `default`, `protected`, hoặc `public`.
4. **Ngoại lệ (Exceptions):** Phương thức ghi đè không được ném ra các **ngoại lệ đã được kiểm tra (checked exceptions)** rộng hơn hoặc mới hơn. Nó có thể ném ra ít hơn, các ngoại lệ đã được kiểm tra hẹp hơn, hoặc bất kỳ ngoại lệ nào chưa được kiểm tra (unchecked/runtime exceptions).
5. **Các phương thức không thể ghi đè (Non-overridable methods):**
   - Các phương thức được đánh dấu là `final` hoặc `private` không thể bị ghi đè.
   - Các hàm khởi dựng (constructors) không thể bị ghi đè.
   - Các phương thức tĩnh (static methods) không thể bị ghi đè (xem phần Ẩn phương thức bên dưới).

---

## Ẩn Phương thức so với Ẩn Thuộc tính (Method Hiding vs. Field Hiding)

### Ẩn Phương thức (Phương thức tĩnh) (Method Hiding (Static Methods))
Nếu một lớp con định nghĩa một phương thức tĩnh có cùng chữ ký với một phương thức tĩnh trong lớp cha, phương thức của lớp cha sẽ bị **ẩn (hidden)** chứ không phải bị ghi đè (overridden).
- Các phương thức tĩnh được giải quyết tại thời điểm biên dịch (compile time) dựa trên **kiểu của biến tham chiếu (reference variable type)**, chứ không phải đối tượng thực tế ở vùng nhớ heap khi chạy (runtime).
- Đặt `@Override` trên một phương thức tĩnh sẽ gây ra lỗi biên dịch.

### Ẩn Trường dữ liệu (Field Hiding)
Nếu một lớp con khai báo một trường dữ liệu có cùng tên với trường dữ liệu của lớp cha, trường của lớp cha sẽ bị ẩn.
- Các trường dữ liệu **không có tính đa hình**. Chúng được giải quyết tĩnh tại thời điểm biên dịch dựa trên kiểu của biến tham chiếu.

```java
class Parent { int value = 10; }
class Child extends Parent { int value = 20; }

Parent p = new Child();
System.out.println(p.value); // Prints 10 (resolved by Reference Type Parent)
```

## Xem xét chuyên sâu: Quy tắc Thiết kế Kế thừa (Deep Review: Inheritance Design Rules)

Kế thừa rất mạnh mẽ nhưng dễ bị lạm dụng. Nó nên đại diện cho một mối quan hệ **is-a** bền vững, chứ không chỉ đơn thuần là "Tôi muốn tái sử dụng một số code".

Sử dụng kế thừa khi:

- Lớp con thực sự là một dạng chuyên biệt của lớp cha.
- Lớp cha định nghĩa hành vi mà các lớp con có thể kế thừa hoặc ghi đè một cách an toàn.
- Hệ thống phân cấp ổn định và ít có khả năng cần nhiều biến thể không liên quan.

Ưu tiên thành phần/kết hợp (composition) khi:

- Bạn chỉ muốn tái sử dụng phần triển khai.
- Mối quan hệ là "has-a" chứ không phải "is-a".
- Các hành vi khác nhau cần được hoán đổi một cách độc lập.

```java
class Engine { }

class Car {
    private final Engine engine; // composition: Car has an Engine
}
```

### Bẫy Ghi đè so với Ẩn (Overriding vs Hiding Traps)

- Các phương thức thể hiện (instance methods) có tính đa hình và có thể bị ghi đè.
- Các phương thức tĩnh bị ẩn chứ không bị ghi đè.
- Các trường dữ liệu bị ẩn chứ không bị ghi đè.
- Hàm khởi dựng không bao giờ được kế thừa.
- Các phương thức `private` không bị ghi đè vì lớp con không thể nhìn thấy chúng.

---

## Case Study: Tại sao gọi một phương thức bị ghi đè từ hàm khởi dựng lại nguy hiểm (Case Study: Why calling an overridden method from a constructor is dangerous)

Khi một đối tượng lớp con được khởi tạo, hàm khởi dựng của lớp cha sẽ thực thi trước. Nếu hàm khởi dựng lớp cha gọi một phương thức thể hiện mà lớp con ghi đè, triển khai của phương thức đó ở lớp con sẽ chạy **trước khi** hàm khởi dựng lớp con kịp khởi tạo các trường dữ liệu của chính nó.

### Kịch bản Ví dụ (Example Scenario)
Hãy xem xét điều gì xảy ra khi chúng ta khởi tạo `Child`:

```java
class Parent {
    Parent() {
        System.out.println("Parent Constructor starts.");
        printState(); // Calling polymorphic method from constructor!
    }

    void printState() {
        System.out.println("Parent state: active");
    }
}

class Child extends Parent {
    private String customConfig = "InitializedChildConfig"; // Field Initializer

    Child() {
        System.out.println("Child Constructor runs.");
    }

    @Override
    void printState() {
        // Danger: customConfig is still null here because Child's initializers and constructor haven't run!
        System.out.println("Child config length: " + (customConfig != null ? customConfig.length() : "NULL"));
    }
}

public class Main {
    public static void main(String[] args) {
        new Child();
    }
}
```

### Kết quả đầu ra (Output):
```text
Parent Constructor starts.
Child config length: NULL
Child Constructor runs.
```

### Điểm rút ra quan trọng (Key Takeaway)
Không bao giờ gọi các phương thức có thể ghi đè bên trong hàm khởi dựng. Nếu bạn phải gọi một phương thức để thực hiện công việc khởi tạo trong hàm khởi dựng, hãy đảm bảo rằng phương thức đó là `private` hoặc `final`, điều này ngăn phương thức đó bị ghi đè một cách đa hình.

---

## Các lỗi thường gặp (Common Mistakes)

### 1. Nhầm lẫn giữa Nạp chồng và Ghi đè (Lỗi chính tả chữ ký phương thức) (Confusing Overload with Override (Signature Typo))
Nếu chữ ký phương thức lớp con khác một chút so với lớp cha, Java sẽ coi đó là nạp chồng (overload) chứ không phải ghi đè (override). Nếu không có chú thích `@Override`, lỗi này vẫn sẽ biên dịch thành công mà không có cảnh báo nhưng sẽ không thực thi đa hình ở thời điểm chạy.
```java
class Printer {
    void print(String message) {}
}

class ColorPrinter extends Printer {
    // Mistake: parameter type Object instead of String. Overloads instead of overrides!
    void print(Object message) {} 
}
```

### 2. Cố gắng Ghi đè Phương thức tĩnh (Attempting to Override Static Methods)
Nếu một lớp con định nghĩa một phương thức tĩnh có cùng chữ ký với một phương thức tĩnh của lớp cha, nó sẽ **ẩn** phương thức đó chứ không phải ghi đè. Việc sử dụng `@Override` trên một phương thức tĩnh sẽ gây lỗi biên dịch.
```java
class Base {
    static void display() {}
}
class Derived extends Base {
    // @Override // Compile Error: static methods cannot be overridden
    static void display() {} 
}
```

### 3. Truy cập Đa hình vào các Trường bị ẩn (Accessing Hidden Fields Polymorphically)
Các trường dữ liệu được giải quyết tĩnh tại thời điểm biên dịch dựa trên kiểu của biến tham chiếu, chứ không phải kiểu đối tượng thực tế khi chạy. Việc khai báo một trường có cùng tên ở lớp con sẽ ẩn trường của lớp cha, điều này có thể dẫn đến sự nhầm lẫn.
```java
class Super { int val = 100; }
class Sub extends Super { int val = 200; }

Super obj = new Sub();
System.out.println(obj.val); // Prints 100 (resolved from Super reference type, not Sub)
```

### Liên kết tham khảo (Reference Links)

- Oracle Java Tutorials - Inheritance: https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html
- Oracle Java Tutorials - Overriding and hiding methods: https://docs.oracle.com/javase/tutorial/java/IandI/override.html
- Oracle Java Tutorials - Interfaces and Inheritance: https://docs.oracle.com/javase/tutorial/java/IandI/index.html
- Dev.java Inheritance: https://dev.java/learn/inheritance/

---

## Tại sao super() phải là câu lệnh đầu tiên trong Hàm khởi dựng của Lớp con (Why super() Must Be the First Statement in a Subclass Constructor)

Java yêu cầu `super()` (hoặc `super(args)`) phải là câu lệnh đầu tiên trong hàm khởi dựng của lớp con bởi vì một đảm bảo về mặt thứ tự cơ bản: mọi đối tượng trong hệ thống phân cấp phải được khởi tạo hoàn chỉnh từ đỉnh chuỗi xuống dưới trước khi bất kỳ code lớp con nào có thể tham chiếu tới `this`. Nếu hàm khởi dựng lớp con được phép thực thi các câu lệnh trước khi gọi `super()`, nó có thể đọc hoặc gọi các phương thức trên `this` trong khi phần lớp cha của đối tượng — các trường và bất kỳ khối khởi tạo nào của nó — vẫn chưa được chạy, tạo ra một đối tượng ở trạng thái chưa hoàn thiện, không hợp lệ. Đặc tả Ngôn ngữ Java (JLS §8.8.7) mã hóa ràng buộc này trực tiếp vào trình biên dịch: nếu không có lời gọi `super(...)` hoặc `this(...)` rõ ràng ở câu lệnh đầu tiên, trình biên dịch sẽ tự động chèn một lời gọi `super()` tới hàm khởi dựng không tham số của lớp cha trực tiếp. Sự chèn ngầm định này lan truyền lên toàn bộ chuỗi: mỗi lớp cha cũng sẽ gọi `super()` của chính nó, cho đến khi chạm tới và thực thi hàm khởi dựng của `java.lang.Object`. Hàm khởi dựng của `Object` thực hiện các ghi chép cấp phát cuối cùng và là gốc rễ của mọi chuỗi khởi dựng. Các hàm khởi dựng sau đó trả về theo thứ tự LIFO (Vào sau ra trước) — Object hoàn thành trước, sau đó đến từng lớp trung gian, và cuối cùng là lớp con — do đó tại thời điểm thân của lớp con hoàn thành, mọi trạng thái tổ tiên của nó được đảm bảo đã được khởi tạo.

### Mô hình tư duy (Mental Model)

```
new SportsCar("Ferrari", 300)
        |
        v
  Hàm khởi dựng SportsCar được gọi
        |
        |-- super("Ferrari") phải là câu lệnh đầu tiên
        v
  Hàm khởi dựng Car được gọi
        |
        |-- super() được chèn ngầm định bởi trình biên dịch
        v
  Hàm khởi dựng Vehicle được gọi
        |
        |-- super() được chèn ngầm định bởi trình biên dịch
        v
  Hàm khởi dựng Object được gọi
        |
        Các trường của Object được khởi tạo  <-- gốc của chuỗi
        |
  Hàm khởi dựng Object trả về
        |
  Các trường của Vehicle được khởi tạo & thân hàm chạy
        |
  Các trường của Car được khởi tạo & thân hàm chạy
        |
  Các trường của SportsCar được khởi tạo & thân hàm chạy
        |
  Đối tượng được dựng hoàn chỉnh, tham chiếu trả về cho phía gọi
```

### Ví dụ Code (Code Example)

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
        super(brand); // MUST be first — compiler enforces this
        this.topSpeed = topSpeed;
        System.out.println("Car constructor: topSpeed = " + topSpeed);
    }
}

class SportsCar extends Car {
    private String model;

    SportsCar(String brand, int topSpeed, String model) {
        super(brand, topSpeed); // delegates to Car, which delegates to Vehicle, which delegates to Object
        this.model = model;
        System.out.println("SportsCar constructor: model = " + model);
    }
}

public class Main {
    public static void main(String[] args) {
        new SportsCar("Ferrari", 300, "F40");
    }
}
// Output:
// Vehicle constructor: brand = Ferrari
// Car constructor: topSpeed = 300
// SportsCar constructor: model = F40
```

### Chuỗi Nguyên nhân - Kết quả (Cause-Effect Chain)

`new SportsCar(...)` được gọi
→ Hàm khởi dựng SportsCar bắt đầu; `super(brand, topSpeed)` là câu lệnh đầu tiên
→ Hàm khởi dựng Car bắt đầu; `super(brand)` là câu lệnh đầu tiên
→ Hàm khởi dựng Vehicle bắt đầu; trình biên dịch chèn `super()` ngầm định
→ Hàm khởi dựng Object chạy trước, hoàn thành khởi tạo gốc
→ Các trường và thân hàm của Vehicle hoàn tất
→ Các trường và thân hàm của Car hoàn tất
→ Các trường và thân hàm của SportsCar hoàn tất
→ Tham chiếu đối tượng đã khởi tạo hoàn toàn được trả về cho phía gọi với mọi trạng thái tổ tiên được đảm bảo hợp lệ
