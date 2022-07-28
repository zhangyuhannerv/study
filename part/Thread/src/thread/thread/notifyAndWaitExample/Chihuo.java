package thread.thread.notifyAndWaitExample;

/**
 * @ClassName Chihuo
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/25
 * @Version 1.0
 */
public class Chihuo extends Thread {
    // 1.需要在成员位置创建一个包子变量
    private Baozi baozi;

    public Chihuo() {
    }

    // 2.使用带参数构造方法，为这个包子变量赋值
    public Chihuo(Baozi baozi) {
        this.baozi = baozi;
    }
    // 设置线程任务（run）：吃包子

    @Override
    public void run() {
        // 使用死循环让吃货一直吃包子
        while (true) {
            // 必须使用同步技术保证俩个线程只有一个在执行
            synchronized (baozi) {
                // 对包子的状态进行判断
                if (baozi.isFlag() == false) {
                    // 吃货调用wait方法进行等待
                    try {
                        baozi.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 被唤醒之后执行的代码，吃包子
                System.out.println("吃货正在吃：" + baozi.getPi() + baozi.getXian() + "的包子");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 吃完包子
                // 修改包子的状态为false没有
                baozi.setFlag(false);
                // 唤醒包子铺线程，生产包子
                baozi.notify();

                System.out.println("吃货吃完了：" + baozi.getPi() + baozi.getXian() + "的包子");
                System.out.println("------------");
            }
        }

    }
}
