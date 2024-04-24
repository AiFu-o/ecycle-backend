package com.ecycle.auth.filter;

import lombok.Data;

/**
 * @author wangweichen
 * @Date 2024/4/24
 * @Description 微信登录信息
 */
@Data
public class WxPrincipal {
    /**
     * encryptedData
     */
    private String encryptedData;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * openId
     */
    private String openId;
    /**
     * 用来自动登录的手机号和 openId 加密字符串
     */
    private String iv;
}
