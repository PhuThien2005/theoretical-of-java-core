package notificationserviceobserver;

import java.util.ArrayList;
import java.util.List;

public class NotificationServiceObserverSolution {

    // Strategy Pattern
    
    public interface FormattingStrategy {
        String format(String message, String sender);
    }

    public static class PlainTextStrategy implements FormattingStrategy {
        @Override
        public String format(String message, String sender) {
            validateInputs(message, sender);
            return "From " + sender + ": " + message;
        }
    }

    public static class MarkdownStrategy implements FormattingStrategy {
        @Override
        public String format(String message, String sender) {
            validateInputs(message, sender);
            return "**From " + sender + "**: *" + message + "*";
        }
    }

    public static class HtmlStrategy implements FormattingStrategy {
        @Override
        public String format(String message, String sender) {
            validateInputs(message, sender);
            return "<b>From " + sender + "</b>: <i>" + message + "</i>";
        }
    }

    private static void validateInputs(String message, String sender) {
        if (message == null || sender == null) {
            throw new IllegalArgumentException("Message and sender cannot be null");
        }
    }

    // Observer Pattern
    
    public interface NotificationListener {
        void onNotification(String formattedMessage);
    }

    public static class NotificationService {
        
        private final List<NotificationListener> listeners = new ArrayList<>();
        private FormattingStrategy strategy;

        public NotificationService(FormattingStrategy initialStrategy) {
            if (initialStrategy == null) {
                throw new IllegalArgumentException("Initial strategy cannot be null");
            }
            this.strategy = initialStrategy;
        }

        public void register(NotificationListener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener cannot be null");
            }
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }

        public void unregister(NotificationListener listener) {
            listeners.remove(listener);
        }

        public void setStrategy(FormattingStrategy strategy) {
            if (strategy == null) {
                throw new IllegalArgumentException("Strategy cannot be null");
            }
            this.strategy = strategy;
        }

        public void sendNotification(String message, String sender) {
            validateInputs(message, sender);
            // Format message using Strategy Pattern
            String formatted = strategy.format(message, sender);
            
            // Notify all registered Observers (Observer Pattern)
            for (NotificationListener listener : listeners) {
                listener.onNotification(formatted);
            }
        }
    }
}
