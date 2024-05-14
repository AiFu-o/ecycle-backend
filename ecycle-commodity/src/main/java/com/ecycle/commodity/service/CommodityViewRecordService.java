package com.ecycle.commodity.service;

import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.model.CommodityViewRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_commodity_view_record(商品浏览记录)】的数据库操作Service
* @createDate 2024-05-13 17:33:14
*/
public interface CommodityViewRecordService extends IService<CommodityViewRecord> {

    /**
     * 增加浏览记录
     * @param commodityId 商品id
     */
    void addRecord(UUID commodityId);

    /**
     * 获取 30天内历史记录
     * @param body 查询参数
     * @return 历史记录列表
     */
    PageQueryResponse queryAll(PageQueryRequest body);

    /**
     * 获取 30天内历史记录条数
     * @return 历史记录条数
     */
    Integer queryCount();
}
