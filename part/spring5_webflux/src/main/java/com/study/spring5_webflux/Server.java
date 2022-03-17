package com.study.spring5_webflux;

import com.study.spring5_webflux.handler.UserHandler;
import com.study.spring5_webflux.service.IUserService;
import com.study.spring5_webflux.service.impl.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

/**
 * @ClassName Server
 * @Description 函数式编程完成webflux
 * @Author Zhangyuhan
 * @Date 2022/3/17
 * @Version 1.0
 */
public class Server {
    // 最终调用
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.createReactorServer();
        System.out.println("enter to exit");
        System.in.read();
    }

    // 1.创建Router路由
    public RouterFunction<ServerResponse> routingFunction() {
        // 创建handler对象
        IUserService userService = new UserServiceImpl();
        UserHandler handler = new UserHandler(userService);

        // 设置路由部分
        return RouterFunctions.route(
                GET("/user/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getUserById
        ).andRoute(
                GET("/users").and(accept(MediaType.APPLICATION_JSON)), handler::getAllUsers
        );

    }

    // 2.创建服务器完成适配
    public void createReactorServer() {
        // 路由和handler的适配
        RouterFunction<ServerResponse> router = routingFunction();
        HttpHandler httpHandler = toHttpHandler(router);
        ReactorHttpHandlerAdapter reactorHttpHandlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);

        // 创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.handle(reactorHttpHandlerAdapter).bindNow();
    }
}
