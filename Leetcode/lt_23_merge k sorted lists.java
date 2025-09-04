import java.util.*;

/** ListNode 定義 + 測試器需要的輔助方法 */
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    /** 你的評測器會呼叫：把 JsonArray（如 [1,4,5]）轉成單條鏈結串列 */
    public static ListNode arrayToListNode(com.eclipsesource.json.JsonArray arr) {
        if (arr == null) return null;
        ListNode dummy = new ListNode(0), cur = dummy;
        for (com.eclipsesource.json.JsonValue v : arr) {
            if (v == null || v.isNull()) continue;
            cur.next = new ListNode(v.asInt());
            cur = cur.next;
        }
        return dummy.next; // 空陣列 -> null
    }

    /** 可選：把 "[1,4,5]" 轉成 1->4->5；"[]" 或空白 -> null（本地除錯用） */
    public static ListNode deserialize(String s) {
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

    /** 可選：把鏈結串列序列化成 "[...]"（本地除錯用） */
    public static String serialize(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode c = head; c != null; c = c.next) {
            if (c != head) sb.append(',');
            sb.append(c.val);
        }
        return sb.append(']').toString();
    }
}

/** LeetCode 23. Merge k Sorted Lists — 最小堆解法 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;

        PriorityQueue<ListNode> pq =
            new PriorityQueue<>(lists.length, Comparator.comparingInt(a -> a.val));

        for (ListNode node : lists) if (node != null) pq.offer(node);

        ListNode dummy = new ListNode(0), tail = dummy;
        while (!pq.isEmpty()) {
            ListNode cur = pq.poll();
            tail.next = cur;            // 直接重用節點
            tail = cur;
            if (cur.next != null) pq.offer(cur.next);
        }
        return dummy.next;
    }

    /* 亦可改用分治兩兩合併（時間同為 O(N log k)）
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        int interval = 1;
        while (interval < lists.length) {
            for (int i = 0; i + interval < lists.length; i += interval * 2) {
                lists[i] = mergeTwo(lists[i], lists[i + interval]);
            }
            interval *= 2;
        }
        return lists[0];
    }
    private ListNode mergeTwo(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0), t = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) { t.next = a; a = a.next; }
            else { t.next = b; b = b.next; }
            t = t.next;
        }
        t.next = (a != null) ? a : b;
        return dummy.next;
    }
    */
}

/*
解題思路（最小堆）：
1) 將 k 條鏈的當前節點放入最小堆（按 val 升序）。
2) 反覆彈出堆頂最小節點接到結果尾端，並將該節點的 next 放回堆。
3) 直到堆空即完成合併。
時間複雜度：O(N log k)（N 為總節點數），空間：O(k)。
*/
