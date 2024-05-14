package com.ecycle.auth.provider;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.UserService;
import com.ecycle.common.context.UserInfo;
import lombok.extern.log4j.Log4j2;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangweichen
 * @Date 2024/4/3
 * @Description 微信小程序登录校验
 */
@Component
@Log4j2
public class WxAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private WxMaService wxMaService;

    @Resource
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jsCode = (String) authentication.getPrincipal();

        WxMaJscode2SessionResult session;
        try {
            session = wxMaService.getUserService().getSessionInfo(jsCode);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
        String openId = session.getOpenid();
        User user = userService.findByOpenId(openId);

        if (null == user) {
            // 创建用户
            user = userService.createWxFirstLoginUser(openId);
        }

        UserInfo userInfo = userService.buildUserInfo(user);

        return new UsernamePasswordAuthenticationToken(userInfo, authentication.getCredentials(), authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }
}
