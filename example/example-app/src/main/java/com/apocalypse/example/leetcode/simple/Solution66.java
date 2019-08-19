package com.apocalypse.example.leetcode.simple;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/15
 */
public class Solution66 {

    public static void main(String[] args) {
        System.out.println(IntStream.of(plusOne(new int[]{1, 2, 3})).boxed().collect(Collectors.toList()));
        System.out.println(IntStream.of(plusOne(new int[]{4, 3, 2, 1})).boxed().collect(Collectors.toList()));
        System.out.println(IntStream.of(plusOne(new int[]{4, 3, 2, 9})).boxed().collect(Collectors.toList()));
        System.out.println(IntStream.of(plusOne(new int[]{9, 9})).boxed().collect(Collectors.toList()));
    }

    public static int[] plusOne(int[] digits) {
        int[] result = new int[digits.length + 1];

        int carryOver = 1;
        int currentValue;
        for (int i = digits.length - 1; i >= 0; i--) {
            currentValue = digits[i] + carryOver;
            result[i + 1] = currentValue % 10;
            carryOver = currentValue / 10;
        }
        if (carryOver != 1) {
            int[] temp = new int[digits.length];
            System.arraycopy(result, 1, temp, 0, temp.length);
            result = temp;
        } else {
            result[0] = 1;
        }
        return result;
    }
}
