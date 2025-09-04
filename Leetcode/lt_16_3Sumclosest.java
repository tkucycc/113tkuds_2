import java.util.*;

class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);                           // 先排序
        int n = nums.length;
        int best = nums[0] + nums[1] + nums[2];      // 以任意有效三數和作初始值

        for (int i = 0; i < n - 2; i++) {
            int l = i + 1, r = n - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];

                // 若更接近目標就更新答案
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }

                if (sum == target) {
                    return target;                   // 已是最接近，不可能更好了
                } else if (sum < target) {
                    l++;                              // 總和太小 → 左指針右移增大總和
                } else {
                    r--;                              // 總和太大 → 右指針左移減小總和
                }
            }
        }
        return best;
    }
}

/*
解題思路（排序 + 雙指針）：
1) 先對陣列排序。固定第一個索引 i，剩下兩個數用左右指針 l、r 在區間 (i+1..n-1) 內尋找。
2) 計算 sum = nums[i] + nums[l] + nums[r]，用它與 target 的距離去更新當前最佳解 best。
3) 若 sum < target → 需要更大總和，l++；若 sum > target → 需要更小總和，r--；
   若 sum == target，已最接近，直接回傳。
4) 外層 i 從 0 掃到 n-3；整體時間 O(n^2)，排序 O(n log n) 但被 O(n^2) 主導；空間 O(1)。

*/
