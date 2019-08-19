package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/16
 */
public class Solution70 {

    public static void main(String[] args) {
        System.out.println(climbStairs(1) + ":" + climbStairs1(1));
        System.out.println(climbStairs(2) + ":" + climbStairs1(2));
        System.out.println(climbStairs(3) + ":" + climbStairs1(3));
        System.out.println(climbStairs(4) + ":" + climbStairs1(4));
        System.out.println(climbStairs(5) + ":" + climbStairs1(5));
        System.out.println(climbStairs(6) + ":" + climbStairs1(6));
        System.out.println(climbStairs(7) + ":" + climbStairs1(7));
        System.out.println(climbStairs(44) + ":" + climbStairs1(44));

    }

    public static int climbStairs(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        // 最后一次爬两个台阶 + 最后一次爬一个台阶
        return climbStairs(n - 2) + climbStairs(n - 1);
    }

    public static int climbStairs1(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int f1 = 1;
        int f2 = 2;
        int curr = 0;
        for (int i = 3; i <= n ; i++) {
            curr = f1 + f2;
            f1 = f2;
            f2 = curr;
        }
        return curr;
    }
}
