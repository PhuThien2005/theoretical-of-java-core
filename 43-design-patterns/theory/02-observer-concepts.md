# Basic Design Patterns Commonly Seen in Java - Part 2

## Learning Goal

This file covers GoF behavioral patterns and architectural patterns (MVC, DAO, DTO, Repository, Service Layer) standard in Java enterprise code. Study each concept as a practical Java rule.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Observer` | Subscription model allowing multiple listener objects to react to subject state changes. |
| `Template Method` | Defining the skeleton of an algorithm in an abstract method, leaving implementation steps to subclasses. |
| `Command` | Encapsulating requests as objects, supporting operation logging, queuing, and undoing. |
| `Iterator` | Sequential traversal of a collection hiding its internal structure. |
| `State` | Allowing an object to alter its behavior when its internal state changes (states act as classes). |
| `MVC` | Architectural separation pattern of Model (data), View (UI), and Controller (logic). |
| `DAO` | Data Access Object abstraction separating low-level DB queries from business logic. |
| `DTO` | Data Transfer Object carrying data across process/network barriers (contains no business logic). |
| `Repository` | Domain-driven pattern mimicking an in-memory collection mapping to database persistence. |
| `Service Layer` | Encapsulation boundary for core business transactions. |

---

## Detailed Notes

### Observer

Defines a one-to-many dependency where when one object (Subject) changes state, all its dependents (Observers) are notified and updated automatically.

- **Runnable Example**:
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

Defines the skeleton of an algorithm in a method, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.

- **Runnable Example**:
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

Encapsulates a request as an object, thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.

- **Runnable Example**:
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

Provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

- **Runnable Example**:
  ```java
  List<String> list = List.of("a", "b", "c");
  Iterator<String> iterator = list.iterator();
  
  while (iterator.hasNext()) {
      String element = iterator.next();
      System.out.println(element);
  }
  ```

- **Common Mistake**: Modifying a collection structurally (e.g. `list.remove()`) while iterating using an external iterator, causing a `ConcurrentModificationException`. Always use `iterator.remove()` if mutating is required.

---

### State

Allows an object to alter its behavior when its internal state changes. The object will appear to change its class.

- **Runnable Example**:
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

An architectural pattern separating concerns into three modules:
- **Model**: Represents database schemas, entities, and data logic.
- **View**: Renders UI components for user display.
- **Controller**: Listens to user inputs, updates the Model, and refreshes the View.

---

### DAO (Data Access Object)

The DAO pattern isolates the application/business layer from the persistence layer (usually database operations) using an abstract interface.

- **Runnable Example**:
  ```java
  public interface UserDao {
      User findById(long id);
      void save(User user);
  }
  ```

---

### DTO (Data Transfer Object)

A DTO is an object that carries data between processes (e.g., across REST APIs, microservices, or DB entities to presentation layers) to reduce the number of method/network calls. DTOs are simple containers; they contain no business logic.

- **Runnable Example**:
  ```java
  // In modern Java, records are perfect DTO carriers
  public record UserDto(String username, String email) implements Serializable {}
  ```

---

### Repository

A Repository is a Domain-Driven Design (DDD) pattern that mediates between the domain and data mapping layers, acting like an in-memory collection of domain objects.

- **Difference from DAO**: While a DAO maps closely to single database tables, a Repository maps to a higher level Domain Aggregate, coordinating queries across multiple DAOs/tables and managing transactional state.

---

### Service Layer

The Service Layer encapsulates the core business rules and transactions of an application. It sits between the presentation layer (Controllers) and the persistence layer (Repositories/DAOs), orchestrating domain model logic.

---

## Why the Observer Pattern Decouples Subjects from Observers

The Observer pattern defines a one-to-many dependency that decouples the subject (the source of events) from its observers (the consumers of events) using Java interfaces. In a tightly coupled system, a subject would maintain direct references to concrete observer classes and call their specific methods. This forces the subject to change whenever a new observer type is introduced, violating the Open-Closed Principle. By programming to an interface, the subject only interacts with a generic signature, such as `update()`.

At runtime, the subject stores observers in a collection (e.g., `ArrayList<Observer>`). When a state change occurs, the subject iterates through this collection and invokes `update()` on each element. The JVM's execution engine dynamically dispatches each call to the appropriate concrete observer class using virtual method tables (vtables). Consequently, the subject has no compile-time dependency on concrete observers, allowing developers to add, remove, or swap listeners at runtime without modifying the subject's implementation.

### Mental Model
```
     Subject (maintains List<Observer>) 
       |
       +---notify()---> [ Loop: Observer.update() ]
                             |
         +-------------------+-------------------+
         |                                       |
         v                                       v
   ConcreteObserverA (update)             ConcreteObserverB (update)
   (Draws graph)                          (Sends email)
```

### Code Example
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

### Cause-Effect Chain
Subject directly references concrete observer classes &rarr; Tight coupling and compile-time dependency on every observer type &rarr; Define abstract Observer interface &rarr; Subject maintains references using the Observer interface type &rarr; JVM dynamic method dispatch resolves concrete subclasses at runtime &rarr; New observers added dynamically without altering Subject code.
