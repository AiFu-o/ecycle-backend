package com.ecycle.commodity.service;

import com.ecycle.commodity.model.BiddingOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.commodity.web.info.CreateOrderRequest;

import java.math.BigDecimal;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_order(订单)】的数据库操作Service
* @createDate 2024-04-19 09:05:55
*/
public interface BiddingOrderService extends IService<BiddingOrder> {

    /**
     * 创建出价订单
     * @param request 出价参数
     * @return 订单 id
     */
    UUID createOrder(CreateOrderRequest request);

    /**
     * 修改出价
     * @param orderId 订单 id
     * @param commodityAmount 出价
     * @return 是否修改成功
     */
    Boolean updateCommodityAmount(UUID orderId, BigDecimal commodityAmount);
}
