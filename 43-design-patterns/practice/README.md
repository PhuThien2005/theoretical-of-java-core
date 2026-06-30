# Practice Exercises: Design Patterns

This folder contains hands-on practice exercises to reinforce your understanding of Java design patterns, specifically the Observer, Strategy, and Dynamic Proxy patterns.

## Exercises

### 1. Notification Service Observer (`notification-service-observer`)
Implement a notification system combining the **Observer** and **Strategy** design patterns:
- **Observer Pattern**: Define a subject `NotificationService` that maintains a list of observers (`NotificationListener`) and notifies them when new notifications arrive.
- **Strategy Pattern**: Define a `FormattingStrategy` interface and implement concrete strategies (`PlainTextStrategy`, `MarkdownStrategy`, `HtmlStrategy`) to format the notification message.

#### Directory Structure
- [NotificationServiceObserver.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/43-design-patterns/practice/notification-service-observer/src/NotificationServiceObserver.java)
- [NotificationServiceObserverTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/43-design-patterns/practice/notification-service-observer/test/NotificationServiceObserverTest.java)
- [NotificationServiceObserver.java (Solution)](file:///home/fhu_thjen/projects/learning-java/43-design-patterns/practice/notification-service-observer/solution/NotificationServiceObserver.java)

---

### 2. Dynamic Proxy Logger (`dynamic-proxy-logger`)
Implement a dynamic logging proxy that wraps any service interface and logs execution details:
- **Dynamic Proxy**: Use Java's `java.lang.reflect.Proxy` and `java.lang.reflect.InvocationHandler` to intercept method calls.
- **Interception**: Log the method name, arguments, execution duration, and return values (or exceptions).

#### Directory Structure
- [DynamicProxyLogger.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/43-design-patterns/practice/dynamic-proxy-logger/src/DynamicProxyLogger.java)
- [DynamicProxyLoggerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/43-design-patterns/practice/dynamic-proxy-logger/test/DynamicProxyLoggerTest.java)
- [DynamicProxyLogger.java (Solution)](file:///home/fhu_thjen/projects/learning-java/43-design-patterns/practice/dynamic-proxy-logger/solution/DynamicProxyLogger.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository to test your implementations:

```bash
python3 scripts/verify_exercise.py 43-design-patterns
```
