import java.util.*;

class L01_TwoSum {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(); // 值 -> 索引
        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];
            if (map.containsKey(need)) {
                return new int[]{ map.get(need), i };
            }
            map.put(nums[i], i);
        }
        return new int[0]; // 題目保證有解，實務上不會到這
    }
}

/*
解題思路：
- 以 HashMap 記錄「數值 -> 索引」；走訪陣列時檢查 target - nums[i] 是否已出現。
- 找到就回傳兩索引；沒找到就把目前值存入表。
- 時間 O(n)，空間 O(n)。
*/

