class Solution {
    public int divide(int dividend, int divisor) {
        // 特殊情況：溢出
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        // 確定結果的正負號
        boolean negative = (dividend < 0) ^ (divisor < 0);

        // 轉為 long，避免溢出，並取絕對值
        long a = Math.abs((long) dividend);
        long b = Math.abs((long) divisor);

        long result = 0;

        // 核心：從高位開始，找能減多少次
        while (a >= b) {
            long temp = b, multiple = 1;
            // 每次加倍 divisor，直到超過 a
            while (a >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }
            a -= temp;        // 減掉當前最大能減的值
            result += multiple; // 累計對應倍數
        }

        return negative ? (int) -result : (int) result;
    }
}

/*
解題思路：
1. 題目限制不能用乘法、除法、取餘數，所以用「位移模擬除法」：
   - 每次將 divisor 左移（*2），找到 <= dividend 的最大倍數。
   - 減去這個倍數後，繼續處理剩餘 dividend。
   - 用位移記錄商的倍數，累加得到最終商。

2. 邊界處理：
   - dividend = Integer.MIN_VALUE，divisor = -1 時會超過 int 範圍，所以回傳 Integer.MAX_VALUE。

3. 時間複雜度：
   - 每次最多位移 31 次，整體 O(logN)。

4. 範例：
   dividend=10, divisor=3
   - 3<<1=6<=10, 減6，商=2
   - 剩餘4，再減3，商+1=3
   - 答案=3
*/
