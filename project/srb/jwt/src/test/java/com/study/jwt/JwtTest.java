package com.study.jwt;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class JwtTest {
    private static long tokenExpiration = 1000 * 60 * 60 * 24;// 一天
    private static String tokenSignKye = "study123";// 签名密钥

    public String createdToken() {
        JwtBuilder jwtBuilder = Jwts.builder();
        String token = jwtBuilder
                // 头
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                // 载荷:自定义信息
                .claim("nickname", "ichi")
                .claim("avatar", "1")
                .claim("role", "admin")
                // 载荷：默认信息
                .setSubject("srb-user")// 主题
                .setIssuer("zyh")// 签发者
                .setAudience("lst")// 接收方
                .setIssuedAt(new Date())// 令牌的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)) // 令牌的过期时间
//                .setNotBefore(new Date(System.currentTimeMillis() + 1000 * 20))// 令牌的生效时间(这里是签发的20秒之后才生效)
                .setId(UUID.randomUUID().toString()) //令牌的唯一标识（id）
                // 签名哈希
                .signWith(SignatureAlgorithm.HS256, tokenSignKye)
                // 组装jwt字符串
                .compact();
        return token;
    }

    @Test
    public void seeToken() {
        System.out.println(createdToken());
    }

    @Test
    public void testGetUserInfo() {
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(tokenSignKye).parseClaimsJws(createdToken());

        Claims claims = claimsJws.getBody();
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");
        String role = (String) claims.get("role");

        System.out.println(nickname);
        System.out.println(avatar);
        System.out.println(role);

        String id = claims.getId();
        System.out.println(id);
    }

}
