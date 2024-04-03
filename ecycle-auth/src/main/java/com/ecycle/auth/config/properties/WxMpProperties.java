package com.ecycle.auth.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangweichen
 * @Date 2024/4/3
 * @Description 微信公众平台配置
 */
@Configuration
@RefreshScope
@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {

    private String url;
    private String appid;
    private String secret;

}
