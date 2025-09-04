import java.util.*;

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        if (n <= 0) return ans;
        char[] path = new char[2 * n];
        dfs(0, 0, n, path, ans);
        return ans;
    }

    // open: 已放入 '(' 的數量；close: 已放入 ')' 的數量
    private void dfs(int open, int close, int n, char[] path, List<String> ans) {
        int idx = open + close;
        if (idx == 2 * n) {                 // 長度夠了，收集答案
            ans.add(new String(path));
            return;
        }
        if (open < n) {                     // 仍可放 '('
            path[idx] = '(';
            dfs(open + 1, close, n, path, ans);
        }
        if (close < open) {                 // 只有當 ')' 不會超過 '(' 才能放 ')'
            path[idx] = ')';
            dfs(open, close + 1, n, path, ans);
        }
    }
}

/*
解題思路（回溯 / 生成所有合法括號）：
1) 以 open, close 追蹤目前已放入的 '(' 與 ')' 數量。
2) 規則／剪枝：
   - 若 open < n，可放 '('；
   - 若 close < open，可放 ')'（確保任何前綴都合法）。
3) 當長度達 2n 時加入答案。因為過程中已保證合法，不需再做檢查。
4) 複雜度：
   - 產生的總數為第 n 個 Catalan 數 Cn，時間/輸出規模為 O(Cn)，遞迴深度 O(n)。
*/
