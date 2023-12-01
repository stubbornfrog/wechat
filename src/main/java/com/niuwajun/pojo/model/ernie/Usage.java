package com.niuwajun.pojo.model.ernie;

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
public class Usage {

    String prompt_tokens;
    String completion_tokens;
    String total_tokens;

}

