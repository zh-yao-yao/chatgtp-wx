package com.zyy.wxpush.constant;


/**
 * 微信常量
 */
public interface WxConstant {

    /**
     * 获取access_token
     */
    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    String UNION_ID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 自定义发送
     */
    String CUSTOM_SEND = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";



    /**
     * access_token键
     */
    String ACCESS_TOKEN_KEY = "access_token";

    String UNION_ID_KEY = "unionid";

    /**
     * 到期 键
     */
    String EXPIRES_IN_KEY = "expires_in";

    /**
     * access_token缓存键
     */
    String ACCESS_TOKEN_CACHE_KEY = "WX_ACCESS_TOKEN_CACHE_KEY";

    /**
     * 发送模板信息URL
     */
    String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";


    /**
     * 返回消息类型：文本
     */
    String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 返回消息类型：图片
     */
    String RESP_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 返回消息类型：语音
     */
    String RESP_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 返回消息类型：视频
     */
    String RESP_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 请求消息类型：文本
     */
    String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：视频
     */
    String REQ_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 请求消息类型：推送
     */
    String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 事件类型：VIEW(自定义菜单URl视图)
     */
    String EVENT_TYPE_VIEW = "VIEW";

    /**
     * 事件类型：LOCATION(上报地理位置事件)
     */
    String EVENT_TYPE_LOCATION = "LOCATION";

    /**
     * 事件类型：LOCATION(上报地理位置事件)
     */
    String EVENT_TYPE_SCAN = "SCAN";


    /**
     * xml名称
     */
    String XML_NAME = "xml";

    /**
     * 消息类型名称
     */
    String MSG_TYPE_NAME = "MsgType";

    /**
     * 事件名称
     */
    String EVENT_NAME = "Event";

    /**
     * 订阅名称
     */
    String SUBSCRIBE_NAME = "subscribe";

    /**
     * 用户名称
     */
    String FROM_USER_NAME = "FromUserName";

    String TO_USER_NAME = "ToUserName";

    String MSG_ID = "MsgId";

    String CONTENT = "Content";


    String ERR_MSG_KEY = "errmsg";

    String OK = "ok";


}
