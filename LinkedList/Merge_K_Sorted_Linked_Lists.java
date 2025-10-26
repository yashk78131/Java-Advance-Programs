import java.util.PriorityQueue;

// Definition for singly-linked list node
class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

public class MergeKSortedLists {

    // Function to merge K sorted linked lists
    public static ListNode mergeKLists(ListNode[] lists) {
        // Edge case: if no lists
        if (lists == null || lists.length == 0)
            return null;

        // Create a Min-Heap (PriorityQueue) that compares nodes by their values
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

        // Add the head node of each list to the heap
        for (ListNode node : lists) {
            if (node != null)
                minHeap.add(node);
        }

        // Dummy node to help build the merged list
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        // While heap is not empty, extract the smallest node
        while (!minHeap.isEmpty()) {
            // Remove the node with the smallest value
            ListNode minNode = minHeap.poll();

            // Add it to the merged list
            tail.next = minNode;
            tail = tail.next;

            // If the extracted node has a next node, push it to the heap
            if (minNode.next != null)
                minHeap.add(minNode.next);
        }

        // The merged list starts after the dummy node
        return dummy.next;
    }

    // Helper function to print linked list
    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // Create example sorted lists:
        // List 1: 1 -> 4 -> 7
        // List 2: 2 -> 5 -> 8
        // List 3: 3 -> 6 -> 9

        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(7);

        ListNode l2 = new ListNode(2);
        l2.next = new ListNode(5);
        l2.next.next = new ListNode(8);

        ListNode l3 = new ListNode(3);
        l3.next = new ListNode(6);
        l3.next.next = new ListNode(9);

        // Put all lists in an array
        ListNode[] lists = { l1, l2, l3 };

        // Merge all K sorted lists
        ListNode mergedHead = mergeKLists(lists);

        // Print the final merged list
        System.out.println("Merged Sorted Linked List:");
        printList(mergedHead);
    }
}
