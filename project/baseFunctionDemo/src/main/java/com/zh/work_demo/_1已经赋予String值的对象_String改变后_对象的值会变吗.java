package com.zh.work_demo;

/**
 * @ClassName _1已经赋予String值的对象_String改变后_对象的值会变吗
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/9/3
 * @Version 1.0
 */
public class _1已经赋予String值的对象_String改变后_对象的值会变吗 {
    public static void main(String[] args) {
        User user = new User();
        user.setName("张雨晗");
        System.out.println(user.getName());

        String str = "张三";
        user.setName(str);
        System.out.println(user.getName());

        str = "李四";
        System.out.println(user.getName());

        // 结论，已经赋予对象某个String值后，后续这个String值再变，对象的值不会变
    }
}

class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
