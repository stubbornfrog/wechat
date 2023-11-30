package com.niuwajun.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
public interface WxMessageService {

    /**
     * 接受消息并回复
     *
     * @param message
     * @return
     */
    String receiveAndResponseMessage(Map<String, String> message);

}
