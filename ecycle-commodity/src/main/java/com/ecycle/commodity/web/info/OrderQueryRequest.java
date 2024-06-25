package com.ecycle.commodity.web.info;

import com.ecycle.commodity.constant.OrderStatus;
import com.ecycle.common.context.PageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangweichen
 * @Date 2024/5/15
 * @Description TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderQueryRequest extends PageQueryRequest {
    private OrderStatus status;
}
