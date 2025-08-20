import java.util.*;

public class M02_YouBikeNextArrival {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] mins = new int[n];
        for (int i = 0; i < n; i++) {
            mins[i] = toMinutes(sc.next());
        }
        int query = toMinutes(sc.next());

        int idx = lowerBound(mins, query);  // first index with value >= query

        if (idx == n) {
            System.out.println("No bike");
        } else {
            System.out.println(toHHMM(mins[idx]));
        }
    }

    // 轉成自 00:00 起的分鐘數
    private static int toMinutes(String hhmm) {
        String[] p = hhmm.split(":");
        return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
    }

    // 轉回 HH:mm（補零）
    private static String toHHMM(int m) {
        int h = m / 60, mm = m % 60;
        return String.format("%02d:%02d", h, mm);
    }

    // 標準 lower_bound：找第一個 >= target 的位置（陣列已排序、無重複）
    private static int lowerBound(int[] a, int target) {
        int l = 0, r = a.length; // [l, r)
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (a[mid] >= target) r = mid;
            else l = mid + 1;
        }
        return l; // 若回傳 == a.length，表示沒有 ≥ target 的元素
    }
}


