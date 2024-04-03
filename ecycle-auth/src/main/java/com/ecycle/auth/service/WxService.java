package com.ecycle.auth.service;

import com.ecycle.auth.model.User;

import java.util.Map;

/**
 * @author wangweichen
 */
public interface WxService {

    /**
     * 获取微信公众平台 accessToken
     *
     * @return accessToken
     */
    String getAccessToken();

    /**
     * 获取小程序 openId
     *
     * @param jsCode jsCode
     * @return openId 和 sessionId
     */
    Map<String, String> getMiniAppSessionId(String jsCode);

    /**
     * 获取小程序用户手机号
     *
     * @param phoneCode phoneCode
     * @param openId openId
     * @return 手机号
     */
    String getTelephone(String phoneCode, String openId);

    /**
     * 校验 sessionId 的有效性
     *
     * @param openId openId
     * @param sessionId sessionId
     * @return 是否有效
     */
    Boolean checkSessionId(String openId, String sessionId);
}
