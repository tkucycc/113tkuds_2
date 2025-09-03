class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) { 
            return findMedianSortedArrays(nums2, nums1); // 保證 nums1 是短的
        }

        int m = nums1.length, n = nums2.length;
        int totalLeft = (m + n + 1) / 2;

        int left = 0, right = m; 
        while (left <= right) {
            int i = (left + right) / 2;
            int j = totalLeft - i;

            int nums1LeftMax = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int nums1RightMin = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int nums2LeftMax = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int nums2RightMin = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (nums1LeftMax <= nums2RightMin && nums2LeftMax <= nums1RightMin) {
                if ((m + n) % 2 == 1) {
                    return Math.max(nums1LeftMax, nums2LeftMax);
                } else {
                    return (Math.max(nums1LeftMax, nums2LeftMax) + 
                            Math.min(nums1RightMin, nums2RightMin)) / 2.0;
                }
            } else if (nums1LeftMax > nums2RightMin) {
                right = i - 1;
            } else {
                left = i + 1;
            }
        }
        throw new IllegalArgumentException();
    }
}
/*
解題思路：
- 在較短陣列 A 上二分切點 i，令 j 使左半總長 = (m+n+1)/2。
- 目標滿足：A[i-1] <= B[j] 且 B[j-1] <= A[i]。
- 依比較調整 i 範圍；找到後根據奇偶回傳中位數。
- 時間 O(log min(m,n))，空間 O(1)。
*/
