package com.study.springbootstarterhelloautoconfigure.hello.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName HelloProperties
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/5/10
 * @Version 1.0
 */

@ConfigurationProperties("hello")
public class HelloProperties {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
