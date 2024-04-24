package com.ecycle.auth.service.impl;

import com.ecycle.common.context.UserInfo;
import com.ecycle.auth.exception.UserException;
import com.ecycle.auth.service.UserInfoService;
import com.ecycle.common.constants.TokenConstants;
import com.ecycle.common.utils.JwtTokenUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author wangweichen
 * @Date 2024/4/17
 * @Description 用户信息 service
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveCurrentUserInfo(UUID userId, UserInfo userInfo, Boolean permanentValidity) {
        if(permanentValidity){
            redisTemplate.opsForValue().set(TokenConstants.REDIS_PREFIX + userId, userInfo);
        } else {
            redisTemplate.opsForValue().set(TokenConstants.REDIS_PREFIX + userId, userInfo, TokenConstants.EXPIRATION_SECONDS, TimeUnit.SECONDS);
        }
    }

    @Override
    public UserInfo getCurrentUserInfo() {
        UUID userId;
        try {
            userId = JwtTokenUtils.getCurrentUserId();
        } catch (Exception e) {
            throw new UserException("用户未登录");
        }
        Object userInfoObj = redisTemplate.opsForValue().get(TokenConstants.REDIS_PREFIX + userId);
        if (null == userInfoObj) {
            throw new UserException("用户未登录");
        }
        return (UserInfo) userInfoObj;
    }

    @Override
    public void refreshCurrentUserInfoTime() {
        UUID userId;
        try {
            userId = JwtTokenUtils.getCurrentUserId();
        } catch (Exception e) {
            throw new UserException("用户未登录");
        }
        redisTemplate.opsForValue().getOperations().expire(TokenConstants.REDIS_PREFIX + userId, TokenConstants.EXPIRATION_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public void delCurrentUserInfo() {
        UUID userId;
        try {
            userId = JwtTokenUtils.getCurrentUserId();
        } catch (Exception e) {
            throw new UserException("用户未登录");
        }
        redisTemplate.delete(TokenConstants.REDIS_PREFIX + userId);
    }

}
