# Các mẫu thiết kế cơ bản thường thấy trong Java - Phần 1 (Basic Design Patterns Commonly Seen in Java - Part 1)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm một phần trọng tâm của các **Mẫu thiết kế (Design Patterns)** khởi tạo và cấu trúc (GoF) được sử dụng rộng rãi trong Java. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Singleton` | Giới hạn việc khởi tạo lớp ở duy nhất một đối tượng với quyền truy cập toàn cục. |
| `Factory Method` | Ủy quyền khởi tạo đối tượng cho các lớp con bằng cách sử dụng chữ ký phương thức nhà máy. |
| `Abstract Factory` | Giao diện tạo ra các họ đối tượng liên quan mà không cần chỉ định các lớp cụ thể. |
| `Builder` | Xây dựng từng bước các đối tượng phức tạp bằng cách sử dụng một API trôi chảy (fluent API). |
| `Prototype` | Tạo các đối tượng mới bằng cách sao chép một thực thể đã được cấu hình trước. |
| `Adapter` | Thống nhất các giao diện không tương thích bằng cách bọc một lớp nguồn bên trong một adapter. |
| `Decorator` | Bổ sung các tính năng cho một đối tượng một cách năng động bằng cách bọc các biến instance. |
| `Facade` | Cung cấp một API đơn giản hóa làm đại diện cho một phân hệ phức tạp bên dưới. |
| `Proxy` | Cung cấp một đối tượng đại diện để kiểm soát truy cập, ghi nhật ký hoặc tải lười biếng thực thể đích. |
| `Strategy` | Bao đóng các thuật toán có thể hoán đổi cho nhau được lựa chọn tại thời điểm chạy. |

---

## Ghi chú chi tiết (Detailed Notes)

### Singleton

Giới hạn một lớp chỉ có duy nhất một thực thể và cung cấp một điểm truy cập toàn cục.

- **Ví dụ có thể chạy được (Khóa kiểm tra hai lần - Double-Checked Locking)**:
  ```java
  public final class DatabaseConnection {
      // volatile prevents instruction reordering issues during instantiation
      private static volatile DatabaseConnection instance;

      private DatabaseConnection() {
          // Prevent reflection breaking constructor encapsulation
          if (instance != null) {
              throw new IllegalStateException("Instance already exists!");
          }
      }

      public static DatabaseConnection getInstance() {
          if (instance == null) { // First check (no synchronization overhead)
              synchronized (DatabaseConnection.class) {
                  if (instance == null) { // Second check
                      instance = new DatabaseConnection();
                  }
              }
          }
          return instance;
      }
  }
  ```

- **Sai lầm thường gặp / Chế độ thất bại**:
  - **Thiếu từ khóa Volatile**: Không có `volatile`, trình biên dịch/JVM có thể tái sắp xếp lệnh (phân bổ bộ nhớ &rarr; công bố tham chiếu &rarr; chạy hàm khởi tạo). Một luồng khác có thể đọc được thực thể mới chỉ được khởi tạo một nửa.
  - **Enum Singleton**: Cách triển khai Singleton an toàn tuyệt đối nhất là sử dụng một enum có duy nhất một phần tử, giúp xử lý các cuộc tấn công tuần tự hóa (serialization) và reflection một cách tự nhiên:
    ```java
    public enum SafeSingleton {
        INSTANCE;
        public void performAction() {}
    }
    ```

---

### Factory Method

Định nghĩa một giao diện để tạo một đối tượng nhưng để các lớp con quyết định lớp nào sẽ được khởi tạo.

- **Ví dụ có thể chạy được**:
  ```java
  public abstract class Dialog {
      public void renderWindow() {
          Button okButton = createButton();
          okButton.render();
      }
      // Subclasses override this factory method to supply different buttons
      protected abstract Button createButton();
  }

  public class WindowsDialog extends Dialog {
      protected Button createButton() { return new WindowsButton(); }
  }
  ```

---

### Abstract Factory

Cung cấp một interface để tạo ra các họ đối tượng có liên quan hoặc phụ thuộc lẫn nhau mà không cần chỉ định các lớp cụ thể của chúng.

- **Ví dụ có thể chạy được**:
  ```java
  public interface GUIFactory {
      Button createButton();
      Checkbox createCheckbox();
  }

  public class MacFactory implements GUIFactory {
      public Button createButton() { return new MacButton(); }
      public Checkbox createCheckbox() { return new MacCheckbox(); }
  }
  ```

