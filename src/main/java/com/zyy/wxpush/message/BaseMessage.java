package com.zyy.wxpush.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 基础信息
 */
@Data
public class BaseMessage {

    @XStreamAlias(value = "ToUserName")
    private String toUserName;

    @XStreamAlias(value = "FoUserName")
    private String fromUserName;

    @XStreamAlias(value = "CreateTime")
    private Long createTime;

    @XStreamAlias(value = "MsgType")
    private String msgType;

}
