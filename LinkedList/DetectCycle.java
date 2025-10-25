// Here’s the Java code for Detecting a Cycle in a Linked List using Floyd’s Cycle Detection Algorithm (Tortoise and Hare method)
// Node structure for Linked List
class ListNode {
    int val;        // stores data
    ListNode next;  // pointer to the next node

    // constructor to initialize value
    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

public class DetectCycle {

    // Function to check if a linked list has a cycle or not
    public static boolean hasCycle(ListNode head) {
        // If list is empty or has only one node, cycle is not possible
        if (head == null || head.next == null)
            return false;

        // Use two pointers - slow and fast
        ListNode slow = head;   // moves one step at a time
        ListNode fast = head;   // moves two steps at a time

        // Traverse the list
        while (fast != null && fast.next != null) {
            slow = slow.next;          // move slow by 1 node
            fast = fast.next.next;     // move fast by 2 nodes

            // If slow and fast meet, it means there is a loop
            if (slow == fast) {
                return true; // Cycle found
            }
        }

        // If fast reaches the end, no cycle exists
        return false;
    }

    public static void main(String[] args) {
        // Create a linked list: 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        // (Optional) Create a cycle for testing:
        // Make the next of node 5 point back to node 3
        head.next.next.next.next.next = head.next.next;

        // Check if the linked list has a cycle
        if (hasCycle(head))
            System.out.println("✅ Cycle detected in the linked list!");
        else
            System.out.println("❌ No cycle detected in the linked list.");
    }
}
