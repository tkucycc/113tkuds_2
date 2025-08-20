import java.util.*;

public class M10_RBPropertiesCheck {
    static class Node {
        int val;        // -1 代表 null
        char color;     // 'B' or 'R'（val = -1 時忽略，但視為黑 NIL）
        Node(int v, char c) { val = v; color = c; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Node[] a = new Node[n];
        for (int i = 0; i < n; i++) {
            int v = sc.nextInt();
            String cs = sc.next();
            char c = cs.charAt(0);
            a[i] = new Node(v, c);
        }

        // 1) Root must be black（空樹/根為 -1 視為黑 NIL）
        if (!isBlack(a, 0)) {
            System.out.println("RootNotBlack");
            return;
        }

        // 2) No adjacent red nodes（依索引從小到大找，回報第一個違規索引，0-based）
        int redIdx = findFirstRedRedViolation(a);
        if (redIdx != -1) {
            System.out.println("RedRedViolation at index " + redIdx);
            return;
        }

        // 3) Black-height must match on all paths
        if (blackHeight(a, 0) == -1) {
            System.out.println("BlackHeightMismatch");
        } else {
            System.out.println("RB Valid");
        }
    }

    // 是否為黑（含越界或 -1 視為黑 NIL）
    private static boolean isBlack(Node[] a, int i) {
        if (i >= a.length || a[i].val == -1) return true; // NIL is black
        return a[i].color == 'B';
    }

    // 找到第一個（最小索引）紅-紅相鄰違規
    private static int findFirstRedRedViolation(Node[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i >= a.length || a[i].val == -1) continue;
            if (a[i].color == 'R') {
                int L = 2 * i + 1, R = 2 * i + 2;
                if (L < a.length && a[L].val != -1 && a[L].color == 'R') return i;
                if (R < a.length && a[R].val != -1 && a[R].color == 'R') return i;
            }
        }
        return -1;
    }

    // 計算黑高；若任一處不相等回傳 -1
    // 定義：NIL 的黑高 = 1；非空節點若為黑色，黑高 +1，紅色則不加。
    private static int blackHeight(Node[] a, int i) {
        // NIL（越界或 -1）→ 黑高 1
        if (i >= a.length || a[i].val == -1) return 1;

        int leftBH  = blackHeight(a, 2 * i + 1);
        if (leftBH == -1) return -1;
        int rightBH = blackHeight(a, 2 * i + 2);
        if (rightBH == -1) return -1;

        if (leftBH != rightBH) return -1;          // 黑高不一致

        int add = (a[i].color == 'B') ? 1 : 0;     // 黑節點 +1
        return leftBH + add;                       // leftBH == rightBH
    }
}

