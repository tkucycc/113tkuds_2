class Solution {
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        int n = haystack.length(), m = needle.length();
        if (m > n) return -1;

        int[] lps = buildLps(needle);  // 最長前後綴表
        int i = 0, j = 0;              // i 掃 haystack，j 掃 needle
        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++; j++;
                if (j == m) return i - m; // 完全匹配，回起始位置
            } else if (j > 0) {
                j = lps[j - 1];          // 失配：利用 lps 跳回
            } else {
                i++;                     // j==0 無可跳回，只能 i 前進
            }
        }
        return -1;
    }

    // 建立 lps（Longest Prefix which is also Suffix）
    private int[] buildLps(String p) {
        int m = p.length();
        int[] lps = new int[m];
        int len = 0; // 目前匹配的前後綴長度
        for (int i = 1; i < m; ) {
            if (p.charAt(i) == p.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];      // 回退到更短的可用前綴
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }
}

/*
解題思路（KMP）：
1) 先用 needle 建 lps 陣列：lps[i] 表示 p[0..i] 的最長「真前綴 = 真後綴」長度。
2) 掃描 haystack 與 needle：
   - 字元相等：i、j 同步前進；若 j 到 m，找到起點 i-m。
   - 失配且 j>0：j 跳到 lps[j-1]（避免 i 回頭）。
   - 失配且 j==0：只能 i++。
3) 複雜度：時間 O(n+m)，空間 O(m)。

備註：
- 這題資料量 m,n ≤ 1e4，朴素雙迴圈也可過（O(n*m)）；KMP 在最壞情況更穩定。
*/
