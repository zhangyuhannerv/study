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
    // toString()是后加的，所以要重新序列化一次
    // 任何后续在该类上的变动，都要重新序列化一次，否则反序列化就会报错(底层是版本不一致的问题)

    private String name;
    private int age;

    public Dog() {
    }

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
