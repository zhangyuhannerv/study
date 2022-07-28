package thread.thread.security;

/**
 * @ClassName RunnableThreadImpl
 * @Description 实现买票案例:出现线程安全问题，卖出了不存在的票和重复的票
 * 因此：解决线程安全问题的第一种方案:使用代码同步块
 * 格式：
 * synchronized(锁对象){
 * 可能会出现线程安全问题的代码（访问了共享数据的代码）
 * }
 * 注意：
 * 1.同步代码块中的对象可以是任意的对象
 * 2.但是必须保证多个线程使用的锁对象是同一个
 * 3.锁对象作用：
 * 把同步代码块锁住，只让一个线程在同步代码块中执行
 * @Author Zhangyuhan
 * @Date 2020/9/23
 * @Version 1.0
 */
public class RunnableThreadImpl implements Runnable {

    //定义一个多线程共享的票源
    private int ticket = 100;

    // 设置线程任务：买票
    @Override
    public void run() {
        // 先判断票是否存在


        while (true) {
            if (ticket > 0) {
                // 提高安全问题出现的机率,使用sleep()方法
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 票存在，买票
                System.out.println(Thread.currentThread().getName() + "-->正在卖" + ticket + "票");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket--;
            } else {
                break;
            }
        }

    }
}
