import java.util.*;

class Solution {
    public boolean isValid(String s) {
        if ((s.length() & 1) == 1) return false;   // 奇數長度必不可能匹配

        Deque<Character> st = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // 遇到左括號就把「期望的右括號」推入堆疊
            if (c == '(')      st.push(')');
            else if (c == '[') st.push(']');
            else if (c == '{') st.push('}');
            else {
                // 遇到右括號：必須和堆疊頂端相同
                if (st.isEmpty() || st.pop() != c) return false;
            }
        }
        return st.isEmpty(); // 所有括號都正確匹配才成立
    }
}

/*
解題思路（堆疊）：
1) 依序掃描字串：
   - 若是左括號：把「對應的右括號」推入堆疊（例如遇 '(' -> 推 ')')。
   - 若是右括號：檢查堆疊是否為空，且彈出的字元必須等於該右括號。
2) 掃描結束後堆疊需為空，才代表全部成對且順序正確。
3) 小剪枝：若長度為奇數直接回傳 false。

為什麼推入「期望的右括號」？
- 這樣遇到右括號時，只需與堆疊頂端做一次等號比較即可，不需再做 map 對照，程式更簡潔。

複雜度：
- 時間 O(n)：每個字元進/出堆疊各一次。
- 空間 O(n)：最壞情況全是左括號，堆疊大小為 n。
*/
