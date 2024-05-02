package com.ecycle.common.config;

import com.ecycle.common.config.properties.IgnoreWhiteProperties;
import com.ecycle.common.filter.NoAuthRequestMatcher;
import com.ecycle.common.handler.CustomAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @Resource
    private NoAuthRequestMatcher noAuthRequestMatcher;

    @Bean
    @Order(0)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.formLogin().disable();
        http.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers(noAuthRequestMatcher).permitAll();
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
