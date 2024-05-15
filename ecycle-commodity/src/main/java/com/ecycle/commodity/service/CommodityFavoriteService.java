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

    void favorite(UUID commodityId);

    void cancel(UUID commodityId);

    PageQueryResponse queryAll(PageQueryRequest body);

    Integer queryCount();
}
