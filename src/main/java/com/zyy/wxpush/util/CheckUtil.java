package com.zyy.wxpush.util;

import java.util.Arrays;


/**
 * 检查实效
 *
 */
public class CheckUtil {


    public static boolean checkSignature(String signature, String timestamp, String nonce,String token) {
        String[] str = new String[]{token, timestamp, nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuilder buffer = new StringBuilder();
        for (String s : str) {
            buffer.append(s);
        }
        //进行sha1加密
        String temp = SHA1.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }


}
