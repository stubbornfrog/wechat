package com.niuwajun.pojo.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
public enum WxMessageTypeEnum {

    /**
     * 请求消息类型：文本
     */
    REQ_MESSAGE_TYPE_TEXT("text", "文本"),

    /**
     * 请求消息类型：图片
     */
    REQ_MESSAGE_TYPE_IMAGE("image", "图片"),

    /**
     * 请求消息类型：语音
     */
    REQ_MESSAGE_TYPE_VOICE("voice", "语音"),

    /**
     * 请求消息类型：视频
     */
    REQ_MESSAGE_TYPE_VIDEO("video", "视频"),

    /**
     * 请求消息类型：链接
     */
    REQ_MESSAGE_TYPE_LINK("link", "链接"),

    /**
     * 请求消息类型：地理位置
     */
    REQ_MESSAGE_TYPE_LOCATION("location", "地理位置"),

    /**
     * 请求消息类型：小视频
     */
    REQ_MESSAGE_TYPE_SHORTVIDEO("shortvideo", "小视频"),

    /**
     * 请求消息类型：事件推送
     */
    REQ_MESSAGE_TYPE_EVENT("event", "事件推送"),

    /**
     * 返回消息类型：文本
     */
    RESP_MESSAGE_TYPE_TEXT("text", "文本"),

    /**
     * 消息返回类型：图片
     */
    RESP_MESSAGE_TYPE_IMAGE("image", "图片"),

    /**
     * 消息返回类型:语音
     */
    RESP_MESSAGE_TYPE_VOICE("voice", "语音"),

    /**
     * 消息返回类型：音乐
     */
    RESP_MESSAGE_TYPE_MUSIC("music", "音乐"),

    /**
     * 消息返回类型：图文
     */
    RESP_MESSAGE_TYPE_NEWS("news", "图文"),

    /**
     * 消息返回类型：视频
     */
    RESP_MESSAGE_TYPE_VIDEO("video", "视频"),

    /**
     * 事件类型:订阅
     */
    EVENT_TYPE_SUBSCRIBE("subscribe", "订阅"),

    /**
     * 事件类型：取消订阅
     */
    EVENT_TYPE_UNSUBSCRIBE("unsubscribe", "取消订阅"),

    /**
     * 事件类型：scan(关注用户扫描带参二维码)
     */
    EVENT_TYPE_SCAN("SCAN", "关注用户扫描带参二维码"),

    /**
     * 事件类型：location(上报地理位置)
     */
    EVENT_TYPE_LOCATION("LOCATION", "上报地理位置"),

    /**
     * 事件类型：CLICK(点击菜单拉取消息)
     */
    EVENT_TYPE_CLICK("CLICK", "点击菜单拉取消息"),

    /**
     * 事件类型：VIEW(自定义菜单URl视图)
     */
    EVENT_TYPE_VIEW("VIEW", "自定义菜单URl视图");

    public String code;
    public String desc;

    WxMessageTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private static Map<String, WxMessageTypeEnum> map = new HashMap<>();

    static {
        for (WxMessageTypeEnum item : WxMessageTypeEnum.values()) {
            map.put(item.code, item);
        }
    }

    public static WxMessageTypeEnum getByCode(String code) {
        WxMessageTypeEnum value = map.get(code);
        if (value == null) {
            value = REQ_MESSAGE_TYPE_TEXT;
        }
        return value;
    }
}
