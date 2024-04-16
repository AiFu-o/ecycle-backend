package com.ecycle.auth.mapper;

import com.ecycle.auth.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author wangweichen
* @description 针对表【ecycle_role】的数据库操作Mapper
* @createDate 2024-04-01 10:15:14
* @Entity com.ecycle.auth.model.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据编号获取角色
     * @param code 编号
     * @return 角色
     */
    @Select("select * from ecycle_role where code = #{code}")
    Role findByCode(@Param("code") String code);

}




