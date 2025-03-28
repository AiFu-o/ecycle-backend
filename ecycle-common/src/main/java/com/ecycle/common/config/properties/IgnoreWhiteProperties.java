package com.ecycle.common.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/1
 * @Description TODO
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "security.ignore")
@Data
public class IgnoreWhiteProperties {
    /**
     * 放行白名单配置，网关不校验此处的白名单
     */
    private List<String> urls = new ArrayList<>();
}
