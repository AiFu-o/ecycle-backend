package com.ecycle.commodity.service;

import com.ecycle.commodity.model.BiddingRecord;
import com.ecycle.commodity.model.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.commodity.web.info.OrderQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_order(订单)】的数据库操作Service
* @createDate 2024-05-15 16:59:49
*/
public interface OrderService extends IService<Order> {

    /**
     * 根据出价记录生成订单
     * @param biddingRecord 出价记录
     */
    void generateOrder(BiddingRecord biddingRecord);

    /**
     * 生成订单服务费预付单
     * @param orderId 订单 id
     * @return 预付单参数
     */
    RestResponse<String> payServiceCharge(UUID orderId);

    /**
     * 服务费支付成功回调
     * @param orderId 订单 id
     */
    void serviceChargeSuccess(UUID orderId);

    /**
     * 分页查询我的订单
     * @param orderQueryRequest 请求参数
     * @return 我的订单列表
     */
    PageQueryResponse queryMineAll(OrderQueryRequest orderQueryRequest);
}
