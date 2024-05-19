package com.ecycle.commodity.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.commodity.exception.AddressException;
import com.ecycle.commodity.mapper.UserAddressMapper;
import com.ecycle.commodity.model.UserAddress;
import com.ecycle.commodity.service.UserAddressService;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_user_address(用户地址)】的数据库操作Service实现
 * @createDate 2024-04-23 14:04:13
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
        implements UserAddressService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID createAddress(UserAddress address) {
        validateData(address);
        UUID id = address.getId();
        if (null == id) {
            id = UUID.randomUUID();
        }
        address.setId(id);
        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        address.setCreatorId(userId);
        save(address);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID updateAddress(UserAddress address) {
        validateData(address);
        UserAddress history = getById(address.getId());
        if(null == history){
            throw new AddressException("找不到地址");
        }
        history.setName(address.getName());
        history.setPhone(address.getPhone());
        history.setAddress(address.getAddress());
        history.setProvince(address.getProvince());
        history.setCity(address.getCity());
        history.setDistrict(address.getDistrict());
        history.setStress(address.getStress());

        history.setProvinceCode(address.getProvinceCode());
        history.setCityCode(address.getCityCode());
        history.setDistrictCode(address.getDistrictCode());
        history.setStressCode(address.getStressCode());
        if(null != address.getDefaultAddress()){
            history.setDefaultAddress(address.getDefaultAddress());
        }
        updateById(history);
        return history.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void defaultAddress(UUID addressId) {
        UserAddress address = getById(addressId);
        if(null == address){
            throw new AddressException("找不到地址");
        }

        if(!address.getDefaultAddress()){
            UUID userId = CurrentUserInfoUtils.getCurrentUserId();
            baseMapper.cancelDefaultAddress(userId);
        }

        address.setDefaultAddress(!address.getDefaultAddress());
        updateById(address);
    }

    @Override
    public List<UserAddress> queryMineAll() {
        QueryChainWrapper<UserAddress> queryChainWrapper = super.query();

        UUID userId = CurrentUserInfoUtils.getCurrentUserId();
        queryChainWrapper.eq("creator_id", userId);
        queryChainWrapper.orderByAsc("create_time");

        return queryChainWrapper.list();
    }

    private void validateData(UserAddress address) {
        if (StringUtils.isEmpty(address.getName())) {
            throw new AddressException("姓名不能为空");
        }
        if (StringUtils.isEmpty(address.getPhone())) {
            throw new AddressException("手机号不能为空");
        }
        if (StringUtils.isEmpty(address.getAddress())) {
            throw new AddressException("详细地址不能为空");
        }
        if (StringUtils.isEmpty(address.getProvince()) ||
                StringUtils.isEmpty(address.getProvinceCode())) {
            throw new AddressException("省不能为空");
        }
        if (StringUtils.isEmpty(address.getCity()) ||
                StringUtils.isEmpty(address.getCityCode())) {
            throw new AddressException("市不能为空");
        }
        if (StringUtils.isEmpty(address.getDistrict()) ||
                StringUtils.isEmpty(address.getDistrictCode())) {
            throw new AddressException("区不能为空");
        }
        if (StringUtils.isEmpty(address.getStress()) ||
                StringUtils.isEmpty(address.getStressCode())) {
            throw new AddressException("街道不能为空");
        }
    }
}




