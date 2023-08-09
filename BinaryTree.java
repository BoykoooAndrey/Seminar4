import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    Node root;

    public BinaryTree() {
        root = null;
    }

    private class Node {
        int value;
        Node leftChild;
        Node rightChild;
        Node parent;
        Color color;

        public String toString() {
            return Integer.toString(value);
        }
    }

    private enum Color {
        RED, BLACK
    }

    public boolean find(int value) {
        Node current = root;
        while (current != null) {
            if (current.value == value)
                return true;

            if (value < current.value) {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
        }
        return false;
    }

    private Node LeftSwap(Node node) {
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rightChild;
   
        leftChild.parent = node.parent;
        node.parent = leftChild;
        if (betweenChild != null) betweenChild.parent = node;

        leftChild.rightChild = node;
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    private Node RightSwap(Node node) {
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;

        rightChild.parent = node.parent;
        node.parent = rightChild;
        if (betweenChild != null) betweenChild.parent = node;

        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    private void ColorSwap(Node node) {
        node.color = Color.RED;
        node.leftChild.color = Color.BLACK;
        node.rightChild.color = Color.BLACK;
    }

    private Node Rebalanse(Node Node) {
        Node result = Node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.rightChild != null && result.rightChild.color == Color.RED &&
                    (result.leftChild == null || result.leftChild.color == Color.BLACK)) {
                needRebalance = true;
                result = RightSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    (result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED)) {
                needRebalance = true;
                result = LeftSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.rightChild != null && result.rightChild.color == Color.RED) {
                needRebalance = true;
                ColorSwap(result);
            }
        } while (needRebalance);
        return result;

    }

    public boolean add(int value) {
        if (root != null) {
            boolean result = push(root, value);
            root = Rebalanse(root);
            root.color = Color.BLACK;
            return result;
        } else {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }

    public boolean push(Node node, int value) {
        if (node.value == value) {
            return false;
        } else {
            if (node.value > value) {
                if (node.leftChild != null) {
                    boolean result = push(node.leftChild, value);
                    node.leftChild = Rebalanse(node.leftChild);
                    return result;
                } else {
                    node.leftChild = new Node();
                    node.leftChild.color = Color.RED;
                    node.leftChild.value = value;
                    node.leftChild.parent = node;
                    return true;
                }
            } else {
                if (node.rightChild != null) {
                    boolean result = push(node.rightChild, value);
                    node.rightChild = Rebalanse(node.rightChild);
                    return result;
                } else {
                    node.rightChild = new Node();
                    node.rightChild.color = Color.RED;
                    node.rightChild.value = value;
                    node.rightChild.parent = node;
                    return true;
                }
            }
        }

    }

    public Node printTree() {
        List<List<Node>> lines = new ArrayList<>();
        List<Node> line = new ArrayList<>();
        line.add(root);
        lines.add(line);
        while (line.size() > 0) {
            List<Node> nextLine = new ArrayList<>();
            for (Node node : line) {

                System.out.printf("%s(%s-p) ", node.toString(), node.parent );
                if (node.leftChild != null)
                    nextLine.add(node.leftChild);
                if (node.rightChild != null)
                    nextLine.add(node.rightChild);
            }

            System.out.println();
            line = nextLine;
            if(nextLine.size() > 0) lines.add(nextLine);
        }
        

        return null;
    }

}