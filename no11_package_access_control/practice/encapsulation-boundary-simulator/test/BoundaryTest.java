package no11_package_access_control.practice.encapsulation_boundary_simulator;

import services.ServiceManager;

/**
 * Test runner for Encapsulation Boundary Simulator.
 */
public class BoundaryTest {

    public static void main(String[] args) {
        try {
            testServiceExecution();
            testClassVisibilityBoundary();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + "\nExpected: [" + expected + "]\nActual:   [" + actual + "]");
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testServiceExecution() {
        ServiceManager manager = new ServiceManager();
        assertEquals("work-complete", manager.executeTask(), "ServiceManager should run executeTask successfully via delegation");
    }

    private static void testClassVisibilityBoundary() {
        try {
            // Verify ServiceManager is public
            int managerMod = ServiceManager.class.getModifiers();
            assertTrue(java.lang.reflect.Modifier.isPublic(managerMod), "ServiceManager must be public");

            // Verify InternalWorker is NOT public (package-private)
            Class<?> workerClass = Class.forName("services.InternalWorker");
            int workerMod = workerClass.getModifiers();
            assertTrue(!java.lang.reflect.Modifier.isPublic(workerMod), "InternalWorker class must be package-private (NOT public)!");
            assertTrue(!java.lang.reflect.Modifier.isProtected(workerMod), "InternalWorker class must be package-private (NOT protected)!");
            assertTrue(!java.lang.reflect.Modifier.isPrivate(workerMod), "InternalWorker class must be package-private (NOT private)!");

        } catch (ClassNotFoundException e) {
            throw new AssertionError("InternalWorker class not found", e);
        }
    }
}
