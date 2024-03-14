package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.model.Role;
import com.ecycle.auth.service.RoleService;
import com.ecycle.auth.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author wangweichen
* @description 针对表【ecycle_role】的数据库操作Service实现
* @createDate 2024-01-29 08:59:13
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




