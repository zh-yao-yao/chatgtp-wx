package com.zyy.wxpush.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * wx配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx-config")
public class WxConfig {

    private String appId;

    private String appSecret;

    private String checkToken;

}

