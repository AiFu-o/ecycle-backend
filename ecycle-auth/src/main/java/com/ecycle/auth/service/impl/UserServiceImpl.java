package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.exception.UserException;
import com.ecycle.auth.mapper.RoleMapper;
import com.ecycle.auth.mapper.UserRoleMapper;
import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.User;
import com.ecycle.auth.model.UserRole;
import com.ecycle.auth.service.RoleService;
import com.ecycle.auth.service.UserRoleService;
import com.ecycle.auth.service.UserService;
import com.ecycle.auth.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_user】的数据库操作Service实现
 * @createDate 2024-01-24 14:21:05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(Role role, User user) {
        if(roleService.hasRoleById(role.getId())){
            throw new UserException("用户已有该角色不能重复创建");
        }
        Assert.notNull(role, "role is not null");
        Assert.notNull(user, "user is not null");
        UserRole userRole = new UserRole();
        userRole.setId(UUID.randomUUID());
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleService.save(userRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoleByCode(String roleCode, User user) {
        Role role = roleService.findByCode(roleCode);
        addRole(role, user);
    }
}




