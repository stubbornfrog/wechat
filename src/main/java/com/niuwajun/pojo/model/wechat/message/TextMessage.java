package com.niuwajun.pojo.model.wechat.message;

import lombok.Data;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
@Data
public class TextMessage extends BaseInfo {
    /**
     * 接收用户发送的文字消息
     */
    private String Content;
}
