package com.ecycle.commodity.service;

import com.ecycle.commodity.model.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.common.context.PageQueryRequest;
import com.ecycle.common.context.PageQueryResponse;

import java.util.List;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_user_address(用户地址)】的数据库操作Service
* @createDate 2024-04-23 14:04:13
*/
public interface UserAddressService extends IService<UserAddress> {

    /**
     * 创建地址
     * @param address 地址参数
     * @return 地址id
     */
    UUID createAddress(UserAddress address);

    /**
     * 修改地址
     * @param address 地址参数
     * @return 地址id
     */
    UUID updateAddress(UserAddress address);

    /**
     * 设置或取消默认地址
     * @param addressId 地址id
     */
    void defaultAddress(UUID addressId);


    /**
     * 查询我的地址
     * @return 地址
     */
    List<UserAddress> queryMineAll();

}