---

### Builder

Tách biệt quá trình xây dựng một đối tượng phức tạp khỏi biểu diễn của nó, cho phép lắp ráp từng bước các trường (đặc biệt hữu ích khi lớp chứa nhiều tham số tùy chọn).

- **Ví dụ có thể chạy được**:
  ```java
  public class User {
      private final String name; // Required
      private final int age;     // Optional

      private User(Builder builder) {
          this.name = builder.name;
          this.age = builder.age;
      }

      public static class Builder {
          private final String name;
          private int age;

          public Builder(String name) { this.name = name; }
          public Builder age(int age) { this.age = age; return this; }
          public User build() { return new User(this); }
      }
  }

  // Usage:
  User u = new User.Builder("Bob").age(30).build();
  ```

---

### Prototype

Tạo ra các đối tượng mới bằng cách sao chép (nhân bản - cloning) một thực thể hiện có (prototype) thay vì tạo mới chúng hoàn toàn bằng từ khóa `new`.

- **Ví dụ có thể chạy được**:
  ```java
  public interface Prototype {
      Prototype clone();
  }

  public class Cell implements Prototype {
      private String color;

      public Cell(Cell target) { if (target != null) this.color = target.color; }
      public Cell clone() { return new Cell(this); }
  }
  ```

---

### Adapter

Chuyển đổi giao diện của một lớp thành một giao diện khác mà client mong đợi, cho phép các lớp có giao diện không tương thích có thể làm việc cùng nhau.

- **Ví dụ có thể chạy được**:
  ```java
  public interface TypeCInput { void connectTypeC(); }
  
  public class LegacyUsbCable { void plugUsb() {} }

  // Adapter wraps LegacyUsbCable to expose TypeCInput interface
  public class UsbToTypeCAdapter implements TypeCInput {
      private final LegacyUsbCable usbCable;

      public UsbToTypeCAdapter(LegacyUsbCable cable) { this.usbCable = cable; }
      public void connectTypeC() { usbCable.plugUsb(); }
  }
  ```

---

### Decorator

Gắn thêm các trách nhiệm cho đối tượng một cách năng động. Decorator cung cấp một giải pháp thay thế linh hoạt cho kế thừa lớp để mở rộng chức năng.

- **Ví dụ có thể chạy được**:
  ```java
  public interface Coffee { double getCost(); }

  public class SimpleCoffee implements Coffee { public double getCost() { return 2.0; } }

  public class MilkDecorator implements Coffee {
      private final Coffee coffee;

      public MilkDecorator(Coffee coffee) { this.coffee = coffee; }
      public double getCost() { return coffee.getCost() + 0.5; }
  }
  ```

---

### Facade

Cung cấp một giao diện thống nhất, đơn giản hóa cho một tập hợp các giao diện trong một phân hệ phức tạp.

- **Ví dụ**: Tạo một `HomeTheaterFacade` trừu tượng hóa các cuộc gọi tới `Amplifier.on()`, `DvdPlayer.play(movie)`, `Projector.widescreenMode()` thành một phương thức duy nhất: `facade.watchMovie("Inception")`.

---

### Proxy

Cung cấp một đối tượng thay thế hoặc đại diện cho một đối tượng khác để kiểm soát truy cập vào đối tượng đó (tải lười biếng, kiểm tra quyền truy cập, ghi nhật ký, lưu vào bộ đệm).

- **Ví dụ có thể chạy được**:
  ```java
  public interface Image { void display(); }

  public class RealImage implements Image {
      public RealImage(String filename) { loadFromDisk(filename); }
      public void display() {}
      private void loadFromDisk(String filename) {}
  }

  // Proxy lazy loads RealImage only when display() is actually called
  public class ProxyImage implements Image {
      private RealImage realImage;
      private final String filename;

      public ProxyImage(String filename) { this.filename = filename; }
      public void display() {
          if (realImage == null) realImage = new RealImage(filename);
          realImage.display();
      }
  }
  ```

---

### Strategy

Định nghĩa một họ các thuật toán, bao đóng từng thuật toán và làm cho chúng có thể hoán đổi cho nhau tại thời điểm chạy.

