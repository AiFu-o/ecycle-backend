package com.ecycle.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangweichen
 * @Date 2024/4/26
 * @Description TODO
 */

@Component
@ConfigurationProperties(prefix = "wx.xcx-pay")
@Data
public class WxConfiguration {
    private String appId;
    private String appSecret;
    private String merchantId;
    private String payNotifyUrl;
    private String refundNotifyUrl;
    private String privateKeyFilePath;
    private String merchantSerialNumber;
    private String apiV3Key;
}
