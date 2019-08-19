package com.apocalypse.example.leetcode.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/19
 */
public class Solution107 {

    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(1);
        TreeNode left1 = new TreeNode(2);
        TreeNode right1 = new TreeNode(3);

        root1.left = left1;
        root1.right = right1;

        System.out.println(levelOrderBottom(root1));
    }

    public static List<List<Integer>> levelOrderBottom(TreeNode root) {

        List<List<Integer>> res = new ArrayList<>();
        levelOrderBottom(root, res, 0);
        Collections.reverse(res);
        return res;
    }

    public static List<List<Integer>> levelOrderBottom(TreeNode root, List<List<Integer>> list, int height) {

        if (root == null) {
            return null;
        }
        if (list.size() <= height) {
            list.add(new ArrayList<>());
        }

        list.get(height).add(root.val);
        height++;

        levelOrderBottom(root.left, list, height);
        levelOrderBottom(root.right, list, height);

        return list;
    }
}
