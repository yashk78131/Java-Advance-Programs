import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class BoundaryTraversal {

    // Add nodes from the left boundary (exclude leaf nodes)
    private static void addLeftBoundary(TreeNode node, List<Integer> result) {
        if (node == null)
            return;

        if (node.left != null) {
            result.add(node.val);
            addLeftBoundary(node.left, result);
        } else if (node.right != null) {
            result.add(node.val);
            addLeftBoundary(node.right, result);
        }
    }

    // Add all leaf nodes (L -> R)
    private static void addLeaves(TreeNode node, List<Integer> result) {
        if (node == null)
            return;

        addLeaves(node.left, result);
        if (node.left == null && node.right == null) {
            result.add(node.val);
        }
        addLeaves(node.right, result);
    }

    // Add nodes from the right boundary in reverse order (exclude leaf nodes)
    private static void addRightBoundary(TreeNode node, List<Integer> result) {
        if (node == null)
            return;

        if (node.right != null) {
            addRightBoundary(node.right, result);
            result.add(node.val);
        } else if (node.left != null) {
            addRightBoundary(node.left, result);
            result.add(node.val);
        }
    }

    // Boundary traversal of binary tree in anti-clockwise order
    public static List<Integer> boundaryTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null)
            return result;

        // Root value is always included
        result.add(root.val);

        // 1) Left boundary (excluding leaf nodes)
        addLeftBoundary(root.left, result);

        // 2) All leaf nodes
        addLeaves(root.left, result);
        addLeaves(root.right, result);

        // 3) Right boundary (excluding leaf nodes), added bottom-up
        addRightBoundary(root.right, result);

        return result;
    }

    // Small helper to print a traversal
    private static void printList(List<Integer> list) {
        for (int x : list)
            System.out.print(x + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        // Sample tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.left.right.left = new TreeNode(8);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        root.right.right.right = new TreeNode(9);

        List<Integer> boundary = boundaryTraversal(root);
        System.out.println("Boundary Traversal of the Binary Tree:");
        printList(boundary);
    }
}
