package com.study.spring5_demo2.collectionType;

/**
 * @ClassName Course
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/1
 * @Version 1.0
 */
public class Course {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                '}';
    }
}
