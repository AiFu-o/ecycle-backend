package com.ecycle.commodity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecycle.commodity.model.CommodityViewRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecycle.commodity.web.info.ViewRecordQueryResponse;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_commodity_view_record(商品浏览记录)】的数据库操作Mapper
 * @createDate 2024-05-13 17:33:14
 * @Entity com.ecycle.commodity.model.CommodityViewRecord
 */
public interface CommodityViewRecordMapper extends BaseMapper<CommodityViewRecord> {

    /**
     * 查询用户的商品浏览记录
     *
     * @param userId      用户 id
     * @param commodityId 商品 id
     * @return 浏览记录
     */
    CommodityViewRecord findByUserIdAndCommodityId(@Param("userId") UUID userId,
                                                   @Param("commodityId") UUID commodityId);

    /**
     * 查询当前用户的商品浏览记录（最近 30 天）
     *
     * @param query  查询对象
     * @param userId 当前用户 id
     * @return 浏览记录
     */
    IPage<ViewRecordQueryResponse> queryMineAll(IPage<ViewRecordQueryResponse> query,
                                                @Param("userId") UUID userId);

    /**
     * 查询当前用户商品浏览记录总数
     *
     * @param userId 当前用户 id
     * @return 浏览记录数量
     */
    Integer queryCount(@Param("userId") UUID userId);
}




