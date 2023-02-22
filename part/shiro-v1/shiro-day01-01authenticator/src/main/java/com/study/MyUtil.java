package com.study;

import java.io.File;

public class MyUtil {
    public static void main(String[] args) {
        String fileDir = "/Users/zhangyuhan/Work/study/part/shiro-v1/springboot-shiro-parent";
        removePrefix(new File(fileDir));

    }

    public static void removePrefix(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().startsWith("._")) {
                file.delete();
            } else {
                removePrefix(file);
            }
        }
    }
}
