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
    // serialVersionUID 序列化的版本号，可以提高序列化的兼容性
    // 当有序列号时，当这个类改变，java不会认位它是一个新的类，而是将改变后的类看作原先类的升级版
    private static final Long serialVersionUID = 1L;

    // transient修饰的成员变量在序列化时不会被序列化
    private transient String color;

    // static修饰的成员变量在序列化时不会被序列化
    private static String nation;

    // 如果一个Dog实例对Master属性赋了值，那么Master必须实现Serializable接口，否则该Dog实列序列化报错
    // 如果Dog实例的Master属性是空值，那么此时Master即使没有实现Serializable接口，那么该Dog实例还是可以序列化的
    private Master master = new Master();

    private String name;
    private int age;

    public Dog() {
    }

    public Dog(String name, int age, String nation, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
        this.nation = nation;
    }

    // toString()是后加的，所以要重新序列化一次
    // 任何后续在该类上的变动，都要重新序列化一次，否则反序列化就会报错(底层是版本不一致的问题)
    @Override
    public String toString() {
        return "Dog{" +
                "color='" + color + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}' + nation + " master是" + master;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static String getNation() {
        return nation;
    }

    public static void setNation(String nation) {
        Dog.nation = nation;
    }
}
