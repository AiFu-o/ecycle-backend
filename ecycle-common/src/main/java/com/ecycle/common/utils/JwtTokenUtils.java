package com.ecycle.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.ecycle.common.constants.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SecurityException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.servlet.http.HttpServletRequest;

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
     * @param permanentValidity 是否长期有效
     * @return 令牌
     */
    public static String createToken(UUID userId, Map<String, Object> claims, Boolean permanentValidity) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder().subject(userId.toString())
                .issuedAt(new Date()).claims(claims)
                .signWith(SECRET_KEY, ALGORITHM);
        if (!permanentValidity) {
            builder.expiration(new Date(now.getTime() + EXPIRATION_SECONDS * 1000));
        }
        return builder.compact();
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
    public static UUID getUserId(String token) {
        Claims claims = parseToken(token);
        if (null == claims) {
            return null;
        }
        return UUID.fromString(claims.getSubject());
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

    public static String getToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader(AUTHENTICATION);
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(PREFIX)) {
            token = token.replaceFirst(PREFIX, StringUtils.EMPTY);
        }
        return token;
    }

    /**
     * 获取当前用户 id
     *
     * @return userId
     */
    public static UUID getCurrentUserId() {
        String token = getToken();
        if (StringUtils.isEmpty(token)) {
            throw new NullPointerException("令牌不能为空");
        }
        return getUserId(token);
    }

}
