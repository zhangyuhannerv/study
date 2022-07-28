package thread.thread.synchronized_lock;

/**
 * @ClassName TestSecurity
 * @Description 创建3个线程，同时开启，对共享的票进行出售
 * @Author Zhangyuhan
 * @Date 2020/9/23
 * @Version 1.0
 */
public class TestSecurity {
    public static void main(String[] args) {
        // 创建 Runnable接口的实现类对象
        // 因为只有一个实现类，所以票源是共享的
        Runnable run = new RunnableThreadImpl();
        System.out.println("run" + run);
        // 创建Thread对象，构造方法中传递Runnable接口的实现类对象
        Thread thread = new Thread(run);
        Thread thread1 = new Thread(run);
        Thread thread2 = new Thread(run);
        // 调用start方法开启多线程
        thread.start();
        thread1.start();
        thread2.start();

        /**
         * 总结：同步中的线程：没有执行完毕不会释放锁，同步外的线程没有锁进不去同步
         * 缺点：程序频繁的判断锁，获取锁，释放锁，程序的效率会降低
         * 优点：锁对象只有一个，一个线程获取到锁对象才能进入同步代码块，即只有一个线程能够进入同步代码块对共享数据进行修改
         */
    }
}
