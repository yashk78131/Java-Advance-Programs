class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

public class RemoveCycle {

    // Function to detect and remove cycle if present
    public static void removeCycle(ListNode head) {
        if (head == null || head.next == null)
            return;

        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;

        // Step 1: Detect cycle using Floyd’s algorithm
        while (fast != null && fast.next != null) {
            slow = slow.next;          // move slow by 1
            fast = fast.next.next;     // move fast by 2

            if (slow == fast) {        // cycle detected
                hasCycle = true;
                break;
            }
        }

        // Step 2: If no cycle found, return
        if (!hasCycle) {
            System.out.println("No cycle detected.");
            return;
        }

        // Step 3: Find the starting node of the cycle
        slow = head; // move slow to head
        ListNode prev = null; // to track node before fast

        // move slow and fast at same speed
        while (slow != fast) {
            prev = fast;    // store previous of fast
            slow = slow.next;
            fast = fast.next;
        }

        // Step 4: 'slow' and 'fast' now point to the start of the loop
        // 'prev' is the node just before the start of the loop
        prev.next = null;  // break the loop

        System.out.println("Cycle removed successfully!");
    }

    // Utility function to print the list
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // Create list: 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        // Create a cycle: connect node 5 to node 3
        head.next.next.next.next.next = head.next.next;

        // Remove cycle
        removeCycle(head);

        // Print the list after removing cycle
        System.out.println("Linked List after removing cycle:");
        printList(head);
    }
}
