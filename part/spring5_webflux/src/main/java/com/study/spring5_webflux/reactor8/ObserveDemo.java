package com.study.spring5_webflux.reactor8;

import java.util.Observable;

/**
 * @ClassName ObserveDemo
 * @Description 使用java8实现观察者模式。注意9及以上的版本和8不同
 * @Author Zhangyuhan
 * @Date 2022/3/15
 * @Version 1.0
 */
public class ObserveDemo extends Observable {
    public static void main(String[] args) {
        ObserveDemo observe = new ObserveDemo();
        // 添加观察者1
        observe.addObserver((o, arg) -> {
            System.out.println("发生了变化");
        });
        // 添加观察者2
        observe.addObserver((o, arg) -> {
            System.out.println("收到观察者通知，准备改变");
        });
        observe.setChanged();// 数据变化
        observe.notifyObservers();// 通知
    }
}
