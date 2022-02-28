package com.study.spring5_demo1.bean;

/**
 * @ClassName Dept
 * @Description 部门类
 * @Author Zhangyuhan
 * @Date 2022/2/28
 * @Version 1.0
 */
public class Dept {
    private String dName;

    public void setdName(String dName) {
        this.dName = dName;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "dName='" + dName + '\'' +
                '}';
    }
}
