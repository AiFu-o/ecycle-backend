package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.mapper.CommodityMapper;
import com.ecycle.commodity.web.info.CommodityQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.utils.MybatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author wangweichen
 * @description 针对表【ecycle_commodity(商品)】的数据库操作Service实现
 * @createDate 2024-03-14 09:16:06
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity>
        implements CommodityService {

    @Override
    public PageQueryResponse pageQueryAll(CommodityQueryRequest body) {
        QueryChainWrapper<Commodity> queryChainWrapper = super.query();
        if(StringUtils.isNotEmpty(body.getName())){
            queryChainWrapper.like("name", "%" + body.getName() + "%");
        }
        queryChainWrapper.orderByAsc("create_time");

        MybatisUtils<Commodity> mybatisUtils = new MybatisUtils<>();
        return mybatisUtils.pageQuery(queryChainWrapper, body);
    }
}




