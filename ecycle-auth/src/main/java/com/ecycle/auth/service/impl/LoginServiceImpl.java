package com.ecycle.auth.service.impl;

import com.ecycle.auth.context.UserInfo;
import com.ecycle.auth.model.User;
import com.ecycle.auth.service.LoginService;
import com.ecycle.common.constants.TokenConstants;
import com.ecycle.common.context.RestResponse;
import com.ecycle.common.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Map;
import java.util.HashMap;

/**
 * @author wangweichen
 * @Date 2024/4/2
 * @Description TODO
 */
@Service
@Log4j2
public class LoginServiceImpl implements LoginService, TokenConstants {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public RestResponse<String> doLogin(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)){
            throw new AuthenticationServiceException("登录失败");
        }
        UserInfo userInfo = (UserInfo) authenticate.getPrincipal();
        Map<String,Object> map = new HashMap<>();
        User user = userInfo.getUser();
        map.put(F_USER, user);
        map.put(F_USERNAME, user.getUsername());
        map.put(F_AUTHORITIES, userInfo.getAuthorities());
        map.put(F_ROLES, userInfo.getRoles());
        String userId = user.getId();
        String token = JwtTokenUtils.createToken(userId, map);
        Claims claims = JwtTokenUtils.parseToken(token);
        if(null == claims){
            throw new NullPointerException();
        }
        userInfo.setToken(token);
        userInfo.setLoginTime(claims.getIssuedAt().getTime());
        userInfo.setExpireTime(claims.getExpiration().getTime());
        redisTemplate.opsForValue().set(REDIS_PREFIX + userId, userInfo);
        return RestResponse.success(token);
    }
}
