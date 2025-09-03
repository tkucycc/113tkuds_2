class Solution {
    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;  // 取尾數
            x /= 10;

            // 溢位檢查
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }

            rev = rev * 10 + pop;
        }
        return rev;
    }
}
/*
解題思路：
- 反轉數字 = 每次取尾數拼回 rev。
- 在乘回去前檢查是否會超出 32-bit（正界 2147483647、負界 -2147483648）。
- 時間 O(log|x|)，空間 O(1)。
*/
