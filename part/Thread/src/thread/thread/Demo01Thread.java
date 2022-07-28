package thread.thread;


/**
 * @ClassName Demo01Thread
 * @Description 创建多线程程序的第一种方式，创建Thread类的子类
 * java.lang.Thread类：是描述线程的类，我们想要实现多线程程序，就必须继承Thread类
 * 实现步骤：
 * 1.创建Thread类的子类
 * 2.在子类中重写Thread的run（）方法，设置线程任务（开启线程要做什么）
 * 3.创建Thread类的子类对象
 * 4.调用Thread类中的start（）方法，开启新的线程，来执行run（）方法
 * void start（）使该线程开始执行；java虚拟机调用该线程的run方法
 * 结果是两个线程并发的运行；当前线程（main线程）和另一个线程（创建的新的线程，执行其run方法）
 * 多次启动一个线程是非法的，特别是当线程已经结束后，不能再次重新启动
 * java程序属于抢占式调度，哪个线程的优先级高，哪个线程就优先执行；同一个优先级就随机选一个执行
 * @Author Zhangyuhan
 * @Date 2020/9/22
 * @Version 1.0
 */
public class Demo01Thread {
    /**
     * 线程的名称：
     * 主线程：main
     * 新线程：Thread-0,Thread-1.....
     *
     * @param args
     */
    public static void main(String[] args) {
        // 创建Thread类的子类对象
        MyThread myThread = new MyThread();
        // 给线程设置名称
        myThread.setName("小强");

        // 调用Thread类中的start（）方法，开启新的线程，来执行run（）方法
        // 子线程执行，调用start方法，开启新线程，执行run方法
        myThread.start();

        new MyThread().start();
        new MyThread("旺财").start();


        // 获取主线程的名称
        System.out.println("主线程的名称是" + Thread.currentThread().getName());

        // 主线程执行
        for (int i = 0; i < 20; i++) {
            System.out.println("main" + i);
        }

    }

}
