package com.ecycle.auth.service.impl;

import com.ecycle.auth.context.UserInfo;
import com.ecycle.auth.exception.UserException;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.LoginService;
import com.ecycle.auth.service.UserInfoService;
import com.ecycle.auth.service.UserService;
import com.ecycle.auth.service.WxService;
import com.ecycle.common.constants.TokenConstants;
import com.ecycle.common.context.RestResponse;
import com.ecycle.common.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/4/2
 * @Description TODO
 */
@Service
@Log4j2
public class LoginServiceImpl implements LoginService, TokenConstants {

    @Resource(name = "passwordAuthenticationManager")
    private AuthenticationManager passwordAuthenticationManager;

    @Resource(name = "wxAuthenticationManager")
    private AuthenticationManager wxAuthenticationManager;

    @Resource
    private UserService userService;

    @Resource
    private WxService wxService;

    @Resource
    private UserInfoService userInfoService;

    @Override
    public RestResponse<String> doLogin(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = passwordAuthenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new AuthenticationServiceException("登录失败");
        }
        UserInfo userInfo = (UserInfo) authenticate.getPrincipal();
        String token = createToken(userInfo, userInfo.getUser());
        return RestResponse.success(token);
    }

    private String createToken(UserInfo userInfo, User user) {
        Map<String, Object> map = new HashMap<>();
        map.put(F_USER, user);
        map.put(F_USERNAME, user.getUsername());
        map.put(F_AUTHORITIES, userInfo.getAuthorities());
        map.put(F_ROLES, userInfo.getRoles());
        if (StringUtils.isNotEmpty(user.getOpenId())) {
            map.put(F_OPEN_ID, user.getOpenId());
        }
        UUID userId = user.getId();
        String token = JwtTokenUtils.createToken(userId, map);
        Claims claims = JwtTokenUtils.parseToken(token);
        if (null == claims) {
            throw new NullPointerException();
        }
        userInfo.setToken(token);
        userInfo.setLoginTime(claims.getIssuedAt().getTime());
        userInfo.setExpireTime(claims.getExpiration().getTime());

        userInfoService.saveCurrentUserInfo(userId, userInfo);
        return token;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse<String> doWxMiniAppLogin(Map<String, String> requestParams) {
        if (!requestParams.containsKey("jsCode") || StringUtils.isEmpty(requestParams.get("jsCode"))) {
            throw new NullPointerException("jsCode is not empty!");
        }

        if (!requestParams.containsKey("phoneCode") || StringUtils.isEmpty(requestParams.get("phoneCode"))) {
            throw new NullPointerException("phoneCode is not empty!");
        }

        String accessToken = wxService.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            throw new NullPointerException("accessToken is not empty!");
        }

        User user = new User();
        Map<String, String> loginResult = wxService.getMiniAppSessionId(requestParams.get("jsCode"));
        String sessionId = loginResult.get("sessionId");
        String openId = loginResult.get("openId");

        String telephone = wxService.getTelephone(requestParams.get("phoneCode"), openId);
        user.setOpenId(openId);
        user.setUsername(telephone);

        userService.save(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(telephone, openId);
        Authentication authenticate = wxAuthenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new AuthenticationServiceException("登录失败");
        }
        UserInfo userInfo = (UserInfo) authenticate.getPrincipal();

        String token = createToken(userInfo, userInfo.getUser());
        return RestResponse.success(token);
    }

}
