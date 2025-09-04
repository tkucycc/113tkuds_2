import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        if (s == null || words == null || words.length == 0) return ans;

        int n = s.length();
        int w = words[0].length();      // 每個單字的長度（題目給定相同）
        int m = words.length;           // 單字數
        int total = w * m;
        if (n < total) return ans;

        // 需求表：每個單字需要幾次
        Map<String, Integer> need = new HashMap<>();
        for (String t : words) need.merge(t, 1, Integer::sum);

        // 依照起點位移 0..w-1 分成 w 組做滑動視窗
        for (int offset = 0; offset < w; offset++) {
            int left = offset, right = offset;
            int matched = 0;                          // 已滿足的單字數
            Map<String, Integer> seen = new HashMap<>();

            while (right + w <= n) {
                String cur = s.substring(right, right + w);
                right += w;

                if (need.containsKey(cur)) {
                    seen.merge(cur, 1, Integer::sum);
                    if (seen.get(cur) <= need.get(cur)) matched++;

                    // 若 cur 出現次數超標，縮小左界直到合法
                    while (seen.get(cur) > need.get(cur)) {
                        String drop = s.substring(left, left + w);
                        seen.put(drop, seen.get(drop) - 1);
                        if (seen.get(drop) < need.get(drop)) matched--;
                        left += w;
                    }

                    // 匹配到 m 個單字 → 記錄答案，再滑掉左邊一個單字繼續找下一個
                    if (matched == m) {
                        ans.add(left);
                        String drop = s.substring(left, left + w);
                        seen.put(drop, seen.get(drop) - 1);
                        matched--;
                        left += w;
                    }
                } else {
                    // 碰到不在字典裡的片段，整個視窗重置
                    seen.clear();
                    matched = 0;
                    left = right;
                }
            }
        }
        return ans;
    }
}

/*
解題思路（分組滑動視窗）：
1) 每個 words 的長度相同，記為 w；總字數 m，總長度 total = w*m。
2) 先用 HashMap<單字,需求次數> 建「需求表 need」以處理重複單字。
3) 將字串 s 依 w 做分組（起點 offset = 0..w-1）。對每組，以步長 w 向右掃描，
   維護一個視窗 [left, right) 與「已見次數 seen」：
   - 若當前片段 cur 在 need 中：
       * seen[cur]++；若未超過 need[cur]，matched++。
       * 若 cur 超標，從左邊每次刪一個單字並更新 matched，直到 cur 不再超標。
       * 當 matched == m，代表視窗內正好是某個排列的串接 → 記錄 left，
         然後從左邊再丟一個單字，繼續找下一個起點（避免重複計數）。
   - 若 cur 不在 need 中：清空 seen，matched=0，left 跳到 right。
4) 這樣每個字片段最多被加入/移除一次，對每個 offset 都是線性的。
   時間複雜度約 O(n)，空間 O(字典大小)。

小提醒：
- 先處理邊界：words 為空、s 長度 < w*m 直接回空。
- 這個方法能正確處理 words 中有重複單字的情況（例如 ["foo","foo"]）。
*/