- **Ví dụ có thể chạy được**:
  ```java
  public interface PaymentStrategy { void pay(int amount); }

  public class CreditCardPayment implements PaymentStrategy { public void pay(int amount) {} }
  public class PayPalPayment implements PaymentStrategy { public void pay(int amount) {} }

  public class ShoppingCart {
      public static void main(String[] args) {
          ShoppingCart cart = new ShoppingCart();
          cart.checkout(100, new CreditCardPayment());
      }
  }
  ```

---

## Tại sao Khóa kiểm tra hai lần đảm bảo Singleton an toàn đa luồng (Why Double-Checked Locking Ensures Thread-Safe Singleton)

Mẫu khóa kiểm tra hai lần (double-checked locking) được thiết kế để giảm thiểu chi phí đồng bộ hóa trong khởi tạo lười biếng. Theo Mô hình Bộ nhớ Java (Java Memory Model), nếu không có kiểm tra hai lần, mỗi cuộc gọi tới `getInstance()` sẽ yêu cầu lấy khóa giám sát cấp lớp (class-level monitor), tạo ra một nút thắt cổ chai hiệu năng nghiêm trọng. Trong mẫu này, lượt kiểm tra đầu tiên bỏ qua hoàn toàn việc đồng bộ hóa một khi thực thể đã được khởi tạo. Nếu thực thể là null, luồng sẽ lấy khóa và thực hiện lần kiểm tra thứ hai để xác minh xem một luồng khác đã khởi tạo singleton trong lúc luồng này chờ lấy khóa hay chưa.

Điều quan trọng là tham chiếu thực thể phải được khai báo là `volatile` để ngăn chặn việc tái sắp xếp lệnh (instruction reordering) do trình biên dịch hoặc JVM. Việc tạo một đối tượng mới `new Singleton()` không phải là một thao tác nguyên tử (atomic); nó liên quan đến việc phân bổ bộ nhớ, khởi tạo các trường (thực thi hàm khởi tạo) và ghi địa chỉ bộ nhớ vào biến tham chiếu. Không có `volatile`, trình biên dịch hoặc CPU có thể tái sắp xếp việc ghi địa chỉ trước khi hàm khởi tạo chạy. Nếu điều này xảy ra, một luồng đồng thời thực hiện lần kiểm tra đầu tiên không được đồng bộ hóa sẽ thấy một tham chiếu non-null và trả về nó, dẫn đến việc để lộ một đối tượng chỉ mới được khởi tạo một phần.

### Mô hình tư duy (Mental Model)
```
             Luồng A                             Luồng B
      1. Kiểm tra 1: null               1. Kiểm tra 1: non-null!
      2. Lấy Khóa                       2. Trả về đối tượng chưa hoàn thiện (SẬP)
      3. [Thực thi bị tái sắp xếp]
         - Phân bổ bộ nhớ
         - Ghi địa chỉ vào 'instance'
         - (Chưa chạy hàm khởi tạo!)
```

### Ví dụ Code (Code Example)
```java
public final class DclSingleton {
    private static volatile DclSingleton instance;

    private DclSingleton() {}

    public static DclSingleton getInstance() {
        if (instance == null) {
            synchronized (DclSingleton.class) {
                if (instance == null) {
                    instance = new DclSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        DclSingleton s1 = DclSingleton.getInstance();
        DclSingleton s2 = DclSingleton.getInstance();
        System.out.println("Same: " + (s1 == s2)); // Output: Same: true
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Bỏ qua từ khóa `volatile`
  → Trình biên dịch/CPU tái sắp xếp việc ghi địa chỉ thực thể trước khi hoàn thành hàm khởi tạo
  → Luồng đồng thời đọc tham chiếu non-null trong lần kiểm tra đầu tiên không đồng bộ
  → Luồng trả về một tham chiếu của đối tượng mới khởi tạo một phần
  → Truy cập các trường đối tượng dẫn đến trạng thái lỗi hoặc NullPointerException.
```


---

## Tại sao Singleton kiểu Bill Pugh đạt được khởi tạo lười biếng an toàn đa luồng (Why Bill Pugh Singleton Achieves Thread-Safe Lazy Initialization)

