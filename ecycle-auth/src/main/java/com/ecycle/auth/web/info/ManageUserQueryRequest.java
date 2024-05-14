package com.ecycle.auth.web.info;

import com.ecycle.common.context.PageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangweichen
 * @Date 2024/5/8
 * @Description 管理员列表查询参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ManageUserQueryRequest extends PageQueryRequest {
    private String input;
}
