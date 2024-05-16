package com.ecycle.commodity.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author wangweichen
 * 商品状态
 */
@Getter
public enum CommodityStatus {

    /**
     * 出售中
     */
    SELLING("SELLING", "出售中"),
    /**
     * 已售出
     */
    SOLD("SOLD", "已售出"),
    /**
     * 已下架
     */
    OFF_SHELF("OFF_SHELF", "已下架");

    @EnumValue
    private final String value;

    private final String name;

    CommodityStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
