import java.util.*;

class TreeNode2 {
    int val;
    TreeNode2 left, right;
    TreeNode2(int val) { this.val = val; }
}

public class ReverseLevelOrderTraversal {
    public static void reverseLevelOrder(TreeNode2 root) {
        if (root == null) return;
        Queue<TreeNode2> queue = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode2 node = queue.poll();
            stack.push(node.val);
            if (node.right != null) queue.offer(node.right);
            if (node.left != null) queue.offer(node.left);
        }

        System.out.print("Reverse Level Order: ");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    public static void main(String[] args) {
        TreeNode2 root = new TreeNode2(1);
        root.left = new TreeNode2(2);
        root.right = new TreeNode2(3);
        root.left.left = new TreeNode2(4);
        root.left.right = new TreeNode2(5);
        reverseLevelOrder(root);
    }
}
