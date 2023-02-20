package com.itheima.shiro.core.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.itheima.shiro.config.JwtProperties;
import com.itheima.shiro.utils.EmptyUtil;
import com.itheima.shiro.utils.EncodesUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义jwtToken管理者
 */
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class JwtTokenManager {
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 签发令牌
     * 1.头部信息：密码，加密算法
     * 2.payload：签发的时间，唯一标识，签发者，过期的时间
     * 3.加密签名
     *
     * @param iss       签发者
     * @param ttlMills  过期时间
     * @param sessionId 会话id
     * @param claims    jwt存储的非隐私信息
     */
    public String issuedToken(String iss, long ttlMills, String sessionId, Map<String, Object> claims) {
        if (EmptyUtil.isNullOrEmpty(claims)) {
            claims = new HashMap<>();
        }
        // 获取当前时间
        long nowMills = System.currentTimeMillis();
        // 获取加密签名
        String base64EncodeSecretKey = EncodesUtil.encodeBase64(jwtProperties.getBase64EncodeSecretKey().getBytes());
        // 构建令牌
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)// 构建非隐私信息
                .setId(sessionId)// 构建唯一标识,此时使用shiro生成的唯一id
                .setIssuedAt(new Date(nowMills))// 构建签发时间
                .setSubject(iss)// 签发者
                .signWith(SignatureAlgorithm.HS256, base64EncodeSecretKey);// 指定算法和密钥
        if (ttlMills > 0) {
            builder.setExpiration(new Date(nowMills + ttlMills));
        }

        return builder.compact();
    }

    /**
     * 解析令牌
     */
    public Claims decodeToken(String jwtToken) {
        // 获取加密签名
        String base64EncodeSecretKey = EncodesUtil.encodeBase64(jwtProperties.getBase64EncodeSecretKey().getBytes());
        // 带着密码去解析字符串
        return Jwts.parser().setSigningKey(base64EncodeSecretKey)
                .parseClaimsJws(jwtToken).getBody();
    }

    /**
     * 校验令牌:1.校验头部信息和载荷信息是否被篡改 2.校验令牌是否过期
     */
    public boolean isVerifyToken(String jwtToken) {
        // 带着签名构建对象
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getBase64EncodeSecretKey().getBytes());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        // 校验：如果校验不通过，则会抛出异常
        jwtVerifier.verify(jwtToken);
        return true;
    }
}
