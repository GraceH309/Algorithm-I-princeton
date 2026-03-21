import java.util.ArrayList;
import java.util.List;

// 二叉树节点定义
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class BSTMorrisTraversal {
    /**
     * 利用Morris算法实现BST的中序遍历（O(1)额外空间）
     * @param root BST根节点
     * @return 中序遍历结果（BST中序遍历为有序序列）
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode cur = root;
        TreeNode pre = null; // 记录当前节点的中序前驱

        while (cur != null) {
            if (cur.left == null) {
                // 1. 左子树为空，直接访问当前节点，移至右子树
                result.add(cur.val);
                cur = cur.right;
            } else {
                // 2. 左子树非空，找到前驱节点（左子树最右节点）
                pre = cur.left;
                // 循环找到左子树的最右节点（排除已建立的线索）
                while (pre.right != null && pre.right != cur) {
                    pre = pre.right;
                }

                if (pre.right == null) {
                    // 2.1 前驱右指针为空：建立线索，移至左子树
                    pre.right = cur;
                    cur = cur.left;
                } else {
                    // 2.2 前驱右指针指向当前节点：左子树已遍历完，恢复树结构并访问当前节点
                    pre.right = null; // 恢复原始树结构
                    result.add(cur.val);
                    cur = cur.right;
                }
            }
        }
        return result;
    }

    // 测试用例
    public static void main(String[] args) {
        // 构建示例BST：
        //        4
        //      /   \
        //     2     5
        //    / \
        //   1   3
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        BSTMorrisTraversal traversal = new BSTMorrisTraversal();
        List<Integer> result = traversal.inorderTraversal(root);
        System.out.println("BST中序遍历结果（有序）：" + result); // 输出：[1, 2, 3, 4, 5]
    }
}