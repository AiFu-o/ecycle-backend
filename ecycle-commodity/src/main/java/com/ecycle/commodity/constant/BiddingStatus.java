package com.ecycle.commodity.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author wangweichen
 * @Date 2024/4/18
 * @Description 出价状态
 */
@Getter
public enum BiddingStatus {

    /**
     * 竞价中
     */
    BIDDING("BIDDING","竞价中"),

    /**
     * 竞价成功
     */
    SUCCESS("SUCCESS","竞价成功"),

    /**
     * 竞价失败
     */
    FAIL("FAIL","竞价失败"),

    /**
     * 竞价关闭
     */
    CLOSED("CLOSED","竞价关闭");

    @EnumValue
    private final String value;

    private final String name;

    BiddingStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
