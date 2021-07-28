package com.zh.EmailDemo;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @ClassName EmailDemo
 * @Description java发送电子邮件，使用qq邮箱给google发送邮件
 * @Author Zhangyuhan
 * @Date 2021/7/28
 * @Version 1.0
 */
public class EmailDemo {
    public static void main(String[] args) {
        // 先登陆上qq邮箱

        // 创建会话之前设置一些参数
        Properties properties = new Properties();
        // mail.host是固定的。smtp.qq.com可以换成smtp.123.com等等(发件人的邮箱)
        properties.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件(这俩参数是固定的)
        properties.setProperty("mail.transport.protocol", "smtp");

        // 会话
        Session session = Session.getDefaultInstance(properties);

        Transport ts = null;
        try {
            // 获取传输对象,该对象可以发送邮件
            ts = session.getTransport();

            // 发送邮件之前，校验账号和密码
            // 密码不是qq号的密码，是邮箱的授权码
            ts.connect("1355166049@qq.com", "tgwdqbestcxuifab");

            // 构建一封邮件
            MimeMessage message = new MimeMessage(session);

            // 发件人
            message.setFrom(new InternetAddress("1355166049@qq.com"));

            // 收件人
            // Message.RecipientType.TO 表示收件人
            // Message.RecipientType.CC 抄送
            // Message.RecipientType.BCC 暗送(密送)
            message.setRecipient(
                    Message.RecipientType.TO, new InternetAddress("zhangyuhannerv@gmail.com"));

            // 主题
            // message.setSubject("测试用java代码从qq向谷歌发送邮件");

            // 激活邮件
            message.setSubject("激活邮件");

            // 正文
            // message.setContent("测试字符串", "text/html;charset=UTF-8");

            message.setContent(
                    "亲，欢迎您注册xxx网站,点击连接激活用户。<a href='http://www.baidu.com'>点击激活</a>",
                    "text/html;charset=utf-8");

            // 保存邮件
            message.saveChanges();

            // 发送邮件
            // 第一个是message对象;第二个是发给谁，使用自带的方法获取到所有的接收者
            ts.sendMessage(message, message.getAllRecipients());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ts != null) {
                try {
                    ts.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
