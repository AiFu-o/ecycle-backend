package com.ecycle.auth.handler;

import com.ecycle.auth.service.UserInfoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangweichen
 * @Date 2024/2/8
 * @Description 注销成功过滤器
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Resource
    private UserInfoService userInfoService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        userInfoService.delCurrentUserInfo();
    }
}
