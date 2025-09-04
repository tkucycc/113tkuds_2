import java.util.*;

public class LC26_RemoveDuplicates_Scores {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // 呼叫去重函式
        int len = removeDuplicates(arr);

        // 輸出新長度
        System.out.println(len);

        // 輸出前段結果
        for (int i = 0; i < len; i++) {
            System.out.print(arr[i]);
            if (i < len - 1) System.out.print(" ");
        }
        System.out.println();
    }

    /**
     * 去重函式：返回新長度，陣列前 len 個為結果
     */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;

        // write 指標指向下一個可寫入位置
        int write = 1;

        // 從第 2 個元素開始遍歷
        for (int read = 1; read < nums.length; read++) {
            // 若當前讀到的值與前一個不同，則寫入
            if (nums[read] != nums[read - 1]) {
                nums[write] = nums[read];
                write++;
            }
        }

        return write;
    }
}

/*
解題思路：
1. 已知輸入陣列是「升序排序」的，所以重複值一定相鄰。
2. 使用雙指針：
   - read：逐一遍歷元素。
   - write：指向可以存放「不重複數值」的位置。
3. 每當 nums[read] != nums[read-1]，表示出現新值，將它寫入 nums[write]，並讓 write++。
4. 最終 write 的值就是新長度，前 write 個元素就是去重結果。
5. 時間複雜度 O(n)，空間 O(1)，僅用一個額外指標。
*/
