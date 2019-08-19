package com.apocalypse.example.leetcode.simple;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/15
 */
public class Solution58 {

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("hello")); //5
        System.out.println(lengthOfLastWord("hello ")); //5
        System.out.println(lengthOfLastWord("hello world")); //5
        System.out.println(lengthOfLastWord("hello world ")); //5
        System.out.println(lengthOfLastWord(" hello world ")); //5

    }

    public static int lengthOfLastWord(String s) {
        s = s.trim();
        int count = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ') {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
}
