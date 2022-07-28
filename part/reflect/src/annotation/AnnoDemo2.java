package annotation;

import java.util.Date;

/**
 * @ClassName AnnoDemo2
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2020/9/15
 * @Version 1.0
 */

// @SupperWarnings注解用来压制警告，传递参数all就是用来压制所有的警告，可以用在任何地方，但是一般用在类上用来压制有关该类的所有警告
@SuppressWarnings("all")
public class AnnoDemo2 {
    @Override
    public String toString() {
        return super.toString();
    }

    // Deprecated用来标注show1方法已经过时
    @Deprecated
    public void show1() {
        // 有缺陷

    }

    public void show2() {
        //替代show1方法
    }

    public void demo() {
        // show1已经过期，但是还是可以正常使用
        show1();
        Date date = new Date();
    }
}
