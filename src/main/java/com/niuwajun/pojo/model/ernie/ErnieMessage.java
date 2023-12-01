package com.niuwajun.pojo.model.ernie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/12/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErnieMessage {

    /**
     * 当前支持以下：
     * user: 表示用户
     * assistant: 表示对话助手
     * function: 表示函数
     */
    String role;
    /**
     * 对话内容，说明：
     * （1）当前message存在function_call，且role="assistant"时可以为空，其他场景不能为空
     * （2）最后一个message对应的content不能为blank字符，包含空格、"\n"、“\r”、“\f”等
     */
    String content;
    String name;
    String function_call;
}
