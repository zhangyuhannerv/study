package com.study.spring5_webflux.reactor8;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName TestReactor
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/3/15
 * @Version 1.0
 */
public class TestReactor {
    public static void main(String[] args) {
        // just方法直接声明
        Flux.just(1, 2, 3, 4).subscribe(System.out::println);
        Mono.just(1).subscribe(System.out::println);

        // 其他方法
        Integer[] array = {1, 2, 3, 4};
        Flux.fromArray(array);

        List<Integer> list = Arrays.asList(array);
        Flux.fromIterable(list);

        Flux.fromStream(Arrays.stream(array));


        // 错误信号
        // Flux.error(new RuntimeException());
    }
}
