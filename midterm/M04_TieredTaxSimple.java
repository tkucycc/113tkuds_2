import java.util.Scanner;

public class M04_TieredTaxSimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   // 輸入筆數
        int[] incomes = new int[n];
        int totalTax = 0;

        for (int i = 0; i < n; i++) {
            incomes[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            int tax = calculateTax(incomes[i]);
            System.out.println("Tax: " + tax);
            totalTax += tax;
        }

        int average = totalTax / n;
        System.out.println("Average: " + average);
    }

    // 計算稅額
    public static int calculateTax(int income) {
        int tax = 0;

        if (income <= 120000) {
            tax = income * 5 / 100;
        } else if (income <= 500000) {
            tax = 120000 * 5 / 100 +
                  (income - 120000) * 12 / 100;
        } else if (income <= 1000000) {
            tax = 120000 * 5 / 100 +
                  (500000 - 120000) * 12 / 100 +
                  (income - 500000) * 20 / 100;
        } else {
            tax = 120000 * 5 / 100 +
                  (500000 - 120000) * 12 / 100 +
                  (1000000 - 500000) * 20 / 100 +
                  (income - 1000000) * 30 / 100;
        }

        return tax;
    }
}

/*
-----------------------------------------
複雜度分析 (Complexity Analysis):

Time Complexity:
- 輸入 n 筆收入 → O(n)
- 每筆收入計算稅額 → O(1) (因為稅率區間固定)
=> 總時間複雜度: O(n)

Space Complexity:
- 使用一個長度為 n 的陣列存收入 → O(n)
- 其他變數為常數空間 → O(1)
=> 總空間複雜度: O(n)
-----------------------------------------
*/
