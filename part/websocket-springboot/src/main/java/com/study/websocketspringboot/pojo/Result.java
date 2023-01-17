package com.study.websocketspringboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean flag;
    private String message;
}
