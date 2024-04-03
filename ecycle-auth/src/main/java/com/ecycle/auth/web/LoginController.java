package com.ecycle.auth.web;

import com.ecycle.auth.service.LoginService;
import com.ecycle.common.context.RestResponse;
import com.ecycle.common.utils.JwtTokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangweichen
 * @Date 2024/4/2
 * @Description TODO
 */
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public RestResponse<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        return loginService.doLogin(username, password);
    }

    @PostMapping("/wx-mini-app/login")
    public RestResponse<String> wxMiniAppLogin(@RequestBody Map<String, String> requestParams) {
        return loginService.doWxMiniAppLogin(requestParams);
    }
}
