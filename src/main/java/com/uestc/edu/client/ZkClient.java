package com.uestc.edu.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @ClassName: ZkClient.java
 * @Description: zk 客户端
 * @Author: linchen8@lenovo.com
 * @Date: 2022/9/30 11:17
 **/
public class ZkClient {
    public static CuratorFramework getClient() {
        return CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                //连接超时时间，默认15秒
                .connectionTimeoutMs(15 * 1000)
                //会话超时时间，默认60秒
                .sessionTimeoutMs(60 * 1000)
                //设置命名空间
                .namespace("arch")
                .build();
    }

    public static void create(final CuratorFramework client, final String path, final byte[] payload) throws Exception {
        client.create().creatingParentsIfNeeded().forPath(path, payload);
    }

    public static void createEphemeral(final CuratorFramework client, final String path, final byte[] payload) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    }

    public static String createEphemeralSequential(final CuratorFramework client, final String path, final byte[] payload) throws Exception {
        return client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, payload);
    }

    public static void setData(final CuratorFramework client, final String path, final byte[] payload) throws Exception {
        client.setData().forPath(path, payload);
    }

    public static void delete(final CuratorFramework client, final String path) throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    public static void guaranteedDelete(final CuratorFramework client, final String path) throws Exception {
        client.delete().guaranteed().forPath(path);
    }

    public static String getData(final CuratorFramework client, final String path) throws Exception {
        return new String(client.getData().forPath(path));
    }

    public static List<String> getChildren(final CuratorFramework client, final String path) throws Exception {
        return client.getChildren().forPath(path);
    }

}
