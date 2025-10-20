class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class SumTreeConverter {

    // Convert binary tree to sum tree. Returns sum of subtree including original
    // node value.
    public static int toSumTree(TreeNode node) {
        if (node == null)
            return 0;

        int leftSum = toSumTree(node.left);
        int rightSum = toSumTree(node.right);

        int oldValue = node.val;
        node.val = leftSum + rightSum;
        return node.val + oldValue;
    }

    // Inorder traversal print
    public static void printInorder(TreeNode node) {
        if (node == null)
            return;
        printInorder(node.left);
        System.out.print(node.val + " ");
        printInorder(node.right);
    }

    public static void main(String[] args) {
        // Example Tree
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(-2);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(8);
        root.left.right = new TreeNode(-4);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(5);

        System.out.println("Inorder Traversal before conversion:");
        printInorder(root);

        toSumTree(root);

        System.out.println("\n\nInorder Traversal after conversion to Sum Tree:");
        printInorder(root);
    }
}
