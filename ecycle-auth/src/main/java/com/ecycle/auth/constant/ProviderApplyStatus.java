package com.ecycle.auth.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 回收商申请单状态
 *
 * @author wangweichen
 */
@Getter
public enum ProviderApplyStatus {

    /**
     * 待审批
     */
    PENDING("PENDING", "待审批"),
    /**
     * 审批通过
     */
    APPROVE("APPROVE", "审批通过"),
    /**
     * 已驳回
     */
    REJECT("REJECT", "已驳回"),
    /**
     * 已废弃
     */
    DISCARD("DISCARD", "已废弃");

    @EnumValue
    private final String value;

    private final String name;

    ProviderApplyStatus(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
