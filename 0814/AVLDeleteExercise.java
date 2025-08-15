public class AVLDeleteExercise {

    static class Node {
        int key;
        Node left, right;

        public Node(int key) {
            this.key = key;
        }
    }

    static class AVLTree {
        Node root;

        // 插入節點
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

        // 刪除節點
        public void delete(int key) {
            root = delete(root, key);
        }

        private Node delete(Node node, int key) {
            if (node == null) return null;

            if (key < node.key) {
                node.left = delete(node.left, key);
            } else if (key > node.key) {
                node.right = delete(node.right, key);
            } else {
                // 找到要刪除的節點
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;

                // 有兩個子節點，找中序後繼
                Node successor = getMin(node.right);
                node.key = successor.key;
                node.right = delete(node.right, successor.key);
            }

            return balance(node);
        }

        private Node getMin(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }

        // 搜尋節點
        public boolean search(int key) {
            return search(root, key);
        }

        private boolean search(Node node, int key) {
            if (node == null) return false;
            if (key == node.key) return true;
            return key < node.key ? search(node.left, key) : search(node.right, key);
        }

        // 計算樹高度
        public int height() {
            return height(root);
        }

        private int height(Node node) {
            if (node == null) return -1;
            return 1 + Math.max(height(node.left), height(node.right));
        }

        // 檢查是否為有效 AVL 樹
        public boolean isAVL() {
            return isAVL(root);
        }

        private boolean isAVL(Node node) {
            if (node == null) return true;

            int balance = getBalance(node);
            if (Math.abs(balance) > 1) return false;

            return isAVL(node.left) && isAVL(node.right);
        }

        // 取得平衡因子
        private int getBalance(Node node) {
            if (node == null) return 0;
            return height(node.left) - height(node.right);
        }

        // 平衡處理
        private Node balance(Node node) {
            int balance = getBalance(node);

            // LL
            if (balance > 1 && getBalance(node.left) >= 0)
                return rotateRight(node);

            // LR
            if (balance > 1 && getBalance(node.left) < 0) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }

            // RR
            if (balance < -1 && getBalance(node.right) <= 0)
                return rotateLeft(node);

            // RL
            if (balance < -1 && getBalance(node.right) > 0) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }

            return node;
        }

        private Node rotateLeft(Node z) {
            Node y = z.right;
            Node T2 = y.left;
            y.left = z;
            z.right = T2;
            return y;
        }

        private Node rotateRight(Node z) {
            Node y = z.left;
            Node T3 = y.right;
            y.right = z;
            z.left = T3;
            return y;
        }

        // 中序輸出 (可視化用)
        public void printInOrder() {
            System.out.print("中序遍歷: ");
            printInOrder(root);
            System.out.println();
        }

        private void printInOrder(Node node) {
            if (node != null) {
                printInOrder(node.left);
                System.out.print(node.key + " ");
                printInOrder(node.right);
            }
        }
    }

    // 測試主程式
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // 建立測試樹
        int[] keys = {50, 30, 70, 20, 40, 60, 80};
        for (int key : keys) {
            tree.insert(key);
        }

        System.out.println("初始 AVL 樹：");
        tree.printInOrder();
        System.out.println("是 AVL？ " + tree.isAVL());

        // 1. 刪除葉節點
        System.out.println("\n刪除葉節點 20");
        tree.delete(20);
        tree.printInOrder();
        System.out.println("是 AVL？ " + tree.isAVL());

        // 2. 刪除只有一個子節點的節點
        System.out.println("\n刪除只有一個子節點的節點 30");
        tree.delete(30);
        tree.printInOrder();
        System.out.println("是 AVL？ " + tree.isAVL());

        // 3. 刪除有兩個子節點的節點
        System.out.println("\n刪除有兩個子節點的節點 50");
        tree.delete(50);
        tree.printInOrder();
        System.out.println("是 AVL？ " + tree.isAVL());

        // 搜尋測試
        System.out.println("\n搜尋節點 60: " + tree.search(60)); // true
        System.out.println("搜尋節點 50: " + tree.search(50)); // false（已刪除）

        // 顯示樹高度
        System.out.println("目前樹高度: " + tree.height());
    }
}
