package no20_comparable_comparator.practice.custom_sort_heap;

import java.util.Comparator;

/**
 * Test runner for CustomSortHeap.
 */
public class CustomSortHeapTest {

    public static void main(String[] args) {
        try {
            testNaturalOrdering();
            testCustomComparatorDescending();
            testNonComparableClassWithComparator();
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
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testNaturalOrdering() {
        // Natural ordering for Integer is ascending (smallest first)
        CustomSortHeap<Integer> heap = new CustomSortHeap<>();
        heap.add(30);
        heap.add(10);
        heap.add(20);

        assertEquals(10, heap.poll(), "Poll smallest (10)");
        assertEquals(20, heap.poll(), "Poll middle (20)");
        assertEquals(30, heap.poll(), "Poll largest (30)");
    }

    private static void testCustomComparatorDescending() {
        // Custom comparator for Integer: descending order (largest first)
        Comparator<Integer> descending = (a, b) -> b.compareTo(a);
        CustomSortHeap<Integer> heap = new CustomSortHeap<>(descending);
        
        heap.add(30);
        heap.add(10);
        heap.add(20);

        assertEquals(30, heap.poll(), "Poll largest first (30)");
        assertEquals(20, heap.poll(), "Poll middle (20)");
        assertEquals(10, heap.poll(), "Poll smallest (10)");
    }

    private static void testNonComparableClassWithComparator() {
        // Item class does not implement Comparable, so we MUST provide a Comparator
        Comparator<Item> nameComparator = (a, b) -> a.name.compareTo(b.name);
        CustomSortHeap<Item> heap = new CustomSortHeap<>(nameComparator);

        heap.add(new Item("banana"));
        heap.add(new Item("apple"));
        heap.add(new Item("cherry"));

        assertEquals("apple", heap.poll().name, "First item alphabetically");
        assertEquals("banana", heap.poll().name, "Second item alphabetically");
        assertEquals("cherry", heap.poll().name, "Third item alphabetically");
    }

    // Helper class that is NOT Comparable
    static class Item {
        String name;

        public Item(String name) {
            this.name = name;
        }
    }
}
