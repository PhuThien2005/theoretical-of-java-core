# Các mẫu thiết kế cơ bản thường thấy trong Java - Phần 2 (Basic Design Patterns Commonly Seen in Java - Part 2)

## Mục tiêu học tập (Learning Goal)

Tài liệu này bao gồm các mẫu thiết kế hành vi (behavioral patterns) GoF và các mẫu thiết kế kiến trúc (MVC, DAO, DTO, Repository, Service Layer) tiêu chuẩn trong mã nguồn Java doanh nghiệp. Hãy nghiên cứu từng khái niệm như một quy tắc Java thực tế.

## Phạm vi đề cương (Outline Coverage)

| Khái niệm (Concept) | Những điều cần biết (What to know) |
| --- | --- |
| `Observer` | Mô hình đăng ký cho phép nhiều đối tượng lắng nghe (listeners) phản ứng với những thay đổi trạng thái của chủ thể (subject). |
| `Template Method` | Định nghĩa bộ khung của một thuật toán trong một phương thức trừu tượng (Abstraction), nhường các bước triển khai cho các lớp con. |
| `Command` | Bao đóng các yêu cầu dưới dạng các đối tượng, hỗ trợ ghi nhật ký hoạt động, lập hàng đợi và hoàn tác (undo). |
| `Iterator` | Duyệt tuần tự qua một bộ sưu tập (collection) mà không làm lộ cấu trúc bên trong của nó. |
| `State` | Cho phép một đối tượng thay đổi hành vi của nó khi trạng thái nội bộ của nó thay đổi (các trạng thái hoạt động như các lớp). |
| `MVC` | Mẫu kiến trúc tách biệt các mối quan tâm thành Model (dữ liệu), View (giao diện người dùng) và Controller (logic xử lý). |
| `DAO` | Trừu tượng hóa đối tượng truy cập dữ liệu (Data Access Object) tách biệt các truy vấn DB cấp thấp khỏi logic nghiệp vụ. |
| `DTO` | Đối tượng chuyển dữ liệu (Data Transfer Object) mang dữ liệu qua các ranh giới tiến trình/mạng (không chứa logic nghiệp vụ). |
| `Repository` | Mẫu thiết kế hướng miền (domain-driven) mô phỏng một bộ sưu tập trong bộ nhớ ánh xạ tới việc lưu trữ cơ sở dữ liệu. |
| `Service Layer` | Ranh giới bao đóng cho các giao dịch nghiệp vụ cốt lõi (core business transactions). |

---

## Ghi chú chi tiết (Detailed Notes)

### Observer

Định nghĩa một mối quan hệ phụ thuộc một-nhiều sao cho khi một đối tượng (Chủ thể - Subject) thay đổi trạng thái, tất cả các đối tượng phụ thuộc của nó (Observers) sẽ được thông báo và cập nhật tự động.

- **Ví dụ có thể chạy được**:
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

Định nghĩa bộ khung của một thuật toán trong một phương thức, chuyển giao một số bước cho các lớp con. Template Method cho phép các lớp con định nghĩa lại các bước nhất định của thuật toán mà không làm thay đổi cấu trúc của thuật toán đó.

- **Ví dụ có thể chạy được**:
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

Bao đóng một yêu cầu dưới dạng một đối tượng, từ đó cho phép bạn tham số hóa các client với các yêu cầu khác nhau, lập hàng đợi hoặc ghi nhật ký các yêu cầu, và hỗ trợ các thao tác có thể hoàn tác.

- **Ví dụ có thể chạy được**:
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

Cung cấp một cách để truy cập tuần tự các phần tử của một đối tượng tổng hợp (aggregate object) mà không làm lộ cấu trúc bên dưới của nó.

- **Ví dụ có thể chạy được**:
  ```java
  List<String> list = List.of("a", "b", "c");
  Iterator<String> iterator = list.iterator();
  
  while (iterator.hasNext()) {
      String element = iterator.next();
      System.out.println(element);
  }
  ```

- **Sai lầm thường gặp**: Sửa đổi cấu trúc của một bộ sưu tập (ví dụ: `list.remove()`) khi đang duyệt qua nó bằng một iterator bên ngoài, gây ra ngoại lệ `ConcurrentModificationException`. Luôn luôn sử dụng `iterator.remove()` nếu cần sửa đổi cấu trúc trong quá trình duyệt.

---

### State

Cho phép một đối tượng thay đổi hành vi của nó khi trạng thái nội bộ của nó thay đổi. Đối tượng sẽ trông giống như thay đổi lớp của nó.

- **Ví dụ có thể chạy được**:
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

Một mẫu thiết kế kiến trúc phân tách các mối quan tâm thành ba module:
- **Model**: Đại diện cho các schema cơ sở dữ liệu, các thực thể và logic dữ liệu.
- **View**: Hiển thị các thành phần giao diện người dùng (UI) cho người dùng xem.
- **Controller**: Lắng nghe các dữ liệu đầu vào của người dùng, cập nhật Model và làm mới View.

