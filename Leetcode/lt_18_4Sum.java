import java.util.*;

class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        if (n < 4) return res;

        Arrays.sort(nums);

        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // i 去重

            // 針對 i 的剪枝（最小和 / 最大和）
            long min1 = (long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (min1 > target) break; // 後面只會更大，直接結束
            long max1 = (long) nums[i] + nums[n - 1] + nums[n - 2] + nums[n - 3];
            if (max1 < target) continue; // 以 nums[i] 為首都太小，換下一個 i

            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // j 去重

                // 針對 j 的剪枝（最小和 / 最大和）
                long min2 = (long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2];
                if (min2 > target) break; // 後面 j 只會更大，直接結束本層
                long max2 = (long) nums[i] + nums[j] + nums[n - 1] + nums[n - 2];
                if (max2 < target) continue; // 這個 j 太小，換下一個 j

                int l = j + 1, r = n - 1;
                while (l < r) {
                    long sum = (long) nums[i] + nums[j] + nums[l] + nums[r]; // 用 long 避免整數溢位
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[l], nums[r]));
                        int lv = nums[l], rv = nums[r];      // 內層去重
                        while (l < r && nums[l] == lv) l++;
                        while (l < r && nums[r] == rv) r--;
                    } else if (sum < target) {
                        l++; // 總和太小，左指針右移
                    } else {
                        r--; // 總和太大，右指針左移
                    }
                }
            }
        }
        return res;
    }
}

/*
解題思路（排序 + 雙指針 + 去重 + 剪枝）：
1) 先排序，外層兩層迴圈固定 i、j，內層在區間 (j+1..n-1) 以左右指針 l、r 尋找兩數，使
   nums[i] + nums[j] + nums[l] + nums[r] == target。
2) 去重：
   - i 與 j：若與前一個值相同則跳過。
   - l 與 r：找到一組後，連續跳過相同值，避免重複四元組。
3) 剪枝（重要，能顯著加速）：
   - 對於固定 i（或 j），計算可能的最小和／最大和（用鄰近最小/最大的三個或兩個數），
     若與 target 比較後不可能達成，提前 break/continue。
4) 溢位處理：
   - 題目值與目標可達 ±1e9，四數和可能超過 32-bit；計算 sum 與剪枝判斷一律用 long。
5) 複雜度：
   - 時間 O(n^3)（排序 O(n log n)，主體 O(n^3)），空間 O(1)（不含輸出）。
*/
