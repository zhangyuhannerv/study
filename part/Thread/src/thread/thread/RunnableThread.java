package thread.thread;

/**
 * @ClassName RunnableThread
 * @Description 创建多线程的第二种方式，实现Runnable接口
 * java.lang.Runnable：该接口应该由那些打算通过某一线程执行其实例的类来实现，继承接口的类必须定义一个称为run的无参构造方法
 * java.lang.Thread(Runnable target,String name)
 * @Author Zhangyuhan
 * @Date 2020/9/23
 * @Version 1.0
 */

/**
 * 实现步骤
 * 1.创建一个Runnable接口的实现类
 * 2.在实现类中重写Runnable接口的run方法，设置线程任务
 * 3.创建Runnable接口的实现类对象
 * 4.创建Thread类对象，构造方法传递Runnable接口的实现类对象
 * 5.调用Thread类中的start方法，开启新的线程执行run方法
 */
public class RunnableThread implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(Thread.currentThread().getName() + "-->" + i);
        }
    }

    public static void main(String[] args) {
        // 创建Runnable的实现类对象
        Runnable runnable = new RunnableThread();
        // 创建Thread类对象，传递Runnable接口的实现类对象
        new Thread(runnable).start();
        for (int i = 0; i < 20; i++) {
            System.out.println(Thread.currentThread().getName() + "-->" + i);
        }
    }

    /**
     * 实现Runnable接口创建多线程程序的好处：
     * 1.避免了单继承的局限性（一个类只能继承一个类，一个类如果继承了Thread类，就不能继承其他类）
     *      实现了Runnable接口，还可以继承其他的类，实现其他的接口
     * 2.增强了程序的扩展性，降低了程序的耦合性（解耦）
     *      实现Runnable接口的方式，把设置线程任务和开启新的线程进行了分离（解耦）
     *      实现类中，重写了run方法，用来设置线程任务，创建Thread类对象，调用start（）开启新的线程
     *
     */

}

