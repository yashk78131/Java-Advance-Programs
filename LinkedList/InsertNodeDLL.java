// Definition for a Doubly Linked List Node
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

class Solution {
    // Function to insert a new node with value x after position p
    public static Node addNode(Node head, int p, int x) {
        Node newNode = new Node(x);
        Node curr = head;

        for (int i = 0; i < p && curr != null; i++) {
            curr = curr.next;
        }

        // If the list is empty or position is invalid
        if (curr == null)
            return head;

        // Insert newNode after curr
        newNode.next = curr.next;
        newNode.prev = curr;
        curr.next = newNode;

        // If newNode is not inserted at the end, fix the next node's prev pointer
        if (newNode.next != null) {
            newNode.next.prev = newNode;
        }

        return head;
    }

    // Helper function to print the doubly linked list
    public static void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data);
            if (temp.next != null) System.out.print(" <-> ");
            temp = temp.next;
        }
        System.out.println();
    }

    // Main method to test the code
    public static void main(String[] args) {
        // Create a sample doubly linked list: 1 <-> 2 <-> 3 <-> 4
        Node head = new Node(1);
        Node second = new Node(2);
        Node third = new Node(3);
        Node fourth = new Node(4);

        head.next = second;
        second.prev = head;
        second.next = third;
        third.prev = second;
        third.next = fourth;
        fourth.prev = third;

        System.out.println("Original List:");
        printList(head);

        // Example 1: Insert 6 after position 2
        head = addNode(head, 2, 6);
        System.out.println("After inserting 6 after position 2:");
        printList(head);

        // Example 2: Insert 44 after position 0
        head = addNode(head, 0, 44);
        System.out.println("After inserting 44 after position 0:");
        printList(head);
    }
}
