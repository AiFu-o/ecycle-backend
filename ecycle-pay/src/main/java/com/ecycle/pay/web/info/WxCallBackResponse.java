package com.ecycle.pay.web.info;

import lombok.Data;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description 微信支付回调返回
 */
@Data
public class WxCallBackResponse {
    private String code;
    private String message;

    public static WxCallBackResponse success(String message){
        WxCallBackResponse response = new WxCallBackResponse();
        response.setCode("SUCCESS");
        response.setMessage(message);
        return response;
    }

    public static WxCallBackResponse error(String message){
        WxCallBackResponse response = new WxCallBackResponse();
        response.setCode("ERROR");
        response.setMessage(message);
        return response;
    }
}
