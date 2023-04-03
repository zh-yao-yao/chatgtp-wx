package com.zyy.wxpush.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;


/**
 * 短信
 */
@Data
public class ImageMessage extends BaseMessage {

    @XStreamAlias(value = "Content")
    private String content;


    public ImageMessage() {
        this.setMsgType("image");
    }

}
