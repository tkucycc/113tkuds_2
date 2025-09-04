import java.io.*;
import java.util.*;

/** LC01_TwoSum_THRSHoliday.java */
public class LC01_TwoSum_THRSHoliday {

    // 快速讀取（比 Scanner 快很多，n 可到 1e5 也沒問題）
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
        long nextLong() throws IOException {
            int c = read();
            while (c <= ' ') c = read();
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            long x = 0;
            while (c > ' ') { x = x * 10 + (c - '0'); c = read(); }
            return x * sign;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        if (!fs.hasNext()) return;
        int n = (int) fs.nextLong();
        long target = fs.nextLong();

        // key: 還需要的數值 (target - x)，value: 該需要值對應的索引
        Map<Long, Integer> needIdx = new HashMap<>(Math.max(16, n * 2));

        int i1 = -1, i2 = -1; // 答案索引
        for (int i = 0; i < n; i++) {
            long x = fs.nextLong();

            // 如果之前有人「需要」現在的 x，就找到解了
            Integer j = needIdx.get(x);
            if (j != null && i1 == -1) {
                i1 = j; i2 = i; // 任一組解即可
            } else {
                // 否則記錄「還需要 target - x」以及目前索引 i
                needIdx.put(target - x, i);
            }
        }

        if (i1 == -1) System.out.println("-1 -1");
        else          System.out.println(i1 + " " + i2);
    }
}

/*
解題思路（HashMap 一次遍歷）：
- 邊掃邊查。對當前數 x：
  1) 若 map 中存在 key = x，表示之前的某個數 y 使得 y + x == target，
     直接輸出 (map.get(x), i)。
  2) 否則把 (target - x) 作為「還需要的值」記入 map，對應目前索引 i。
- 若整趟掃完沒找到，輸出 -1 -1。

正確性與細節：
- 允許有多組解，第一次找到就輸出即可。
- 可處理重複數（例如 target/2 出現兩次）：第一次遇到會記下「需要 target/2」，
  第二次遇到時就能匹配成功。
- 使用 long 避免 target - x 在邊界下的整數溢位風險。

複雜度：
- 時間 O(n)，空間 O(n)。
*/
