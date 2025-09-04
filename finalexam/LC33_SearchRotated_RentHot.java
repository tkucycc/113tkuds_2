

class Solution {
    public int search(int[] nums, int target) {
        int n = nums.length;
        int l = 0, r = n - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target) return mid;

            // 判斷哪一半是「有序」的（不含旋轉斷點）
            if (nums[l] <= nums[mid]) {                  // 左半段有序
                if (nums[l] <= target && target < nums[mid]) {
                    r = mid - 1;                        // 目標在左半段
                } else {
                    l = mid + 1;                        // 目標在右半段
                }
            } else {                                     // 右半段有序
                if (nums[mid] < target && target <= nums[r]) {
                    l = mid + 1;                        // 目標在右半段
                } else {
                    r = mid - 1;                        // 目標在左半段
                }
            }
        }
        return -1;
    }
}

/*
解題思路（Binary Search 變形）

1) 観察
   - 原本為嚴格遞增陣列（無重複），但被旋轉一次，因此整體可視為「兩段有序」接起來。
   - 任取中點 mid，至少有一側（左半段 [l..mid] 或右半段 [mid..r]）會是完全有序的。

2) 演算法
   while l <= r:
     mid = (l + r) / 2
     若 nums[mid] == target 直接回傳 mid
     否則判斷哪一半有序：
       - 若 nums[l] <= nums[mid]：左半段有序
           若 target 落在 [nums[l], nums[mid])，縮小到左半段 => r = mid - 1
           否則去右半段 => l = mid + 1
       - 否則：右半段有序
           若 target 落在 (nums[mid], nums[r]]，縮小到右半段 => l = mid + 1
           否則去左半段 => r = mid - 1

3) 邊界與細節
   - 條件務必注意「<= / <」邊界：上面用半開半閉區間避免重疊與漏判：
       左半段：nums[l] <= target && target < nums[mid]
       右半段：nums[mid] < target && target <= nums[r]
   - 未旋轉（純遞增）或只有一個元素皆可正常處理。
   - 題目假設無重複；若有重複需額外處理等於邊界的情況（本題不需）。

4) 複雜度
   - 時間：O(log n)（單次二分搜尋）
   - 空間：O(1)
*/
