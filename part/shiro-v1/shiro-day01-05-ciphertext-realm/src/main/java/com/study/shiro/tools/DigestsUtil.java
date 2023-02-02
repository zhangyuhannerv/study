package com.study.shiro.tools;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.HashMap;
import java.util.Map;

/**
 * 生成摘要（shiro的hash算法)
 */
public class DigestsUtil {
    public static final String SHA1 = "SHA-1";
    public static final Integer ITERATIONS = 512;

    /**
     * sha1摘要算法
     *
     * @param input 明文字符串
     * @param salt  干扰数据
     * @return
     */
    public static String sha1(String input, String salt) {
        return new SimpleHash(SHA1, input, salt, ITERATIONS).toString();
    }

    /**
     * 获得随机生成的salt
     *
     * @return hex编码的salt
     */
    public static String generateSalt() {
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        return randomNumberGenerator.nextBytes().toHex();
    }

    /**
     * 生成salt和密文密码
     *
     * @param passwordPlan 明文密码
     * @return map->salt和密文密码
     */
    public static Map<String, String> entryptPassword(String passwordPlan) {
        Map<String, String> map = new HashMap<>();
        String salt = generateSalt();
        String password = sha1(passwordPlan, salt);
        map.put("salt", salt);
        map.put("password", password);
        // 后续会将salt和密文密码password存到数据库里
        return map;
    }
}
