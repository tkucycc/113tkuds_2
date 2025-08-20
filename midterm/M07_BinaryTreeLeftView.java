import java.util.*;

public class M07_BinaryTreeLeftView {
    // 二元樹節點定義
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   // 節點數
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        TreeNode root = buildTree(arr);
        List<Integer> leftView = getLeftView(root);

        System.out.print("LeftView:");
        for (int val : leftView) {
            System.out.print(" " + val);
        }
    }

    // 建立二元樹 (依層序，-1 表 null)
    public static TreeNode buildTree(int[] arr) {
        if (arr.length == 0 || arr[0] == -1) return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;

        while (!q.isEmpty() && i < arr.length) {
            TreeNode curr = q.poll();

            // 左子樹
            if (i < arr.length && arr[i] != -1) {
                curr.left = new TreeNode(arr[i]);
                q.add(curr.left);
            }
            i++;

            // 右子樹
            if (i < arr.length && arr[i] != -1) {
                curr.right = new TreeNode(arr[i]);
                q.add(curr.right);
            }
            i++;
        }
        return root;
    }

    // BFS 找每層最左邊
    public static List<Integer> getLeftView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (i == 0) { // 每層第一個
                    result.add(node.val);
                }
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
        }
        return result;
    }
}

/*
-----------------------------------------
複雜度分析 (Complexity Analysis)

Time Complexity:
- 建樹過程：每個節點訪問一次 → O(n)
- BFS 輸出左視圖：每個節點訪問一次 → O(n)
=> 總時間複雜度: O(n)

Space Complexity:
- 佇列儲存節點，最差情況下一層可能有 O(n) 節點
- => 總空間複雜度: O(n)
-----------------------------------------
*/
