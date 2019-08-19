package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/15
 */
public class Solution38 {
    public static void main(String[] args) {
        System.out.println(countAndSay(1));
        System.out.println(countAndSay(2));
        System.out.println(countAndSay(3));
        System.out.println(countAndSay(4));
        System.out.println(countAndSay(5));

    }

    public static String countAndSay(int n) {
        if (n == 1) {
            return "1";
        } else {
            String str =  countAndSay(n-1);
            char temp = str.charAt(0);
            int count = 0;
            String result = "";
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == temp) {
                    count++;
                } else {
                    result = result + count + temp;
                    temp = str.charAt(i);
                    count = 1;
                }
                if (i == str.length() - 1) {
                    result = result + count + temp;
                }
            }
            return result;
        }
    }
}
