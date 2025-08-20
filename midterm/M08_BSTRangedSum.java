import java.util.*;

public class M08_BSTRangedSum {
    // 樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();           // 節點個數（含 -1 佔位）
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        int L = sc.nextInt();
        int R = sc.nextInt();

        TreeNode root = buildTree(arr);
        long sum = rangeSumBST(root, L, R);
        System.out.println("Sum: " + sum);
    }

    // 依層序建立樹（-1 表示 null）
    private static TreeNode buildTree(int[] a) {
        if (a.length == 0 || a[0] == -1) return null;
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(a[0]);
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < a.length) {
            TreeNode cur = q.poll();
            // left
            if (i < a.length && a[i] != -1) {
                cur.left = new TreeNode(a[i]);
                q.add(cur.left);
            }
            i++;
            // right
            if (i < a.length && a[i] != -1) {
                cur.right = new TreeNode(a[i]);
                q.add(cur.right);
            }
            i++;
        }
        return root;
    }

    // BST 區間總和（剪枝）
    private static long rangeSumBST(TreeNode node, int L, int R) {
        if (node == null) return 0;

        if (node.val < L) {
            // 當前值太小，只需往右子樹找
            return rangeSumBST(node.right, L, R);
        } else if (node.val > R) {
            // 當前值太大，只需往左子樹找
            return rangeSumBST(node.left, L, R);
        } else {
            // 介於 [L, R]，計入並往兩邊繼續
            return node.val
                 + rangeSumBST(node.left, L, R)
                 + rangeSumBST(node.right, L, R);
        }
    }
}

