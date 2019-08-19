package com.apocalypse.example.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/16
 */
public class Solution15 {

    static List<List<Integer>> result = new ArrayList<>();
    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        int[] temp = new int[nums.length - 1];
        System.arraycopy(nums, 0, temp, 0, temp.length);
    }

    public List<List<Integer>> threeSum(int[] nums) {
        return null;
    }

    public static void test(int[] nums, int target) {
        if (nums.length < 3) {
            if (nums[0] + nums[1] == target * -1) {
                result.add(Arrays.asList(nums[0], nums[1], target));
            }
        }
        for (int i = nums.length - 1; i >= 3 ; i++) {
            int[] temp = new int[i];
            System.arraycopy(nums, 0, temp, 0, temp.length);
            test(temp, nums[i]);
//            result.add();
        }
    }
}
