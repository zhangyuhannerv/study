package com.study._2distributeLock;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DistributeLockTest {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final DistributeLock lock1 = new DistributeLock();
        final DistributeLock lock2 = new DistributeLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.zkLock();
                    System.out.println("线程1启动，获取到锁");
                    TimeUnit.SECONDS.sleep(5);
                    lock1.unZkLock();
                    System.out.println("线程1，释放锁");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.zkLock();
                    System.out.println("线程2启动，获取到锁");
                    TimeUnit.SECONDS.sleep(5);
                    lock2.unZkLock();
                    System.out.println("线程2，释放锁");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
