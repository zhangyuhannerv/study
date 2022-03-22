package com.study.springboot2study.bean;

/**
 * @ClassName Pet
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/22
 * @Version 1.0
 */
public class Pet {
    private String name;

    public Pet() {
    }

    public Pet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
