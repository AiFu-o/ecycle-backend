package com.ecycle.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;

import com.ecycle.common.constants.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SecurityException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

/**
 * @author wangweichen
 * @Date 2024/4/2
 * @Description jwt-token 工具类
 */
public class JwtTokenUtils implements TokenConstants {

    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;

    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据
     * @return 令牌
     */
    public static String createToken(String userId, Map<String, Object> claims) {
        Date now = new Date();
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(now.getTime() + EXPIRATION_SECONDS * 1000))
                .claims(claims)
                .signWith(SECRET_KEY, ALGORITHM)
                .compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SecurityException e) {
            return null;
        }
    }

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserId(String token) {
        Claims claims = parseToken(token);
        if (null == claims) {
            return null;
        }
        return claims.getSubject();
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key    键
     * @return 值
     */
    public static String getValue(Claims claims, String key) {
        return claims.get(key).toString();
    }

}
