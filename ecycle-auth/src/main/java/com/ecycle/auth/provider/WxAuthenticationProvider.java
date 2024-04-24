package com.ecycle.auth.provider;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.ecycle.auth.filter.WxPrincipal;
import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.RoleService;
import com.ecycle.auth.service.UserService;
import com.ecycle.common.context.UserInfo;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/3
 * @Description 微信小程序登录校验
 */
@Component
public class WxAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private WxMaService wxMaService;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxPrincipal principal = (WxPrincipal) authentication.getPrincipal();
        String openId = principal.getOpenId();
        User user = userService.findByOpenId(openId);

        if (null == user) {
            String code = (String) authentication.getCredentials();
            WxMaJscode2SessionResult session;
            try {
                session = wxMaService.getUserService().getSessionInfo(code);
            } catch (WxErrorException e) {
                throw new RuntimeException(e);
            }
            openId = session.getOpenid();
            String sessionKey = session.getSessionKey();
            // 先临时用着 因为新版本收费
            WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, principal.getEncryptedData(), principal.getIv());
            // 创建用户
            user = userService.createWxFirstLoginUser(phoneNoInfo.getPhoneNumber(), openId);
        }

        UserInfo userInfo = userService.buildUserInfo(user);

        return new UsernamePasswordAuthenticationToken(userInfo, authentication.getCredentials(), authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == UsernamePasswordAuthenticationToken.class;
    }
}
