package com.zyy.wxpush.service.impl;


import com.zyy.chatgpt.ChatGptUtil;
import com.zyy.wxpush.constant.WxConstant;
import com.zyy.wxpush.service.WxCheckSignatureService;
import com.zyy.wxpush.util.WxPushMessageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * impl wx检查签名服务
 */
@Service
public class WxCheckSignatureServiceImpl implements WxCheckSignatureService {

    @Resource
    private WxPushMessageUtil messageUtil;

    @Resource
    private ChatGptUtil chatGptUtil;

    @Override
    public String messageHandle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = messageUtil.parseXml(request);
        System.out.println("map {}" + map);
        String msgType = map.get(WxConstant.MSG_TYPE_NAME);
        return autoResp(msgType, map);
    }

    /**
     * 根据消息类型自动回复
     */
    private String autoResp(String type, Map<String, String> map) {
        String openId = map.get(WxConstant.FROM_USER_NAME);
        String toUserName = map.get(WxConstant.TO_USER_NAME);
        String msgId = map.get(WxConstant.MSG_ID);
        String content = map.get(WxConstant.CONTENT);
        switch (type) {
            // 文本类型
            case WxConstant.RESP_MESSAGE_TYPE_TEXT:
                String msg = chatGptUtil.chatGptMsg(content, msgId);
                return messageUtil.sendCustomText(openId, toUserName, msg);
            // 关注 和 取消关注类型
            case WxConstant.REQ_MESSAGE_TYPE_EVENT:
                if (map.get(WxConstant.EVENT_NAME).equals(WxConstant.SUBSCRIBE_NAME)) {
                    //发送关注文案
                    return messageUtil.sendCustomText(openId, toUserName, "大帅逼，欢迎你！");
                } else {
                    return null;
                }
            default:
                return null;

        }
    }

}
