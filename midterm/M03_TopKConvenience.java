import java.util.*;

/**
 * 便利商店 Top-K 熱銷 — M03_TopKConvenience.java
 * 讀入 n 與 K，再讀入 n 筆 (name, qty)，輸出銷量最高的前 K 名：
 * 排序規則：qty 由高到低；qty 相同時，name 依字典序由小到大。
 */
public class M03_TopKConvenience {
    static class Item {
        String name;
        int qty;
        Item(String n, int q) { name = n; qty = q; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), K = sc.nextInt();

        // Min-Heap：把「最差」的放在頂端以便淘汰
        // 最差定義：qty 較小；若 qty 相同，name 字典序較大（因為輸出時較小者應排前）
        PriorityQueue<Item> pq = new PriorityQueue<>(
            (a, b) -> {
                if (a.qty != b.qty) return Integer.compare(a.qty, b.qty); // 小在前
                return b.name.compareTo(a.name); // 名字大的視為更差
            }
        );

        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int qty = sc.nextInt();

            pq.offer(new Item(name, qty));
            if (pq.size() > K) pq.poll(); // 只留前 K
        }

        // 取出並按最終規則排序：qty 由高到低；同 qty 時 name 由小到大
        List<Item> ans = new ArrayList<>(pq);
        ans.sort((a, b) -> {
            if (a.qty != b.qty) return Integer.compare(b.qty, a.qty);
            return a.name.compareTo(b.name);
        });

        for (Item it : ans) {
            System.out.println(it.name + " " + it.qty);
        }
    }
}

/*
--------------------------------------
複雜度分析 (Complexity Analysis)

設輸入筆數為 n、K 為欲輸出的前 K 名（K ≤ n）。

1) 讀取每筆資料並維護大小為 K 的最小堆：
   - 每次插入/可能彈出各 O(log K)，共 n 次
   - 時間：O(n log K)

2) 將堆中的最多 K 筆資料轉成清單並做一次排序輸出：
   - 排序 K 筆：O(K log K)

總時間複雜度：O(n log K + K log K) = O(n log K)
總額外空間複雜度：O(K)  — 儲存最小堆與輸出清單
（若 K ≥ n，則退化為 O(n log n) 與 O(n)）
--------------------------------------
*/
