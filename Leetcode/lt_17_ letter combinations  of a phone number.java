import java.util.*;

class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if (digits == null || digits.length() == 0) return ans;

        // 0,1 不對應字母；2~9 對應電話鍵盤
        String[] map = {
            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
        };

        char[] path = new char[digits.length()];
        dfs(digits, 0, map, path, ans);
        return ans;
    }

    private void dfs(String digits, int idx, String[] map, char[] path, List<String> ans) {
        if (idx == digits.length()) {
            ans.add(new String(path));
            return;
        }
        String letters = map[digits.charAt(idx) - '0'];
        for (int i = 0; i < letters.length(); i++) {
            path[idx] = letters.charAt(i);
            dfs(digits, idx + 1, map, path, ans);
        }
    }
}

/*
解題思路（回溯 / 深度優先）：
1) 每個數字對應一組字母（2→"abc", 3→"def", ...）。題目要所有「每位各選一字母」的組合。
2) 用回溯建立長度等於 digits.length 的路徑 path：
   - 在第 idx 位，枚舉該數字對應的所有字母，填入 path[idx]，再遞迴到下一位。
   - 當 idx 到尾（== 長度）時，將 path 轉成字串加入答案。
3) 初始空輸入直接回傳空列表。

複雜度：
- 設有 k 位數，其中含 m 個三鍵（"abc"/"def"/"ghi"/"jkl"/"mno"/"tuv"）與 n 個四鍵（"pqrs"/"wxyz"），
  組合數為 3^m * 4^n；每條路徑建立與輸出要 O(k)。
- 時間 O(k * 3^m * 4^n)，空間 O(k)（遞迴深度與 path）。
*/

