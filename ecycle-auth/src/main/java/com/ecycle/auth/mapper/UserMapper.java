package com.ecycle.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecycle.auth.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wangweichen
 * @Date 2024/1/24
 * @Description TODO
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
