package com.ecycle.auth.service.impl;

import com.ecycle.common.context.UserInfo;
import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.RoleService;
import com.ecycle.auth.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/1/29
 * @Description TODO
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        List<? extends GrantedAuthority> authorities = new ArrayList<>();
        List<String> roleCodes = new ArrayList<>();
        List<Role> roles = roleService.findRolesByUserId(user.getId());
        if (null != roles) {
            for (Role role : roles) {
                roleCodes.add(role.getName());
            }
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword(user.getPassword());
        userInfo.setUsername(user.getUsername());
        userInfo.setRoles(roleCodes);
        userInfo.setAuthorities(authorities);
        return userInfo;
    }
}
