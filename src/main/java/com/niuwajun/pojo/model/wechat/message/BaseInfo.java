package com.niuwajun.pojo.model.wechat.message;

import lombok.Data;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
@Data
public class BaseInfo {

    /**
     * 开发者微信号
     */
    private String ToUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    private String FromUserName;
    /**
     * 文本 图片 语音 视频 小视频 地理位置 链接 事件
     */
    private String MsgType;
    /**
     * 消息创建时间 （整型）
     */
    private long CreateTime;
    /**
     * 消息id
     */
    private String MsgId;


}
