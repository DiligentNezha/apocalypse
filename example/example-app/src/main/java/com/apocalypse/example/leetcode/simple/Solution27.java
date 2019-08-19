package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/14
 */
public class Solution27 {
    public static void main(String[] args) {
        System.out.println(removeElement(new int[]{0,1,2,2,3,0,4,2}, 2));
        System.out.println(removeElement(new int[]{3,2,2,3}, 3));
        System.out.println(removeElement(new int[]{2}, 3));
    }

    public static int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int currIndex = 0;
        int nextIndex = 0;
        while (nextIndex < nums.length) {
            if (nums[nextIndex] != val) {
                nums[currIndex++] = nums[nextIndex++];
            } else {
                nextIndex++;
            }
        }
        return currIndex;
    }

}
