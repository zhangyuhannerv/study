package com.study.spring5_webflux;

import com.study.spring5_webflux.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * @ClassName Client
 * @Description 使用client访问server
 * @Author Zhangyuhan
 * @Date 2022/3/17
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) {
        // 通过服务器的地址创建
        WebClient webClient = WebClient.create("http://127.0.0.1:62151");

        // 根据id查询
        String id = "1";
        User user = webClient.get()
                .uri("/user/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .block();
        System.out.println(user);

        // 查询所有
        Flux<User> userFlux = webClient.get()
                .uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);
        userFlux.map(person -> person)
                .buffer()
                .doOnNext(System.out::println)
                .blockFirst();
    }
}
