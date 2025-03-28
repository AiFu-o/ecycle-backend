package com.ecycle.commodity.service;

import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.web.info.CommodityInfo;
import com.ecycle.commodity.web.info.CommodityQueryRequest;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.common.context.PageQueryResponse;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_commodity(商品)】的数据库操作Service
* @createDate 2024-03-14 09:16:06
*/
public interface CommodityService extends IService<Commodity> {

    /**
     * 商品分页查询
     * @param body 查询参数
     * @return 分页返回结果
     */
    PageQueryResponse pageQueryAll(CommodityQueryRequest body);

    /**
     * 商品发布
     * @param commodity 商品
     * @return 商品id
     */
    UUID publish(Commodity commodity);

    /**
     * 商品编辑
     * @param commodity 商品
     * @return 商品id
     */
    UUID edit(Commodity commodity);

    /**
     * 商品上架
     * @param id 商品id
     */
    void shelveProduct(UUID id);

    /**
     * 商品下架
     * @param id 商品id
     */
    void shelveOffProduct(UUID id);

    /**
     * 我的商品分页查询
     * @param body 查询参数
     * @return 分页返回结果
     */
    PageQueryResponse pageQueryMineAll(CommodityQueryRequest body);

    /**
     * 获取商品详情 增加浏览量（慎用）
     * @param id 商品 id
     * @return 商品信息
     */
    CommodityInfo loadInfo(UUID id);

    /**
     * 将浏览量数据持久化到数据库（定时任务每天晚上 12点执行，也可以手动调用）
     */
    void saveCommoditiesPageViews();
}
