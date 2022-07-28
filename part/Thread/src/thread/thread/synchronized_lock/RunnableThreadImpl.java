package thread.thread.synchronized_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName RunnableThreadImpl
 * @Description 实现买票案例:出现线程安全问题，卖出了不存在的票和重复的票
 * 因此：解决线程安全问题的第三种方案:使用lock锁
 * java.util.concurrent.locks.lock接口
 * Lock实现提供比使用synchronized方法和语句可以获得的更广泛的锁定操作。
 * lock接口中的方法：
 * void lock()  获取锁
 * void unlock()  /释放锁
 * <p>
 * 实现类：
 * java.util.concurrent.locks.ReentrantLock
 * 该实现类实现了lock接口
 * <p>
 * 使用步骤：
 * 1.在成员位置创建一个 ReentrantLock对象
 * 2.在可能出现安全问题的代码前调用lock接口中的方法lock（）获取锁
 * 3.在可能出现安全问题的代码后调用lock接口中的方法unlock（）释放锁
 * <p>
 * <p>
 * }
 * @Author Zhangyuhan
 * @Date 2020/9/23
 * @Version 1.0
 */
public class RunnableThreadImpl implements Runnable {

    // 定义一个多线程共享的票源
    private static int ticket = 100;

    // 1.在成员位置创建一个 ReentrantLock对象
    Lock lock = new ReentrantLock();


    // 设置线程任务：卖票
    @Override
    public void run() {
        while (true) {
            // 2.在可能出现安全问题的代码前调用lock接口中的方法lock（）获取锁
            lock.lock();
            try {
                // 先判断票是否存在
                if (ticket > 0) {
                    // 提高安全问题出现的机率,使用sleep()方法
                    Thread.sleep(10);

                    // 票存在，买票
                    System.out.println(Thread.currentThread().getName() + "-->正在卖" + ticket + "票");

                    Thread.sleep(10);

                    ticket--;
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 3.在可能出现安全问题的代码后调用lock接口中的方法unlock（）释放锁
                lock.unlock(); // 建议使用try_catch，这样无论程序是否出现异常，都会把锁释放，方便其他线程来访问这个对象
            }

        }
    }
}
