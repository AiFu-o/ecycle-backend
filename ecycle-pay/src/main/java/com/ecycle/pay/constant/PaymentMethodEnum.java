package com.ecycle.pay.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description TODO
 */
@Getter
public enum PaymentMethodEnum {

    /**
     * 微信支付
     */
    WX_PAY("WX_PAY", "微信支付");

    @EnumValue
    private final String value;

    private final String name;

    PaymentMethodEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
