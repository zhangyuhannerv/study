package thread.thread.synchronized_method;

/**
 * @ClassName RunnableThreadImpl
 * @Description 实现买票案例:出现线程安全问题，卖出了不存在的票和重复的票
 * 因此：解决线程安全问题的第二种方案:使用同步方法
 * 使用步骤：
 * 1.把访问了共享数据代码抽取出来，放到 一个方法中
 * 2.在方法上添加一个synchronized修饰符
 * 格式：定义方法的格式
 * 修饰符 synchronized 返回值类型 方法名（参数列表）{
 * 访问了共享数据的代码
 * }
 * @Author Zhangyuhan
 * @Date 2020/9/23
 * @Version 1.0
 */
public class RunnableThreadImpl implements Runnable {

    //定义一个多线程共享的票源
    private static int ticket = 100;

    // 定义一个同步方法

    /**
     * 原理：同步方法也会把方法内部的代码锁住
     * 只让一个线程执行
     * 同步方法的锁对象是谁？
     * 就是实现类对象 new RunnableThreadImpl();
     * 也就是this
     */
    public synchronized void sellTicked() {

        // 这个方法等价于把synchronized关键字去掉，然后写成如下的形式

        /* synchronized (this){
         *//*
            代码
             *//*
        }*/


        while (true) {
            // 先判断票是否存在
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

    // 静态同步方法
    /*
        静态同步方法也能实现同步，那么锁对象是谁？
        不能是this。
        this是创建对象之后产生的，而静态方法优先于对象
        静态方法的锁对象是本类的class属性--》即class文件对象（反射）
     */
    public static synchronized void payTicket() {

        // 这个方法等价于把synchronized关键字去掉，然后写成如下的形式

        /* synchronized (RunnableThreadImpl.class){
         *//*
            代码
             *//*
        }*/

        while (true) {
            // 先判断票是否存在
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

    // 设置线程任务：买票
    @Override
    public void run() {
        System.out.println("this" + this);
        //sellTicked();
        payTicket();
    }
}
