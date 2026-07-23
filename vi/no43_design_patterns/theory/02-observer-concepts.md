# Các Mẫu Thiết Kế Cơ Bản Thường Gặp Trong Java - Phần 2

## Mục Tiêu Học Tập

File này đề cập đến các mẫu thiết kế hành vi (behavioral pattern) thuộc nhóm GoF và các mẫu kiến trúc (architectural pattern - MVC, DAO, DTO, Repository, Service Layer) tiêu chuẩn trong mã nguồn doanh nghiệp Java. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Đề Cương Khái Niệm

- **`Observer`** — Observer: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Template Method`** — Template Method: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Command`** — Command: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Iterator`** — Iterator: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`State`** — State: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`MVC`** — MVC: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`DAO`** — DAO: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`DTO`** — DTO: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Repository`** — Repository: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.
- **`Service Layer`** — Service Layer: Cung cấp các quy tắc và cơ chế hoạt động cụ thể trong Java.

---

## Ghi Chú Chi Tiết

### Observer

Định nghĩa một mối quan hệ phụ thuộc một-nhiều trong đó khi một đối tượng (Subject - Đối tượng chính) thay đổi trạng thái, tất cả các đối tượng phụ thuộc của nó (Observer) sẽ được thông báo và cập nhật tự động.

- **Ví dụ chạy được**:
  ```java
  public interface Observer { void update(String event); }
  
  public class NewsChannel implements Observer {
      public void update(String event) { System.out.println("Breaking: " + event); }
  }

  public class NewsAgency {
      private final List<Observer> observers = new ArrayList<>();

      public void addObserver(Observer channel) { observers.add(channel); }
      public void setNews(String news) {
          for (Observer observer : observers) {
              observer.update(news); // Notify all listeners
          }
      }
  }
  ```

---

### Template Method

Định nghĩa khung của một thuật toán trong một phương thức, trì hoãn một số bước cho các lớp con. Template Method cho phép các lớp con định nghĩa lại một số bước nhất định của một thuật toán mà không làm thay đổi cấu trúc của thuật toán.

- **Ví dụ chạy được**:
  ```java
  public abstract class NetworkDataImporter {
      // Template Method defining the execution flow
      public final void importData() {
          readData();
          parseData();
          saveToDatabase();
      }
      
      protected abstract void readData();
      protected abstract void parseData();
      
      private void saveToDatabase() { /* common code */ }
  }
  ```

---

### Command

Đóng gói một yêu cầu thành một đối tượng, từ đó cho phép bạn tham số hóa các client với các yêu cầu khác nhau, xếp hàng hoặc ghi log các yêu cầu, và hỗ trợ các hoạt động có thể hoàn tác.

- **Ví dụ chạy được**:
  ```java
  public interface Command { void execute(); }

  public class LightOnCommand implements Command {
      private final Light light;
      public LightOnCommand(Light light) { this.light = light; }
      public void execute() { light.turnOn(); }
  }

  public class RemoteControl {
      private Command command;
      public void setCommand(Command command) { this.command = command; }
      public void pressButton() { command.execute(); }
  }
  ```

---

### Iterator

Cung cấp một cách thức để truy cập tuần tự các phần tử của một đối tượng tập hợp mà không để lộ cấu trúc bên dưới của nó.

- **Ví dụ chạy được**:
  ```java
  List<String> list = List.of("a", "b", "c");
  Iterator<String> iterator = list.iterator();
  
  while (iterator.hasNext()) {
      String element = iterator.next();
      System.out.println(element);
  }
  ```

- **Sai lầm phổ biến**: Thay đổi cấu trúc của một collection (ví dụ: `list.remove()`) trong khi đang duyệt bằng một iterator ngoài, gây ra lỗi `ConcurrentModificationException`. Luôn luôn sử dụng `iterator.remove()` nếu cần sửa đổi.

---

### State

Cho phép một đối tượng thay đổi hành vi của nó khi trạng thái nội bộ của nó thay đổi. Đối tượng sẽ có vẻ như thay đổi lớp của nó.

- **Ví dụ chạy được**:
  ```java
  public interface State { void handleRequest(); }

  public class PlayState implements State {
      public void handleRequest() { System.out.println("Playing video..."); }
  }

  public class VideoPlayer {
      private State currentState = new PlayState();

      public void setState(State state) { this.currentState = state; }
      public void pressPlay() { currentState.handleRequest(); }
  }
  ```

---

### MVC (Model-View-Controller)

Một mẫu kiến trúc phân tách các mối quan tâm thành ba module:
- **Model**: Đại diện cho các lược đồ cơ sở dữ liệu, các thực thể và logic dữ liệu.
- **View**: Kết xuất các thành phần UI để hiển thị cho người dùng.
- **Controller**: Lắng nghe các đầu vào của người dùng, cập nhật Model và làm mới View.

---

### DAO (Data Access Object)

Mẫu thiết kế DAO tách biệt tầng ứng dụng/nghiệp vụ khỏi tầng lưu trữ (thường là các hoạt động cơ sở dữ liệu) bằng cách sử dụng một interface trừu tượng.

- **Ví dụ chạy được**:
  ```java
  public interface UserDao {
      User findById(long id);
      void save(User user);
  }
  ```

