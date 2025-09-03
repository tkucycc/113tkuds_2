import java.util.*;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            // 外層去重：同樣的起點只做一次
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            // 若最小值都已經 > 0，後面不可能湊成 0
            if (nums[i] > 0) break;

            int l = i + 1, r = n - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    // 內層去重：跳過相同的 l、r
                    int leftVal = nums[l], rightVal = nums[r];
                    while (l < r && nums[l] == leftVal) l++;
                    while (l < r && nums[r] == rightVal) r--;
                } else if (sum < 0) {
                    l++;
                } else { // sum > 0
                    r--;
                }
            }
        }
        return res;
    }
}

/*
解題思路摘要：
- 排序後，固定 i，對子陣列 [i+1, n-1] 用雙指針找兩數和 = -nums[i]。
- 為避免重複三元組：
  1) 外層若 nums[i] 與前一個相同就跳過；
  2) 內層找到答案後，把等於當前值的 l、r 連續跳過。
- 提前剪枝：若 nums[i] > 0，因為陣列遞增，後面不可能再組成 0，直接結束。
- 時間 O(n^2)，空間 O(1)（不含輸出）。
*/
