package thread.thread;

/**
 * @ClassName Demo03Thread
 * @Description 匿名内部类的方式实现线程的创建
 * 匿名：没有名字
 * 内部类：写在其他类内部的类
 * 匿名内部类作用：简化代买
 * 把子类继承父类，重写父类的方法，创建子类对象合成一步完成
 * 把实现类实现接口，重写接口中的方法，创建实现类对象合成一步完成
 * 匿名内部类的最终产物：子类/实现类对象，而这个类它没有名字
 * <p>
 * 格式：
 * new 父类/接口（）{
 * 重写父类/接口中的方法
 * }
 * @Author Zhangyuhan
 * @Date 2020/9/23
 * @Version 1.0
 */
public class Demo03Thread {
    public static void main(String[] args) {
        // 之前：
        // 线程的父类是Thread
        // new MyThread().start();

        // 现在：
        // new 一个父类
        new Thread() {
            // 重写run方法，设置线程任务
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(Thread.currentThread().getName() + "-->张雨晗");
                }
            }
        }.start();

        // 线程的接口
        // 之前
        // Runnable target = new RunnableThread();
        Runnable r = new Runnable() {
            // 重写run方法，设置线程任务
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(Thread.currentThread().getName() + "-->程序员" + i);
                }
            }
        };

        new Thread(r).start();

        // 简化接口的方法
        new Thread(new Runnable() {
            // 重写run方法，设置线程任务
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(Thread.currentThread().getName() + "-->秃头" + i);
                }
            }
        }).start();
    }

}
