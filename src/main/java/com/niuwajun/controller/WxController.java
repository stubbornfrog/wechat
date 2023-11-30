package com.niuwajun.controller;

import cn.hutool.core.util.StrUtil;
import com.niuwajun.pojo.req.WxReq;
import com.niuwajun.service.WxMessageService;
import com.niuwajun.utils.WeiXinMessageUtil;
import com.niuwajun.weixin.aes.WXBizMsgCrypt;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/29
 */
@RestController
public class WxController {

    @Resource
    private WxMessageService wxMessageService;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    private static final String Token = "stubbornfrog";
    private static final String EncodingAESKey = "ikUsuLSxByX41wctmB4tgVLGZPSug48rwk7xEHHKRXe";
    private static final String AppID = "wxf2727953018a6358";
    private static final String EncryptTypeAES = "aes";

    @GetMapping("/wx")
    public String wxGet(@RequestParam(value = "signature") String signature,
                        @RequestParam(value = "timestamp") String timestamp,
                        @RequestParam(value = "nonce") String nonce,
                        @RequestParam(value = "echostr") String echostr) {
        if (check(timestamp, nonce, signature)) {
            return echostr;
        } else {
            return "error";
        }
    }

    @PostMapping("/wx")
    public String wxPost(HttpServletRequest req, HttpServletResponse resp, WxReq wxReq) {
        try {
            req.setCharacterEncoding("utf8");
            resp.setCharacterEncoding("utf8");
            Map<String, String> decryptMap = new HashMap<>(8);
            //接受的消息体
            if (StrUtil.equalsIgnoreCase(wxReq.getEncrypt_type(), EncryptTypeAES)) {
                //非明文模式
                Map<String, String> requestMap = parseRequest(req.getInputStream());
                String format = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><Encrypt><![CDATA[%2$s]]></Encrypt></xml>";
                String fromXML = String.format(format, requestMap.get("ToUserName"), requestMap.get("Encrypt"));
                WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(Token, EncodingAESKey, AppID);
                String result = wxBizMsgCrypt.decryptMsg(wxReq.getMsg_signature(), wxReq.getTimestamp(), wxReq.getNonce(), fromXML);
                decryptMap = WeiXinMessageUtil.xmlToMap(result);
                System.out.println("解密后明文: " + decryptMap);
            } else {
                // 明文模式直接解析
                decryptMap = parseRequest(req.getInputStream());
                System.out.println("明文: " + decryptMap);
            }
            String message = wxMessageService.receiveAndResponseMessage(decryptMap);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static boolean check(String timestamp, String nonce, String signature) {
        //1) 将token、timestamp、nonce三个参数进行字典序排序
        String[] array = new String[]{timestamp, nonce, Token};
        Arrays.sort(array);
        //2）将三个参数字符串拼接成一个字符串进行sha1加密
        String str = array[0] + array[1] + array[2];
        String mySign = sha1(str);
        //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return mySign.equals(signature);
    }

    private static String sha1(String str) {
        try {
            //获取一个加密对象
            MessageDigest md = MessageDigest.getInstance("sha1");
            //加密
            byte[] digest = md.digest(str.getBytes());
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            StringBuilder sb = new StringBuilder();
            //处理加密结果，一个byte分别 处理 高四位和低四位，范围都是0-15(相当于16进制的数），所以分开处理
            for (byte b : digest) {
                //高四位向右移动4位，让他与15,得到一个0-15的数字，然后转换成16进制的
                sb.append(chars[(b >> 4) & 15]);
                //处理低四位
                sb.append(chars[b & 15]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map<String, String> parseRequest(ServletInputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        HashMap<String, String> map = new HashMap<>();
        try {
            Document read = saxReader.read(inputStream);
            //获取根节点
            Element root = read.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements) {
                map.put(element.getName(), element.getStringValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
