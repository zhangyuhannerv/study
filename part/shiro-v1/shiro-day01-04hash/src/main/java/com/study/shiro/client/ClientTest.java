package com.study.shiro.client;

import com.study.shiro.tools.DigestsUtil;
import com.study.shiro.tools.EncodesUtil;
import org.junit.Test;

import java.util.Map;

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

    @Test
    public void testDigestsUtil() {
        Map<String, String> map = DigestsUtil.entryptPassword("123");
        System.out.println(map);
    }
}
