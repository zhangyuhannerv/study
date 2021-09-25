package com.zh.work_demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName _2区段合并算法调整
 * @Description 将不连续的一维数组分为多个连续的二维数组
 * @Author Zhangyuhan
 * @Date 2021/9/22
 * @Version 1.0
 */
public class _2区段合并算法调整 {
    public static void main(String[] args) {
        Integer[] arr = {1};
        // Integer[] arr = {1, 2};
        // Integer[] arr = {1, 3};
        // Integer[] arr = {1, 2, 4};
        // Integer[] arr = {1, 3, 4};
        // Integer[] arr = {1, 2, 3};
        // Integer[] arr = {1, 2, 4, 5};
        // Integer[] arr = {1, 2, 4, 6, 7};
        // Integer[] arr = {1, 4, 6, 7};
        // Integer[] arr = {1, 2, 4, 5, 7};
        List<Integer> mileRepeatArr = Arrays.asList(arr);
        List<List<Integer>> markArr = new ArrayList<>();

        // 筛选出各个区段
        List<Integer> tempArr = new ArrayList<>();
        for (int i = 0; i < mileRepeatArr.size(); i++) {
            if (i == 0) {
                tempArr.add(mileRepeatArr.get(i));
                if (i == mileRepeatArr.size() - 1) {
                    markArr.add(tempArr);
                }
                continue;
            }

            if (mileRepeatArr.get(i) - mileRepeatArr.get(i - 1) != 1) {
                markArr.add(tempArr);
                tempArr = new ArrayList<>();
            }

            tempArr.add(mileRepeatArr.get(i));


            if (i == mileRepeatArr.size() - 1) {
                markArr.add(tempArr);
            }
        }

        System.out.println(markArr);
    }
}
