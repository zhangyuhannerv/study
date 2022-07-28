package thread.thread.notifyAndWaitExample;

/**
 * @ClassName Test
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/25
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        // 创建包子对象
        Baozi baozi = new Baozi();
        // 创建包子铺线程，开启，生产包子
        new BaoziPu(baozi).start();
        // 创建吃货线程，开启，吃包子
        new Chihuo(baozi).start();
    }
}
