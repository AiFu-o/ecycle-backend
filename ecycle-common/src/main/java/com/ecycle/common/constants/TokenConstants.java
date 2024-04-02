package com.ecycle.common.constants;

import lombok.Data;

import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/2
 * @Description token存储的 key 常量
 */
public interface TokenConstants {

    /**
     * redis key 前缀
     */
    String REDIS_PREFIX = "LOGIN_TOKEN_";

    /**
     * 令牌前缀
     */
    String PREFIX = "Bearer ";

    /**
     * 令牌秘钥
     */
    String SECRET = "1a6900bc166443d4b617374ca7330ebf";

    /**
     * 令牌自定义标识
     */
    String AUTHENTICATION = "Authorization";

    /**
     * 到期时间(秒)
     */
    Integer EXPIRATION_SECONDS = 3600;

    /**
     * token
     */
    String F_TOKEN = "token";

    /**
     * 用户
     */
    String F_USER = "user";

    /**
     * 用户id
     */
    String F_USER_ID = "userId";

    /**
     * 用户名
     */
    String F_USERNAME = "username";

    /**
     * 权限
     */
    String F_AUTHORITIES = "authorities";

    /**
     * 角色
     */
    String F_ROLES = "roles";
}
