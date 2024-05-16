package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.mapper.CommodityFavoriteMapper;
import com.ecycle.commodity.model.CommodityFavorite;
import com.ecycle.commodity.service.CommodityFavoriteService;
import com.ecycle.commodity.web.info.FavoriteRecordQueryResponse;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_commodity_favorite(商品收藏)】的数据库操作Service实现
* @createDate 2024-05-13 17:33:14
*/
@Service
public class CommodityFavoriteServiceImpl extends ServiceImpl<CommodityFavoriteMapper, CommodityFavorite>
    implements CommodityFavoriteService{


    @Override
    public void favorite(UUID commodityId) {
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        CommodityFavorite favorite = baseMapper.findByUserIdAndCommodityId(userId, commodityId);
        if(null != favorite) {
           return;
        }
        favorite = new CommodityFavorite();
        favorite.setId(UUID.randomUUID());
        favorite.setCommodityId(commodityId);
        favorite.setFavoriteTime(new Date());
        favorite.setUserId(userId);
        saveOrUpdate(favorite);
    }

    @Override
    public void cancel(UUID commodityId) {
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        CommodityFavorite favorite = baseMapper.findByUserIdAndCommodityId(userId, commodityId);
        if(null == favorite) {
            return;
        }
        removeById(favorite);
    }

    @Override
    public PageQueryResponse queryAll(PageQueryRequest body){
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        PageQueryResponse result = new PageQueryResponse();
        IPage<FavoriteRecordQueryResponse> query = new Page<>(body.getPageIndex(), body.getPageSize());
        query = baseMapper.queryMineAll(query, userId);
        result.setTotal(query.getTotal());
        result.setDataList(query.getRecords());
        return result;
    }

    @Override
    public Integer queryCount() {
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        return baseMapper.queryCount(userId);
    }

}




