package com.ecycle.pay.service;

import com.ecycle.pay.constant.OrderTypeEnum;
import com.ecycle.pay.web.info.WxCallBackResponse;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 微信支付 service
 */
public interface WxPayService {

    /**
     * 支付成功回调
     * @param body 回调参数
     * @param headers 请求头
     * @return 回调回执参数
     */
    WxCallBackResponse payNotifyCallBack(String body, Map<String, String> headers);

    /**
     * 创建预付单
     * @param orderType 被支付订单类型
     * @param orderId 被支付订单 id
     * @param amount 订单金额
     * @return 预付单参数
     */
    PrepayWithRequestPaymentResponse prepay(OrderTypeEnum orderType, UUID orderId, Integer amount);
}
