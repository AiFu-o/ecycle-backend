package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.RoleAuthority;
import com.ecycle.auth.service.RoleAuthorityService;
import com.ecycle.auth.mapper.RoleAuthorityMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_role_authority】的数据库操作Service实现
* @createDate 2024-04-01 10:15:14
*/
@Service
public class RoleAuthorityServiceImpl extends ServiceImpl<RoleAuthorityMapper, RoleAuthority>
    implements RoleAuthorityService{
}




