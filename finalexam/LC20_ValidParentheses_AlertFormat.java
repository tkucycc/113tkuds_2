import java.io.*;

/** LC20_ValidParentheses_AlertFormat.java */
public class LC20_ValidParentheses_AlertFormat {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        if (s == null) s = "";                 // 空輸入視為空字串
        System.out.println(isValid(s) ? "true" : "false");
    }

    // O(n) 時間、O(n) 空間（最壞情況）
    static boolean isValid(String s) {
        int n = s.length();
        if ((n & 1) == 1) return false;        // 奇數長度一定不可能完全配對

        char[] stack = new char[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack[++top] = c;              // push
            } else {                            // c 為 關
                if (top < 0) return false;     // 沒有對應開
                char open = stack[top--];      // pop
                if (!match(open, c)) return false;
            }
        }
        return top == -1;                       // 全部配對完才是合法
    }

    private static boolean match(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }
}

/*
解題思路（Stack）：
1) 讀字元：遇到「開」括號就 push；遇到「關」括號就 pop 並檢查是否對應。
2) 任何時刻若需要 pop 但棧為空或型別不匹配，立即回傳 false。
3) 掃描完成後棧需為空才是合法。
邊界：空字串 true；奇數長度直接 false。
*/
