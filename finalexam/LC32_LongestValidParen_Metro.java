import java.io.*;

/** LC32_LongestValidParen_Metro.java
 *  最長有效括號（Longest Valid Parentheses）— 索引堆疊做法
 */
public class LC32_LongestValidParen_Metro {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        if (s == null) s = "";
        System.out.println(longestValidParentheses(s));
    }

    // O(n) 時間、O(n) 空間；以「索引」當作堆疊元素，並以 -1 作為基準哨兵。
    static int longestValidParentheses(String s) {
        int n = s.length();
        if (n < 2) return 0;

        int[] stack = new int[n + 1]; // 模擬棧：存索引
        int top = 0;
        stack[0] = -1;                // 哨兵：最後一個不合法位置

        int best = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack[++top] = i;     // push '(' 的索引
            } else { // c == ')'
                top--;                // 嘗試與最近的 '(' 配對
                if (top < 0) {       // 棧空：沒有可配對的 '('，重置基準
                    top = 0;
                    stack[0] = i;     // 之後的有效片段起點必須在 i 之後
                } else {
                    // 當前 i 與棧頂之下的索引之間形成一段有效區間
                    best = Math.max(best, i - stack[top]);
                }
            }
        }
        return best;
    }
}

/*
解題思路（索引堆疊）
1) 用「索引」當堆疊元素，並先放入哨兵 -1 當作「上一個不合法位置」。
2) 掃描字串：
   - 遇 '(' ：把其索引 push。
   - 遇 ')' ：pop 一次；若此時棧空，代表出現孤立的 ')'，
     將目前位置 i 設為新的不合法基準（push 成 -1 的替代）；否則
     以 i - stack[top] 計算當前可延伸到的最長長度並更新答案。
3) 這樣每個索引最多進棧/出棧一次，時間 O(n)、空間 O(n)。
備註：也可用「雙向計數法」(left/right counters) 兩趟線性掃描達到 O(1) 空間，
但堆疊法實作更直觀、容易避免邊界錯誤。
*/
