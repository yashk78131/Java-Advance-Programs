import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BPlusTree {
    private BPlusTreeNode root;
    private final int order; // Order 'm'

    // Node class is nested to keep everything in one file
    private class BPlusTreeNode {
        boolean isLeaf;
        List<Integer> keys;
        List<BPlusTreeNode> children;
        BPlusTreeNode nextLeaf; // For sequential traversal

        public BPlusTreeNode(boolean isLeaf) {
            this.isLeaf = isLeaf;
            this.keys = new ArrayList<>();
            if (!isLeaf) {
                this.children = new ArrayList<>();
            }
        }
    }

    public BPlusTree(int order) {
        this.order = order;
        this.root = new BPlusTreeNode(true);
    }

    // --- Core Operations ---

    public void insert(int key) {
        BPlusTreeNode leaf = findLeaf(key);
        
        // 1. Insert key into leaf
        insertIntoNode(leaf, key);

        // 2. Check for split
        if (leaf.keys.size() == order) {
            handleSplit(leaf);
        }
    }

    public boolean search(int key) {
        BPlusTreeNode leaf = findLeaf(key);
        return Collections.binarySearch(leaf.keys, key) >= 0;
    }

    // --- Helper Methods ---

    private BPlusTreeNode findLeaf(int key) {
        BPlusTreeNode node = root;
        while (!node.isLeaf) {
            int i = 0;
            // Find the child pointer to follow
            while (i < node.keys.size() && key >= node.keys.get(i)) {
                i++;
            }
            node = node.children.get(i);
        }
        return node;
    }

    private void insertIntoNode(BPlusTreeNode node, int key) {
        // Find insertion position and insert key in sorted order
        int pos = Collections.binarySearch(node.keys, key);
        if (pos < 0) {
            pos = -(pos + 1);
        }
        node.keys.add(pos, key);
    }
    
    // Simplifed split handler. For full implementation, this needs to be recursive 
    // to handle internal node splits (insertIntoParent).
    private void handleSplit(BPlusTreeNode node) {
        // Only leaf node split is shown for simplicity
        if (!node.isLeaf) return; 

        BPlusTreeNode newLeaf = new BPlusTreeNode(true);
        
        // Calculate the middle index for the split
        int middle = node.keys.size() / 2;
        
        // Move keys to new node
        newLeaf.keys.addAll(node.keys.subList(middle, node.keys.size()));
        node.keys.subList(middle, node.keys.size()).clear(); 

        // Update sequential link
        newLeaf.nextLeaf = node.nextLeaf;
        node.nextLeaf = newLeaf;

        // Get the key to be promoted
        int promotedKey = newLeaf.keys.get(0); 

        // If root is splitting
        if (node == root) {
            BPlusTreeNode newRoot = new BPlusTreeNode(false);
            newRoot.keys.add(promotedKey);
            newRoot.children.add(node);
            newRoot.children.add(newLeaf);
            root = newRoot;
        } else {
            // A full implementation would find the parent and call insertIntoParent(parent, promotedKey, newLeaf)
            // This example simply stops here, assuming the parent is not full.
            // In a real B+ Tree, this is the most complex part.
        }
    }


    // --- Printing for Demonstration ---
    
    public void printTree() {
        System.out.println("--- B+ Tree Structure (Order " + order + ") ---");
        printNode(root, 0);
    }

    private void printNode(BPlusTreeNode node, int level) {
        String type = node.isLeaf ? "Leaf" : "Internal";
        System.out.println("Level " + level + " (" + type + "): " + node.keys);

        if (!node.isLeaf) {
            for (BPlusTreeNode child : node.children) {
                printNode(child, level + 1);
            }
        }
    }

    // --- Main Method for Example Usage ---
    
    public static void main(String[] args) {
        // Order 3: Max 2 keys per node
        BPlusTree tree = new BPlusTree(3); 
        
        System.out.println("Inserting: 10, 20, 30, 40, 5");
        tree.insert(10);
        tree.insert(20);
        
        // Insertion of 30 causes a leaf split and root creation
        tree.insert(30); 
        tree.insert(40);
        tree.insert(5);

        tree.printTree();

        System.out.println("\nSearch Results:");
        System.out.println("Search for 20: " + tree.search(20)); // Expected: true
        System.out.println("Search for 25: " + tree.search(25)); // Expected: false
    }
}
