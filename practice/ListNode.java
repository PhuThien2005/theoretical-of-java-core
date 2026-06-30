
public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode();
        ListNode a = l1;
        for (int i = 0; i < 7; i++) {
            l1.val = 9;
            l1.next = new ListNode();
            l1 = l1.next;
        }
        ListNode l2 = new ListNode();
        ListNode b = l2;
        for (int i = 0; i < 4; i++) {
            l2.val = 9;
            l2.next = new ListNode();
            l2 = l2.next;
        }
        Solution s = new Solution();
        ListNode d = s.addTwoNumbers(a, b);
    }
}

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode();
        ListNode tmp = new ListNode();
        ListNode res = l3;
        int a, b, r, sum;
        a = b = r = sum = 0;
        while (true) {
            a = l1 == null ? 0 : l1.val;
            b = l2 == null ? 0 : l2.val;
            sum = a + b + r;
            if (sum != 0) {
                l3.val = sum % 10;
                r = a + b > 9 ? 1 : 0;
                ListNode nex = new ListNode();
                l3.next = nex;
                tmp = l3;
                l3 = nex;
                l1 = l1 != null ? l1.next : null;
                l2 = l2 != null ? l2.next : null;
            } else {
                tmp.next = null;
                return res;
            }
        }
    }
}
