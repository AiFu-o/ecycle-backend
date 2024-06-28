package com.ecycle.commodity.web.info;

import com.ecycle.commodity.constant.OrderStatus;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.model.Order;
import com.ecycle.commodity.model.UserAddress;
import com.ecycle.common.context.PageQueryRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/5/15
 * @Description TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderInfo extends Order {
    private Commodity commodity;
    private UserAddress address;
}
