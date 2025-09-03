class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        // 初始化：處理可以匹配空字串的 pattern，例如 "a*", "a*b*", "a*b*c*"
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*' && dp[0][j - 2]) {
                dp[0][j] = true;
            }
        }

        // 遍歷 s 與 p
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);
                if (pc == '*') {
                    char prev = p.charAt(j - 2);
                    // 1) "*" 當成「0 次」：丟掉 "prev*"
                    dp[i][j] = dp[i][j - 2];
                    // 2) "*" 當成「>=1 次」：若 s[i-1] 能和 prev 匹配，就延續 dp[i-1][j]
                    if (matches(s.charAt(i - 1), prev)) {
                        dp[i][j] |= dp[i - 1][j];
                    }
                } else {
                    // 非 "*"：必須當前字元匹配，且前綴也匹配
                    if (matches(s.charAt(i - 1), pc)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        return dp[m][n];
    }

    private boolean matches(char sc, char pc) {
        return pc == '.' || pc == sc;
    }
}

/*
解題思路：
1. 定義：dp[i][j] 表示 s 的前 i 個字元 (s[0..i-1]) 與 p 的前 j 個字元 (p[0..j-1]) 是否匹配。
2. 初始化：
   - dp[0][0] = true 代表空字串匹配。
   - 若 p 是像 "a*", "a*b*" 這類可以消成空的 pattern，則 dp[0][j] = true。
3. 狀態轉移：
   - 若 p[j-1] 不是 '*'：
       - 需要 s[i-1] 與 p[j-1] 相等 (或 p[j-1] = '.' 可以匹配任意字元)，
         且 dp[i-1][j-1] 必須為 true。
   - 若 p[j-1] 是 '*'：
       - 可以當「0 次」：忽略 p[j-2]* → dp[i][j] = dp[i][j-2]
       - 可以當「>=1 次」：若 s[i-1] 可匹配 p[j-2]，
         則 dp[i][j] = dp[i-1][j]（繼續看前一個 s 字元）。
4. 最終答案：dp[m][n]
5. 複雜度：
   - 時間 O(m*n)，空間 O(m*n)。
*/
