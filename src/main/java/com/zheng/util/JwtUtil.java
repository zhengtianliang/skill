package com.zheng.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/19  17:20
 * @desc: 是生成jwt token的工具类
 */

public class JwtUtil {

    private static final String key = "skill_jwt";

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/19  17:21
     * @desc: 生成jwt
     */
    public static String createJwtToken(String json) {
        // 指定加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 构建jwt
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(UUID.randomUUID().toString()) // 设置id，是为了
                .setIssuedAt(new Date()) // 设置jwt的签发时间
                //.setExpiration()       // 设置jwt的过期时间
                .setSubject(json)        // 设置主题，一般是我们自定义的属性 (传参是自定义对象的json格式)
                .signWith(signatureAlgorithm, key)   // 设置加密算法和盐
                .claim("liangge", "真帅")       // 设置一些自定义的属性，
                .claim("加油", "变有钱");
        return jwtBuilder.compact();
    }

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/19  17:30
     * @desc: 解析jwt
     */
    public static String parseJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        if (claims != null) {
            return claims.getSubject();
        } else {
            return null;
        }
    }

}
