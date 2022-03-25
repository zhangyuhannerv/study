package search;

import java.util.Arrays;

/**
 * @ClassName FibonacciSearch
 * @Description 斐波那契查找，同样要求数组是有序的
 * @Author Zhangyuhan
 * @Date 2020/11/12
 * @Version 1.0
 */
public class FibonacciSearch {
    public static int maxSize = 20;

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        System.out.println(fibSearch(arr, 89));
    }

    // 后面mid=low+F(k-1),需要使用到斐波那契数列
    // 因此需要先获取到斐波那契数列
    // 这里使用非递归的方式得到斐波那契数列
    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    // 编写斐波那契查找算法
    // 使用非递归的方式编写算法
    // 在这里写几点自己的说明
    /**
     * 1.f[k]为新copy数组出来的长度（即扩容出来的数组）
     * 2.f[k]-1为新数组最后元素的下标
     */

    /**
     * fibSearch
     *
     * @param a   数组
     * @param key 我们需要查找的关键码（就是你要找的数字）
     * @return 返回对应的下标，没有返回-1
     * @author Zhangyuhan
     * @date 2020/11/12 23:56
     */
    public static int fibSearch(int[] a, int key) {
        int low = 0;
        int high = a.length - 1;
        int k = 0;// 表示斐波那契分割数值的那个下标
        //
        // 存放mid值
        int mid = 0;
        int[] f = fib();// 获取到斐波那契数列
        // 获取到斐波那契分割数值的下标
        while (high > f[k] - 1) {
            k++;
        }
        // 因为f[k]可能大于我们数组a的长度
        // 因此我们需要使用一个Array类，构造一个新的数组，并指向temp[];
        // 若f[k]>a.length,会用0补齐
        int[] temp = Arrays.copyOf(a, f[k]);
        // 实际上需要使用a数组最后的数而不是0来填充temp
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = a[high];
        }

        // 使用while循环处理，找到我们的key
        while (low <= high) {// 只要这个条件满足，就可以找
            mid = low + f[k - 1] - 1;
            if (key < temp[mid]) {// 说明我们应该继续向数组的前面（左）查找
                high = mid - 1;
                // 为什么是k--?
                /**
                 * 1. 全部元素 = 前面的元素 + 后边的元素
                 * 2. f[k] = f[k-1] + f[k-2]
                 * 3. 因为前面有f[k - 1]个元素,所以可以继续拆分f[k-1] = f[k-2] + f[k-3]
                 * 4. 即在f[k-1]的前面继续查找，所以为k--
                 * 5. 即下次循环: mid = low + f[k-1-1] -1;
                 */
                k--;
            } else if (key > temp[mid]) {// 说明我们要向后面（右）查找
                low = mid + 1;
                // 为什么是k-2呢？
                /**
                 * 1. 全部元素 = 前面的元素 + 后边的元素
                 * 2. f[k] = f[k-1] + f[k-2]
                 * 3. 因为后面我们有f[k-2]个元素，所以可以继续拆分f[k-2] = f[k-3] + f[k-4]
                 * 4. 即在f[k-2]的前面可以继续进行查找
                 * 5. 即下次循环mid = low + f[k-2-1]-1
                 */
                k -= 2;
            } else { // 找到了
                // 需要确定返回的是哪个下标
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        // while循环中没有返回值，说明没有找到，所以要返回-1
        return -1;
    }
}
