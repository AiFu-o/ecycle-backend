package com.ecycle.auth.config;

import com.ecycle.auth.config.filter.UsernameAuthenticationFilter;
import com.ecycle.auth.config.handler.CustomLogoutSuccessHandler;
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
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Resource
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    @Order(0)
    public SecurityFilterChain passwordLoginFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.authorizeHttpRequests(authorize -> authorize
                .antMatchers(("/login")).permitAll()
                .anyRequest().authenticated()
        );
        UsernameAuthenticationFilter filter = new UsernameAuthenticationFilter(authenticationManager());
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authenticationProvider);
    }
}
