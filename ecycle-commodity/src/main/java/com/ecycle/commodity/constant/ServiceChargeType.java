package com.ecycle.commodity.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author wangweichen
 * 服务费类型
 */
@Getter
public enum ServiceChargeType {

    /**
     * 按比例支付
     */
    RATIO_PAYMENT("RATIO_PAYMENT", "按比例支付"),

    /**
     * 定额支付
     */
    FIXED_PAYMENT("FIXED_PAYMENT", "定额支付");

    @EnumValue
    private final String value;

    private final String name;

    ServiceChargeType(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
