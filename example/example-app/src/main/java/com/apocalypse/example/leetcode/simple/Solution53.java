package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/15
 */
public class Solution53 {

    public static void main(String[] args) {

        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(maxSubArray2(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }


    public static int maxSubArray(int[] nums) {
        int result = nums[0];
        int sum = 0;
        for(int num: nums) {
            if(sum > 0) {
                sum += num;
            } else {
                sum = num;
            }
            result = Math.max(result, sum);
        }
        return result;
    }

    public static int maxSubArray2(int[] nums) {
        int endValue = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            endValue = Math.max(nums[i], endValue + nums[i]);
            max = Math.max(max, endValue);
        }
        return max;
    }

}
