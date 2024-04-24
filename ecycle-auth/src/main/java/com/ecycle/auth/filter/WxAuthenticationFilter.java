package com.ecycle.auth.filter;

import com.alibaba.fastjson.JSONObject;
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

    public static final String SPRING_SECURITY_FORM_CODE_KEY = "jsCode";
    public static final String SPRING_SECURITY_FORM_ENCRYPTED_DATA_KEY = "encryptedData";
    public static final String SPRING_SECURITY_FORM_IV_KEY = "iv";
    public static final String SPRING_SECURITY_FORM_OPEN_ID_KEY = "openId";
    public static final String SPRING_SECURITY_FORM_TELEPHONE_KEY = "telephone";
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
        WxPrincipal principal = new WxPrincipal() ;
        String code;
        if(body.containsKey(SPRING_SECURITY_FORM_OPEN_ID_KEY) && body.containsKey(SPRING_SECURITY_FORM_TELEPHONE_KEY)){
            principal.setTelephone(body.getString(SPRING_SECURITY_FORM_TELEPHONE_KEY));
            principal.setOpenId(body.getString(SPRING_SECURITY_FORM_OPEN_ID_KEY));
            code = principal.getOpenId();
        } else {
            code = body.getString(SPRING_SECURITY_FORM_CODE_KEY);
            String encryptedData = body.getString(SPRING_SECURITY_FORM_ENCRYPTED_DATA_KEY);
            String iv = body.getString(SPRING_SECURITY_FORM_IV_KEY);
            principal.setEncryptedData(encryptedData);
            principal.setIv(iv);
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                principal, code);
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request,
                            UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
