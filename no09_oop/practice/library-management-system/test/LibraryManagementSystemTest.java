package no09_oop.practice.library_management_system;

/**
 * Test runner for LibraryManagementSystem.
 */
public class LibraryManagementSystemTest {

    public static void main(String[] args) {
        try {
            testBookPolymorphism();
            testJournalPolymorphism();
            testCheckoutLifecycle();
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

    private static void assertEquals(boolean expected, boolean actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testBookPolymorphism() {
        LibraryItem item = new Book("Effective Java", "B01", "Joshua Bloch", 412);
        
        // Assert base properties
        assertEquals("Effective Java", item.getTitle(), "Title should match");
        assertEquals("B01", item.getItemId(), "Item ID should match");
        assertEquals(false, item.isCheckedOut(), "Should not be checked out initially");
        
        // Assert polymorphic getDetails
        assertEquals("Book: Effective Java by Joshua Bloch (412 pages)", item.getDetails(), "Polymorphic Book details");
    }

    private static void testJournalPolymorphism() {
        LibraryItem item = new Journal("Java Magazine", "J01", 84);
        
        assertEquals("Java Magazine", item.getTitle(), "Title should match");
        assertEquals("J01", item.getItemId(), "Item ID should match");
        
        // Assert polymorphic getDetails
        assertEquals("Journal: Java Magazine (Issue #84)", item.getDetails(), "Polymorphic Journal details");
    }

    private static void testCheckoutLifecycle() {
        LibraryItem item = new Book("Clean Code", "B02", "Robert Martin", 464);
        
        // Test Checkout
        item.checkOut();
        assertEquals(true, item.isCheckedOut(), "Should be checked out");
        
        // Double Checkout should fail
        try {
            item.checkOut();
            throw new AssertionError("Double checkout should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // Expected
        }

        // Return Item
        item.returnItem();
        assertEquals(false, item.isCheckedOut(), "Should be returned (not checked out)");

        // Return Item again should fail
        try {
            item.returnItem();
            throw new AssertionError("Returning item that is not checked out should throw IllegalStateException");
        } catch (IllegalStateException e) {
            // Expected
        }
    }
}
