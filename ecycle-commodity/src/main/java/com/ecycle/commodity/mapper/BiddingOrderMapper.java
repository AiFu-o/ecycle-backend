package com.ecycle.commodity.mapper;

import com.ecycle.commodity.model.BiddingOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_order(订单)】的数据库操作Mapper
 * @createDate 2024-04-19 09:05:55
 * @Entity com.ecycle.commodity.model.Order
 */
public interface BiddingOrderMapper extends BaseMapper<BiddingOrder> {

    /**
     * 获取商品其他竞价订单
     *
     * @param commodityId 商品 id
     * @param orderId     订单 id
     * @return 其他竞价订单
     */
    List<BiddingOrder> getOtherBiddingByCommodityId(@Param("commodityId") UUID commodityId,
                                                    @Param("orderId") UUID orderId);
}




