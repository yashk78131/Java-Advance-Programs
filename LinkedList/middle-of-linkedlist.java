class Node {
    int val;
    Node next;
    Node(int val) { this.val = val; }
}

public class MiddleOfLinkedList {
    public static Node findMiddle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        Node a = new Node(1);
        a.next = new Node(2);
        a.next.next = new Node(3);
        a.next.next.next = new Node(4);
        a.next.next.next.next = new Node(5);
        System.out.println("Middle Element: " + findMiddle(a).val);
    }
}
