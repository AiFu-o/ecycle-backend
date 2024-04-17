package com.ecycle.auth.mapper;

import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_user_role】的数据库操作Mapper
* @createDate 2024-04-01 10:15:14
* @Entity com.ecycle.auth.model.UserRole
*/
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 获取用户的所有角色
     *
     * @param userId 用户 id
     * @return 角色
     */
    List<Role> findRolesByUserId(@Param("userId") UUID userId);

}




