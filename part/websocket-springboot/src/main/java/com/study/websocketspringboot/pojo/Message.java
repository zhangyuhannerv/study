package com.study.websocketspringboot.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String toName;
    private String message;
}
