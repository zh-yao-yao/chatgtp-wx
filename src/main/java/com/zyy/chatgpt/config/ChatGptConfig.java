package com.zyy.chatgpt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * chatgpt配置
 *
 * @author user
 */
@Data
@Component
@ConfigurationProperties(prefix = "chatgpt-config")
public class ChatGptConfig {

    private String model;

    private Double temperature;

    private Integer maxTokens;

    private String apiKey;
}

