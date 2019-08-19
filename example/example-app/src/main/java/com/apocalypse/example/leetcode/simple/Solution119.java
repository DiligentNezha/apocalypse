package com.apocalypse.example.leetcode.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/19
 */
public class Solution119 {

    public static void main(String[] args) {
        System.out.println(getRow(0));
        System.out.println(getRow(1));
        System.out.println(getRow(2));
        System.out.println(getRow(3));
        System.out.println(getRow(4));
        System.out.println(getRow(5));
        System.out.println(getRow(6));
        System.out.println(getRow(7));
        System.out.println(getRow(8));
    }

    public static List<Integer> getRow(int rowIndex) {
        rowIndex++;
        List<Integer> pre;
        List<Integer> curr = new ArrayList<>();
        List<Integer> first = Arrays.asList(1);
        List<Integer> second = Arrays.asList(1, 1);

        if (rowIndex == 1) {
            curr = first;
        }
        if (rowIndex == 2) {
            curr = second;
        }

        pre = second;
        for (int i = 3; i <= rowIndex; i++) {
            curr = new ArrayList<>();
            curr.add(1);
            for (int j = 1; j < i - 1; j++) {
                curr.add(pre.get(j - 1) + pre.get(j));
            }
            curr.add(1);
            pre = curr;
        }

        return curr;
    }

}
