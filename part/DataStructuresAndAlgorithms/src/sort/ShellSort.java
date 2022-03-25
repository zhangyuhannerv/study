package sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName ShellSort
 * @Description 希尔排序（升序：小到大）
 * @Author Zhangyuhan
 * @Date 2020/11/1
 * @Version 1.0
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        // shellSort(arr);
        shellSortJiaoHuan(arr);
        System.out.println(Arrays.toString(arr));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        /*// 测试希尔排序（交换法）排序所花费的时间,
        // 创建80000个的随机的数组
        int[] arrTestTime = new int[80000];
        for (int i = 0; i < arrTestTime.length; i++) {
            arrTestTime[i] = (int) (Math.random() * 80000); // 会生成[0,80000)这个区间的一个随机浮点数
        }

        Date date1 = new Date();
        String date_1_str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是:" + date_1_str);

        // 用总结出来的排序方法排序随机数组
        shellSortJiaoHuan(arrTestTime);

        Date date2 = new Date();
        String date_2_str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:" + date_2_str);

        // 测试完毕，希尔排序(交换）所花费时间要比冒泡排序短很多，80000数据，7~10s*/


        // 测试希尔排序（位移）排序所花费的时间,
        // 创建80000个的随机的数组
        int[] arrTestTime1 = new int[80000];
        for (int i = 0; i < arrTestTime1.length; i++) {
            arrTestTime1[i] = (int) (Math.random() * 80000); // 会生成[0,80000)这个区间的一个随机浮点数
        }

        Date date3 = new Date();
        String date_3_str = simpleDateFormat.format(date3);
        System.out.println("排序前的时间是:" + date_3_str);

        // 用总结出来的排序方法排序随机数组
        shellSortWeiyi(arrTestTime1);
        // System.out.println(Arrays.toString(arrTestTime1));

        Date date4 = new Date();
        String date_4_str = simpleDateFormat.format(date4);
        System.out.println("排序后的时间是:" + date_4_str);

        // 测试完毕，希尔排序(位移式）所花费时间要比冒泡排序短很多，80000数据，7~10s
    }

    // 使用逐步推导的方式来编写希尔排序
    public static void shellSort(int[] arr) {
        int temp = 0;

        // 希尔排序的第1轮排序
        // 因为第一轮排序，是将10个数据分成了10/2=5组，
        for (int i = 5; i < arr.length; i++) {
            // 遍历各个组中所有的元素（共5组，每组中有两个元素），所以步长是5
            for (int j = i - 5; j >= 0; j -= 5) {
                //如果当前元素大于加上步长后的那个元素，说明需要交换
                if (arr[j] > arr[j + 5]) {
                    temp = arr[j];
                    arr[j] = arr[j + 5];
                    arr[j + 5] = temp;
                }
            }
        }

        System.out.println("希尔排序1轮后：" + Arrays.toString(arr));

        // 希尔排序的第2轮排序
        // 因为第二轮排序，是将10个数据分成了5 / 2 = 2组，
        for (int i = 2; i < arr.length; i++) {
            // 遍历各个组中所有的元素（共2组，每组中有5个元素），所以步长是2
            for (int j = i - 2; j >= 0; j -= 2) {
                //如果当前元素大于加上步长后的那个元素，说明需要交换
                if (arr[j] > arr[j + 2]) {
                    temp = arr[j];
                    arr[j] = arr[j + 2];
                    arr[j + 2] = temp;
                }
            }
        }

        System.out.println("希尔排序2轮后：" + Arrays.toString(arr));

        // 希尔排序的第3轮排序
        // 因为第3轮排序，是将10个数据分成了2 / 2 = 1组，
        for (int i = 1; i < arr.length; i++) {
            // 遍历各个组中所有的元素（共1组，每组中有10个元素），所以步长是1
            for (int j = i - 1; j >= 0; j -= 1) {
                //如果当前元素大于加上步长后的那个元素，说明需要交换
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        System.out.println("希尔排序3轮后：" + Arrays.toString(arr));
    }

    // 根据前面的逐步分析，使用循环处理
    // 希尔排序交换法
    // 思路（算法）-->代码
    public static void shellSortJiaoHuan(int[] arr) {
        int temp = 0;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                // 遍历各组中所有的元素(共gap组），步长为gap
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
    }

    // 希尔排序法（位移式）(交换式的改进)
    public static void shellSortWeiyi(int[] arr) {
        // 增量gap,并逐步缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 从第gap个元素开始，逐个对其所在的组进行直接插入
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j - gap]) {
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        // 移动
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    // 当退出while循环后，就给temp找到了插入的位置
                    arr[j] = temp;
                }
            }
        }
    }
}
