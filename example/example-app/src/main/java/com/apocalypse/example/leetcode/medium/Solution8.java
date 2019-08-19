package com.apocalypse.example.leetcode.medium;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/16
 */
public class Solution8 {

    public static void main(String[] args) {
        System.out.println(myAtoi("+-2"));
        System.out.println(myAtoi(" -42"));
        System.out.println(myAtoi(" 420"));
        System.out.println(myAtoi("-91283472332"));
        System.out.println(myAtoi(""));

    }

    public static int myAtoi(String str) {
        //清空字符串开头和末尾空格（这是trim方法功能，事实上我们只需清空开头空格）
        str = str.trim();
        //java正则表达式
        Pattern p = Pattern.compile("^[\\+\\-]?\\d+");
        Matcher m = p.matcher(str);
        int value = 0;
        //判断是否能匹配
        if (m.find()){
            //字符串转整数，溢出
            try {
                value = Integer.parseInt(str.substring(m.start(), m.end()));
            } catch (Exception e){
                //由于有的字符串"42"没有正号，所以我们判断'-'
                value = str.charAt(0) == '-' ? Integer.MIN_VALUE: Integer.MAX_VALUE;
            }
        }
        return value;
    }
}
