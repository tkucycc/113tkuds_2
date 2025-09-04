import java.util.*;

public class LC34_SearchRange_DelaySpan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 讀入：n target
        int n = sc.nextInt();
        int target = sc.nextInt();

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        int[] ans = searchRange(nums, target);
        System.out.println(ans[0] + " " + ans[1]);
    }

    // 兩次二分：left = lower_bound(target)，right = upper_bound(target) - 1
    public static int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int left = lowerBound(nums, target);       // 第一個 >= target 的位置

        // 若越界或該位不是 target -> 不存在
        if (left == n || nums[left] != target) return new int[]{-1, -1};

        int right = upperBound(nums, target) - 1;  // 最後一個 <= target 的位置
        return new int[]{left, right};
    }

    // 回傳第一個 >= x 的索引；若都小於 x，回傳 n
    private static int lowerBound(int[] a, int x) {
        int l = 0, r = a.length;                   // [l, r)
        while (l < r) {
            int mid = l + ((r - l) >> 1);
            if (a[mid] >= x) r = mid;
            else l = mid + 1;
        }
        return l;
    }

    // 回傳第一個 > x 的索引；若皆 <= x，回傳 n
    private static int upperBound(int[] a, int x) {
        int l = 0, r = a.length;                   // [l, r)
        while (l < r) {
            int mid = l + ((r - l) >> 1);
            if (a[mid] > x) r = mid;
            else l = mid + 1;
        }
        return l;
    }
}

/*
解題思路：
1) 陣列已排序，重複值連成區間。用兩次「專用二分」在 O(log n) 內鎖定左右邊界：
   - lower_bound(target)：第一個 >= target 的索引，即左邊界。
   - upper_bound(target)：第一個 > target 的索引；其前一格就是最後一個 == target 的位置，
     因此右邊界 = upper_bound(target) - 1。
2) 驗證存在性：若 left 越界或 nums[left] != target，代表 target 不存在，回傳 -1 -1。
3) 複雜度：每次二分 O(log n)，總 O(log n)；額外空間 O(1)。
4) 邊界情況：
   - n=0 → 直接回 -1 -1。
   - 全為 target → 會得到 [0, n-1]。
   - target 僅出現一次（頭或尾）與「不在範圍內」皆正確處理。
*/
