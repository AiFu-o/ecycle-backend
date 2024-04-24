package com.ecycle.auth.config;

import com.ecycle.auth.filter.UsernameAuthenticationFilter;
import com.ecycle.auth.filter.WxAuthenticationFilter;
import com.ecycle.auth.handler.CustomLogoutSuccessHandler;
import com.ecycle.auth.handler.CustomizeAuthenticationFailureHandler;
import com.ecycle.auth.handler.LoginSuccessHandler;
import com.ecycle.auth.handler.WxAuthAuthenticationSuccessHandler;
import com.ecycle.auth.provider.WxAuthenticationProvider;
import com.ecycle.common.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author wangweichen
 * @Date 2024/1/31
 * @Description 认证配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    @Resource
    private CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;

    @Resource
    private WxAuthAuthenticationSuccessHandler wxAuthAuthenticationSuccessHandler;

    @Resource
    private WxAuthenticationProvider wxAuthenticationProvider;

    @Resource
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Bean
    @Order(0)
    public SecurityFilterChain wxFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.authorizeHttpRequests(authorize -> authorize
                .antMatchers(("/wx/login")).permitAll()
                .anyRequest().authenticated()
        );
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        WxAuthenticationFilter filter = new WxAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(wxAuthAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(customizeAuthenticationFailureHandler);
        filter.setAuthenticationManager(wxAuthenticationManager());
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.authorizeHttpRequests(authorize -> authorize
                .antMatchers(("/login")).permitAll()
                .anyRequest().authenticated()
        );
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        UsernameAuthenticationFilter filter = new UsernameAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(customizeAuthenticationFailureHandler);
        filter.setAuthenticationManager(passwordAuthenticationManager());
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager passwordAuthenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public AuthenticationManager wxAuthenticationManager() {
        return new ProviderManager(wxAuthenticationProvider);
    }
}
