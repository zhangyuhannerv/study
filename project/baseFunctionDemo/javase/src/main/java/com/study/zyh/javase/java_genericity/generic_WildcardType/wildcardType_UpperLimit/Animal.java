package com.study.zyh.javase.java_genericity.generic_WildcardType.wildcardType_UpperLimit;

/**
 * @ClassName Animal
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/12/6
 * @Version 1.0
 */
public class Animal {
    protected String name;

    public Animal() {
    }

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                '}';
    }
}
