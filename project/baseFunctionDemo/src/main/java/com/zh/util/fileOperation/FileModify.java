package com.zh.util.fileOperation;


import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class FileModify {
    /**
     * 修改文件中的内容
     *
     * @param file        文件
     * @param target      被替换的字符串
     * @param replacement 替换字符串
     */
    public static void modifyFileContent(File file, String target, String replacement) {
        log.info("开始修改文件：{}, 替换 {} 为 {}", file.getName(), target, replacement);
        StringBuilder sb = new StringBuilder();
        //记录修改的行数
        int cnt = 0;
        //记录替换所在的行
        int rowLine = 0;
        //换行符
        String enter = System.getProperty("line.separator");

        //printWriter原本也想放在 try-with 中，少写点代码，
        //但是一个文件不能同时读写，pw 和 br 对同一个文件操作的结果时，文件的内容被清空！！！
        //不妨试下，将 pw 申明在 try-with 中，看下运行结果。
        PrintWriter pw = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            for (line = br.readLine(); line != null; line = br.readLine()) {
                rowLine++;
                if (line.contains(target)) {
                    line = line.replace(target, replacement);
                    log.info("替换所在行：{}", rowLine);
                    cnt++;
                }
                //数据暂存在 StringBuilder 中
                if (rowLine == 1) {
                    sb.append(line);
                } else {
                    sb.append(enter).append(line);
                }
            }
            pw = new PrintWriter(new FileWriter(file));
            pw.print(sb);
        } catch (FileNotFoundException e) {
            log.error("文件不存在！", e);
            System.exit(1);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        log.info("修改文件：{} 结束,一共替换{}行数据", file.getName(), cnt);
    }
}
