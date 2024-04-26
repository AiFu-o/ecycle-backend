package com.ecycle.commodity.service;

import com.ecycle.commodity.model.BiddingOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.commodity.web.info.CreateOrderRequest;
import com.ecycle.common.context.RestResponse;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 服务费预付单
     * @param orderId 竞价订单 id
     * @return 生成预付单需的参数
     */
    RestResponse<String> payServiceCharge(UUID orderId);

    /**
     * 卖家确认出价 出售
     * @param orderId 订单 id
     * @return 是否出售成功
     */
    Boolean sell(UUID orderId);

    /**
     * 回收商关闭出价订单
     * @param orderId 订单 id
     * @return 是否关闭成功
     */
    Boolean close(UUID orderId);

    /**
     * 服务费支付成功
     * @param orderId 订单 id
     */
    void serviceChargeSuccess(UUID orderId);
}
