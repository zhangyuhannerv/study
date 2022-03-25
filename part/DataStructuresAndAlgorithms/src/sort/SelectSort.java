package sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName SelectSort
 * @Description 选择排序
 * @Author Zhangyuhan
 * @Date 2020/10/31
 * @Version 1.0
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1};
        // 推导方法排序
        // selectSort(arr);
        // 总结方法排序
        selectSort1(arr);

        // 测试选择排序所花费的时间,时间复杂度：O(n^2)
        // 创建80000个的随机的数组
        int[] arrTestTime = new int[80000];
        for (int i = 0; i < arrTestTime.length; i++) {
            arrTestTime[i] = (int) (Math.random() * 80000); // 会生成[0,80000)这个区间的一个随机浮点数
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date_1_str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是:" + date_1_str);

        // 用总结出来的排序方法排序随机数组
        selectSort1(arrTestTime);

        Date date2 = new Date();
        String date_2_str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:" + date_2_str);

        // 测试完毕，选择排序所花费时间要比冒泡排序短很多，80000数据，2~3s

    }

    // 选择排序的方法
    public static void selectSort(int[] arr) {
        // 使用逐步推导的方式来讲解选择排序
        // 第一轮
        // 原始的数据：101，34，119，1
        // 第一轮排序后：1，34，119，101
        // 算法：先简单-》再复杂（无论是分析还是记忆还是实现）：可以把复杂的算法拆分成简单的问题，然后再逐步解决


        // 第一轮
        // 假定最小值所在的下标
        int minIndex = 0;
        // 假定最小值
        int min = arr[0];
        for (int j = 0 + 1; j < arr.length; j++) {
            if (min > arr[j]) {
                // 说明假定的最小值并不是最小的
                min = arr[j];// 重置最小值
                minIndex = j;// 重置最小值所在的下标
            }
        }
        // 第一轮结束之后，进行最小值的交换
        if (minIndex != 0) {
            // 把arr[0]放到到最小值所在的位置
            arr[minIndex] = arr[0];
            // 把最小值放到arr[0]的位置上
            arr[0] = min;
        }
        System.out.println("第一轮后，排序的结果是：" + Arrays.toString(arr));

        // 第二轮
        // 假定最小值所在的下标
        minIndex = 1;
        // 假定最小值
        min = arr[1];
        for (int j = 1 + 1; j < arr.length; j++) {
            if (min > arr[j]) {
                // 说明假定的最小值并不是最小的
                min = arr[j];// 重置最小值
                minIndex = j;// 重置最小值所在的下标
            }
        }
        // 第一轮结束之后，进行最小值的交换
        // 补充的优化
        if (minIndex != 1) {
            // 把arr[0]放到到最小值所在的位置
            arr[minIndex] = arr[1];
            // 把最小值放到arr[0]的位置上
            arr[1] = min;
        }
        System.out.println("第二轮后，排序的结果是：" + Arrays.toString(arr));

        // 第三轮就不写了吧，基本和第一轮和第二轮是一样的
        // .....
    }

    public static void selectSort1(int[] arr) {
        // 在推导的过程中，我们发现了规律，因此，可以使用一个嵌套循环来解决
        // 选择排序的时间复杂度也是O(n^2)
        for (int i = 0; i < arr.length - 1; i++) {
            // 假定最小值所在的下标
            int minIndex = i;
            // 假定最小值
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    // 说明假定的最小值并不是最小的
                    min = arr[j];// 重置最小值
                    minIndex = j;// 重置最小值所在的下标
                }
            }
            // 第一轮结束之后，进行最小值的交换
            if (minIndex != i) {
                // 把arr[0]放到到最小值所在的位置
                arr[minIndex] = arr[i];
                // 把最小值放到arr[0]的位置上
                arr[i] = min;
            }
            // System.out.println("第" + i + 1 + "轮后，排序的结果是：" + Arrays.toString(arr));
        }
    }
}
