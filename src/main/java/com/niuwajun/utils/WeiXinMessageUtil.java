package com.niuwajun.utils;

import cn.hutool.core.util.XmlUtil;
import com.niuwajun.pojo.model.wechat.result.TextResult;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/11/30
 */
public class WeiXinMessageUtil {

    /**
     * 将二进制数据转换为16进制字符串
     *
     * @param src
     * @return
     */
    public static String byte2HexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & 0xFF);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 对字符串进行sha1加签
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String sha1(String context) throws Exception {
        // 获取sha1算法封装类
        MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
        // 进行加密
        byte[] digestResult = sha1Digest.digest(context.getBytes("UTF-8"));
        // 转换为16进制字符串
        return WeiXinMessageUtil.byte2HexString(digestResult);
    }

    /**
     * 将输入流使用指定编码转化为字符串 获取xml
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String inputStream2String(HttpServletRequest request) throws Exception {
        InputStream io = request.getInputStream();
        // 建立输入流读取类
        InputStreamReader reader = new InputStreamReader(io, "UTF-8");
        // 设定每次读取字符个数
        char[] data = new char[512];
        int dataSize = 0;
        // 循环读取
        StringBuilder stringBuilder = new StringBuilder();
        while ((dataSize = reader.read(data)) != -1) {
            stringBuilder.append(data, 0, dataSize);
        }
        return stringBuilder.toString();
    }

    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<>(16);
        SAXReader reader = new SAXReader();
        InputStream io = request.getInputStream();
        Document document = (Document) reader.read(io);
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            map.put(element.getName(), element.getText());
        }
        io.close();
        return map;
    }

    public static Map<String, String> xmlToMap(String xmlText) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<>(16);
        Document document = DocumentHelper.parseText(xmlText);
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            map.put(element.getName(), element.getText());
        }
        return map;
    }

}
