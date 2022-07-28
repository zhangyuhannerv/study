package thread.thread.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName DemoThreadPool1
 * @Description 线程池类，JDK1.5之后提供的
 * java.util.concurrent.Executors：本案例中要用到的线程池的工厂类，用来生产线程池
 * <p>
 * Executors类中有许多生产线程池的静态方法
 * <p>
 * 本案例中用到的生产线程池的方法：
 * newFixedThreadPool(int nThreads)
 * 创建一个线程池，该线程池重用固定数量的从共享无界队列中运行的线程。
 * 参数：
 * int nThreads:创建线程池中包含的线程的数量
 * 返回值：
 * ExecutorService是一个接口，返回的是ExecutorService接口的实现类对象，我们可以使用ExecutorService接口接收（面向接口编程）
 * <p>
 * ExecutorService接口（本案例中是线程池接口）
 * java.util.concurrent
 * 在本案例中它可以用来从线程池中获取线程，不需要调用start()方法，即可执行线程任务，详情见下面submit（）方法的介绍
 * <p>
 * submit(Runnable task)
 * 提交一个可运行的任务执行，并返回一个表示该任务的Future。
 * <p>
 * 该接口中还有一个关闭/销毁的方法
 * shutdown()
 * 启动有序关闭，其中先前提交的任务将被执行，但不会接受任何新任务。
 * <p>
 *
 * 线程池的使用步骤
 * 1.使用线程池的工厂类Executors里面提供的静态方法newFixedThreadPool()生产一个指定线程数量的线程池
 * 2.创建一个类，实现Runnable接口，重写run（）方法，设置线程任务
 * 3.调用ExecutorService中的方法submit（），传递线程任务，开启线程，执行run（）方法
 * 4.调用ExecutorService中的方法shutdown()销毁线程池，(不建议执行）
 *
 * @Author Zhangyuhan
 * @Date 2020/9/25
 * @Version 1.0
 */
public class DemoThreadPool1 {
    public static void main(String[] args) {
        // 使用线程池的工厂类Executors里面提供的静态方法newFixedThreadPool()生产一个指定线程数量的线程池
        ExecutorService es = Executors.newFixedThreadPool(2);
        // 调用ExecutorService中的方法submit（），传递线程任务，开启线程，执行run（）方法
        es.submit(new RunnableImpl());
        // 线程池会一直开启，使用完了线程，会自动把线程归还给线程池，所以线程可以重复使用
        es.submit(new RunnableImpl());
        es.submit(new RunnableImpl());

        // 调用ExecutorService中的方法shutdown()销毁线程池，(不建议执行）
        es.shutdown();
        // es.submit(new RunnableImpl());// 抛异常，线程池已经关闭了，就不嫩获取线程了
    }

}
