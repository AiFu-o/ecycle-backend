package com.ecycle.commodity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecycle.commodity.model.BiddingRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecycle.commodity.web.info.BiddingRecordQueryRequest;
import com.ecycle.commodity.web.info.OrderQueryResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

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
     *
     * @param commodityId 商品 id
     * @return 最高出价
     */
    BigDecimal getHighestAmount(@Param("commodityId") UUID commodityId);

    /**
     * 查询我的出价记录
     *
     * @param query  分页器
     * @param param  查询参数
     * @param userId 用户 id
     * @return 出价记录列表
     */
    IPage<OrderQueryResponse> queryMineAll(IPage<OrderQueryResponse> query, @Param("param") BiddingRecordQueryRequest param, @Param("userId") UUID userId);

    /**
     * 查询用户在某个商品的出价
     *
     * @param userId      用户 id
     * @param commodityId 商品 id
     * @return 出价记录
     */
    BiddingRecord loadUserBiddingByCommodityId(@Param("userId") UUID userId, @Param("commodityId") UUID commodityId);
}




