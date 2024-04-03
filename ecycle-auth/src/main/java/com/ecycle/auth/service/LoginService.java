package com.ecycle.auth.service;

import com.ecycle.common.context.RestResponse;

import java.util.Map;

/**
 * @author wangweichen
 */
public interface LoginService {

    /**
     * 用户名密码登录
     *
     * @param username 用户名
     * @param password 密码
     * @return Result<String> token
     */
    RestResponse<String> doLogin(String username, String password);


    /**
     * 微信小程序登录
     *
     * @param requestParams jsCode 和 phoneCode
     * @return Result<String> token
     */
    RestResponse<String> doWxMiniAppLogin(Map<String, String> requestParams);
}
