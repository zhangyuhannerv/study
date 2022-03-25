package tree;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName HeepSort
 * @Description 堆排序
 * @Author Zhangyuhan
 * @Date 2021/1/31
 * @Version 1.0
 */
public class HeepSort {
    public static void main(String[] args) {
        // 要求将数组进行升序排列，因此要调整成大顶堆
        int[] arr = {4, 6, 8, 5, 9, -1, 90, 89, 5999, -9999};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));

        // 测试插入排序所花费的时间,时间复杂度：O(n^2)
        // 创建80000个的随机的数组
        int[] arrTestTime = new int[8000000];
        for (int i = 0; i < arrTestTime.length; i++) {
            arrTestTime[i] = (int) (Math.random() * 8000000); // 会生成[0,8000000)这个区间的一个随机浮点数
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date_1_str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是:" + date_1_str);

        // 用总结出来的排序方法排序随机数组
        heapSort(arrTestTime);

        Date date2 = new Date();
        String date_2_str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:" + date_2_str);

    }

    // 编写一个堆排序的方法
    public static void heapSort(int[] arr) {
        int temp = 0;

        // 分步完成
        // adjustHeap(arr, 1, arr.length);
        // System.out.println("第一次" + Arrays.toString(arr));
        // adjustHeap(arr, 0, arr.length);
        // System.out.println("第二次" + Arrays.toString(arr));

        // 完成最终代码
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        for (int j = arr.length - 1; j >= 0; j--) {
            // 交换
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, j);
        }
    }

    // 将一个数组（二叉树），调整成一个大顶堆

    /**
     * 功能：完成将i对应的非叶子节点的数调整成大顶堆
     * 举例：int[]  arr = {4,6,8,5,9}; i=1=> adjustHeap() =>得到{4，9，8，5，6}
     * 如果我们再次调用，adjustHeap 传入的是i=0 => 得到{4，9，8，5，6} => {9,6,8,5,4}
     *
     * @param arr    待调整的数组
     * @param i      表示非叶子节点在数组中的索引
     * @param length 表示对多少个元素进行调整，length是在住键的减少的
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        int temp = arr[i]; // 先取出当前元素的值，保存在一个临时变量
        // 开始调整
        // 说明
        // 1.k = i * 2 + 1 ,此时，k是i节点的左子节点下标
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && arr[k] < arr[k + 1]) {// 说明左子节点的值小于右子节点的值
                k++; // 就让k指向右子节点
            }

            if (arr[k] > temp) {// 如果左子节点或右子节点大于父节点
                arr[i] = arr[k]; // 把较大的值赋给当前节点
                i = k; // !!!然后让i指向k，继续循环比较
            } else {
                break;// !!!
            }
        }

        // 当for循环结束后，我们已经将以i为父节点的这颗树的最大值放在了i的这个位置（最顶上）
        // 是局部的
        arr[i] = temp; // 将temp值放到调整后的位置
    }

}
