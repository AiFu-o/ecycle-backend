package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.exception.CommodityCategoryException;
import com.ecycle.commodity.model.CommodityCategory;
import com.ecycle.commodity.service.CommodityCategoryService;
import com.ecycle.commodity.mapper.CommodityCategoryMapper;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.utils.MybatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_commodity_category(商品分类)】的数据库操作Service实现
 * @createDate 2024-03-14 09:16:06
 */
@Service
public class CommodityCategoryServiceImpl extends ServiceImpl<CommodityCategoryMapper, CommodityCategory>
        implements CommodityCategoryService {

    @Override
    public PageQueryResponse queryAll(PageQueryRequest body) {
        QueryChainWrapper<CommodityCategory> queryChainWrapper = super.query();

        queryChainWrapper.orderByAsc("create_time");

        MybatisUtils<CommodityCategory> mybatisUtils = new MybatisUtils<>();

        return mybatisUtils.pageQuery(queryChainWrapper, body);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID doSave(CommodityCategory category) {
        validateData(category);
        UUID id = category.getId();
        if (null == id) {
            id = UUID.randomUUID();
        }
        category.setId(id);
        save(category);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID edit(CommodityCategory category) {
        validateData(category);
        CommodityCategory historyEntity = getById(category.getId());
        if (null == historyEntity) {
            throw new CommodityCategoryException("找不到分类");
        }
        historyEntity.setName(category.getName());
        historyEntity.setTitle(category.getTitle());
        historyEntity.setIconFileId(category.getIconFileId());
        historyEntity.setServiceChargeSetting(category.getServiceChargeSetting());
        historyEntity.setServiceChargeType(category.getServiceChargeType());
        updateById(historyEntity);
        return historyEntity.getId();
    }

    private void validateData(CommodityCategory category) {
        if (StringUtils.isEmpty(category.getName())) {
            throw new CommodityCategoryException("分类名称不能为空");
        }
        if (StringUtils.isEmpty(category.getTitle())) {
            throw new CommodityCategoryException("分类标题不能为空");
        }
        if (null == category.getIconFileId()) {
            throw new CommodityCategoryException("请设置分类图标");
        }
        if (null == category.getServiceChargeSetting()) {
            throw new CommodityCategoryException("服务费不能为空");
        }
        if (null == category.getServiceChargeType()) {
            throw new CommodityCategoryException("服务费类型不能为空");
        }
    }
}




