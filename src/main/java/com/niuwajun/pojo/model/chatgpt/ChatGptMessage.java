package com.niuwajun.pojo.model.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptMessage {
    String role;
    String content;
}

