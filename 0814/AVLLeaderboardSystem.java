import java.util.*;

public class AVLLeaderboardSystem {
    static class Node {
        int score;
        String player;
        Node left, right;
        int height;
        int size; // 子樹大小
        public Node(String player, int score) {
            this.player = player;
            this.score = score;
            this.height = 1;
            this.size = 1;
        }
    }

    static class AVLTree {
        Node root;
        Map<String, Node> playerMap = new HashMap<>();

        // 1. 新增玩家分數
        public void add(String player, int score) {
            root = insert(root, player, score);
        }

        // 2. 更新玩家分數
        public void update(String player, int newScore) {
            Node node = playerMap.get(player);
            if (node != null) {
                root = delete(root, node.score, player);
                root = insert(root, player, newScore);
            }
        }

        // 3. 查詢玩家排名（分數高者排名前面）
        public int getRank(String player) {
            Node node = playerMap.get(player);
            if (node == null) return -1;
            return getRank(root, node.score, player);
        }

        private int getRank(Node node, int score, String player) {
            if (node == null) return 0;
            if (score > node.score || (score == node.score && player.compareTo(node.player) < 0)) {
                return getRank(node.left, score, player);
            } else if (score < node.score || (score == node.score && player.compareTo(node.player) > 0)) {
                int leftSize = (node.left != null) ? node.left.size : 0;
                return leftSize + 1 + getRank(node.right, score, player);
            } else {
                int leftSize = (node.left != null) ? node.left.size : 0;
                return leftSize + 1;
            }
        }

        // 4. 查詢前 K 名玩家
        public List<String> topK(int k) {
            List<String> result = new ArrayList<>();
            topK(root, result, k);
            return result;
        }

        private void topK(Node node, List<String> result, int k) {
            if (node == null || result.size() >= k) return;
            topK(node.left, result, k);
            if (result.size() < k) result.add(node.player + ":" + node.score);
            topK(node.right, result, k);
        }

        // AVL 插入
        private Node insert(Node node, String player, int score) {
            if (node == null) {
                Node newNode = new Node(player, score);
                playerMap.put(player, newNode);
                return newNode;
            }
            if (score > node.score || (score == node.score && player.compareTo(node.player) < 0)) {
                node.left = insert(node.left, player, score);
            } else if (score < node.score || (score == node.score && player.compareTo(node.player) > 0)) {
                node.right = insert(node.right, player, score);
            } else {
                // 玩家已存在，直接更新分數
                node.score = score;
                playerMap.put(player, node);
                return node;
            }
            update(node);
            return balance(node);
        }

        // AVL 刪除
        private Node delete(Node node, int score, String player) {
            if (node == null) return null;
            if (score > node.score || (score == node.score && player.compareTo(node.player) < 0)) {
                node.left = delete(node.left, score, player);
            } else if (score < node.score || (score == node.score && player.compareTo(node.player) > 0)) {
                node.right = delete(node.right, score, player);
            } else {
                playerMap.remove(player);
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;
                Node min = getMin(node.right);
                node.player = min.player;
                node.score = min.score;
                node.right = delete(node.right, min.score, min.player);
            }
            update(node);
            return balance(node);
        }

        private Node getMin(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }

        private void update(Node node) {
            if (node == null) return;
            int leftHeight = (node.left != null) ? node.left.height : 0;
            int rightHeight = (node.right != null) ? node.right.height : 0;
            node.height = Math.max(leftHeight, rightHeight) + 1;
            int leftSize = (node.left != null) ? node.left.size : 0;
            int rightSize = (node.right != null) ? node.right.size : 0;
            node.size = leftSize + rightSize + 1;
        }

        private int getBalance(Node node) {
            if (node == null) return 0;
            int leftHeight = (node.left != null) ? node.left.height : 0;
            int rightHeight = (node.right != null) ? node.right.height : 0;
            return leftHeight - rightHeight;
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
            Node y = x.right;
            Node T2 = y.left;
            y.left = x;
            x.right = T2;
            update(x);
            update(y);
            return y;
        }

        private Node rotateRight(Node y) {
            Node x = y.left;
            Node T2 = x.right;
            x.right = y;
            y.left = T2;
            update(y);
            update(x);
            return x;
        }
    }

    public static void main(String[] args) {
        AVLTree leaderboard = new AVLTree();
        leaderboard.add("Alice", 100);
        leaderboard.add("Bob", 120);
        leaderboard.add("Carol", 110);
        leaderboard.add("Dave", 90);
        leaderboard.update("Alice", 130);
        System.out.println("Alice 排名: " + leaderboard.getRank("Alice"));
        System.out.println("前 3 名: " + leaderboard.topK(3));
    }
}
