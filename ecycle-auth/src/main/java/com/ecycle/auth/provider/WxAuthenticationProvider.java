package com.ecycle.auth.provider;

import com.ecycle.auth.service.WxService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangweichen
 * @Date 2024/4/3
 * @Description 微信小程序登录校验
 */
@Component
public class WxAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private WxService wxService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Boolean isEffective = wxService.checkSessionId((String) authentication.getPrincipal(), (String) authentication.getCredentials());
        if(!isEffective){
            throw new AuthenticationServiceException("sessionId 无效");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication != null && authentication == UsernamePasswordAuthenticationToken.class;
    }
}
