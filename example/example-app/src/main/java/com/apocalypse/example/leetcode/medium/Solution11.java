package com.apocalypse.example.leetcode.medium;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/16
 */
public class Solution11 {
    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        System.out.println(maxArea1(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    public static int maxArea(int[] height) {
        int max = 0;

        for (int i = 0; i < height.length - 1; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int min = Math.min(height[i], height[j]);
                int temp = min * (j - i);
                if (temp > max) {
                    max = temp;
                }
            }
        }
        return max;
    }

    public static int maxArea1(int[] height) {
        int max = 0;
        int head = 0;
        int tail = height.length - 1;
        while (head < tail) {
            int temp = (tail - head) * Math.min(height[head], height[tail]);
            if (temp > max) {
                max = temp;
            }
            if (height[head] < height[tail]) {
                head++;
            } else {
                tail--;
            }
        }
        return max;
    }

}
