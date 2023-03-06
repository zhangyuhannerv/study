package com.zh.util.fileOperation;

import java.io.File;
import java.io.IOException;

public class FileCreate {
    public static void createFile1(File file) {
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
