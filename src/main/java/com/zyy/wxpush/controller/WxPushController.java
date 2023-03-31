package com.zyy.wxpush.controller;


import com.zyy.chatgpt.ChatGptUtil;
import com.zyy.wxpush.config.WxConfig;
import com.zyy.wxpush.service.WxCheckSignatureService;
import com.zyy.wxpush.util.CheckUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * wx推控制器
 */
@RestController
@RequestMapping("/wx")
public class WxPushController {

    @Resource
    private WxCheckSignatureService wxCheckSignatureService;

    @Resource
    private WxConfig wxConfig;

    @Resource
    private ChatGptUtil chatGptUtil;


    /**
     * 验证
     */
    @GetMapping("login")
    public String validation(String signature, String timestamp, String nonce, String echostr) {
        if (CheckUtil.checkSignature(signature, timestamp, nonce, wxConfig.getCheckToken())) {
            return echostr;
        }
        return null;
    }

    /**
     * 消息处理
     *
     * @param request  请求
     * @param response 响应
     * @return {@link String}
     */
    @PostMapping("login")
    public String messageHandle(HttpServletRequest request, HttpServletResponse response) {
        return wxCheckSignatureService.messageHandle(request, response);
    }

    @GetMapping
    public String hello(String content) {
        return chatGptUtil.chatGptTurbo(content);
    }

}
