import java.util.*;

class Solution {
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int val = map.get(s.charAt(i));
            // 若下一個字元存在 且比當前大 → 減法情況
            if (i + 1 < s.length() && map.get(s.charAt(i + 1)) > val) {
                total -= val;
            } else {
                total += val;
            }
        }
        return total;
    }
}

/*
解題思路：
1. 建立映射表，把每個羅馬字母對應到數值。
2. 從左到右掃描字串：
   - 一般情況：直接加上數值。
   - 特殊情況：若 s[i] < s[i+1]，表示這是一個「減法組合」(如 IV=4, IX=9)，
     此時 total 減去 s[i] 的值。
3. 例子 "MCMXCIV"：
   - M=1000 (+1000)
   - C=100 < M=1000 → 減100
   - M=1000 (+1000)
   - X=10 < C=100 → 減10
   - C=100 (+100)
   - I=1 < V=5 → 減1
   - V=5 (+5)
   總和 = 1994
4. 時間 O(n)，空間 O(1)。
*/
