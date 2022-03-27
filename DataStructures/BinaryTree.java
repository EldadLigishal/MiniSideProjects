package DataStructures;

public class BinaryTree {
    Node root;

    public void addNode(int key, String val) {
        Node node = new Node(key, val);

        if (root==null) {
            this.root = node;
        } else {
            Node currNode = root;
            Node parent;

            while (true) {
                parent = currNode;
                if (currNode.key > key)  {
                    currNode = currNode.leftChild;

                    if (currNode == null) {
                        parent.leftChild = node;
                        return;
                    }
                } else {
                    currNode = currNode.rightChild;
                    if (currNode == null) {
                        parent.rightChild = node;
                        return;
                    }
                }
            }
        }
    }

    public Node search(int key) {
        Node node = root;

        while (node.key != key) {
            if (key < node.key) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }

            if (node == null) {
                return null;
            }
        }
        return node;
    }

    public void inOrderScan(Node node) {
        if (node != null) {
            inOrderScan(node.leftChild);
            System.out.println(node);
            inOrderScan(node.rightChild);
        }
    }

    public void preOrderScan(Node node) {
        if (node != null) {
            System.out.println(node);
            preOrderScan(node.leftChild);
            preOrderScan(node.rightChild);
        }
    }

    public void postOrderScan(Node node) {
        if (node != null) {
            postOrderScan(node.leftChild);
            postOrderScan(node.rightChild);
            System.out.println(node);
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.addNode(50, "Boss");
        tree.addNode(25, "Coder");
        tree.addNode(15, "Secretary");
        tree.addNode(30, "QA");
        tree.addNode(75, "CTO");
        tree.addNode(80, "CEO");
        tree.addNode(60, "Clown");

        tree.inOrderScan(tree.root);
        System.out.println("\n");
        tree.postOrderScan(tree.root);
        System.out.println("\n");
        tree.preOrderScan(tree.root);
        //System.out.println(tree.search(75));

    }
}

class Node {
    int key;
    String value;
    Node leftChild;
    Node rightChild;

    Node(int key, String val) {
        this.key = key;
        this.value = val;
    }

    public String toString() {
        return this.value + " has a key " + this.key;
    }
}
