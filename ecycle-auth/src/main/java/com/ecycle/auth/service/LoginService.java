package com.ecycle.auth.service;

import com.ecycle.common.context.RestResponse;

/**
 * @author wangweichen
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return Result<String>
     */
    RestResponse<String> doLogin(String username, String password);

}
