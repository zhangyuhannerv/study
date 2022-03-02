package com.study.spring5_demo2.autowire;

/**
 * @ClassName Emp
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/2
 * @Version 1.0
 */
public class Emp {
    private Dept dept;

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "dept=" + dept +
                '}';
    }

    public void test() {
        System.out.println(dept);
    }
}