---

### DAO (Data Access Object)

Mẫu DAO cô lập tầng ứng dụng/nghiệp vụ khỏi tầng lưu trữ (thường là các thao tác cơ sở dữ liệu) bằng cách sử dụng một interface trừu tượng.

- **Ví dụ có thể chạy được**:
  ```java
  public interface UserDao {
      User findById(long id);
      void save(User user);
  }
  ```

---

### DTO (Data Transfer Object)

DTO là một đối tượng mang dữ liệu giữa các tiến trình (ví dụ: qua các REST API, microservices, hoặc từ các thực thể DB tới tầng hiển thị) để giảm số lượng cuộc gọi phương thức hoặc cuộc gọi mạng. DTO là các container đơn giản; chúng không chứa logic nghiệp vụ.

- **Ví dụ có thể chạy được**:
  ```java
  // In modern Java, records are perfect DTO carriers
  public record UserDto(String username, String email) implements Serializable {}
  ```

---

### Repository (Repository)

Repository là một mẫu Thiết kế hướng miền (Domain-Driven Design - DDD) làm trung gian giữa miền và các tầng ánh xạ dữ liệu, hoạt động giống như một bộ sưu tập trong bộ nhớ của các đối tượng miền.

- **Sự khác biệt so với DAO**: Trong khi DAO ánh xạ chặt chẽ với các bảng/hoạt động cơ sở dữ liệu đơn lẻ, Repository ánh xạ tới một Tổng hợp miền (Domain Aggregate) cấp cao hơn, điều phối các truy vấn trên nhiều DAO/bảng và quản lý trạng thái giao dịch (transactional state).

---

### Service Layer (Service Layer)

Tầng dịch vụ (Service Layer) bao đóng các quy tắc nghiệp vụ cốt lõi và các giao dịch của một ứng dụng. Nó nằm giữa tầng hiển thị (Controllers) và tầng lưu trữ (Repositories/DAOs), điều phối logic của mô hình miền.

---

## Tại sao mẫu Observer giúp tách rời chủ thể khỏi các Observer (Why the Observer Pattern Decouples Subjects from Observers)

Mẫu Observer định nghĩa một mối quan hệ phụ thuộc một-nhiều giúp tách rời chủ thể (nguồn phát sự kiện) khỏi các observer của nó (nguồn tiêu thụ sự kiện) bằng cách sử dụng các interface Java.

Trong một hệ thống liên kết chặt chẽ (tightly coupled), chủ thể sẽ duy trì các tham chiếu trực tiếp đến các lớp observer cụ thể và gọi các phương thức cụ thể của chúng. Điều này buộc chủ thể phải thay đổi bất cứ khi nào một kiểu observer mới được giới thiệu, vi phạm Nguyên tắc Đóng/Mở. Bằng cách lập trình hướng interface, chủ thể chỉ tương tác với một chữ ký chung, chẳng hạn như `update()`.

Tại thời điểm chạy, chủ thể lưu trữ các observer trong một bộ sưu tập (ví dụ: `ArrayList<Observer>`). Khi có sự thay đổi trạng thái xảy ra, chủ thể lặp qua bộ sưu tập này và gọi `update()` trên mỗi phần tử. Động cơ thực thi của JVM sẽ phân phát động mỗi cuộc gọi đến đúng lớp observer cụ thể bằng cách sử dụng các bảng phương thức ảo (vtable). Do đó, chủ thể không có phụ thuộc ở thời điểm biên dịch nào vào các observer cụ thể, cho phép lập trình viên thêm, bớt hoặc hoán đổi các trình lắng nghe (listeners) tại thời điểm chạy mà không cần sửa đổi triển khai của chủ thể.

### Mô hình tư duy (Mental Model)
```
      Chủ thể (Subject) (duy trì List<Observer>) 
        |
        +---notify()---> [ Vòng lặp: Observer.update() ]
                              |
          +-------------------+-------------------+
          |                                       |
          v                                       v
    ConcreteObserverA (update)             ConcreteObserverB (update)
    (Vẽ sơ đồ)                             (Gửi email)
```

### Ví dụ Code (Code Example)
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

### Chuỗi nguyên nhân - kết quả (Cause-Effect Chain)

```text
Chủ thể tham chiếu trực tiếp các lớp observer cụ thể
  → Liên kết chặt chẽ và phụ thuộc ở thời điểm biên dịch vào mọi kiểu observer
  → Định nghĩa interface Observer trừu tượng
  → Chủ thể duy trì các tham chiếu bằng cách sử dụng kiểu giao diện Observer
  → Phân phát phương thức động của JVM giải quyết các lớp con cụ thể tại thời điểm chạy
  → Các observer mới được thêm vào một cách năng động mà không làm thay đổi mã nguồn của Chủ thể.
```

