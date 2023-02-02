package com.study.shiro.tools;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;

/**
 * 编码工具类
 */
public class EncodesUtil {
    /**
     * HEX编码
     * HEX-String-byte[]
     *
     * @param input 字节数组
     * @return 字符串
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeToString(input);
    }

    /**
     * HEX解码
     * HEX-byte[]-string
     *
     * @param input 输入字符串
     * @return 字节数组
     */
    public static byte[] decodeHex(String input) {
        return Hex.decode(input);
    }

    /**
     * Base64编码
     * Base64-String-byte[]
     *
     * @param input 字节数组
     * @return 字符串
     */
    public static String encodeBase64(byte[] input) {
        return Base64.encodeToString(input);
    }

    /**
     * Base64解码
     * Base64-byte[]-string
     *
     * @param input 输入字符串
     * @return 字节数组
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decode(input);
    }
}
