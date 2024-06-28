package com.ecycle.commodity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecycle.commodity.model.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecycle.commodity.web.info.OrderInfo;
import com.ecycle.commodity.web.info.OrderQueryRequest;
import com.ecycle.commodity.web.info.OrderQueryResponse;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_order(订单)】的数据库操作Mapper
 * @createDate 2024-05-15 16:59:49
 * @Entity com.ecycle.commodity.model.Order
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据卖家查询订单
     *
     * @param query    分页器
     * @param param    查询参数
     * @param sellerId 卖家 id
     * @return 订单列表
     */
    IPage<OrderQueryResponse> queryBySeller(IPage<OrderQueryResponse> query,
                                            @Param("param") OrderQueryRequest param, @Param("sellerId") UUID sellerId);

    /**
     * 根据买家查询订单
     *
     * @param query    分页器
     * @param param    查询参数
     * @param buyerId 买家 id
     * @return 订单列表
     */
    IPage<OrderQueryResponse> queryByBuyer(IPage<OrderQueryResponse> query,
                                            @Param("param") OrderQueryRequest param, @Param("buyerId") UUID buyerId);
}




