package workCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MultiListToFewList
 * @Description 多个点的集合根据一个固定前后值合并成一个大的集合里面包含若干个不相交的小集合
 * @Author Zhangyuhan
 * @Date 2021/5/19
 * @Version 1.0
 */
public class MultiListToFewList {
    public static void main(String[] args) {
        List<Map<String, Double>> rangeList = new ArrayList<>();
        List<Double> numList = new ArrayList<>();
        numList.add(2.0);
        numList.add(3.0);
        numList.add(4.0);
        numList.add(10.0);
        numList.add(16.0);
        numList.add(17.0);
        numList.add(23.0);

        double space = 2.5;

        double begin = numList.get(0) - space;
        double end = numList.get(0) + space;

        if (numList.size() == 1) {
            Map<String, Double> rangeMap = new HashMap<>(2);
            rangeMap.put("begin", begin);
            rangeMap.put("end", end);
            rangeList.add(rangeMap);
        } else {
            for (int i = 1; i < numList.size(); i++) {
                double tempNum = numList.get(i);
                if (tempNum - space <= end) {
                    end = tempNum + space;
                    if (i == numList.size() - 1) {
                        Map<String, Double> rangeMap = new HashMap<>(2);
                        rangeMap.put("begin", begin);
                        rangeMap.put("end", end);
                        rangeList.add(rangeMap);
                    }

                } else {
                    Map<String, Double> rangeMap = new HashMap<>(2);
                    rangeMap.put("begin", begin);
                    rangeMap.put("end", end);
                    rangeList.add(rangeMap);

                    begin = tempNum - space;
                    end = tempNum + space;

                    if (i == numList.size() - 1) {
                        rangeMap = new HashMap<>(2);
                        rangeMap.put("begin", begin);
                        rangeMap.put("end", end);
                        rangeList.add(rangeMap);
                    }
                }
            }
        }
        System.out.println(rangeList);

    }
}
