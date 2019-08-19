package com.apocalypse.example.leetcode.medium;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/14
 */
public class Solution12 {
    public static void main(String[] args) {
        List<String> result = new ArrayList<>(3999);
        IntStream.rangeClosed(1, 3999).forEach(value -> result.add(intToRoman(value)));
        result.stream().sorted((o1, o2) -> {
            if (o1.length() > o2.length()) {
                return 1;
            } else if (o1.length() < o2.length()) {
                return -1;
            } else {
                return 0;
            }
        }).forEach(System.out::println);
    }

    public static String intToRoman(int num) {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            if (num < 0) {
                break;
            }
            Integer base = iterator.next();
            if (num / base > 0) {
                String romanVal = String.join("", Collections.nCopies(num / base, map.get(base)));
                sb.append(romanVal);
                num %= base;
            }
        }
        return sb.toString();
    }
}
