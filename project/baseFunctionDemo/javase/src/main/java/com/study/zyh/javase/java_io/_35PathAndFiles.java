package com.study.zyh.javase.java_io;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class _35PathAndFiles {
    public static class CopyFileVisitor extends SimpleFileVisitor<Path> {
        private final Path sourceDir;
        private final Path targetDir;

        public CopyFileVisitor(Path sourceDir, Path targetDir) {
            this.sourceDir = sourceDir;
            this.targetDir = targetDir;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Path targetPath = targetDir.resolve(sourceDir.relativize(dir));
            try {
                Files.copy(dir, targetPath);
            } catch (FileAlreadyExistsException e) {
                if (!Files.isDirectory(targetPath)) {
                    throw e;
                }
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Path targetPath = targetDir.resolve(sourceDir.relativize(file));
            Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
        }
    }

    /**
     * 拷贝文件夹
     * 将目录aaa里的所有子文件拷贝到目录bbb里
     * bbb如果没有回自动创建
     */
    @Test
    public void copyDir() {
        // 复制一个目录
        Path sourceDirPath = Paths.get("/Users/zhangyuhan/Downloads/aaa");
        Path targetDirPath = Paths.get("/Users/zhangyuhan/Downloads/bbb");
        try {
            //CopyFileVisitor是需要自己实现的
            Files.walkFileTree(sourceDirPath, new CopyFileVisitor(sourceDirPath, targetDirPath));
            System.out.println("目录复制成功");
        } catch (IOException e) {
            System.out.println("目录复制失败：" + e.getMessage());
        }
    }
}
