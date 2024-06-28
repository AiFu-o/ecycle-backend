package com.ecycle.commodity.service;

import com.ecycle.commodity.model.CommodityFavorite;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_commodity_favorite(商品收藏)】的数据库操作Service
* @createDate 2024-05-13 17:33:14
*/
public interface CommodityFavoriteService extends IService<CommodityFavorite> {

    /**
     * 收藏商品
     * @param commodityId 商品 id
     * @return 收藏记录 id
     */
    UUID favorite(UUID commodityId);

    /**
     * 取消收藏商品
     * @param commodityId 商品 id
     */
    void cancel(UUID commodityId);

    /**
     * 查询我的全部收藏
     * @param body 查询条件
     * @return 收藏列表
     */
    PageQueryResponse queryAll(PageQueryRequest body);

    /**
     * 查询我的收藏数量
     * @return 收藏数量
     */
    Integer queryCount();
}
