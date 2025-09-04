// ===== ListNode with deserialize/serialize =====
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    // "[1,2,4]" -> 1->2->4, "[]" 或空白 -> null
    static ListNode deserialize(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty() || s.equals("[]")) return null;
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

    // For local debug: list -> "[1,2,4]"
    static String serialize(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode c = head; c != null; c = c.next) {
            if (c != head) sb.append(',');
            sb.append(c.val);
        }
        return sb.append(']').toString();
    }
}

// ===== LeetCode 21: Merge Two Sorted Lists =====
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        ListNode a = list1, b = list2;
        while (a != null && b != null) {
            if (a.val <= b.val) {            // 穩定：相等時先取 a
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;     // 接上剩餘的一邊
        return dummy.next;
    }
}

/*
解題思路（迭代 + 假頭 dummy）：
1) 同時掃描兩條已排序鏈的當前節點，將較小者接到結果尾端，指標往前移。
2) 用 dummy 簡化處理頭節點；掃描結束把未耗盡的一條整段接上。
3) 時間 O(m+n)、空間 O(1)（重用原節點）。
*/
