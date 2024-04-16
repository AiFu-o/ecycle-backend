package com.ecycle.auth.service;

import com.ecycle.auth.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_role】的数据库操作Service
 * @createDate 2024-04-01 10:15:14
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据编号获取角色
     *
     * @param code 编号
     * @return 角色
     */
    Role findByCode(String code);

    /**
     * 根据角色编号判断用户是否有此角色
     *
     * @param code 角色编号
     * @return 是否有此角色
     */
    Boolean hasRoleByCode(String code);

    /**
     * 根据角色id判断用户是否有此角色
     *
     * @param id 角色 id
     * @return 是否有此角色
     */
    Boolean hasRoleById(UUID id);

}
