package com.study._1distributeServerDynamicOnline;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class DistributeServer {
    // 连接集群。注意','左右不能有空格
    private String connectString = "192.168.220.21:2181,192.168.220.22:2181,192.168.220.23:2181";
    // 200秒
    private int sessionTimeOut = 200000;
    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DistributeServer server = new DistributeServer();
        // 1.获取zk连接
        server.getConnect();

        // 2.服务器注册zk集群（创建临时节点）
        server.regeist(args[0]);

        // 3.模拟服务器的运行，启动业务逻辑（睡觉）
        server.business();
    }

    /**
     * 线程沉睡
     */
    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 创建节点
     */
    private void regeist(String hostName) throws InterruptedException, KeeperException {
        String create = zooKeeper.create("/servers/server", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostName + "is online");
    }

    /**
     * 创建客户端
     */
    private void getConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeOut, watchedEvent -> {

        });
    }
}
