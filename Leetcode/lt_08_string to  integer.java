class Solution {
    public int myAtoi(String s) {
        int n = s.length(), i = 0;

        // 1) 跳過空白
        while (i < n && s.charAt(i) == ' ') i++;
        if (i == n) return 0;

        // 2) 判斷正負號
        int sign = 1;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            i++;
        }

        // 3) 讀取數字
        int ans = 0;
        int INT_MAX = Integer.MAX_VALUE; // 2147483647
        int INT_MIN = Integer.MIN_VALUE; // -2147483648

        while (i < n && Character.isDigit(s.charAt(i))) {
            int d = s.charAt(i) - '0';

            // 事先檢查是否會溢位
            if (ans > INT_MAX / 10 || (ans == INT_MAX / 10 && d > 7)) {
                return sign == 1 ? INT_MAX : INT_MIN;
            }

            ans = ans * 10 + d;
            i++;
        }

        return ans * sign;
    }
}
