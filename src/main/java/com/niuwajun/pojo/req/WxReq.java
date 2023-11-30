package com.niuwajun.pojo.req;

import lombok.Data;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/29
 */
@Data
public class WxReq {

    /**
     * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     */
    private String signature;

    /**
     *时间戳
     */
    private String timestamp;

    /**
     *随机数
     */
    private String nonce;

    /**
     * 随机字符串
     */
    private String encrypt_type;

    /**
     *
     */
    private String msg_signature;

}
