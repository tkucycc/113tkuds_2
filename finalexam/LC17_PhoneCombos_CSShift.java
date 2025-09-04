import java.io.*;
import java.util.*;

public class LC17_PhoneCombos_CSShift {

    private static final String[] MAP = new String[]{
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String digits = br.readLine();
        if (digits == null) return;
        digits = digits.trim().replace(" ", "");
        if (digits.isEmpty()) return;                 // 空字串 → 不輸出

        List<String> ans = letterCombinations(digits);
        StringBuilder out = new StringBuilder();
        for (String s : ans) out.append(s).append('\n');
        System.out.print(out.toString());
    }

    static List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        dfs(digits, 0, path, res);
        return res;
    }

    private static void dfs(String digits, int idx, StringBuilder path, List<String> res) {
        if (idx == digits.length()) {
            res.add(path.toString());
            return;
        }
        char d = digits.charAt(idx);
        if (d < '2' || d > '9') return;              // 題目限制只有 2~9
        String letters = MAP[d - '2'];
        for (int i = 0; i < letters.length(); i++) {
            path.append(letters.charAt(i));
            dfs(digits, idx + 1, path, res);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
