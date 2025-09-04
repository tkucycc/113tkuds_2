import java.util.*;

public class LC21_MergeTwoLists_Clinics {

    // 基本單向鏈結串列節點
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    // 主程式：讀入、建立串列、合併、輸出
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 讀 n, m
        int n = sc.nextInt();
        int m = sc.nextInt();

        // 讀第一條升序串列
        ListNode l1 = buildList(sc, n);

        // 讀第二條升序串列
        ListNode l2 = buildList(sc, m);

        // 合併
        ListNode merged = mergeTwoLists(l1, l2);

        // 輸出（以空格分隔）
        printList(merged);
    }

    // 以輸入建立長度為 len 的升序串列
    private static ListNode buildList(Scanner sc, int len) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int i = 0; i < len; i++) {
            tail.next = new ListNode(sc.nextInt());
            tail = tail.next;
        }
        return dummy.next;
    }

    // 核心：合併兩個已排序串列（Dummy + tail）
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        // 雙指針逐一比較，小者接到 tail 後面並前進
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }

        // 收尾：把尚未耗盡的那條整段接上
        tail.next = (l1 != null) ? l1 : l2;

        return dummy.next;
    }

    private static void printList(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            head = head.next;
            if (head != null) sb.append(' ');
        }
        System.out.println(sb.toString());
    }
}
