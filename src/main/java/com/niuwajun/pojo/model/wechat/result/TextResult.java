package com.niuwajun.pojo.model.wechat.result;

import lombok.Data;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
@Data
public class TextResult extends BaseResult {
    /**
     * 回复的消息内容
     */
    private String Content;
}
