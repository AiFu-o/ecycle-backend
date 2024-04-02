package com.ecycle.auth.web;

import com.ecycle.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangweichen
 * @Date 2024/3/14
 * @Description 权限相关接口
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/hasAuth")
    public Object hasAuth() {
        return true;
    }

}
