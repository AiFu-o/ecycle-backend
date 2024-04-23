package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.model.UserAddress;
import com.ecycle.commodity.service.UserAddressService;
import com.ecycle.commodity.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;

/**
* @author wangweichen
* @description 针对表【ecycle_user_address(用户地址)】的数据库操作Service实现
* @createDate 2024-04-23 14:04:13
*/
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
    implements UserAddressService{

}




