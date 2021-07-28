package com.zh.EmailDemo;

/**
 * @ClassName EmailUtilTest
 * @Description 测试发送邮件的工具类是否好使
 * @Author Zhangyuhan
 * @Date 2021/7/29
 * @Version 1.0
 */
public class EmailUtilTest {
    public static void main(String[] args) {
        EmaiUtil.sendEmail("zhangyuhannerv@gmail.com", "测试工具类", "好使");
    }
}
