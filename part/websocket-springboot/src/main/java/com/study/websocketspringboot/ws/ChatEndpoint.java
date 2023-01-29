package com.study.websocketspringboot.ws;

import com.study.websocketspringboot.util.MessageUtils;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注意每一个客户端都有一个该类对象与之相对应
 */
@Component
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
@Data
public class ChatEndpoint {
    // 用来存储每一个客户端对应的ChatEndpoint对象
    private static Map<String, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    // 声明session对象，通过该对象可以发送消息给指定的客户端
    private Session session;

    // 声明一个HttpSession对象，因为我们之前在HttpSession对象中存储了用户名
    private HttpSession httpSession;

    // 连接建立时被调用
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // 将局部的session对象赋值给成员session
        this.session = session;
        // 获取HttpSession对象
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        // 从httpSession对象中获取用户名
        String username = (String) httpSession.getAttribute("user");
        // 将当前对象存储到容器中
        onlineUsers.put(username, this);
        // 将当前在线的用户的用户名推送给所有的客户端
        // 1.获取消息
        String message = MessageUtils.getMessage(true, null, getNames());
        // 2.调用方法进行系统消息的推送
        broadAllUsers(message);
    }

    /**
     * 广播消息
     */
    private void broadAllUsers(String message) {
        try {
            // 要将该消息推送给所有的客户端
            Set<String> names = onlineUsers.keySet();
            for (String name : names) {
                ChatEndpoint chatEndpoint = onlineUsers.get(name);
                chatEndpoint.getSession().getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有的用户名
     */
    private Set<String> getNames() {
        return onlineUsers.keySet();
    }

    // 接收到客户端发送的数据时被调用
    @OnMessage
    public void onMessage(String message, Session session) {

    }


    // 连接关闭的时候被调用
    @OnClose
    public void onClose(Session session) {

    }
}
