package ten_algorithm.kmp;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @ClassName KMPAlgorithm
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/3/12
 * @Version 1.0
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDEZHANG";
        String str2 = "G";
        // int[] next = kmpNext("CD");
        // a ab abc abcd abcda
        // bcdab cdab dab ab b
        // System.out.println("next=" + Arrays.toString(next));
        // System.out.println("index=" + kmpSearch(str1, "BBC", next));// 结果是0
        // System.out.println("index=" + kmpSearch(str1, "ABCDABD", next)); // 结果是15
        // System.out.println("index=" + kmpSearch(str1, "ABCDAB", next)); // 结果是4
        // System.out.println("index=" + kmpSearch(str1, "DE", next)); // 结果是21

        System.out.println(kmpSearch(str1,str2,kmpNext(str2)));
    }

    /**
     * @param str1 原字符串
     * @param str2 子字符串
     * @param next 部分匹配表，子串对应的部分匹配表
     * @return 如果是-1就是没有匹配到，否则就返回第一个匹配的位置
     */
    // 写出kmp搜索算法
    public static int kmpSearch(String str1, String str2, int[] next) {
        // 遍历
        for (int i = 0, j = 0; i < str1.length(); i++) {
            // 需要考虑str1.charAt(i)!=str2.charAt(j),去调整j的大小
            // kmp算法的core
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }

            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }

            if (j == str2.length()) { // 找到了
                return i - j + 1;
            }

        }
        return -1;
    }

    // 获取到一个字符串（子串）的部分匹配值
    public static int[] kmpNext(String dest) {
        // 创建一个next数组保存部分匹配值
        int[] next = new int[dest.length()];
        next[0] = 0; // 如果字符串的长度为1，那么它的部分匹配值就是0
        for (int i = 1, j = 0; i < dest.length(); i++) {
            // 当dest.charAt(i）!= dest.charAt(j),我们需要从next[j-1]获取新的j
            // 直到我们发现有dest.charAt(i)==dest.charAt[j]成立时才退出
            // 这是kmp算法的一个基础
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }

            if (dest.charAt(i) == dest.charAt(j)) {
                // 当这个条件满足，部分匹配值就是要加1
                j++;
            }
            next[i] = j;
        }

        return next;
    }

}