Mẫu Singleton kiểu Bill Pugh (Bill Pugh Singleton) dựa trên cơ chế nạp lớp (class loading) của máy ảo Java (JVM) để đạt được quá trình khởi tạo lười biếng an toàn đa luồng. Theo Quy chuẩn Ngôn ngữ Java (Java Language Specification - JLS), một lớp chỉ được nạp và khởi tạo khi nó lần đầu tiên được tham chiếu bởi luồng thực thi. Khi lớp singleton bên ngoài được nạp vào bộ nhớ, lớp tĩnh nội bộ `SingletonHolder` của nó vẫn chưa được nạp. Chỉ khi phương thức `getInstance()` của lớp bên ngoài được gọi rõ ràng, tham chiếu tới `SingletonHolder.INSTANCE`, JVM mới kích hoạt việc nạp và khởi tạo lớp nội bộ này.

Điều quan trọng là phân hệ nạp lớp (class loading subsystem) của JVM vốn dĩ tuần tự hóa việc khởi tạo lớp. JVM sử dụng các khóa nội bộ trong quá trình nạp và xác thực lớp để đảm bảo rằng chỉ có một luồng có thể nạp một lớp tại một thời điểm. Điều này đảm bảo rằng `SingletonHolder` và biến static final `INSTANCE` của nó được tạo theo cách an toàn đa luồng mà không cần lập trình viên phải thực hiện đồng bộ hóa. Vì lớp được nạp chính xác một lần, tham chiếu được công bố an toàn, bỏ qua hoàn toàn chi phí khóa lúc chạy ở các cuộc gọi tiếp theo.

### Mô hình tư duy (Mental Model)
```
      Nạp lớp ngoài -> Chưa nạp lớp nội bộ (Lười biếng)
      getInstance() được gọi -> Kích hoạt nạp lớp nội bộ
      Bộ nạp lớp JVM khóa việc nạp lớp -> Thực thi an toàn đa luồng
      Khởi tạo thực thể -> Static final được công bố an toàn
```

### Ví dụ Code (Code Example)
```java
public final class BillPughSingleton {
    private BillPughSingleton() {}

    private static class SingletonHolder {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void main(String[] args) {
        BillPughSingleton s1 = BillPughSingleton.getInstance();
        BillPughSingleton s2 = BillPughSingleton.getInstance();
        System.out.println("Same: " + (s1 == s2)); // Output: Same: true
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Gọi phương thức `getInstance()`
  → Phân hệ nạp lớp của JVM kích hoạt nạp `SingletonHolder` theo nhu cầu
  → Bộ nạp lớp của JVM tuần tự hóa quá trình khởi tạo bằng cách sử dụng các khóa nội bộ JVM
  → Biến static final `INSTANCE` được khởi tạo an toàn và nguyên tử
  → Các lần đọc tiếp theo lấy được thực thể đã được xây dựng hoàn chỉnh mà không tốn chi phí đồng bộ hóa.
```


---

## Tại sao Factory Method chuyển giao quyết định khởi tạo đối tượng (Why Factory Method Defers Object Instantiation)

Mẫu Factory Method giải quyết vấn đề liên kết chặt chẽ liên quan đến việc sử dụng trực tiếp toán tử `new` trong mã client. Khi một client khởi tạo một lớp cụ thể bằng cách sử dụng `new`, nó sẽ tự liên kết với triển khai cụ thể đó, vi phạm Nguyên tắc đảo ngược phụ thuộc. JVM phải giải quyết kiểu lớp chính xác đó ở thời điểm biên dịch, khiến cho việc thay đổi động hoặc ghi đè (override) của lớp con là không thể. Bằng cách định nghĩa một phương thức trừu tượng để tạo đối tượng, Factory Method chuyển giao quyết định khởi tạo đối tượng cho các lớp con lúc chạy.

Thiết kế này cho phép tính đa hình ở tầng khởi tạo, đảm bảo client chỉ tương tác duy nhất với các interface sản phẩm trừu tượng. Khi các lớp con ghi đè phương thức nhà máy, JVM phân phát cuộc gọi một cách năng động bằng cách sử dụng gọi phương thức ảo (vtable lookup). Kết quả là, framework cốt lõi vẫn tách rời khỏi các lớp cụ thể, cho phép lập trình viên giới thiệu các loại sản phẩm mới mà không cần sửa đổi mã client hiện có.

### Mô hình tư duy (Mental Model)
```
      Mã Client -----> Creator (Trừu tượng) ---gọi---> createProduct() [vtable]
                            ^                                |
                            | (Kế thừa)                     | (Đa hình)
                            |                                v
                     ConcreteCreator --------------> trả về ConcreteProduct
```

