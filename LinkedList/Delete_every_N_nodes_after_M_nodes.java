// Definition of Linked List Node
class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

public class DeleteNAfterM {

    // Function to delete N nodes after every M nodes
    public static ListNode deleteNAfterM(ListNode head, int M, int N) {
        // If list is empty or M or N are invalid, return as is
        if (head == null || M <= 0 || N < 0) {
            return head;
        }

        ListNode current = head; // pointer to traverse the list

        // Traverse the entire linked list
        while (current != null) {

            //  Skip M nodes
            for (int i = 1; i < M && current != null; i++) {
                current = current.next;
            }

            // If we've reached end of list while skipping, break
            if (current == null)
                break;

            // Start deleting next N nodes
            ListNode temp = current.next; // temp points to first node to delete
            for (int i = 1; i <= N && temp != null; i++) {
                temp = temp.next;
            }

            // Link current node to the node after deleted part
            current.next = temp;

            // Move current to next segment
            current = temp;
        }

        return head;
    }

    // Function to print the linked list
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // Create linked list: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(6);
        head.next.next.next.next.next.next = new ListNode(7);
        head.next.next.next.next.next.next.next = new ListNode(8);
        head.next.next.next.next.next.next.next.next = new ListNode(9);
        head.next.next.next.next.next.next.next.next.next = new ListNode(10);

        System.out.println("Original Linked List:");
        printList(head);

        int M = 2;  // Keep 2 nodes
        int N = 3;  // Delete next 3 nodes

        head = deleteNAfterM(head, M, N);

        System.out.println("\nLinked List after deleting every " + N + " nodes after " + M + " nodes:");
        printList(head);
    }
}
