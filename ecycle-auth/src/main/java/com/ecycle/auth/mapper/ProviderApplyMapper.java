package com.ecycle.auth.mapper;

import com.ecycle.auth.model.ProviderApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_provider_apply(回收商申请单)】的数据库操作Mapper
* @createDate 2024-04-15 14:57:15
* @Entity com.ecycle.auth.model.ProviderApply
*/
public interface ProviderApplyMapper extends BaseMapper<ProviderApply> {

    /**
     * 查找我的申请单
     * @param userId 用户 id
     * @return 申请单
     */
    ProviderApply loadMine(@Param("userId") UUID userId);
}




