package com.study.spring5_demo2.bean;

/**
 * @ClassName Orders
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/2
 * @Version 1.0
 */
public class Orders {
    private String oName;

    public Orders() {
        System.out.println("第一步，执行的无参构造，创建了bean实例");
    }

    public void setoName(String oName) {
        System.out.println("第二步，执行set()赋值");
        this.oName = oName;
    }

    // 创建执行的初始化的方法
    public void initMethod() {
        System.out.println("第三步，执行初始化的方法");
    }

    // 创建销毁的方法
    public void destroyMethod() {
        System.out.println("第五步，最后一步，执行了bean销毁的方法");
    }


    @Override
    public String toString() {
        return "Orders{" +
                "oName='" + oName + '\'' +
                '}';
    }
}
