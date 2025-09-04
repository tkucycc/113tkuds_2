import java.io.*;
import java.util.*;

public class LC11_MaxArea_FuelHoliday {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null || line.trim().isEmpty()) { System.out.println(0); return; }
        int n = Integer.parseInt(line.trim());

        int[] h = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) h[i] = Integer.parseInt(st.nextToken());

        System.out.println(maxArea(h));
    }

    // 兩指針夾逼：從最外側開始往內收，永遠移動較短的一側
    static int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        long best = 0;  // 用 long 計算避免中途溢位，最後轉回 int（題目範圍安全）
        while (l < r) {
            long width = r - l;
            long area = width * Math.min(height[l], height[r]);
            if (area > best) best = area;

            if (height[l] < height[r]) {
                l++;                // 只有提升短板才可能得到更大面積
            } else {
                r--;
            }
        }
        return (int) best;
    }
}

/*
解題思路（雙指針）：
1) 令左右指針 l=0、r=n-1，當前面積 = (r-l) * min(h[l], h[r])；
2) 想要面積更大，寬度 (r-l) 只能變小，因此只有提高「短板」才可能提升面積：
   - 若 h[l] < h[r]，移動 l++；否則 r--。
3) 全程 O(n) 掃描一次；空間 O(1)。
邊界：n=2 只計算該對；高度含 0 也 OK。
*/
