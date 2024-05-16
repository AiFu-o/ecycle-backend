package com.ecycle.commodity.service;

import com.ecycle.commodity.model.CommodityCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_commodity_category(商品分类)】的数据库操作Service
* @createDate 2024-03-14 09:16:06
*/
public interface CommodityCategoryService extends IService<CommodityCategory> {

    /**
     * 商品分类分页查询
     * @param body 查询参数
     * @return 商品分类列表
     */
    PageQueryResponse queryAll(PageQueryRequest body);

    /**
     * 创建商品分类
     * @param category 分类数据
     * @return 商品分类 id
     */
    UUID doSave(CommodityCategory category);

    /**
     * 编辑商品分类
     * @param category 分类数据
     * @return 分类 id
     */
    UUID edit(CommodityCategory category);
}
