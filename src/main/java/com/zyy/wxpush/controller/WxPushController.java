package com.zyy.wxpush.controller;


import cn.hutool.core.io.IoUtil;
import com.zyy.chatgpt.ChatGptUtil;
import com.zyy.wxpush.config.WxConfig;
import com.zyy.wxpush.service.WxCheckSignatureService;
import com.zyy.wxpush.util.CheckUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * wx推控制器
 */
@CrossOrigin
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
        String messageHandle = wxCheckSignatureService.messageHandle(request, response);
        System.out.println("响应微信服务器: " + messageHandle);
        return messageHandle;
    }

    @GetMapping
    public Object hello(@RequestParam(value="content") String content, HttpServletResponse response) {
        if (content.indexOf("画") == 0) {
            String imageUrl = chatGptUtil.chatGptImage(content);
            try {
                URL urls = new URL(imageUrl);
                HttpURLConnection conn = (HttpURLConnection)urls.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(50 * 1000);
                conn.setReadTimeout(50 * 1000);
                //通过输入流获取图片数据
                InputStream inputStream = conn.getInputStream();
                ServletOutputStream outputStream = response.getOutputStream();
                response.setContentType("image/jpg");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("image", String.valueOf(StandardCharsets.UTF_8)) + ".jpg");
                response.setCharacterEncoding("utf-8");
                IoUtil.copy(inputStream, outputStream, IoUtil.DEFAULT_BUFFER_SIZE);
                IoUtil.close(inputStream);
                IoUtil.close(outputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return chatGptUtil.chatGptTurbo(content);
        }
        return null;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
