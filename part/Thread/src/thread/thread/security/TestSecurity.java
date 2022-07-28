package thread.thread.security;

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
        // 创建Thread对象，构造方法中传递Runnable接口的实现类对象
        Thread thread = new Thread(run);
        Thread thread1 = new Thread(run);
        Thread thread2 = new Thread(run);
        // 调用start方法开启多线程
        thread.start();
        thread1.start();
        thread2.start();
    }
}
