package com.apocalypse.example.leetcode;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/14
 */
public class Solution7 {
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(reverse(Integer.MAX_VALUE));
        System.out.println(reverse(Integer.MIN_VALUE));
        System.out.println(reverse(123));
        System.out.println(reverse(-123));
        System.out.println(reverse(0));
        System.out.println(reverse(120));
        System.out.println(reverse(1534236469));
    }

    public static int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }
            rev = rev * 10 + pop;
        }
        return rev;
    }

}
