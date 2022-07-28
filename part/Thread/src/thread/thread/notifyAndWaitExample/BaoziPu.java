package thread.thread.notifyAndWaitExample;

/**
 * @ClassName BaoziPu
 * @Description 注意事项：
 * 包子铺线程和吃货线程的关系是什么--》通信关系（互斥）
 * 必须使用同步技术保证俩个线程只有一个在执行
 * 锁对象：必须保证唯一，在这里可以使用包子对象作为锁对象
 * 包子铺类和吃货类就需要把包子对象作为参数传递进来
 * 1.需要在成员位置创建一个包子变量
 * 2.使用带参数构造方法，为这个包子变量赋值
 * @Author Zhangyuhan
 * @Date 2020/9/25
 * @Version 1.0
 */
public class BaoziPu extends Thread {
    // 1.需要在成员位置创建一个包子变量
    private Baozi baozi;

    public BaoziPu() {
    }


    // 2.使用带参数构造方法，为这个包子变量赋值
    public BaoziPu(Baozi baozi) {
        this.baozi = baozi;
    }

    @Override
    public void run() {
        // 定义一个变量
        int count = 0;
        // 必须使用同步技术保证俩个线程只有一个在执行
        // 死循环的目的是让包子铺一直生产包子
        while (true) {
            synchronized (baozi) {
                // 对包子的状态进行判断
                if (baozi.isFlag() == true) {
                    // 有包子，包子铺调用wait()方法进行等待
                    try {
                        baozi.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 被唤醒之后，包子铺生产包子
                // 增加趣味性，交替生产两种包子
                if (count % 2 == 0) {
                    // 生产剥皮三鲜馅包子
                    baozi.setPi("薄皮");
                    baozi.setXian("三鲜馅");

                } else {
                    // 生产冰皮牛肉大葱馅的包子
                    baozi.setPi("冰皮");
                    baozi.setXian("牛肉大葱馅");
                }

                count++;
                System.out.println("包子铺正在生成" + baozi.getPi() + baozi.getXian() + "包子");
                // 生产包子需要3s
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 生产完包子
                baozi.setFlag(true);
                // 唤醒吃货线程
                baozi.notify();
                System.out.println("包子铺已经生产好了" + baozi.getPi() + baozi.getXian() + "包子，可以开吃了");
            }
        }

    }
}
