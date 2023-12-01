package com.niuwajun.chatgpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niuwajun.pojo.model.chatgpt.ChatGptMessage;
import com.niuwajun.pojo.model.chatgpt.Choices;
import com.niuwajun.pojo.model.ernie.ErnieMessage;
import com.niuwajun.pojo.req.ernie.ErnieChatReq;
import com.niuwajun.pojo.resp.chatgpt.ChatGptResp;
import com.niuwajun.pojo.resp.ernie.ErnieChatResp;
import com.niuwajun.pojo.resp.ernie.ErnieTokenResp;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/12/1
 */
public class ErnieBot {

    /**
     * 应用的的ApiKey
     */
    private String apiKey = "UY9i5ckYepkoHKs2wG0asi1L";
    /**
     * 应用的ApiKey
     */
    private String secretKey = "mhlRicpa4AsoebD1qC8STOrYssP2rNcP";

    /**
     * 获取token的url
     */
    private String token_url = "https://aip.baidubce.com/oauth/2.0/token";
    /**
     * 对应的请求接口
     */
    private String url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions";
    /**
     * 默认编码
     */
    private Charset charset = StandardCharsets.UTF_8;
    /**
     * 相应超时时间，毫秒
     */
    private int responseTimeout = 20000;


    public String getToken(CloseableHttpClient client) {
        try {
            List<NameValuePair> nvp = new ArrayList<>();
            // GET 请求参数
            nvp.add(new BasicNameValuePair("grant_type", "client_credentials"));
            nvp.add(new BasicNameValuePair("client_id", apiKey));
            nvp.add(new BasicNameValuePair("client_secret", secretKey));
            URI uri = new URIBuilder(new URI(token_url))
                    .addParameters(nvp)
                    .build();
            // 创建一个HttpGet
            HttpPost httpPost = new HttpPost(token_url);
            httpPost.setUri(uri);
            // 创建一个ObjectMapper，用于解析和创建json
            ObjectMapper objectMapper = new ObjectMapper();
            // 设置请求头
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            // 用于设置超时时间
            RequestConfig config = RequestConfig
                    .custom()
                    .setResponseTimeout(responseTimeout, TimeUnit.MILLISECONDS)
                    .build();
            httpPost.setConfig(config);
            // 提交请求
            return client.execute(httpPost, response -> {
                // 得到返回的内容
                String resStr = EntityUtils.toString(response.getEntity(), charset);
//                System.out.println(resStr);
                // 转换为对象
                ErnieTokenResp ernieTokenResp = objectMapper.readValue(resStr, ErnieTokenResp.class);
                // 返回信息
                return ernieTokenResp.getAccess_token();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ErnieBot ernieBot = new ErnieBot();
//        ernieBot.getToken(httpClient);
        final String result = ernieBot.getAnswer(httpClient, "你是谁");
        System.out.println(result);
    }


    public String getAnswer(CloseableHttpClient client, String question) {
        List<NameValuePair> nvp = new ArrayList<>();
        // GET 请求参数
        nvp.add(new BasicNameValuePair("access_token", getToken(client)));
        URI uri = null;
        try {
            uri = new URIBuilder(new URI(url))
                    .addParameters(nvp)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // 创建一个HttpPost
        HttpPost httpPost = new HttpPost(url);
        httpPost.setUri(uri);
        // 创建一个ObjectMapper，用于解析和创建json
        ObjectMapper objectMapper = new ObjectMapper();
        // 设置请求参数
        ErnieChatReq ernieChatReq = new ErnieChatReq();
        ernieChatReq.addMessages(new ErnieMessage("user", question, null, null));
        HttpEntity httpEntity = null;
        try {
            // 对象转换为json字符串
            httpEntity = new StringEntity(objectMapper.writeValueAsString(ernieChatReq), charset);
        } catch (JsonProcessingException e) {
            System.out.println(question + "->json转换异常");
            return null;
        }
        httpPost.setEntity(httpEntity);
        // 设置请求头
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        // 用于设置超时时间
        RequestConfig config = RequestConfig
                .custom()
                .setResponseTimeout(responseTimeout, TimeUnit.MILLISECONDS)
                .build();
        httpPost.setConfig(config);
        try {
            // 提交请求
            return client.execute(httpPost, response -> {
                // 得到返回的内容
                String resStr = EntityUtils.toString(response.getEntity(), charset);
                // 转换为对象
                ErnieChatResp responseParameter = objectMapper.readValue(resStr, ErnieChatResp.class);
                // 返回信息
                return responseParameter.getResult();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
