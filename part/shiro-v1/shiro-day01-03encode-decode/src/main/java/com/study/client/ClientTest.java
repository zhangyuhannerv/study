package com.study.client;

import com.study.tools.EncodesUtil;
import org.junit.Test;

/**
 * 测试编码、解码
 */
public class ClientTest {
    @Test
    public void testHex() {
        String val = "hello";
        String flag = EncodesUtil.encodeHex(val.getBytes());
        String valHandler = new String(EncodesUtil.decodeHex(flag));
        System.out.println(flag);
        System.out.println("比较结果：" + val.equals(valHandler));
    }

    @Test
    public void testBase64() {
        String val = "hello";
        String flag = EncodesUtil.encodeBase64(val.getBytes());
        String valHandler = new String(EncodesUtil.decodeBase64(flag));
        System.out.println(flag);
        System.out.println("比较结果：" + val.equals(valHandler));
    }
}
