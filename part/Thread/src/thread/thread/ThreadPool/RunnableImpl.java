package thread.thread.ThreadPool;

/**
 * @ClassName RunnableImpl
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/25
 * @Version 1.0
 */
public class RunnableImpl implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"提供了一个新的线程执行");
    }
}
