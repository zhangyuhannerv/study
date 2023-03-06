package com.zh.util.hexoBlog;

import com.zh.util.fileOperation.FileCopy;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HexoBlogUtil {
    private static final String parentPath = "/Users/zhangyuhan/Work/study/note/";

    private static final String targetPath = "/Users/zhangyuhan/Work/hexo-blog/source/_posts/";

    private static int num = 0;

    public static final String COPYRIGHT_INFO = "The copyright of this article is owned by Zhang Yuhan, and it follows the CC BY-NC-SA 4.0 agreement. For reprinting, please attach the original source link and this statement";

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public void writeFrontMatter(File file, String title, List<String> categories, List<String> tags) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        bf.write("---");
        bf.newLine();
        // 写标题
        bf.write("title: '" + title + "'");
        bf.newLine();
        // 写日期
        bf.write("date: " + getDate());
        bf.newLine();
        // 写版权
        bf.write("copyright_info: " + COPYRIGHT_INFO);
        bf.newLine();
        // 写分类
        bf.write("categories: ");
        bf.newLine();
        for (int i = 0; i < categories.size(); i++) {
            if (i != categories.size() - 1) {
                bf.write("  - '" + categories.get(i) + "'");
                bf.newLine();
            }
        }
        // 写标签
        bf.write("tags: ");
        bf.newLine();
        for (String tag : tags) {
            bf.write("  - '" + tag + "'");
            bf.newLine();
        }

        bf.write("---");
        bf.newLine();
        bf.close();
    }

    public void writeContent(File sourceFile, File targetFile) throws IOException {
        FileCopy.copyFile1(sourceFile, targetFile, true);
    }

    public List<String> getCategoriesAndTags(File file) {
        String filePath = file.getAbsolutePath();
        filePath = filePath.replace(parentPath, "");
        String[] split = filePath.split(File.separator);
        List<String> list = new ArrayList<>();
        for (String s : split) {
            if (s.contains(".md")) {
                s = s.substring(0, s.lastIndexOf("."));
            }

            if (s.contains("Obsidian")) {
                s = s.replace("Obsidian", "");
            }


            if (s.indexOf("_") != -1) {
                s = s.substring(s.indexOf("_") + 1);
            }

            list.add(s);
        }
        return list;
    }

    public void transferNoteToBlog(File sourceFile, File targetFile) throws IOException {
        List<String> categoriesAndTags = getCategoriesAndTags(sourceFile);
        writeFrontMatter(targetFile, categoriesAndTags.get(categoriesAndTags.size() - 1), categoriesAndTags, categoriesAndTags);
        writeContent(sourceFile, targetFile);
        num++;
    }

    public void transfer(File dir) throws IOException {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().startsWith(".")) {
                continue;
            }
            if (file.isFile()) {
                String targetFilePath = file.getAbsolutePath().replace(parentPath, targetPath);
                targetFilePath = targetFilePath.replace("Obsidian", "");
                File targetFile = new File(targetFilePath);
                transferNoteToBlog(file, targetFile);
            } else {
                transfer(file);
            }
        }
    }

    public void listFileName(File dir, List<String> list) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                list.add(file.getName());
            } else if (!file.getName().startsWith(".")) {
                listFileName(file, list);
            }
        }
    }

    @Test
    public void start() {
        try {
            transfer(new File(parentPath));
            System.out.println("转换的数量：" + num);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkFile() {
        File sourceDir = new File(parentPath);
        File targetDir = new File(targetPath);
        List<String> sourceNames = new ArrayList<>();
        listFileName(sourceDir, sourceNames);
        List<String> targetNames = new ArrayList<>();
        listFileName(targetDir, targetNames);
        System.out.println(sourceNames.size());
        System.out.println(targetNames.size());

        Set<String> set = new HashSet<>();


        for (String sourceName : sourceNames) {
            if (!targetNames.contains(sourceName)) {
                System.out.println(sourceName);
            }

            if (set.contains(sourceName)) {
                System.out.println(sourceName);
            }
            set.add(sourceName);
        }
    }
}
