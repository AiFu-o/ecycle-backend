package com.ecycle.commodity.mapper;

import com.ecycle.commodity.model.BiddingRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_order(订单)】的数据库操作Mapper
 * @createDate 2024-04-19 09:05:55
 * @Entity com.ecycle.commodity.model.Order
 */
public interface BiddingRecordMapper extends BaseMapper<BiddingRecord> {

    /**
     * 获取商品其他竞价记录
     *
     * @param commodityId 商品 id
     * @param orderId     订单 id
     * @return 其他竞价记录
     */
    List<BiddingRecord> getOtherBiddingByCommodityId(@Param("commodityId") UUID commodityId,
                                                     @Param("orderId") UUID orderId);

    /**
     * 获取商品最高出价
     * @param commodityId 商品 id
     * @return 最高出价
     */
    BigDecimal getHighestAmount(@Param("commodityId") UUID commodityId);
}




