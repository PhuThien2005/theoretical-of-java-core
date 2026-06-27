# Các Mẫu Thiết Kế Cơ Bản Thường Gặp Trong Java - Phần 1

## Mục Tiêu Học Tập

File này đề cập đến một phần trọng tâm của các **Mẫu thiết kế (Design Pattern)** khởi tạo (creational) và cấu trúc (structural) thuộc nhóm GoF được sử dụng rộng rãi trong Java. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Đề Cương Khái Niệm

| Khái niệm | Những điều cần biết |
| --- | --- |
| `Singleton` | Giới hạn việc khởi tạo lớp thành một đối tượng duy nhất với điểm truy cập toàn cục. |
| `Factory Method` | Ủy quyền khởi tạo đối tượng cho các lớp con bằng cách sử dụng một chữ ký phương thức nhà máy (factory method). |
| `Abstract Factory` | Interface tạo ra các họ đối tượng có liên quan với nhau mà không cần chỉ định các lớp cụ thể của chúng. |
| `Builder` | Xây dựng từng bước các đối tượng phức tạp bằng cách sử dụng một API dạng chuỗi (fluent API). |
| `Prototype` | Tạo các đối tượng mới bằng cách nhân bản một thực thể đã được cấu hình trước. |
| `Adapter` | Thống nhất các interface không tương thích bằng cách bọc một lớp nguồn bên trong một adapter. |
| `Decorator` | Thêm các tính năng vào một đối tượng một cách động bằng cách bọc các biến thực thể. |
| `Facade` | Cung cấp một API đơn giản hóa đóng vai trò là giao diện cho một tầng phân hệ (subsystem) phức tạp. |
| `Proxy` | Cung cấp một đối tượng đại diện (placeholder) để kiểm soát truy cập, ghi log hoặc tải lười (lazy-load) các thực thể đích. |
| `Strategy` | Đóng gói các thuật toán có thể hoán đổi cho nhau và được lựa chọn tại thời điểm chạy. |

---

## Ghi Chú Chi Tiết

### Singleton

Giới hạn một lớp chỉ có duy nhất một thực thể và cung cấp một điểm truy cập toàn cục.

- **Ví dụ chạy được (Khóa Kiểm Tra Hai Lần - Double-Checked Locking)**:
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

- **Sai lầm phổ biến / Chế độ thất bại**:
  - **Thiếu từ khóa Volatile**: Nếu không có `volatile`, trình biên dịch/JVM có thể sắp xếp lại các chỉ thị (cấp phát bộ nhớ -> xuất bản tham chiếu -> chạy hàm khởi tạo). Một luồng khác có thể đọc được một thực thể mới chỉ được khởi tạo một nửa.
  - **Enum Singleton**: Cách an toàn tuyệt đối nhất để triển khai một Singleton là sử dụng một enum có một phần tử duy nhất, nó sẽ tự động xử lý các cuộc tấn công tuần tự hóa và phản xạ một cách tự nhiên:
    ```java
    public enum SafeSingleton {
        INSTANCE;
        public void performAction() {}
    }
    ```

---

### Factory Method

Định nghĩa một interface để tạo một đối tượng nhưng cho phép các lớp con quyết định lớp nào sẽ được khởi tạo.

- **Ví dụ chạy được**:
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

- **Ví dụ chạy được**:
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

Tách biệt việc xây dựng một đối tượng phức tạp khỏi biểu diễn của nó, cho phép lắp ráp từng bước các trường dữ liệu (đặc biệt hữu ích khi lớp chứa nhiều tham số tùy chọn).

- **Ví dụ chạy được**:
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

Tạo các đối tượng mới bằng cách sao chép (nhân bản/clone) một thực thể hiện có (prototype) thay vì tạo mới chúng bằng từ khóa `new` từ đầu.

- **Ví dụ chạy được**:
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

Chuyển đổi interface của một lớp thành một interface khác mà client mong đợi, cho phép các lớp có interface không tương thích hoạt động cùng nhau.

- **Ví dụ chạy được**:
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

