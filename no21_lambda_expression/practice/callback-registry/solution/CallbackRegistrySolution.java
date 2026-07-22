package no21_lambda_expression.practice.callback_registry;

import java.util.List;
import java.util.ArrayList;

/**
 * Reference solution for CallbackRegistrySolution.
 * 
 * Lambdas & Functional Interfaces:
 * - A functional interface contains exactly one abstract method.
 * - Lambda expressions `(msg) -> { ... }` provide inline implementation of the abstract method.
 * - This allows writing cleaner code without verbose anonymous inner classes.
 */
public class CallbackRegistrySolution {

    private final List<NotificationCallback> callbacks = new ArrayList<>();

    public void register(NotificationCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback cannot be null");
        }
        callbacks.add(callback);
    }

    public void trigger(String message) {
        for (NotificationCallback callback : callbacks) {
            callback.onNotification(message);
        }
    }

    public int getCallbackCount() {
        return callbacks.size();
    }
}

@FunctionalInterface
interface NotificationCallback {
    void onNotification(String message);
}
