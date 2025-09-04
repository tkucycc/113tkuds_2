import java.io.*;
import java.util.*;

/*
 * 題目：多院區即時叫號合併
 * 思路：維護「每條序列的指標」+ 最小堆（PriorityQueue）
 *      先把每條非空序列的第一個元素丟進堆，之後每次彈出最小值，
 *      並把該值所屬序列的下一個元素再丟回堆。直到堆空。
 *
 * 時間複雜度：O(N log k) ；N 為總元素數量、k 為序列條數
 * 空間複雜度：O(k)
 */
public class LC23_MergeKLists_Hospitals {

    // 放入最小堆的節點：值、來源序列編號、在來源序列中的索引
    static class Node {
        int val;
        int listId;
        int idx;
        Node(int v, int id, int i) { val = v; listId = id; idx = i; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 讀 k（可能有空行，做個防禦性處理）
        String line = nextNonEmptyLine(br);
        if (line == null || line.isEmpty()) {
            // 無輸入就直接結束（輸出空行）
            System.out.println();
            return;
        }
        int k = Integer.parseInt(line.trim());

        // 讀入 k 條遞增序列（每行以 -1 結束，可能為空行：直接 -1）
        List<List<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            String row = nextNonEmptyLine(br);      // 容忍空白行
            if (row == null) row = "";
            lists.add(parseLine(row));
        }

        // 最小堆：值小者優先；若值相同，按 listId 讓結果穩定可重現
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) ->
            a.val != b.val ? Integer.compare(a.val, b.val) : Integer.compare(a.listId, b.listId)
        );

        // 每條非空序列丟第一個值進堆
        for (int i = 0; i < k; i++) {
            if (!lists.get(i).isEmpty()) {
                pq.offer(new Node(lists.get(i).get(0), i, 0));
            }
        }

        // 逐步彈出最小值並補進下一個
        StringBuilder out = new StringBuilder();
        boolean first = true;
        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (!first) out.append(' ');
            out.append(cur.val);
            first = false;

            int nextIdx = cur.idx + 1;
            List<Integer> src = lists.get(cur.listId);
            if (nextIdx < src.size()) {
                pq.offer(new Node(src.get(nextIdx), cur.listId, nextIdx));
            }
        }

        System.out.println(out.toString());
    }

    // 讀下一行非空字串（若檔尾則回傳 null）
    private static String nextNonEmptyLine(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (!s.isEmpty()) return s;
        }
        return null;
        // 若想保留空白行為空序列，可改回傳空字串 ""，但題面範例使用非空行即可。
    }

    // 將一行轉成整數列表，遇到 -1 為止；若該行是 "-1" 表示空序列
    private static List<Integer> parseLine(String line) {
        List<Integer> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(line);
        while (st.hasMoreTokens()) {
            int v = Integer.parseInt(st.nextToken());
            if (v == -1) break;            // -1 作為該序列的結束符
            list.add(v);
        }
        return list;
    }
}