Đính kèm thêm các trách nhiệm vào một đối tượng một cách động. Các decorator cung cấp một sự thay thế linh hoạt cho việc phân lớp (subclassing) để mở rộng chức năng.

- **Ví dụ chạy được**:
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

Cung cấp một giao diện thống nhất, đơn giản hóa cho một tập hợp các interface trong một phân hệ (subsystem) phức tạp.

- **Ví dụ**: Tạo một `HomeTheaterFacade` giúp trừu tượng hóa các cuộc gọi tới `Amplifier.on()`, `DvdPlayer.play(movie)`, `Projector.widescreenMode()` thành một phương thức duy nhất: `facade.watchMovie("Inception")`.

---

### Proxy

Cung cấp một đối tượng đại diện (surrogate hoặc placeholder) cho một đối tượng khác để kiểm soát quyền truy cập vào nó (tải lười, kiểm tra ủy quyền, ghi log, lưu bộ nhớ đệm).

- **Ví dụ chạy được**:
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

Định nghĩa một họ các thuật toán, đóng gói từng thuật toán và giúp chúng có thể hoán đổi cho nhau tại thời điểm chạy.

- **Ví dụ chạy được**:
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

## Tại Sao Khóa Kiểm Tra Hai Lần Đảm Bảo Singleton An Toàn Luồng

Mẫu thiết kế khóa kiểm tra hai lần (double-checked locking) được thiết kế để giảm thiểu chi phí đồng bộ hóa trong việc khởi tạo lười (lazy initialization). Theo Mô Hình Bộ Nhớ Java (Java Memory Model), nếu không kiểm tra hai lần, mọi lời gọi tới `getInstance()` sẽ đều yêu cầu phải có một monitor cấp lớp, tạo ra một nút thắt hiệu năng đáng kể. Trong mẫu thiết kế này, lần kiểm tra đầu tiên sẽ bỏ qua hoàn toàn việc đồng bộ hóa một khi thực thể đã được khởi tạo. Nếu thực thể là null, luồng sẽ giành lấy khóa và thực hiện lần kiểm tra thứ hai để xác minh rằng không có luồng nào khác đã khởi tạo singleton trong khi luồng này đang chờ khóa.

Điều quan trọng là tham chiếu thực thể phải được khai báo `volatile` để ngăn chặn việc sắp xếp lại chỉ thị (instruction reordering) bởi trình biên dịch hoặc JVM. Việc tạo ra một đối tượng mới `new Singleton()` không phải là một hoạt động nguyên tử; nó bao gồm cấp phát bộ nhớ, khởi tạo các trường (thực thi hàm khởi tạo) và ghi địa chỉ bộ nhớ vào biến tham chiếu. Nếu không có `volatile`, trình biên dịch hoặc CPU có thể sắp xếp lại việc ghi địa chỉ trước khi hàm khởi tạo chạy. Nếu điều này xảy ra, một luồng đồng thời khác thực thi kiểm tra đầu tiên (không đồng bộ) sẽ nhìn thấy một tham chiếu non-null và trả về nó, dẫn đến việc phơi bày một đối tượng mới chỉ được khởi tạo một phần.

### Phơi bày thực thể khởi tạo một phần (Mental Model)
```text
            Luồng A                            Luồng B
     1. Lần kiểm tra thứ nhất: null     1. Lần kiểm tra thứ nhất: non-null!
     2. Giành lấy Khóa                  2. Trả về đối tượng khởi tạo một phần (CRASH)
     3. [Thực thi bị sắp xếp lại]
        - Cấp phát bộ nhớ
        - Ghi địa chỉ vào 'instance'
        - (Hàm khởi tạo chưa chạy!)
```

### Ví Dụ Mã Nguồn
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

### Chuỗi Nguyên Nhân - Kết Quả
Bỏ qua từ khóa `volatile` &rarr; Trình biên dịch/CPU sắp xếp lại việc ghi địa chỉ thực thể lên trước khi hoàn thành hàm khởi tạo &rarr; Luồng đồng thời đọc thấy tham chiếu non-null ở lần kiểm tra không đồng bộ thứ nhất &rarr; Luồng trả về một tham chiếu tới đối tượng mới được khởi tạo một phần &rarr; Việc truy cập các trường của đối tượng dẫn đến trạng thái bị hỏng hoặc lỗi NullPointerException.

