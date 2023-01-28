package com.study._2distributeLock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DistributeLock {
    private ZooKeeper zk;
    private final String connectString = "127.0.0.1:2181";
    private final int sessionTimeOut = 2000;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private CountDownLatch waitLatch = new CountDownLatch(1);
    private String waitPath;
    private String currentNode;

    public DistributeLock() throws IOException, InterruptedException, KeeperException {
        // 获取连接
        zk = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // countLatch 如果连接上zk，可以释放掉
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
                // waitLatch 需要释放
                if (watchedEvent.getType() == Event.EventType.NodeDeleted && watchedEvent.getPath().equals(waitPath)) {
                    waitLatch.countDown();
                }
            }
        });
        // 等待zk正常连接，往下走程序
        countDownLatch.await();
        // 判断根结点/lock是否存在
        Stat stat = zk.exists("/locks", false);
        if (stat == null) {
            // 创建一下根节点
            zk.create("/locks", "lcoks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    // 对zk加锁
    public void zkLock() {
        // 创建对应的临时带序号的节点
        try {
            currentNode = zk.create("/locks/seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 判断创建的节点是否是最小的序号节点，如果是获取到锁；如果不是，监听他序号的前一个节点
            List<String> children = zk.getChildren("/locks", false);
            // 如果children，只有一个，那就直接获取锁；如果有多个节点，就需要判断谁最小
            if (children.size() == 1) {
                return;
            } else {
                Collections.sort(children);
                // 获取节点名称
                String thisNode = currentNode.substring("/locks/".length());
                // 通过节点名称获取到在集合中的位置
                int index = children.indexOf(thisNode);
                // 判断
                if (index == -1) {
                    System.out.println("数据异常");
                } else if (index == 0) {
                    // 就一个节点 获取到锁
                    return;
                } else {
                    // 需要监听当前节点的前一个节点
                    waitPath = "/locks/" + children.get(index - 1);
                    zk.getData(waitPath, true, null);
                    // 等待监听
                    waitLatch.await();
                    return;
                }
            }
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    // 对zk解锁
    public void unZkLock() {
        // 删除节点
        try {
            zk.delete(currentNode, -1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }
}
