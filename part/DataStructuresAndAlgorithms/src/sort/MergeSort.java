package sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName MergeSort
 * @Description 归并排序(也叫分治排序 ） ： 小到大 ， 升序
 * @Author Zhangyuhan
 * @Date 2020/11/3
 * @Version 1.0
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2, 13, -4, -241};
        int[] temp = new int[arr.length]; // 说明归并排序需要额外的空间
        mergeSort(arr, 0, arr.length - 1, temp);
        System.out.println("归并排序后：");
        System.out.println(Arrays.toString(arr));

        // 测试归并排序所花费的时间
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
        mergeSort(arrTestTime, 0, arrTestTime.length - 1, new int[arrTestTime.length]);

        Date date2 = new Date();
        String date_2_str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:" + date_2_str);

        // 测试完毕

    }

    // 分+合的方法
    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) / 2; // 中间的索引
            // 先向左递归进行分解
            mergeSort(arr, left, mid, temp);
            // 向右递归进行分解
            mergeSort(arr, mid + 1, right, temp);
            // 拆解到最后合并一下，之后递归回调，依次合并回来
            merge(arr, left, right, mid, temp);
        }
    }

    // 合并的方法

    /**
     * merge
     *
     * @param arr   排序的原始数组
     * @param left  左边有序序列的初始索引
     * @param right 右边索引
     * @param mid   中间索引
     * @param temp  做中转的数组
     * @return
     * @author Zhangyuhan
     * @date 2020/11/3 22:44
     */
    public static void merge(int[] arr, int left, int right, int mid, int[] temp) {
        // System.out.println("xxx");
        int i = left; // i表示左边有序序列的初始索引
        int j = mid + 1; // 初始化j，表示右边有序序列的初始索引
        int t = 0; // 指向temp的数组的当前索引

        // （一）
        // 先把左右两边（有序）的数据按照规则填充到temp数组里去
        // 直到左右两边的有序序列，有一边处理完毕为止
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                // 如果左边有序序列的当前元素小于或者等于右边有序序列的当前元素
                // 即将左边的当前元素拷贝到temp数组里去
                // 然后t要往后移一下，i也要往后移
                temp[t] = arr[i];
                t++;
                i++;
            } else {
                // 反之，右边的有序序列的当前元素小于左边有序序列的当前元素
                // 将右边有序序列的当前元素填充到temp数组里去
                temp[t] = arr[j];
                t++;
                j++;
            }
        }

        //（二）
        // 把有剩余数据的一边的剩余数据依次全部填充到temp里去
        while (i <= mid) { // 说明左边的有序序列仍有剩余元素，就全部填充到temp
            temp[t] = arr[i];
            t++;
            i++;
        }

        while (j <= right) {// 说明右边的有序序列仍有剩余元素，就全部填充到temp
            temp[t] = arr[j];
            t++;
            j++;
        }

        // （三）
        // 将temp数组的元素重新拷贝到arr
        // 注意：并不是每次都拷贝8个
        // 这里老师虽然加了临时变量tempLeft，但我觉得是多余的，直接用left也可以
        // 所以我自行替换为left
        t = 0;
        // int tempLeft = left;
        while (left <= right) {
            // 第一次合并时，templeft = 0，right = 1
            // 第二次合并时，templeft = 2，right = 3
            // 第三次合并时，templeft = 0，right = 3
            // ...
            // 最后一次合并时，templeft = 0，right = 7
            arr[left] = temp[t];
            t++;
            left++;
        }
    }
}
