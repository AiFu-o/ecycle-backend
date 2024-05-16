package com.ecycle.message.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author wangweichen
 */

@Getter
public enum NotificationType {

    /**
     * 商品被拍下
     */
    BOUGHT("BOUGHT","商品被拍下"),
    /**
     * 报价提醒
     */
    QUOTE("QUOTE", "报价提醒");

    @EnumValue
    private final String value;

    private final String name;

    NotificationType(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
