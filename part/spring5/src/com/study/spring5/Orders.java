package com.study.spring5;

/**
 * @ClassName Orders
 * @Description 使用有参数的构造
 * @Author Zhangyuhan
 * @Date 2022/2/25
 * @Version 1.0
 */
public class Orders {
    private String oName;
    private String address;

    public Orders(String oName, String address) {
        this.oName = oName;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oName='" + oName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
