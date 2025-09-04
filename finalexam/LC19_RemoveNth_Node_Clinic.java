import java.io.*;
import java.util.*;

/**  題目 10：護理紀錄刪除倒數第 N 筆  */
public class LC19_RemoveNth_Node_Clinic {

    // ======== 基本 ListNode 定義 ========
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { this.val = v; }
        ListNode(int v, ListNode n) { this.val = v; this.next = n; }
    }

    // ======== 輕量輸入工具（支援空白/換行混用） ========
    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        Integer nextInt() throws IOException {
            String s = next();
            return s == null ? null : Integer.parseInt(s);
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();

        // 讀入 n
        Integer nObj = fs.nextInt();
        if (nObj == null) return; // 沒有輸入
        int n = nObj;

        // 讀入 n 個節點值並建立單向串列
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int i = 0; i < n; i++) {
            int v = fs.nextInt();
            tail.next = new ListNode(v);
            tail = tail.next;
        }

        // 讀入 k
        int k = fs.nextInt();

        // 執行刪除倒數第 k 個節點
        ListNode head = removeNthFromEnd(dummy.next, k);

        // 輸出刪除後序列（空串列則輸出空行即可）
        StringBuilder out = new StringBuilder();
        for (ListNode p = head; p != null; p = p.next) {
            if (out.length() > 0) out.append(' ');
            out.append(p.val);
        }
        System.out.print(out.toString());
    }

    /** 雙指針：刪除倒數第 k 個節點（O(n) 時間 / O(1) 空間） */
    static ListNode removeNthFromEnd(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);

        // 讓 fast 比 slow 提前 k 步
        ListNode fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;   // k 保證合法：1 <= k <= n
        }

        // slow 從 dummy 出發；當 fast 抵達尾端時，slow 正好在「待刪節點的前一個」
        ListNode slow = dummy;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 刪除 slow 之後那個節點
        slow.next = slow.next.next;

        return dummy.next;
    }
}

/*
解題思路（雙指針 + 假頭 dummy）：
1) 先建一個指向 head 的假頭 dummy，方便處理刪到頭節點的情況。
2) 用兩個指標 fast 與 slow：
   - 先讓 fast 先走 k 步。
   - 接著 fast、slow 同步前進；當 fast 抵達串列尾端（null）時，
     slow 會剛好停在「待刪節點的前一個節點」。
3) 將 slow.next 指向 slow.next.next，即完成刪除。
4) 回傳 dummy.next 作為新的頭節點。
時間複雜度：O(n) 只掃一遍；空間複雜度：O(1) 只用常數額外空間。
*/
