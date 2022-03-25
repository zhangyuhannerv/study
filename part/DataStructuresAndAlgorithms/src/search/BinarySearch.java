package search;

import java.util.ArrayList;

/**
 * @ClassName BinarySearch
 * @Description 二分查找（要求数组必须是有序的）
 * @Author Zhangyuhan
 * @Date 2020/11/8
 * @Version 1.0
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1000, 1000, 1000, 1000};
        // int resultIndex = binarySearch(arr, 0, arr.length - 1, 1000);
        // System.out.println("resultIndex = " + resultIndex);

        // int resultIndex2 = binarySearch(arr, -8);
        // System.out.println("resultIndex2=" + resultIndex2);

        // 做第二个binarySearch的测试
        ArrayList<Integer> resultIndexList = binarySearch2(arr, 0, arr.length - 1, 1000);
        System.out.println("resultIndexList = " + resultIndexList);
    }

    // 二分查找算法

    /**
     * binarySearch
     *
     * @param arr       数组
     * @param left      左边的索引
     * @param right     右边的索引
     * @param findValue 要查找的值
     * @return 如果找到就返回下标，如果没有找到，就返回-1
     * @author Zhangyuhan
     * @date 2020/11/8 17:35
     */
    public static int binarySearch(int[] arr, int left, int right, int findValue) {
        // 当left>right，说明递归了整个数组，但是没有找到
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midValue = arr[mid];
        if (findValue > midValue) {// 需要向右递归（因为数组从小到大排列）
            return binarySearch(arr, mid + 1, right, findValue);
        } else if (findValue < midValue) {// 需要向左递归
            return binarySearch(arr, left, mid - 1, findValue);
        } else {
            return mid;
        }
    }

    // 二分查找的非递归实现

    /**
     * binarySearch
     *
     * @param arr    待查找的数组，arr是升序排列
     * @param target 需要查找的数
     * @return 返回对应的下标,-1表示没有找到
     * @author Zhangyuhan
     * @date 2021/3/3 9:13
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {// 说明可以继续查找
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {// 需要向左边查找
                right = mid - 1;
            } else {
                left = mid + 1;// 需要向右边查找
            }
        }
        return -1;
    }

    // 完成一个课后思考题：

    /**
     * 一个有序数组有多个相同的数时，要将所有的数值都查找到
     * 思路分析：
     * 1，在找到mid索引值时，不要马上返回
     * 2. 先向mid索引值的左边扫描，将所有满足查找值的下标加入到一个集合中ArrayList
     * 3. 再向mid索引值的右边扫描，将所有满足查找值的元素下标，也加入到集合ArrayList
     * 4. 将ArrayList集合
     *
     * @param
     * @return
     * @author Zhangyuhan
     * @date 2020/11/8 17:48
     */

    public static ArrayList<Integer> binarySearch2(int[] arr, int left, int right, int findValue) {
        // 当left>right，说明递归了整个数组，但是没有找到
        if (left > right) {
            return new ArrayList<Integer>();
        }
        int mid = (left + right) / 2;
        int midValue = arr[mid];
        if (findValue > midValue) {// 需要向右递归（因为数组从小到大排列）
            return binarySearch2(arr, mid + 1, right, findValue);
        } else if (findValue < midValue) {// 需要向左递归
            return binarySearch2(arr, left, mid - 1, findValue);
        } else {
            // 为何只需向左或者向右扫描就行了呢?因为数组是有序的
            ArrayList<Integer> resultIndexList = new ArrayList<>();
            // 向左边扫描
            int temp = mid - 1;
            while (true) {
                if (temp < 0 || arr[temp] != findValue) {// 退出
                    break;
                }
                // 否则，就把temp放到集合中
                resultIndexList.add(temp);
                temp -= 1;// temp左移
            }
            resultIndexList.add(mid);

            temp = mid + 1;
            while (true) {
                if (temp >= arr.length || arr[temp] != findValue) {// 退出
                    break;
                }
                // 否则，就把temp放到集合中
                resultIndexList.add(temp);
                temp += 1;// temp右移
            }
            return resultIndexList;
        }
    }

}
