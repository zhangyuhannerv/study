package sort;

import java.util.Arrays;

/**
 * @ClassName QuickSort
 * @Description 快速排序（小到大：升序）,韩顺平讲的可能有点问题，若要实用代码请使用quickSort2
 * @Author Zhangyuhan
 * @Date 2020/11/2
 * @Version 1.0
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {-9, 78, 0, 23, -567, 70, 70, -1, 900, 4561, 2};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    // left代表最左边
    // right代表最右边
    public static void quickSort(int[] arr, int left, int right) {
        int l = left; // 左下标
        int r = right; // 右下标
        // pivot：中轴的意思
        int pivot = arr[(left + right) / 2];
        // 临时变量，作为交换时使用
        int temp = 0;

        // while循环的目的是让比pivot值小的值放到左边
        // 比pivot值大的值放到右边
        while (l < r) {
            // 在pivot的左边一直找，找到大于或者等于pivot的值，才退出
            while (arr[l] < pivot) {
                l += 1;
            }

            // 在pivot的右边一直找，找到小于于或者等于pivot的值，才退出
            while (arr[r] > pivot) {
                r -= 1;
            }

            // 如果l>=r成立，说明pivot两边的值，已经按照左边全部是小于等于pivot的值
            // 而右边全部是大于等于pivot的值
            if (l >= r) {
                break;
            }

            // 交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            // 如果交换完后，发现这个arr[l] == pivot值，那么往前再走一步
            // 此时让r--,相当于前移了一步
            if (arr[l] == pivot) {
                r--;
            }

            // 同理
            if (arr[r] == pivot) {
                l++;
            }
        }

        // 如果说l==r,必须让l++,r--，否则会出现栈溢出
        if (l == r) {
            l++;
            r--;
        }

        // 向左递归
        if (left < r) {
            quickSort(arr, left, r);
        }

        // 向右递归
        if (right > l) {
            quickSort(arr, l, right);
        }
    }
}
