package com.ecycle.commodity.mapper;

import com.ecycle.commodity.model.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_user_address(用户地址)】的数据库操作Mapper
* @createDate 2024-04-23 14:04:13
* @Entity com.ecycle.commodity.model.UserAddress
*/
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    /**
     * 取消用户其他的默认地址
     * @param userId 用户 id
     */
    void cancelDefaultAddress(@Param("userId") UUID userId);
}




