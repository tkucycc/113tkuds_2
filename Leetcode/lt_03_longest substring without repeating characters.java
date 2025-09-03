import java.util.Arrays;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        // 若只包含英文字母、數字、符號與空白（ASCII）
        int[] last = new int[128];           // last[c] = 這個字元上次出現的位置
        Arrays.fill(last, -1);
        int best = 0, left = 0;              // 視窗為 [left, i]

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (last[c] >= left) {           // c 在視窗內重複了，左邊縮到重複那格右一格
                left = last[c] + 1;
            }
            last[c] = i;                     // 更新 c 的最新位置
            best = Math.max(best, i - left + 1);
        }
        return best;
    }
}

/*
解題思路：
- 滑動視窗 + 最近出現位置（Hash/陣列）。
- 若當前字元 c 在視窗內出現過，視窗左界跳到「上次位置 + 1」。
- 每步更新最長長度。
- 時間 O(n)，空間 O(Σ)；若含非 ASCII 可改 HashMap。
*/
