package com.ecycle.auth.context;

import com.ecycle.auth.model.Role;
import com.ecycle.auth.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wangweichen
 * @Date 2024/4/2
 * @Description 用户信息缓存数据结构
 */
@Data
public class UserInfo implements UserDetails {

    public UserInfo(){

    }

    public UserInfo(User user, List<String> authorities, List<String> roles) {
        this.user = user;
        this.roles = roles;
        this.authorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * token
     */
    private String token;

    private User user;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    private List<? extends GrantedAuthority> authorities;

    @Override
    @JsonIgnore
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
