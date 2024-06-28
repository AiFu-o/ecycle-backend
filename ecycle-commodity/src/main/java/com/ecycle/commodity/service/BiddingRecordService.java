package com.ecycle.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.commodity.model.BiddingRecord;
import com.ecycle.commodity.web.info.BiddingRecordQueryRequest;
import com.ecycle.commodity.web.info.CreateBiddingRequest;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;

import java.math.BigDecimal;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_order(订单)】的数据库操作Service
* @createDate 2024-04-19 09:05:55
*/
public interface BiddingRecordService extends IService<BiddingRecord> {

    /**
     * 创建出价
     * @param request 出价参数
     * @return 订单 id
     */
    UUID create(CreateBiddingRequest request);

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
     * 分页查询我的出价
     * @param biddingRecordQueryRequest 查询参数
     * @return 我的出价列表
     */
    PageQueryResponse queryMineAll(BiddingRecordQueryRequest biddingRecordQueryRequest);

    /**
     * 根据商品获取出价列表
     * @param param 分页参数
     * @param commodityId 商品 id
     * @return 出价列表
     */
    PageQueryResponse queryAllByCommodityId(PageQueryRequest param, UUID commodityId);
}
