package com.itheima.shiro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "itheima.framework.jwt")
public class JwtProperties {
    /**
     * 签名密码
     */
    private String base64EncodeSecretKey;
}
