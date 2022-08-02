package com.study.srb.sms.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.google.gson.Gson;
import com.study.srb.exception.BusinessException;
import com.study.srb.result.ResponseEnum;
import com.study.srb.sms.service.SmsService;
import com.study.srb.sms.util.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }


    @Override
    public void send(String mobile, String templateCode, Map<String, Object> param) {
        Client client = null;
        try {
            client = createClient(SmsProperties.KEY_ID, SmsProperties.KEY_SECRET);
        } catch (Exception e) {
            log.error("创建阿里云短信发送客户端失败");
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR, e);
        }

        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(SmsProperties.SIGN_NAME)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(mobile)
                .setTemplateParam(new Gson().toJson(param));
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);


            // 标准的四大返回参数,代表什么去阿里的sdk文档里查
//            sendSmsResponse.getBody().getMessage();
//            sendSmsResponse.getBody().getCode();
//            sendSmsResponse.getBody().getBizId();
//            sendSmsResponse.getBody().getRequestId();


            // 业务失败的处理
            String code = sendSmsResponse.getBody().getCode();
            String message = sendSmsResponse.getBody().getMessage();

            if (!"OK".equals(code)) {
                log.warn("短信发送失败：{}", message);
                throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR);
            }

        } catch (BusinessException e) {
            throw e;
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            // 打印日志
            log.error("阿里云短信发送调用sdk失败:{},{}", error.getCode(), error.getMessage());
            // 返回给前端错误
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR, error);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            // 打印日志
            log.error("阿里云短信发送调用sdk失败:{},{}", error.getCode(), error.getMessage());
            // 返回给前端错误
            throw new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR, error);
        }
    }
}
