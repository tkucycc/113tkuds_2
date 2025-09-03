class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) return s;

        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) rows[i] = new StringBuilder();

        int row = 0;
        int step = 1; // 1: 往下, -1: 往上
        for (int i = 0; i < s.length(); i++) {
            rows[row].append(s.charAt(i));
            // 碰到頂/底就反向
            if (row == 0) step = 1;
            else if (row == numRows - 1) step = -1;
            row += step;
        }

        StringBuilder ans = new StringBuilder();
        for (StringBuilder r : rows) ans.append(r);
        return ans.toString();
    }
}
