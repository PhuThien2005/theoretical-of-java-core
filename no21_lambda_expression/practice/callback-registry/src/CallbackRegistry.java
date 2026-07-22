package no21_lambda_expression.practice.callback_registry;

import java.util.List;
import java.util.ArrayList;

/**
 * Starter template for a Callback Registry using functional interfaces.
 */
public class CallbackRegistry {

    private final List<NotificationCallback> callbacks = new ArrayList<>();

    /**
     * Registers a callback listener to receive notifications.
     */
    public void register(NotificationCallback callback) {
        // TODO: Register the callback
    }

    /**
     * Triggers all registered callbacks with the specified message.
     */
    public void trigger(String message) {
        // TODO: Loop through callbacks and invoke their onNotification method
    }

    public int getCallbackCount() {
        return callbacks.size();
    }
}

/**
 * A functional interface representing a notification listener callback.
 */
@FunctionalInterface
interface NotificationCallback {
    void onNotification(String message);
}
