import java.util.*;

public class M11_HeapSortWithTie {
    // 以兩個平行陣列存堆的 (score, index)
    static int[] Hs;   // score
    static int[] Hi;   // index (原始位置，從 0 開始)
    static int sz;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Hs = new int[n + 5];
        Hi = new int[n + 5];
        sz = 0;

        for (int i = 0; i < n; i++) {
            int s = sc.nextInt();
            push(s, i); // 建立最小堆：score 小者優先；同分 index 小者優先
        }

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < n; i++) {
            out.append(popMinScore());
            if (i + 1 < n) out.append(' ');
        }
        System.out.println(out.toString());
    }

    // ---- Min-Heap helpers ----

    // 比較 (aScore,aIdx) 是否比 (bScore,bIdx) 更「小」
    // 小的會往上（min-heap）
    static boolean less(int aS, int aI, int bS, int bI) {
        if (aS != bS) return aS < bS;
        return aI < bI; // 分數相同，索引小者優先
    }

    static void swap(int x, int y) {
        int ts = Hs[x], ti = Hi[x];
        Hs[x] = Hs[y];  Hi[x] = Hi[y];
        Hs[y] = ts;     Hi[y] = ti;
    }

    static void push(int score, int idx) {
        Hs[++sz] = score;
        Hi[sz] = idx;
        siftUp(sz);
    }

    static void siftUp(int k) {
        while (k > 1) {
            int p = k / 2;
            if (less(Hs[k], Hi[k], Hs[p], Hi[p])) {
                swap(k, p);
                k = p;
            } else break;
        }
    }

    static int popMinScore() {
        // 取出堆頂（最小）
        int ret = Hs[1];
        swap(1, sz--);
        siftDown(1);
        return ret;
    }

    static void siftDown(int k) {
        while (2 * k <= sz) {
            int a = 2 * k, b = a + 1, m = a;
            if (b <= sz && less(Hs[b], Hi[b], Hs[a], Hi[a])) m = b;
            if (less(Hs[m], Hi[m], Hs[k], Hi[k])) {
                swap(k, m);
                k = m;
            } else break;
        }
    }
}

/*
-----------------------------------------
複雜度分析 (Complexity Analysis)

Let n = 數據筆數

Time Complexity:
- 建堆（逐一 push）每次 O(log n)，總計 O(n log n)
  （也可用 Floyd O(n) 建堆，再彈出 n 次 O(n log n)）
- 取出 n 次，每次 O(log n)
=> 總時間複雜度：O(n log n)

Space Complexity:
- 兩個大小為 n 的陣列存堆元素
=> 空間複雜度：O(n)
-----------------------------------------
*/
