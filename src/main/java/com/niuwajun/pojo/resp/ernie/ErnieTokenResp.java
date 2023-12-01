package com.niuwajun.pojo.resp.ernie;

import lombok.Data;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/12/1
 */
@Data
public class ErnieTokenResp {

    /**
     * 访问凭证
     */
    private String access_token;

    /**
     * 有效期，Access Token的有效期。
     * 说明：单位是秒，有效期30天
     */
    private int expires_in;

    /**
     * 错误码
     * 说明：响应失败时返回该字段，成功时不返回
     */
    private String error;

    /**
     * 错误描述信息，帮助理解和解决发生的错误
     * 说明：响应失败时返回该字段，成功时不返回
     */
    private String error_description;

    /**
     * 暂时未使用，可忽略
     */
    private String session_key;

    /**
     * 暂时未使用，可忽略
     */
    private String refresh_token;

    /**
     * 暂时未使用，可忽略
     */
    private String scope;

    /**
     * 暂时未使用，可忽略
     */
    private String session_secret;

}
