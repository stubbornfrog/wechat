package com.niuwajun.service.impl;

import com.alibaba.fastjson.JSON;
import com.niuwajun.pojo.enums.WxMessageTypeEnum;
import com.niuwajun.pojo.model.wechat.message.TextMessage;
import com.niuwajun.pojo.model.wechat.result.BaseResult;
import com.niuwajun.pojo.model.wechat.result.TextResult;
import com.niuwajun.service.WxMessageService;
import com.niuwajun.utils.WeiXinMessageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
@Service
public class WxMessageServiceImpl implements WxMessageService {

    @Override
    public String receiveAndResponseMessage(Map<String, String> messageMap) {
        // 发送方账号(用户方)
        String fromUserName = messageMap.get("FromUserName");
        // 接受方账号（公众号）
        String toUserName = messageMap.get("ToUserName");
        // 消息类型
        String msgType = messageMap.get("MsgType");

        String jsonString = JSON.toJSONString(messageMap);
        //普通消息类型
        if (msgType.equals(WxMessageTypeEnum.REQ_MESSAGE_TYPE_TEXT.code)) {
            TextMessage message = JSON.parseObject(jsonString, TextMessage.class);
            // 返回消息
            return buildTextMessage(toUserName, fromUserName, "傻狗，你好");
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
