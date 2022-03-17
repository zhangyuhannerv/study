package com.study.spring5_webflux.handler;

import com.study.spring5_webflux.entity.User;
import com.study.spring5_webflux.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @ClassName UserHandler
 * @Description 函数式编程模型，handler
 * @Author Zhangyuhan
 * @Date 2022/3/17
 * @Version 1.0
 */
public class UserHandler {
    private final IUserService userService;

    public UserHandler(IUserService userService) {
        this.userService = userService;
    }

    // 根据id
    public Mono<ServerResponse> getUserById(ServerRequest request) {
        // 获取请求的id值
        int id = Integer.valueOf(request.pathVariable("id"));

        // 空值处理(如果找不到user)
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        // 调用service得到数据
        Mono<User> userMono = userService.getUserById(id);
        // 把userMono进行转换然后返回
        // 使用Reactor操作符flatMap执行此操作
        return userMono.flatMap(
                person -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromObject(person))
                        .switchIfEmpty(notFound)
        );

    }

    // 查询所有
    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        // 调用service的方法得到结果
        Flux<User> allUser = userService.getAllUser();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(allUser, User.class);
    }

    // 添加
    public Mono<ServerResponse> saveUser(ServerRequest request) {
        // 得到User对象
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().build(this.userService.saveUser(userMono));
    }
}
