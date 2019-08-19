package com.apocalypse.example.leetcode.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/14
 */
public class Solution13 {
    public static void main(String[] args) {
        System.out.println(romanToInt("III"));
        System.out.println(romanToInt("MCMXCIV"));
    }

    public static int romanToInt(String s) {
        Map<String, Integer> specialMap = new HashMap<>();
        specialMap.put("IV", 4);
        specialMap.put("IX", 9);
        specialMap.put("XL", 40);
        specialMap.put("XC", 90);
        specialMap.put("CD", 400);
        specialMap.put("CM", 900);
        if (specialMap.containsKey(s)) {
            return specialMap.get(s);
        }

        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int sum = 0;
        int i = 0;
        while (i < s.length()) {
            String temp = "";
            if (i <= s.length() - 2) {
                temp = s.substring(i, i + 2);
            }
            if (specialMap.containsKey(temp)) {
                sum += specialMap.get(temp);
                i++;
            } else {
                sum += map.get(s.charAt(i));
            }
            i++;
        }
        return sum;
    }
}
