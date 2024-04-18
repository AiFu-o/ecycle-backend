package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.constant.CommodityStatus;
import com.ecycle.commodity.exception.CommodityException;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.mapper.CommodityMapper;
import com.ecycle.commodity.service.feign.AuthFeignService;
import com.ecycle.commodity.web.info.CommodityQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.utils.JwtTokenUtils;
import com.ecycle.common.utils.MybatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_commodity(商品)】的数据库操作Service实现
 * @createDate 2024-03-14 09:16:06
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity>
        implements CommodityService {

    @Resource
    private AuthFeignService authFeignService;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID publish(Commodity commodity) {
        hasPublishAuth();
        UUID id = UUID.randomUUID();
        if(null != commodity.getId()){
            id = commodity.getId();
        }
        commodity.setId(id);
        commodity.setStatus(CommodityStatus.SELLING);
        UUID userId = JwtTokenUtils.getCurrentUserId();
        if(null == userId){
            throw new CommodityException("用户未登录");
        }
        commodity.setCreatorId(userId);
        save(commodity);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID edit(Commodity commodity) {
        hasPublishAuth();
        Commodity historyEntity = load(commodity.getId());
        if(null == commodity.getStatus() || commodity.getStatus() == CommodityStatus.SOLD){
            throw new CommodityException("商品状态异常");
        }
        historyEntity.setDesc(commodity.getDesc());
        historyEntity.setName(commodity.getName());
        historyEntity.setCategoryId(commodity.getCategoryId());
        updateById(historyEntity);
        return historyEntity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shelveProduct(UUID id) {
        Commodity commodity = load(id);
        if(null == commodity.getStatus() || commodity.getStatus() == CommodityStatus.SOLD){
            throw new CommodityException("商品状态异常");
        }
        commodity.setStatus(CommodityStatus.SELLING);
        save(commodity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shelveOffProduct(UUID id) {
        Commodity commodity = load(id);
        if(null == commodity.getStatus() || commodity.getStatus() == CommodityStatus.SOLD){
            throw new CommodityException("商品状态异常");
        }
        commodity.setStatus(CommodityStatus.OFF_SHELF);
        save(commodity);
    }

    @Override
    public PageQueryResponse pageQueryMineAll(CommodityQueryRequest body) {
        QueryChainWrapper<Commodity> queryChainWrapper = super.query();
        if(StringUtils.isNotEmpty(body.getName())){
            queryChainWrapper.like("name", "%" + body.getName() + "%");
        }
        UUID userId = JwtTokenUtils.getCurrentUserId();
        if(null == userId){
            throw new CommodityException("用户未登录");
        }
        queryChainWrapper.eq("creator_id", userId);
        queryChainWrapper.orderByAsc("create_time");

        MybatisUtils<Commodity> mybatisUtils = new MybatisUtils<>();
        return mybatisUtils.pageQuery(queryChainWrapper, body);
    }

    private void hasPublishAuth(){
        // TODO 先暂时用角色 以后改成权限项控制接口
        Boolean hasRole = authFeignService.hasRole("normalUser");
        if(!hasRole){
            throw new CommodityException("暂无权限");
        }
    }

    private Commodity load(UUID id){
        Commodity commodity = getById(id);
        if(null == commodity){
            throw new CommodityException("找不到商品");
        }
        return commodity;
    }

}




