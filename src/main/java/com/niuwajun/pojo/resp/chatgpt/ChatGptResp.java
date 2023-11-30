package com.niuwajun.pojo.resp.chatgpt;

import com.niuwajun.pojo.model.chatgpt.Choices;
import com.niuwajun.pojo.model.chatgpt.Usage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptResp {

    String id;
    String object;
    String created;
    String model;
    String system_fingerprint;
    Usage usage;
    List<Choices> choices;
}


