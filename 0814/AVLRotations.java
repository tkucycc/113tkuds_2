public class AVLRotations {
    
    // 右旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        // 執行旋轉
        x.right = y;
        y.left = T2;
        
        // 更新高度
        y.updateHeight();
        x.updateHeight();
        
        return x; // 新的根節點
    }
    
    // 左旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        // 執行旋轉
        y.left = x;
        x.right = T2;
        
        // 更新高度
        x.updateHeight();
        y.updateHeight();
        
        return y; // 新的根節點
    }
        public static void main(String[] args) {
            // 測試右旋
            AVLNode root = new AVLNode(30);
            root.left = new AVLNode(20);
            root.left.left = new AVLNode(10);
            root.updateHeight();
            root.left.updateHeight();
            System.out.println("右旋前根節點: " + root.data);
            root = rightRotate(root);
            System.out.println("右旋後根節點: " + root.data);

            // 測試左旋
            AVLNode root2 = new AVLNode(10);
            root2.right = new AVLNode(20);
            root2.right.right = new AVLNode(30);
            root2.updateHeight();
            root2.right.updateHeight();
            System.out.println("左旋前根節點: " + root2.data);
            root2 = leftRotate(root2);
            System.out.println("左旋後根節點: " + root2.data);
        }
}
