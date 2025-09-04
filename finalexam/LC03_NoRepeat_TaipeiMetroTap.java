import java.io.*;
import java.util.Arrays;

/** LC03_NoRepeat_TaipeiMetroTap.java
 *  最長無重複片段（Longest Substring Without Repeating Characters）
 */
public class LC03_NoRepeat_TaipeiMetroTap {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();            // 題目：一行字串；可能為空字串
        if (s == null) s = "";
        System.out.println(lengthOfLongestSubstring(s));
    }

    // 滑動視窗：右指標 i 右移，左界 left 由最近一次出現的位置 +1 來更新
    static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        // 用陣列記錄「最後一次出現的索引」，初始化為 -1
        // 65536 覆蓋 BMP 字元（中文也 OK）；若題目只保證 ASCII，改 256 也可
        int[] last = new int[65536];
        Arrays.fill(last, -1);

        int left = 0, best = 0;
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i);
            // 若此字元上次出現位置在視窗內，將左界推到它右邊
            if (last[c] >= left) {
                left = last[c] + 1;
            }
            last[c] = i;                     // 更新最後出現位置
            best = Math.max(best, i - left + 1);
        }
        return best;
    }
}

/*
解題思路（滑動視窗 + Map/陣列記錄最後出現位置）：
1) 維護視窗 [left, i] 內皆不重複。右指標 i 每次加入新字元 c。
2) 若 c 在視窗內曾出現（last[c] >= left），將 left 跳到 last[c]+1 去掉舊的 c。
3) 每步更新答案 best = max(best, 視窗長度)。
4) 因每個位置至多被 left、i 各經過一次，時間 O(n)；用陣列/Map 記錄出現位置，空間 O(Σ)。
邊界：空字串回傳 0；重複全相同回 1；交錯如 "abba" 回 2。
*/
