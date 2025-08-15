import java.util.*;

public class PersistentAVLExercise {
    static class Node {
        int key, height;
        Node left, right;
        public Node(int key) {
            this.key = key;
            this.height = 1;
        }
        // 路徑複製用建構子
        public Node(Node node) {
            if (node == null) return;
            this.key = node.key;
            this.height = node.height;
            this.left = node.left;
            this.right = node.right;
        }
    }

    static class PersistentAVL {
        List<Node> versions = new ArrayList<>();

        public PersistentAVL() {
            versions.add(null); // 初始空樹
        }

        // 插入操作產生新版本
        public void insert(int version, int key) {
            Node newRoot = insert(versions.get(version), key);
            versions.add(newRoot);
        }

        // 查詢歷史版本
        public Node getVersion(int version) {
            return versions.get(version);
        }

        // 中序遍歷（查詢某版本內容）
        public List<Integer> inOrder(int version) {
            List<Integer> result = new ArrayList<>();
            inOrder(getVersion(version), result);
            return result;
        }

        private void inOrder(Node node, List<Integer> result) {
            if (node == null) return;
            inOrder(node.left, result);
            result.add(node.key);
            inOrder(node.right, result);
        }

        // 路徑複製插入
        private Node insert(Node node, int key) {
            if (node == null) return new Node(key);
            Node newNode = new Node(node); // 複製路徑
            if (key < node.key) {
                newNode.left = insert(node.left, key);
            } else if (key > node.key) {
                newNode.right = insert(node.right, key);
            } else {
                return newNode; // 不插入重複
            }
            updateHeight(newNode);
            return balance(newNode);
        }

        private void updateHeight(Node node) {
            int lh = (node.left != null) ? node.left.height : 0;
            int rh = (node.right != null) ? node.right.height : 0;
            node.height = Math.max(lh, rh) + 1;
        }

        private int getBalance(Node node) {
            int lh = (node.left != null) ? node.left.height : 0;
            int rh = (node.right != null) ? node.right.height : 0;
            return lh - rh;
        }

        private Node balance(Node node) {
            int balance = getBalance(node);
            if (balance > 1) {
                if (getBalance(node.left) < 0) node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
            if (balance < -1) {
                if (getBalance(node.right) > 0) node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
            return node;
        }

        private Node rotateLeft(Node x) {
            Node y = new Node(x.right);
            Node T2 = y.left;
            y.left = new Node(x);
            y.left.right = T2;
            updateHeight(y.left);
            updateHeight(y);
            return y;
        }

        private Node rotateRight(Node y) {
            Node x = new Node(y.left);
            Node T2 = x.right;
            x.right = new Node(y);
            x.right.left = T2;
            updateHeight(x.right);
            updateHeight(x);
            return x;
        }
    }

    public static void main(String[] args) {
        PersistentAVL tree = new PersistentAVL();
        tree.insert(0, 10); // v1
        tree.insert(1, 20); // v2
        tree.insert(2, 5);  // v3
        tree.insert(3, 15); // v4
        System.out.println("v1: " + tree.inOrder(1)); // [10]
        System.out.println("v2: " + tree.inOrder(2)); // [10, 20]
        System.out.println("v3: " + tree.inOrder(3)); // [5, 10, 20]
        System.out.println("v4: " + tree.inOrder(4)); // [5, 10, 15, 20]
    }
}
