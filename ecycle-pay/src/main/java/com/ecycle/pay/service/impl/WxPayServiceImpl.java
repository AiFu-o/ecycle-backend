package com.ecycle.pay.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ecycle.common.context.UserInfo;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import com.ecycle.pay.config.WxConfiguration;
import com.ecycle.pay.constant.OrderTypeEnum;
import com.ecycle.pay.constant.PaymentMethodEnum;
import com.ecycle.pay.constant.PaymentStatus;
import com.ecycle.pay.exception.PayOrderException;
import com.ecycle.pay.exception.WxPayException;
import com.ecycle.pay.exception.WxPayStatusException;
import com.ecycle.pay.model.PayOrder;
import com.ecycle.pay.service.PayOrderService;
import com.ecycle.pay.service.WxPayService;
import com.ecycle.pay.service.feign.CommodityFeign;
import com.ecycle.pay.web.info.WxCallBackResponse;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description TODO
 */
@Service
@Log4j2
public class WxPayServiceImpl implements WxPayService {

    @Resource
    private CommodityFeign commodityFeign;

    @Resource
    private WxConfiguration wxConfiguration;

    @Resource
    private PayOrderService payOrderService;

    private NotificationConfig wxPayConfig = null;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxCallBackResponse payNotifyCallBack(String body, Map<String, String> headers) {
        log.info("微信支付回调开始");
        NotificationParser parser = new NotificationParser(getWxPayConfig());
        Transaction transaction = parser.parse(getRequestParam(headers, body), Transaction.class);

        PayOrder payOrder = payOrderService.findByBillCode(transaction.getOutTradeNo());

        if (null == payOrder) {
            throw new PayOrderException("找不到订单: " + transaction.getOutTradeNo());
        }

        paySuccess(transaction, payOrder);
        return WxCallBackResponse.success("支付成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = WxPayStatusException.class)
    public PrepayWithRequestPaymentResponse prepay(OrderTypeEnum orderType, UUID orderId, Integer amount) {
        UserInfo currentUser = CurrentUserInfoUtils.getCurrentUserInfo();

        if (null == currentUser || StringUtils.isEmpty(currentUser.getOpenId())) {
            throw new WxPayException("用户未登录或未注册");
        }
        if (null == orderType) {
            throw new WxPayException("支付类型不能为空");
        }
        if (null == orderId) {
            throw new WxPayException("订单 id不能为空");
        }
        if (null == amount || amount.compareTo(0) <= 0) {
            throw new WxPayException("订单金额不能为空");
        }

        // 判断订单是不是已经支付或者有未支付成功的预付单
        judgeOrderStatus(orderId, orderType);

        PrepayRequest request = new PrepayRequest();
        request.setDescription(orderType.getName());
        Amount payAmount = new Amount();
        payAmount.setTotal(amount);
        Payer payer = new Payer();
        payer.setOpenid(currentUser.getOpenId());
        request.setAmount(payAmount);
        request.setPayer(payer);

        PayOrder payOrder = buildOrderRecord(orderType, orderId, amount, currentUser);

        payOrderService.save(payOrder);

        if (StringUtils.isEmpty(wxConfiguration.getPayNotifyUrl())) {
            throw new WxPayException("回调地址不能为空");
        }

        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config((Config) getWxPayConfig()).build();

        request.setAppid(wxConfiguration.getAppId());
        request.setMchid(wxConfiguration.getMerchantId());
        // 回调地址
        request.setNotifyUrl(wxConfiguration.getPayNotifyUrl());
        // 订单号
        request.setOutTradeNo(payOrder.getBillCode());
        // 调用下单方法，得到应答
        return service.prepayWithRequestPayment(request);
    }

    private Transaction queryWxOrderByBillCode(String billCode) {
        JsapiService service = new JsapiService.Builder().config((Config) getWxPayConfig()).build();
        QueryOrderByOutTradeNoRequest queryOrderByOutTradeNoRequest = new QueryOrderByOutTradeNoRequest();
        queryOrderByOutTradeNoRequest.setMchid(wxConfiguration.getMerchantId());
        queryOrderByOutTradeNoRequest.setOutTradeNo(billCode);
        return service.queryOrderByOutTradeNo(queryOrderByOutTradeNoRequest);
    }

    private void paySuccess(Transaction transaction, PayOrder payOrder) {
        // TODO 支付成功处理 要回调商品服务修改订单状态

        // 修改支付订单状态
        updatePayOrder(transaction, payOrder);

        // 回调商品服务
        commodityFeign.serviceChargeSuccessCallBack(payOrder.getOrderId());
        log.info("{} 支付成功", payOrder.getBillCode());
    }


    private void judgeOrderStatus(UUID orderId, OrderTypeEnum orderType) {
        List<PayOrder> payOrders = payOrderService.findNoPayOrderByOrderIdAndType(orderId, orderType);
        String errorMessage = null;

        for (PayOrder payOrder : payOrders) {
            // 支付中的先去查询支付状态
            if (payOrder.getStatus() == PaymentStatus.PAYING) {
                Transaction transaction = queryWxOrderByBillCode(payOrder.getBillCode());
                // 如果是支付成功 走支付成功的逻辑
                if (transaction.getTradeState() == Transaction.TradeStateEnum.SUCCESS) {
                    paySuccess(transaction, payOrder);
                } else if (transaction.getTradeState() == Transaction.TradeStateEnum.USERPAYING) {
                    errorMessage = "订单支付中";
                } else if (transaction.getTradeState() == Transaction.TradeStateEnum.CLOSED) {
                    updatePayOrder(transaction, payOrder);
                } else if (transaction.getTradeState() == Transaction.TradeStateEnum.NOTPAY) {
                    // 如果未支付关闭预付单
                    closePrepay(payOrder);
                    // 因为如果没报错肯定关闭了 所以直接改为关闭状态
                    transaction.setTradeState(Transaction.TradeStateEnum.CLOSED);
                    updatePayOrder(transaction, payOrder);
                } else {
                    updatePayOrder(transaction, payOrder);
                    errorMessage = "订单已支付";
                }
            } else {
                errorMessage = "订单已支付";
            }
        }
        // 如果有异常订单在处理完订单状态后抛异常
        if (null != errorMessage) {
            throw new WxPayStatusException(errorMessage);
        }
    }

    private void closePrepay(PayOrder payOrder) {
        JsapiService service = new JsapiService.Builder().config((Config) getWxPayConfig()).build();
        CloseOrderRequest closeOrderRequest = new CloseOrderRequest();
        closeOrderRequest.setMchid(wxConfiguration.getMerchantId());
        closeOrderRequest.setOutTradeNo(payOrder.getBillCode());
        service.closeOrder(closeOrderRequest);
    }

    private void updatePayOrder(Transaction transaction, PayOrder payOrder) {
        if (null != transaction.getSuccessTime()) {
            Date successDate = DateUtil.parse(transaction.getSuccessTime(), "yyyy-MM-dd'T'HH:mm:ssXXX");
            payOrder.setPayTime(successDate);
        }
        if (StringUtils.isNotEmpty(transaction.getTransactionId())) {
            payOrder.setTransactionId(transaction.getTransactionId());
        }
        PaymentStatus status = PaymentStatus.valueOfByWxOrderStatus(transaction.getTradeState());
        payOrder.setStatus(status);
        payOrderService.updateById(payOrder);
    }

    private PayOrder buildOrderRecord(OrderTypeEnum orderType, UUID orderId, Integer amount, UserInfo currentUser) {
        PayOrder payOrder = new PayOrder();
        payOrder.setId(UUID.randomUUID());
        payOrder.setOrderId(orderId);
        payOrder.setOrderType(orderType);
        payOrder.setAmount(amount);
        payOrder.setPaymentMethod(PaymentMethodEnum.WX_PAY);

        String billCode = buildBillCode(orderType);
        payOrder.setBillCode(billCode);
        payOrder.setCreatorId(currentUser.getUserId());
        return payOrder;
    }

    private String buildBillCode(OrderTypeEnum orderTypeEnum) {
        String enumStr = orderTypeEnum.getBillCodeEnum();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String time = sdf.format(new Date());
        String randomString = RandomStringUtils.randomNumeric(8);
        if (enumStr.length() > 8) {
            enumStr = enumStr.substring(0, 8);
        }
        return enumStr + "WX" + time + randomString;
    }

    private NotificationConfig getWxPayConfig() {
        if (null == wxPayConfig) {
            wxPayConfig = new RSAAutoCertificateConfig.Builder()
                    .merchantId(wxConfiguration.getMerchantId())
                    .privateKeyFromPath(getPrivateKeyFromPath())
                    .merchantSerialNumber(wxConfiguration.getMerchantSerialNumber())
                    .apiV3Key(wxConfiguration.getApiV3Key())
                    .build();
        }
        return wxPayConfig;
    }

    private String getPrivateKeyFromPath() {
        ClassPathResource classPathResource = new ClassPathResource(wxConfiguration.getPrivateKeyFilePath());
        return classPathResource.getPath();
    }

    private RequestParam getRequestParam(Map<String, String> headers, String body) {
        String signature = headers.get("wechatpay-signature");
        String nonce = headers.get("wechatpay-nonce");
        String serialNumber = headers.get("wechatpay-serial");
        String timestamp = headers.get("wechatpay-timestamp");
        return new RequestParam.Builder()
                .serialNumber(serialNumber)
                .nonce(nonce)
                .signature(signature)
                .timestamp(timestamp)
                .body(body)
                .build();
    }

}
