package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.model.Commodity;
import com.ecycle.commodity.service.CommodityService;
import com.ecycle.commodity.mapper.CommodityMapper;
import org.springframework.stereotype.Service;

/**
* @author wangweichen
* @description 针对表【ecycle_commodity(商品)】的数据库操作Service实现
* @createDate 2024-03-14 09:16:06
*/
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity>
    implements CommodityService{

}




