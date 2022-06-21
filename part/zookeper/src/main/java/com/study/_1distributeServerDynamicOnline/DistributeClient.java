package com.study._1distributeServerDynamicOnline;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {
    // 连接集群。注意','左右不能有空格
    private String connectString = "192.168.220.21:2181,192.168.220.22:2181,192.168.220.23:2181";
    // 200秒
    private int sessionTimeOut = 200000;
    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DistributeClient client = new DistributeClient();
        // 1.获取zk连接
        client.getConnect();
        // 2.监听/servers下面的子节点的增加和删除
        client.getServerList();
        // 3.业务逻辑（睡觉）
        client.business();
    }


    private void getConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeOut, watchedEvent -> {
            try {
                getServerList();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void getServerList() throws InterruptedException, KeeperException {
        List<String> children = zooKeeper.getChildren("/servers", true);
        ArrayList<String> servers = new ArrayList<>();
        for (String child : children) {
            byte[] data = zooKeeper.getData("/servers/" + child, false, null);
            servers.add(new String(data));
        }

        // 打印
        System.out.println(servers);
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }


}
