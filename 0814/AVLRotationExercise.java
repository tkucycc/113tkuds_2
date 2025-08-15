public class AVLRotationExercise {
    public static void main(String[] args) {
        AVLTree tree;

        // 測試 1：右旋 (LL)
        System.out.println("=== 測試 1：右旋 (LL) ===");
        tree = new AVLTree();
        tree.insert(30);
        tree.insert(20);
        tree.insert(10); // LL
        System.out.println("高度: " + tree.height());
    System.out.println("是 AVL？ " + tree.isValidAVL());

        // 測試 2：左旋 (RR)
        System.out.println("\n=== 測試 2：左旋 (RR) ===");
        tree = new AVLTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30); // RR
        System.out.println("高度: " + tree.height());
    System.out.println("是 AVL？ " + tree.isValidAVL());

        // 測試 3：左右旋 (LR)
        System.out.println("\n=== 測試 3：左右旋 (LR) ===");
        tree = new AVLTree();
        tree.insert(30);
        tree.insert(10);
        tree.insert(20); // LR
        System.out.println("高度: " + tree.height());
    System.out.println("是 AVL？ " + tree.isValidAVL());

        // 測試 4：右左旋 (RL)
        System.out.println("\n=== 測試 4：右左旋 (RL) ===");
        tree = new AVLTree();
        tree.insert(10);
        tree.insert(30);
        tree.insert(20); // RL
        System.out.println("高度: " + tree.height());
    System.out.println("是 AVL？ " + tree.isValidAVL());
    }
}
