package linkedlistcycledetector;

public class LinkedListCycleDetectorSolution {

    public static class Node {
        public int val;
        public Node next;

        public Node(int val) {
            this.val = val;
            this.next = null;
        }
    }

    /**
     * Determines if a singly linked list contains a cycle.
     * Uses Floyd's Cycle-Finding Algorithm (slow and fast pointers).
     * Operates in O(1) auxiliary space.
     */
    public static boolean hasCycle(Node head) {
        if (head == null || head.next == null) {
            return false;
        }

        Node slow = head;
        Node fast = head;

        // Traverse the list: fast moves twice as fast as slow
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            // If they meet, there is a cycle
            if (slow == fast) {
                return true;
            }
        }

        // Fast pointer reached the end of the list, so there is no cycle
        return false;
    }
}
