package com.zh.work_demo;

import java.util.*;

public class _8计算时间值和获取箱型图数据 {
    public static void main(String[] args) {
        List<Double> list = Arrays.asList(1000d, 900d, 800d, 700d, 600d, 500d, 400d, 300d, 200d, 100d);
//        List<Double> list = Arrays.asList(1000d);
//        List<Double> list = new ArrayList<>();
        System.out.println(calBoxplotData(list));
    }

    public static Integer calTimeValue(String timeStr) {
        String[] arr = timeStr.split(":");
        String o1h = arr[0];
        String o1m = arr[1];
        if (o1h.length() == 1) {
            o1h = "0" + o1h;
        }
        if (o1m.length() == 1) {
            o1m = o1m + "0";
        }
        return Integer.parseInt(o1h) * 100 + Integer.parseInt(o1m);
    }

    public static Map<String, Object> calBoxplotData(List<Double> dataList) {
        int len = dataList.size() - 1;

        // 箱型图数据
        List<Double> boxplotData = new ArrayList<>();
        if (len == 0) {
            double singleData = dataList.get(len);
            Collections.addAll(boxplotData, singleData, singleData, singleData, singleData, singleData);
        } else if (len > 0) {
            // 从小到大排个序
            dataList.sort(Double::compareTo);
            Collections.addAll(boxplotData, dataList.get((int) (len * 0.1)), dataList.get((int) (len * 0.25)), dataList.get((int) (len * 0.5)), dataList.get((int) (len * 0.75)), dataList.get((int) (len * 0.9)));
        }

        // 平均值
        double avgData = dataList.stream().mapToDouble(e -> e).average().orElse(0D);

        Map<String, Object> res = new HashMap<>();
        res.put("boxplotData", boxplotData);
        res.put("avgData", avgData);
        return res;
    }
}