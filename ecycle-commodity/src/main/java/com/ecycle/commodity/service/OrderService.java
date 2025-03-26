package com.ecycle.commodity.service;

import com.ecycle.commodity.model.BiddingRecord;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.web.info.OrderInfo;
import com.ecycle.commodity.web.info.OrderQueryRequest;
import com.ecycle.commodity.model.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.context.RestResponse;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_order(订单)】的数据库操作Service
* @createDate 2024-05-15 16:59:49
*/
public interface OrderService extends IService<Order> {

    /**
     * 根据出价记录生成订单
     *
     * @param biddingRecord 出价记录
     * @param commodity 商品
     */
    void generateOrder(BiddingRecord biddingRecord, Commodity commodity);

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
     * 根据卖家获取订单
     * @param orderQueryRequest 请求参数
     * @return 我的订单列表
     */
    PageQueryResponse queryBySeller(OrderQueryRequest orderQueryRequest);

    /**
     * 根据回收商获取订单
     * @param orderQueryRequest 请求参数
     * @return 我的订单列表
     */
    PageQueryResponse queryMineAll(OrderQueryRequest orderQueryRequest);

    /**
     * 查询订单详情
     * @param id 订单id
     * @return 订单详情
     */
    OrderInfo loadInfo(UUID id);

    /**
     * 完成订单
     * @param orderId 订单 id
     */
    void finish(UUID orderId);

    /**
     * 回收商到达指定位置
     * @param orderId 订单 id
     */
    void arrived(UUID orderId);
}
