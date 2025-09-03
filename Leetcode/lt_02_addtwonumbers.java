// LeetCode 會提供這個定義；若在本地測試請保留。
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class L02_AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0), cur = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        return dummy.next;
    }
}

/*
解題思路：
- 兩串列倒序儲存十進位個位到高位，模擬直式加法。
- while 條件同時考慮 l1、l2、carry；每次建立新節點放 (sum % 10)。
- 最後若有進位，補一節點。
- 時間 O(max(m,n))，空間 O(1)（不含輸出鏈結串列）。
*/
