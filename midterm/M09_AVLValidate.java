import java.util.*;

public class M09_AVLValidate {
    // 二元樹節點
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        TreeNode root = buildTree(arr);

        // 1) 先驗證 BST（嚴格不允許重複：left < node < right）
        if (!isBST(root, Long.MIN_VALUE, Long.MAX_VALUE)) {
            System.out.println("Invalid BST");
            return;
        }

        // 2) 再驗證 AVL（後序回傳高度；-1 表示不平衡）
        if (checkAVL(root) == -1) {
            System.out.println("Invalid AVL");
        } else {
            System.out.println("Valid");
        }
    }

    // 依層序建立樹（-1 表 null）
    private static TreeNode buildTree(int[] a) {
        if (a.length == 0 || a[0] == -1) return null;
        TreeNode root = new TreeNode(a[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < a.length) {
            TreeNode cur = q.poll();
            // 左
            if (i < a.length && a[i] != -1) {
                cur.left = new TreeNode(a[i]);
                q.add(cur.left);
            }
            i++;
            // 右
            if (i < a.length && a[i] != -1) {
                cur.right = new TreeNode(a[i]);
                q.add(cur.right);
            }
            i++;
        }
        return root;
    }

    // 驗證 BST（帶上下界；嚴格不含等號）
    private static boolean isBST(TreeNode node, long lower, long upper) {
        if (node == null) return true;
        if (!(lower < node.val && node.val < upper)) return false;
        return isBST(node.left, lower, node.val) &&
               isBST(node.right, node.val, upper);
    }

    // 驗證 AVL：回傳高度；若任一處不平衡則回傳 -1
    private static int checkAVL(TreeNode node) {
        if (node == null) return 0;          // 空樹高度 0
        int lh = checkAVL(node.left);
        if (lh == -1) return -1;
        int rh = checkAVL(node.right);
        if (rh == -1) return -1;

        if (Math.abs(lh - rh) > 1) return -1; // 失衡
        return Math.max(lh, rh) + 1;
    }
}

/*
-----------------------------------------
複雜度分析 (Complexity Analysis)

Let n = 節點數, h = 樹高

Time Complexity:
- 建樹：O(n)
- 驗證 BST：每節點一次 → O(n)
- 驗證 AVL（後序計算高度）：每節點一次 → O(n)
=> 總時間複雜度：O(n)

Space Complexity:
- 佇列建樹在最寬層可能達 O(n)
- 遞迴深度至多為樹高 h → O(h)（最壞 O(n)）
=> 總空間複雜度：O(n)（受 BFS 及最寬層/遞迴堆疊主導）
-----------------------------------------
*/
