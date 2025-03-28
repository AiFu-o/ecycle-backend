package com.ecycle.pay.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author wangweichen
 * @Description 订单类型
 */
@Getter
public enum OrderTypeEnum {

    /**
     * 服务费
     */
    SERVICE_CHARGE("SERVICE_CHARGE", "服务费", "FWF");

    @EnumValue
    private final String value;

    private final String name;

    private final String billCodeEnum;

    OrderTypeEnum(String value, String name, String billCodeEnum) {
        this.value = value;
        this.name = name;
        this.billCodeEnum = billCodeEnum;
    }

}
