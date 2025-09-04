class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        int k = 1; // 下一個要寫入「不重複元素」的位置
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[k - 1]) {  // 發現新值就覆寫到前面
                nums[k++] = nums[i];
            }
        }
        return k; // 前 k 個即為結果，其後元素不重要
    }
}

/*
解題思路（雙指針 / 原地覆寫）：
1) 陣列已排序，相同元素一定相鄰。
2) 用 k 指向下一個「應寫入的不重複元素位置」，初始 k=1。
3) 從 i=1 掃到尾：
   - 若 nums[i] 與目前最後寫入的 nums[k-1] 不同，代表出現新值：
     將 nums[i] 複寫到 nums[k]，並 k++。
4) 迴圈結束，回傳 k；題目只檢查前 k 個元素，其後內容可忽略。

複雜度：
- 時間 O(n)
- 空間 O(1)
*/

