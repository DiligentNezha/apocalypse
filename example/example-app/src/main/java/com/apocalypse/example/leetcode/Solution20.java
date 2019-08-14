package com.apocalypse.example.leetcode;

import java.util.Stack;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/14
 */
public class Solution20 {
    public static void main(String[] args) {
        System.out.println(isValid("()"));
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("(]"));
        System.out.println(isValid("([)]"));
        System.out.println(isValid("{[]}"));
        System.out.println(isValid("{{)}"));
    }

    public static boolean isValid(String s) {
        if (s.length() % 2 == 1) {
            return false;
        }

        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            if (stack.empty()) {
                stack.push(currentChar);
            } else {
                Character peek = stack.peek();
                if ('(' == peek && ')' == currentChar) {
                    stack.pop();
                } else if ('{' == peek && '}' == currentChar) {
                    stack.pop();
                } else if ('[' == peek && ']' == currentChar) {
                    stack.pop();
                } else {
                    stack.push(currentChar);
                }
            }
        }
        return stack.empty();
    }
}
