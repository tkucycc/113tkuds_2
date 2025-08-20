import java.util.Scanner;

public class M06_PalindromeClean {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        // 雙指標檢查是否為回文
        if (isPalindrome(s)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while (left < right) {
            // 跳過非字母
            while (left < right && !Character.isLetter(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetter(s.charAt(right))) {
                right--;
            }

            // 比對字母（轉小寫）
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}

