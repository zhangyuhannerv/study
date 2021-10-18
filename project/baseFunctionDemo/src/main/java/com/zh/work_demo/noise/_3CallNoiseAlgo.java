package com.zh.work_demo.noise;

import java.io.IOException;

/**
 * @ClassName _3CallNoiseAlgo
 * @Description 调用python算法解析噪声文件
 * @Author Zhangyuhan
 * @Date 2021/10/18
 * @Version 1.0
 */
public class _3CallNoiseAlgo {
    public static void callNoiseAlgo(String algoPath, String sourceFilePath, String sourceFileName) {
        String[] arguments = new String[]{"python", algoPath, sourceFilePath, sourceFileName};
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            process.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
