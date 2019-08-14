package com.apocalypse.example.leetcode;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/14
 */
public class Solution6 {

    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 3));
        System.out.println(convert("LEETCODEISHIRING", 3));
        System.out.println(convert("LEETCODEISHIRING", 4));
    }

    public static String convert(String s, int numRows) {
        int column = s.length();
        char[][] matrix = new char[numRows][column];
        boolean topToBottom = true;
        int i = 0;
        int x = 0;
        int y = 0;
        while (i < s.length()) {
            if (topToBottom) {
                for (x = 0; i < s.length() && x < numRows; x++) {
                    matrix[x][y] = s.charAt(i++);
                }
                topToBottom = false;
                y++;
            } else {
                for (x = numRows - 2; i < s.length() &&  x >= 1; x--) {
                    matrix[x][y++] = s.charAt(i++);
                }
                topToBottom = true;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (x = 0; x < numRows; x++) {
            for (y = 0; y < column; y++) {
                if (matrix[x][y] != 0) {
                    sb.append(matrix[x][y]);
                }
            }
        }
        return sb.toString();
    }

}
