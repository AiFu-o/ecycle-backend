package com.ecycle.auth.handler;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangweichen
 */
@Component
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try (PrintWriter out = response.getWriter()) {
            out.append("true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
