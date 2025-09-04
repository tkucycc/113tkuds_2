
// 可留作本地/你評測器使用：把 "[1,2,3]" 轉成鏈，或把鏈印成 "[...]"
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

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
            if (!p.isEmpty()) {
                cur.next = new ListNode(Integer.parseInt(p));
                cur = cur.next;
            }
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
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k <= 1 || head == null) return head;

        ListNode dummy = new ListNode(0, head);
        ListNode groupPrev = dummy;

        while (true) {
            // 找到這一組的第 k 個節點（組尾）；不足 k 個就結束
            ListNode kth = getKth(groupPrev, k);
            if (kth == null) break;
            ListNode groupNext = kth.next;

            // 反轉 [groupPrev.next, kth] 這一段
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;
            while (curr != groupNext) {
                ListNode tmp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = tmp;
            }

            // 連接：groupPrev -> (kth 變新頭) ... -> (原頭變新尾) -> groupNext
            ListNode newTail = groupPrev.next; // 反轉前的頭，反轉後變尾
            groupPrev.next = kth;
            groupPrev = newTail;               // 下一組的前一個節點
        }

        return dummy.next;
    }

    // 從 curr 出發往後走 k 步；若不足 k 步回傳 null
    private ListNode getKth(ListNode curr, int k) {
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;
    }
}

/*
解題思路（迭代，指標操作）：
1) 不能改節點值，只能改指標。用假頭 dummy 指向 head，並以 groupPrev 指到每一組的前一個節點。
2) 每輪：
   - 用 getKth(groupPrev, k) 找到組尾 kth；若不足 k 個直接結束（尾端保持原樣）。
   - 將 [groupPrev.next .. kth] 以「就地反轉」：令 prev = kth.next、curr = groupPrev.next，
     反轉直到 curr 走到 groupNext (= kth.next)。
   - 反轉完成後，kth 變成該組新頭、原頭變新尾。把 groupPrev.next 指到 kth，
     並把 groupPrev 移到新尾，進入下一組。
3) 複雜度：時間 O(n)（每個節點恰好被移動一次），空間 O(1)。
*/
