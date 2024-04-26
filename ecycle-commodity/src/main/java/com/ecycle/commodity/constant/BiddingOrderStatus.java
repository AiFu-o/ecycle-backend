package com.ecycle.commodity.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author wangweichen
 * @Date 2024/4/18
 * @Description 订单状态
 */
@Getter
public enum BiddingOrderStatus {

    /**
     * 竞价中
     */
    BIDDING("BIDDING","竞价中"),

    /**
     * 待支付
     */
    PENDING_PAYMENT("PENDING_PAYMENT","待支付"),

    /**
     * 支付失败
     */
    PAYMENT_ERROR("PAYMENT_ERROR","支付失败"),

    /**
     * 待上门
     */
    PENDING_VISIT("PENDING_VISIT","待上门"),

    /**
     * 已上门
     */
    VISITED("VISITED","已上门"),

    /**
     * 已退款
     */
    REFUNDED("REFUNDED","已退款"),

    /**
     * 待评价
     */
    PENDING_REVIEW("PENDING_REVIEW","待评价"),

    /**
     * 已完成
     */
    COMPLETED("COMPLETED","已完成"),

    /**
     * 竞价关闭
     */
    CLOSED("CLOSED","竞价关闭");

    @EnumValue
    private final String value;

    private final String name;

    BiddingOrderStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
