class Node {
    int data;
    Node next;
    Node child;

    Node(int data) {
        this.data = data;
        this.next = null;
        this.child = null;
    }
}

public class FlattenLinkedList {

    // Function to merge two sorted linked lists (by child pointers)
    private static Node merge(Node a, Node b) {
        // Base cases
        if (a == null) return b;
        if (b == null) return a;

        Node result;

        // Compare data and merge accordingly
        if (a.data < b.data) {
            result = a;
            result.child = merge(a.child, b); // merge remaining child lists
        } else {
            result = b;
            result.child = merge(a, b.child);
        }

        result.next = null; // next pointer not used in flattened list
        return result;
    }

    // Function to flatten the multi-level list
    public static Node flatten(Node head) {
        // Base case
        if (head == null || head.next == null)
            return head;

        // Recursively flatten the next part of the list
        head.next = flatten(head.next);

        // Merge current list with flattened next list
        head = merge(head, head.next);

        return head;
    }

    // Function to print the flattened list
    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data + " -> ");
            head = head.child;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // Example Multi-level linked list:
        // 5 -> 10 -> 19 -> 28
        // |     |     |     |
        // V     V     V     V
        // 7     20    22    35
        // |           |     |
        // V           V     V
        // 8           50    40
        // |                 |
        // V                 V
        // 30                45

        Node head = new Node(5);
        head.next = new Node(10);
        head.next.next = new Node(19);
        head.next.next.next = new Node(28);

        head.child = new Node(7);
        head.child.child = new Node(8);
        head.child.child.child = new Node(30);

        head.next.child = new Node(20);

        head.next.next.child = new Node(22);
        head.next.next.child.child = new Node(50);

        head.next.next.next.child = new Node(35);
        head.next.next.next.child.child = new Node(40);
        head.next.next.next.child.child.child = new Node(45);

        // Flatten the list
        Node flatHead = flatten(head);

        // Print flattened list
        System.out.println("Flattened Linked List:");
        printList(flatHead);
    }
}
