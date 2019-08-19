package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/19
 */
public class Solution110 {
    boolean res = true;
    public static void main(String[] args) {

    }

    public boolean isBalanced(TreeNode root) {
        maxDepth(root);
        return res;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int left = maxDepth(root.left) + 1;
            int right = maxDepth(root.right) + 1;
            if (Math.abs(right - left) > 1) {
                res = false;
            }
            return Math.max(left, right);
        }
    }
}
