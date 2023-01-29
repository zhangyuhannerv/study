package com.study.websocketspringboot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.websocketspringboot.pojo.ResultMessage;

public class MessageUtils {
    public static String getMessage(boolean isSystemMessage, String fromName, Object message) {
        try {
            ResultMessage result = new ResultMessage();
            result.setIsSystem(isSystemMessage);
            result.setMessage(message);
            if (fromName != null) {
                result.setFromName(fromName);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
