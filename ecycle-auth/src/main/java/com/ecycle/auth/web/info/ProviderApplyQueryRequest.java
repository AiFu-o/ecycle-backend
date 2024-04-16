package com.ecycle.auth.web.info;

import com.ecycle.common.context.PageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangweichen
 * @Date 2024/4/12
 * @Description 服务商申请单列表查询传参
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProviderApplyQueryRequest extends PageQueryRequest {
    private String name;
}
