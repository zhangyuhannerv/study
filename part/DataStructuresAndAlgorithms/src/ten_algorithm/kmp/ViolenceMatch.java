package ten_algorithm.kmp;

/**
 * @ClassName ViolenceMatch
 * @Description 暴力匹配字符串
 * @Author Zhangyuhan
 * @Date 2021/3/10
 * @Version 1.0
 */
public class ViolenceMatch {
    public static void main(String[] args) {
        // 测试暴力匹配算法
        String str1 = "afdfasfdasdfjfkaljfdklajflad.  afjasldfj";
        String str2 = "jfka";// 正确答案应该是12
        int index = violenceMatch(str1, str2);
        System.out.println(index);

    }

    public static int violenceMatch(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1len = s1.length;
        int s2len = s2.length;

        int i = 0;// i相当于索引指向s1
        int j = 0;// j指向s2
        while (i < s1len && j < s2len) {// 保证匹配时不越界
            if (s1[i] == s2[j]) {// 匹配成功
                i++;
                j++;
            } else {// 没有匹配成功
                i = i - (j - 1);
                j = 0;
            }
        }

        // 判断是否匹配成功
        if (j == s2len) {
            return i - j;
        } else {
            return -1;
        }
    }
}
