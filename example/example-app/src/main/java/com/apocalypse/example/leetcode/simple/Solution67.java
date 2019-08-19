package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/15
 */
public class Solution67 {

    public static void main(String[] args) {
        System.out.println(addBinary("11", "1"));
        System.out.println(addBinary("1010", "1011"));
    }

    public static String addBinary(String a, String b) {
        a = new StringBuilder(a).reverse().toString();
        b = new StringBuilder(b).reverse().toString();
        StringBuilder sb = new StringBuilder();
        int len = Math.max(a.length(), b.length());
        int currA;
        int currB;
        int carryOver = 0;
        int currentValue;
        for (int i = 0; i < len; i++) {
            currA = 0;
            currB = 0;
            if (i < a.length()) {
                currA = Integer.parseInt(a.charAt(i) + "");
            }
            if (i < b.length()) {
                currB = Integer.parseInt(b.charAt(i) + "");
            }

            currentValue = currA + currB + carryOver;

            sb.append(currentValue % 2);

            carryOver = currentValue / 2;
        }
        if (carryOver == 1) {
            sb.append(carryOver);
        }
        return sb.reverse().toString();
    }
}
