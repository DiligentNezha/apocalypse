package com.apocalypse.example;

import java.io.*;
import java.util.*;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail jingkaihui@adpanshi.com
 */
public class Test {
    public static void main(String[] args) throws Exception {

//        wordStatistic("D:\\8918\\sourcecode\\hujin5\\if5_servers\\cash-mis\\src\\main\\webapp\\WEB-INF\\jsp");

        devNameCodeStatistic("D:\\8918\\sourcecode\\hujin5\\if5_recycle\\recycle-front\\src\\main\\java");
    }

    public static final void devNameCodeStatistic(String path) throws Exception {
        List<File> files = new ArrayList<>(512);

        dirFiles(files, new File(path), ".java");

        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\8918\\桌面\\DevName.txt"));
        for (File file : files) {
            LinkedHashMap<String, Integer[]> slh = oneFileDevName(file);
            for (Map.Entry<String, Integer[]> entry : slh.entrySet()) {
                StringBuilder sb = new StringBuilder(entry.getKey());
                sb.append("----")
                        .append(entry.getValue()[0])
                        .append(":")
                        .append(entry.getValue()[1]);
                bw.write(sb.toString());
                bw.newLine();
            }
        }
        bw.flush();
        bw.close();
    }

    public static final void wordStatistic(String path) throws Exception{
        List<File> files = new ArrayList<>(256);
        dirFiles(files, new File(path), ".jsp");

        Set<String> words = new HashSet<>(1024);
        int sum = 0;
        for (int i = 0; i < files.size(); i++) {
            Set<String> oneFileWords = oneFileWords(files.get(i));
            sum += oneFileWords.size();
            System.out.println(files.get(i).getName() + ":" + oneFileWords.size());
            words.addAll(oneFileWords);
        }
        System.out.println("去重前:" + sum);
        System.out.println("去重后:" + words.size());
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\8918\\桌面\\中文统计.txt"));
        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            bw.write(it.next());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static final LinkedHashMap<String, Integer[]> oneFileDevName(File file) throws Exception {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String str;
        LinkedHashMap<String, Integer[]> map = new LinkedHashMap<>(8);
        int line = 1;
        int num = 1;
        while ((str = br.readLine()) != null) {
            if (str.contains("DevNameEnum.")) {
                Integer[] range = {line, str.indexOf("DevNameEnum.")};
                map.put(file.getPath() + "-->" + num++, range);
            }
            line++;
        }
        br.close();
        fr.close();
        return map;
    }

    public static final Set<String> oneFileWords(File file) throws Exception{
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        Set<String> words = new HashSet<>();
        String str;
        int line = 1;
        while ((str = br.readLine()) != null) {
            String temp = str.trim();
            if (temp.contains("//") || temp.startsWith("<!--")) {
                continue;
            }
            boolean isChinese = false;
            int start = 0;
            int end;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (isChinese(str.charAt(i)) && isChinese == false) {
                    start = i;
                    isChinese = true;
                }
                if ((!isChinese(str.charAt(i)) && isChinese == true) || (isChinese(str.charAt(i)) && i == str.length() - 1)) {
                    end = i;
                    isChinese = false;
                    String word = str.substring(start, end);
                    sb.append(word).append(" ");
                    words.add(word);
                    System.out.println(line + ":" + sb);
                }
            }
            line++;
        }
        br.close();
        fr.close();
        return words;
    }

    public static final void dirFiles(List<File> files, File file, String... suffix) {
        if (file.isFile()) {
            files.add(file);
            return;
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {
                        return true;
                    }
                    if (pathname.isFile()) {
                        for (String aSuffix : suffix) {
                            if (pathname.getName().endsWith(aSuffix)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });
            for (File listFile : listFiles) {
                dirFiles(files, listFile, suffix);
            }
        }
    }

    // GENERAL_PUNCTUATION 判断中文的“号
    // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
    // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
    public static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;

    }
}
