package recusion;

/**
 * @ClassName RecusionTest
 * @Description 递归调用规则：
 * 1.当程序执行到一个方法时（包括main方法），就会开辟一个独立的空间（栈）
 * 2.每个空间的数据（局部变量），是独立的
 * @Author Zhangyuhan
 * @Date 2020/10/22
 * @Version 1.0
 */
public class RecusionTest {
    // 回顾递归：打印问题
    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }
        System.out.println("n=" + n);
    }

    // 回顾递归：阶乘问题
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }
    }

    public static void main(String[] args) {
        // 通过打印问题，回顾递归调用机制
        test(4);
        // 阶乘回顾
        System.out.println("阶乘是" + factorial(4));
    }


}
