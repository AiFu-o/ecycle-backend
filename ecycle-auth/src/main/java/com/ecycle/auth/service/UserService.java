package com.ecycle.auth.service;

import com.ecycle.auth.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wangweichen
* @description 针对表【ecycle_user】的数据库操作Service
* @createDate 2024-01-24 14:21:05
*/
public interface UserService extends IService<User> {
    User findByUsername(String username);
}
