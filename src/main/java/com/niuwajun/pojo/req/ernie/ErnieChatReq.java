package com.niuwajun.pojo.req.ernie;

import com.niuwajun.pojo.model.ernie.ErnieMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/12/1
 */
@Data
public class ErnieChatReq {

    /**
     * 聊天上下文信息。说明：
     * （1）messages成员不能为空，1个成员表示单轮对话，多个成员表示多轮对话
     * （2）最后一个message为当前请求的信息，前面的message为历史对话信息
     * （3）必须为奇数个成员，成员中message的role必须依次为user或function、assistant，第一个message的role不能是function
     * （4）最后一个message的content长度（即此轮对话的问题）不能超过4800 个字符，且不能超过2000 tokens
     * （5）如果messages中content总长度大于4800 个字符或2000 tokens，系统会依次遗忘最早的历史会话，直到content的总长度不超过4800 个字符且不超过2000 tokens
     */
    private List<ErnieMessage> messages = new ArrayList<>();
    ;

    public void addMessages(ErnieMessage message) {
        this.messages.add(message);
    }

}
