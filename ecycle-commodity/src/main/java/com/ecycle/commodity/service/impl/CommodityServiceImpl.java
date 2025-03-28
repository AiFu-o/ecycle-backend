package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.constant.CommodityStatus;
import com.ecycle.commodity.exception.CommodityException;
import com.ecycle.commodity.mapper.CommodityMapper;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.service.CommodityCategoryService;
import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.service.CommodityViewRecordService;
import com.ecycle.commodity.service.UserAddressService;
import com.ecycle.commodity.web.info.CommodityInfo;
import com.ecycle.commodity.web.info.CommodityQueryRequest;
import com.ecycle.common.context.PageQueryResponse;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import com.ecycle.common.utils.MybatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
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
    private UserAddressService userAddressService;

    @Resource
    private CommodityViewRecordService viewRecordService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageQueryResponse pageQueryAll(CommodityQueryRequest body) {
        PageQueryResponse result = new PageQueryResponse();
        IPage<Commodity> query = new Page<>(body.getPageIndex(), body.getPageSize());
        query = baseMapper.pageQueryAll(query, body);
        result.setTotal(query.getTotal());

        List<Commodity> commodities = query.getRecords();

        // 因为浏览量是可能存在 redis 里的所以从 redis 取一下
        putQueryPageViews(commodities);
        result.setDataList(commodities);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID publish(Commodity commodity) {
        hasPublishAuth();
        validateData(commodity);

        UUID id = UUID.randomUUID();
        if (null != commodity.getId()) {
            id = commodity.getId();
        }
        commodity.setId(id);
        commodity.setStatus(CommodityStatus.SELLING);
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        if (null == userId) {
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
        validateData(commodity);

        Commodity historyEntity = load(commodity.getId());
        if (null == commodity.getStatus() || commodity.getStatus() == CommodityStatus.SOLD) {
            throw new CommodityException("商品状态异常");
        }
        historyEntity.setInfo(commodity.getInfo());
        historyEntity.setName(commodity.getName());
        historyEntity.setCoverFileId(commodity.getCoverFileId());
        historyEntity.setCategoryId(commodity.getCategoryId());
        updateById(historyEntity);
        return historyEntity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shelveProduct(UUID id) {
        Commodity commodity = load(id);
        if (null == commodity.getStatus() || commodity.getStatus() == CommodityStatus.SOLD) {
            throw new CommodityException("商品状态异常");
        }
        commodity.setStatus(CommodityStatus.SELLING);
        save(commodity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void shelveOffProduct(UUID id) {
        Commodity commodity = load(id);
        if (null == commodity.getStatus() || commodity.getStatus() == CommodityStatus.SOLD) {
            throw new CommodityException("商品状态异常");
        }
        commodity.setStatus(CommodityStatus.OFF_SHELF);
        save(commodity);
    }

    @Override
    public PageQueryResponse pageQueryMineAll(CommodityQueryRequest body) {
        QueryChainWrapper<Commodity> queryChainWrapper = super.query();
        if (StringUtils.isNotEmpty(body.getInput())) {
            queryChainWrapper.like("name", "%" + body.getInput() + "%");
        }
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        if (null == userId) {
            throw new CommodityException("用户未登录");
        }
        queryChainWrapper.eq("creator_id", userId);

        if(null != body.getStatusList() && body.getStatusList().size() > 0){
            queryChainWrapper.in("status", body.getStatusList());
        }
        queryChainWrapper.orderByAsc("create_time");

        MybatisUtils<Commodity> mybatisUtils = new MybatisUtils<>();
        PageQueryResponse response = mybatisUtils.pageQuery(queryChainWrapper, body);

        // 因为浏览量是可能存在 redis 里的所以从 redis 取一下
        putQueryPageViews((List<Commodity>) response.getDataList());
        return response;
    }

    @Override
    public CommodityInfo loadInfo(UUID id) {
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        if (null == userId) {
            throw new CommodityException("用户未登录");
        }

        // 增加浏览量
        CommodityInfo commodity = baseMapper.loadInfo(id, userId);
        commodity.setAddress(userAddressService.getById(commodity.getAddressId()));
        Integer pageViews = addPageViews(commodity);
        commodity.setPageViews(pageViews);
        viewRecordService.addRecord(id);
        return commodity;
    }

    private final String PAGE_VIEWS_REDIS_KEY = "_PAGE_VIEWS";

    private Integer addPageViews(Commodity commodity) {
        String key = commodity.getId().toString() + PAGE_VIEWS_REDIS_KEY;
        Integer pageViews;
        Object redisPageViews = redisTemplate.opsForValue().get(key);
        if (null != redisPageViews) {
            pageViews = (Integer) redisPageViews;
        } else {
            pageViews = commodity.getPageViews();
        }
        pageViews++;
        redisTemplate.opsForValue().set(key, pageViews);
        return pageViews;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 0 * * *")
    public void saveCommoditiesPageViews() {
        Set<String> commoditiesKeys = redisTemplate.keys("*" + PAGE_VIEWS_REDIS_KEY);
        if (commoditiesKeys != null) {
            for (String commoditiesKey : commoditiesKeys) {
                UUID id = UUID.fromString(commoditiesKey.substring(0, 36));
                Integer pageViews = (Integer) redisTemplate.opsForValue().get(commoditiesKey);
                baseMapper.saveCommoditiesPageViews(id, pageViews);
            }
            redisTemplate.delete(commoditiesKeys);
        }
    }

    private void putQueryPageViews(List<Commodity> commodities) {
        for (Commodity commodity : commodities) {
            String key = commodity.getId().toString() + PAGE_VIEWS_REDIS_KEY;
            Object redisPageViews = redisTemplate.opsForValue().get(key);
            if (null != redisPageViews) {
                commodity.setPageViews((Integer) redisPageViews);
            }
        }
    }

    private void hasPublishAuth() {
        // TODO 先暂时用角色 以后改成权限项控制接口
        Boolean hasRole = true;
        // authFeignService.hasRole("normalUser");
        if (!hasRole) {
            throw new CommodityException("暂无权限");
        }
    }

    private Commodity load(UUID id) {
        Commodity commodity = getById(id);
        if (null == commodity) {
            throw new CommodityException("找不到商品");
        }
        return commodity;
    }

    private void validateData(Commodity commodity) {
        if (StringUtils.isEmpty(commodity.getName())) {
            throw new CommodityException("商品名称不能为空");
        }
        if (StringUtils.isEmpty(commodity.getInfo())) {
            throw new CommodityException("商品描述不能为空");
        }
        if (null == commodity.getCoverFileId()) {
            throw new CommodityException("请设置商品封面");
        }
        if (null == commodity.getCategoryId()) {
            throw new CommodityException("商品分类不能为空");
        }
    }

}




