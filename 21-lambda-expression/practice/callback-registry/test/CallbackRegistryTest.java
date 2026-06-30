import java.util.ArrayList;
import java.util.List;

/**
 * Test runner for CallbackRegistry.
 */
public class CallbackRegistryTest {

    public static void main(String[] args) {
        try {
            testCallbackExecution();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testCallbackExecution() {
        CallbackRegistry registry = new CallbackRegistry();
        assertEquals(0, registry.getCallbackCount(), "Initially 0 callbacks");

        // We use mutable list containers to accumulate messages triggered inside lambdas
        List<String> receivedMessages = new ArrayList<>();

        // Register lambda callback 1
        registry.register(msg -> receivedMessages.add("Callback1: " + msg));
        
        // Register lambda callback 2
        registry.register(msg -> receivedMessages.add("Callback2: " + msg));

        assertEquals(2, registry.getCallbackCount(), "Registered 2 callbacks");

        // Trigger notifications
        registry.trigger("Hello Lambdas!");

        // Assert that both callbacks executed
        assertEquals(2, receivedMessages.size(), "Two executions");
        assertEquals("Callback1: Hello Lambdas!", receivedMessages.get(0), "Callback 1 message match");
        assertEquals("Callback2: Hello Lambdas!", receivedMessages.get(1), "Callback 2 message match");
    }
}
