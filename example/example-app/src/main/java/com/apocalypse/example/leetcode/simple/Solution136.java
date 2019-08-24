package com.apocalypse.example.leetcode.simple;

import cn.hutool.core.collection.IterUtil;
import com.alibaba.druid.sql.visitor.functions.If;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/19
 */
public class Solution136 {



    public static void main(String[] args) throws IOException {
        new Solution136().files(new File("D:\\8918\\sourcecode\\opensource\\passcloud"));
    }

    void files(File file) {
        if (file.isFile() && file.canRead() && file.canWrite() && file.getName().endsWith(".java")) {
            process(file.getAbsolutePath());
        } else if (file.isDirectory() && !file.getName().startsWith(".")){
            for (File subFile : file.listFiles()) {
                files(subFile);
            }
        }
    }

    private void process(String path) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines.get(0).contains("package")) {
            return;
        }
        int firstCommentStart = -1;
        int firstCommentEnd = -1;
        int secondCommentStart = -1;
        int secondCommentEnd = -1;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.trim().startsWith("/*")) {
                if (firstCommentStart == -1) {
                    firstCommentStart = i;
                    continue;
                } else if (secondCommentStart == -1) {
                    secondCommentStart = i;
                    continue;
                }
            }
            if (line.trim().startsWith("*/")) {
                if (firstCommentEnd == -1) {
                    firstCommentEnd = i;
                    continue;
                } else if (secondCommentEnd == -1) {
                    secondCommentEnd = i;
                } else {
                    break;
                }
            }
        }

        if (firstCommentEnd + 1 > secondCommentStart) {
            return;
        }
        List<String> newLines = new ArrayList<>();
        newLines.addAll(lines.subList(firstCommentEnd + 1, secondCommentStart));
        newLines.addAll(lines.subList(secondCommentEnd + 1, lines.size()));
        if (newLines.get(0).trim().isEmpty()) {
            List<String> temp = newLines.subList(1, newLines.size());
            newLines = new ArrayList<>();
            newLines.addAll(temp);
        }
        try {
            Files.write(Paths.get(path), IterUtil.asIterable(newLines.iterator()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main1(String[] args) {
        System.out.println(singleNumber(new int[]{4, 1, 2, 1, 2}));
        System.out.println(singleNumber1(new int[]{4, 1, 2, 1, 2}));
        System.out.println(singleNumber1(new int[]{2, 2, 1}));
    }

    public static int singleNumber(int[] nums) {
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 1; i += 2) {
            if (nums[i] != nums[i + 1]) {
                return nums[i];
            }
        }
        return nums[nums.length - 1];
    }

    public static int singleNumber1(int[] nums) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            result = result ^ nums[i];
        }
        return result;
    }
}
