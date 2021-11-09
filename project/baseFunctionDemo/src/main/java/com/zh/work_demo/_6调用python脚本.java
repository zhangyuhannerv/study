package com.zh.work_demo;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName _6调用python脚本
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/11/9
 * @Version 1.0
 */
public class _6调用python脚本 {
    @Test
    public void test() {
        String scriptPath1 = "D:\\pythoncode\\InteriorNoise\\octave_3Bpt.py";// 1_3倍频程脚本
        String scriptPath2 = "D:\\pythoncode\\InteriorNoise\\fft_Ppt_1s.py";// 频谱脚本1s
        String scriptPath3 = "D:\\pythoncode\\InteriorNoise\\fft_Ppt.py";// 频谱脚本
        String filePath = "D:\\Work\\workProject\\dtjc文件\\5车内噪声\\真数据文件\\20191211呼和浩特地铁1号线平稳性\\原始数据\\20191211呼和浩特地铁1号线平稳性1#26.sts";
        String start = "100";
        String end = "120";
        String fs = "32768";
        List<List<Double>> res = getAllRes(scriptPath1, filePath, start, end, fs);
        System.out.println(res.size());
        System.out.println("***");
        System.out.println(res);
    }

    /**
     * @param start 开始生效时间
     * @param end   生效结束时间
     * @param fs    频率
     *              如果变成三维频谱图，可以将开始与结束时间相差1s，循环使用该方法
     * @return
     */
    public static List<List<Double>> getAllRes(String scriptPath, String filePath, String start, String end, String fs) {

        //本地python路径
        String res = "-1.0";
        List<List<Double>> result = new ArrayList<>();
        BufferedReader in = null;
        try {
            Process process = Runtime.getRuntime().exec("python " + scriptPath + " " + filePath + " " + start + " " + end + " " + fs);
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((res = in.readLine()) != null) {
                res = res.substring(1, res.length() - 1);
                String[] a = res.split(",");
                List<Double> ls = new ArrayList<>();
                for (String s : a) {
                    ls.add(Double.parseDouble(s));
                }
                result.add(ls);
            }
            int re = process.waitFor();
            System.out.println(re == 1 ? "----状态码1----运行失败" : "----状态码0----运行成功");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
