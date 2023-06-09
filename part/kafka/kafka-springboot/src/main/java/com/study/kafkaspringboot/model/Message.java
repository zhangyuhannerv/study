package com.study.kafkaspringboot.model;

import lombok.Data;

@Data
public class Message {
    private String type;
    private Object data;
}