---

## Tại Sao Bill Pugh Singleton Đạt Được Khởi Tạo Lười An Toàn Luồng

Mẫu thiết kế Bill Pugh Singleton dựa trên cơ chế tải lớp (class loading) của Máy ảo Java để đạt được khả năng khởi tạo lười (lazy initialization) an toàn luồng. Theo Đặc tả Ngôn ngữ Java (JLS), một lớp chỉ được tải và khởi tạo khi nó được tham chiếu lần đầu tiên bởi luồng thực thi. Khi lớp singleton bên ngoài được tải vào bộ nhớ, lớp helper lồng nhau `SingletonHolder` vẫn chưa được tải. Chỉ khi phương thức `getInstance()` của lớp bên ngoài được gọi một cách rõ ràng, tham chiếu tới `SingletonHolder.INSTANCE`, JVM mới kích hoạt việc tải và khởi tạo lớp bên trong.

Điều quan trọng là phân hệ tải lớp của JVM vốn dĩ đã tuần tự hóa việc khởi tạo lớp. JVM sử dụng các khóa nội bộ trong quá trình tải và xác thực lớp để đảm bảo rằng chỉ có một luồng có thể tải một lớp tại một thời điểm. Điều này đảm bảo rằng `SingletonHolder` và biến `static final` `INSTANCE` của nó được tạo ra một cách an toàn luồng mà nhà phát triển không cần can thiệp đồng bộ hóa thủ công. Vì lớp được tải chính xác một lần, tham chiếu được xuất bản một cách an toàn, bỏ qua hoàn toàn chi phí khóa thời gian chạy trong các lần gọi tiếp theo.

### Khởi tạo lười qua ClassLoader (Mental Model)
```text
Lớp bên ngoài được tải -> Lớp lồng nhau chưa được tải (Lazy)
       |
getInstance() được gọi -> Kích hoạt tải lớp lồng nhau
       |
ClassLoader của JVM khóa việc tải -> Thực thi an toàn luồng
       |
Thực thể được khởi tạo -> Static final được xuất bản an toàn
```

### Ví Dụ Mã Nguồn
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

### Chuỗi Nguyên Nhân - Kết Quả
Gọi phương thức `getInstance()` &rarr; Phân hệ tải lớp của JVM kích hoạt tải `SingletonHolder` theo nhu cầu &rarr; Classloader của JVM tuần tự hóa việc khởi tạo sử dụng các khóa nội bộ của JVM &rarr; Biến static final `INSTANCE` được khởi tạo an toàn và nguyên tử &rarr; Các lần đọc tiếp theo lấy được thực thể đã xây dựng hoàn chỉnh mà không tốn chi phí đồng bộ hóa.

---

## Tại Sao Factory Method Trì Hoãn Việc Khởi Tạo Đối Tượng

Mẫu thiết kế Factory Method giải quyết vấn đề liên kết chặt chẽ liên quan đến việc sử dụng trực tiếp toán tử `new` trong mã nguồn client. Khi một client khởi tạo một lớp cụ thể bằng cách sử dụng `new`, nó tự liên kết với triển khai cụ thể đó, vi phạm Nguyên tắc Đảo ngược Phụ thuộc. JVM phải phân giải kiểu lớp chính xác đó tại thời điểm biên dịch, khiến cho việc thay đổi động hoặc ghi đè lớp con là bất khả thi. Bằng cách định nghĩa một phương thức abstract cho việc tạo đối tượng, Factory Method ủy quyền quyết định khởi tạo cho các lớp con tại thời điểm chạy.

Thiết kế này cho phép tính đa hình ở cấp độ khởi tạo, đảm bảo client chỉ tương tác duy nhất với các interface sản phẩm (product interface) trừu tượng. Khi các lớp con ghi đè phương thức nhà máy, JVM điều phối cuộc gọi một cách động bằng cách sử dụng lời gọi phương thức ảo (vtable lookup). Nhờ đó, khung lõi (core framework) vẫn được tách biệt khỏi các lớp cụ thể, cho phép các nhà phát triển giới thiệu các loại sản phẩm mới mà không cần sửa đổi mã nguồn client hiện có.

