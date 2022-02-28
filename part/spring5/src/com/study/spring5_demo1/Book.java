package com.study.spring5_demo1;

/**
 * @ClassName Book
 * @Description 演示使用set方法进行属性的注入
 * @Author Zhangyuhan
 * @Date 2022/2/24
 * @Version 1.0
 */
public class Book {
    // 创建属性
    private String bName;

    private String bAuthor;

    private String address;

    // 创建属性对应的构造方法
    public void setbName(String bName) {
        this.bName = bName;
    }

    public void setbAuthor(String bAuthor) {
        this.bAuthor = bAuthor;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bName='" + bName + '\'' +
                ", bAuthor='" + bAuthor + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
