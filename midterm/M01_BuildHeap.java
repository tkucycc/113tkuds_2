import java.util.*;

public class M01_BuildHeap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String type = sc.next();     // "max" or "min"
        int n = sc.nextInt();        // number of elements

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // Build heap (Bottom-up)
        buildHeap(arr, type);

        // Output
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + (i == n - 1 ? "" : " "));
        }
    }

    // Bottom-up buildHeap
    static void buildHeap(int[] arr, String type) {
        int n = arr.length;
        // Start from the last non-leaf node, move upward
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapifyDown(arr, n, i, type);
        }
    }

    // Heapify (percolate down)
    static void heapifyDown(int[] arr, int n, int i, String type) {
        int target = i; // current node index

        while (true) {
            int left = 2 * target + 1;
            int right = 2 * target + 2;
            int chosen = target;

            // Compare with left child
            if (left < n && compare(arr[left], arr[chosen], type)) {
                chosen = left;
            }
            // Compare with right child
            if (right < n && compare(arr[right], arr[chosen], type)) {
                chosen = right;
            }

            if (chosen != target) {
                swap(arr, target, chosen);
                target = chosen; // continue down
            } else {
                break;
            }
        }
    }

    // Comparison depending on heap type
    static boolean compare(int a, int b, String type) {
        if (type.equals("max")) {
            return a > b;
        } else {
            return a < b;
        }
    }

    static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

/*
--------------------------------------
複雜度分析 (Complexity Analysis)

1. 單次 heapifyDown：
   - 最壞情況要往下走樹高 → O(log n)

2. buildHeap (Bottom-up)：
   - 從 n/2 個非葉子節點往上做 heapifyDown
   - 雖然看似 n/2 * O(log n)，
     但因為大部分節點高度低，不需要走到 log n
   - 整體攤銷下來為 O(n)

3. 輸出整個陣列：
   - O(n)

=> 總時間複雜度：O(n)
=> 空間複雜度：O(1) （原地建堆）
--------------------------------------
*/
