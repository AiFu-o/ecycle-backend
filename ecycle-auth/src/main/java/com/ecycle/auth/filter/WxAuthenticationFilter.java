package com.ecycle.auth.filter;

import com.alibaba.fastjson.JSONObject;
import com.ecycle.auth.web.info.WxUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author wangweichen
 * @Date 2024/4/1
 * @Description 微信登录
 */
public class WxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_JS_CODE_KEY = "jsCode";
    public WxAuthenticationFilter() {
        super(new AntPathRequestMatcher("/wx/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        InputStream is = request.getInputStream();
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = is.read(b)) != -1;){
            sb.append(new String(b, 0, n));
        }
        is.close();
        JSONObject body = JSONObject.parseObject(sb.toString());

        String jsCode = body.getString(SPRING_SECURITY_FORM_JS_CODE_KEY);

        WxUserInfo wxUserInfo = new WxUserInfo();
        if(StringUtils.isNotEmpty(body.getString("nickname"))){
            wxUserInfo.setNickName(body.getString("nickname"));
        }
        if(StringUtils.isNotEmpty(body.getString("avatarUrl"))){
            wxUserInfo.setAvatarUrl(body.getString("avatarUrl"));
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                jsCode, wxUserInfo);
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request,
                            UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
