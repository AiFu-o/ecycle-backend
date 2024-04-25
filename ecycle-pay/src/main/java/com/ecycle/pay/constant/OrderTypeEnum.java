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
    SERVICE_CHARGE("SERVICE_CHARGE", "服务费");

    @EnumValue
    private final String value;

    private final String name;

    OrderTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
