
// 可保留：若你的評測器會把 "[1,2,3,4]" 轉成鏈，這兩個工具很方便本地測試
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    // "[1,2,3]" -> 1->2->3；"[]" 或空白 -> null
    static ListNode deserialize(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty() || "[]".equals(s)) return null;
        if (s.charAt(0) == '[') s = s.substring(1);
        if (s.charAt(s.length() - 1) == ']') s = s.substring(0, s.length() - 1);
        if (s.trim().isEmpty()) return null;

        String[] parts = s.split(",");
        ListNode dummy = new ListNode(0), cur = dummy;
        for (String p : parts) {
            p = p.trim();
            if (p.isEmpty()) continue;
            cur.next = new ListNode(Integer.parseInt(p));
            cur = cur.next;
        }
        return dummy.next;
    }

    static String serialize(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode c = head; c != null; c = c.next) {
            if (c != head) sb.append(',');
            sb.append(c.val);
        }
        return sb.append(']').toString();
    }
}

class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode prev = dummy;

        while (head != null && head.next != null) {
            ListNode first = head;
            ListNode second = head.next;

            // 重新接指標：prev -> second -> first -> nextPairHead
            prev.next = second;
            first.next = second.next;
            second.next = first;

            // 移動到下一對
            prev = first;
            head = first.next;
        }
        return dummy.next;
    }
}

/*
解題思路（迭代 + 假頭 dummy）：
1) 只能改指標，不能改節點值。用 dummy 指向 head，prev 指向待交換那對的前一個節點。
2) 對每一對 (first=head, second=head.next)：
   prev.next = second
   first.next = second.next
   second.next = first
   然後 prev 移到 first，head 移到 first.next 繼續處理下一對。
3) 迴圈條件 head 與 head.next 皆不為 null，避免單獨剩一個節點時誤交換。
複雜度：時間 O(n)（每節點走一次），空間 O(1)。

（備選：遞迴寫法）
ListNode swapPairsRec(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode second = head.next;
    head.next = swapPairsRec(second.next);
    second.next = head;
    return second;
}
*/
