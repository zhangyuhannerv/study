package com.zh.wordDemo;

import com.mathworks.toolbox.javabuilder.MWNumericArray;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class MatLabSpsjk {

    public static void main(String[] args) {
        String filePath = "/Volumes/ESD-USB/新的水平三角坑/DXJCXS_JICHANG_CAOQIAO_27032023_145929_2.txt";
        BufferedReader bufferedReader = null;
        int i = -1;
        String[] str;
        String line;//按行读取的文本变量
        Map<String, Integer> col = new HashMap<>();

        List<Double[]> oldSp = new ArrayList<>();
        List<Double[]> oldSjk = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(filePath)), "gbk"));
            while ((line = bufferedReader.readLine()) != null) {
                i++;
                str = line.split(",");
                if (i == 0) {// 跳过第一行表头
                    for (int j = 0; j < str.length; j++) {
                        if (str[j].contains("(")) {
                            str[j] = str[j].substring(0, str[j].indexOf("("));
                        }
                        if (str[j].contains("（")) {
                            str[j] = str[j].substring(0, str[j].indexOf("（"));
                        }
                        col.put(str[j], j);
                    }
                    continue;
                }

                Double sp = getTdValue(str, col, "水平", "Xlvl");
                oldSp.add(new Double[]{sp});
                Double sjk = getTdValue(str, col, "三角坑", "Warp_1");
                oldSjk.add(new Double[]{sjk});
            }

            List<Double> newSp = dealForSPSJKForMatelab(oldSp);
            List<Double> newSjk = dealForSPSJKForMatelab(oldSjk);
            System.out.println("计算完成");
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (bufferedReader != null) {
                try {
                    // 在关闭处理流时，只需要关闭外层流（处理流：bufferedReader）即可，因为底层会自动的关闭节点流。这里的节点流就是FileReader
                    bufferedReader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }


    private static List<Double> dealForSPSJKForMatelab(List<Double[]> resOldArray) {
        long timeAIntegral1 = new Date().getTime();
        System.out.println("矫正开始,数据量：" + resOldArray.size());
        List<Double> res = new ArrayList<>();
        // 调⽤matelbajar包
        // 输⼊参数
        //data：要消除缓和曲线偏差的数据
        //Nstd=0.2;
        //NR=20;
        //MaxIter = 800; （这些为推荐参数）
        //Num IMF参与重构数量，8~10都⾏
        //输出参数
        //twitch_chonggou：消除缓和曲线偏差后的结果
        // 三⻆坑处理
        try {
            twitchease_emd.Class1 classSjk = new twitchease_emd.Class1();
            System.out.println("调⽤matlab算法");
            Object[] objectSjk =
                    classSjk.twitchease_emd(1, resOldArray.toArray(), Double.parseDouble("0.2"), Double.parseDouble("25"), Double.parseDouble("1000"), Double.parseDouble
                            ("7"));
            MWNumericArray outputSjk = (MWNumericArray) objectSjk[0];
            double[] doubleDataSjk = outputSjk.getDoubleData();
            res = Arrays.stream(doubleDataSjk).boxed().collect(Collectors.toList());
            classSjk.dispose();
            long timeAIntegral2 = new Date().getTime();
            System.out.println("-------------======= 调⽤matlab结束:通道处理，数据量" + doubleDataSjk.length + " =========----------：" + (timeAIntegral2
                    - timeAIntegral1) / 1000 + "秒");
            System.out.println("返回数据");
            doubleDataSjk = null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return res;
        }
        return res;
    }

    public static Double getTdValue(String[] str, Map<String, Integer> col, String chName, String enName) {
        if (col.get(chName) != null) {
            return Double.parseDouble(str[col.get(chName)]);
        } else if (col.get(enName) != null) {
            return Double.parseDouble(str[col.get(enName)]);
        } else {
            return 0d;
        }
    }
}
