package com.study.spring5_log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName UserLog
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/11
 * @Version 1.0
 */
public class UserLog {
    // 注意Logger是org.slf4j里的。
    // 传入的参数是当前类的class。
    private static final Logger log = LoggerFactory.getLogger(UserLog.class);

    public static void main(String[] args) {
        log.info("这是info信息");
        log.warn("这是warn信息");
        log.error("这是error信息");
    }
}
