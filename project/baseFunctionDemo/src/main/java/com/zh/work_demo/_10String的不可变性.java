package com.zh.work_demo;

import lombok.Data;
import lombok.ToString;

public class _10String的不可变性 {
    private String str = new String("good");
    private char[] ch = {'t', 'e', 's', 't'};
    private StringUser user = new StringUser("张三");

    public void change(String str, char[] ch, StringUser user) {
        str = "test ok";
        ch[0] = 'b';
        user = new StringUser("李四");
//        user.setName("李四");
    }

    public static void main(String[] args) {
        _10String的不可变性 demo = new _10String的不可变性();
        demo.change(demo.str, demo.ch, demo.user);
        System.out.println(demo.str);
        System.out.println(demo.ch);
        System.out.println(demo.user);
    }
}

@ToString
@Data
class StringUser {
    private String name;

    public StringUser(String name) {
        this.name = name;
    }
}

