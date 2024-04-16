package com.ecycle.auth.constant;

/**
 * 回收商申请单状态
 *
 * @author wangweichen
 */

public enum ProviderApplyStatus {

    /**
     * 待审批
     */
    PENDING,
    /**
     * 审批通过
     */
    APPROVE,
    /**
     * 已驳回
     */
    REJECT,
    /**
     * 已废弃
     */
    DISCARD;

}
