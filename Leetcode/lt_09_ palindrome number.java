class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        String s = Integer.toString(x);
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }
}

/*
解題思路：
- 不轉字串，僅反轉「一半」數字：
  - 迭代：rev = rev*10 + 尾數；x 去掉尾數。
  - 直到 rev >= x。
- 奇偶長度分別比較 x==rev 或 x==rev/10。
- 時間 O(log n)，空間 O(1)。
*/
