package com.ecycle.auth.service.impl;

import com.ecycle.auth.context.UserInfo;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.UserService;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        List<String> authorities = new ArrayList<>();
        return new UserInfo(user, authorities);
    }
}
