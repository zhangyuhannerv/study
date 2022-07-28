package thread.thread;

/**
 * @ClassName Demo02Thread
 * @Description sleeep(毫秒数)：当前正在执行的线程以指定的毫秒数暂停（暂时停止执行）
 * 毫秒数结束之后，线程继续执行
 * @Author Zhangyuhan
 * @Date 2020/9/23
 * @Version 1.0
 */
public class Demo02Thread {
    public static void main(String[] args) {
        // 模拟秒表
        for (int i = 1; i <= 60; i++) {
            System.out.println(i);

            // 使用Thread类的sleep方法，让程序睡眠一秒钟
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
