package com.ecycle.auth.handler;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangweichen
 */
@Component
public class CustomizeAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        String errMessage;
        if(exception instanceof BadCredentialsException) {
            errMessage=String.format("用户名和密码不匹配，请重新输入!");
        }else if(exception instanceof AccountExpiredException) {
            errMessage="用户已过期，请联系管理员！";
        }else if(exception instanceof LockedException) {
            errMessage="该用户被禁止登录，无法正常登录，请联系系统管理员！";
        }else if(exception instanceof DisabledException) {
            errMessage="该用户被停用，无法正常登录，请联系系统管理员！";
        }else if(exception instanceof UsernameNotFoundException){
            errMessage="用户不存在！";
        }else{
            errMessage="未知错误，请稍后再试！ ";
        }
        exception.printStackTrace();
        if(StringUtils.isNotEmpty(errMessage)){
            response.setStatus(401);
            try (PrintWriter out = response.getWriter()) {
                out.write(errMessage);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
