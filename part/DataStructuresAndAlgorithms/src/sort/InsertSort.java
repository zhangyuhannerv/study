package sort;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName InsertSort
 * @Description 插入排序：主要思想就是有一个有序表和一个无序表，有序表长度逐渐增大，无序表长度逐渐减小
 * @Author Zhangyuhan
 * @Date 2020/11/1
 * @Version 1.0
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1, -1, 89};
        // insertSort(arr);
        insertSort1(arr);
        System.out.println(Arrays.toString(arr));


        // 测试插入排序所花费的时间,时间复杂度：O(n^2)
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
        insertSort1(arrTestTime);

        Date date2 = new Date();
        String date_2_str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是:" + date_2_str);

        // 测试完毕，插入排序所花费时间：80000数据，不到1s
    }

    // 插入排序
    public static void insertSort(int[] arr) {
        // 逐步推岛的方式来讲解，便于理解
        // 第一轮{101，34，119，1} =》 {34,101,119,1}

        // 定义待插入的数
        int insertValue = arr[1];
        int insertIndex = 1 - 1; // 即arr[1]的前面的这个数的下标

        //给insertValue找到插入的位置
        while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
            // 这句话保证在给insertValue找插入位置时，不越界
            // 待插入的数还没有找到适当的插入位置
            // 就需要将arr[insertIndex]后移
            arr[insertIndex + 1] = arr[insertIndex]; // arr[insertIndex]后移
            insertIndex--;
        }
        // 当退出while循环时，说明插入的位置找到,insertIndex+1的位置
        // 假如理解不了，可以自己debug一下
        arr[insertIndex + 1] = insertValue;
        System.out.println("第一轮插入后：");
        System.out.println(Arrays.toString(arr));

        // 第二轮
        insertValue = arr[2];
        insertIndex = 2 - 1;
        while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
            arr[insertIndex + 1] = arr[insertIndex]; // arr[insertIndex]后移
            insertIndex--;
        }
        arr[insertIndex + 1] = insertValue;
        System.out.println("第二轮插入后：");
        System.out.println(Arrays.toString(arr));

        // 第三轮
        insertValue = arr[3];
        insertIndex = 3 - 1;
        while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
            arr[insertIndex + 1] = arr[insertIndex]; // arr[insertIndex]后移
            insertIndex--;
        }
        arr[insertIndex + 1] = insertValue;
        System.out.println("第三轮插入后：");
        System.out.println(Arrays.toString(arr));

        // 4个数默认第一个数在有序表里面，所以4个数的排序只需要3轮

    }

    //集成插入排序方法(升序：小到大)
    public static void insertSort1(int[] arr) {
        // 使用for循环来集成插入排序
        int insertValue = 0;
        int insertIndex = 0;
        for (int i = 1; i < arr.length; i++) {

            insertValue = arr[i];
            insertIndex = i - 1;

            while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex]; // arr[insertIndex]后移
                insertIndex--;
            }

            // 这里优化，判断是否需要赋值（实际上不需要，也没必要，可以去掉这个优化判断）
            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertValue;
            }
        }
    }

    //集成插入排序方法2(升序：小到大)(看它比较好理解)
    public static void insertSort2(Comparable[] arr){
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            // 寻找元素 arr[i] 合适的插入位置
            for( int j = i ; j > 0 ; j -- )
                if( arr[j].compareTo( arr[j-1] ) < 0 )
                    swap( arr, j , j-1 );
                else
                    break;
        }
    }
    //核心代码---结束
    private static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
