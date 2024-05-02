package com.ecycle.common.filter;

import com.ecycle.common.constants.TokenConstants;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangweichen
 * @Date 2024/5/2
 * @Description TODO
 */
@Component
public class NoAuthRequestMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {
        return null != request.getHeader(TokenConstants.NO_AUTH_HEADER_NAME) &&
                request.getHeader(TokenConstants.NO_AUTH_HEADER_NAME).equals(TokenConstants.SECRET);
    }
}
