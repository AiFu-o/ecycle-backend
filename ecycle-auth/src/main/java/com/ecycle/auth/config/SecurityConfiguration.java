package com.ecycle.auth.config;

import com.ecycle.auth.filter.CustomConcurrentSessionFilter;
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
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

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

    @Resource
    private SessionRegistry sessionRegistry;

    @Bean
    @Order(0)
    public SecurityFilterChain wxFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.antMatcher("/wx/login").authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        WxAuthenticationFilter filter = new WxAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(wxAuthAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(customizeAuthenticationFailureHandler);
        filter.setAuthenticationManager(wxAuthenticationManager());
        RequestMatcher requestMatcher = new AntPathRequestMatcher("/wx/login", "POST");
        filter.setRequiresAuthenticationRequestMatcher(requestMatcher);
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().maximumSessions(1);
        http.csrf().disable();
        http.cors().disable();
        http.authorizeRequests()
                .antMatchers(("/login")).permitAll()
                .anyRequest().authenticated();
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        UsernameAuthenticationFilter filter = new UsernameAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(customizeAuthenticationFailureHandler);
        filter.setAuthenticationManager(passwordAuthenticationManager());
        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));

        CustomConcurrentSessionFilter concurrentSessionFilter = new CustomConcurrentSessionFilter(sessionRegistry) ;
        http.addFilterAt(concurrentSessionFilter, ConcurrentSessionFilter.class);
        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        http.logout().logoutUrl("/logout").invalidateHttpSession(true)
                .permitAll();
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
