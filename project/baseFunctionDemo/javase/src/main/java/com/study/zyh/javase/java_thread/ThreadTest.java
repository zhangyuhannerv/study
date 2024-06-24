package com.study.zyh.javase.java_thread;

import java.util.concurrent.*;

public class ThreadTest {
    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main...start");
        // 第一种线程方式，继承Thread类
        Thread01 thread01 = new Thread01();
        thread01.start();
        // 第二种线程方式，实现Runnable接口
        Runnable01 runnable01 = new Runnable01();
        new Thread(runnable01).start();
        // 第三种线程方式，(jdk1.5之后)实现Callable接口+FutureTask(可以拿到返回结果，可以处理异常)
        // 会阻塞等待当前线程，等待整个异步线程任务执行完成，获取返回结果
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable01());
        new Thread(futureTask).start();
        Integer integer = futureTask.get();
        // 第四种方式：给线程池直接提交任务
        // 在业务代码里，以上三种线程启动的方式都不用
        // 应该将所有的多线程的异步任务都交给线程池
        // 系统中最好保持线程池在3个或3个以内
        // submit()能获取返回值，execute不能获取返回值
        executorService.execute(new Runnable01());

        // 总结：
        // 1、2两种方式不能获得返回值，3可以获取返回值
        // 1、2、3都不能控制资源
        // 4可以控制资源，性能稳定

        System.out.println("main...end..." + integer);
    }

    public static class Thread01 extends Thread {
        @Override
        public void run() {
            System.out.printf("当前线程:" + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果:" + i);
        }
    }

    public static class Runnable01 implements Runnable {
        @Override
        public void run() {
            System.out.printf("当前线程:" + Thread.currentThread().getId());
            int i = 12 / 2;
            System.out.println("运行结果:" + i);
        }
    }

    public static class Callable01 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.printf("当前线程:" + Thread.currentThread().getId());
            int i = 14 / 2;
            System.out.println("运行结果:" + i);
            return i;
        }
    }


}
