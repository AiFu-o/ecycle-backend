package com.ecycle.auth.mapper;

import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.RoleAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_role_authority】的数据库操作Mapper
* @createDate 2024-04-01 10:15:14
* @Entity com.ecycle.auth.model.RoleAuthority
*/
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthority> {

}




