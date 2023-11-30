package com.niuwajun.pojo.req.chatgpt;

import com.niuwajun.pojo.model.chatgpt.ChatGptMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
@Data
public class ChatGptReq {

    String model = "gpt-3.5-turbo";

    List<ChatGptMessage> messages = new ArrayList<>();

    public void addMessages(ChatGptMessage message) {
        this.messages.add(message);
    }
}
