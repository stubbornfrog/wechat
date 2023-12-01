package com.niuwajun.pojo.resp.ernie;

import com.niuwajun.pojo.model.ernie.Usage;
import lombok.Data;

/**
 * @Author: zmm
 * @Des:
 * @Date: 2023/12/1
 */
@Data
public class ErnieChatResp {

//    {
//        "id":"as-bcmt5ct4iy",
//            "object":"chat.completion",
//            "created":1680167072,
//            "result":"您好，我是百度研发的知识增强大语言模型，中文名是文心一言，英文名是ERNIE-Bot。我能够与人对话互动，回答问题，协助创作，高效便捷地帮助人们获取信息、知识和灵感。",
//            "is_truncated":false,
//            "need_clear_history":false,
//            "usage":{
//        "prompt_tokens":7,
//                "completion_tokens":67,
//                "total_tokens":74
//    }
//    }

    /**
     * 本轮对话的id
     */
    private String id;

    /**
     * 回包类型
     * chat.completion：多轮对话返回
     */
    private String object;

    /**
     * 时间戳
     */
    private int created;

    /**
     * 表示当前子句是否是最后一句。只有在流式接口模式下会返回该字段
     */
    private boolean is_end;

    /**
     * 表示当前子句的序号。只有在流式接口模式下会返回该字段
     */
    private Integer sentence_id;

    /**
     * 当前生成的结果是否被截断
     */
    private Boolean is_truncated;

    /**
     * 输出内容标识，说明：
     * · normal：输出内容完全由大模型生成，未触发截断、替换
     * · stop：输出结果命中入参stop中指定的字段后被截断
     * · length：达到了最大的token数，根据EB返回结果is_truncated来截断
     * · content_filter：输出内容被截断、兜底、替换为**等
     * · function_call：调用了funtion call功能
     */
    private String finish_reason;

    /**
     * 搜索数据，当请求参数enable_citation为true并且触发搜索时，会返回该字段
     */
    private String search_info;

    /**
     * 对话返回结果
     */
    private String result;

    /**
     * 表示用户输入是否存在安全，是否关闭当前会话，清理历史会话信息
     * true：是，表示用户输入存在安全风险，建议关闭当前会话，清理历史会话信息
     * false：否，表示用户输入无安全风险
     */
    private Boolean need_clear_history;

    /**
     * 当need_clear_history为true时，此字段会告知第几轮对话有敏感信息，如果是当前问题，ban_round=-1
     */
    private Integer ban_round;

    /**
     * token统计信息，token数 = 汉字数+单词数*1.3 （仅为估算逻辑）
     */
    private Usage usage;

    /**
     * 由模型生成的函数调用，包含函数名称，和调用参数
     */
    private String function_call;


}
