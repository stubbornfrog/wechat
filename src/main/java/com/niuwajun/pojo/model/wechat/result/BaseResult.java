package com.niuwajun.pojo.model.wechat.result;

import lombok.Data;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
@Data
public class BaseResult {

    /**
     * 接收方帐号（收到的OpenID）
     */
    private String ToUserName;
    /**
     * 开发者微信号
     */
    private String FromUserName;
    /**
     * 消息创建时间 （整型）
     */
    private Long CreateTime;
    /**
     * 消息类型（text/music/news）
     */
    private String MsgType;
}
