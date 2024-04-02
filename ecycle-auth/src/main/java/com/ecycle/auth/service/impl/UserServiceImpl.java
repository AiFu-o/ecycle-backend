package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.UserService;
import com.ecycle.auth.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author wangweichen
* @description 针对表【ecycle_user】的数据库操作Service实现
* @createDate 2024-01-24 14:21:05
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User findByUsername(String username) {
        return baseMapper.findByUsername(username);
    }
}




