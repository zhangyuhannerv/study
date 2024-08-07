package thread;

/**
 * @ClassName Demo01MainThread
 * @Description 主线程：执行main方法的线程
 * 单线程程序：java程序中只有一个线程
 * 执行从main方法开始，从上到下依次执行
 * @Author Zhangyuhan
 * @Date 2020/9/22
 * @Version 1.0
 */

public class Demo01MainThread {
    /**
     * JVM执行main方法，main方法会进入到栈内存
     * JVM会找操作系统开辟一条main（）方法通向cpu的路径
     * cpu就可以通过这个路径来执行main（）方法
     * 而这个路径有一个名字，叫main（主）线程
     * @param args
     */
    public static void main(String[] args) {
        Person p1 = new Person("小钱");
        p1.run();
        System.out.println(0/0);
        // 报错，后面的代码就不执行了
        Person p2 = new Person("旺财");
        p2.run();

    }


}
