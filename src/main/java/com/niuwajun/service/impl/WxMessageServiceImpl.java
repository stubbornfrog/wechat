package com.niuwajun.service.impl;

import com.alibaba.fastjson.JSON;
import com.niuwajun.chatgpt.CustomChatGpt;
import com.niuwajun.chatgpt.ErnieBot;
import com.niuwajun.pojo.enums.WxMessageTypeEnum;
import com.niuwajun.pojo.model.wechat.message.TextMessage;
import com.niuwajun.pojo.model.wechat.result.BaseResult;
import com.niuwajun.pojo.model.wechat.result.TextResult;
import com.niuwajun.pojo.resp.ernie.ErnieChatResp;
import com.niuwajun.service.WxMessageService;
import com.niuwajun.utils.WeiXinMessageUtil;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
@Service
public class WxMessageServiceImpl implements WxMessageService {


    @Override
    public String receiveAndResponseMessage(Map<String, String> messageMap) {
        // 消息类型
        String msgType = messageMap.get("MsgType");
        String jsonString = JSON.toJSONString(messageMap);
        //普通消息类型
        if (msgType.equals(WxMessageTypeEnum.REQ_MESSAGE_TYPE_TEXT.code)) {
            TextMessage message = JSON.parseObject(jsonString, TextMessage.class);
            // 调用chatGpt
            CloseableHttpClient httpClient = HttpClients.createDefault();
            long start = System.currentTimeMillis();

//            String apiKey = "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";
//            CustomChatGpt customChatGpt = new CustomChatGpt(apiKey);
//            // 根据自己的网络设置吧
//            customChatGpt.setResponseTimeout(5000);
//            String answer = customChatGpt.getAnswer(httpClient, messageMap.get("Content").trim());

            ErnieBot ernieBot = new ErnieBot();
            String answer = ernieBot.getAnswer(httpClient, messageMap.get("Content").trim());

            long end = System.currentTimeMillis();
            System.out.println("该回答花费时间为：" + (end - start) / 1000.0 + "秒");
            System.out.println(answer);
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 返回消息
            return buildTextMessage(messageMap.get("ToUserName"), messageMap.get("FromUserName"), answer);
        } else {
            return null;
        }
    }

    public static String buildTextMessage(String fromUserName, String toUserName, String content) {
        BaseResult baseResult = getBaseResult(fromUserName, toUserName, WxMessageTypeEnum.RESP_MESSAGE_TYPE_TEXT.code);
        TextResult resultMessage = new TextResult();
        BeanUtils.copyProperties(baseResult, resultMessage);
        resultMessage.setContent(content);
        String format = "<xml>\n" + "<ToUserName><![CDATA[%1$s]]></ToUserName>\n"
                + "<FromUserName><![CDATA[%2$s]]></FromUserName>\n"
                + "<CreateTime>%3$s</CreateTime>\n"
                + "<MsgType><![CDATA[%4$s]]></MsgType>\n"
                + "<Content><![CDATA[%5$s]]></Content>\n"
                + "</xml>";
        return String.format(format, toUserName, fromUserName, resultMessage.getCreateTime(), resultMessage.getMsgType(), resultMessage.getContent());

    }

    private static BaseResult getBaseResult(String fromUserName, String toUserName, String msgType) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCreateTime(System.currentTimeMillis());
        // 微信回复消息时，toUserName就是用户方(接收时的fromUserName)，fromUserName为当前公众号账户(接收时的toUserName)
        baseResult.setToUserName(toUserName);
        baseResult.setFromUserName(fromUserName);
        baseResult.setMsgType(msgType);
        return baseResult;
    }

}
