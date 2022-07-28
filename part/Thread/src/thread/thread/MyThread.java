package thread.thread;

/**
 * @ClassName MyThread
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/22
 * @Version 1.0
 */

// 创建一个Thread类的子类
public class MyThread extends Thread {
    /**
     * 获取线程的名称
     * 1.Thread类的方法，getName(),子类可以直接执行，返回名称
     * 2.可以先获取到当前正在执行的线程static Thread currentThread()，再使用线程中的方法getName()获取线程的名称
     * <p>
     * 设置线程的名称：
     * 1.Thread类中的方法，setName(名字) ，改变线程名称，使之与参数name相同
     * 2.创建一个带参数的构造方法，参数传递线程的名称；调用父类的带参构造方法，把线程名称传递给父类，让父类（Thread）给子线程起一个名字
     * 父类构造方法：Thread(String name)；分配新的Thread对象
     */

    // 在子类中重写Thread的run（）方法，设置线程任务（开启线程要做什么）
    @Override
    public void run() {
        // 获取线程的名称
        // System.out.println(getName());
        Thread thread = Thread.currentThread();

        System.out.println(thread);
        System.out.println(thread.getName());

        // 子线程要执行的代码
        for (int i = 0; i < 20; i++) {
            System.out.println("run" + i);
        }
    }

    public MyThread() {
    }


    public MyThread(String name) {
        super(name);
    }
}
