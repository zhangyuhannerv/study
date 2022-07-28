package thread.thread.waitAndNotify;

/**
 * @ClassName WaitAndNotify
 * @Description 等待唤醒案例：也叫线程之间的通信
 * 创建一个顾客线程（消费者）:  告知老板要的包子的种类和数量，调用wait方法，放弃cpu执行，进入到waiting状态（无限等待）
 * 创建一个老板线程（生产者）： 花了5s做包子，做好包子之后，调用notify方法，唤醒顾客吃包子
 * <p>
 * 注意：
 * 顾客和老板线程必须使用同步代码块包裹起来，保证等待和唤醒只有一个执行
 * 同步使用的锁对象必须保证唯一
 * 只有锁对象才能调用waith（）和notify（），两个方法是Object类中的
 * wait()
 * 导致当前线程等待，直到另一个线程调用该对象的 notify()方法或 notifyAll()方法。
 * notify()
 * 唤醒正在等待对象监视器的单个线程。
 * <p>
 * 唤醒之后会继续执行wait方法之后的方法
 * @Author Zhangyuhan
 * @Date 2020/9/25
 * @Version 1.0
 */
public class WaitAndNotify {
    public static void main(String[] args) {
        // 创建锁对象，保证唯一
        Object o = new Object();
        new Thread() {
            @Override
            public void run() {
                // 一直等着买包子
                while (true) {
                    // 保证等待和唤醒的线程只能有一个正在进行，需要使用同步技术
                    synchronized (o) {
                        System.out.println("告知老板要的包子的种类和数量");
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 唤醒之后执行的代码
                        System.out.println("包子已经做好了，开吃");
                        System.out.println("-----------------------");
                    }
                }

            }
        }.start();

        // 创建一个老板线程
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    // 花了5s钟做包子
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 保证等待和唤醒的线程只能有一个正在进行，需要使用同步技术
                    synchronized (o) {
                        System.out.println("5s钟之后做好包子，告知顾客，可以吃包子了");
                        // 调用notify方法，唤醒顾客吃包子
                        o.notify();
                    }
                }

            }
        }.start();

    }
}
