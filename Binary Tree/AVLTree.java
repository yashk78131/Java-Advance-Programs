/**
 * AVL Tree Implementation in Java
 * AVL tree is a self-balancing Binary Search Tree (BST) where the difference 
 * between heights of left and right subtrees cannot be more than one for all nodes.
 */
class Node {
    int key;
    int height;
    Node left;
    Node right;

    Node(int key) {
        this.key = key;
        this.height = 1; // New node is initially at height 1
    }
}

public class AVLTree {
    private Node root;

    // Get height of the node
    private int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Get balance factor of node
    private int getBalance(Node node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    // Right rotate
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotate
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Insert a node
    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node node, int key) {
        // 1. Perform normal BST insertion
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else // Duplicate keys not allowed
            return node;

        // 2. Update height of ancestor node
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        // 3. Get the balance factor
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Print the tree in inorder traversal
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    // Main method to test the implementation
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        /* Test inserting values that trigger different rotations */
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);  // Triggers left rotation
        tree.insert(40);
        tree.insert(50);  // Triggers left rotation
        tree.insert(25);  // Triggers right-left rotation

        System.out.println("Inorder traversal of the AVL tree:");
        tree.inOrder();
    }
}
