package com.ecycle.commodity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecycle.commodity.model.CommodityFavorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecycle.commodity.model.CommodityViewRecord;
import com.ecycle.commodity.web.info.FavoriteRecordQueryResponse;
import com.ecycle.commodity.web.info.ViewRecordQueryResponse;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_commodity_favorite(商品收藏)】的数据库操作Mapper
* @createDate 2024-05-13 17:33:14
* @Entity com.ecycle.commodity.model.CommodityFavorite
*/
public interface CommodityFavoriteMapper extends BaseMapper<CommodityFavorite> {

    /**
     * 查询当前登录用户是否收藏该商品
     * @param userId 当前用户 id
     * @param commodityId 商品 id
     * @return 收藏
     */
    CommodityFavorite findByUserIdAndCommodityId(UUID userId, UUID commodityId);

    /**
     * 查询当前用户收藏列表
     * @param query 查询参数
     * @param userId 当前用户 id
     * @return 收藏列表
     */
    IPage<FavoriteRecordQueryResponse> queryMineAll(IPage<FavoriteRecordQueryResponse> query, UUID userId);

    /**
     * 查询当前用户收藏数量
     * @param userId userId 当前用户 id
     * @return 收藏数量
     */
    Integer queryCount(UUID userId);
}




