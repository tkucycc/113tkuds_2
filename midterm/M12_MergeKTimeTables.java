import java.util.*;

/**
 * M12_MergeKTimeTables
 * 讀入 K 份已排序的時刻（分鐘或 HH:mm），以最小堆合併成一份遞增清單。
 */
public class M12_MergeKTimeTables {

    static class Node {
        int time;     // 分鐘
        int which;    // 來自哪一條清單
        int idx;      // 該清單中的索引
        Node(int t, int w, int i) { time = t; which = w; idx = i; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int K = Integer.parseInt(sc.next());
        List<int[]> lists = new ArrayList<>(K);

        for (int i = 0; i < K; i++) {
            int len = Integer.parseInt(sc.next());
            int[] arr = new int[len];
            for (int j = 0; j < len; j++) {
                String token = sc.next();
                arr[j] = parseToMinutes(token);
            }
            lists.add(arr);
        }

        // 最小堆：依 time 升冪
        PriorityQueue<Node> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.time, b.time)
        );

        // 各清單的首元素進堆
        for (int i = 0; i < K; i++) {
            int[] arr = lists.get(i);
            if (arr.length > 0) pq.offer(new Node(arr[0], i, 0));
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (!first) sb.append(' ');
            sb.append(cur.time);
            first = false;

            int[] from = lists.get(cur.which);
            int nextIdx = cur.idx + 1;
            if (nextIdx < from.length) {
                pq.offer(new Node(from[nextIdx], cur.which, nextIdx));
            }
        }

        System.out.println(sb.toString());
    }

    // 解析分鐘或 "HH:mm" -> 分鐘
    private static int parseToMinutes(String s) {
        if (s.indexOf(':') >= 0) {
            String[] p = s.split(":");
            int hh = Integer.parseInt(p[0]);
            int mm = Integer.parseInt(p[1]);
            return hh * 60 + mm;
        } else {
            return Integer.parseInt(s);
        }
    }
}

/*
-----------------------------------------
複雜度分析 (Complexity Analysis)

Let K = 清單數 (≤5), N = 所有時刻總數 (≤ 5 * 200)

Time Complexity:
- 每次從最小堆取出/放入的成本為 O(log K)。
- 總共處理 N 個元素 → O(N log K)。

Space Complexity:
- 最小堆同時只容納最多 K 個元素 → O(K)。
- 儲存輸入清單 → O(N)。
-----------------------------------------
*/
