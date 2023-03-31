package com.zyy.chatgpt.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 完成要求参数
 */
@Data
public class CompletionsReqParams {

    private String model;

    private String prompt;

    private Double temperature;

    @JSONField(name = "max_tokens")
    private Integer maxTokens;

    private List<Messages> messages;
}
