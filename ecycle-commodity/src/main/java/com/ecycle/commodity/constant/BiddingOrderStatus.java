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
     * 竞价失败
     */
    BIDDING_ERROR("BIDDING_ERROR","竞价失败"),

    /**
     * 待支付
     */
    PENDING_PAYMENT("PENDING_PAYMENT","待支付"),

    /**
     * 支付成功
     */
    PAYMENT_SUCCESS("PAYMENT_SUCCESS","支付成功"),

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
     * 已取消
     */
    CANCEL("CANCEL","已取消");

    @EnumValue
    private final String value;

    private final String name;

    BiddingOrderStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
