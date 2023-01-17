package com.study.websocketspringboot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMessage {
    private Boolean isSystem;
    private String fromName;
    private Object message;
}
