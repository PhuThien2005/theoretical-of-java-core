package linkedlistcycledetector;

import linkedlistcycledetector.LinkedListCycleDetector.*;

public class LinkedListCycleDetectorTest {

    public static void main(String[] args) {
        try {
            testEmptyAndSingleNode();
            testNoCycleList();
            testCycleList();
            testSelfCycle();
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

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testEmptyAndSingleNode() {
        assertFalse(LinkedListCycleDetector.hasCycle(null), "Null head should not have cycle");
        assertFalse(LinkedListCycleDetector.hasCycle(new Node(1)), "Single node list should not have cycle");
    }

    private static void testNoCycleList() {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);

        assertFalse(LinkedListCycleDetector.hasCycle(head), "Linear list should not have cycle");
    }

    private static void testCycleList() {
        Node head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);
        Node fourth = new Node(4);

        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = second; // Create cycle: 4 -> 2

        assertTrue(LinkedListCycleDetector.hasCycle(head), "List with cycle 4 -> 2 should be detected");
    }

    private static void testSelfCycle() {
        Node head = new Node(1);
        head.next = head; // Self cycle: 1 -> 1

        assertTrue(LinkedListCycleDetector.hasCycle(head), "List with self cycle 1 -> 1 should be detected");
    }
}
