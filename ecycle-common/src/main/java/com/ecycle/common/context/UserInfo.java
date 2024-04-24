package com.ecycle.common.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/4/2
 * @Description 用户信息缓存数据结构
 */
@Data
public class UserInfo implements UserDetails {

    private UUID userId;

    private String username;

    private String password;

    private String openId;

    private String telephone;

    /**
     * 角色
     */
    private List<String> roles;

    private List<? extends GrantedAuthority> authorities;

    @Override
    @JsonIgnore
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
