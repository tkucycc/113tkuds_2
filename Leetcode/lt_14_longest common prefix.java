class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }
}

/*
解題思路：
1. 以第一個字串作為基準。
2. 從第 0 個字元開始，逐一檢查其他字串同一位置是否一致。
3. 如果遇到：
   - 任何字串長度不夠，或
   - 當前字元不同，
   立刻回傳從 0 到 i 的子字串。
4. 若檢查完第一個字串仍無差異，代表它就是最長公共前綴。
5. 時間複雜度 O(n*m)，n = 字串數量，m = 最短字串長度。
*/
