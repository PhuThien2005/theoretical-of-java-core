# Exercise: Notification Service Observer

## Objective
Implement a notification system combining the **Observer** and **Strategy** design patterns.

## Problem Description
1. **Observer Pattern**: Allows a `NotificationService` (subject) to register, unregister, and notify multiple `NotificationListener` (observers) of incoming notifications.
2. **Strategy Pattern**: Decouples the message formatting from the notification listeners. The service formats messages using a `FormattingStrategy` before passing them to the listeners.

## Requirements
1. **Strategy**:
   - `FormattingStrategy` interface with `String format(String message, String sender)`.
   - `PlainTextStrategy`: returns `"From " + sender + ": " + message`.
   - `MarkdownStrategy`: returns `"**From " + sender + "**: *" + message + "*"`.
   - `HtmlStrategy`: returns `"<b>From " + sender + "</b>: <i>" + message + "</i>"`.

2. **Observer/Listener**:
   - `NotificationListener` interface with `void onNotification(String formattedMessage)`.
   - Implement `ConsoleListener` or a testing list-backed listener.

3. **Subject**:
   - `NotificationService` handles:
     - `void register(NotificationListener listener)`
     - `void unregister(NotificationListener listener)`
     - `void setStrategy(FormattingStrategy strategy)`
     - `void sendNotification(String message, String sender)` which formats the message using the active strategy and notifies all registered listeners.
