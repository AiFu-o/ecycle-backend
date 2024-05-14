package com.ecycle.auth.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecycle.auth.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecycle.auth.web.info.ManageUserQueryRequest;
import com.ecycle.auth.web.info.UserInfoResponse;
import com.ecycle.common.context.PageQueryResponse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.UUID;

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

    /**
     * 根据openId查找用户
     * @param openId openId
     * @return user
     */
    @Select("SELECT * FROM ecycle_user WHERE open_id = #{openId}")
    User findByOpenId(String openId);

    /**
     * 获取管理员用户数量
     * @param page 分页参数
     * @param body 请求参数
     * @return 分页查询结果
     */
    IPage<UserInfoResponse> queryManageUser(IPage<UserInfoResponse> page, @Param("params") ManageUserQueryRequest body);

    /**
     * 获取用户信息
     * @param id 用户 id
     * @return 用户信息
     */
    UserInfoResponse findInfoById(UUID id);
}




