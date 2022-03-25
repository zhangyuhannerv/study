package search;

import java.util.Arrays;

/**
 * @ClassName InsertValueSearch
 * @Description 插值查找算法, 同样要求数组是有序的
 * @Author Zhangyuhan
 * @Date 2020/11/10
 * @Version 1.0
 */
public class InsertValueSearch {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }
        // System.out.println(Arrays.toString(arr));
        System.out.println(insertValueSearch(arr, 0, arr.length - 1, 25));
    }

    // 编写插值查找算法(只找一个)

    /**
     * insertValueSearch
     *
     * @param arr       数组
     * @param left      左边的索引
     * @param right     右边的索引
     * @param findValue 要查找的值
     * @return 如果找到，就返回对应的下标，如果找不到，就返回-1
     * @author Zhangyuhan
     * @date 2020/11/10 0:25
     */
    public static int insertValueSearch(int[] arr, int left, int right, int findValue) {
        // 注意：findValue < arr[0]和findValue > arr[arr.length - 1]这两个条件必须需要
        // 否则，我们得到的mid这个值可能越界
        if (left > right || findValue < arr[0] || findValue > arr[arr.length - 1]) {
            return -1;
        }
        // 求出mid，mid是自适应的，因为findValue参与到了mid的计算中
        int mid = left + (right - left) * (findValue - arr[left]) / (arr[right] - arr[left]);
        int midValue = arr[mid];
        if (findValue > midValue) {
            // 说明应该向右边递归(因为数组从小到大）
            return insertValueSearch(arr, mid + 1, right, findValue);
        } else if (findValue < midValue) {
            // 说明应该向左边递归查找
            return insertValueSearch(arr, left, mid - 1, findValue);
        } else {
            return mid;
        }
    }
}
