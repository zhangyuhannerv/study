package com.study.zyh.javase.java_genericity.generic_WildcardType.wildcardType_UpperLimit;

/**
 * @ClassName Cat
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/12/6
 * @Version 1.0
 */
public class Cat extends Animal {
    protected int age;

    public Cat() {
    }

    public Cat(String name, int age) {
        super(name);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
