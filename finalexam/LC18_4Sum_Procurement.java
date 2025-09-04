import java.io.*;
import java.util.*;

public class LC18_4Sum_Procurement {

    // 簡單高速讀取
    static class FastScanner {
        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
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
            while ((c = read()) != -1) {
                if (c > ' ') { ptr--; return true; }
            }
            return false;
        }
        int nextInt() throws IOException {
            int c = read();
            while (c <= ' ') c = read();
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int x = 0;
            while (c > ' ') { x = x * 10 + (c - '0'); c = read(); }
            return x * sign;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        if (!fs.hasNext()) return;

        int n = fs.nextInt();
        int target = fs.nextInt();

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = fs.nextInt();

        Arrays.sort(nums);

        StringBuilder out = new StringBuilder();

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;      // 去重 i
            // 剪枝（可選）：若最小四數 > target 或最大四數 < target 可跳出/跳過
            long min4 = (long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (min4 > target) break;
            long max4 = (long) nums[i] + nums[n - 1] + nums[n - 2] + nums[n - 3];
            if (max4 < target) continue;

            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // 去重 j

                int L = j + 1, R = n - 1;
                long need = (long) target - nums[i] - nums[j];

                while (L < R) {
                    long two = (long) nums[L] + nums[R];
                    if (two == need) {
                        // 已排序，輸出遞增四元組
                        out.append(nums[i]).append(' ')
                           .append(nums[j]).append(' ')
                           .append(nums[L]).append(' ')
                           .append(nums[R]).append('\n');
                        L++; R--;
                        while (L < R && nums[L] == nums[L - 1]) L++; // 去重 L
                        while (L < R && nums[R] == nums[R + 1]) R--; // 去重 R
                    } else if (two < need) {
                        L++;
                    } else {
                        R--;
                    }
                }
            }
        }

        System.out.print(out.toString()); // 若無解，輸出為空
    }
}

/*
解題思路：
- 將陣列排序後，枚舉 i、j 作為前兩個元素，再用雙指針 L、R 尋找另外兩個，
  使四數和為 target。過程中精確去重（i、j、L、R）以避免重複四元組。
- 由於已排序，輸出組合天然為遞增；每組一行。
- 複雜度：O(n^3)，空間 O(1)（不含輸出）。
*/
