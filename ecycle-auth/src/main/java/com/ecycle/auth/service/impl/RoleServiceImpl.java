package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.model.Role;
import com.ecycle.auth.service.RoleService;
import com.ecycle.auth.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_role】的数据库操作Service实现
* @createDate 2024-04-01 10:15:14
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Override
    public Role findByCode(String code) {
        return super.baseMapper.findByCode(code);
    }

    @Override
    public Boolean hasRoleByCode(String code) {
        // TODO 缺角色判断的处理
        return false;
    }

    @Override
    public Boolean hasRoleById(UUID id) {
        // TODO 缺角色判断的处理
        return false;
    }
}




