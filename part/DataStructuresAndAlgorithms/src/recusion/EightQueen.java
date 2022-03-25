package recusion;

/**
 * @ClassName EightQueen
 * @Description 八皇后问题代码的实现
 * @Author Zhangyuhan
 * @Date 2020/10/27
 * @Version 1.0
 */
public class EightQueen {
    // 定义一个max表示共有多少个皇后
    int max = 8;
    // 定义一个数组array，用于保存皇后放置位置的结果，比如 arr = {0,4,7,5,2,6,1,3}就表示一种摆法
    int[] array = new int[max];
    static int count = 0;

    public static void main(String[] args) {
        // 测试一把，看看8皇后是否正确
        EightQueen eightQueen = new EightQueen();
        eightQueen.check(0);
        System.out.println("一共有" + count + "种解法");
    }


    // 编写一个方法，放置第n个皇后
    // 特别注意：check()方法是每一次递归时，进入到check（）中都有一套for循环，因此会有回溯
    private void check(int n) {
        if (n == max) {
            // 注意n从0开始放，而此时n=8，0-7已经放好了，即说明8个皇后已经放好了
            print();
            return;
        }

        // 该行依次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            // 先把当前这个皇后n，放到改行的第一列
            array[n] = i;
            // 然后判断当放置第n个皇后到i时，是否冲突
            if (judge(n)) {
                // 不冲突的情况
                // 那么就接着放第n+1个皇后
                check(n + 1);
            }

            // 如果冲突，就继续循环，执行array[n]=i；即将第n个皇后放在本行的下一列的位置上

        }
    }


    // 查看当我们放置第n个皇后时，就去检测该皇后是否和前面已经摆放的皇后冲突

    /**
     * n表示放置第n个皇后时
     *
     * @param n
     * @return
     */
    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            // 1.array[i] == array[n] 表示判断第n个皇后是否和第i个皇后在同一列
            // 2.Math.abs(n - i) == Math.abs(array[n] - array[i]) 这是在判断第n个皇后是否和第i个皇后是否在同一斜线
            // 3.判断是否在同一行没有必要，因为n每次都在递增，那么一定是放在不同的行
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    // 写一个方法,可以将皇后摆放的位置输出
    private void print() {
        count++;// 计算总共有多少种正确的解法
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "");
        }
        System.out.println();
    }
}
