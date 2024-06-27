package com.study.zyh.javase.java_thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTest {
    public static ExecutorService service = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main...start");
//        fun1();
//        fun2();
//        fun3();
//        fun4();
//        fun5();
//        fun6();
//        fun7();
//        fun8();
//        fun9();
        fun10();
    }

    public static void fun1() {
        // 异步执行
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("当前线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果:" + i);
        }, service);
        System.out.println("main...end");
    }

    public static void fun2() throws ExecutionException, InterruptedException {
        // 阻塞当前线程异步执行
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果:" + i);
            return i;
        }, service);

        // 不调用get()就会异步执行,先打印end
        // 调用get()就会阻塞当前线程,先打印运行结果
        Integer i = future.get();
        System.out.println("main...end..." + i);
    }

    public static void fun3() throws ExecutionException, InterruptedException {
        // 链式调用1：whenComplete,方法完成后的感知
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程:" + Thread.currentThread().getId());
            int i = 10 / 0;
//            int i = 10 / 2;
            System.out.println("运行结果:" + i);
            return i;
        }, service).whenComplete((res, exception) -> {
            // 这里可以感知到任务结束执行了
            // 虽然能得到异常信息，但是没办法修改返回数据,只能继续执行新的任务
            System.out.println("异步任务执行结束了,结果是：" + res + ",异常是:" + exception);
        }).exceptionally(exception -> {
            // 这里也可以感知到异常，同时返回默认值
            // 结果出现异常之后，就给个默认返回
            System.out.println("感知到的异常：" + exception);
            return 10;
        });

        Integer i = future.get();
        System.out.println("main...end..." + i);
    }

    public static void fun4() throws ExecutionException, InterruptedException {
        // 链式调用2：handle，方法完成后的处理
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程:" + Thread.currentThread().getId());
            int i = 10 / 0;
//            int i = 10 / 2;
            System.out.println("运行结果:" + i);
            return i;
        }, service).handle((res, exception) -> {
            if (exception != null) {
                return 0;
            }
            return res * 2;
        });

        Integer i = future.get();
        System.out.println("main...end..." + i);
    }

    public static void fun5() {
        // 线程串行化
        // thenRun,不能获取到上一步的执行结果，同时自已运行也没有返回结果
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果:" + i);
            return i;
        }, service).thenRunAsync(() -> {
            System.out.println("任务2启动了...");
        }, service);
        System.out.println("main...end...");
    }

    public static void fun6() {
        // 线程串行化
        // thenAccept,能获取到上一步的执行结果，但是自已运行没有返回结果
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果:" + i);
            return i;
        }, service).thenAcceptAsync(res -> {
            System.out.println("上一步的返回结果" + res);
            System.out.println("任务2启动了...");
        }, service);
        System.out.println("main...end...");
    }

    public static void fun7() throws ExecutionException, InterruptedException {
        // 线程串行化
        // thenApply,既能获取到上一步的执行结果，又能有返回值
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果:" + i);
            return i;
        }, service).thenApplyAsync(res -> {
            System.out.println("上一步的返回结果" + res);
            System.out.println("任务2启动了...");
            return 20;
        }, service);
        Integer i = future.get();
        System.out.println("main...end..." + i);
    }

    public static void fun8() {
        // 组合任务,两个任务都完成才能执行第三个
        // runAfterBoth不能感知到运行结果,同时也没有返回值
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("任务1运行结果:" + i);
            return i;
        }, service);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程:" + Thread.currentThread().getId());
            System.out.println("任务2结束");
            return "hello";
        }, service);

        future1.runAfterBothAsync(future2, () -> {
            System.out.println("任务3开始...");
            System.out.println("任务3结束...");
        }, service);

        System.out.println("main...end...");

    }

    public static void fun9() {
        // 组合任务,两个任务都完成才能执行第三个
        // thenAcceptBoth能感知到运行结果,但是没有返回值
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("任务1运行结果:" + i);
            return i;
        }, service);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程:" + Thread.currentThread().getId());
            System.out.println("任务2结束");
            return "hello";
        }, service);

        future1.thenAcceptBothAsync(future2, (f1, f2) -> {
            System.out.println("任务3开始...");
            System.out.println("任务1的结果：" + f1);
            System.out.println("任务2的结果：" + f2);
            System.out.println("任务3结束...");
        }, service);

        System.out.println("main...end...");

    }

    public static void fun10() throws ExecutionException, InterruptedException {
        // 组合任务,两个任务都完成才能执行第三个
        // thenCombine能感知到运行结果,同时自身有返回值
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("任务1运行结果:" + i);
            return i;
        }, service);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2线程:" + Thread.currentThread().getId());
            System.out.println("任务2结束");
            return "hello";
        }, service);

        CompletableFuture<String> future = future1.thenCombineAsync(future2, (f1, f2) -> {
            System.out.println("任务3开始...");
            System.out.println("任务1的结果：" + f1);
            System.out.println("任务2的结果：" + f2);
            System.out.println("任务3结束...");
            return f1 + f2 + "haha";
        }, service);

        System.out.println("main...end..." + future.get());

    }
}
