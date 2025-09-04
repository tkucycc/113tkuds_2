import java.util.*;

public class LC40_CombinationSum2_Procurement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), target = sc.nextInt();
        int[] cand = new int[n];
        for (int i = 0; i < n; i++) cand[i] = sc.nextInt();

        List<List<Integer>> ans = combinationSum2(cand, target);

        // 輸出：每行一個升序組合
        for (List<Integer> comb : ans) {
            for (int i = 0; i < comb.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(comb.get(i));
            }
            System.out.println();
        }
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates); // 去重的前提
        List<List<Integer>> res = new ArrayList<>();
        dfs2(candidates, 0, target, new ArrayList<>(), res);
        return res;
    }

    // II 版：元素只能用一次 → 下一層從 i+1；同層跳過重複值
    private static void dfs2(int[] a, int start, int remain, List<Integer> path, List<List<Integer>> res) {
        if (remain == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < a.length; i++) {
            if (a[i] > remain) break;              // 排序剪枝
            if (i > start && a[i] == a[i - 1]) continue; // **同層去重**：避免重複組合

            path.add(a[i]);
            dfs2(a, i + 1, remain - a[i], path, res); // i+1（每個數最多一次）
            path.remove(path.size() - 1);
        }
    }
}

/*
解題思路（Combination Sum II）：
1) 與 I 版同樣使用回溯 + remain；但每個元素只能用一次，下一層索引為 i+1。
2) 先排序，然後在「同一層」迴圈中做去重：若 i>start 且 a[i]==a[i-1]，跳過，
   這樣可避免 [1,2,5] 與另一個相同 1、2、5 組成的重複解。
3) 若 a[i] > remain，可直接 break（剪枝）。
4) 複雜度：回溯為指數級；排序 O(n log n)。相對 I 版，多了層級去重邏輯。
*/
