package com.ecycle.auth.web;

import com.ecycle.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/queryAll")
    public Object queryAll() {
        return userService.list();
    }
}
