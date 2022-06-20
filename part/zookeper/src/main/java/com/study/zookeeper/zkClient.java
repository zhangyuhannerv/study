package com.study.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class zkClient {
    // 连接集群。注意','左右不能有空格
    private String connectString = "192.168.220.21:2181,192.168.220.22:2181,192.168.220.23:2181";
    // 200秒
    private int sessionTimeOut = 200000;
    private ZooKeeper zkClient;

    /**
     * 初始化创建客户端
     *
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeOut, watchedEvent -> {
            System.out.println("-------------------------");
            List<String> children;
            try {
                children = zkClient.getChildren("/", true);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (String child : children) {
                System.out.println(child);
            }
            System.out.println("-------------------------");
        });
    }

    /**
     * 创建子节点
     */
    @Test
    public void create() throws InterruptedException, KeeperException {
        String nodeCreated = zkClient.create("/atguigu", "ss.avi".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 获取子节点
     */
    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        // 这里的watch就是创建客户端时的写的监听事件。true就是注册监听。false就是非注册监听
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }

        // 延时
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 判断节点是否存在
     */
    @Test
    public void existNode() throws InterruptedException, KeeperException {
        // 这里watch的这个参数不生效。。。
        Stat stat = zkClient.exists("/atguigu", false);
        System.out.println(stat == null ? "not exist" : "exist");
    }
}
