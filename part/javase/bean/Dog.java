package bean;

import java.io.Serializable;

/**
 * @ClassName Dog
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/9/22
 * @Version 1.0
 */
public class Dog implements Serializable {
    private String name;
    private int age;

    public Dog() {
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
