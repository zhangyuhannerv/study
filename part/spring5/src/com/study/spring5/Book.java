package com.study.spring5;

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

    // 创建属性对应的构造方法
    public void setbName(String bName) {
        this.bName = bName;
    }

    public void setbAuthor(String bAuthor) {
        this.bAuthor = bAuthor;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bName='" + bName + '\'' +
                ", bAuthor='" + bAuthor + '\'' +
                '}';
    }
}