### Ví dụ Code (Code Example)
```java
interface Shape { void draw(); }
class Circle implements Shape { public void draw() { System.out.println("Circle"); } }

abstract class ShapeCreator {
    public void render() {
        Shape shape = createShape();
        shape.draw();
    }
    protected abstract Shape createShape(); // Factory Method
}

class CircleCreator extends ShapeCreator {
    protected Shape createShape() { return new Circle(); }
}

public class Main {
    public static void main(String[] args) {
        ShapeCreator creator = new CircleCreator();
        creator.render(); // Output: Circle
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Khởi tạo trực tiếp bằng toán tử `new`
  → Phụ thuộc cứng vào lớp cụ thể ở thời điểm biên dịch
  → Chuyển sang interface Factory Method trừu tượng
  → Phân phát phương thức động giải quyết các lớp con cụ thể tại thời điểm chạy
  → Mã client vẫn tách rời và mở cho việc mở rộng mà không cần sửa đổi.
```


---

## Tại sao mẫu Builder thay thế hàm khởi tạo phình to (Why the Builder Pattern Replaces Telescoping Constructors)

Anti-pattern hàm khởi tạo phình to (telescoping constructor anti-pattern) phát sinh khi một lớp chứa quá nhiều trường tùy chọn, dẫn đến sự gia tăng theo cấp số nhân của các hàm khởi tạo nạp chồng (overloaded constructors). Trong Java, điều này dẫn đến các chuỗi khởi tạo cồng kềnh, nơi mỗi phương thức gọi `this(...)` với các tham số mặc định, làm cho API không thể đọc được và dễ xảy ra lỗi. Trình biên dịch không thể phát hiện khi một lập trình viên vô tình hoán đổi vị trí của hai đối số liền kề có cùng kiểu (chẳng hạn như hai chuỗi string hoặc hai số nguyên). Mẫu Builder giải quyết điều này bằng cách bao đóng logic xây dựng vào một lớp helper chuyên dụng với một API trôi chảy.

Bằng cách sử dụng mẫu Builder, các thuộc tính tùy chọn được thiết lập từng bước thông qua các cuộc gọi phương thức được đặt tên. Điều này đảm bảo rằng các tham số được xác định rõ ràng trong mã client, nâng cao khả năng đọc tổng thể và ngăn ngừa lỗi thứ tự tham số. Ngoài ra, nó thực thi tính bất biến của đối tượng bằng cách cho phép các trường trong lớp đích được khai báo là `final`, chỉ được khởi tạo một lần thông qua một hàm khởi tạo private. Phương thức `build()` đóng vai trò là một cổng xác thực tập trung, ném ra ngoại lệ nếu các tham số kết hợp vi phạm bất biến của lớp trước khi đối tượng cuối cùng được khởi tạo.

### Mô hình tư duy (Mental Model)
```
  Phình to (Telescoping): Client -> Client -> Client (Khó đọc, dễ nhầm thứ tự)
  Builder:    Builder -> setFieldA() -> setFieldB() -> build() -> ImmutableObject
```

### Ví dụ Code (Code Example)
```java
public final class Laptop {
    private final String cpu;
    private final int ram;

    private Laptop(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
    }

    public static class Builder {
        private String cpu;
        private int ram;

        public Builder cpu(String cpu) { this.cpu = cpu; return this; }
        public Builder ram(int ram) { this.ram = ram; return this; }
        public Laptop build() {
            if (cpu == null) throw new IllegalStateException("CPU is required");
            return new Laptop(this);
        }
    }

    public static void main(String[] args) {
        Laptop laptop = new Laptop.Builder().cpu("M3").ram(16).build();
        System.out.println("Laptop: CPU=" + laptop.cpu + ", RAM=" + laptop.ram);
        // Output: Laptop: CPU=M3, RAM=16
    }
}
```

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Nhiều tham số khởi tạo tùy chọn
  → Các hàm khởi tạo nạp chồng với các vị trí tham số gây nhầm lẫn
  → Lớp Builder được xây dựng để nhận các tham số từng bước
  → Thực thi kiểm thực tham số trong phương thức `build()`
  → Lớp đích bất biến được khởi tạo an toàn với các tham số hợp lệ.
```

