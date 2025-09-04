import java.io.*;
import java.util.*;

/** LC15_3Sum_THSRStops.java
 *  3Sum：排序 + 固定一點 + 兩指針；時間 O(n^2)，空間 O(1)（輸出除外）
 */
public class LC15_3Sum_THSRStops {

    // 快速讀取（支援多行輸入）
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
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = fs.nextInt();

        Arrays.sort(nums);

        StringBuilder out = new StringBuilder();

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 跳過重複起點
            if (nums[i] > 0) break;                         // 後面都 >0，不可能湊成 0

            int L = i + 1, R = n - 1;
            while (L < R) {
                int sum = nums[i] + nums[L] + nums[R];
                if (sum == 0) {
                    out.append(nums[i]).append(' ')
                       .append(nums[L]).append(' ')
                       .append(nums[R]).append('\n');
                    // 取到一組後，同步略過重複
                    L++; R--;
                    while (L < R && nums[L] == nums[L - 1]) L++;
                    while (L < R && nums[R] == nums[R + 1]) R--;
                } else if (sum < 0) {
                    L++;
                } else {
                    R--;
                }
            }
        }

        System.out.print(out.toString()); // 若無解則不輸出任何行
    }
}

/*
解題思路：
1) 先排序，枚舉每個 i 作為第一個數；其餘兩數以雙指針在區間 (i, n-1] 內尋找，使三數和為 0。
2) 去重：
   - i 與 i-1 相同則跳過；
   - 當 sum==0 取到一組解後，同步跳過 L、R 的重複值，避免輸出重複三元組。
3) 若 nums[i] > 0，因為已排序，後面更不可能補成 0，提早結束。
時間 O(n^2)、空間 O(1)（不計輸出）。
*/
