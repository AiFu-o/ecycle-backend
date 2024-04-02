package com.ecycle.auth.config.filter;

import com.ecycle.auth.context.UserInfo;
import com.ecycle.common.constants.TokenConstants;
import com.ecycle.common.utils.JwtTokenUtils;
import com.ecycle.common.utils.SpringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/1
 * @Description TODO
 */
public class UsernameAuthenticationFilter extends BasicAuthenticationFilter implements TokenConstants {

    public UsernameAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(AUTHENTICATION);

        if (header == null || !header.startsWith(PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX, "");

        String userId = JwtTokenUtils.getUserId(token);
        if(null == userId){
            chain.doFilter(request, response);
            return;
        }

        RedisTemplate<String, Object> redisTemplate = SpringUtils.getBean("redisTemplate");
        if (null == redisTemplate.opsForValue().get(REDIS_PREFIX + userId)) {
            chain.doFilter(request, response);
            return;
        }

        UserInfo loginUser = (UserInfo) redisTemplate.opsForValue().get(REDIS_PREFIX + userId);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        if (loginUser == null) {
            chain.doFilter(request, response);
            return;
        }
        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), null, roles);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }
}
