package sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName RadixSort
 * @Description 桶排序，也叫基数排序（升序：小到大）
 * @Author Zhangyuhan
 * @Date 2020/11/8
 * @Version 1.0
 */
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        // radix(arr);
        radixSort(arr);
        System.out.println(Arrays.toString(arr));

        // 以下为测试基数排序算法所花费的时间
        int[] arrTestTime = new int[80000];
        for (int i = 0; i < arrTestTime.length; i++) {
            arrTestTime[i] = (int) (Math.random() * 80000); // 会生成[0,80000)这个区间的一个随机浮点数
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date_1_str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是:" + date_1_str);

        // 用总结出来的排序方法排序随机数组
        radixSort(arrTestTime);

        Date date2 = new Date();
        String date_2_str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:" + date_2_str);
    }

    // 基数排序方法
    public static void radix(int[] arr) {

        // 针对每个元素的个位进行排序处理
        // 先定义一个二维数组表示10个桶，每个桶就是一个一维数组
        // 说明：
        // 1.二维数组包含10个一维数组（分别表示数字0-9的桶）
        // 2.为了防止在放入数的时候，数据溢出，则每一个一维数组（桶），大小定义为arr.length.
        // 3.很明显：基数排序是使用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];

        // 为了记录每个桶中实际存放了多少个数据，我们定义一个一个一维数组来记录各个桶的每次放入的数据的个数
        // 可以这样理解：buckElementCounts[0]记录的就是bucket[0]桶每次放入数据的个数
        int[] bucketElementCounts = new int[10];
        // 第一轮排序
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的个位值
            int digitOfElement = arr[j] / 1 % 10;
            // 放入到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }
        // 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来的数组）
        int index = 0;
        // 遍历每一个桶，并将桶中的数据放入到原数组
        for (int k = 0; k < bucket.length; k++) {
            // 如果桶中有数据，我们才放入到原数组
            if (bucketElementCounts[k] != 0) {
                // 循环该桶即第k个桶（即第k个一维数组），放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素放入到arr中
                    arr[index] = bucket[k][l];
                    index++;
                }
                // 每个桶的数据放回到原数组后，需要将bucketElementCounts[k] = 0，即默认这个桶里有0个元素，方便下一轮放入新的元素
                bucketElementCounts[k] = 0;
            }
        }


        System.out.println("第一轮对个位的排序处理arr=" + Arrays.toString(arr));

        // 第二轮排序
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的十位值
            int digitOfElement = arr[j] / 10 % 10;
            // 放入到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }
        // 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来的数组）
        index = 0;
        // 遍历每一个桶，并将桶中的数据放入到原数组
        for (int k = 0; k < bucket.length; k++) {
            // 如果桶中有数据，我们才放入到原数组
            if (bucketElementCounts[k] != 0) {
                // 循环该桶即第k个桶（即第k个一维数组），放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素放入到arr中
                    arr[index] = bucket[k][l];
                    index++;
                }
                bucketElementCounts[k] = 0;
            }
        }

        System.out.println("第二轮对十位的排序处理arr=" + Arrays.toString(arr));

        // 第三轮排序
        for (int j = 0; j < arr.length; j++) {
            // 取出每个元素的百位值
            int digitOfElement = arr[j] / 100 % 10;
            // 放入到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }
        // 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来的数组）
        index = 0;
        // 遍历每一个桶，并将桶中的数据放入到原数组
        for (int k = 0; k < bucket.length; k++) {
            // 如果桶中有数据，我们才放入到原数组
            if (bucketElementCounts[k] != 0) {
                // 循环该桶即第k个桶（即第k个一维数组），放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    // 取出元素放入到arr中
                    arr[index] = bucket[k][l];
                    index++;
                }
                bucketElementCounts[k] = 0;
            }
        }
        System.out.println("第三轮对百位的排序处理arr=" + Arrays.toString(arr));
    }

    // 根据前面的推导过程，集成为一个总方法
    public static void radixSort(int[] arr) {
        // 1.先得到数组中最大的数的位数
        int max = arr[0];// 假设第一个数就是最大的数
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        // 得到最大数的位数
        int maxLength = String.valueOf(max).length();
        System.out.println("最大数的位数是+" + maxLength);


        // 这里使用循环集成基数排序算法
        int[][] bucket = new int[10][arr.length];
        int[] bucketElementCounts = new int[10];

        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            // 针对每个元素对应的位进行排序，第一次是个位，第二次是十位，第三次是百位....
            // 因此定义了一个n用来取得每轮对应的位数
            for (int j = 0; j < arr.length; j++) {
                // 取出每个元素对应位的值
                int digitOfElement = arr[j] / n % 10;
                // 放入到对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
            // 按照这个桶的顺序（一维数组的下标依次取出数据，放入原来的数组）
            int index = 0;
            // 遍历每一个桶，并将桶中的数据放入到原数组
            for (int k = 0; k < bucket.length; k++) {
                // 如果桶中有数据，我们才放入到原数组
                if (bucketElementCounts[k] != 0) {
                    // 循环该桶即第k个桶（即第k个一维数组），放入
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        // 取出元素放入到arr中
                        arr[index] = bucket[k][l];
                        index++;
                    }
                    // 每个桶的数据放回到原数组后，需要将bucketElementCounts[k] = 0，即默认这个桶里有0个元素，方便下一轮放入新的元素
                    bucketElementCounts[k] = 0;
                }
            }
        }

    }
}
