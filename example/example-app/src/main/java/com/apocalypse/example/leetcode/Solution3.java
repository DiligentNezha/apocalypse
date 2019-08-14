package com.apocalypse.example.leetcode;

import java.util.Arrays;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/13
 */
public class Solution3 {

    public static void main(String[] args) {
        int[] nums1 = new int[]{3};
        int[] nums2 = new int[]{-2, -1};
        System.out.println(test(nums1, nums2));

    }

    public static double test(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length + nums2.length];
        int head1 = 0;
        int head2 = 0;
        if (nums1.length == 0) {
            result = nums2;
        }
        if (nums2.length == 0) {
            result = nums1;
        }

        if (nums1.length != 0 && nums2.length != 0) {
            while (head1 < nums1.length || head2 < nums2.length) {
                if (nums1[head1] <= nums2[head2]) {
                    result[head1] = nums1[head1];
                    head1++;
                }
                if (nums1[head1] > nums2[head2]) {
                    result[head2] = nums2[head2];
                    head2++;
                }
            }
        }
        int middleIndex = result.length / 2;
        double middleValue;
        if (result.length % 2 == 0) {
            middleValue = (result[middleIndex - 1] + result[middleIndex]) / 2.0;
        } else {
            middleValue = result[middleIndex];
        }

        return middleValue;
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length + nums2.length];
        int head1 = 0;
        int head2 = 0;
        int tail1 = nums1.length - 1;
        int tail2 = nums2.length - 1;
        int min;
        int max;
        int head = 0;
        int tail = result.length - 1;

        if (nums1.length == 0) {
            result = nums2;
        }
        if (nums2.length == 0) {
            result = nums1;
        }

        int sum = 0;
        while ((head1 <= tail1 || head2 <= tail2)) {
            int tempHead1 = head1;
            int tempHead2 = head2;
            int tempTail1 = tail1;
            int tempTail2 = tail2;

            if (head1 >= nums1.length || head2 >= nums2.length || tail1 < 0 || tail2 < 0) {
                break;
            }

            // 寻找最小值
            if (nums1[head1] <= nums2[head2]) {
                min = nums1[head1];
                head1++;
            } else {
                min = nums2[head2];
                head2++;
            }
            result[head++] = min;

            // 寻找最大值
            if (nums1[tail1] >= nums2[tail2]) {
                max = nums1[tail1];
                tail1--;
            } else {
                max = nums2[tail2];
                tail2--;
            }
            result[tail--] = max;

            if (tempHead1 <= tempTail1) {
                sum += min;
            }
            if (tempHead2 <= tempTail2) {
                sum += max;
            }
        }
        int middleIndex = result.length / 2;
        double middleValue;
        if (result.length % 2 == 0) {
            middleValue = (result[middleIndex - 1] + result[middleIndex]) / 2.0;
        } else {
            middleValue = result[middleIndex];
        }

        return middleValue;
    }
}
