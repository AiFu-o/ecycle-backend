package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.model.UserRole;
import com.ecycle.auth.mapper.UserRoleMapper;
import com.ecycle.auth.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
* @author wangweichen
* @description 针对表【ecycle_user_role】的数据库操作Service实现
* @createDate 2024-04-01 10:15:14
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {

}