### Trì hoãn qua Đa hình kiểu con (Mental Model)
```text
Mã nguồn Client -----> Creator (Abstract) ---gọi---> createProduct() [vtable]
                            ^                                |
                            | (Kế thừa)                     | (Đa hình)
                            |                                v
                    ConcreteCreator --------------> trả về ConcreteProduct
```

### Ví Dụ Mã Nguồn
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

### Chuỗi Nguyên Nhân - Kết Quả
Khởi tạo trực tiếp bằng toán tử `new` &rarr; Phụ thuộc viết cứng vào lớp cụ thể tại thời điểm biên dịch &rarr; Chuyển sang interface Factory Method trừu tượng &rarr; Điều phối phương thức động phân giải lớp con cụ thể tại thời điểm chạy &rarr; Mã nguồn client vẫn được tách biệt và mở cho việc mở rộng mà không cần sửa đổi.

---

## Tại Sao Mẫu Thiết Kế Builder Thay Thế Hàm Khởi Tạo Hình Kính Viễn Vọng

Phản khuôn mẫu hàm khởi tạo hình kính viễn vọng (telescoping constructor) phát sinh khi một lớp chứa nhiều trường tùy chọn, dẫn đến sự tăng trưởng lũy thừa của các hàm khởi tạo quá tải (overloaded constructor). Trong Java, điều này dẫn đến các chuỗi hàm khởi tạo phình to, nơi mỗi phương thức gọi `this(...)` với các tham số mặc định, khiến cho API khó đọc và dễ xảy ra lỗi. Trình biên dịch không thể phát hiện khi nhà phát triển vô tình tráo đổi hai đối số liền kề có cùng kiểu dữ liệu (chẳng hạn như hai chuỗi hoặc hai số nguyên). Mẫu thiết kế Builder giải quyết vấn đề này bằng cách đóng gói logic xây dựng vào một lớp helper chuyên dụng có API dạng chuỗi (fluent API).

Bằng việc sử dụng mẫu thiết kế Builder, các thuộc tính tùy chọn được thiết lập từng bước thông qua các lời gọi phương thức có tên. Điều này đảm bảo rằng các tham số được xác định rõ ràng trong mã nguồn client, nâng cao khả năng đọc tổng thể và ngăn ngừa các lỗi về thứ tự tham số. Thêm vào đó, nó thực thi tính bất biến (immutability) của đối tượng bằng cách cho phép các trường trong lớp đích được khai báo `final`, chỉ được khởi tạo một lần thông qua một hàm khởi tạo private. Phương thức `build()` đóng vai trò là một cổng xác thực tập trung, ném ra một ngoại lệ nếu các tham số kết hợp vi phạm bất biến lớp trước khi đối tượng cuối cùng được khởi tạo.

### Tránh tráo đổi đối số kiểu (Mental Model)
```text
Hình kính viễn vọng: Client -> Client -> Client (Khó đọc, dễ nhầm thứ tự)
Builder:             Builder -> setFieldA() -> setFieldB() -> build() -> ImmutableObject
```

### Ví Dụ Mã Nguồn
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

### Chuỗi Nguyên Nhân - Kết Quả
Nhiều tham số hàm khởi tạo tùy chọn &rarr; Các hàm khởi tạo quá tải với các vị trí tham số dễ gây nhầm lẫn &rarr; Lớp Builder được xây dựng để nhận các tham số từng bước &rarr; Xác thực các tham số được thực thi trong phương thức `build()` &rarr; Lớp đích bất biến được khởi tạo an toàn với các tham số hợp lệ.

## Liên Kết Tham Khảo (Reference Links)

- https://refactoring.guru/design-patterns (Mẫu thiết kế Refactoring Guru)
- https://docs.oracle.com/javase/tutorial/java/concepts/ (Tài liệu khái niệm Java của Oracle)
