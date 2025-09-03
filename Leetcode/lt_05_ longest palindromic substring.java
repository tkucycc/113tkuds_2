class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) return s;
        int start = 0, end = 0; // 最長迴文的左右端點

        for (int i = 0; i < s.length(); i++) {
            int len1 = expandFromCenter(s, i, i);     // 奇數長度，如 "aba"
            int len2 = expandFromCenter(s, i, i + 1); // 偶數長度，如 "abba"
            int len = Math.max(len1, len2);
            if (len > end - start + 1) {
                // 由中心 i 推回左右端點
                start = i - (len - 1) / 2;
                end   = i +  len      / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expandFromCenter(String s, int L, int R) {
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--; R++;
        }
        return R - L - 1; // 退出時 L、R 已超過有效範圍
    }
}
