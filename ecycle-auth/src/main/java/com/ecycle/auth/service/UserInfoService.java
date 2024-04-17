package com.ecycle.auth.service;

import com.ecycle.auth.context.UserInfo;
import com.ecycle.auth.model.User;
import com.ecycle.common.context.RestResponse;

import java.util.Map;
import java.util.UUID;

/**
 * @author wangweichen
 */
public interface UserInfoService {

    /**
     * 保存当前用户信息存储到 redis
     *
     * @param userId   用户 id
     * @param userInfo 用户信息
     */
    void saveCurrentUserInfo(UUID userId, UserInfo userInfo);


    /**
     * 从 redis 获取当前用户信息
     *
     * @return 用户信息
     */
    UserInfo getCurrentUserInfo();

    /**
     * 刷新当前用户信息的存储时间
     */
    void refreshCurrentUserInfoTime();

}
