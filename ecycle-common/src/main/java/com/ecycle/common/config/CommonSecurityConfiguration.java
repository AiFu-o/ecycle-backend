package com.ecycle.common.config;

import com.ecycle.common.config.properties.IgnoreWhiteProperties;
import com.ecycle.common.handler.CustomAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;

/**
 * @author wangweichen
 * @Date 2024/4/24
 * @Description TODO
 */
@EnableWebSecurity
@Configuration
@ConditionalOnExpression("#{!'ecycle-auth'.equals(environment.getProperty('spring.application.name'))}")
public class CommonSecurityConfiguration {

    @Resource
    private IgnoreWhiteProperties ignoreWhiteProperties;

    @Resource
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    @Order(0)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.formLogin().disable();
        http.authorizeHttpRequests(authorize -> {
            if (null != ignoreWhiteProperties.getUrls() && ignoreWhiteProperties.getUrls().size() > 0) {
                for (String url : ignoreWhiteProperties.getUrls()) {
                    authorize.antMatchers((url)).permitAll();
                }
            }
            authorize.anyRequest().authenticated();
        });
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        return http.build();
    }

}
