package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/15
 */
public class Solution35 {

    public static void main(String[] args) {
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 5));
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 2));
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 7));
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 0));

    }

    public static int searchInsert(int[] nums, int target) {
        int i = 0;
        do {
            if (target == nums[i] || target < nums[i]) {
                return i;
            }
        } while (++i < nums.length);
        return i;
    }
}
