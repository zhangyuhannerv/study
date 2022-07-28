package thread.thread.notifyAllAndWaitWithParam;

/**
 * @ClassName NotifyAllAndWaitWithParam
 * @Description 进入到timewaiting（计时等待）有两种方式
 * 1.使用sleep（long mSeconds）方法，在毫秒值结束之后睡醒，线程进入到Runnable/block状态
 * 2.使用wait(long mSeconds)方法，wait方法如果在毫秒值结束之后，还没有被notify唤醒，就会自动醒来，此时线程进入到Runnable/block状态
 * wait(long timeout)
 * 导致当前线程等待，直到另一个线程调用 notify()方法或该对象的 notifyAll()方法，或者指定的时间已过。
 * <p>
 * 唤醒的方法有两个：
 * notify()
 * 唤醒正在等待对象监视器的单个线程。
 * notifyAll()
 * 唤醒正在等待对象监视器的所有线程。
 * @Author Zhangyuhan
 * @Date 2020/9/25
 * @Version 1.0
 */
public class NotifyAllAndWaitWithParam {
    public static void main(String[] args) {
        // 创建锁对象，保证唯一
        Object o = new Object();

        // 第一个顾客
        new Thread() {
            @Override
            public void run() {
                // 一直等着买包子
                while (true) {
                    // 保证等待和唤醒的线程只能有一个正在进行，需要使用同步技术
                    synchronized (o) {
                        System.out.println("顾客1告知老板要的包子的种类和数量");
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 唤醒之后执行的代码
                        System.out.println("包子已经做好了，顾客1开吃");
                        System.out.println("-----------------------");
                    }
                }

            }
        }.start();

        // 第二个顾客
        new Thread() {
            @Override
            public void run() {
                // 一直等着买包子
                while (true) {
                    // 保证等待和唤醒的线程只能有一个正在进行，需要使用同步技术
                    synchronized (o) {
                        System.out.println("顾客2告知老板要的包子的种类和数量");
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 唤醒之后执行的代码
                        System.out.println("包子已经做好了，顾客2开吃");
                        System.out.println("-----------------------");
                    }
                }

            }
        }.start();

        // 第三个顾客
        new Thread() {
            @Override
            public void run() {
                // 一直等着买包子
                while (true) {
                    // 保证等待和唤醒的线程只能有一个正在进行，需要使用同步技术
                    synchronized (o) {
                        System.out.println("顾客3告知老板要的包子的种类和数量");
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 唤醒之后执行的代码
                        System.out.println("包子已经做好了，顾客3开吃");
                        System.out.println("-----------------------");
                    }
                }

            }
        }.start();

        // 第四个顾客
        new Thread() {
            @Override
            public void run() {
                // 一直等着买包子
                while (true) {
                    // 保证等待和唤醒的线程只能有一个正在进行，需要使用同步技术
                    synchronized (o) {
                        System.out.println("顾客4告知老板要的包子的种类和数量");
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 唤醒之后执行的代码
                        System.out.println("包子已经做好了，顾客4开吃");
                        System.out.println("-----------------------");
                    }
                }

            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o) {
                        System.out.println("包子做好了，叫醒顾客，顾客就不需要等待5s那么久了");
                        // o.notify(); // 如果有多个等待线程，唤醒等待时间最长的
                        o.notifyAll(); // 唤醒所有等待的线程
                    }
                }

            }
        }.start();
    }
}
