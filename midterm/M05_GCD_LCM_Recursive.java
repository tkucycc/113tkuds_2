import java.util.Scanner;

public class M05_GCD_LCM_Recursive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong();   // 1 ≤ a ≤ 1e9
        long b = sc.nextLong();   // 1 ≤ b ≤ 1e9

        long g = gcd(a, b);       // 遞迴歐幾里得算法
        long l = (a / g) * b;     // 先除後乘，避免 a*b 溢位

        System.out.println("GCD: " + g);
        System.out.println("LCM: " + l);
    }

    // 遞迴版 GCD：gcd(x, y) = gcd(y, x % y)
    private static long gcd(long x, long y) {
        if (y == 0) return x;
        return gcd(y, x % y);
    }
}

/*
-----------------------------------------
複雜度分析 (Complexity Analysis)

Time Complexity:
- 歐幾里得演算法的時間複雜度為 O(log min(a, b))。

Space Complexity:
- 使用遞迴呼叫堆疊，深度約為 O(log min(a, b))。
  (Java 無尾遞迴最佳化，但在輸入 ≤ 1e9 時遞迴層數 < ~50，安全。)
-----------------------------------------
*/
