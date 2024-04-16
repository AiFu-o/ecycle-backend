package com.ecycle.auth.web.info;

import lombok.Data;

/**
 * @author wangweichen
 * @Date 2024/4/15
 * @Description 服务商申请单审批请求体
 */
@Data
public class ProviderApplyApprovalRequest {
    private String approvalMessage;
    private Boolean approve = false;
}
