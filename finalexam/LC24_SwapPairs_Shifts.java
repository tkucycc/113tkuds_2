import java.util.*;

public class LC24_SwapPairs_Shifts {

    // 定義鏈結串列節點
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int v) { val = v; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 讀取一行整數
        String line = sc.nextLine().trim();
        if (line.isEmpty()) {
            return; // 空輸入，直接結束
        }
        String[] parts = line.split("\\s+");

        // 建立鏈結串列
        ListNode head = buildList(parts);

        // 執行兩兩交換
        ListNode swapped = swapPairs(head);

        // 輸出
        printList(swapped);
    }

    // 建立串列
    private static ListNode buildList(String[] arr) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (String s : arr) {
            tail.next = new ListNode(Integer.parseInt(s));
            tail = tail.next;
        }
        return dummy.next;
    }

    // 兩兩交換
    public static ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (prev.next != null && prev.next.next != null) {
            // 取出要交換的兩個節點 a, b
            ListNode a = prev.next;
            ListNode b = a.next;

            // 執行交換：prev -> b -> a -> next
            prev.next = b;
            a.next = b.next;
            b.next = a;

            // 前進 prev 指標
            prev = a;
        }

        return dummy.next;
    }

    // 輸出串列
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

/*
解題思路：
1. 使用 Dummy 節點避免處理頭節點交換的特例。
2. 用 prev 指向要交換 pair 的前一個節點。
3. 設 a=prev.next, b=a.next，進行交換：
   prev.next = b;
   a.next = b.next;
   b.next = a;
   完成 (a,b) -> (b,a)。
4. 每次交換後 prev 前進到 a，繼續處理下一對。
5. 如果串列長度為奇數，最後一個節點不動。
6. 時間複雜度 O(n)，空間 O(1)。
*/
