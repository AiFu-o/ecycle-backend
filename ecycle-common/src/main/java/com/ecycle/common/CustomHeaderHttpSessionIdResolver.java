package com.ecycle.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/24
 * @Description TODO
 */
public class CustomHeaderHttpSessionIdResolver implements HttpSessionIdResolver {

    private String headerName = "Authorization";
    private String paramName = "authorization";

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        List<String> ids = new ArrayList<>();
        String sessionId = request.getHeader(this.headerName);
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = request.getParameter(paramName);
        }
        if (!StringUtils.isEmpty(sessionId)) {
            ids.add(sessionId);
        }
        return ids;
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        response.setHeader(this.headerName, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.headerName, "");
    }

}