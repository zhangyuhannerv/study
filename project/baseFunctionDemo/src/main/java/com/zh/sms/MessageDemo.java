package com.zh.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @ClassName MessageDemo
 * @Description 利用阿里云发送短信(本文只是一个小demo, 推荐看官方sdk)
 * @Author Zhangyuhan
 * @Date 2021/8/1
 * @Version 1.0
 */
public class MessageDemo {
    // 使用阿里云短信服务的前提:
    // 查看官方文档申请短信签名(短信标题)，短信模板(短信内容)和api使用权限。
    // 这里都没有申请，所以肯定发送失败。主要是看代码

    public static void main(String[] args) {
        // api使用密钥（账号和密码)
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<accessKeyId>", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        // 要发送给的手机号
        request.putQueryParameter("PhoneNumbers", "1503871****");
        // 短信签名
        request.putQueryParameter("SignName", "阿里大于测试专用");
        // 短信模板
        request.putQueryParameter("TemplateCode", "SMS_209335004");
        // 短信模板里的变量替换，如：您的验证码是${code}。
        request.putQueryParameter("TemplateParam", "{\"code\":\"1111\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
