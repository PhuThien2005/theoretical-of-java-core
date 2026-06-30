package notificationserviceobserver;

import java.util.ArrayList;
import java.util.List;

public class NotificationServiceObserver {

    // Strategy Pattern
    
    public interface FormattingStrategy {
        String format(String message, String sender);
    }

    public static class PlainTextStrategy implements FormattingStrategy {
        @Override
        public String format(String message, String sender) {
            // TODO: Return "From " + sender + ": " + message
            return "";
        }
    }

    public static class MarkdownStrategy implements FormattingStrategy {
        @Override
        public String format(String message, String sender) {
            // TODO: Return "**From " + sender + "**: *" + message + "*"
            return "";
        }
    }

    public static class HtmlStrategy implements FormattingStrategy {
        @Override
        public String format(String message, String sender) {
            // TODO: Return "<b>From " + sender + "</b>: <i>" + message + "</i>"
            return "";
        }
    }

    // Observer Pattern
    
    public interface NotificationListener {
        void onNotification(String formattedMessage);
    }

    public static class NotificationService {
        
        // TODO: Declare list of listeners and the active FormattingStrategy.

        public NotificationService(FormattingStrategy initialStrategy) {
            // TODO: Initialize fields.
        }

        public void register(NotificationListener listener) {
            // TODO: Register listener.
        }

        public void unregister(NotificationListener listener) {
            // TODO: Unregister listener.
        }

        public void setStrategy(FormattingStrategy strategy) {
            // TODO: Update the formatting strategy.
        }

        public void sendNotification(String message, String sender) {
            // TODO: Format the message using the current strategy.
            // TODO: Notify all registered listeners.
        }
    }
}
