package com.ecycle.auth.handler;

import javax.servlet.http.Cookie;
import com.alibaba.fastjson.JSONObject;
import com.ecycle.common.constants.TokenConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author wangweichen
 * @Date 2024/2/8
 * @Description 注销成功过滤器
 */
@Component
@Log4j2
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        HttpSession session = request.getSession();
        JSONObject result = new JSONObject();
        result.put("token", session.getId());
        Cookie cookie = new Cookie(TokenConstants.AUTHENTICATION, session.getId());
        cookie.setPath("/");
        cookie.setMaxAge(TokenConstants.EXPIRATION_SECONDS);
        response.addCookie(cookie);
        response.getWriter().append(result.toJSONString());
    }
}
