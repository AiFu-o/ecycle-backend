package com.ecycle.auth.service;

import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ecycle.auth.model.UserRole;
import com.ecycle.common.context.UserInfo;

/**
* @author wangweichen
* @description 针对表【ecycle_user】的数据库操作Service
* @createDate 2024-01-24 14:21:05
*/
public interface UserService extends IService<User> {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return user
     */
    User findByUsername(String username);

    /**
     * 给用户添加角色
     *
     * @param role 角色
     * @param user 用户
     */
    void addRole(Role role, User user);

    /**
     * 根据角色编号给用户添加角色
     *
     * @param roleCode 角色编号
     * @param user 用户
     */
    void addRoleByCode(String roleCode, User user);

    /**
     * 根据openId查找用户
     *
     * @param openId openId
     * @return user
     */
    User findByOpenId(String openId);

    /**
     * 微信小程序第一次登录自动创建用户
     *
     * @param openId openId
     * @param phoneNumber 手机号
     * @return user
     */
    User createWxFirstLoginUser(String phoneNumber, String openId);

    /**
     * 生成 userInfo
     *
     * @param user 用户
     * @return userInfo
     */
    UserInfo buildUserInfo(User user);
}
