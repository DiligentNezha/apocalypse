package com.apocalypse.example.leetcode;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/14
 */
public class Solution26 {
    public static void main(String[] args) {
        System.out.println(removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}));
        System.out.println(removeDuplicates(new int[]{1, 1, 2}));
    }

    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int currIndex = 0;
        int nextIndex = currIndex + 1;
        int i = 0;
        while (i++ < nums.length - 1) {
            if (nums[currIndex] == nums[nextIndex]) {
                nextIndex++;
            } else {
                nums[currIndex + 1] = nums[nextIndex++];
                currIndex++;
            }
        }
        return currIndex + 1;
    }
}
