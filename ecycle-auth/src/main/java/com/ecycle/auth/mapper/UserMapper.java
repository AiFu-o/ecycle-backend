package com.ecycle.auth.mapper;

import com.ecycle.auth.config.CustomUserDetails;
import com.ecycle.auth.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
* @author wangweichen
* @description 针对表【ecycle_user】的数据库操作Mapper
* @createDate 2024-01-24 14:21:05
* @Entity com.ecycle.auth.model.User
*/
public interface UserMapper extends BaseMapper<User> {
    CustomUserDetails findByUsername(@Param("username") String username);
}




