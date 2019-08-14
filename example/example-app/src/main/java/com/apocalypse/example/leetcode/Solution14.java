package com.apocalypse.example.leetcode;

import org.springframework.util.comparator.Comparators;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/14
 */
public class Solution14 {

    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"", "flow", "flight"}));
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        StringBuilder prefix = new StringBuilder();
        List<String> list = Arrays.stream(strs).sorted((o1, o2) -> {
            if (o1.length() > o2.length()) {
                return 1;
            } else if (o1.length() < o2.length()) {
                return -1;
            } else {
                return 0;
            }
        }).collect(Collectors.toList());
        String first = list.get(0);
        int length = first.length();
        boolean stop = false;
        for (int i = 0; i < length; i++) {
            char currentChar = first.charAt(i);
            for (int j = 1; j < list.size(); j++) {
                if (currentChar != list.get(j).charAt(i)) {
                    stop = true;
                    break;
                }
            }
            if (!stop) {
                prefix.append(currentChar);
            }
        }
        return prefix.toString();
    }
}
