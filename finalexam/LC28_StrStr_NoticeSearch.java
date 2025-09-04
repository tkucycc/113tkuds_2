import java.util.*;

public class LC28_StrStr_NoticeSearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 讀兩行：haystack 與 needle（允許空字串）
        String haystack = sc.hasNextLine() ? sc.nextLine() : "";
        String needle   = sc.hasNextLine() ? sc.nextLine() : "";

        System.out.println(strStr(haystack, needle));
    }

    // 入口：KMP 搜尋
    public static int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;               // 規格：needle 空回 0
        if (haystack.length() < needle.length()) return -1;

        int[] lps = buildLPS(needle);                     // 失配函數 / 前綴表
        int i = 0, j = 0;                                 // i 掃 haystack；j 掃 needle

        while (i < haystack.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++; j++;
                if (j == needle.length()) {               // 成功比對到整段
                    return i - j;                         // 起始索引
                }
            } else {
                if (j > 0) j = lps[j - 1];               // 失配：用 lps「跳回」最佳位置
                else i++;                                 // j==0 無可跳，i 前進
            }
        }
        return -1;
    }

    // 構建 LPS（Longest Prefix which is also Suffix）表
    private static int[] buildLPS(String pat) {
        int n = pat.length();
        int[] lps = new int[n];
        int len = 0;                                      // 當前最長相等前後綴長度
        for (int i = 1; i < n; ) {
            if (pat.charAt(i) == pat.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];                       // 繼續嘗試更短的前後綴
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }
}

/*
解題思路（KMP）：
1) 目標：在字串 haystack 中找到子字串 needle 的首次出現起始索引，找不到回 -1；若 needle 為空回 0。
2) 為避免暴力 O(n*m) 每次失配都回頭重比，我們使用 KMP。
   - 先對 needle 建立 LPS（Longest Prefix-Suffix）表：lps[k] 代表 pat[0..k] 的最長「既是前綴又是後綴」長度。
   - 比對時若失配，j 不用回到 0，而是跳到 lps[j-1]，避免重複比較。
3) 正確性直覺：lps 保證跳轉後的前綴與已比對成功的尾端對齊，未遺漏任何可能匹配。
4) 複雜度：建表 O(m)，比對 O(n)，總 O(n+m)，額外空間 O(m)。
5) 邊界：needle 空→0；needle 長於 haystack→-1；重複前綴情境（如 "aaaaab" 找 "aaab"）由 LPS 正確處理。
*/
