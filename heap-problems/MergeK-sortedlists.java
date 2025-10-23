import java.util.PriorityQueue;
/* You are given k sorted linked lists. Merge them into one sorted linked list and return it.
Requirements:
1. Use a min heap to always extract the smallest element among the heads of the lists.
2. Handle linked list node insertion and extraction. */ 
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

public class MergeKSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode node : lists) {
            if (node != null) {
                minHeap.offer(node);
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            current.next = node;
            current = current.next;
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        MergeKSortedLists merger = new MergeKSortedLists();

        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(5);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);

        ListNode l3 = new ListNode(2);
        l3.next = new ListNode(6);

        ListNode[] lists = {l1, l2, l3};
        ListNode mergedHead = merger.mergeKLists(lists);

        while (mergedHead != null) {
            System.out.print(mergedHead.val + " ");
            mergedHead = mergedHead.next;
        }
}
}
