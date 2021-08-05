package com.zh.FileSearch;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @ClassName FileSearch
 * @Description 利用java代码实现文件检索(广度优先遍历 ( 队列)和深度优先遍历(递归))
 * @Author Zhangyuhan
 * @Date 2021/8/1
 * @Version 1.0
 */

public class FileSearch {
    public static void main(String[] args) {
        // 本次使用广度优先遍历（队列）
        String rootPath = "D:\\Work\\Study\\github_Takatsukun_study_rep";

        // File类(既可以表示文件夹又可以表示txt,mp3等文件)
        File rootFile = new File(rootPath);

        // 获取到根目录下的子文件(注意不是遍历所有文件，只是找到当前层的所有文件夹和文件)
        // File[] files = rootFile.listFiles();

        // System.out.println(files.length);

        selectFile(rootFile);
        // selectFile1(rootFile);
    }

    /**
     * 传入根节点，把该节点下及其子节点下所有的文件查询到，并打印(广度优先)
     *
     * @param rootFile
     */
    public static void selectFile(File rootFile) {
        // 创建队列的对象
        Queue<File> queue = new LinkedList<>();
        // 把根节点存放到队列中
        queue.add(rootFile);

        // 编写while循环
        while (!queue.isEmpty()) {
            // 队列不为空，while会一直循环

            // 从队列中取出元素
            File file = queue.poll();

            // 获取当前file对象下所有的子节点
            File[] files = file.listFiles();


            for (File tempFile : files) {
                if (tempFile.isFile()) {
                    // 是文件，打印名称
                    System.out.println(tempFile.getName());
                    continue;
                }

                // 是文件夹，那么继续入队
                queue.add(tempFile);
            }
        }
    }

    /**
     * 递归方式实现(非常不推荐,因为太耗内存,并且效率很低)
     *
     * @param rootFile
     */
    public static void selectFile1(File rootFile) {
        File[] files = rootFile.listFiles();
        for (File tempFile : files) {
            if (tempFile.isFile()) {
                System.out.println(tempFile.getName());
                continue;
            }
            selectFile1(tempFile);
        }
    }
}
