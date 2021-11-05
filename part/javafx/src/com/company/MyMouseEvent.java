package com.company;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


/**
 * @ClassName MyMouseEvent
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/11/5
 * @Version 1.0
 */
public class MyMouseEvent implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        System.out.println("你点击了按钮");
    }
}
