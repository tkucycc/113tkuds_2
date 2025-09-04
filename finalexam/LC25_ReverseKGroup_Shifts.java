import java.io.*;
import java.util.*;

/** 反轉鏈結串列的每 k 個節點；不足 k 的尾端保持不動。 */
public class Main {

    // ====== 基本 ListNode 定義 ======
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 讀 k
        String line = nextNonEmptyLine(br);
        if (line == null) return;
        int k = Integer.parseInt(line.trim());

        // 讀整列數字（直到 EOF）
        List<Integer> vals = new ArrayList<>();
        String s;
        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (s.isEmpty()) continue;
            StringTokenizer st = new StringTokenizer(s);
            while (st.hasMoreTokens()) {
                vals.add(Integer.parseInt(st.nextToken()));
            }
        }

        // 建立鏈結串列
        ListNode head = build(vals);

        // 執行 k-group 反轉
        ListNode ans = reverseKGroup(head, k);

        // 輸出
        printList(ans);
    }

    // 取得下一個非空白行（可容忍空行）
    private static String nextNonEmptyLine(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) return line;
        }
        return null;
    }

    // 由數列建立鏈結串列
    private static ListNode build(List<Integer> vals) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int v : vals) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    // 輸出鏈結串列（以空白分隔）
    private static void printList(ListNode head) {
        StringBuilder sb = new StringBuilder();
        for (ListNode p = head; p != null; p = p.next) {
            sb.append(p.val).append(' ');
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1); // 去掉最後一個空白
        System.out.println(sb.toString());
    }

    // ====== 核心：每 k 個節點反轉，不足 k 的尾端保留 ======
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (k <= 1 || head == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode groupPrev = dummy; // 每一段反轉前的前驅
        while (true) {
            // 找到第 k 個節點（群組尾）
            ListNode kth = getKth(groupPrev, k);
            if (kth == null) break; // 剩餘不足 k，結束
            ListNode groupNext = kth.next; // 下一段的起點

            // 反轉 [groupPrev.next .. kth]
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            // 接回去；groupPrev.next 變成這段的尾巴
            ListNode newTail = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = newTail; // 下一段的前驅
        }
        return dummy.next;
    }

    // 從 start 往後走 k 步，回傳第 k 個節點；若不夠 k 個回傳 null
    private static ListNode getKth(ListNode start, int k) {
        while (k > 0 && start != null) {
            start = start.next;
            k--;
        }
        return start;
    }
}

/*
解題思路（雙指標 + 原地反轉）：
1) 直覺：每次取出 k 個節點做反轉；若不滿 k 則尾端保持原樣。
2) 技巧：
   - 設置 dummy 指向 head，方便處理頭部被反轉的情況。
   - 以 groupPrev 紀錄每一段反轉前的節點；用 getKth(groupPrev, k) 找到第 k 個節點 kth。
   - 設 groupNext = kth.next；接著把 [groupPrev.next .. kth] 這段用標準「頭插法」反轉，
     反轉時把指標指向 groupNext，讓反轉後能直接接回後續。
   - 反轉完成後，把 groupPrev.next 指向 kth，並將 groupPrev 移動到這段反轉後的尾巴（原本的 groupPrev.next）。
3) 時間複雜度：O(n)，每個節點只被走訪與改指標一次。
   空間複雜度：O(1)，原地反轉。
*/
