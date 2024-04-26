package com.ecycle.pay.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.wechat.pay.java.service.payments.model.Transaction;
import lombok.Getter;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description 支付状态
 */
@Getter
public enum PaymentStatus {

    /**
     * 未支付
     */
    NOT_PAY("NOT_PAY", "未支付"),
    /**
     * 支付中
     */
    PAYING("PAYING", "支付中"),
    /**
     * 支付成功
     */
    SUCCESS("SUCCESS", "未支付"),
    /**
     * 已关闭
     */
    CLOSED("CLOSED", "已关闭"),
    /**
     * 部分退款
     */
    PART_REFUND("PART_REFUND", "部分退款"),
    /**
     * 已退款
     */
    REFUND("REFUND", "已退款");

    @EnumValue
    private final String value;

    private final String name;

    public static PaymentStatus valueOfByWxOrderStatus(Transaction.TradeStateEnum tradeStateEnum) {
        PaymentStatus result;
        switch (tradeStateEnum) {
            case SUCCESS:
                result = PaymentStatus.SUCCESS;
                break;
            case NOTPAY:
                result = PaymentStatus.NOT_PAY;
                break;
            case REFUND:
                result = PaymentStatus.REFUND;
                break;
            case CLOSED:
            case PAYERROR:
                result = PaymentStatus.CLOSED;
                break;
            case USERPAYING:
                result = PaymentStatus.PAYING;
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    PaymentStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
