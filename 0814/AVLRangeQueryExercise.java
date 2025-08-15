import java.util.*;

public class AVLRangeQueryExercise {
    static class Node {
        int key;
        Node left, right;
        public Node(int key) {
            this.key = key;
        }
    }

    static class AVLTree {
        Node root;

        public void insert(int key) {
            root = insert(root, key);
        }

        private Node insert(Node node, int key) {
            if (node == null) return new Node(key);
            if (key < node.key) node.left = insert(node.left, key);
            else if (key > node.key) node.right = insert(node.right, key);
            else return node;
            return balance(node);
        }

        // 範圍查詢功能
        public List<Integer> rangeQuery(int min, int max) {
            List<Integer> result = new ArrayList<>();
            rangeQuery(root, min, max, result);
            return result;
        }

        private void rangeQuery(Node node, int min, int max, List<Integer> result) {
            if (node == null) return;
            if (node.key > min) rangeQuery(node.left, min, max, result);
            if (node.key >= min && node.key <= max) result.add(node.key);
            if (node.key < max) rangeQuery(node.right, min, max, result);
        }

        // ...其餘 AVL 樹平衡、旋轉等方法可自行補上...
        private Node balance(Node node) { return node; }
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        int[] keys = {10, 5, 20, 15, 30, 25, 35};
        for (int key : keys) tree.insert(key);
        System.out.println("範圍 12~28 內的元素: " + tree.rangeQuery(12, 28)); // 應輸出 [15, 20, 25]
    }
}
