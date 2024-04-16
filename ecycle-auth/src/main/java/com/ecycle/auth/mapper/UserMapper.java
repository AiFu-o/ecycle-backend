package com.ecycle.auth.mapper;

import com.ecycle.auth.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
* @author wangweichen
* @description 针对表【ecycle_user】的数据库操作Mapper
* @createDate 2024-01-24 14:21:05
* @Entity com.ecycle.auth.model.User
*/
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return user
     */
    @Select("SELECT * FROM ecycle_user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);
}




