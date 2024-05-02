package com.ecycle.common.filter;

import com.ecycle.common.constants.TokenConstants;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * @author wangweichen
 * @Date 2024/4/30
 * @Description feign header 拦截器 自动加 token
 */
public class HeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            if (null != CurrentUserInfoUtils.getCurrentUserInfo()) {
                request = requestAttributes.getRequest();
                HttpSession session = request.getSession();
                // 在这里添加header
                template.header(TokenConstants.AUTHENTICATION, session.getId());
            } else {
                template.header(TokenConstants.NO_AUTH_HEADER_NAME, TokenConstants.SECRET);
            }
        } else {
            template.header(TokenConstants.NO_AUTH_HEADER_NAME, TokenConstants.SECRET);
        }
    }
}
