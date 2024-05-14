package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.exception.UserException;
import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.User;
import com.ecycle.auth.model.UserRole;
import com.ecycle.auth.service.RoleService;
import com.ecycle.auth.service.UserRoleService;
import com.ecycle.auth.service.UserService;
import com.ecycle.auth.mapper.UserMapper;
import com.ecycle.auth.web.info.UserInfoResponse;
import com.ecycle.common.context.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(Role role, User user) {
        if (roleService.hasRole(role.getCode())) {
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

    @Override
    public User findByOpenId(String openId) {
        return baseMapper.findByOpenId(openId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createWxFirstLoginUser(String openId) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setNickName("普通用户");
        user.setUsername(openId);
        // 随便生成个密码就不让他用密码登录
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setOpenId(openId);
        save(user);
        addRoleByCode("normalUser", user);
        return user;
    }

    @Override
    public UserInfo buildUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        List<? extends GrantedAuthority> authorities = new ArrayList<>();
        List<String> roleCodes = new ArrayList<>();
        List<Role> roles = roleService.findRolesByUserId(user.getId());
        if (null != roles) {
            for (Role role : roles) {
                roleCodes.add(role.getName());
            }
        }
        userInfo.setUsername(user.getUsername());
        userInfo.setRoles(roleCodes);
        userInfo.setAuthorities(authorities);
        userInfo.setPassword(user.getPassword());
        if (StringUtils.isNotEmpty(user.getOpenId())) {
            userInfo.setOpenId(user.getOpenId());
        }
        if (StringUtils.isNotEmpty(user.getTelephone())) {
            userInfo.setTelephone(user.getTelephone());
        }
        return userInfo;
    }

    @Override
    public UserInfoResponse findInfoById(UUID id) {
        return userMapper.findInfoById(id);
    }

}




