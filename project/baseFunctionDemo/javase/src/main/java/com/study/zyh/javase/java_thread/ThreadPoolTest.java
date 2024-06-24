package com.study.zyh.javase.java_thread;

import java.util.concurrent.*;

public class ThreadPoolTest {
    public static void main(String[] args) {
        // 线程池的创建
        // 1.Executors工具类快速创建
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        // 2.原生创建方式:有7大参数

        // 核心线程数，线程池创建好后总是保持的线程数量。除非设置了allowCoreThreadTimeOut该属性，这样核心线程也会被回收
        // int corePoolSize

        // 最大线程数量,控制最大并发数量
        // int maximumPoolSize

        // 存活时间：如果当前线程池的线程数量大于核心数量，多出来的线程在该时间内没有接到新的任务，该线程就会被回收
        // long keepAliveTime

        // 存活时间单位（keepAliveTime）
        // TimeUnit unit

        // 阻塞队列：如果任务有很多，超过maximumPoolSize设置的最大线程数量，超出的任务就会存储在这个队列里面
        // 只要有线程空闲了就会去队列里取出新的任务继续执行
        // BlockingQueue<Runnable> workQueue

        // 线程的创建工厂（默认就有），但是也可以自定义，但是很少用（比如需要自定义线程的名字）
        // ThreadFactory threadFactory

        // 如果阻塞队列满了，存不了更多的等待线程了，用来处理这种情况
        // 此时在这里定义拒绝策略，按照定义的策略执行任务
        // RejectedExecutionHandler handler

        /*
         * 运行流程：
         * 1、线程池创建，准备好 core 数量的核心线程，准备接受任务
         * 2、新的任务进来，用 core 准备好的空闲线程执行。
         * (1) 、core 满了，就将再进来的任务放入阻塞队列中。空闲的 core 就会自己去阻塞队
         * 列获取任务执行
         * (2) 、阻塞队列满了，就直接开新线程执行，最大只能开到 max 指定的数量
         * (3) 、max 都执行好了。Max-core 数量空闲的线程会在 keepAliveTime 指定的时间后自
         * 动销毁。最终保持到 core 大小
         * (4) 、如果线程数开到了 max 的数量，还有新任务进来，就会使用 reject 指定的拒绝策
         * 略进行处理
         * 3、所有的线程创建都是由指定的 factory 创建的。
         * 面试：
         * 一个线程池 core 7； max 20 ，queue：50，100 并发进来怎么分配的；
         * 先有 7 个能直接得到执行，接下来 50 个进入队列排队，在多开 13 个继续执行。现在 70 个
         * 被安排上了。剩下 30 个默认执行拒绝策略。
         */

        // 原生创建线程池
        // LinkedBlockingQueue默认长度是Integer的最大值，此时可能内存占满，所以一定要根据业务自行设置
        // ThreadPoolExecutor.AbortPolicy()是拒绝线程执行，并抛出异常的策略
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
                50,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());


        // Executors快速创建线程池
        /*
         * newCachedThreadPool(核心是0，所有线程无任务都会被回收)
         * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
         * newFixedThreadPool(核心和最大值相等，且所有线程永不回收)
         * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
         * newScheduledThreadPool
         * 创建一个定长线程池，支持定时及周期性任务执行。
         * newSingleThreadExecutor
         * 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
         */
    }
}
