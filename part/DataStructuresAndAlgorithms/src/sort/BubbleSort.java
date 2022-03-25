package sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName BubbleSort
 * @Description 冒泡排序
 * @Author Zhangyuhan
 * @Date 2020/10/31
 * @Version 1.0
 */
public class BubbleSort {
    public static void main(String[] args) {
        int arr[] = {3, 9, -1, 10, 20};

        // 本例子是从小到大
        // 为了容易理解，我们把冒泡排序的演变过程展示一下
        // 第一次排序，就是将最大的数排在最后
        /*int temp = 0;// 临时变量
        for (int j = 0; j < arr.length - 1; j++) {
            // 如果前面的数比后面的数大，则交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第1次排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第二次排序,把第二大的数排在倒数第二位
        for (int j = 0; j < arr.length - 1 - 1; j++) {
            // 如果前面的数比后面的数大，则交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }

        System.out.println("第2次排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第三次排序,把第三大的数排在倒数第三位
        for (int j = 0; j < arr.length - 1 - 2; j++) {
            // 如果前面的数比后面的数大，则交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }

        System.out.println("第3次排序后的数组");
        System.out.println(Arrays.toString(arr));

        // 第四次排序,把第四大的数排在倒数第四位
        for (int j = 0; j < arr.length - 1 - 3; j++) {
            // 如果前面的数比后面的数大，则交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }

        System.out.println("第4次排序后的数组");
        System.out.println(Arrays.toString(arr));*/


        // 上面的过程可以抽象为一个二重循环
        // 冒泡排序的时间复杂度：(n^2)
        int temp = 0;// 临时变量
        boolean flag = false; // 标识变量，表示是否进行过交换
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 如果前面的数比后面的数大，则交换
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            System.out.println("第" + (i + 1) + "次排序后的数组");
            System.out.println(Arrays.toString(arr));

            if (!flag) {
                // 此时，flage == false
                // 说明在这一次排序中，一次交换都没有发生过,那么就跳出循环
                break;
            } else {
                flag = false; // 重置flag，进行下次判断
            }
        }

        // 测试写的冒泡排序方法
        int[] testArr = {2, 4, -1, 0, 44, 43, 1, 2};
        bubbleSort(testArr);
        System.out.println("冒泡算法排序后的数组是" + Arrays.toString(testArr));

        // 测试冒泡排序的速度O(n^2),给80000个的数据，测试
        // 创建80000个的随机的数组
        int[] arrTestTime = new int[80000];
        for (int i = 0; i < arrTestTime.length; i++) {
            arrTestTime[i] = (int) (Math.random() * 80000); // 会生成[0,80000)这个区间的一个随机浮点数
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date_1_str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是:" + date_1_str);

        bubbleSort(arrTestTime);

        Date date2 = new Date();
        String date_2_str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:" + date_2_str);
        // 80000数据，12s

    }

    // 将前面的冒泡排序的算法封装成一个方法
    public static void bubbleSort(int[] arr) {
        int temp = 0;// 临时变量
        boolean flag = false; // 标识变量，表示是否进行过交换
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 如果前面的数比后面的数大，则交换
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            if (!flag) {
                // 此时，flage == false
                // 说明在这一次排序中，一次交换都没有发生过,那么就跳出循环
                break;
            } else {
                flag = false; // 重置flag，进行下次判断
            }
        }
    }
}
