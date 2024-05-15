package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.model.CommodityViewRecord;
import com.ecycle.commodity.service.CommodityViewRecordService;
import com.ecycle.commodity.mapper.CommodityViewRecordMapper;
import com.ecycle.commodity.web.info.ViewRecordQueryResponse;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wangweichen
 * @description 针对表【ecycle_commodity_view_record(商品浏览记录)】的数据库操作Service实现
 * @createDate 2024-05-13 17:33:14
 */
@Service
public class CommodityViewRecordServiceImpl extends ServiceImpl<CommodityViewRecordMapper, CommodityViewRecord>
        implements CommodityViewRecordService {

    @Override
    public void addRecord(UUID commodityId) {
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        // TODO 暂时先直接存储数据库 以后高并发可能会直接放到数据仓库或异步
        CommodityViewRecord record = baseMapper.findByUserIdAndCommodityId(userId, commodityId);
        if(null == record) {
            record = new CommodityViewRecord();
            record.setId(UUID.randomUUID());
        }
        record.setCommodityId(commodityId);
        record.setViewTime(new Date());
        record.setUserId(userId);
        saveOrUpdate(record);
    }

    @Override
    public PageQueryResponse queryAll(PageQueryRequest body){
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        PageQueryResponse result = new PageQueryResponse();
        IPage<ViewRecordQueryResponse> query = new Page<>(body.getPageIndex(), body.getPageSize());
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




