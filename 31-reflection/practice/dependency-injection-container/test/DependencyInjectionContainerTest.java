/**
 * Test runner for DependencyInjectionContainer.
 */
public class DependencyInjectionContainerTest {

    public static void main(String[] args) {
        try {
            testSuccessfulInjection();
            testUnregisteredDependency();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testSuccessfulInjection() throws Exception {
        DependencyInjectionContainer container = new DependencyInjectionContainer();
        
        // Register database service
        DatabaseService dbService = new DatabaseService("jdbc:mysql://localhost:3306/db");
        container.register(DatabaseService.class, dbService);

        // Inject into controller
        UserController controller = new UserController();
        container.injectDependencies(controller);

        // Assert dependency was successfully injected
        assertEquals(dbService, controller.getDatabaseService(), "Service should be injected");
    }

    private static void testUnregisteredDependency() throws Exception {
        DependencyInjectionContainer container = new DependencyInjectionContainer();
        UserController controller = new UserController();

        try {
            container.injectDependencies(controller);
            throw new AssertionError("Injecting unregistered dependency should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    // Test classes
    static class DatabaseService {
        private final String dbUrl;

        public DatabaseService(String dbUrl) {
            this.dbUrl = dbUrl;
        }

        public String getDbUrl() {
            return dbUrl;
        }
    }

    static class UserController {
        @Inject
        private DatabaseService databaseService; // Private field to be injected

        public DatabaseService getDatabaseService() {
            return databaseService;
        }
    }
}
