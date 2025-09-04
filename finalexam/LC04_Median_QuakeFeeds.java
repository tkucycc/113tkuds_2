import java.io.*;

/** LC04_Median_QuakeFeeds.java
 *  以「二分切割」在較短陣列上做 O(log min(n,m))，空間 O(1)
 */
public class LC04_Median_QuakeFeeds {

    // 快速讀取（支援到 1e5 規模）
    static class FastScanner {
        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { this.in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buf);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buf[ptr++];
        }
        boolean hasNext() throws IOException {
            int c;
            while (true) {
                c = read();
                if (c == -1) return false;
                if (c > ' ') { ptr--; return true; }
            }
        }
        String next() throws IOException {
            int c = read();
            while (c <= ' ') c = read();
            StringBuilder sb = new StringBuilder();
            while (c > ' ') { sb.append((char)c); c = read(); }
            return sb.toString();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
        double nextDouble() throws IOException { return Double.parseDouble(next()); }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        if (!fs.hasNext()) return;

        int n = fs.nextInt();
        int m = fs.nextInt();

        double[] A = new double[n];
        for (int i = 0; i < n; i++) A[i] = fs.nextDouble();

        double[] B = new double[m];
        for (int i = 0; i < m; i++) B[i] = fs.nextDouble();

        double median = findMedianSortedArrays(A, B);
        System.out.printf("%.1f%n", median);
    }

    // 經典二分切割：確保在較短陣列上二分
    static double findMedianSortedArrays(double[] A, double[] B) {
        int n = A.length, m = B.length;
        if (n == 0 && m == 0) return 0.0;     // 兩空列，題目未定義，此處回 0.0 作為保底

        if (n > m) return findMedianSortedArrays(B, A); // 保證 A 較短
        int totalLeft = (n + m + 1) / 2;

        int lo = 0, hi = n;
        while (lo <= hi) {
            int i = (lo + hi) >>> 1;          // 取 A 左邊 i 個
            int j = totalLeft - i;            // 取 B 左邊 j 個

            double Aleft   = (i == 0) ? Double.NEGATIVE_INFINITY : A[i - 1];
            double Aright  = (i == n) ? Double.POSITIVE_INFINITY : A[i];
            double Bleft   = (j == 0) ? Double.NEGATIVE_INFINITY : B[j - 1];
            double Bright  = (j == m) ? Double.POSITIVE_INFINITY : B[j];

            if (Aleft <= Bright && Bleft <= Aright) {      // 切割正確
                if (((n + m) & 1) == 1) {
                    return Math.max(Aleft, Bleft);
                } else {
                    double leftMax  = Math.max(Aleft, Bleft);
                    double rightMin = Math.min(Aright, Bright);
                    return (leftMax + rightMin) / 2.0;
                }
            } else if (Aleft > Bright) {
                hi = i - 1;                                 // A 左邊取太多，往左縮
            } else {
                lo = i + 1;                                 // A 左邊取太少，往右擴
            }
        }
        // 理論上不會到這；回傳以防極端輸入
        return 0.0;
    }
}

/*
解題思路（Median of Two Sorted Arrays 經典二分）：
1) 設 A 為較短陣列、B 為較長陣列。二分 i（A 左半取 i 個），令 j = (n+m+1)/2 - i，
   讓左半總元素數量恰為 (n+m+1)/2。
2) 檢查切割是否合法：A[i-1] ≤ B[j] 且 B[j-1] ≤ A[i]。
   - 若合法：奇數長度回 max(A[i-1], B[j-1])；偶數長度回
     (max(A[i-1],B[j-1]) + min(A[i],B[j])) / 2。
3) 若 A[i-1] > B[j]，代表 i 太大（A 左取太多），往左縮；否則往右擴。
4) 邊界用 ±∞（NEGATIVE/POSTIVE_INFINITY）處理 i=0/i=n、j=0/j=m。
時間 O(log min(n,m))，空間 O(1)。
*/
