package annotation.demo;

/**
 * @ClassName Calculator
 * @Description 计算机类
 * @Author Zhangyuhan
 * @Date 2020/9/21
 * @Version 1.0
 */
public class Calculator {

    // 加法
    @Check
    public void add() {
        String str = null;
        str.indexOf(1);
        System.out.println("1+0=" + (1 + 0));
    }

    // 减法
    @Check
    public void sub() {
        System.out.println("1-0=" + (1 - 0));
    }

    // 乘法
    @Check
    public void mul() {
        System.out.println("1*0=" + (1 * 0));
    }

    // 除法
    @Check
    public void div() {
        System.out.println("1/0=" + (1 / 0));
    }

    public void show() {
        System.out.println("永无bug。。。");
    }
}