---

### DTO (Data Transfer Object)

Một DTO là một đối tượng vận chuyển dữ liệu giữa các tiến trình (ví dụ: qua các REST API, microservice, hoặc từ các thực thể DB tới các tầng hiển thị) nhằm giảm số lượng cuộc gọi phương thức hoặc cuộc gọi mạng. Các DTO là những vật chứa đơn giản; chúng không chứa bất kỳ logic nghiệp vụ nào.

- **Ví dụ chạy được**:
  ```java
  // In modern Java, records are perfect DTO carriers
  public record UserDto(String username, String email) implements Serializable {}
  ```

---

### Repository

Một Repository là một mẫu thiết kế hướng miền (Domain-Driven Design - DDD) làm trung gian giữa tầng miền và tầng ánh xạ dữ liệu, hoạt động giống như một collection của các đối tượng miền trên bộ nhớ.

- **Điểm khác biệt so với DAO**: Trong khi một DAO ánh xạ chặt chẽ tới các bảng cơ sở dữ liệu riêng lẻ, một Repository ánh xạ tới một Domain Aggregate (Tập hợp Miền) cấp cao hơn, điều phối các truy vấn trên nhiều DAO/bảng và quản lý trạng thái giao dịch.

---

### Tầng Dịch Vụ (Service Layer)

Tầng Dịch vụ (Service Layer) đóng gói các quy tắc nghiệp vụ cốt lõi và các giao dịch của một ứng dụng. Nó nằm giữa tầng hiển thị (các Controller) và tầng lưu trữ dữ liệu (các Repository/DAO), điều phối logic mô hình miền.

---

## Tại Sao Mẫu Observer Giúp Giảm Phụ Thuộc Giữa Đối Tượng Chính Và Các Observer

Mẫu thiết kế Observer định nghĩa một mối quan hệ phụ thuộc một-nhiều giúp giảm phụ thuộc (decouple) đối tượng chính (subject - nguồn sự kiện) khỏi các observer (người tiêu thụ sự kiện) bằng cách sử dụng các interface Java. Trong một hệ thống liên kết chặt chẽ, một subject sẽ giữ các tham chiếu trực tiếp đến các lớp observer cụ thể và gọi các phương thức cụ thể của chúng. Điều này buộc subject phải thay đổi mỗi khi một loại observer mới được đưa vào, vi phạm Nguyên tắc Đóng/Mở. Bằng cách lập trình hướng tới interface, subject chỉ tương tác duy nhất với một chữ ký generic, chẳng hạn như `update()`.

Tại thời điểm chạy, subject lưu trữ các observer trong một collection (ví dụ: `ArrayList<Observer>`). Khi có sự thay đổi trạng thái xảy ra, subject lặp qua collection này và gọi `update()` trên từng phần tử. Trình thực thi của JVM điều phối một cách động từng cuộc gọi tới lớp observer cụ thể tương ứng bằng cách sử dụng bảng phương thức ảo (vtable). Do đó, subject không có bất kỳ sự phụ thuộc nào tại thời điểm biên dịch vào các observer cụ thể, cho phép các nhà phát triển thêm, xóa hoặc hoán đổi các bộ lắng nghe tại thời điểm chạy mà không cần sửa đổi triển khai của subject.

### Sơ đồ điều phối sự kiện (Mental Model)
```text
     Subject (duy trì List<Observer>) 
       |
       +---notify()---> [ Vòng lặp: Observer.update() ]
                             |
         +-------------------+-------------------+
         |                                       |
         v                                       v
   ConcreteObserverA (update)             ConcreteObserverB (update)
   (Vẽ đồ thị)                            (Gửi email)
```

### Ví Dụ Mã Nguồn
```java
import java.util.ArrayList;
import java.util.List;

interface Observer { void update(String msg); }

class ConsoleObserver implements Observer {
    public void update(String msg) { System.out.println("Console: " + msg); }
}

class Subject {
    private final List<Observer> observers = new ArrayList<>();
    public void attach(Observer o) { observers.add(o); }
    public void notifyAll(String msg) {
        for (Observer o : observers) { o.update(msg); }
    }
}

public class Main {
    public static void main(String[] args) {
        Subject subject = new Subject();
        subject.attach(new ConsoleObserver());
        subject.notifyAll("Event Triggered"); // Output: Console: Event Triggered
    }
}
```

### Chuỗi Nguyên Nhân - Kết Quả
Subject trực tiếp tham chiếu tới các lớp observer cụ thể &rarr; Liên kết chặt chẽ và phụ thuộc tại thời điểm biên dịch vào mọi loại observer &rarr; Định nghĩa interface Observer trừu tượng &rarr; Subject duy trì các tham chiếu bằng kiểu interface Observer &rarr; Điều phối phương thức động của JVM phân giải các lớp con cụ thể tại thời điểm chạy &rarr; Các observer mới được thêm vào một cách động mà không cần thay đổi mã nguồn Subject.

## Liên Kết Tham Khảo (Reference Links)

- https://refactoring.guru/design-patterns (Mẫu thiết kế Refactoring Guru)
- https://docs.oracle.com/javase/tutorial/java/concepts/ (Tài liệu khái niệm Java của Oracle)
