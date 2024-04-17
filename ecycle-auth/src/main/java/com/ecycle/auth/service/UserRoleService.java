package com.ecycle.auth.service;

import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_user_role】的数据库操作Service
* @createDate 2024-04-01 10:15:14
*/
public interface UserRoleService extends IService<UserRole> {

    /**
     * 获取用户的所有角色
     *
     * @param userId 用户 id
     * @return 角色
     */
    List<Role> findRolesByUserId(UUID userId);
}
