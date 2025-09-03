class Solution {
    public String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                sb.append(symbols[i]);
            }
        }
        return sb.toString();
    }
}

/*
解題思路：
1. 羅馬數字由大到小構成，並有幾個特殊的「減法形式」(如 4=IV, 9=IX)。
2. 我們準備一組對照表 values[] 和 symbols[]，包含一般數字與特殊組合。
3. 從最大數值開始，能減就減，並在結果中加上對應的符號。
   例如 1994：
     - 減 1000 → M
     - 減 900 → CM
     - 減 90 → XC
     - 減 4 → IV
     → "MCMXCIV"
4. 由於 values 陣列涵蓋所有情況，整數一定能轉換完成。
5. 時間複雜度：O(1)，因為最大數值 < 4000，最多迴圈固定次數。
*/
