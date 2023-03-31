package com.zyy.wxpush.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * wx检查签名服务
 */
public interface WxCheckSignatureService {

    /**
     * 消息处理
     *
     * @param request  请求
     * @param response 响应
     * @return {@link String}
     */
    String messageHandle(HttpServletRequest request, HttpServletResponse response);
}
