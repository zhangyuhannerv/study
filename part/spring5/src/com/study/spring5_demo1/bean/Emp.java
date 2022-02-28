package com.study.spring5_demo1.bean;

/**
 * @ClassName Emp
 * @Description 员工类
 * @Author Zhangyuhan
 * @Date 2022/2/28
 * @Version 1.0
 */
public class Emp {
    private String eName;

    // 员工属于某一个部门
    private Dept dept;

    // 行别
    private String gender;

    public Dept getDept() {
        return dept;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "eName='" + eName + '\'' +
                ", dept=" + dept +
                ", gender='" + gender + '\'' +
                '}';
    }
}
