import java.util.*;

public class LC39_CombinationSum_PPE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), target = sc.nextInt();
        int[] cand = new int[n];
        for (int i = 0; i < n; i++) cand[i] = sc.nextInt();

        List<List<Integer>> ans = combinationSum(cand, target);

        // 輸出：每行一個升序組合
        for (List<Integer> comb : ans) {
            for (int i = 0; i < comb.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(comb.get(i));
            }
            System.out.println();
        }
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates); // 方便剪枝 & 保持輸出升序
        List<List<Integer>> res = new ArrayList<>();
        dfs(candidates, 0, target, new ArrayList<>(), res);
        return res;
    }

    // I 版：可重複選同一個數字 → 下一層仍然從 i 開始
    private static void dfs(int[] a, int start, int remain, List<Integer> path, List<List<Integer>> res) {
        if (remain == 0) { // 收斂
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < a.length; i++) {
            if (a[i] > remain) break; // 排序後可剪枝
            path.add(a[i]);
            dfs(a, i, remain - a[i], path, res); // i（可重複）
            path.remove(path.size() - 1);
        }
    }
}

/*
解題思路（Combination Sum I）：
1) 回溯 + 剩餘值 remain：每個節點代表「目前已選清單」與「剩餘 target」。
2) 排序後，若 a[i] > remain 可立即剪枝（後面更大）。
3) I 版允許同一元素重複取用 → 下一層仍從 i 開始（不是 i+1）。
4) 當 remain==0 時，把 path 收進答案；<0 不會發生（因剪枝）。
5) 複雜度：最壞情形為指數級；排序 O(n log n)。
*/
