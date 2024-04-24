package com.ecycle.common.config;

import com.ecycle.common.CustomHeaderHttpSessionIdResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author wangweichen
 * @Date 2024/4/24
 * @Description TODO
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60,
        redisNamespace = "ecycle.session")
public class HttpSessionConfig {
    @Bean
    public CustomHeaderHttpSessionIdResolver customHeaderHttpSessionIdResolver() {
        return new CustomHeaderHttpSessionIdResolver();
    }

}
