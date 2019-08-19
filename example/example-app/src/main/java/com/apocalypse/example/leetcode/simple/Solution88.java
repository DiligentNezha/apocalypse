package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/19
 */
public class Solution88 {

    public static void main(String[] args) {
        merge(new int[]{1, 2, 3, 0, 0, 0}, 3, new int[]{2, 5, 6}, 3);
        System.out.println("hello");
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int len = m + n;
        int[] result = new int[len];

        int head1 = 0;
        int head2 = 0;
        int i = 0;
        while (head1 < m || head2 < n) {
            if (head1 < m && head2 < n) {
                if (nums1[head1] <= nums2[head2]) {
                    result[i++] = nums1[head1++];
                } else {
                    result[i++] = nums2[head2++];
                }
            } else {
                if (head1 >= m) {
                    result[i++] = nums2[head2++];
                } else {
                    result[i++] = nums1[head1++];
                }
            }
        }
        System.arraycopy(result, 0, nums1, 0, len);
    }
}
