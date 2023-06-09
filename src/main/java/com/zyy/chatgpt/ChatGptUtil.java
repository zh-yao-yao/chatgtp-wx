package com.zyy.chatgpt;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyy.chatgpt.config.ChatGptConfig;
import com.zyy.chatgpt.constant.ChatGptConstant;
import com.zyy.chatgpt.constant.ComConstant;
import com.zyy.chatgpt.constant.GptTurboConstant;
import com.zyy.chatgpt.dto.CompletionsReqParams;
import com.zyy.chatgpt.dto.Messages;
import com.zyy.wxpush.util.OptionalString;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 聊天gpt跑龙套
 *
 * @author 5pyx55CG5ri4
 * @date 2022/12/13
 */
@Component
public class ChatGptUtil {

    @Resource
    private ChatGptConfig chatGptConfig;

    private static final Cache<String, String> MSG_CACHE = CacheUtil.newLFUCache(100);

    private static final Cache<String, Integer> COUNT_CACHE = CacheUtil.newLFUCache(100);

    private static final Cache<String, String> TEXT_CACHE = CacheUtil.newLFUCache(100);

    private static final Cache<String, String> IMAGE_CACHE = CacheUtil.newLFUCache(100);

    public String chatGptTurbo(String prompt) {
        int count = Optional.ofNullable(COUNT_CACHE.get(prompt)).orElse(1);
        String cache = MSG_CACHE.get(prompt);
        if (StrUtil.isNotBlank(cache)) {
            return cache;
        }
        // 微信服务器会在三秒发送三次请求
        if (count >= 3) {
            return OptionalString.ofNullable(TEXT_CACHE.get(prompt)).orElse("还在学习中,请稍后再试....");
        }
        COUNT_CACHE.put(prompt, count + 1);
        HttpRequest post = getHttpRequest(GptTurboConstant.COMPLETIONS_URL);
        CompletionsReqParams completionsReqParams = new CompletionsReqParams();
        completionsReqParams.setModel(chatGptConfig.getModel());
        Messages messages = new Messages();
        messages.setRole("user");
        messages.setContent(prompt);
        completionsReqParams.setMessages(Arrays.asList(messages));
        post.body(JSONObject.toJSONString(completionsReqParams));
        System.out.println("body is " + completionsReqParams.toString());
        HttpResponse execute = post.execute();
        String body = execute.body();
        System.out.println("execute body is " + body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONArray choices = jsonObject.getJSONArray(ComConstant.CHOICES);
        String ret = choices.stream().map(JSONObject::toJSONString)
                .map(JSONObject::parseObject)
                .map(s -> s.getJSONObject(ComConstant.MESSAGE).getString(ComConstant.CONTENT))
                .collect(Collectors.joining("\n"));
        MSG_CACHE.put(prompt, ret);
        TEXT_CACHE.put(prompt, ret);
        return ret;
    }


    public String chatGptTurbo(String prompt, String msgId) {
        int count = Optional.ofNullable(COUNT_CACHE.get(msgId)).orElse(1);
        String cache = MSG_CACHE.get(msgId);
        if (StrUtil.isNotBlank(cache)) {
            return cache;
        }
        if (count >= 3) {
            return OptionalString.ofNullable(TEXT_CACHE.get(prompt)).orElse("还在学习中,请稍后再试....");
        }
        COUNT_CACHE.put(msgId, count + 1);
        HttpRequest post = getHttpRequest(GptTurboConstant.COMPLETIONS_URL);
        CompletionsReqParams completionsReqParams = new CompletionsReqParams();
        completionsReqParams.setModel(chatGptConfig.getModel());
        Messages messages = new Messages();
        messages.setRole("user");
        messages.setContent(prompt);
        completionsReqParams.setMessages(Arrays.asList(messages));
        post.body(JSONObject.toJSONString(completionsReqParams));
        System.out.println("body is " + completionsReqParams.toString());
        HttpResponse execute = post.execute();
        String body = execute.body();
        System.out.println("execute body is " + body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONArray choices = jsonObject.getJSONArray(ComConstant.CHOICES);
        String ret = choices.stream().map(JSONObject::toJSONString)
                .map(JSONObject::parseObject)
                .map(s -> s.getJSONObject(ComConstant.MESSAGE).getString(ComConstant.CONTENT))
                .collect(Collectors.joining("\n"));
        MSG_CACHE.put(msgId, ret);
        TEXT_CACHE.put(prompt, ret);
        return ret;
    }

    public String chatGptMsg(String prompt, String msgId) {
        int count = Optional.ofNullable(COUNT_CACHE.get(msgId)).orElse(1);
        String cache = MSG_CACHE.get(msgId);
        if (StrUtil.isNotBlank(cache)) {
            return cache;
        }
        if (count >= 3) {
            return OptionalString.ofNullable(TEXT_CACHE.get(prompt)).orElse("还在学习中,请稍后再试....");
        }
        COUNT_CACHE.put(msgId, count + 1);
        HttpRequest post = getHttpRequest(ChatGptConstant.COMPLETIONS_URL);
        CompletionsReqParams completionsReqParams = new CompletionsReqParams();
        completionsReqParams.setModel(chatGptConfig.getModel());
        completionsReqParams.setTemperature(chatGptConfig.getTemperature());
        completionsReqParams.setMaxTokens(chatGptConfig.getMaxTokens());
        completionsReqParams.setPrompt(prompt);
        post.body(JSONObject.toJSONString(completionsReqParams));
        System.out.println("body is " + completionsReqParams.toString());
        HttpResponse execute = post.execute();
        String body = execute.body();
        System.out.println("execute body is " + body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONArray choices = jsonObject.getJSONArray(ComConstant.CHOICES);
        String ret = choices.stream().map(JSONObject::toJSONString)
                .map(JSONObject::parseObject)
                .map(s -> s.getString(ComConstant.TEXT))
                .collect(Collectors.joining("\n"));
        MSG_CACHE.put(msgId, ret);
        TEXT_CACHE.put(prompt, ret);
        return ret;
    }

    /**
     * chatGPT作画-微信公众号
     *
     * @param prompt
     * @param msgId
     * @return 返回图片的url
     */
    public String chatGptImage(String prompt, String msgId) {
        int count = Optional.ofNullable(COUNT_CACHE.get(msgId)).orElse(1);
        String cache = MSG_CACHE.get(msgId);
        if (StrUtil.isNotBlank(cache)) {
            return cache;
        }
        if (count >= 3) {
            return OptionalString.ofNullable(IMAGE_CACHE.get(prompt)).orElse("还在学习中,请稍后再试....");
        }
        COUNT_CACHE.put(msgId, count + 1);
        HttpRequest post = getHttpRequest(ChatGptConstant.IMAGE_URL);
        CompletionsReqParams completionsReqParams = new CompletionsReqParams();
        completionsReqParams.setPrompt(prompt);
        post.body(JSONObject.toJSONString(completionsReqParams));
        System.out.println("AI作画：body is " + completionsReqParams.toString());
        HttpResponse execute = post.execute();
        String body = execute.body();
        System.out.println("AI作画：execute body is " + body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONArray data = jsonObject.getJSONArray(ComConstant.DATA);
        // 默认一幅图
        String ret = data.getJSONObject(0).getString(ComConstant.URL);
        MSG_CACHE.put(msgId, ret);
        IMAGE_CACHE.put(prompt, ret);
        return ret;
    }

    /**
     * chatGPT作画
     *
     * @param prompt
     * @return 返回图片的url
     */
    public String chatGptImage(String prompt) {
        String cache = IMAGE_CACHE.get(prompt);
        if (StrUtil.isNotBlank(cache)) {
            return cache;
        }
        HttpRequest post = getHttpRequest(ChatGptConstant.IMAGE_URL);
        CompletionsReqParams completionsReqParams = new CompletionsReqParams();
        completionsReqParams.setPrompt(prompt);
        post.body(JSONObject.toJSONString(completionsReqParams));
        System.out.println("AI作画：body is " + completionsReqParams.toString());
        HttpResponse execute = post.execute();
        String body = execute.body();
        System.out.println("AI作画：execute body is " + body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONArray data = jsonObject.getJSONArray(ComConstant.DATA);
        // 默认一幅图
        String ret = data.getJSONObject(0).getString(ComConstant.URL);
        IMAGE_CACHE.put(prompt, ret);
        return ret;
    }

    private HttpRequest getHttpRequest(String apiUrl) {
        HttpRequest post = HttpUtil.createPost(apiUrl);
        post.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890)));
        post.header(ComConstant.AUTHORIZATION, ComConstant.BEARER.concat(chatGptConfig.getApiKey()));
        return post;
    }
}
