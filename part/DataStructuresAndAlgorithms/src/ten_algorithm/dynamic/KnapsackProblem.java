package ten_algorithm.dynamic;

/**
 * @ClassName KnapsakProblem
 * @Description 动态规划算法解决背包问题（01背包：每个物品仅可以放一次）
 * @Author Zhangyuhan
 * @Date 2021/3/9
 * @Version 1.0
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        int[] w = {1, 4, 3};// 物品的重量
        int[] val = {1500, 3000, 2000};// 物品的价值,这里的val[i] 就是前面的v[i];
        int m = 4;// 背包的容量
        int n = val.length; // 物品的个数

        // 为了记录放入商品的情况，我们定义一个二维数组
        int[][] path = new int[n + 1][m + 1];

        // 创建二维数组
        // v[i][j]表示在前i个物品中装入容量为j的背包中的最大价值
        int[][] v = new int[n + 1][m + 1];

        // 初始化第一行和第一列，这里在本程序中可以不去初始化，因为默认就是0
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;//将第一列设置为0
        }

        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0;// 将第一行设置为0
        }

        // 输出一下，看看目前的情况
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                System.out.print(v[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("------------");

        // 根据公式来动态规划处理
        for (int j = 1; j < v[0].length; j++) {// 不处理第一列，i是从1开始的
            for (int i = 1; i < v.length; i++) {// 不处理第一行，j是从1开始的
                if (w[i - 1] > j) {// 因为我们的程序i时从1开始的，因此原来的公式中的w[i]要修改为w[i-1]
                    v[i][j] = v[i - 1][j];
                } else {
                    // 因为我们的i是从1开始的，因此公式需要调整成下面这段代码
                    // v[i][j] = Math.max(v[i - 1][j], val[i - 1] + v[i - 1][j - w[i - 1]]);

                    // 为了记录商品存放到背包的情况，我们不能简单使用上面的公式，需要使用if-else来体现这个公式
                    if (v[i - 1][j] < val[i - 1] + v[i - 1][j - w[i - 1]]) {
                        v[i][j] = val[i - 1] + v[i - 1][j - w[i - 1]];
                        // 把当前的情况记录到path
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i - 1][j];
                    }
                }
            }
        }
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                System.out.print(v[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("-----------");
        // 最后输出我们放入的是哪些商品
        // 这样输出会把所有的放入情况都得到，起始我们只需要最后的放入
       /* for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[0].length; j++) {
                if (path[i][j] == 1) {
                    System.out.printf("第%d商品放入到背包\n", i);
                }
            }
        }*/

        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0) { // 从path的最后开始找
            if (path[i][j] == 1) {
                System.out.printf("第%d商品放入到背包\n", i);
                j -= w[i - 1];
            }
            i--;
        }
    }
}
