package com.apocalypse.example.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/13
 */
public class Solution1 {
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 5};
        int[] result = twoSum1(nums, 9);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    public static int[] twoSum1(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == target - nums[j]) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        throw new IllegalArgumentException("no expect data");
    }

    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[]{i, map.get(complement)};
            }
        }
        throw new IllegalArgumentException("no expect data");
    }

    public static int[] twoSum3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("no expect data");
    }
}
