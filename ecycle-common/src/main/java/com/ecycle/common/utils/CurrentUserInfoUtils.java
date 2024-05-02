package com.ecycle.common.utils;

import com.ecycle.common.context.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/4/24
 * @Description 获取当前登录用户信息工具类
 */
public class CurrentUserInfoUtils {

    public static UserInfo getCurrentUserInfo(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes == null) {
            return null;
        }
        request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        if(null == session.getAttribute("SPRING_SECURITY_CONTEXT")){
            return null;
        }
        SecurityContextImpl context = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return (UserInfo) context.getAuthentication().getPrincipal();
    }

    public static UUID getCurrentUserId() {
        UserInfo userInfo = getCurrentUserInfo();
        if (null == userInfo) {
            throw new NullPointerException("用户未登录");
        }
        return userInfo.getUserId();
    }

}
