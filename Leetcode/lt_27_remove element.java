class Solution {
    // 方式一：穩定寫回（保留相對順序）
    public int removeElement(int[] nums, int val) {
        int k = 0;                        // 下一個要寫入的位置
        for (int x : nums) {
            if (x != val) nums[k++] = x;  // 發現非 val 就往前覆寫
        }
        return k;                         // 只檢查前 k 個，後面不重要
    }

    // 方式二：交換法（不保證順序，但寫入次數少；可視需要改用）
    // public int removeElement(int[] nums, int val) {
    //     int i = 0, n = nums.length;
    //     while (i < n) {
    //         if (nums[i] == val) {
    //             nums[i] = nums[n - 1]; // 用尾巴覆蓋
    //             n--;                    // 縮短有效區間
    //         } else {
    //             i++;
    //         }
    //     }
    //     return n;
    // }
}

/*
解題思路：
- 已知只需把陣列中 ≠ val 的元素放在前面，並回傳個數 k；k 之後的內容不重要。
- 方式一（雙指針覆寫）：
  逐一掃描，用 k 指向下一個要寫入的位置；遇到 ≠ val 就寫到 nums[k] 並 k++。
  時間 O(n)，空間 O(1)，而且保持原有相對順序。
- 方式二（交換尾端）：
  用 i 掃描、n 表示當前有效長度；若 nums[i]==val，就把尾端搬來覆蓋並 n--；
  否則 i++。當 val 很多時，寫入次數更少。不保證順序，同樣 O(n)/O(1)。
*/
