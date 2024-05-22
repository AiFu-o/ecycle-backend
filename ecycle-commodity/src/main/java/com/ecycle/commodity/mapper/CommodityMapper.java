package com.ecycle.commodity.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecycle.commodity.model.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecycle.commodity.web.info.CommodityQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_commodity(商品)】的数据库操作Mapper
* @createDate 2024-03-14 09:16:06
* @Entity com.ecycle.commodity.model.Commodity
*/
public interface CommodityMapper extends BaseMapper<Commodity> {

    /**
     * 将浏览量持久化到数据库
     * @param id 商品 id
     * @param pageViews 浏览量
     */
    void saveCommoditiesPageViews(@Param("id") UUID id,
                                  @Param("pageViews") Integer pageViews);

    /**
     * 分页查询商品列表
     * @param query 分页参数
     * @param params 查询参数
     * @return 商品列表
     */
    IPage<Commodity> pageQueryAll(IPage<Commodity> query, @Param("params") CommodityQueryRequest params);
}




