package ten_algorithm.dac;

/**
 * @ClassName Hanoitower
 * @Description 分治算法解决汉诺塔问题
 * @Author Zhangyuhan
 * @Date 2021/3/4
 * @Version 1.0
 */
public class Hanoitower {
    public static void main(String[] args) {
        hanoiTower(5, 'A', 'B', 'C');
    }

    // 汉诺塔的移动方法
    // 使用分治算法解决
    public static void hanoiTower(int num, char a, char b, char c) {
        // 如果只有一个盘
        if (num == 1) {
            System.out.println("第1个盘从" + a + "->" + c);
        } else {
            // 有两个盘及以上（分成最下面的一个盘和上面的所有盘）
            // 1.先把上面的所有盘 a->b,移动过程会使用到c
            hanoiTower(num - 1, a, c, b);
            // 2.把最下边的盘从a移动到c
            System.out.println("第" + num + "个盘从" + a + "->" + c);
            // 3.把b塔的所有盘移动到c,移动过程使用a塔
            hanoiTower(num - 1, b, a, c);
        }
    }
}
