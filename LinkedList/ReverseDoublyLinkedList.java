/*
 * ReverseDoublyLinkedList.java
 * 
 * Description:
 * Given the head of a doubly linked list, reverse the list and return the new head.
 * 
 * Approach:
 * Traverse the list once, swapping each node's next and prev pointers.
 * The last processed node becomes the new head of the reversed list.
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 * Stable: Not applicable (reversal operation)
 */

class Node {
    int data;
    Node next;
    Node prev;

    Node(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

public class ReverseDoublyLinkedList {

    // Function to reverse a doubly linked list
    public static Node reverseDLL(Node head) {
        if (head == null || head.next == null)
            return head;

        Node current = head;
        Node temp = null;

        // Swap next and prev pointers for all nodes
        while (current != null) {
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            current = current.prev; // move to the next node (originally prev)
        }

        // After the loop, temp will point to the old prev of the last node
        if (temp != null)
            head = temp.prev;

        return head;
    }

    // Helper function to print the list
    public static void printList(Node head) {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    // Main method for testing
    public static void main(String[] args) {
        // Create a sample doubly linked list: 1 <-> 2 <-> 3 <-> 4 <-> 5
        Node head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);
        Node fourth = new Node(4);
        Node fifth = new Node(5);

        // Connect the nodes
        head.next = second;
        second.prev = head;
        second.next = third;
        third.prev = second;
        third.next = fourth;
        fourth.prev = third;
        fourth.next = fifth;
        fifth.prev = fourth;

        System.out.println("Original Doubly Linked List:");
        printList(head);

        head = reverseDLL(head);

        System.out.println("Reversed Doubly Linked List:");
        printList(head);
    }
}
