class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
import java.util.Stack;

public class Solution {
    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev = null; // 记录中序遍历的前一个节点
        TreeNode curr = root;
        
        while (curr != null || !stack.isEmpty()) {
            // 遍历左子树，全部入栈
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            // 处理当前节点（中序遍历的核心步骤）
            curr = stack.pop();
            // 若当前节点 ≤ 前一个节点，不是 BST
            if (prev != null && curr.val <= prev.val) return false;
            prev = curr; // 更新前一个节点
            
            // 遍历右子树
            curr = curr.right;
        }
        return true;
    }
}