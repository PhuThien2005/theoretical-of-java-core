package no18_generics.practice.generic_repository_pattern;

import java.util.List;

/**
 * Test runner for GenericRepositoryPattern.
 */
public class GenericRepositoryPatternTest {

    public static void main(String[] args) {
        try {
            testUserRepository();
            testProductRepository();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testUserRepository() {
        GenericRepository<User, String> userRepo = new GenericRepository<>();
        assertEquals(0, userRepo.size(), "User repo starts empty");

        User u1 = new User("alice", "Alice Smith");
        User u2 = new User("bob", "Bob Jones");

        userRepo.save(u1.username, u1);
        userRepo.save(u2.username, u2);
        assertEquals(2, userRepo.size(), "Size matches 2");

        // Test find
        assertEquals(u1, userRepo.findById("alice"), "Find by ID alice");
        assertTrue(userRepo.existsById("bob"), "Exists by ID bob");
        assertTrue(!userRepo.existsById("charlie"), "Exists by ID charlie should be false");

        // Test findAll
        List<User> all = userRepo.findAll();
        assertEquals(2, all.size(), "findAll size matches");
        assertTrue(all.contains(u1) && all.contains(u2), "findAll contains both users");

        // Test delete
        userRepo.deleteById("alice");
        assertEquals(1, userRepo.size(), "Size after deletion is 1");
        assertTrue(userRepo.findById("alice") == null, "Alice is deleted");
    }

    private static void testProductRepository() {
        // Enforce repository reusability with a different type combination
        GenericRepository<Product, Integer> productRepo = new GenericRepository<>();

        Product p1 = new Product(101, "Laptop", 999.99);
        productRepo.save(p1.id, p1);

        assertEquals(p1, productRepo.findById(101), "Product lookup");
    }

    // Helper classes
    static class User {
        String username;
        String name;

        public User(String username, String name) {
            this.username = username;
            this.name = name;
        }
    }

    static class Product {
        int id;
        String name;
        double price;

        public Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }
}
